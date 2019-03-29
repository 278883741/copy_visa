package com.aoji.service.impl;
import com.aoji.async.AsyncTask;
import com.aoji.contants.Contants;
import com.aoji.contants.NoteMessageStatus;
import com.aoji.contants.StudentStatus;
import com.aoji.mapper.ApplyInfoMapper;
import com.aoji.mapper.AuditApplyInfoMapper;
import com.aoji.mapper.AuditResultInfoMapper;
import com.aoji.mapper.StudentSettleInfoMapper;
import com.aoji.model.*;
import com.aoji.model.req.SendMessageReq;
import com.aoji.service.*;
import com.aoji.utils.HttpUtils;
import com.aoji.utils.ServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaojianfei
 * @description 审批结果信息表
 * @date Created in 下午2:31 2017/12/7
 */
@Service
public class AuditResultImpl implements AuditResultService{

    public static final Logger logger = LoggerFactory.getLogger(AuditResultImpl.class);

    @Autowired
    AuditApplyInfoMapper auditApplyInfoMapper;
    @Autowired
    AuditResultInfoMapper auditResultInfoMapper;

    @Autowired
    AuditApplyService auditApplyService;
    @Autowired
    VisaApplyService visaApplyService;
    @Autowired
    VisaResultService visaResultService;
    @Autowired
    VisaRecordService visaRecordService;
    @Autowired
    FollowServiceInfoService followServiceInfoService;
    @Autowired
    FollowServiceResultService followServiceResultService;
    @Autowired
    UserTaskRelationService userTaskRelationService;
    @Autowired
    PlanCollegeInfoService planCollegeInfoService;
    @Autowired
    CoeApplyService coeApplyService;
    @Autowired
    StudentService studentService;
    @Autowired
    PlanInfoService planInfoService;
    @Autowired
    ApplyResultService applyResultService;
    @Autowired
    ApplyCollegeService applyCollegeService;
    @Autowired
    UserService userService;
    @Autowired
    StudentSettleService studentSettleService;
    @Autowired
    StudentSettleRecordService studentSettleRecordService;
    @Autowired
    SendStudentMessageService sendStudentMessageService;
    @Autowired
    CountryService countryService;
    @Autowired
    StudentDelayService studentDelayService;
    @Autowired
    StudentSettleInfoMapper studentSettleInfoMapper;
    @Autowired
    AuditResultService auditResultService;
    @Autowired
    ApplyInfoMapper applyInfoMapper;

    @Autowired
    private CommissionManageService commissionManageService;

    @Autowired
    AsyncTask asyncTask;

    @Value("${insertMessage.enable}")
    private Boolean insertMessageEnable;
    @Value("${insertMessage.url}")
    private String insertMessageUrl;

    @Value("${sendMessageAPP.enable}")
    private Boolean sendMessageAPPEnable;
    @Value("${sendMessageAPP.url}")
    private String sendMessageAPPUrl;
    @Value("${updateVisaStatus.url}")
    private String updateVisaStatusUrl;

    @Value("${apiTaskComplete.enable}")
    private Boolean apiTaskCompleteEnable;
    @Value("${apiTaskComplete.url}")
    private String apiTaskCompleteUrl;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(AuditResultInfo auditResultInfo) {
        boolean result = true;
        result = auditResultInfoMapper.insertSelective(auditResultInfo) > 0;
        if (result) {
            AuditApplyInfo auditApplyInfo = new AuditApplyInfo();
            auditApplyInfo.setId(auditResultInfo.getApplyId());
            auditApplyInfo = auditApplyService.get(auditApplyInfo);
            auditApplyInfo.setDeleteStatus(true);
            auditApplyInfo.setUpdateTime(new Date());
            auditApplyService.update(auditApplyInfo);
            // 非审批最后节点
            if (Integer.valueOf(0).equals(auditApplyInfo.getLastAudit())) {
                // 一级审批人同意 -> 插入下一级待审批数据并更新业务数据审批状态为审批中
                if (auditResultInfo.getApplyStatus().equals(2)) {
                    switch (auditResultInfo.getCaseId()) {
                        case 3: {
                            ApplyResultInfo applyResultInfo = new ApplyResultInfo();
                            applyResultInfo.setId(auditResultInfo.getBusinessId());
                            applyResultInfo = applyResultService.get(applyResultInfo);
                            applyResultInfo.setAuditStatus(Contants.APPLYSTATUS_AUDITING);
                            applyResultInfo.setUpdateTime(new Date());
                            applyResultService.update(applyResultInfo);
                        };break;
                        case 4: {
                            CoeApplyInfo coeApplyInfo = new CoeApplyInfo();
                            coeApplyInfo.setId(auditResultInfo.getBusinessId());
                            coeApplyInfo = coeApplyService.get(coeApplyInfo);
                            coeApplyInfo.setUpdateTime(new Date());
                            coeApplyService.update(coeApplyInfo);
                        };break;
                        case 5: {
                            VisaApplyInfo visaApplyInfo = new VisaApplyInfo();
                            visaApplyInfo.setId(auditResultInfo.getBusinessId());
                            visaApplyInfo = visaApplyService.get(visaApplyInfo);
                            visaApplyInfo.setApplyAuditStatus(Contants.APPLYSTATUS_AUDITING);
                            visaApplyInfo.setUpdateTime(new Date());
                            visaApplyService.update(visaApplyInfo);
                        };break;
                        case 6: {
                            VisaResultInfo visaResultInfo = new VisaResultInfo();
                            visaResultInfo.setId(auditResultInfo.getBusinessId());
                            visaResultInfo = visaResultService.get(visaResultInfo);
                            visaResultInfo.setVisaStatus(Contants.APPLYSTATUS_AUDITING);
                            visaResultInfo.setUpdateTime(new Date());
                            visaResultService.update(visaResultInfo);
                        };break;
                        case 7: {
                            VisaRecordInfo visaRecordInfo = new VisaRecordInfo();
                            visaRecordInfo.setId(auditResultInfo.getBusinessId());
                            visaRecordInfo = visaRecordService.get(visaRecordInfo);
                            visaRecordInfo.setAuditStatus(Contants.APPLYSTATUS_AUDITING);
                            visaRecordInfo.setUpdateTime(new Date());
                            visaRecordService.update(visaRecordInfo);
                        };break;
                        case 8: {
                            FollowServiceResult followServiceResult = new FollowServiceResult();
                            followServiceResult.setId(auditResultInfo.getBusinessId());
                            followServiceResult = followServiceResultService.get(followServiceResult);
                            followServiceResult.setAuditStatus(Contants.APPLYSTATUS_AUDITING);
                            followServiceResult.setUpdateTime(new Date());
                            followServiceResultService.update(followServiceResult);
                        };break;
                        case 10: {
                            StudentInfo studentInfo = new StudentInfo();
                            studentInfo.setId(auditResultInfo.getBusinessId());
                            studentInfo = studentService.get(studentInfo);
                            studentInfo.setUpdateTime(new Date());
                            studentService.update(studentInfo);
                        };break;
                        case 11: {
                            StudentSettleInfo studentSettleInfo = new StudentSettleInfo();
                            studentSettleInfo.setId(auditResultInfo.getBusinessId());
                            studentSettleInfo = studentSettleService.get(studentSettleInfo);
                            studentSettleInfo.setAuditStatus(Contants.APPLYSTATUS_AUDITING);
                            studentSettleInfo.setUpdateTime(new Date());
                            studentSettleService.update(studentSettleInfo);
                        }
                        default:
                            break;
                    }
                    auditApplyService.add(auditApplyInfo.getBusinessId(), auditApplyInfo.getCaseId(), auditApplyInfo.getAuditSequence() + 1, auditResultInfo.getStudentNo(),"","");
                }
                // 一级审批人不同意
                else {
                    int auditResult =  Contants.APPLYSTATUS_REJECT;
                    lastAudit(auditResultInfo,auditResult);
                }
            }
            // 审批最后节点人
            else {
                int auditResult = 0;
                // 1-不同意 2-同意
                // 1-已提交 2-审核中 3-审核通过 4-审核不通过
                if (auditResultInfo.getApplyStatus().equals(1)) {
                    auditResult = Contants.APPLYSTATUS_REJECT;
                }
                else if (auditResultInfo.getApplyStatus().equals(2)) {
                    auditResult = Contants.APPLYSTATUS_ACCEPT;
                }
                lastAudit(auditResultInfo,auditResult);
            }
        }
        else{
            result = false;
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public void lastAudit(AuditResultInfo auditResultInfo,Integer auditResult){
        SendMessageReq sendMessageReq = new SendMessageReq();
        Map<String, String> map = new HashMap<String, String>();
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(auditResultInfo.getStudentNo());
        studentInfo = studentService.get(studentInfo);
        switch (auditResultInfo.getCaseId()) {
            case 3:{
                ApplyResultInfo applyResultInfo = new ApplyResultInfo();
                applyResultInfo.setId(auditResultInfo.getBusinessId());
                applyResultInfo = applyResultService.get(applyResultInfo);
                applyResultInfo.setAuditStatus(auditResult);
                applyResultInfo.setUpdateTime(new Date());
                applyResultService.update(applyResultInfo);

                ApplyInfo applyInfo = new ApplyInfo();
                applyInfo.setId(applyResultInfo.getApplyId());
                applyInfo.setDeleteStatus(false);
                applyInfo = applyInfoMapper.selectOne(applyInfo);
                String collegeName = "";
                if(!StringUtils.isEmpty(applyInfo.getCollegeName())){
                    collegeName = applyInfo.getCollegeName();
                }

                map.put("caseName","院校申请-申请结果审核");
                sendMessageReq.setOaid(applyResultInfo.getOperatorNo());

                if(auditResult.equals(Contants.APPLYSTATUS_ACCEPT)) {
                    if(insertMessageEnable){
                        Date now = new Date();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String message = String.format("studentNoOrResourceNo=%s&type=%s&taskType=%s&title=%s&content=%s&messageTime=%s", studentInfo.getStudentNo(),"1", "5", "院校申请反馈", "您好。您申请的"+collegeName+"学校已有了新进度，请尽快跟进", simpleDateFormat.format(now));
                        logger.info("插入小希系统消息数据: " + message);
                        String s = HttpUtils.sendPost2(insertMessageUrl, message);
                        logger.info("插入小希系统消息响应数据: " +s);
                    }
                    // 调用学生留学管家发送接口
                    if(sendMessageAPPEnable) {
                        String message = String.format("studentNo=%s&content=%s。", applyInfo.getStudentNo(), "您好，您申请的院校已有申请结果，进入申请进程了解详情");
                        logger.info("审批院校申请结果后调用留学管家消息: " +message);
                        String result = HttpUtils.sendPost2(sendMessageAPPUrl, message);
                        logger.info("审批院校申请结果后调用留学管家消息返回数据: " +result);
                    }
                }
            };break;
            case 4:{
                CoeApplyInfo coeApplyInfo = new CoeApplyInfo();
                coeApplyInfo.setId(auditResultInfo.getBusinessId());
                coeApplyInfo = coeApplyService.get(coeApplyInfo);
                Integer nation = studentInfo.getNationId();
                if(nation.equals(4) || nation.equals(40) || nation.equals(41)){
                    map.put("caseName", "院校申请-I-20");
                }
                else if(nation.equals(5)){
                    map.put("caseName", "院校申请-Full Offer");
                }
                else if(nation.equals(3)){
                    map.put("caseName", "院校申请-CAS");
                }
                else if(nation.equals(2)){
                    map.put("caseName", "院校申请-Receipt");
                }
                else {
                    map.put("caseName", "院校申请-COE");
                }
                sendMessageReq.setOaid(coeApplyInfo.getOperatorNo());
            };break;
            case 5: {
                VisaApplyInfo visaApplyInfo = new VisaApplyInfo();
                visaApplyInfo.setId(auditResultInfo.getBusinessId());
                visaApplyInfo = visaApplyService.get(visaApplyInfo);
                visaApplyInfo.setApplyAuditStatus(auditResult);
                visaApplyInfo.setUpdateTime(new Date());
                visaApplyService.update(visaApplyInfo);

                SysUser sysUser = userService.getLoginUser();
                if(auditResult.equals(Contants.APPLYSTATUS_ACCEPT)) {
                    if(studentInfo.getStatus() <= StudentStatus.NO_VISA_APPLY.getCode()) {
                        studentService.updateProcessStatus(visaApplyInfo.getStudentNo(), StudentStatus.NO_VISA_APPLY.getCode(), sysUser.getOaid());
                    }
                    // 发短信
                    if(studentInfo.getChannelStatus() != null && !StringUtils.isEmpty(studentInfo.getChannelStatus()) && !studentInfo.getChannelStatus().equals(1)) {
                        sendStudentMessageService.sendStudentMessage(sysUser, NoteMessageStatus.NOTE_NO_VISA_COACH.getCode(), studentInfo.getStudentNo(), false, studentInfo.getNationName());
                    }
                }
                map.put("caseName","签证申请");
                sendMessageReq.setOaid(visaApplyInfo.getOperatorNo());
            };break;
            case 6: {
                VisaResultInfo visaResultInfo = visaResultService.getById(auditResultInfo.getBusinessId());
                visaResultInfo.setVisaStatus(auditResult);
                visaResultInfo.setUpdateTime(new Date());
                visaResultService.update(visaResultInfo);

                SysUser sysUser = userService.getLoginUser();
                if(auditResult.equals(Contants.APPLYSTATUS_ACCEPT)) {
                    if(studentInfo.getStatus().equals(StudentStatus.NO_VISA_RESULT.getCode())) {
                        studentService.updateProcessStatus(visaResultInfo.getStudentNo(), studentInfo.getStatus(), sysUser.getOaid());
                    }
                    //小希学生端消息
                    if(insertMessageEnable){
                        if(visaResultInfo.getVisaResult().equals(0) || visaResultInfo.getVisaResult().equals(1)) {
                            Date now = new Date();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String message = String.format("studentNoOrResourceNo=%s&type=%s&taskType=%s&title=%s&content=%s&messageTime=%s", studentInfo.getStudentNo(), "1", "6", "签证申请反馈", "您好。您的签证申请有了新状态，赶快去查看吧", simpleDateFormat.format(now));
                            logger.info(message);
                            String s = HttpUtils.sendPost2(insertMessageUrl, message);
                            logger.info(s);
                        }
                    }
                    if(visaResultInfo.getVisaResult().equals(1)){
                        // 发短信
                        boolean isDanQianZheng = false;
                        if(studentInfo.getContractType() != null){
                            if(studentInfo.getContractType().equals(ServiceUtils.CONTRACT_TYPE_VISA)){
                                isDanQianZheng = true;
                            }
                        }
                        if(studentInfo.getChannelStatus() != null && !StringUtils.isEmpty(studentInfo.getChannelStatus()) && !studentInfo.getChannelStatus().equals(1)) {
                            sendStudentMessageService.sendStudentMessage(sysUser, NoteMessageStatus.NOTE_NO_VISA_APPLY.getCode(), studentInfo.getStudentNo(), isDanQianZheng, studentInfo.getNationName());
                        }
                        // 调用学生留学管家发送接口
                        if(sendMessageAPPEnable) {
                            String message = String.format("studentNo=%s&content=%s的签证申请已经通过了。", studentInfo.getStudentNo(), studentInfo.getStudentName());
                            logger.info(message);
                            String result = HttpUtils.sendPost2(sendMessageAPPUrl, message);
                            logger.info(result);
                        }
                        // 审批同意后根据学号更改客户推荐表的获签状态
                        asyncTask.updateVisaStatusUrl(visaResultInfo.getStudentNo(),updateVisaStatusUrl);
                    }
                }
                map.put("caseName","签证结果");
                sendMessageReq.setOaid(visaResultInfo.getOperatorNo());
            };break;
            case 7: {
                VisaRecordInfo visaRecordInfo = new VisaRecordInfo();
                visaRecordInfo.setId(auditResultInfo.getBusinessId());
                visaRecordInfo = visaRecordService.get(visaRecordInfo);
                visaRecordInfo.setAuditStatus(auditResult);
                visaRecordInfo.setUpdateTime(new Date());
                visaRecordService.update(visaRecordInfo);

                map.put("caseName","获签信息-结佣金");
                sendMessageReq.setOaid(visaRecordInfo.getOperatorNo());

                SysUser sysUser = userService.getLoginUser();

                if(auditResult.equals(Contants.APPLYSTATUS_ACCEPT)) {
                    // 获签信息审批通过后学生状态变为已结案
                    studentService.updateProcessStatus(visaRecordInfo.getStudentNo(), StudentStatus.INCOMPLETE.getCode(), sysUser.getOaid());
                    StudentSettleInfo studentSettleInfo = new StudentSettleInfo();
                    studentSettleInfo.setStudentNo(visaRecordInfo.getStudentNo());
                    if(!StringUtils.isEmpty(visaRecordInfo.getStudentNo())){
                        String studentName = studentService.getStudentInfoByStudentNo(visaRecordInfo.getStudentNo()).getStudentName();
                        studentSettleInfo.setStudentName(studentName);
                    }
                    studentSettleInfo.setCreateTime(new Date());
                    studentSettleInfo.setUpdateTime(new Date());
                    studentSettleInfo.setDeleteStatus(false);
                    studentSettleInfo.setOperatorNo(sysUser.getOaid());
                    studentSettleInfo.setOperatorName(sysUser.getUsername());
                    studentSettleInfo.setAttachment("");
                    studentSettleInfo.setReason(4);
                    studentSettleInfo.setAuditStatus(Contants.APPLYSTATUS_ACCEPT);
                    studentSettleInfoMapper.insertSelective(studentSettleInfo);

                    AuditResultInfo auditResultInfo1 = new AuditResultInfo();
                    auditResultInfo1.setBusinessId(studentSettleInfo.getId());
                    auditResultInfo1.setCaseId(11);
                    auditResultInfo1.setApplyStatus(2);
                    auditResultInfo1.setCreateTime(new Date());
                    auditResultInfo1.setUpdateTime(new Date());
                    auditResultInfo1.setDeleteStatus(false);
                    auditResultInfo1.setOperatorNo("admin");
                    auditResultInfo1.setOperatorName("admin");
                    auditResultInfo1.setReason("自动审批");
                    auditResultInfo1.setApplyId(-2);
                    auditResultInfoMapper.insertSelective(auditResultInfo1);

                    //获签后调签约系统接口告知任务完成
                    if(auditResult.equals(Contants.APPLYSTATUS_ACCEPT)) {
                        if(apiTaskCompleteEnable){
                            String result = String.format("sid=%s&isSuccess=%s&api-version=%s", studentInfo.getStudentNo(),1, "1.0");
                            logger.info("获签后自动结案告知任务完成结果为"+result);
                            String s = HttpUtils.sendPost2(apiTaskCompleteUrl+"?"+result, result);
                            logger.info(s);
                        }
                    }
                }
                try{
                    commissionManageService.insertCommissionManage(auditResultInfo.getStudentNo());
                }catch(Exception e){
                    logger.info("insertCommission to Error ! student:"+auditResultInfo.getStudentNo());
                }
            };break;
            case 8:{
                FollowServiceResult followServiceResult = new FollowServiceResult();
                followServiceResult.setId(auditResultInfo.getBusinessId());
                followServiceResult = followServiceResultService.get(followServiceResult);
                followServiceResult.setAuditStatus(auditResult);
                followServiceResult.setUpdateTime(new Date());
                followServiceResultService.update(followServiceResult);
                map.put("caseName","后续申请-申请结果");
                sendMessageReq.setOaid(followServiceResult.getOperatorNo());
                if(auditResult.equals(Contants.APPLYSTATUS_ACCEPT)) {
                    if(insertMessageEnable){
                        Date now = new Date();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String message = String.format("studentNoOrResourceNo=%s&type=%s&taskType=%s&title=%s&content=%s&messageTime=%s", studentInfo.getStudentNo(),"1", "7", "海外服务", "您好。您的海外服务有了新进展，赶快去查看吧", simpleDateFormat.format(now));
                        logger.info(message);
                        String s = HttpUtils.sendPost2(insertMessageUrl, message);
                        logger.info(s);
                    }
                }
            };break;
            case 10:{
                List<StudentDelayInfo> studentDelayInfos = studentDelayService.getList(studentInfo.getStudentNo());
                if(studentDelayInfos.size() > 0 && studentDelayInfos.get(studentDelayInfos.size()-1).getOperatorNo() != null ){
                    map.put("caseName","申请学生停办");
                    sendMessageReq.setOaid(studentDelayInfos.get(studentDelayInfos.size()-1).getOperatorNo());
                }else{
                    map.put("caseName","申请学生停办");
                    sendMessageReq.setOaid("11257");
                }
                if(auditResult.equals(Contants.APPLYSTATUS_ACCEPT)) {
                    if(insertMessageEnable){
                        Date now = new Date();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String message = String.format("studentNoOrResourceNo=%s&type=%s&taskType=%s&title=%s&content=%s&messageTime=%s", studentInfo.getStudentNo(),"1", "8", "留学暂缓", "您好。您发起了留学暂缓申请，请再次确认", simpleDateFormat.format(now));
                        logger.info(message);
                        String s = HttpUtils.sendPost2(insertMessageUrl, message);
                        logger.info(s);
                    }
                }
            };break;
            case 11:{
                StudentSettleInfo studentSettleInfo = new StudentSettleInfo();
                studentSettleInfo.setId(auditResultInfo.getBusinessId());
                studentSettleInfo = studentSettleService.get(studentSettleInfo);
                studentSettleInfo.setAuditStatus(auditResult);
                studentSettleInfo.setUpdateTime(new Date());
                studentSettleService.update(studentSettleInfo);
                if (auditResult.equals(Contants.APPLYSTATUS_ACCEPT)) {
                    SysUser sysUser = userService.getLoginUser();
                    StudentSettleRecord studentSettleRecord = new StudentSettleRecord();
                    studentSettleRecord.setSettleInfoId(studentSettleInfo.getId());
                    studentSettleRecord.setStudentNo(studentSettleInfo.getStudentNo());
                    studentSettleRecord.setOperatorNo(sysUser.getOaid());
                    studentSettleRecord.setOperatorName(sysUser.getUsername());
                    studentSettleRecord.setCreateTime(new Date());
                    studentSettleRecord.setDeteleStatus(false);
                    studentSettleRecordService.add(studentSettleRecord);
                    studentService.updateProcessStatus(studentSettleInfo.getStudentNo(), StudentStatus.NO_VISA_INFO.getCode(), sysUser.getOaid());
                }
                //结案后调签约系统接口告知任务关闭
                if(auditResult.equals(Contants.APPLYSTATUS_ACCEPT)) {
                    if(apiTaskCompleteEnable){
                        String result = String.format("sid=%s&isSuccess=%s&api-version=%s", studentInfo.getStudentNo(),0, "1.0");
                        logger.info("结案后自动结案告知任务关闭结果为"+result);
                        String s = HttpUtils.sendPost2(apiTaskCompleteUrl+"?"+result, result);
                        logger.info(s);
                    }
                }
                map.put("caseName","学生结案申请");
                sendMessageReq.setOaid(studentSettleInfo.getOperatorNo());
            }
            default:
                break;
        }
        map.put("studentNo",studentInfo.getStudentNo());
        map.put("studentName",studentInfo.getStudentName());
        // 通知提交表单的人
        sendMessageReq.setStudentNo(studentInfo.getStudentNo());
        sendMessageReq.setTemplateCode("audited");
        SysUser sysUser = userService.getLoginUser();
        if(sysUser != null){
            sendMessageReq.setOperatorNo(sysUser.getOaid());
        }else{
            sendMessageReq.setOperatorNo(auditResultInfo.getOperatorNo());
        }

        sendMessageReq.setTaskType(3);

        sendMessageReq.setTemplateParam(map);
        userTaskRelationService.sendMessage(sendMessageReq);
    }

    @Override
    public Integer update(AuditResultInfo auditApplyInfo){
        return auditResultInfoMapper.updateByPrimaryKey(auditApplyInfo);
    }

//    @Override
//    public <T> List<T> getList(Integer caseId,Integer businessId,Class<T> tClass){
//        Example example = new Example(tClass);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("deleteStatus", false);
//        List<T> list = new ArrayList<T>();
//        switch (tClass.getName()){
//            case "com.aoji.model.AuditResultInfo":
//                list = (List<T>) auditResultInfoMapper.selectByExample(example);break;
//            case "com.aoji.model.AuditApplyInfo":
//                list = (List<T>) auditApplyInfoMapper.selectByExample(example);break;
//        }
//        return list;
//    }

    @Override
    public List<AuditResultInfo> getList(Integer caseId,Integer businessId){
        Example example = new Example(AuditResultInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deleteStatus", false);
        criteria.andEqualTo("caseId", caseId);
        criteria.andEqualTo("businessId", businessId);
        List<AuditResultInfo> list = auditResultInfoMapper.selectByExample(example);
        for(AuditResultInfo item :list){
            item.setApplyStatus_string(item.getApplyStatus()==1?"不同意":"同意");
        }
        return list;
    }
}
