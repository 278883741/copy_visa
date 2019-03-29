package com.aoji.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aoji.async.AsyncTask;
import com.aoji.contants.*;
import com.aoji.mapper.*;
import com.aoji.model.*;
import com.aoji.model.req.*;
import com.aoji.model.res.PlanCollege;
import com.aoji.model.res.PlanCollegeRes;
import com.aoji.service.*;
import com.aoji.utils.HttpUtils;
import com.aoji.vo.PlanCollegeInfoVO;
import com.aoji.vo.TransferVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

@Service
public class PlanCollegeInfoServiceImpl implements PlanCollegeInfoService {

    public static final Logger logger = LoggerFactory.getLogger(PlanCollegeInfoServiceImpl.class);

    @Autowired
    AsyncTask asyncTask;
    @Autowired
    CountryService countryService;
    @Autowired
    PlanCollegeInfoMapper planCollegeInfoMapper;
    @Autowired
    ApplyCollegeService applyCollegeService;
    @Autowired
    PlanCollegeRecordMapper planCollegeRecordMapper;
    @Autowired
    TransferSendService transferSendService;
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    UserTaskRelationService userTaskRelationService;
    @Autowired
    StudentService studentService;
    @Autowired
    ApplyInfoMapper applyInfoMapper;
    @Autowired
    TransferSendInfoMapper transferSendInfoMapper;
    @Autowired
    SendStudentMessageService sendStudentMessageService;
    @Autowired
    TransferInfoMapper transferInfoMapper;
    @Autowired
    TransferSendMapper transferSendMapper;
    @Autowired
    TransferService transferService;
    @Autowired
    PlanInfoMapper planInfoMapper;
    @Autowired
    FileService fileService;
    @Value("${outreach.auto.accept}")
    Boolean autoAccept;
    @Value("${return.planCollege.sendMessage.url}")
    String returnPlanCollegeSendMessageUrl;
    @Value("${return.planCollege.sendMessage.param}")
    String returnPlanCollegeSendMessageParam;

    /**
     * 分页查询定校方案
     *
     * @param planCollegeInfoVO
     * @return
     */
    @Override
    public List<PlanCollegeInfoVO> planCollegeByPage(PlanCollegeInfoVO planCollegeInfoVO) {

        return this.getPlanCollege(planCollegeInfoVO);//延用原来dao层代码
    }

    @Override
    public int getPlanCollegeCount(PlanCollegeInfoVO planCollegeInfoVO) {
        return planCollegeInfoMapper.getPlanCollegeInfoCount(planCollegeInfoVO);
    }

    @Override
    public int getPlanCollegeCountByWorktable(PlanCollegeInfoVO planCollegeInfoVO) {
        return planCollegeInfoMapper.getPlanCollegeCountByWorktable(planCollegeInfoVO);
    }

    /**
     * 查询定校方案
     *
     * @return
     */
    @Override
    public List<PlanCollegeInfoVO> getPlanCollege(PlanCollegeInfoVO planCollegeInfoVO) {
        return planCollegeInfoMapper.getPlanCollegeInfo(planCollegeInfoVO);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Boolean updateAndInsertApplyInfo(PlanCollegeReq req) {
        SysUser sysUser = userService.getLoginUser();
        if (sysUser.getOaid() == null) {
            logger.error(ErrorMessage.SESSION_TIMEOUT);
            return false;
        }
        List<String> roles = roleService.getRolesByOaId(sysUser.getOaid());

        //修改状态
        PlanCollegeInfo planCollegeInfo = new PlanCollegeInfo();
        planCollegeInfo.setId(req.getId());
        planCollegeInfo.setAuditStatus(req.getStatus());
        if(CollegeAuditStatusEnum.VISA_REJECTED.getCode() == req.getStatus()){
            planCollegeInfo.setStudentConfirmStatus(0);
        }
        planCollegeInfo.setUpdateTime(new Date());
        int result = planCollegeInfoMapper.updateByPrimaryKeySelective(planCollegeInfo);
        if (result < 1) {
            logger.error("院校状态已改变! planCollegeInfo.id = " + req.getId());
            return false;
        }

        //添加记录
        PlanCollegeRecord planCollegeRecord = new PlanCollegeRecord();
        planCollegeRecord.setDeleteStatus(false);
        planCollegeRecord.setCreateTime(new Date());
        planCollegeRecord.setPlanCollegeId(req.getId());
        planCollegeRecord.setOperatorNo(sysUser.getOaid());
        planCollegeRecord.setOperatorName(sysUser.getUsername());
        planCollegeRecord.setRejectReason(req.getReason());
//        planCollegeRecord.setRemark(req.getRemark());
        if(Integer.valueOf(CollegeAuditStatusEnum.VISA_PASSED.getCode()).equals(req.getStatus())){
            planCollegeRecord.setResult(1);
        }else{
            planCollegeRecord.setResult(2);
        }
        planCollegeRecord.setType(Contants.PLAN_COLLEGE_RECORD_TYPE_VISA);
        planCollegeRecordMapper.insert(planCollegeRecord);

        if (Integer.valueOf(CollegeAuditStatusEnum.VISA_PASSED.getCode()).equals(req.getStatus())) {

            //查询申请计划
            PlanCollegeInfoVO planCollegeInfoVO = new PlanCollegeInfoVO();
            planCollegeInfoVO.setId(req.getId());
            //学生当前的文签顾问接受
            planCollegeInfoVO.setCopyOperatorNo(sysUser.getOaid());
            if (roles.contains(Contants.ROLE_VISA_MANAGER)) {
                planCollegeInfoVO.setCopyManager(true);
            }
            List<PlanCollegeInfoVO> planCollegeInfoVOS = planCollegeInfoMapper.getPlanCollegeInfo(planCollegeInfoVO);
            if (planCollegeInfoVOS.size() > 0) {
                planCollegeInfoVO = planCollegeInfoVOS.get(0);
            } else {
                logger.error("planCollegeInfoVOS isEmpty!!");
                throw new ContentException(1, "定校方案不存在！");
            }
            String studentNo = planCollegeInfoVO.getStudentNo();
            //添加申请
            ApplyInfo applyInfo = new ApplyInfo();
            applyInfo.setPlanId(planCollegeInfoVO.getPlanId());
            applyInfo.setPlanCollegeId(req.getId());
            applyInfo.setUpdateTime(new Date());
            applyInfo.setOperatorNo(sysUser.getOaid());
            applyInfo.setOperatorName(sysUser.getUsername());
//            applyInfo.setApplyWay();
            applyInfo.setStudentNo(studentNo);
            applyInfo.setApplyType(-1);
            applyInfo.setApplyStatusCode(ApplyCollegeStatus.NO_COLLEGE_MATERIAL.getCode());
//            applyInfo.setConnector(req.getConnector());  顾问接受后更新
            applyInfo.setCollegeName(planCollegeInfoVO.getCollegeName());
            if (planCollegeInfoVO.getCollegeNo() != null) {
                applyInfo.setCollegeId(planCollegeInfoVO.getCollegeNo());
            }
            applyInfo.setCopyOperator(planCollegeInfoVO.getOperatorNo());
            //TODO
            applyInfo.setCourseId(planCollegeInfoVO.getCourseId());
            applyInfo.setCourseName(planCollegeInfoVO.getCourseName());
            applyInfo.setCourseType(planCollegeInfoVO.getCollegeType());
            applyInfo.setCourseStartTime(planCollegeInfoVO.getCourseStartTime());
//        applyInfo.setCourseLength(planCollegeInfoVO);
            applyInfo.setMajorName(planCollegeInfoVO.getMajorName());
            applyInfo.setMajorDetail(planCollegeInfoVO.getMajorDetail());
            applyInfo.setMajorId(planCollegeInfoVO.getMajorId());
            applyInfo.setEducationSection(planCollegeInfoVO.getEducationLevel());
            applyInfo.setAgencyNo(planCollegeInfoVO.getAgencyNo());
            applyInfo.setAgencyName(planCollegeInfoVO.getAgencyName());
            applyInfo.setNationId(planCollegeInfoVO.getCollegeCountryId());

            applyInfo.setSchoolLength(planCollegeInfoVO.getSchoolLength());

            applyCollegeService.insert(applyInfo);

            //发送短信
            try {
                sendStudentMessageService.sendStudentMessage(sysUser, NoteMessageStatus.NOTE_NO_COLLEGE_APPLY.getCode(), studentNo, false, null);
            } catch (Exception e) {
                logger.error("sendStudentMessage failed! " + e.getMessage());
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean assignConnector(PlanCollegeReq req) {

        SysUser sysUser = userService.getLoginUser();
        if (sysUser.getOaid() == null) {
            logger.error(ErrorMessage.SESSION_TIMEOUT);
            return false;
        }
        List<String> roles = roleService.getRolesByOaId(sysUser.getOaid());
        //查询申请计划
        PlanCollegeInfoVO planCollegeInfoVO = new PlanCollegeInfoVO();
        planCollegeInfoVO.setId(req.getId());
        //学生当前的文签顾问接受
        planCollegeInfoVO.setCopyOperatorNo(sysUser.getOaid());
        if (roles.contains(Contants.ROLE_VISA_MANAGER)) {
            planCollegeInfoVO.setCopyManager(true);
        }
        List<PlanCollegeInfoVO> planCollegeInfoVOS = planCollegeInfoMapper.getPlanCollegeInfo(planCollegeInfoVO);
        if (planCollegeInfoVOS.size() > 0) {
            planCollegeInfoVO = planCollegeInfoVOS.get(0);
        } else {
            logger.error("planCollegeInfoVOS isEmpty!!");
            return false;
        }
        String studentNo = planCollegeInfoVO.getStudentNo();

        List<SysUser> sysUsers = userService.getByRoleName(Contants.ROLE_OUTREACH_MANAGER);

        StudentInfo studentInfo = studentService.getStudentInfoByStudentNo(studentNo);
        if (studentInfo == null) {
            throw new ContentException(1, "student not exist!!!");
        }

        ApplyInfo applyInfo = applyInfoMapper.selectByPlanCollegeId(planCollegeInfoVO.getId());
        if (applyInfo == null) {
            throw new ContentException(1, "申请信息为空！");
        }

        //查询转案信息
//        TransferVO transferVO = new TransferVO();
//        transferVO.setApplyId(applyInfo.getId());
//        List<TransferVO> transferVOS = transferSendService.transferInfoRelatedQuery(transferVO);
//        if (!transferVOS.isEmpty()) {
//            // 文签重新分配外联 禁用原来的转案信息
//            TransferSendInfo transferSendInfo = new TransferSendInfo();
//            transferSendInfo.setApplyId(applyInfo.getId());
//            transferSendInfo.setEnableStatus(false);
//            transferSendInfoMapper.updateByApplyIdSelective(transferSendInfo);
//        }

        if (!sysUsers.isEmpty()) {
            // 添加转案申请 （外联）
            TransferInfo transferInfo = new TransferInfo();
            transferInfo.setApplyId(applyInfo.getId());
            transferInfo.setStudentNo(studentInfo.getStudentNo());
            transferInfo.setStudentName(studentInfo.getStudentName());
            transferInfo.setTransferType(TransferRelatedEnum.TRAN_TYPE_OUTREACH.getCode());
            transferInfo.setCreateTime(new Date());
            transferInfo.setDeleteStatus(false);
            transferInfoMapper.insert(transferInfo);

            TransferSend transferSend = new TransferSend();
            transferSend.setTransferId(transferInfo.getId());
            transferSend.setOperatorNo(sysUser.getOaid());
            transferSend.setOperatorName(sysUser.getUsername());
            transferSend.setReceiveNo(req.getOaid());
            transferSend.setReceiveName(req.getConnector());
            transferSend.setCreateTime(new Date());
            transferSend.setEnableStatus(true);
            transferSend.setDeleteStatus(false);
            transferSend.setTransferType(TransferRelatedEnum.TRAN_SEND_TYPE_2.getCode());
            transferSendMapper.insert(transferSend);

            //发送消息
            SendMessageReq sendMessageReq = new SendMessageReq();
            sendMessageReq.setOaid(req.getOaid());
            sendMessageReq.setOperatorNo(transferSend.getOperatorNo());
            sendMessageReq.setStudentNo(studentNo);
            sendMessageReq.setTaskType(Contants.WORK_MESSAGE);
            sendMessageReq.setTemplateCode("transferConfirm");
            sendMessageReq.getTemplateParam().put(TaskTemplateKeyEnum.STUDENT_NO.getCode(), studentNo);
            sendMessageReq.getTemplateParam().put(TaskTemplateKeyEnum.STUDENT_NAME.getCode(), studentInfo.getStudentName());
            userTaskRelationService.sendMessage(sendMessageReq);

            if(autoAccept) {
                SysUser sysUser1 = new SysUser();
                sysUser1.setOaid(req.getOaid());
                sysUser1.setUsername(req.getConnector());
                transferService.updateConfirmStatus(transferSend.getId(),
                        TransferRelatedEnum.TRAN_CONFIRM_STATUS_1.getCode(), null, sysUser1);
            }
        }

        return true;
    }
	
	@Override
    public Boolean assignConnector(Integer applyId, String oaid, String connector) {

        SysUser sysUser = userService.getLoginUser();
        if (sysUser.getOaid() == null) {
            logger.error(ErrorMessage.SESSION_TIMEOUT);
            return false;
        }
        List<String> roles = roleService.getRolesByOaId(sysUser.getOaid());

        ApplyInfo apply = new ApplyInfo();
        apply.setId(applyId);
        List<ApplyInfo> applyInfos = applyInfoMapper.select(apply);
        ApplyInfo applyInfo = null;
        if (applyInfos != null && applyInfos.size() > 0) {
            applyInfo = applyInfos.get(0);
        } else {
            throw new ContentException(1, "申请信息为空！");
        }

        String studentNo = applyInfo.getStudentNo();

        List<SysUser> sysUsers = userService.getByRoleName(Contants.ROLE_OUTREACH_MANAGER);

        StudentInfo studentInfo = studentService.getStudentInfoByStudentNo(studentNo);
        if (studentInfo == null) {
            throw new ContentException(1, "student not exist!!!");
        }

        //查询转案信息
        TransferVO transferVO = new TransferVO();
        transferVO.setApplyId(applyInfo.getId());
        List<TransferVO> transferVOS = transferSendService.transferInfoRelatedQuery(transferVO);
        if (!transferVOS.isEmpty()) {
            // 文签重新分配外联 禁用原来的转案信息
            TransferSendInfo transferSendInfo = new TransferSendInfo();
            transferSendInfo.setApplyId(applyInfo.getId());
            transferSendInfo.setEnableStatus(false);
            transferSendInfoMapper.updateByApplyIdSelective(transferSendInfo);
        }

        //外联经理接受
        if (!sysUsers.isEmpty()) {
            // 添加转案申请 （外联）
            TransferInfo transferInfo = new TransferInfo();
            transferInfo.setApplyId(applyInfo.getId());
            transferInfo.setStudentNo(studentInfo.getStudentNo());
            transferInfo.setStudentName(studentInfo.getStudentName());
            transferInfo.setTransferType(TransferRelatedEnum.TRAN_TYPE_OUTREACH.getCode());
            transferInfo.setCreateTime(new Date());
            transferInfo.setDeleteStatus(false);
            transferInfoMapper.insert(transferInfo);

            TransferSend transferSend = new TransferSend();
            transferSend.setTransferId(transferInfo.getId());
            transferSend.setOperatorNo(sysUser.getOaid());
            transferSend.setOperatorName(sysUser.getUsername());
            transferSend.setReceiveNo(oaid);
            transferSend.setReceiveName(connector);
            transferSend.setCreateTime(new Date());
            transferSend.setEnableStatus(true);
            transferSend.setDeleteStatus(false);
            transferSend.setTransferType(TransferRelatedEnum.TRAN_SEND_TYPE_2.getCode());
            transferSendMapper.insert(transferSend);

            //发送消息
            SendMessageReq sendMessageReq = new SendMessageReq();
            sendMessageReq.setOaid(oaid);
            sendMessageReq.setOperatorNo(transferSend.getOperatorNo());
            sendMessageReq.setStudentNo(studentNo);
            sendMessageReq.setTaskType(Contants.WORK_MESSAGE);
            sendMessageReq.setTemplateCode("transferConfirm");
            sendMessageReq.getTemplateParam().put(TaskTemplateKeyEnum.STUDENT_NO.getCode(), studentNo);
            sendMessageReq.getTemplateParam().put(TaskTemplateKeyEnum.STUDENT_NAME.getCode(), studentInfo.getStudentName());
            userTaskRelationService.sendMessage(sendMessageReq);
            if(autoAccept) {
                SysUser sysUser1 = new SysUser();
                sysUser1.setOaid(oaid);
                sysUser1.setUsername(connector);
                transferService.updateConfirmStatus(transferSend.getId(),
                        TransferRelatedEnum.TRAN_CONFIRM_STATUS_1.getCode(), null, sysUser1);
            }

        }

        return true;
    }

    @Override
    public Integer update(PlanCollegeInfo planCollegeInfo) {
        return planCollegeInfoMapper.updateByPrimaryKeySelective(planCollegeInfo);
    }

    @Override
    public PlanCollegeInfo get(PlanCollegeInfo planCollegeInfo) {
        planCollegeInfo.setDeleteStatus(false);
        List<PlanCollegeInfo> list = planCollegeInfoMapper.select(planCollegeInfo);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Integer studentConfirm(Integer id, String studentNo){
        SysUser sysUser = userService.getLoginUser();
        PlanCollegeInfo planCollegeInfoNew = new PlanCollegeInfo();
        planCollegeInfoNew.setId(id);
        planCollegeInfoNew.setDeleteStatus(false);
        PlanCollegeInfo planCollegeInfo = get(planCollegeInfoNew);
        if(planCollegeInfo != null) {
            PlanCollegeRecord planCollegeRecord = new PlanCollegeRecord();
//            planCollegeRecord.setRejectReason("规划经理（顾问）审核定校方案");
            //1-文签，2-小希学生确认，3-运营学生确认，5-销售
            planCollegeRecord.setType(3);
            planCollegeRecord.setResult(1);
            planCollegeRecord.setPlanCollegeId(id);
            planCollegeRecord.setCreateTime(new Date());
            planCollegeRecord.setDeleteStatus(false);
            planCollegeRecord.setOperatorNo(sysUser.getOaid());
            planCollegeRecord.setOperatorName(sysUser.getUsername());
            planCollegeRecord.setUpdateTime(new Date());
            planCollegeRecordMapper.insert(planCollegeRecord);

            planCollegeInfo.setStudentConfirmStatus(2);
            planCollegeInfo.setUpdateTime(new Date());
            logger.info("运营人员"+sysUser.getOaid()+"确认了学生"+studentNo+"的定校方案");
            return update(planCollegeInfo);
        }
        else{
            logger.info("未找到学生"+studentNo+"的定校方案");
            return -1;
        }
    }

    /**
     * 添加、修改、作废定校方案
     * @param planCollegeInfoReq
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse mergePlanCollege(PlanCollegeInfoReq planCollegeInfoReq) {
        BaseResponse baseResponse = new BaseResponse();
        String confirmFile = planCollegeInfoReq.getConfirmFile();
        // 文件地址处理
        if(StringUtils.hasText(confirmFile)){
            int index = confirmFile.indexOf("?");
            if (index > -1) {
                planCollegeInfoReq.setConfirmFile(confirmFile.substring(0, index));
            }
        }
        Integer planId = planCollegeInfoReq.getPlanId();
        Integer collegeId = planCollegeInfoReq.getCollegeId();
        Date date = new Date();
        if(planId == null && collegeId == null) { // 添加
            StudentInfo studentInfo = studentService.getStudentInfoByStudentNo(planCollegeInfoReq.getStudentNo());
            if(studentInfo == null){
                throw new ContentException(0, "学生信息获取失败！");
            }
            //添加方案
            PlanInfo planInfo = new PlanInfo();
            this.convert(planInfo, planCollegeInfoReq);
            planInfo.setCreateTime(date);
            planInfo.setDeleteStatus(false);
            planInfoMapper.insert(planInfo);
            //添加定校
            PlanCollegeInfo planCollegeInfo = new PlanCollegeInfo();
            this.convert(planCollegeInfo, planCollegeInfoReq);
            planCollegeInfo.setPlanId(planInfo.getId());
            planCollegeInfo.setCreateTime(date);
            planCollegeInfo.setUpdateTime(date);
            planCollegeInfo.setAuditStatus(CollegeAuditStatusEnum.APPLIED.getCode());
            planCollegeInfo.setDeleteStatus(false);
            planCollegeInfo.setStudentConfirmStatus(0);
            planCollegeInfoMapper.insert(planCollegeInfo);
            baseResponse.setCode(planCollegeInfo.getId().toString());
        } else { //修改
            if(planId != null){
                PlanInfo planInfo = new PlanInfo();
                this.convert(planInfo, planCollegeInfoReq);
                planInfo.setId(planId);
                planInfo.setOperatorNo(null);
                planInfoMapper.updateByPrimaryKeySelective(planInfo);
            }
            // 发送小希系统消息标识
            boolean sendSysMessageFlag = false;
            if(collegeId != null){
                PlanCollegeInfo planCollegeInfo = new PlanCollegeInfo();
                this.convert(planCollegeInfo, planCollegeInfoReq);
                planCollegeInfo.setDeleteStatus(planCollegeInfoReq.getDeleteStatus());
                planCollegeInfo.setAuditStatus(planCollegeInfoReq.getAuditStatus());
                planCollegeInfo.setId(collegeId);
                boolean savePlanCollegeRecord = false;
                Integer auditStatus = planCollegeInfo.getAuditStatus();
                String memberId = null;
                String studentNo = null;
                if(auditStatus != null && auditStatus != 0){
                    PlanCollegeInfo planCollegeInfo1 = planCollegeInfoMapper.selectByPrimaryKey(collegeId);
                    if (planCollegeInfo1 == null) {
                        throw new ContentException(0, "定校单元不存在！");
                    }
                    memberId = planCollegeInfo1.getOperatorNo();
                    PlanInfo planInfo = planInfoMapper.selectByPrimaryKey(planCollegeInfo1.getPlanId());
                    if (planInfo == null) {
                        throw new ContentException(0, "定校方案不存在！");
                    }
                    studentNo = planInfo.getStudentNo();
                    if(CollegeAuditStatusEnum.AUDIT_PASSED.getCode() == auditStatus) {
                        StudentInfo studentInfo = studentService.getStudentInfoByStudentNo(studentNo);
                        if (studentInfo == null) {
                            throw new ContentException(0, "学生信息获取失败！");
                        }
                        if (!Integer.valueOf(1).equals(studentInfo.getUsaStatus())){
                            planCollegeInfo.setStudentConfirmStatus(1);
                            sendSysMessageFlag = true;
                        }
                    }
                    savePlanCollegeRecord = true;
                }
                // 操作人信息不可修改
                planCollegeInfo.setOperatorNo(null);
                planCollegeInfo.setOperatorName(null);
                planCollegeInfo.setUpdateTime(new Date());
                planCollegeInfoMapper.updateByPrimaryKeySelective(planCollegeInfo);
                if(planCollegeInfo.getApplyDeadline() != null || StringUtils.hasText(planCollegeInfo.getApplyDeadlineRemark())) {
                    // 申请截止日期特殊处理
                    PlanCollegeInfo planCollegeInfo1 = new PlanCollegeInfo();
                    planCollegeInfo1.setId(planCollegeInfo.getId());
                    planCollegeInfo1.setApplyDeadline(planCollegeInfo.getApplyDeadline());
                    planCollegeInfo1.setApplyDeadlineRemark(planCollegeInfo.getApplyDeadlineRemark());
                    planCollegeInfoMapper.updateApplyDeadlineById(planCollegeInfo1);
                }
                if(savePlanCollegeRecord){
                    PlanCollegeRecord planCollegeRecord = new PlanCollegeRecord();
                    planCollegeRecord.setDeleteStatus(false);
                    planCollegeRecord.setCreateTime(date);
                    planCollegeRecord.setPlanCollegeId(collegeId);
                    planCollegeRecord.setRejectReason(planCollegeInfoReq.getAuditRemark());
                    planCollegeRecord.setOperatorNo(planCollegeInfoReq.getOperatorNo());
                    planCollegeRecord.setOperatorName(planCollegeInfoReq.getOperatorName());
                    if(Integer.valueOf(CollegeAuditStatusEnum.AUDIT_PASSED.getCode()).equals(auditStatus)){
                        planCollegeRecord.setResult(1);
                    }else{
                        planCollegeRecord.setResult(2);
                    }
                    planCollegeRecord.setType(Contants.PLAN_COLLEGE_RECORD_TYPE_SIGN);
                    planCollegeRecordMapper.insert(planCollegeRecord);

                    // 顾问端发送消息
                    if(CollegeAuditStatusEnum.AUDIT_REJECTED.getCode() == auditStatus){
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("id", studentNo);
                        String param = MessageFormat.format(returnPlanCollegeSendMessageParam, memberId, 8, jsonObject.toString());
                        logger.info(MessageFormat.format("定校驳回发送消息！url:{0}, param:{1}", returnPlanCollegeSendMessageUrl, param));
                        String response = HttpUtils.sendPost2(returnPlanCollegeSendMessageUrl, param);
                        logger.info("定校驳回发送消息返回！response="+response);
                    }
                }
                if(sendSysMessageFlag){
                    // 发送学生确认提醒
                    asyncTask.sendXxSystemMessage(studentNo, date);
                }
            }
        }
        baseResponse.setResult(true);
        return baseResponse;
    }

    /**
     * 参数组装
     * @param planInfo
     * @param planCollegeInfoReq
     */
    public void convert(PlanInfo planInfo, PlanCollegeInfoReq planCollegeInfoReq){
        planInfo.setStudentNo(planCollegeInfoReq.getStudentNo());
//        planInfo.setAgency(planCollegeInfoReq.getAgency());
        planInfo.setApplyType(planCollegeInfoReq.getApplyType());
        if(planCollegeInfoReq.getNation() != null) {
            CountryInfo countryInfo = new CountryInfo();
            countryInfo.setCountryBussid(planCollegeInfoReq.getNation());
            CountryInfo countryInfo1 = countryService.get(countryInfo);
            planInfo.setNation(countryInfo1.getId());
        }
        planInfo.setOperatorNo(planCollegeInfoReq.getOperatorNo());
        planInfo.setAddStatus(planCollegeInfoReq.getAddStatus());
    }

    /**
     * 参数组装
     * @param planCollegeInfo
     * @param planCollegeInfoReq
     */
    public void convert(PlanCollegeInfo planCollegeInfo, PlanCollegeInfoReq planCollegeInfoReq){
        planCollegeInfo.setCollegeName(planCollegeInfoReq.getCollegeName());
        planCollegeInfo.setCollegeNo(planCollegeInfoReq.getCollegeNo());
        planCollegeInfo.setCollegeType(planCollegeInfoReq.getCollegeType());
        if(planCollegeInfoReq.getCollegeCountryId() != null) {
            CountryInfo countryInfo = new CountryInfo();
            countryInfo.setCountryBussid(planCollegeInfoReq.getCollegeCountryId());
            CountryInfo countryInfo1 = countryService.get(countryInfo);
            planCollegeInfo.setCollegeCountryId(countryInfo1.getId());
        }
        planCollegeInfo.setCourseName(planCollegeInfoReq.getCourseName());
        planCollegeInfo.setCourseId(planCollegeInfoReq.getCourseId());
        planCollegeInfo.setCourseStartTime(planCollegeInfoReq.getCourseStartTime());
        planCollegeInfo.setApplyDeadline(planCollegeInfoReq.getApplyDeadline());
        planCollegeInfo.setApplyDeadlineRemark(planCollegeInfoReq.getApplyDeadlineRemark());
        planCollegeInfo.setAgencyNo(planCollegeInfoReq.getAgencyNo());
        planCollegeInfo.setAgencyName(planCollegeInfoReq.getAgencyName());
        planCollegeInfo.setEducationLevel(planCollegeInfoReq.getEducationLevel());
        planCollegeInfo.setMajorName(planCollegeInfoReq.getMajorName());
        planCollegeInfo.setMajorId(planCollegeInfoReq.getMajorId());
        planCollegeInfo.setMajorUrl(planCollegeInfoReq.getMajorUrl());
        planCollegeInfo.setMajorDetail(planCollegeInfoReq.getMajorDetail());
        planCollegeInfo.setOperatorNo(planCollegeInfoReq.getOperatorNo());
        planCollegeInfo.setOperatorName(planCollegeInfoReq.getOperatorName());
        planCollegeInfo.setPlanComment(planCollegeInfoReq.getPlanComment());
        planCollegeInfo.setReduceCreditStatus(planCollegeInfoReq.getReduceCreditStatus());
        planCollegeInfo.setSchoolArea(planCollegeInfoReq.getSchoolArea());
        planCollegeInfo.setSchoolLength(planCollegeInfoReq.getSchoolLength());
        planCollegeInfo.setCourseRemark(planCollegeInfoReq.getCourseRemark());
        planCollegeInfo.setConfirmFile(planCollegeInfoReq.getConfirmFile());
        planCollegeInfo.setXxType(planCollegeInfoReq.getXxType());
    }

    /**
     * 查询定校方案 -- 签约系统
     * @return
     */
    @Override
    public List<PlanCollege> queryPlanCollege(PlanCollegeCondition planCollegeCondition) {
        List<PlanCollege> planColleges = planCollegeInfoMapper.queryPlanCollege(planCollegeCondition);
        planColleges.forEach(planCollege -> {
            if(StringUtils.hasText(planCollege.getConfirmFile())){
                planCollege.setConfirmFile(fileService.getPrivateUrl(planCollege.getConfirmFile()));
            }
        });
        return planColleges;
    }

    @Override
    public PlanCollegeRes queryPlanCollege(PlanCollegeQueryReq planCollegeQueryReq) {
        PlanCollegeRes planCollegeRes = new PlanCollegeRes();
        Integer begin = (planCollegeQueryReq.getPage() - 1) * planCollegeQueryReq.getPageSize();
        Integer end = planCollegeQueryReq.getPage() * planCollegeQueryReq.getPageSize();
        int count = planCollegeInfoMapper.queryPlanCollegeForManagerCount(planCollegeQueryReq);
        List<PlanCollege> planColleges = planCollegeInfoMapper.queryPlanCollegeForManager(planCollegeQueryReq, begin, end);
        planCollegeRes.setResult(true);
        planCollegeRes.setCount(count);
        planCollegeRes.setPlanColleges(planColleges);
        return planCollegeRes;
    }

    @Override
    public List<PlanCollegeInfoVO> queryPlanCollegeForVisa(PlanCollegeCondition planCollegeCondition) {
        List<PlanCollegeInfoVO> planCollegeInfoVOS = planCollegeInfoMapper.queryPlanCollegeForVisa(planCollegeCondition);
        for(PlanCollegeInfoVO planCollegeInfoVO : planCollegeInfoVOS){
            planCollegeInfoVO.setCourseRemarkName(CourseRemarkEnum.getNameByCode(planCollegeInfoVO.getCourseRemark()));
            planCollegeInfoVO.setEducationLevelName(EducationLevel.getNameByCode(planCollegeInfoVO.getEducationLevel()));
        }
        return planCollegeInfoVOS;
    }
}
