package com.aoji.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.*;
import com.aoji.mapper.*;
import com.aoji.model.*;
import com.aoji.model.req.SendMessageReq;
import com.aoji.model.req.TransferReq;
import com.aoji.model.res.TransferRes;
import com.aoji.service.*;
import com.aoji.utils.HttpUtils;
import com.aoji.utils.ServiceUtils;
import com.aoji.vo.AllotVO;
import com.aoji.vo.SaveTransferVO;
import com.aoji.vo.TransferReceiverVO;
import com.aoji.vo.TransferVO;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransferSendServiceImpl implements TransferSendService {

    @Autowired
    TransferSendInfoMapper transferSendInfoMapper;

    @Autowired
    TransferReceiveService transferReceiveService;
    @Autowired
    UserTaskRelationService userTaskRelationService;
    @Autowired
    ServiceInfoMapper serviceInfoMapper;
    @Autowired
    StudentServiceInfoMapper studentServiceInfoMapper;
    @Autowired
    UserService userService;
    @Autowired
    StudentInfoMapper studentInfoMapper;
    @Autowired
    CountryInfoMapper countryInfoMapper;
    @Autowired
    StudentService studentService;
    @Autowired
    ApplyInfoMapper applyInfoMapper;
    @Autowired
    ApplyCollegeService applyCollegeService;
    @Autowired
    BranchService branchService;

    @Autowired
    OldTransferRecordMapper oldTransferRecordMapper;
    @Autowired
    TransferInfoMapper transferInfoMapper;
    @Autowired
    TransferSendMapper transferSendMapper;
    @Autowired
    TransferService transferService;
    @Autowired
    RoleService roleService;

    @Value("${updateStudent.url}")
    private String studentUpdateUrl;

    public static final Logger logger = LoggerFactory.getLogger(TransferSendServiceImpl.class);

    @Override
    public int transferInfoRelatedQueryCount(TransferVO transferVO) {
        transferVO.setDeleteStatus(false);
        return transferSendInfoMapper.transferInfoRelatedQueryCount(transferVO);
    }

    /**
     * 转案信息查询
     *
     * @param transferVO
     * @return
     */
    @Override
    public List<TransferVO> transferInfoRelatedQuery(TransferVO transferVO) {
        transferVO.setDeleteStatus(false);
        return transferSendInfoMapper.transferInfoRelatedQuery(transferVO);
    }

    @Override
    public int updateById(TransferSendInfo transferSendInfo) {
        return transferSendInfoMapper.updateByPrimaryKeySelective(transferSendInfo);
    }

    @Override
    public TransferSendInfo getTransferSendById(Integer id) {
        TransferSendInfo transferSendInfo = new TransferSendInfo();
        transferSendInfo.setId(id);
        transferSendInfo.setDeleteStatus(false);
        List<TransferSendInfo> transferSendInfos = transferSendInfoMapper.select(transferSendInfo);
        if (transferSendInfos.size() > 0) {
            return transferSendInfos.get(0);
        }
        return null;
    }

    @Override
    public int insert(TransferSendInfo transferSendInfo) {
        transferSendInfo.setCreateTime(new Date());
        transferSendInfo.setDeleteStatus(false);
        return transferSendInfoMapper.insert(transferSendInfo);
    }

    /**
     * 保存转案信息
     *
     * @param saveTransferVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveTransfer(SaveTransferVO saveTransferVO) {
        logger.info(JSONObject.toJSONString(saveTransferVO));
        SysUser sysUser = userService.getLoginUser();
        if (sysUser == null || sysUser.getOaid() == null) {
            logger.error(ErrorMessage.SESSION_TIMEOUT);
            return false;
        }
        Integer id = saveTransferVO.getId();
        String studentNo = saveTransferVO.getStudentNo();
        //页面提交的数据集合
        List<TransferReceiverVO> transferReceiverVOS = saveTransferVO.getTransferReceiverVOList();
        //筛选之后的集合
        List<TransferReceiverVO> transferReceiverVOList = new ArrayList<>();
        //去掉空的接收人
        transferReceiverVOS.forEach(transferReceiverVO -> {
            if (StringUtils.hasText(transferReceiverVO.getReceiveNo())) {
                transferReceiverVOList.add(transferReceiverVO);
            }
        });
        if (transferReceiverVOList.size() < 1) {
            logger.error("请选择接收人");
            return false;
        }
        String type = saveTransferVO.getType();
        Integer applyId = saveTransferVO.getApplyId();

        //查询该学生的转案信息
        TransferVO transferVO = new TransferVO();
        transferVO.setStudentNo(studentNo);
        transferVO.setQueryType(Contants.TRANSFER_TYPE_VISA);
        transferVO.setManager(Contants.YES_FLAG);
        transferVO.setOperatorNo(sysUser.getOaid());
        transferVO.setReceiverNo(sysUser.getOaid());
        List<TransferVO> transferVOS = this.transferInfoRelatedQuery(transferVO);

        //判断是文签还是外联
        TransferSendInfo transferSend = new TransferSendInfo();
        transferSend.setEnableStatus(false);
        boolean changeCopyOperator = true;
        boolean changeCopyMaker = true;
        if (Contants.TRANSFER_TYPE_VISA.equals(type)) {

            int index = 0;
            for (TransferReceiverVO transferReceiverVO : transferReceiverVOList) {
                if (!transferVOS.isEmpty()) {
                    for (TransferVO transferVO1 : transferVOS) {
                        //文签
                        if (index == 0 && transferVO1.getReceiverNo().equals(transferReceiverVO.getReceiveNo()) && transferVO1.isCopyOperator()
                                && transferVO1.getOperatorNo().equals(sysUser.getOaid()) && (transferVO1.getConfirmStatus() == null || transferVO1.getConfirmStatus().equals(1))) {  // 经理分配给自己的情况
                            changeCopyOperator = false;
                        }
                        //制作文案
                        if (index == 1 && transferVO1.getReceiverNo().equals(transferReceiverVO.getReceiveNo()) && !transferVO1.isCopyOperator() && (transferVO1.getConfirmStatus() == null || transferVO1.getConfirmStatus().equals(1))) {
                            changeCopyMaker = false;
                        }
                    }
                }
                index++;
            }

            //修改原数据状态 不可用状态
            transferSend.setStudentNo(studentNo);
            transferSendInfoMapper.updateByStudentNoSelective(transferSend, changeCopyOperator, changeCopyMaker);

            //更新学生信息的文签顾问并且清空制作文案
            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setStudentNo(studentNo);
            if (changeCopyOperator) {
                studentInfo.setCopyOperatorNo(sysUser.getOaid());
                studentInfo.setCopyOperator(sysUser.getUsername());
            }
            // 制作文案改变了
            if (changeCopyMaker) {
                studentInfo.setCopyMakerNo("");
                studentInfo.setCopyMaker("");
            }
            studentService.updateByStudentNo(studentInfo);
        } else if (Contants.TRANSFER_TYPE_OUT.equals(type)) {
            // 根据applyId修改
            transferSend.setApplyId(applyId);
            int updateResult = transferSendInfoMapper.updateByApplyIdSelective(transferSend);
            if (updateResult < 1) {
                return false;
            }
            //更新申请表的外联顾问
            ApplyInfo applyInfo = new ApplyInfo();
            applyInfo.setId(applyId);
            applyInfo.setConnector(sysUser.getOaid());
            applyInfo.setConnectorName(sysUser.getUsername());
            applyInfoMapper.updateByPrimaryKeySelective(applyInfo);
        }

        StudentInfo studentInfo = null;
        if (StringUtils.hasText(studentNo)) {
            studentInfo = studentService.getStudentInfoByStudentNo(studentNo);
        } else {
            ApplyInfo applyInfo = applyCollegeService.getById(applyId);
            if (applyInfo != null) {
                studentInfo = studentService.getStudentInfoByStudentNo(applyInfo.getStudentNo());
            }
        }

        int index = 0;
        for (TransferReceiverVO transferReceiver : transferReceiverVOList) {
            String receiveNo = transferReceiver.getReceiveNo();
            String receiveName = transferReceiver.getReceiveName();

            if (StringUtils.hasText(receiveNo) && ((index == 0 && changeCopyOperator) || (index == 1 && changeCopyMaker))) {
                //添加新的转案记录
                TransferSendInfo transferSendInfo = getTransferSendById(id);
                transferSendInfo.setId(null);
                transferSendInfo.setOperatorNo(sysUser.getOaid());
                transferSendInfo.setOperatorName(sysUser.getUsername());
                transferSendInfo.setReceiverNo(receiveNo);
                transferSendInfo.setReceiverName(receiveName);
                transferSendInfo.setCreateTime(new Date());
                transferSendInfo.setEnableStatus(true);
                transferSendInfo.setReason(saveTransferVO.getReason());
                // 集合第一个人为文签顾问
                if (index < 1) {
                    transferSendInfo.setIsCopyOperator(true);
                } else {
                    transferSendInfo.setIsCopyOperator(false);
                    transferSendInfo.setCopyIsChange(changeCopyOperator);
                }
                transferSendInfoMapper.insert(transferSendInfo);

                //发送消息
                SendMessageReq req = new SendMessageReq();
                req.setOaid(receiveNo);
                req.setOperatorNo(transferSendInfo.getOperatorNo());
                req.setStudentNo(studentNo);
                req.setTaskType(Contants.WORK_MESSAGE);
                req.setTemplateCode("transferConfirm");
                if (studentInfo != null) {
                    req.getTemplateParam().put(TaskTemplateKeyEnum.STUDENT_NO.getCode(), studentInfo.getStudentNo());
                    req.getTemplateParam().put(TaskTemplateKeyEnum.STUDENT_NAME.getCode(), studentInfo.getStudentName());
                }
                userTaskRelationService.sendMessage(req);
            }
            index++;
        }
        return true;
    }

    /**
     * 转案
     * @param transferReq
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransferRes doTransfer(TransferReq transferReq) throws ParseException {
        TransferRes transferRes = new TransferRes();

        Integer countryBussId = transferReq.getCountry();

        CountryInfo countryInfo = new CountryInfo();
        if(DoubleSignTypeEnum.AUS_NEW.getType().equals(transferReq.getDoubleSignType())){
            countryInfo.setCountryBussid(countryBussId);
            countryInfo.setCountryName(DoubleSignTypeEnum.getNationNameByType(transferReq.getDoubleSignType()));
        }else{
            countryInfo.setCountryBussid(countryBussId);
        }
        //查询国家信息
        List<CountryInfo> countryInfos = countryInfoMapper.select(countryInfo);
        if (countryInfos.isEmpty()) {
            transferRes.setResult(false);
            transferRes.setErrorMsg(MessageFormat.format("Country not exist!!! country:{0}", countryBussId));
            return transferRes;
        }

        //生成服务
        List<ServiceInfo> serviceInfos = serviceInfoMapper.select(new ServiceInfo());
        List<Integer> serviceIds = ServiceUtils.getServiceIdByContractType(serviceInfos, transferReq.getContractType());
        if (serviceIds.isEmpty()) {
            transferRes.setResult(false);
            transferRes.setErrorMsg(MessageFormat.format("ContractType error!!! contractType:{0}", transferReq.getContractType()));
            return transferRes;
        }

        // 区分美高美普
        if (Integer.valueOf(Contants.AMERICA_BUSS_ID).equals(countryBussId)) {
            if (transferReq.getUsaAStatus()) { //美高
                countryBussId = Integer.valueOf(Contants.AMERICA_TOP_BUSS_ID);
            } else { //美普
                countryBussId = Integer.valueOf(Contants.AMERICA_GENERAL_BUSS_ID);
            }
        }

        boolean isChannel = Integer.valueOf(1).equals(transferReq.getChannelStatus());

        //查询转案分配人
        List<SysUser> users = new ArrayList<>();
        if(isChannel){
            users = transferService.getTransferUserByCountryGroup(CountryGroup.GROUP_CHANNEL.getCode());
        }
        /* =====================标记标记================================ */
        else if(countryBussId.equals(Integer.valueOf(Contants.AMERICA_TOP_BUSS_ID))){
            SysRole sysRole = new SysRole();
            sysRole.setRoleName("规划经理");
            List<SysRole> listRole = roleService.queryRolesByRole(sysRole);
            if(listRole.size() >0){
                sysRole = listRole.get(0);
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setRoleId(sysRole.getId());
                sysUserRole = roleService.getSysUserRole(sysUserRole);
                SysUser sysUser = userService.getUserByName(sysUserRole.getOaId());
                users.add(sysUser);
            }
        }
        /* =====================标记标记================================ */
        else{
            users = transferService.getTransferUser(countryBussId);
        }

        if (users.isEmpty()) {
            transferRes.setResult(false);
            transferRes.setErrorMsg(
                    MessageFormat.format("No Receiver!! country:{0}", countryBussId));
            return transferRes;
        }

        //转案
        Date now = new Date();
        String studentNo = transferReq.getStudentNo();
        String studentName = transferReq.getStudentName();
        String operatorNo = transferReq.getOperatorNo();
        String operatorName = transferReq.getOperatorName();

        // 保存学生信息
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setDeleteStatus(false);
        studentInfo.setStudentNo(studentNo);
        // 学号是否存在查询
        List<StudentInfo> studentInfos = studentInfoMapper.select(studentInfo);
        if (!studentInfos.isEmpty()) {
            transferRes.setResult(false);
            transferRes.setErrorMsg(
                    MessageFormat.format("studentNo exist!! studentNo:{0}", studentNo));
            return transferRes;
        }

        BranchInfo branchInfo = branchService.selectByBranchId(transferReq.getBrandId());

        studentInfo.setNationStatus(DoubleSignTypeEnum.getNationStatusByCode(transferReq.getDoubleSignType()));
        studentInfo.setStudentName(studentName);
        studentInfo.setSalesConsultant(transferReq.getConsultant());
        studentInfo.setSalesConsultantNo(transferReq.getConsultantNo());
        studentInfo.setContractNo(transferReq.getContractNo());
        studentInfo.setConfeeid(transferReq.getConfeeid());
        studentInfo.setContractType(transferReq.getContractType());
        if(branchInfo != null){
            studentInfo.setBranchId(branchInfo.getSeriano());
            studentInfo.setBranchName(branchInfo.getBranchName());
        }
        studentInfo.setNationId(countryInfos.get(0).getId());
        if(StringUtils.hasText(transferReq.getCountryName())) {
            studentInfo.setNationName(transferReq.getCountryName());
        }else{
            studentInfo.setNationName(countryInfos.get(0).getCountryName());
        }
        // 区分美高美普
        if (Integer.valueOf(Contants.AMERICA_TOP_BUSS_ID).equals(countryBussId)) {
            //美高
            studentInfo.setUsaStatus(1);
        } else {
            //美普
            studentInfo.setUsaStatus(0);
        }
        studentInfo.setCreateTime(now);
        studentInfo.setStudentStatus(1);
        studentInfo.setOperatorNo(operatorNo);
        studentInfo.setBirthday(DateUtils.parseDate(transferReq.getBirthday(), Contants.datePattern));
        studentInfo.setStatus(ServiceUtils.getInitStatusByContractNo(transferReq.getContractType()));
        studentInfo.setFirstBonusStatus(1);
        studentInfo.setFinallyBonusStatus(1);
        studentInfo.setChannelStatus(transferReq.getChannelStatus());
        studentInfo.setChannelTransferStatus(0);
//        studentInfo.setStudentMaterial(null);
        studentInfo.setPinyin(transferReq.getSpelling());
        studentInfo.setSignDate(DateUtils.parseDate(transferReq.getSignDate(), Contants.datePattern));
        studentInfo.setAgentId(transferReq.getAgentId());
        studentInfo.setAgentName(transferReq.getAgentName());
        studentInfo.setRegisterStatus(false);
        studentInfoMapper.insert(studentInfo);

        // 生成服务
        serviceIds.forEach(serviceId -> {
            StudentServiceInfo studentServiceInfo = new StudentServiceInfo();
            studentServiceInfo.setDeleteStatus(false);
            studentServiceInfo.setStudentNo(studentNo);
            studentServiceInfo.setServiceId(serviceId);
            studentServiceInfo.setCreateTime(now);
            studentServiceInfo.setOperatorNo(operatorNo);
            studentServiceInfo.setCompleteStatus(false);
            studentServiceInfoMapper.insert(studentServiceInfo);
        });

        TransferInfo transferInfo = new TransferInfo();
        transferInfo.setStudentNo(studentNo);
        transferInfo.setStudentName(studentName);
        if(isChannel){
            transferInfo.setCountryGroup(CountryGroup.GROUP_CHANNEL.getCode());
        }else {
            // 区分美高美普
            if (Integer.valueOf(Contants.AMERICA_TOP_BUSS_ID).equals(countryBussId)) {
                //美高
                // transferInfo.setCountryGroup(CountryGroup.GROUP_AMERICA_H.getCode());
                transferInfo.setCountryGroup(CountryGroup.GROUP_PLAN.getCode());
            } else if (Integer.valueOf(Contants.AMERICA_GENERAL_BUSS_ID).equals(countryBussId)) {
                //美普
                transferInfo.setCountryGroup(CountryGroup.GROUP_AMERICA_C.getCode());
            } else {
                transferInfo.setCountryGroup(countryInfos.get(0).getCountryGroup());
            }
        }
        transferInfo.setComment(transferReq.getComment());
        transferInfo.setCreateTime(now);
        transferInfo.setDeleteStatus(false);
        transferInfo.setTransferType(TransferRelatedEnum.TRAN_TYPE_VISA.getCode());
        transferInfoMapper.insert(transferInfo);

        TransferSend transferSend = new TransferSend();
        transferSend.setTransferId(transferInfo.getId());
        transferSend.setEnableStatus(true);
        transferSend.setDeleteStatus(false);
        transferSend.setCreateTime(now);

        if (Integer.valueOf(Contants.AMERICA_TOP_BUSS_ID).equals(countryBussId)) {
            transferSend.setOperatorType(TransferRelatedEnum.TRAN_OPERATOR_TYPE_5.getCode());
        }
        else{
            transferSend.setOperatorType(TransferRelatedEnum.TRAN_OPERATOR_TYPE_1.getCode());
        }

        transferSend.setTransferType(TransferRelatedEnum.TRAN_SEND_TYPE_1.getCode());
        transferSend.setOperatorNo(transferReq.getConsultantNo());
        transferSend.setOperatorName(transferReq.getConsultant());
        transferSendMapper.insert(transferSend);

        // 保存转案信息
        users.forEach(user -> {

            //发送消息
            SendMessageReq req = new SendMessageReq();
            req.setOaid(user.getOaid());
            req.setOperatorNo(transferSend.getOperatorNo());
            req.setStudentNo(studentNo);
            req.setTaskType(Contants.WORK_MESSAGE);
            req.setTemplateCode("transferConfirm");
            req.getTemplateParam().put(TaskTemplateKeyEnum.STUDENT_NO.getCode(), studentNo);
            req.getTemplateParam().put(TaskTemplateKeyEnum.STUDENT_NAME.getCode(), studentInfo.getStudentName());
            userTaskRelationService.sendMessage(req);
        });

        transferRes.setResult(true);

        try{
            String url = studentUpdateUrl + transferReq.getResourceId() + "/" + transferReq.getStudentNo() + "/";
            logger.info("调取更新学生接口url: " + url);
            String s = HttpUtils.doGet(url);
            logger.info("调取更新学生接口返回结果: " + s);
            this.updateStudentRegisterStatus(transferReq.getStudentNo(),s);
        }catch (Exception e){
            logger.error("调取更新学生接口报错: " + e.getMessage());
        }

        return transferRes;
    }

    /**
     * 如果调用更新学生接口返回数据正确，那么更新一下学生已注册小希
     * @param studentNo
     * @param result
     */
    private void updateStudentRegisterStatus(String studentNo,String result) {
        JSONObject jsonObject = JSONObject.parseObject(result);
        if (jsonObject != null && !jsonObject.isEmpty()) {
            Integer code = jsonObject.getInteger("code");
            if (code == 0) {
                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setStudentNo(studentNo);
                studentInfo.setDeleteStatus(false);
                studentInfo = studentService.get(studentInfo);
                studentInfo.setRegisterStatus(true);
                Integer i = studentService.update(studentInfo);
                if(i <= 0){
                    logger.error("更新学生注册小希失败");
                }
            }
        }
    }

    @Override
    public boolean transferResult(String studentNo) {
        List<TransferVO> transferVOS = transferSendInfoMapper.getTransferResult(studentNo);
        boolean result = true;
        for (TransferVO transferVO : transferVOS) {
            if (transferVO.getConfirmStatus() == null || !transferVO.getConfirmStatus().equals(1)) {
                result = false;
            }
        }
        return result;
    }

    /**
     * 新分页查询
     *
     * @param transferVO
     * @return
     */
    @Override
    public List<TransferVO> transferlistByPage(TransferVO transferVO) {

        List<TransferVO> transferVOS = this.transferInfoRelatedQuery(transferVO);

        //获取当前操作者
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();

        //查询其他组长
        List<SysUser> otherLeaders = userService.getOtherLeaders(user.getOaid());

        transferVOS.forEach(transferVo -> {
            otherLeaders.forEach(sysUser -> {
                if (sysUser.getOaid().equals(transferVo.getReceiverNo())) {
                    transferVo.setChangeGroup(true);
                }
            });
        });
        return transferVOS;
    }

    @Override
    public Boolean updateAllot(AllotVO allotVO) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            SysUser loginSysUser = userService.getLoginUser();
            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setStudentNo(allotVO.getStudentNo());
            studentInfo.setDeleteStatus(false);
            SysUser sysUser = new SysUser();
            sysUser.setEnableStatus(1);
            sysUser.setDeleteStatus(false);
            sysUser.setOaid(allotVO.getAllotOperator());
            List<StudentInfo> studentInfos = studentInfoMapper.select(studentInfo);
            List<SysUser> sysUsers = userService.getList(sysUser);
            if(studentInfos != null && studentInfos.size() > 0 && sysUsers != null && sysUsers.size() > 0){
                studentInfo = studentInfos.get(0);
                sysUser = sysUsers.get(0);
                OldTransferRecord oldTransferRecord = new OldTransferRecord();
                oldTransferRecord.setOperatorType(String.valueOf(allotVO.getAllotType()));
                oldTransferRecord.setReceiveName(sysUser.getUsername());
                oldTransferRecord.setReceiveNo(sysUser.getOaid());
                oldTransferRecord.setSendName( loginSysUser.getUsername());
                oldTransferRecord.setSendNo(loginSysUser.getOaid());
                oldTransferRecord.setReceiveTime(simpleDateFormat.format(new Date()));
                oldTransferRecord.setSendTime(simpleDateFormat.format(new Date()));
                oldTransferRecord.setStudentName(studentInfo.getStudentName());
                oldTransferRecord.setStudentNo(studentInfo.getStudentNo());
                Integer saveOldTransferResult = oldTransferRecordMapper.insert(oldTransferRecord);

                //离职
                if("4".equals(allotVO.getReason())){
                    transferService.resigendTransfer(allotVO.getStudentNo(), null, allotVO.getAllotType());
                }

                if(saveOldTransferResult > 0){
                    if("1".equals(oldTransferRecord.getOperatorType())){
                        studentInfo.setCopy(sysUser.getUsername());
                        studentInfo.setCopyNo(sysUser.getOaid());
                    }else if("2".equals(oldTransferRecord.getOperatorType())){
                        studentInfo.setCopierNo(sysUser.getOaid());
                        studentInfo.setCopier(sysUser.getUsername());
                    }else if("3".equals(oldTransferRecord.getOperatorType())){
                        studentInfo.setVisaOperatorNo(sysUser.getOaid());
                        studentInfo.setVisaOperator(sysUser.getUsername());
                    }
                    Integer UpdateStudentResult = studentInfoMapper.updateByPrimaryKey(studentInfo);
                    if(UpdateStudentResult > 0 ){
                        return true;
                    }
                }
            }
        }catch(Exception e){
            logger.info("toAllot to Error!:"+e.getMessage());
            e.printStackTrace();
        }

        return false;
    }


}
