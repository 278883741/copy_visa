package com.aoji.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.Contants;
import com.aoji.mapper.SysRoleMapper;
import com.aoji.model.*;
import com.aoji.service.*;
import com.aoji.vo.UserTaskRelationVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangjunqiang
 * @description 工作台
 * @date Created in 下午5:07 2017/11/21
 */
@Controller
public class WorkTableController extends BaseController{

    @Autowired
    UserTaskRelationService userTaskRelationService;

    @Autowired
    NewsInfoService newsInfoService;

    @Autowired
    AuditApplyService auditApplyService;
    @Autowired
    UserService userService;
    @Autowired
    VisaApplyService visaApplyService;
    @Autowired
    VisaRecordService visaRecordService;

    @Value("${plan.college.info.url}")
    private String planCollegeInfoUrl;

    @Value("${plan.college.audit.url}")
    private String planCollegeAuditUrl;

    /**
     * 获取工作台信息
     * @param model
     * @return
     */
    @RequestMapping("/workTable")
    public String workTable(Model model){
        Subject currentUser= SecurityUtils.getSubject();
        SysUser user=(SysUser) currentUser.getPrincipal();
        String oaid = user.getOaid();
        int pageIndex = 0;
        userTaskRelationService.getMessageList(oaid,model,pageIndex);
        model.addAttribute("oaid",oaid);
        model.addAttribute("pageIndex",pageIndex);
        return "workTable/list";
    }


    /**
     * 警示/审批/工作消息
     * @param data
     * @param model
     * @return
     */
    @RequestMapping(value ="/workTable/workJson",method = RequestMethod.POST)
    public String workJson(@RequestParam("data")String data,Model model){
        Object obj = JSONArray.parse(data);
        //oaid
        String oaid = ((JSONObject)obj).getString("oaid");
        //获取消息类型
        String taskType = ((JSONObject)obj).getString("taskType");
        //开始页数
        int pageIndex = ((JSONObject)obj).getInteger("pageIndex");
        List<UserTaskRelationVO> userTaskRelationList = userTaskRelationService.getTypeList(oaid,taskType,pageIndex);
        model.addAttribute("pageIndex",pageIndex);
        if(taskType.equals(Contants.WARNING_MESSAGE+"")){
            model.addAttribute("warningList",userTaskRelationList);
            return "workTable/warningJson";
        }else if(taskType.equals(Contants.APPROVAL_MESSAGE+"")){
            model.addAttribute("approvalList",userTaskRelationList);
            return "workTable/approvalJson";
        }else if(taskType.equals(Contants.WORK_MESSAGE+"")){
            model.addAttribute("workList",userTaskRelationList);
            return "workTable/workJson";
        }
        return "";
    }

    /**
     * 公告
     * @param data
     * @param model
     * @return
     */
    @RequestMapping("/workTable/newsInfo")
    public String newsInfo(@RequestParam("data")String data,Model model){
        Object obj = JSONArray.parse(data);
        //开始页数
        int pageIndex = ((JSONObject)obj).getInteger("pageIndex");
        List<NewsInfo> newsInfoList = newsInfoService.newsInfoList(pageIndex);
        model.addAttribute("pageIndex",pageIndex);
        model.addAttribute("newsInfoList",newsInfoList);
        return "workTable/newsInfoJson";
    }

    /**
     * 更改阅读状态
     * @param request
     * @return
     */
    @RequestMapping("/workTable/updateRead")
    @ResponseBody
    public Boolean updateRead(HttpServletRequest request){
        String id = request.getParameter("id");
        return userTaskRelationService.read(Integer.valueOf(id));
    }

    /**
     * 跳转到"我提交的-待审批的签证申请"页面
     * @return
     */
    @RequestMapping("/workTable/visaApplyPage")
    public String visaApplyPage(){
        return "workTable/visaApplyList";
    }

    /**
     * 跳转到"我提交的-待审批的签证结果"页面
     * @return
     */
    @RequestMapping("/workTable/visaResultPage")
    public String visaResultPage(){
        return "workTable/visaResultList";
    }

    /**
     * 跳转到"我提交的-待审批的获签信息"页面
     * @return
     */
    @RequestMapping("/workTable/visaRecordPage")
    public String visaRecordPage(){
        return "workTable/visaRecordList";
    }

    /**
     * 跳转到"我提交的-待审批的停办申请"页面
     * @return
     */
    @RequestMapping("/workTable/studentDelayPage")
    public String studentDelayPage(){
        return "workTable/studentDelayList";
    }

    /**
     * 跳转到"我提交的-待审批的结案申请"页面
     * @return
     */
    @RequestMapping("/workTable/studentSettlePage")
    public String studentSettlePage(){
        return "workTable/studentSettleList";
    }

    /**
     * 跳转到"我提交的-待审批的结案申请"页面
     * @return
     */
    @RequestMapping("/workTable/studentApplyResultPage")
    public String studentApplyResultPage(){
        return "workTable/applyResultList";
    }

    /**
     * 跳转到"待接受的定校信息"页面
     * @return
     */
    @RequestMapping("/workTable/schoolConfirmPage")
    public String schoolConfirmPage(Model model){
        model.addAttribute("planCollegeInfoUrl", planCollegeInfoUrl);
        model.addAttribute("planCollegeAuditUrl", planCollegeAuditUrl);
        return "workTable/schoolConfirmList";
    }

    /**
     * 跳转到"待我审批的 - 签证申请"页面
     * @return
     */
    @RequestMapping("/workTable/toAuditVisaApplyPage")
    public String toAuditVisaApplyPage(){
        return "workTable/toAuditVisaApplyList";
    }

    /**
     * 跳转到"待我审批的 - 获签信息"页面
     * @return
     */
    @RequestMapping("/workTable/toAuditVisaRecordPage")
    public String toAuditVisaRecordPage(){
        return "workTable/toAuditVisaRecordList";
    }

    /**
     * 跳转到"待我审批的-COE/CAS/I-20"页面
     * @return
     */
    @RequestMapping("/workTable/toAuditCOEPage")
    public String toAuditCOEPage(){
        return "workTable/toAuditCOEList";
    }

    /**
     * 跳转到"审批人清单"页面
     * @return
     */
    @RequestMapping("/workTable/toAuditUserList")
    public String toAuditUserList(){
        return "workTable/auditUserList";
    }

    @RequestMapping(value = "/workTable/auditUser/query", method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel queryUserList(PageParam pageParam, BasePageModel basePageModel){
        String[] str = new String[]{"caseId","username","path"};
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart()/pageParam.getiDisplayLength()+1, pageParam.getiDisplayLength());
        auditApplyService.queryAuditUser();
        pageParam.setSortPro(str[pageParam.getiSortCol_0()]);
        return dataTableWapper(page,basePageModel);
    }

    /**
     * 跳转到"查看待审批信息"页面
     * @return
     */
    @RequestMapping("/workTable/checkAuditPage")
    public String checkAuditPage(){
        return "workTable/checkAudit";
    }

    @RequestMapping(value = "/checkAudit/query", method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel get(String oaid, PageParam pageParam, BasePageModel basePageModel) {
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength());
        String[] str = new String[]{"student_no", "student_name", "create_time", "case_id"};
        AuditApplyInfo auditApplyInfo = new AuditApplyInfo();
        auditApplyInfo.setDeleteStatus(false);
        auditApplyInfo.setOaid(oaid);
        auditApplyService.getList(auditApplyInfo);
        pageParam.setSortPro(str[pageParam.getiSortCol_0()]);
        basePageModel.setAaData(page);
        basePageModel.setiTotalDisplayRecords((int) page.getTotal());
        basePageModel.setiTotalRecords((int) page.getTotal());
        return basePageModel;
    }

    /**
     * 跳转到"我提交的-审批未通过的签证申请"页面
     * @return
     */
    @RequestMapping("/workTable/toUnPassVisaApplyPage")
    public String toUnPassVisaApplyPage(){
        return "workTable/unPassVisaApply";
    }
    @RequestMapping(value = "/unPassVisaApply/query", method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel getUnPassVisaApply(PageParam pageParam, BasePageModel basePageModel) {
        SysUser sysUser = userService.getLoginUser();
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength());
        List<Map<String, Object>> list = visaApplyService.getUnPassVisaApplyList(sysUser.getOaid());
        basePageModel.setAaData(page);
        basePageModel.setiTotalDisplayRecords((int) page.getTotal());
        basePageModel.setiTotalRecords((int) page.getTotal());
        return basePageModel;
    }

    /**
     * 跳转到"我提交的-审批未通过的获签信息"页面
     * @return
     */
    @RequestMapping("/workTable/toUnPassVisaRecordPage")
    public String toUnPassVisaRecordPage(){
        return "workTable/unPassVisaRecord";
    }
    @RequestMapping(value = "/unPassVisaRecord/query", method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel getUnPassVisaRecord(PageParam pageParam, BasePageModel basePageModel) {
        SysUser sysUser = userService.getLoginUser();
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength());
        List<Map<String, Object>> list = visaRecordService.getUnPassVisaRecordList(sysUser.getOaid());
        basePageModel.setAaData(page);
        basePageModel.setiTotalDisplayRecords((int) page.getTotal());
        basePageModel.setiTotalRecords((int) page.getTotal());
        return basePageModel;
    }
}
