package com.aoji.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.aoji.contants.CaseIdEnum;
import com.aoji.contants.Contants;
import com.aoji.model.*;
import com.aoji.service.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import com.aoji.service.impl.VisaApplyServiceImpl;
import org.json.JSONObject;
/**
 * @author zhaojianfei
 * @description 待审批控制器
 * @date Created in 下午2:29 2017/12/7
 */
@Controller
public class AuditApplyContrller extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
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
    AuditResultService auditResultService;
    @Autowired
    UserService userService;
    @Autowired
    StudentSettleService studentSettleService;
    @Autowired
    ChannelStudentService channelStudentService;

    /**
     * 跳转到添加页
     * @return
     */
    @RequestMapping("/auditApply/addPage")
    public String add(Integer businessId,Integer caseId,String remark,Model model){
        // 判断当前登录人是不是审批人
        String currUserNo = userService.getLoginUser().getOaid();
        AuditApplyInfo auditApplyInfo = auditApplyService.get(businessId,caseId);
        if(currUserNo.equals(auditApplyInfo.getOaid())) {
            model.addAttribute("auditApplyInfo",auditApplyInfo);

            StudentInfo studentInfo = new StudentInfo();
            switch (caseId) {
                case 1: {
                    PlanCollegeInfo planCollegeInfo = new PlanCollegeInfo();
                    planCollegeInfo.setId(businessId);
                    planCollegeInfo = planCollegeInfoService.get(planCollegeInfo);

                    PlanInfo planInfo = new PlanInfo();
                    planInfo.setId(planCollegeInfo.getPlanId());
                    planInfo.setDeleteStatus(false);
                    planInfo = planInfoService.get(planInfo);

                    studentInfo = new StudentInfo();
                    studentInfo.setStudentNo(planInfo.getStudentNo());
                };break;
                case 2: {
                    studentInfo = new StudentInfo();
                    studentInfo.setId(businessId);
                    studentInfo = studentService.get(studentInfo);
                };break;
                case 3: {
                    ApplyResultInfo applyResultInfo = new ApplyResultInfo();
                    applyResultInfo.setId(businessId);
                    applyResultInfo = applyResultService.get(applyResultInfo);

                    ApplyInfo applyInfo = new ApplyInfo();
                    applyInfo.setId(applyResultInfo.getApplyId());
                    applyInfo = applyCollegeService.get(applyInfo);

                    studentInfo = new StudentInfo();
                    studentInfo.setStudentNo(applyInfo.getStudentNo());
                };break;
                case 4: {
                    CoeApplyInfo coeApplyInfo = new CoeApplyInfo();
                    coeApplyInfo.setId(businessId);
                    coeApplyInfo = coeApplyService.get(coeApplyInfo);

                    studentInfo = new StudentInfo();
                    studentInfo.setStudentNo(coeApplyInfo.getStudentNo());
                };break;
                case 5: {
                    VisaApplyInfo visaApplyInfo = new VisaApplyInfo();
                    visaApplyInfo.setId(businessId);
                    visaApplyInfo = visaApplyService.get(visaApplyInfo);

                    studentInfo = new StudentInfo();
                    studentInfo.setStudentNo(visaApplyInfo.getStudentNo());
                };break;
                case 6: {
                    VisaResultInfo visaResultInfo = new VisaResultInfo();
                    visaResultInfo.setId(businessId);
                    visaResultInfo = visaResultService.get(visaResultInfo);

                    studentInfo = new StudentInfo();
                    studentInfo.setStudentNo(visaResultInfo.getStudentNo());
                };break;
                case 7: {
                    VisaRecordInfo visaRecordInfo = new VisaRecordInfo();
                    visaRecordInfo.setId(businessId);
                    visaRecordInfo = visaRecordService.get(visaRecordInfo);

                    studentInfo = new StudentInfo();
                    studentInfo.setStudentNo(visaRecordInfo.getStudentNo());
                };break;
                case 8: {
                    FollowServiceResult followServiceResult = new FollowServiceResult();
                    followServiceResult.setId(businessId);
                    followServiceResult = followServiceResultService.get(followServiceResult);

                    FollowServiceInfo followServiceInfo = new FollowServiceInfo();
                    followServiceInfo.setId(followServiceResult.getApplyId());
                    followServiceInfo = followServiceInfoService.getList(followServiceInfo).get(0);

                    studentInfo = new StudentInfo();
                    studentInfo.setStudentNo(followServiceInfo.getStudentNo());
                };break;
                case 9: {
                    studentInfo = new StudentInfo();
                    studentInfo.setId(businessId);
                    studentInfo = studentService.get(studentInfo);
                };break;
                case 10: {
                    studentInfo = new StudentInfo();
                    studentInfo.setId(businessId);
                    studentInfo = studentService.get(studentInfo);
                };break;
                default:
                    break;
            }
            model.addAttribute("studentNo",studentInfo.getStudentNo());
            return "auditApply/add";
        }
        else{
            return "error";
        }
    }

    /**
     * 查询待审批信息
     * @param businessId
     * @param caseId
     * @return
     */
    @RequestMapping(value="/auditApply/get",method = RequestMethod.POST)
    @ResponseBody
    public String get(Integer businessId,Integer caseId){
        AuditApplyInfo auditApplyInfo = auditApplyService.get(businessId,caseId);
        String object = JSONArray.toJSONString(auditApplyInfo);
        return object;
    }

    /**
     * 审批功能
     * @param auditResultInfo
     * @return
     */
    @RequestMapping(value="/auditApply/audit",method = RequestMethod.POST)
    @ResponseBody
    public String audit(AuditResultInfo auditResultInfo){
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();

        Integer businessId = auditResultInfo.getBusinessId();
        Integer caseId = auditResultInfo.getCaseId();
        Date businessUpdateTime = null;
        switch (caseId){
            case 5:{
                VisaApplyInfo visaApplyInfo = new VisaApplyInfo();
                visaApplyInfo.setId(auditResultInfo.getBusinessId());
                visaApplyInfo = visaApplyService.get(visaApplyInfo);
                businessUpdateTime= visaApplyInfo.getUpdateTime();
            };break;
            case 6:{
                String studentNo = auditResultInfo.getStudentNo();

                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setStudentNo(studentNo);
                studentInfo.setDeleteStatus(false);
                studentInfo = studentService.get(studentInfo);
                if(studentInfo != null){
                    VisaResultInfo visaResultInfo = new VisaResultInfo();
                    visaResultInfo.setId(auditResultInfo.getBusinessId());
                    visaResultInfo = visaResultService.get(visaResultInfo);
                    if(visaResultInfo != null) {
                        businessUpdateTime = visaResultInfo.getUpdateTime();
                        if (!studentInfo.getChannelStatus().equals(1)){
                            VisaRecordInfo visaRecordInfo = new VisaRecordInfo();
                            visaRecordInfo.setStudentNo(studentNo);
                            visaRecordInfo.setDeleteStatus(false);
                            visaRecordInfo = visaRecordService.get(visaRecordInfo);
                            if(visaResultInfo.getVisaResult() == 1){
                                if(visaRecordInfo == null){
                                    jsonObject.put("code", "-1");
                                    jsonObject.put("message", "请先添加获签或入读信息");
                                    return jsonObject.toString();
                                }else {
                                    if( visaRecordInfo.getAuditStatus() != Contants.APPLYSTATUS_ACCEPT && visaRecordInfo.getAuditStatus() != Contants.APPLYSTATUS_REJECT) {
                                        jsonObject.put("code", "-2");
                                        jsonObject.put("message", "请先审核获签或入读信息");
                                        return jsonObject.toString();
                                    }
                                }
                            }
                        }
                    }
                }
            };break;
            case 7:{
                String studentNo = auditResultInfo.getStudentNo();
                VisaRecordInfo visaRecordInfo = new VisaRecordInfo();
                visaRecordInfo.setId(auditResultInfo.getBusinessId());
                visaRecordInfo = visaRecordService.get(visaRecordInfo);
                if(visaRecordInfo != null) {
                    businessUpdateTime = visaRecordInfo.getUpdateTime();

                    StudentInfo studentInfo = new StudentInfo();
                    studentInfo.setStudentNo(studentNo);
                    studentInfo.setDeleteStatus(false);
                    studentInfo = studentService.get(studentInfo);
                    if(studentInfo != null){
                        if (!studentInfo.getChannelStatus().equals(1)) {
                            VisaApplyInfo visaApplyInfo = new VisaApplyInfo();
                            visaApplyInfo.setStudentNo(studentNo);
                            visaApplyInfo.setDeleteStatus(false);
                            List<VisaApplyInfo> list_visaApply = visaApplyService.getList(visaApplyInfo);
                            if(list_visaApply != null && list_visaApply.size() >0){
                                boolean canAuditVisaRecord = list_visaApply.stream().anyMatch(item -> item.getApplyAuditStatus() == Contants.APPLYSTATUS_ACCEPT || item.getApplyAuditStatus() == Contants.APPLYSTATUS_REJECT);
                                if(!canAuditVisaRecord){
                                    jsonObject.put("code", "-1");
                                    jsonObject.put("message", "在审批获签信息之前您必须先审批签证申请");
                                    return jsonObject.toString();
                                }
                            }else{
                                jsonObject.put("code", "-2");
                                jsonObject.put("message", "在审批获签信息之前您必须先添加签证申请");
                                return jsonObject.toString();
                            }
                        }
                    }
                }
            };break;
            case 11:{
                StudentSettleInfo studentSettleInfo = new StudentSettleInfo();
                studentSettleInfo.setId(auditResultInfo.getBusinessId());
                List<StudentSettleInfo> list = studentSettleService.getListByStudentNo(auditResultInfo.getStudentNo());
                if(list.size() > 0){
                    //因结案老数据过多，待审批信息缺失，在此判断若无待审批信息则新增一条再走审批流程--孔令霁 2018-10-15
                    AuditApplyInfo auditApplyInfo = auditApplyService.get(auditResultInfo.getBusinessId(),CaseIdEnum.StudentSettle.getCode());
                    if(auditApplyInfo == null){
                        Boolean i = auditApplyService.add(auditResultInfo.getBusinessId(),CaseIdEnum.StudentSettle.getCode(),1,auditResultInfo.getStudentNo(),"admin","admin");
                        if(i){
                            AuditApplyInfo auditApply = auditApplyService.get(auditResultInfo.getBusinessId(),CaseIdEnum.StudentSettle.getCode());
                            auditResultInfo.setApplyId(auditApply.getId());
                        }
                    }
                    studentSettleInfo = list.get(list.size()-1);
                    businessUpdateTime = studentSettleInfo.getUpdateTime();
                }
            }
            default:break;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Contants.timePattern);

        if(businessUpdateTime == null || !StringUtils.hasText(auditResultInfo.getBusinessUpdateTime()) || auditResultInfo.getBusinessUpdateTime().equals(simpleDateFormat.format(businessUpdateTime))) {
            jsonObject.put("code", 1);
            auditResultInfo.setOperatorNo(userService.getLoginUser().getOaid());
            auditResultInfo.setOperatorName(userService.getLoginUser().getUsername());
            auditResultInfo.setCreateTime(new Date());
            auditResultInfo.setUpdateTime(new Date());
            auditResultInfo.setDeleteStatus(false);
            if(!auditResultService.add(auditResultInfo)){
                jsonObject.put("code", 2);
            }
        }
        else{
            jsonObject.put("code", 0);
        }
        return  jsonObject.toString();
    }
}
