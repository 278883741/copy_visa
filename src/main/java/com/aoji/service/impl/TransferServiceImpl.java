package com.aoji.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.*;
import com.aoji.mapper.*;
import com.aoji.model.*;
import com.aoji.model.req.SendMessageReq;
import com.aoji.model.res.SignBaseResponse;
import com.aoji.service.*;
import com.aoji.utils.ConvertUtils;
import com.aoji.utils.HttpUtils;
import com.aoji.vo.TransferListVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

@Service
public class TransferServiceImpl implements TransferService {

    @Value("${resigend.transfer.url}")
    private String resigendTransferUrl;
    @Autowired
    private UserService userService;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ApplyInfoMapper applyInfoMapper;
    @Autowired
    private UserGroupMapper userGroupMapper;
    @Autowired
    private TransferInfoMapper transferInfoMapper;
    @Autowired
    private TransferSendMapper transferSendMapper;
    @Autowired
    private TransferReceiveMapper transferReceiveMapper;
    @Autowired
    private UserTaskRelationService userTaskRelationService;
    @Autowired
    private TransferAssignRecordService transferAssignRecordService;
    @Autowired
    private SendStudentMessageService sendStudentMessageService;
    @Autowired
    private OldTransferRecordMapper oldTransferRecordMapper;

    @Value("${sendMessageAPP.enable}")
    private Boolean sendMessageAPPEnable;
    @Value("${sendMessageAPP.url}")
    private String sendMessageAPPUrl;
    @Value("${sendMessageUpdateGroupMembers.url}")
    private String sendMessageUpdateGroupMembersUrl;
    @Value("${update.im.group.url}")
    private String updateGroupUrl;

    public static final Logger logger = LoggerFactory.getLogger(TransferServiceImpl.class);

    @Override
    public List<TransferListVO> transferListForVisa(TransferListVO transferListVO) {
//        transferListVO.setTransferType(TransferRelatedEnum.TRAN_TYPE_VISA.getCode());
        return transferInfoMapper.transferList(transferListVO);
    }

    @Override
    public List<TransferListVO> transferListForOutreach(TransferListVO transferListVO) {
//        transferListVO.setTransferType(TransferRelatedEnum.TRAN_TYPE_OUTREACH.getCode());
        transferListVO.setUserGroup(null);
        transferListVO.setCountryGroup(null);
        return transferInfoMapper.transferList(transferListVO);
    }

    @Override
    public TransferInfo getTransferInfoByStudentNo(String studentNo, Integer transferType) {
        List<TransferInfo> transferInfos = transferInfoMapper.getByStudentNoAndType(studentNo, transferType);
        if(transferInfos != null && transferInfos.size() > 0){
            return transferInfos.get(0);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse saveTransferForVisa(TransferListVO transferListVO) {

        BaseResponse response = new BaseResponse();

        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if(sysUser == null){
            response.setResult(false);
            response.setErrorMsg("会话超时，请重新登录！");
            return response;
        }

        // 查询原转案信息
        TransferSend transferSend = new TransferSend();
        transferSend.setTransferId(transferListVO.getId());
        transferSend.setOperatorType(transferListVO.getOperatorType());
        transferSend.setEnableStatus(true);
        List<TransferSend> transferSends = transferSendMapper.select(transferSend);
        if(transferSends != null && transferSends.size() > 0){
            transferSend = transferSends.get(0);
            // 校验新顾问与原顾问
            if(transferSend.getReceiveNo() != null && transferSend.getReceiveNo().equals(transferListVO.getReceiveNo())
                    && transferSend.getOperatorType().equals(transferListVO.getOperatorType())
                    && transferSend.getTransferType().equals(transferListVO.getTransferSendType())
                    && !TransferRelatedEnum.TRAN_CONFIRM_STATUS_2.getCode().equals(transferSend.getConfirmStatus())){
                response.setResult(false);
                response.setErrorMsg("新顾问与原顾问相同");
                return response;
            }

            // 如果原转案是签约即转案，根据分配指定转案所属组
//            if(TransferRelatedEnum.TRAN_SEND_TYPE_1.getCode().equals(transferSend.getTransferType())){
            List<UserGroup> userGroups = userGroupMapper.getByoaidAndLeaderStatus(sysUser.getOaid(), null);
            if(!userGroups.isEmpty()) {
                TransferInfo transferInfo = new TransferInfo();
                transferInfo.setUserGroup(userGroups.get(0).getId());
                transferInfo.setId(transferSend.getTransferId());
                transferInfoMapper.updateByPrimaryKeySelective(transferInfo);
            }
//            }
        }

        try {
            // 修改原转案的可用状态
            if(transferSend.getId() != null) {
                if(TransferRelatedEnum.TRAN_SEND_TYPE_2.getCode().equals(transferListVO.getTransferSendType())){ // 分配
                    TransferSend transferSend1 = new TransferSend();
                    transferSend1.setId(transferSend.getId());
                    transferSend1.setEnableStatus(false);
                    transferSendMapper.updateByPrimaryKeySelective(transferSend1);
                }else if(TransferRelatedEnum.TRAN_SEND_TYPE_3.getCode().equals(transferListVO.getTransferSendType())){ // 转组
                    // 禁用原转案记录
                    transferSendMapper.updateByTransferId(transferSend.getTransferId(), 0);
                    // 删除所属组
                    TransferInfo transferInfo = transferInfoMapper.selectByPrimaryKey(transferSend.getTransferId());
                    transferInfo.setUserGroup(null);
                    transferInfoMapper.updateByPrimaryKey(transferInfo);
                }else if(TransferRelatedEnum.TRAN_SEND_TYPE_4.getCode().equals(transferListVO.getTransferSendType())){
                    // 禁用原转案记录
                    transferSendMapper.updateByTransferId(transferSend.getTransferId(), 0);
                }
            }

            // 添加新的转案记录
            TransferSend transferSend2 = new TransferSend();
            transferSend2.setTransferId(transferSend.getTransferId());
            transferSend2.setOperatorNo(sysUser.getOaid());
            transferSend2.setOperatorName(sysUser.getUsername());
            transferSend2.setCreateTime(new Date());
            transferSend2.setReceiveNo(transferListVO.getReceiveNo());
            transferSend2.setReceiveName(transferListVO.getReceiveName());
            transferSend2.setTransferType(transferListVO.getTransferSendType());

            transferSend2.setOperatorType(transferListVO.getOperatorType());

            transferSend2.setTransferReason(transferListVO.getTransferReason());
            transferSend2.setEnableStatus(true);
            transferSend2.setDeleteStatus(false);
            transferSendMapper.insert(transferSend2);

            TransferInfo transferInfo = transferInfoMapper.selectByPrimaryKey(transferSend.getTransferId());

            // 同步学生表信息
            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setStudentNo(transferInfo.getStudentNo());
            if(TransferRelatedEnum.TRAN_OPERATOR_TYPE_1.getCode().equals(transferSend2.getOperatorType())){
                studentInfo.setCopyOperatorNo(sysUser.getOaid());
                studentInfo.setCopyOperator(sysUser.getUsername());
            }else if(TransferRelatedEnum.TRAN_OPERATOR_TYPE_2.getCode().equals(transferSend2.getOperatorType())){
                studentInfo.setCopyMakerNo(sysUser.getOaid());
                studentInfo.setCopyMaker(sysUser.getUsername());
            }else if(TransferRelatedEnum.TRAN_OPERATOR_TYPE_3.getCode().equals(transferSend2.getOperatorType())){
                studentInfo.setCopierNo(sysUser.getOaid());
                studentInfo.setCopyMaker(sysUser.getUsername());
            }
            /* ===========我先记录一下我转案改的地方，先不要删我的哈，标记标记============ */
            else if(TransferRelatedEnum.TRAN_OPERATOR_TYPE_5.getCode().equals(transferSend2.getOperatorType())){
                studentInfo.setPlanningConsultantNo(sysUser.getOaid());
                studentInfo.setPlanningConsultant(sysUser.getUsername());
            }
            /* ============我先记录一下我转案改的地方，先不要删我的哈，标记标记===================== */
            studentService.updateByStudentNo(studentInfo);

            //发送消息
            SendMessageReq req = new SendMessageReq();
            req.setOaid(transferListVO.getReceiveNo());
            req.setOperatorNo(sysUser.getOaid());
            req.setStudentNo(transferInfo.getStudentNo());
            req.setTaskType(Contants.WORK_MESSAGE);
            req.setTemplateCode("transferConfirm");
            req.getTemplateParam().put(TaskTemplateKeyEnum.STUDENT_NO.getCode(), transferInfo.getStudentNo());
            req.getTemplateParam().put(TaskTemplateKeyEnum.STUDENT_NAME.getCode(), transferInfo.getStudentName());
            userTaskRelationService.sendMessage(req);
        } catch (Exception e){
            logger.error(e.getMessage());
            throw new ContentException(1, "操作失败");
        }
        response.setResult(true);
        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse saveTransferForOutreach(TransferListVO transferListVO) {
        BaseResponse response = new BaseResponse();

        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        if(sysUser == null){
            response.setResult(false);
            response.setErrorMsg("会话超时，请重新登录！");
            return response;
        }

        // 查询原转案信息
        TransferSend transferSend = new TransferSend();
        transferSend.setId(transferListVO.getSendId());
        transferSend.setEnableStatus(true);
        List<TransferSend> transferSends = transferSendMapper.select(transferSend);
        if(transferSends != null && transferSends.size() > 0){
            transferSend = transferSends.get(0);
            // 校验新顾问与原顾问
            if(transferSend.getReceiveNo() != null && transferSend.getReceiveNo().equals(transferListVO.getReceiveNo())){
                response.setResult(false);
                response.setErrorMsg("新顾问与原顾问相同");
                return response;
            }

        }

        try {
            // 修改原转案的可用状态
            if(transferSend.getId() != null) {
                if(TransferRelatedEnum.TRAN_SEND_TYPE_2.getCode().equals(transferListVO.getTransferSendType())){ // 分配
                    TransferSend transferSend1 = new TransferSend();
                    transferSend1.setId(transferSend.getId());
                    transferSend1.setEnableStatus(false);
                    transferSendMapper.updateByPrimaryKeySelective(transferSend1);
                }
            }

            // 添加新的转案记录
            TransferSend transferSend2 = new TransferSend();
            transferSend2.setTransferId(transferSend.getTransferId());
            transferSend2.setOperatorNo(sysUser.getOaid());
            transferSend2.setOperatorName(sysUser.getUsername());
            transferSend2.setCreateTime(new Date());
            transferSend2.setReceiveNo(transferListVO.getReceiveNo());
            transferSend2.setReceiveName(transferListVO.getReceiveName());
            transferSend2.setTransferType(transferListVO.getTransferSendType());
            transferSend2.setOperatorType(transferListVO.getOperatorType());
            transferSend2.setTransferReason(transferListVO.getTransferReason());
            transferSend2.setEnableStatus(true);
            transferSend2.setDeleteStatus(false);
            transferSendMapper.insert(transferSend2);

            TransferInfo transferInfo = transferInfoMapper.selectByPrimaryKey(transferSend.getTransferId());

            //将外联顾问更新到申请表
            ApplyInfo applyInfo = new ApplyInfo();
            applyInfo.setId(transferInfo.getApplyId());
            applyInfo.setConnector(sysUser.getOaid());
            applyInfo.setConnectorName(sysUser.getUsername());
            applyInfoMapper.updateByPrimaryKeySelective(applyInfo);

            //发送消息
            SendMessageReq req = new SendMessageReq();
            req.setOaid(transferListVO.getReceiveNo());
            req.setOperatorNo(sysUser.getOaid());
            req.setStudentNo(transferInfo.getStudentNo());
            req.setTaskType(Contants.WORK_MESSAGE);
            req.setTemplateCode("transferConfirm");
            req.getTemplateParam().put(TaskTemplateKeyEnum.STUDENT_NO.getCode(), transferInfo.getStudentNo());
            req.getTemplateParam().put(TaskTemplateKeyEnum.STUDENT_NAME.getCode(), transferInfo.getStudentName());
            userTaskRelationService.sendMessage(req);
        } catch (Exception e){
            logger.error(e.getMessage());
            throw new ContentException(1, "操作失败");
        }
        response.setResult(true);
        return response;
    }

    @Override
    public BaseResponse updateConfirmStatus(Integer sendId, Integer confirmStatus, String comment, SysUser sysUser) {

        BaseResponse response = new BaseResponse();

        if(sysUser == null){
            response.setResult(false);
            response.setErrorMsg("会话超时，请重新登录！");
            return response;
        }

        TransferSend transferSend1 = new TransferSend();
        transferSend1.setId(sendId);
        List<TransferSend> transferSends = transferSendMapper.select(transferSend1);
        TransferSend transferSend2 = transferSends.get(0);
        if(transferSend2.getConfirmStatus() != null){
            response.setResult(false);
            response.setErrorMsg("状态已经改变！");
            return response;
        }

        // 修改send表的confirm_status
        TransferSend transferSend = new TransferSend();
        transferSend.setId(sendId);
        transferSend.setConfirmStatus(confirmStatus);
        int result = transferSendMapper.updateByPrimaryKeySelective(transferSend);

        // 添加transfer_receive信息
        TransferReceive transferReceive = new TransferReceive();
        transferReceive.setSendId(sendId);
        transferReceive.setOperatorNo(sysUser.getOaid());
        transferReceive.setOperatorName(sysUser.getUsername());
        transferReceive.setDeleteStatus(false);
        transferReceive.setConfirmStatus(confirmStatus);
        transferReceive.setCreateTime(new Date());
        transferReceive.setReason(comment);
        transferReceiveMapper.insert(transferReceive);
        boolean updateGroupFlag = false;
        if(TransferRelatedEnum.TRAN_CONFIRM_STATUS_1.getCode().equals(confirmStatus)){
            TransferInfo transferInfo = new TransferInfo();
            transferInfo.setId(transferSends.get(0).getTransferId());
            List<TransferInfo> transferInfos = transferInfoMapper.select(transferInfo);
            TransferInfo transferInfo1 = transferInfos.get(0);
            if(TransferRelatedEnum.TRAN_TYPE_VISA.getCode().equals(transferInfo1.getTransferType())){
                // 同步学生表信息
                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setStudentNo(transferInfo1.getStudentNo());
                if(TransferRelatedEnum.TRAN_OPERATOR_TYPE_1.getCode().equals(transferSend2.getOperatorType())){
                    studentInfo.setCopyOperatorNo(sysUser.getOaid());
                    studentInfo.setCopyOperator(sysUser.getUsername());
                    updateGroupFlag = true;
                }else if(TransferRelatedEnum.TRAN_OPERATOR_TYPE_2.getCode().equals(transferSend2.getOperatorType())){
                    studentInfo.setCopyMakerNo(sysUser.getOaid());
                    studentInfo.setCopyMaker(sysUser.getUsername());
                }else if(TransferRelatedEnum.TRAN_OPERATOR_TYPE_3.getCode().equals(transferSend2.getOperatorType())){
                    studentInfo.setCopierNo(sysUser.getOaid());
                    studentInfo.setCopyMaker(sysUser.getUsername());
                }
                /* ===========我先记录一下我转案改的地方，先不要删我的哈，标记标记============ */
                else if(TransferRelatedEnum.TRAN_OPERATOR_TYPE_5.getCode().equals(transferSend2.getOperatorType())){
                    studentInfo.setPlanningConsultantNo(sysUser.getOaid());
                    studentInfo.setPlanningConsultant(sysUser.getUsername());
                }
                /* ===========我先记录一下我转案改的地方，先不要删我的哈，标记标记============ */
                //如果是转国家，更新学生表国家，暂时只是美国，只修改usaStatus
                if(TransferRelatedEnum.TRAN_SEND_TYPE_4.getCode().equals(transferSend2.getTransferType())){
                    List<UserGroup> userGroups = userGroupMapper.getByoaidAndLeaderStatus(sysUser.getOaid(), null);
                    if(userGroups != null && userGroups.size() > 0){
                        UserGroup userGroup = userGroups.get(0);
                        if(CountryGroup.GROUP_AMERICA_H.getCode() == userGroup.getNation()){
                            studentInfo.setUsaStatus(1);
                        }else{
                            studentInfo.setUsaStatus(0);
                        }
                    }
                    studentInfo.setChannelTransferStatus(1);
                }
                studentService.updateByStudentNo(studentInfo);
            }else if(TransferRelatedEnum.TRAN_TYPE_OUTREACH.getCode().equals(transferInfo1.getTransferType())){
                // 如果是外联 同步apply_info
                //将外联顾问更新到申请表
                ApplyInfo applyInfo = new ApplyInfo();
                applyInfo.setId(transferInfo1.getApplyId());
                applyInfo.setConnector(sysUser.getOaid());
                applyInfo.setConnectorName(sysUser.getUsername());
                applyInfoMapper.updateByPrimaryKeySelective(applyInfo);
            }

            // 如果是转组 修改用户组
            // 如果是转国家 修改国家组， 目前不修改国家，仅支持美高美普互转
            if(TransferRelatedEnum.TRAN_SEND_TYPE_3.getCode().equals(transferSend2.getTransferType())
                    || TransferRelatedEnum.TRAN_SEND_TYPE_4.getCode().equals(transferSend2.getTransferType())
                    || TransferRelatedEnum.TRAN_SEND_TYPE_1.getCode().equals(transferSend2.getTransferType())){
                List<UserGroup> userGroups = userGroupMapper.getByoaidAndLeaderStatus(sysUser.getOaid(), null);
                if(userGroups != null && userGroups.size() > 0){
                    UserGroup userGroup = userGroups.get(0);
                    TransferInfo transferInfo2 = new TransferInfo();
                    transferInfo2.setId(transferSend2.getTransferId());
                    transferInfo2.setUserGroup(userGroup.getId());
                    transferInfo2.setCountryGroup(userGroup.getNation());
                    transferInfoMapper.updateByPrimaryKeySelective(transferInfo2);
                }

            }

            StudentInfo studentInfo = studentService.getStudentInfoByStudentNo(transferInfo1.getStudentNo());

            if (TransferRelatedEnum.TRAN_TYPE_VISA.getCode().equals(transferInfo1.getTransferType())) {
                //查询转案分配记录
                int serviceCode = 0;
                List<TransferAssignRecord> transferAssignRecords = transferAssignRecordService.getbyStudentNo(transferInfo1.getStudentNo(), Contants.TRANSFER_TYPE_VISA);
                if (transferAssignRecords.size() > 0) {
                    serviceCode = NoteMessageStatus.NOTE_NO_VISA_RESULT.getCode();
                } else {
                    serviceCode = NoteMessageStatus.NOTE_NO_COPY.getCode();
                }

                // 如果是分配文签顾问的话，添加分配记录，发送短信
                if (TransferRelatedEnum.TRAN_SEND_TYPE_2.getCode().equals(transferSend2.getTransferType())
                        && TransferRelatedEnum.TRAN_OPERATOR_TYPE_1.getCode().equals(transferSend2.getOperatorType())
                        && StudentStatus.COMPLETED.getCode() != studentInfo.getStatus()
                        && StudentStatus.REFUND.getCode() != studentInfo.getStatus()) {

                    //发送短信
                    try {
                        sendStudentMessageService.sendStudentMessage(sysUser, serviceCode, transferInfo1.getStudentNo(), false, null);
                    } catch (Exception e) {
                        logger.error("sendStudentMessage failed! " + e.getMessage());
                    }

                    transferAssignRecordService.insert(transferInfo1.getStudentNo(), Contants.TRANSFER_TYPE_VISA, sysUser.getOaid());

                    // 调用学生留学管家发送接口
                    if(sendMessageAPPEnable) {
                        sendMessageForXiaoXi(transferInfo1.getStudentNo(), transferInfo1.getStudentName());
                    }

                }

            }
            // 更新IM群组
            if(updateGroupFlag){
                try {
                    HttpUtils.doGet(MessageFormat.format(updateGroupUrl, transferInfo1.getStudentNo()), String.class);
                }catch (Exception e){
                    logger.error("更新IM群组成员失败！ error:"+e.getMessage());
                }
            }

            if(studentInfo != null && !Integer.valueOf(1).equals(studentInfo.getChannelStatus())) { //非澳际同业
                //员工离职
                if (Integer.valueOf(4).equals(transferSend2.getTransferReason())) {
                    this.resigendTransfer(transferInfo1.getStudentNo(), transferInfo1.getTransferType(), transferSend2.getOperatorType());
                }
            }
        }

        response.setResult(true);
        return response;
    }

    @Override
    public List<SysUser> getTransferUser(Integer countryBussId) {
        return sysUserMapper.getTransferUser(countryBussId);
    }

    @Override
    public List<SysUser> getTransferUserByCountryGroup(Integer countryGroup) {
        return sysUserMapper.getTransferUserByCountryGroup(countryGroup);
    }

    @Override
    public void resigendTransfer(String studentNo, Integer transferType, Integer operatorType) {
        try {
            Integer states = null;
            JSONObject jsonObject = new JSONObject();
            //是否同步到签约系统标志
            boolean isOk = false;
            if (transferType != null) { // 高签、制作
                // 查询前一个顾问
                if(TransferRelatedEnum.TRAN_TYPE_OUTREACH.getCode().equals(transferType)){
                    operatorType = null;
                }
                List<TransferSend> transferSends = transferSendMapper.selectLastReceiver(studentNo, operatorType, transferType);
                if (transferSends != null && transferSends.size() > 1) {
                    TransferSend oldTransferSend = transferSends.get(1);
                    TransferSend newTransferSend = transferSends.get(0);
                    if (StringUtils.hasText(oldTransferSend.getReceiveNo())) {
                        //外联
                        if (TransferRelatedEnum.TRAN_TYPE_OUTREACH.getCode().equals(transferType)) {
                            states = ResigendTrasferStateEnum.OUTREACH.getCode();
                            //文签
                        } else if (TransferRelatedEnum.TRAN_TYPE_VISA.getCode().equals(transferType)) {
                            if (TransferRelatedEnum.TRAN_OPERATOR_TYPE_1.getCode().equals(operatorType)) {
                                states = ResigendTrasferStateEnum.COPY_OPERATOR.getCode();
                            } else if (TransferRelatedEnum.TRAN_OPERATOR_TYPE_2.getCode().equals(operatorType)) {
                                states = ResigendTrasferStateEnum.COPY_MAKER.getCode();
                            }
                        }
                        jsonObject.put("oldworknum", oldTransferSend.getReceiveNo());
                        jsonObject.put("newworknum", newTransferSend.getReceiveNo());
                        jsonObject.put("operater", newTransferSend.getOperatorNo());

                        isOk = true;
                    }
                }
            } else { // 文书、文案、业务员
                List<OldTransferRecord> oldTransferRecords = oldTransferRecordMapper.selectByStudentAndOperatorType(studentNo, operatorType);
                if (oldTransferRecords != null && oldTransferRecords.size() > 1) {
                    OldTransferRecord oldTransfer = oldTransferRecords.get(1);
                    OldTransferRecord newTransfer = oldTransferRecords.get(0);
                    jsonObject.put("oldworknum", oldTransfer.getReceiveNo());
                    jsonObject.put("newworknum", newTransfer.getReceiveNo());
                    jsonObject.put("operater", newTransfer.getSendNo());
                    if (operatorType.equals(1)) {
                        states = ResigendTrasferStateEnum.COPIER.getCode();
                    } else if (operatorType.equals(2)) {
                        states = ResigendTrasferStateEnum.COPY.getCode();
                    } else if (operatorType.equals(3)) {
                        states = ResigendTrasferStateEnum.VISA_OPERATOR.getCode();
                    }

                    isOk = true;
                }
            }
            jsonObject.put("sid", studentNo);
            jsonObject.put("states", states);
            if (isOk) {
                logger.info("resigendTransfer RQ:"+jsonObject.toString());
                String jsonStr = HttpUtils.sendPost(resigendTransferUrl, jsonObject.toString());
                logger.info("resigendTransfer RS:"+jsonStr);
                SignBaseResponse response = JSONObject.parseObject(jsonStr, SignBaseResponse.class);
                if (response == null || response.getState() != 0) {
                    logger.error(MessageFormat.format("resigendTransfer failed! RQ:{0}; RS:{1}", jsonObject.toJSONString(), jsonStr));
                }
            }
        }catch (Exception e){
            logger.error("resigendTransfer_"+e.getMessage());
        }
    }

    /**
     * 文签顾问修改后调用消息发送消息
     * @param studentNo
     */
    public void sendMessageForXiaoXi(String studentNo,String studentName) {
        try {
            //是否需要发送消息
            boolean isOk = false;
            // 查询前一个顾问
            List<TransferSend> transferSends = transferSendMapper.selectLastReceiver(studentNo,TransferRelatedEnum.TRAN_OPERATOR_TYPE_1.getCode(), TransferRelatedEnum.TRAN_TYPE_VISA.getCode());
            if (transferSends != null && transferSends.size() > 1) {
                TransferSend oldTransferSend = transferSends.get(1);
                TransferSend newTransferSend = transferSends.get(0);
                if (StringUtils.hasText(oldTransferSend.getReceiveNo())) {
                    isOk = true;
                }
            }

            if (isOk) {
                TransferSend oldTransferSend = transferSends.get(1);
                TransferSend newTransferSend = transferSends.get(0);
                String message = String.format("studentNo=%s&content=您的文签顾问已变更。",studentNo);
                HttpUtils.sendPost2(sendMessageAPPUrl, message);

//                String message1 = String.format("oldOaid=%s&oldNickName=%s&oaId=%s&nickName=%s&type=%s&ids=%s",oldTransferSend.getReceiveNo(),oldTransferSend.getReceiveName(),newTransferSend.getReceiveNo(),newTransferSend.getReceiveName(),3,studentNo);
//                HttpUtils.sendPost2(sendMessageUpdateGroupMembersUrl, message1);
            }
        }catch (Exception e){
            logger.error("resigendTransfer_"+e.getMessage());
        }
    }

    /**
     * 批量转案
     * @param transferListVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchSaveTransferForVisa(TransferListVO transferListVO) {
        logger.info("批量转案参数："+transferListVO.toString());
        SysUser sysUser = userService.getLoginUser();
        // 参数转换
        String transferIdStr = transferListVO.getTransferIdStr();
        List<Integer> transferIdList = ConvertUtils.strToList(transferIdStr, ",");
        // 禁用原转案信息
        transferSendMapper.disableByTransferIdAndOperatorType(transferIdList, transferListVO.getOperatorType());

        Date now = new Date();
        // 插入新的转案记录
        for(Integer transferId : transferIdList){
            // 添加新的转案记录
            TransferSend transferSend2 = new TransferSend();
            transferSend2.setTransferId(transferId);
            transferSend2.setOperatorNo(sysUser.getOaid());
            transferSend2.setOperatorName(sysUser.getUsername());
            transferSend2.setCreateTime(now);
            transferSend2.setReceiveNo(transferListVO.getReceiveNo());
            transferSend2.setReceiveName(transferListVO.getReceiveName());
            transferSend2.setTransferType(TransferRelatedEnum.TRAN_SEND_TYPE_2.getCode());
            transferSend2.setOperatorType(transferListVO.getOperatorType());
            transferSend2.setTransferReason(transferListVO.getTransferReason());
            transferSend2.setEnableStatus(true);
            transferSend2.setDeleteStatus(false);
            transferSendMapper.insert(transferSend2);

            TransferInfo transferInfo = transferInfoMapper.selectByPrimaryKey(transferId);

            // 同步学生表信息
            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setStudentNo(transferInfo.getStudentNo());
            if(TransferRelatedEnum.TRAN_OPERATOR_TYPE_1.getCode().equals(transferSend2.getOperatorType())){
                studentInfo.setCopyOperatorNo(sysUser.getOaid());
                studentInfo.setCopyOperator(sysUser.getUsername());
            }else if(TransferRelatedEnum.TRAN_OPERATOR_TYPE_2.getCode().equals(transferSend2.getOperatorType())){
                studentInfo.setCopyMakerNo(sysUser.getOaid());
                studentInfo.setCopyMaker(sysUser.getUsername());
            }else if(TransferRelatedEnum.TRAN_OPERATOR_TYPE_3.getCode().equals(transferSend2.getOperatorType())){
                studentInfo.setCopierNo(sysUser.getOaid());
                studentInfo.setCopyMaker(sysUser.getUsername());
            }else if(TransferRelatedEnum.TRAN_OPERATOR_TYPE_5.getCode().equals(transferSend2.getOperatorType())){
                studentInfo.setPlanningConsultantNo(sysUser.getOaid());
                studentInfo.setPlanningConsultant(sysUser.getUsername());
            }
            studentService.updateByStudentNo(studentInfo);

            //发送消息
            SendMessageReq req = new SendMessageReq();
            req.setOaid(transferListVO.getReceiveNo());
            req.setOperatorNo(sysUser.getOaid());
            req.setStudentNo(transferInfo.getStudentNo());
            req.setTaskType(Contants.WORK_MESSAGE);
            req.setTemplateCode("transferConfirm");
            req.getTemplateParam().put(TaskTemplateKeyEnum.STUDENT_NO.getCode(), transferInfo.getStudentNo());
            req.getTemplateParam().put(TaskTemplateKeyEnum.STUDENT_NAME.getCode(), transferInfo.getStudentName());
            userTaskRelationService.sendMessage(req);
        }

        return true;
    }
}
