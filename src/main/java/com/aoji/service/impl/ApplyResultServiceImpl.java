package com.aoji.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.*;
import com.aoji.mapper.ApplyResultInfoMapper;
import com.aoji.mapper.AuditDingdingInfoMapper;
import com.aoji.mapper.StudentInfoMapper;
import com.aoji.mapper.SysRoleMapper;
import com.aoji.model.*;
import com.aoji.service.*;
import com.aoji.utils.HttpUtils;
import com.aoji.vo.ApplyResultVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.xmp.impl.Base64;
import com.rabbitmq.client.AMQP;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author yangsaixing
 * @description
 * @date Created in 下午2:31 2017/12/7
 */
@Service
public class ApplyResultServiceImpl implements ApplyResultService {
    @Autowired
    ApplyResultInfoMapper applyResultInfoMapper;

    @Autowired
    ApplyCollegeService applyCollegeService;

    @Autowired
    StudentService studentService;

    @Autowired
    AuditApplyService auditApplyService;

    @Autowired
    StudentInfoMapper studentInfoMapper;

    @Autowired
    UserService userService;

    @Autowired
    AuditDingdingInfoMapper auditDingdingInfoMapper;

    @Autowired
    SendStudentMessageService sendStudentMessageService;

    @Autowired
    AuditResultService auditResultService;

    @Autowired
    SendDingDingMessageService sendDingDingMessageService;

    @Autowired
    FileService fileService;

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Value("${upload.ks3.resDomain}")
    private String resDomain;

    @Value("${dingding.message.link}")
    private String dingdingMessageLink;

    @Value("${send.oaid}")
    private String sendOaid;

    @Value("${dingding.message.status}")
    private Boolean dingdingMessageStatus;

    @Value("${dingding.message.api}")
    private String dingdingMessageApi;

    @Value("${offerMessage.enable}")
    private Boolean offerMessageFlag;

    @Value("${offerMessage.url}")
    private String offerMessageUrl;

    @Value("${get.staffInfo.url}")
    private String getStaffInfoUrl;

    private Logger logger = LoggerFactory.getLogger(ApplyResultServiceImpl.class);

    @Override
    public List<ApplyResultInfo> getByPage(ApplyResultInfo applyResultInfo) {
        List<ApplyResultInfo> applyResultInfo1 = applyResultInfoMapper.select(applyResultInfo);
        if (applyResultInfo != null && applyResultInfo1.size() > 0) {
            for (ApplyResultInfo applyResultInfo2 : applyResultInfo1) {
                if (StringUtils.hasText(applyResultInfo2.getOfferAttachment()) && !applyResultInfo2.getOfferAttachment().contains(resDomain)) {
//                    applyResultInfo2.setOfferAttachment(resDomain + applyResultInfo2.getOfferAttachment());
                    applyResultInfo2.setOfferAttachment(fileService.getPrivateUrl(applyResultInfo2.getOfferAttachment()));
                }
                StudentInfo studentInfo = applyResultInfoMapper.getStudentInfoByApplyId(String.valueOf(applyResultInfo2.getApplyId()));
                if (studentInfo != null) {
                    applyResultInfo2.setStudentNo(studentInfo.getStudentNo());
                    applyResultInfo2.setStudentName(studentInfo.getStudentName());
                }
            }
        }
        return applyResultInfo1;
    }

    //NPA非私密附件，私密附件地址前台获取，不绑定
    @Override
    public List<ApplyResultInfo> getByPageNPA(ApplyResultInfo applyResultInfo) {
        List<ApplyResultInfo> applyResultInfo1 = applyResultInfoMapper.select(applyResultInfo);
        if (applyResultInfo != null && applyResultInfo1.size() > 0) {
            for (ApplyResultInfo applyResultInfo2 : applyResultInfo1) {
                if (StringUtils.hasText(applyResultInfo2.getOfferAttachment()) && !applyResultInfo2.getOfferAttachment().contains(resDomain)) {
                    applyResultInfo2.setOfferAttachment(resDomain + applyResultInfo2.getOfferAttachment());
//                    applyResultInfo2.setOfferAttachment(fileService.getPrivateUrl(applyResultInfo2.getOfferAttachment()));
                }
                StudentInfo studentInfo = applyResultInfoMapper.getStudentInfoByApplyId(String.valueOf(applyResultInfo2.getApplyId()));
                if (studentInfo != null) {
                    applyResultInfo2.setStudentNo(studentInfo.getStudentNo());
                    applyResultInfo2.setStudentName(studentInfo.getStudentName());
                }
            }
        }
        return applyResultInfo1;
    }

    @Override
    public List<ApplyResultInfo> getByPageList(ApplyResultInfo applyResultInfo) {
        List<ApplyResultInfo> applyResultInfo1 = applyResultInfoMapper.getApplyResultInfo(applyResultInfo.getOperatorNo());
        if (applyResultInfo != null && applyResultInfo1.size() > 0) {
            for (ApplyResultInfo applyResultInfo2 : applyResultInfo1) {
                if (StringUtils.hasText(applyResultInfo2.getOfferAttachment()) && !applyResultInfo2.getOfferAttachment().contains(resDomain)) {
                    applyResultInfo2.setOfferAttachment(resDomain + applyResultInfo2.getOfferAttachment());
                }
                StudentInfo studentInfo = applyResultInfoMapper.getStudentInfoByApplyId(String.valueOf(applyResultInfo2.getApplyId()));
                if (studentInfo != null) {
                    applyResultInfo2.setStudentNo(studentInfo.getStudentNo());
                    applyResultInfo2.setStudentName(studentInfo.getStudentName());
                }
            }
        }
        return applyResultInfo1;
    }

    @Override
    public ApplyResultInfo getById(Integer id) {
        ApplyResultInfo applyResultInfo = applyResultInfoMapper.selectByPrimaryKey(id);
        return applyResultInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse saveResultAndSchool(ApplyResultVo applyResultVo) throws Exception {
        BaseResponse baseResponse = new BaseResponse();
        Integer updateSchoolStatus = 1;
        Integer updateOfferResult = 0;
        ApplyResultInfo applyResultInfo;
        if (applyResultVo == null || applyResultVo.getResult() == null || StringUtils.isEmpty(applyResultVo.getApply().getId()) || StringUtils.isEmpty(applyResultVo.getResult().getResultType())) {
            throw new ContentException(2001, "保存offer结果参数异常");
        }
        //登录信息
        SysUser sysUser = userService.getLoginUser();
        if (sysUser == null || sysUser.getOaid() == null) {
            logger.error(ErrorMessage.SESSION_TIMEOUT);
            baseResponse.setResult(false);
            baseResponse.setErrorMsg("无登录信息,请重新登录!");
            return baseResponse;
        }
        ApplyInfo applyInfo = applyResultVo.getApply();
        ApplyResultInfo resultInfo = applyResultVo.getResult();
        ApplyResultInfo resultInfoOld = new ApplyResultInfo();

        //1.判断回复类型
        if ((resultInfo.getResultType()).equals(Contants.OFFERRESULT_ACCEPT) && !StringUtils.isEmpty(applyInfo.getId())) {
            //录取-更新课程专业信息
            applyInfo.setUpdateTime(new Date());
            try {
                updateSchoolStatus = applyCollegeService.update(applyInfo);
            } catch (Exception e) {
                throw new ContentException(2001, "Offer信息更新出错");
            }
        }

        //2.添加或更新回复结果
        if (!StringUtils.isEmpty(resultInfo.getId())) {
            //更新回复结果
            resultInfo.setUpdateTime(new Date());
            //根据id查询出上次的结果类型,如果不是录取则再添一条审批记录
            resultInfoOld = applyResultInfoMapper.getAppResultById(resultInfo.getId());
            if (resultInfoOld.getAuditStatus().equals(Contants.APPLYSTATUS_ACCEPT)) {
                baseResponse.setResult(false);
                baseResponse.setErrorCode("2");
                baseResponse.setErrorMsg("此记录已通过审核,不可以进行修改!");
                return baseResponse;
            }
            updateOfferResult = update(resultInfo);
            //获取修改前是否有需要审批的记录(若修改前有审批记录,且修改的状态不是录取时,把审批记录删掉)
            AuditApplyInfo auditApplyInfo = auditApplyService.get(resultInfo.getId(), CaseIdEnum.ApplyResult.getCode());
            if (auditApplyInfo != null) {
                auditApplyInfo.setDeleteStatus(true);
                auditApplyService.delete(auditApplyInfo.getBusinessId(), auditApplyInfo.getCaseId());
            }
            //若审批拒绝,则再添加一条审批记录
            offerAudit(resultInfo, applyInfo);
        } else {
            //添加回复结果
            resultInfo.setApplyId(applyInfo.getId());
            resultInfo.setOperatorNo(sysUser.getOaid());
            resultInfo.setOperatorName(sysUser.getUsername());
            resultInfo.setAuditStatus(1);
            updateOfferResult = insertResult(resultInfo);
            //添加审批
            if (!StringUtils.isEmpty(applyInfo.getId())) {
                //添加审批信息
                offerAudit(resultInfo, applyInfo);
            }

        }


        if (updateOfferResult.equals(1) && updateSchoolStatus.equals(1)) {
            baseResponse.setResult(true);
            baseResponse.setErrorMsg("操作成功");
            return baseResponse;
        } else {
            throw new ContentException(2001, "Offer信息更新出错");
        }
    }

    @Override
    public Integer insertResult(ApplyResultInfo applyResultInfo) {
        applyResultInfo.setDeleteStatus(false);
        applyResultInfo.setCreateTime(new Date());
        applyResultInfo.setUpdateTime(new Date());
        applyResultInfo.setStudentReply(0);
        return applyResultInfoMapper.insertSelective(applyResultInfo);
    }

    /**
     * 查询申请中的offer结果个数
     *
     * @param applyId
     * @param resultType
     * @return
     */
    private Integer queryCountResut(Integer applyId, Integer resultType) {
        ApplyResultInfo applyResultInfo = new ApplyResultInfo();
        applyResultInfo.setApplyId(applyId);
        applyResultInfo.setResultType(resultType);
        applyResultInfo.setDeleteStatus(false);
        return applyResultInfoMapper.selectCount(applyResultInfo);
    }

    @Override
    public Integer update(ApplyResultInfo applyResultInfo) {
        Example example = new Example(ApplyResultInfo.class);
        example.createCriteria().andEqualTo("id", applyResultInfo.getId()).andEqualTo("deleteStatus", false);
        return applyResultInfoMapper.updateByExampleSelective(applyResultInfo, example);
    }

    @Override
    public boolean remove(Integer applyResultId) {
        ApplyResultInfo applyResultInfo = applyResultInfoMapper.getAppResultById(applyResultId);
        applyResultInfo.setId(applyResultId);
        applyResultInfo.setDeleteStatus(true);
        if (applyResultInfo.getUpdateTime() != null) {
            applyResultInfo.setUpdateTime(new Date());
        }
        if (applyResultInfo.getOperatorNo() != null) {
            SysUser sysUser = userService.getLoginUser();
            applyResultInfo.setOperatorNo(sysUser.getOaid());
            applyResultInfo.setOperatorName(sysUser.getUsername());
        }
        int result = applyResultInfoMapper.updateByPrimaryKeySelective(applyResultInfo);
        return result > 0;
    }

    @Override
    public ApplyResultInfo get(ApplyResultInfo applyResultInfo) {
        applyResultInfo.setDeleteStatus(false);
        List<ApplyResultInfo> list = applyResultInfoMapper.select(applyResultInfo);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse approve(Integer id, Integer type, String remark, Integer applyId, String studentNo, String updateTime, BaseResponse baseResponse) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        AuditApplyInfo auditApplyInfo = auditApplyService.get(id, CaseIdEnum.ApplyResult.getCode());
        if (auditApplyInfo == null) {
            baseResponse.setResult(false);
            baseResponse.setErrorCode("3");
            baseResponse.setErrorMsg("无审批内容或该条审批内容已被审批");
            return baseResponse;
        }
        //查询申请结果表中的数据
        ApplyResultInfo applyResultInfoOld = applyResultInfoMapper.getAppResultById(id);
        updateTime = updateTime == null ? "" : updateTime;
        if (applyResultInfoOld != null && applyResultInfoOld.getUpdateTime() != null && !updateTime.equals(simpleDateFormat.format(applyResultInfoOld.getUpdateTime()))) {
            baseResponse.setResult(false);
            baseResponse.setErrorCode("2");
            baseResponse.setErrorMsg("需审批内容已修改,请重新审批!");
            return baseResponse;
        }

        // 修改状态
        ApplyResultInfo applyResultInfo = new ApplyResultInfo();
        applyResultInfo.setId(id);
        StudentInfo student = new StudentInfo();
        student.setStudentNo(studentNo);
        student.setDeleteStatus(false);
        student = studentService.get(student);
        if (type == 2) {
            applyResultInfo.setAuditStatus(3);
            if (StringUtils.isEmpty(student.getStatus())) {
                throw new ContentException(1001, "学生信息有误");
            }
            //更改学生状态
            if (student.getStatus().equals(StudentStatus.NO_COLLEGE_RESULT.getCode())) {
                studentService.updateProcessStatus(student.getStudentNo(), student.getStatus(), auditApplyInfo.getOaid());
            }
            //更改院校申请状态
            ApplyInfo applyInfo1 = applyCollegeService.getById(applyId);
            if (applyInfo1 != null && applyInfo1.getApplyStatusCode() == ApplyCollegeStatus.NO_COLLEGE_RESULT.getCode()) {
                ApplyInfo apply = new ApplyInfo();
                apply.setId(applyId);
                apply.setUpdateTime(new Date());
                apply.setApplyStatusCode(ApplyCollegeStatus.NO_ACCEPT.getCode());
                applyCollegeService.update(apply);
            }
        } else if (type == 1) {
            applyResultInfo.setAuditStatus(4);
        }
        int result = applyResultInfoMapper.updateByPrimaryKeySelective(applyResultInfo);
        if (result < 1) {
            throw new ContentException(1,"操作失败");
        }
        // 修改审批信息
        AuditResultInfo auditResultInfo = new AuditResultInfo();
        auditResultInfo.setStudentNo(studentNo);
        auditResultInfo.setApplyStatus(type);
        auditResultInfo.setReason(remark);
        auditApplyInfo.setLastAudit(auditApplyInfo.getLastAudit());
        auditResultInfo.setApplyId(auditApplyInfo.getId());
        auditResultInfo.setBusinessId(id);
        auditResultInfo.setCaseId(CaseIdEnum.ApplyResult.getCode());
        auditResultInfo.setOperatorNo(auditApplyInfo.getOaid());
        auditResultInfo.setOperatorName(auditApplyInfo.getOaName());
        auditResultInfo.setCreateTime(new Date());
        auditResultInfo.setUpdateTime(new Date());
        Boolean isOk = auditResultService.add(auditResultInfo);
        if (!isOk) {
            throw new ContentException(1, "审批通用服务报错");
        }
        AuditDingdingInfo auditDingdingInfo = new AuditDingdingInfo();
        auditDingdingInfo.setBussinessId(id);
        auditDingdingInfo.setCaseId(CaseIdEnum.ApplyResult.getCode());
        auditDingdingInfo.setAuditTime(new Date());
        auditDingdingInfo.setAuditStatus(true);
        auditDingdingInfo.setAuditOpteratorNo(auditResultInfo.getOperatorNo());
        int updateResult = auditDingdingInfoMapper.updateAudiDingAuditStatusByBussinessIdAndCaseId(auditDingdingInfo);
        if (updateResult > 0) {
            baseResponse.setResult(true);
            baseResponse.setErrorCode("0");
            baseResponse.setErrorMsg("操作成功");
        }else{
            throw new ContentException(1, "操作失败");
        }
        return baseResponse;
    }

    @Override
    public StudentInfo getStudentInfo(String applyId) {
        StudentInfo studentInfo = applyResultInfoMapper.getStudentInfoByApplyId(applyId);
        return studentInfo;
    }


    public void offerAudit(ApplyResultInfo resultInfo, ApplyInfo applyInfo) throws Exception {
        auditApplyService.add(resultInfo.getId(), CaseIdEnum.ApplyResult.getCode(), 1, resultInfo.getStudentNo(), "", "");
        //查询需要审批的审批工号
        AuditApplyInfo auditApplyInfonew = auditApplyService.get(resultInfo.getId(), CaseIdEnum.ApplyResult.getCode());
        //钉钉审批页面路径
        String sendUrl = dingdingMessageLink + "/applyResult/detailPage?id=" + resultInfo.getId() + "&applyId=" + applyInfo.getId() + "&dateTime=" + new Date();
        String sendUrlEncrypt = Base64.encode(sendUrl);
        String send_oaid = auditApplyInfonew.getOaid();

        if (StringUtils.hasText(sendOaid)) {
            send_oaid = sendOaid;
        }

        //判断接收消息的人工号是否为空
        if(StringUtils.hasText(send_oaid)){
            //查看员工信息--是否为内部顾问或平台顾问
            String result = getStaffInfo(send_oaid);
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (!jsonObject.getInteger("code").equals(200)) {
                logger.info("调用资源系统查看员工信息返回数据code码不为200");
                return;
            }
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
//            JSONObject staffType = jsonObject1.getJSONObject("staffType");
            int staffType = jsonObject1.getInteger("StaffType");
            //若为内部顾问，发送顾问短消息和钉钉审批
            if(staffType == 0){
//                pushMessage(send_oaid,resultInfo,applyInfo);

                //typeid=10 （小马哥钉钉消息写死的,和文签相关的都是10）
                String dingdingMessageApiMessage = dingdingMessageApi + "/smspush/DingSing?worknum=" + send_oaid + "&content=您有新的审批消息!学生学号:" + resultInfo.getStudentNo() + "&url=" + sendUrlEncrypt + "&typeid=10";
                logger.info("编写钉钉消息模板 接收人工号："+send_oaid+"学号："+resultInfo.getStudentNo());
                Integer bussinessId = null;
                String applyId = null;
                String applyResultId = null;
                String studentNo = null;
                if (resultInfo.getId() != null && !resultInfo.getId().equals("")) {
                    bussinessId = resultInfo.getId();
                }
                if (applyInfo.getId() != null && !resultInfo.getId().equals("")) {
                    applyId = String.valueOf(applyInfo.getId());
                }
                if (resultInfo.getId() != null && !resultInfo.getId().equals("")) {
                    applyResultId = String.valueOf(resultInfo.getId());
                }
                if (StringUtils.hasText(resultInfo.getStudentNo())) {
                    studentNo = resultInfo.getStudentNo();
                }
                sendDingDingMessageService.sendDingDingMessage(bussinessId, applyId,
                        applyResultId, studentNo,
                        sendUrl, send_oaid,
                        dingdingMessageApiMessage);
            }else{
//                pushMessage(send_oaid,resultInfo,applyInfo);
            }
        }else {
            logger.info("添加offer成功时，将为待审批人发送消息及钉钉审批，待审批人工号为空");
        }
    }

    void pushMessage(String memberId,ApplyResultInfo applyResultInfo,ApplyInfo applyInfo){
        logger.info("offer添加通知消息发送开始！");
        try {
            if (offerMessageFlag) {
                String result = ApplyResultTypeEnum.getResultNameByType(applyResultInfo.getResultType());
                Map<String,String> map = new HashMap<>();
                map.put("result",result);
                map.put("studentNo",applyInfo.getStudentNo());
                map.put("studentName",applyInfo.getStudentName());
                map.put("majorName",applyInfo.getMajorName());
                map.put("schoolName",applyInfo.getCollegeName());
                ObjectMapper json = new ObjectMapper();
                String jsonData = json.writeValueAsString(map);
                MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
                param.add("userId", memberId);
                param.add("templateId", "10");
                param.add("jsonData", jsonData);
                HttpUtils.doPost(offerMessageUrl, param, String.class);
                logger.info("offer添加通知消息发送成功！");

            }
        } catch (Exception e){
            logger.error("offer添加通知消息发送失败！studentNo = {}", applyResultInfo.getStudentNo());
        }
    }

    @Override
    public String getStaffInfo(String memberId){
        logger.info("调用资源系统查看员工信息开始！");
        return HttpUtils.doGet(MessageFormat.format(getStaffInfoUrl, memberId,"1.0"), String.class);
    }
}
