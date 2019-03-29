package com.aoji.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.*;
import com.aoji.model.*;
import com.aoji.service.*;
import com.github.pagehelper.Page;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: chenhaibo
 * description: 后续管理控制类
 * date: 2017/12/27
 */
@Controller
public class FollowServiceInfoController extends BaseController{

    @Autowired
    FollowServiceInfoService followServiceInfoService;

    @Autowired
    AgencyService agencyService;

    @Autowired
    StudentService studentService;

    @Autowired
    FollowServiceResultService followServiceResultService;

    @Autowired
    AuditApplyService auditApplyService;
    @Autowired
    AuditResultService auditResultService;
    @Autowired
    UserService userService;
    @Autowired
    CurrencyInfoService currencyInfoService;
    @Autowired
    VisaRecordService visaRecordService;

    @Value("${upload.ks3.resDomain}")
    private String resDomain;

    public static final Logger logger = LoggerFactory.getLogger(FollowServiceInfoController.class);
    /**
     * 跳转后续申请列表页
     * @return
     */
    @RequestMapping("/followService/list")
    public String list(String studentNo, Model model){
        model.addAttribute("visaRecordAuditStatus", getVisaRecordResult(studentNo));
        return "followServiceInfo/list";
    }

    /**
     * 查询获签结果， true-审核通过；
     * @param studentNo
     * @return
     */
    boolean getVisaRecordResult(String studentNo){
        VisaRecordInfo visaRecordInfo = new VisaRecordInfo();
        visaRecordInfo.setDeleteStatus(false);
        visaRecordInfo.setStudentNo(studentNo);
        VisaRecordInfo visaRecordInfo1 = visaRecordService.get(visaRecordInfo);
        if(visaRecordInfo1 != null && Contants.APPLYSTATUS_ACCEPT == visaRecordInfo1.getAuditStatus()){
            return true;
        }
        return false;
    }

    /**
     * 跳转到新增页
     * @return
     */
    @RequestMapping("/followService/addPage")
    public String add(String studentNo, Model model){
        StudentInfo studentInfo = studentService.getStudentInfoByStudentNo(studentNo);
        if(studentInfo == null){
            logger.error("studentNo error!!!");
//            return "redirect:/followService/list?studentNo="+studentNo;
        }
        AgencyInfo agencyInfo = new AgencyInfo();
        agencyInfo.setEnableStatus(1);
        agencyInfo.setNationId(studentInfo.getNationId().toString());
        List<AgencyInfo> agencyInfos = agencyService.get(agencyInfo);
        model.addAttribute("agencyInfos", agencyInfos);
        List<CurrencyInfo> currencyInfos = currencyInfoService.getList(new CurrencyInfo());
        model.addAttribute("currencyInfos", currencyInfos);
        return "followServiceInfo/add";
    }

    /**
     * 跳转到编辑页
     * @return
     */
    @RequestMapping("/followService/editPage")
    public String edit(Integer id, Model model){
        FollowServiceInfo followServiceInfo = new FollowServiceInfo();
        followServiceInfo.setId(id);
        followServiceInfo.setDeleteStatus(false);
        List<FollowServiceInfo> followServiceInfos = followServiceInfoService.getList(followServiceInfo);
        StudentInfo studentInfo = null;
        if(followServiceInfos != null && followServiceInfos.size() > 0){
            if(followServiceInfos.get(0) != null && StringUtils.hasText(followServiceInfos.get(0).getAttachment()) && !followServiceInfos.get(0).getAttachment().contains(resDomain)){
                followServiceInfos.get(0).setAttachment(followServiceInfos.get(0).getAttachment());
            }
            model.addAttribute("followService", followServiceInfos.get(0));
            studentInfo = studentService.getStudentInfoByStudentNo(followServiceInfos.get(0).getStudentNo());
            //监护
            if(FollowTypeEnum.GUARDIANSHIP.getCode() == followServiceInfos.get(0).getVisitType()){
                boolean result = getVisaRecordResult(studentInfo.getStudentNo());
                model.addAttribute("canUpdate", !result);
            }
        }
        if(studentInfo == null){
            logger.error("studentNo error!!!");
//            return "redirect:/followService/list?studentNo="+studentNo;
        }
        AgencyInfo agencyInfo = new AgencyInfo();
        agencyInfo.setEnableStatus(1);
        agencyInfo.setNationId(studentInfo.getNationId().toString());
        List<AgencyInfo> agencyInfos = agencyService.get(agencyInfo);
        model.addAttribute("agencyInfos", agencyInfos);
        List<CurrencyInfo> currencyInfos = currencyInfoService.getList(new CurrencyInfo());
        model.addAttribute("currencyInfos", currencyInfos);
        model.addAttribute("resDomain",resDomain);
        return "followServiceInfo/edit";
    }

    /**
     * 跳转到详情页
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/followService/detailPage")
    public String detail(Integer id, Model model){
        FollowServiceInfo followServiceInfo = new FollowServiceInfo();
        followServiceInfo.setId(id);
        followServiceInfo.setDeleteStatus(false);
        List<FollowServiceInfo> followServiceInfos = followServiceInfoService.getList(followServiceInfo);
        StudentInfo studentInfo = null;
        if(followServiceInfos != null && followServiceInfos.size() > 0){
            if(StringUtils.hasText(followServiceInfos.get(0).getAttachment()) && !followServiceInfos.get(0).getAttachment().contains(resDomain)){
                followServiceInfos.get(0).setAttachment(resDomain +followServiceInfos.get(0).getAttachment());
            }
            model.addAttribute("followService", followServiceInfos.get(0));
            studentInfo = studentService.getStudentInfoByStudentNo(followServiceInfos.get(0).getStudentNo());
        }

        if(studentInfo == null){
            logger.error("studentNo error!!!");
//            return "redirect:/followService/list?studentNo="+studentNo;
        }
        AgencyInfo agencyInfo = new AgencyInfo();
        agencyInfo.setEnableStatus(1);
        agencyInfo.setNationId(studentInfo.getNationId().toString());
        List<AgencyInfo> agencyInfos = agencyService.get(agencyInfo);
        model.addAttribute("agencyInfos", agencyInfos);
        List<CurrencyInfo> currencyInfos = currencyInfoService.getList(new CurrencyInfo());
        model.addAttribute("currencyInfos", currencyInfos);
        return "followServiceInfo/detail";
    }

    /**
     * 保存修改
     * @param followServiceInfo
     * @return
     */
    @RequestMapping("/followService/saveData")
    @ResponseBody
    public BaseResponse saveFollowService(FollowServiceInfo followServiceInfo){
        BaseResponse baseResponse = new BaseResponse();
        int result = 0;
        SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        String studentNo = followServiceInfo.getStudentNo();
        followServiceInfo.setStudentNo(StringUtils.hasText(studentNo) ? studentNo : null);
        if(followServiceInfo != null && followServiceInfo.getId() != null){
            FollowServiceInfo followServiceInfo1 = new FollowServiceInfo();
            followServiceInfo1.setDeleteStatus(false);
            followServiceInfo1.setId(followServiceInfo.getId());
            List<FollowServiceInfo> followServiceInfos = followServiceInfoService.getList(followServiceInfo1);
            //监护
            if(!followServiceInfos.isEmpty() && FollowTypeEnum.GUARDIANSHIP.getCode() == followServiceInfos.get(0).getVisitType()){
                if(getVisaRecordResult(studentNo)){
                    baseResponse.setErrorMsg("获签信息已审核通过，监护信息不可以修改！");
                    return baseResponse;
                }
            }
            result = followServiceInfoService.updateById(followServiceInfo);
        }else{
            followServiceInfo.setApplyDate(new Date());
            followServiceInfo.setCreateTime(new Date());
            followServiceInfo.setDeleteStatus(false);
            if(sysUser != null){
                followServiceInfo.setOperatorNo(sysUser.getOaid());
                followServiceInfo.setOperatorName(sysUser.getUsername());
            }
            followServiceInfo.setApplyStatus(FollowServiceStatusEnum.SUBMITTED.getCode());
            result = followServiceInfoService.insert(followServiceInfo);
        }
        baseResponse.setResult(result > 0);
        return baseResponse;
    }

    /**
     * 后续申请结果页
     * @return
     */
    @RequestMapping("/followService/resultList")
    public String resultList(HttpServletRequest request,Model model){
        model.addAttribute("applyId",request.getParameter("applyId"));
        model.addAttribute("studentNo",request.getParameter("studentNo"));
        return "followServiceInfo/resultList";
    }

    /**
     * 申请结果信息页
     * @return
     */
    @RequestMapping("/followService/applicationMessage")
    public String applicationMessage(HttpServletRequest request,Model model){
        Subject currentUser= SecurityUtils.getSubject();
        SysUser user=(SysUser) currentUser.getPrincipal();
        String id = request.getParameter("id") ==null ? "" :request.getParameter("id");
        //1:查看 2:修改
        String type = request.getParameter("type") == null ? "1" :request.getParameter("type");
        String applyId = request.getParameter("applyId");
        FollowServiceResult followServiceResult = followServiceResultService.getFollowServiceResultById(id);
        if(followServiceResult != null && StringUtils.hasText(followServiceResult.getOfferAttachment()) && !followServiceResult.getOfferAttachment().contains(resDomain)){
            followServiceResult.setOfferAttachment(resDomain +followServiceResult.getOfferAttachment());
        }
        logger.info("申请状态:"+followServiceResult.getAuditStatus());
        model.addAttribute("type",type);
        model.addAttribute("date",id);
        model.addAttribute("applyId",applyId);
        model.addAttribute("followServiceResult",followServiceResult);
        model.addAttribute("studentNo",request.getParameter("studentNo"));
        model.addAttribute("resDomain",resDomain);

        // 获取审批数据
        if(Contants.UPDATE_TYPE.equals(type)){
            List<AuditResultInfo> list_case_8 = auditResultService.getList(CaseIdEnum.FollowService.getCode(),followServiceResult.getId());
            model.addAttribute("list_case_8",list_case_8);
            //if(list_case_8 == null || list_case_8.size() == 0){

                AuditApplyInfo auditApplyInfo = auditApplyService.get(Integer.valueOf(id),CaseIdEnum.FollowService.getCode());

//                if (auditApplyInfo != null && user.getOaid().equals(auditApplyInfo.getOaid())){
//
//                }
            //查询登录人是否有审批后续结果的角色,有就可以进行审批
//            if(auditApplyInfo != null && auditApplyService.currUserWithPermission("审批后续结果",userService.getLoginUser())){
//                model.addAttribute("canApprove", true);
                //查询具有'审批后续结果'角色的用户
                List<String> oaids = followServiceResultService.getOaidsByRoleName("审批后续结果");
                if(auditApplyInfo != null && oaids != null && oaids.size() >0){
                    model.addAttribute("auditSysUser","true");
                    if (oaids.contains(user.getOaid())){
                        model.addAttribute("canApprove", true);
                    }
                }else{
                    model.addAttribute("auditSysUser","false");
                }
                List<SysUser> sysUsers = followServiceResultService.getSysUserByRoleName("审批后续结果");
                if(sysUsers != null && sysUsers.size() > 0){
                    model.addAttribute("auditSysUsers",sysUsers);
                }


//            }

           // }
            //待审批人
            //AuditApplyInfo auditApplyInfo_apply = auditApplyService.get(followServiceResult.getId(),CaseIdEnum.FollowService.getCode());
//            if(auditApplyInfo != null){
//                model.addAttribute("toAudit_apply",auditApplyInfo);
//                SysUser sysUser = new SysUser();
//                sysUser.setOaid(auditApplyInfo.getOaid());
//                sysUser.setDeleteStatus(false);
//                List<SysUser> list_user = userService.getList(sysUser);
//                if(list_user.size() > 0){
//                    model.addAttribute("toAudit_apply_name",list_user.get(0).getUsername());
//                }
//            }
        }
        return "followServiceInfo/message";
    }

    /**
     * 审批
     * @param applyId id
     * @param type 通过 / 拒绝
     * @param remark 备注
     * @return
     */
    @RequestMapping(value = "/followService/approve")
    @ResponseBody
    public BaseResponse approve(Integer applyId, Integer type, String remark, String studentNo, String updateTime){
        logger.info(MessageFormat.format(" COE approve: id:{0}; type:{1}; remark:{2}",applyId, type, remark));
        try{
            return followServiceResultService.approve(applyId, type, remark,studentNo,updateTime);
        }catch(Exception e){
            throw new ContentException(1,"审批错误");
        }
    }

    @RequestMapping("/followService/addApplicationMessage")
    public String addApplicationMessage(HttpServletRequest request,Model model){
        model.addAttribute("applyId",request.getParameter("applyId"));
        model.addAttribute("studentNo",request.getParameter("studentNo"));
        return "followServiceInfo/addMessage";
    }

    /**
     * 后续申请列表数据
     * @param followServiceInfo
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping("/followService/list/data")
    @ResponseBody
    public BasePageModel listData(FollowServiceInfo followServiceInfo, PageParam pageParam, BasePageModel basePageModel){
        Page<?> page = pageWapper(pageParam,propList());
        followServiceInfo.setDeleteStatus(false);
        String studentNo = followServiceInfo.getStudentNo();
        followServiceInfo.setStudentNo(StringUtils.hasText(studentNo) ? studentNo : null);
        //后续信息
        List<FollowServiceInfo> followServiceIfos = followServiceInfoService.getList(followServiceInfo);
        Map<Integer, String> agencyMap = new HashMap<>();
        //机构信息
        List<AgencyInfo> agencyInfos = agencyService.get(new AgencyInfo());
        agencyInfos.forEach(agencyInfo -> {
            agencyMap.put(agencyInfo.getId(), agencyInfo.getAgencyName());
        });
        //添加机构名称
        followServiceIfos.forEach(followService -> {
            followService.setAgencyName(agencyMap.get(followService.getAgency()));
        });
        return dataTableWapper(page,basePageModel);
    }

    /**
     * 后续申请结果列表数据
     * @param followServiceResult
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping("/followService/resultList/data")
    @ResponseBody
    public BasePageModel resultListData(FollowServiceResult followServiceResult, PageParam pageParam, BasePageModel basePageModel){
        Page<?> page = pageWapper(pageParam,propList());
        followServiceResult.setDeleteStatus(false);
        List<FollowServiceResult> followServiceInfos = followServiceResultService.getResultList(followServiceResult);
        return dataTableWapper(page,basePageModel);
    }

    /**
     * 新增学生/学校的确认时间
     * @param request
     * @return
     */
    @RequestMapping("/followService/addTime")
    @ResponseBody
    public Boolean addTime(HttpServletRequest request){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String studentTime = request.getParameter("studentTime") == null ? "" : request.getParameter("studentTime")  ;
        String schoolTime = request.getParameter("schoolTime") == null ? "" : request.getParameter("schoolTime");
        String id = request.getParameter("id") == null ? "" :request.getParameter("id");
        FollowServiceResult followServiceResult = new FollowServiceResult();
        followServiceResult.setId(Integer.valueOf(id));
        try{
            if(StringUtils.hasText(studentTime)){
                followServiceResult.setStudentReplyDate(simpleDateFormat.parse(studentTime));
            }
            if(StringUtils.hasText(schoolTime)){
                followServiceResult.setSchoolReplyDate(simpleDateFormat.parse(schoolTime));
            }
        }catch(ParseException e){
           throw new ContentException(1,"时间转换异常");
        }

        if(StringUtils.hasText(id)){
            Integer updateResult = followServiceResultService.update(followServiceResult);
            if(updateResult > 0){
                return true;
            }
        }
        return false;
    }

    @RequestMapping(value = "/followService/save", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse save(@RequestParam("data")String data){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SysUser user = userService.getLoginUser();
        Object obj = JSONArray.parse(data);
        String studentNo = ((JSONObject)obj).getString("studentNo");
        //id
        String id = ((JSONObject)obj).getString("id");
        //申请id
        String applyId = ((JSONObject)obj).getString("applyId") == null ? "" : ((JSONObject)obj).getString("applyId");
        //申请结果
        String resultType = ((JSONObject)obj).getString("resultType") == null ? "1" : ((JSONObject)obj).getString("resultType");
        //申请结果方式
        String replyWay = ((JSONObject)obj).getString("replyWay") == null ? "1" : ((JSONObject)obj).getString("replyWay");
        //回复日期
        String resultDate = ((JSONObject)obj).getString("resultDate")== null  ? "" : ((JSONObject)obj).getString("resultDate");
        //offer附件地址
        String offerAttachment = ((JSONObject)obj).getString("offerAttachment") == null ? "" : ((JSONObject)obj).getString("offerAttachment");
        //截止日期
        String replyDeadline = ((JSONObject)obj).getString("replyDeadline") ;
        //备注
        String resultComment = ((JSONObject)obj).getString("resultComment") == null ? "" : ((JSONObject)obj).getString("resultComment");
        //拒绝原因
        String replyReason = ((JSONObject)obj).getString("reply_reason") == null ? "" :((JSONObject)obj).getString("reply_reason");
        FollowServiceResult followServiceResult = new FollowServiceResult();
        FollowServiceInfo followServiceInfo = new FollowServiceInfo();
        BaseResponse baseResponse = new BaseResponse();
        int updateResult = 0;
        if(Contants.UPDATE_TYPE.equals(resultType)){
            followServiceInfo.setApplyStatus(2);
        }else if(Contants.DELETE_TYPE.equals(resultType)){
            followServiceInfo.setApplyStatus(3);
        }
        try{
            followServiceResult.setResultType(Integer.valueOf(resultType));
            followServiceResult.setReplyWay(Integer.valueOf(replyWay));
            followServiceResult.setOfferAttachment(offerAttachment);
            if(StringUtils.hasText(applyId)){
                followServiceInfo.setId(Integer.valueOf(applyId));
                followServiceResult.setApplyId(Integer.valueOf(applyId));
            }
            if(StringUtils.hasText(resultDate)){
                followServiceResult.setResultDate(simpleDateFormat.parse(resultDate));
            }else{
                followServiceResult.setResultDate(null);
            }
            if(StringUtils.hasText(replyDeadline)){
                followServiceResult.setReplyDeadline(simpleDateFormat.parse(replyDeadline));
            }
            if(StringUtils.hasText(replyReason)){
                followServiceResult.setReplyReason(replyReason);
            }
            followServiceResult.setResultComment(resultComment);
            followServiceResult.setOperatorNo(user.getOaid());
            followServiceResult.setOperatorName(user.getUsername());
            FollowServiceResult followServiceResultOld = followServiceResultService.getFollowServiceResultById(id);
            //修改
            if(StringUtils.hasText(id)){
                //修改之前查看此条记录是否审批通过,通过则不可以修改
                if(followServiceResultOld.getAuditStatus().equals(Contants.APPLYSTATUS_ACCEPT)){
                    baseResponse.setResult(false);
                    baseResponse.setErrorCode("2");
                    baseResponse.setErrorMsg("此记录已通过审核,不可以进行修改!");
                    return baseResponse;
                }
                followServiceResult.setUpdateTime(new Date());
                followServiceResult.setId(Integer.valueOf(id));
                followServiceResultService.updateFollowResult(followServiceResult);
                updateResult = followServiceInfoService.updateById(followServiceInfo);

                //查询有没有审批记录,有就删除
                AuditApplyInfo auditApplyInfo = auditApplyService.get(followServiceResult.getId(),CaseIdEnum.FollowService.getCode());
                if(auditApplyInfo != null){
                    auditApplyInfo.setDeleteStatus(true);
                    auditApplyService.delete(auditApplyInfo.getBusinessId(),auditApplyInfo.getCaseId());
                }
                //若审批拒绝,则再添加一条审批记录(修改后的)
                FollowServiceResult followServiceResultNew = followServiceResultService.getFollowServiceResultById(id);
                if(followServiceResult.getResultType() ==1){
                    auditApplyService.add(followServiceResultNew.getId(), CaseIdEnum.FollowService.getCode(),1,studentNo,"","");
                }
                //以前是拒绝现在变成录取时插一条审批记录
//                else if(followServiceResultOld.getResultType() != 1 && followServiceResultNew.getResultType() ==1){
//                    auditApplyService.add(followServiceResultNew.getId(), CaseIdEnum.FollowService.getCode(),1,studentNo,true);
//                }
                if(updateResult > 0){
                    baseResponse.setResult(true);
                    return baseResponse;
                }
             //新增
            }else{
                followServiceResult.setCreateTime(new Date());
                followServiceResult.setAuditStatus(1);
                followServiceResult.setDeleteStatus(Boolean.valueOf("0"));
                followServiceResult.setOperatorNo(user.getOaid());
                followServiceResult.setOperatorName(user.getUsername());
                followServiceResultService.save(followServiceResult);
                updateResult = followServiceInfoService.updateById(followServiceInfo);
                //添加审批信息
                if(followServiceResult.getResultType() ==1 && followServiceResult.getId() != null){
                    auditApplyService.add(followServiceResult.getId(),CaseIdEnum.FollowService.getCode(),1,studentNo,"","aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                }
                if(updateResult > 0){
                    baseResponse.setResult(true);
                    return baseResponse;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        baseResponse.setResult(false);
        baseResponse.setErrorMsg("操作失败");
        return baseResponse;
    }

    @RequestMapping("followService/studentTime")
    public String studentAddTime(HttpServletRequest request,Model model){
        model.addAttribute("date",request.getParameter("id"));
        return "followServiceInfo/studentTime";
    }

    @RequestMapping("followService/schoolTime")
    public String schoolAddTime(HttpServletRequest request,Model model){
        model.addAttribute("date",request.getParameter("id"));
        return "followServiceInfo/schoolTime";
    }

    @RequestMapping("followService/deletePage")
    public Boolean deletePage(HttpServletRequest request){
        String id = request.getParameter("id");
        String studentNo = request.getParameter("studentNo") == null ? "" :request.getParameter("studentNo");
        FollowServiceInfo followServiceInfo = new FollowServiceInfo();
        if(StringUtils.hasText(id)){
            followServiceInfo.setId(Integer.valueOf(id));
            int deleteResult = followServiceInfoService.deleteFollowServiceInfo(followServiceInfo);
            if (deleteResult > 0){
                return true;
            }
        }
        return false;
    }

    public String[] propList(){
        return new String[]{};
    }

}
