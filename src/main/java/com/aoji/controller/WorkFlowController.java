package com.aoji.controller;

import com.aoji.contants.CaseIdEnum;
import com.aoji.model.*;
import com.aoji.service.UserGroupService;
import com.aoji.service.WorkflowService;
import com.aoji.vo.WorkFlowVo;
import com.aoji.vo.WorkflowNodeInfoVo;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 审批人员管理
 * yanjun
 * 2018年5月10号
 */

@Controller
@RequestMapping("/workflow")
public class WorkFlowController extends BaseController {

    @Autowired
    private WorkflowService workflowService;

    @Autowired
    private UserGroupService userGroupService;

    /**
     * 页面跳转
     *
     * @return
     */
    @GetMapping("/list")
    public String workTable(Model model) {

        List<UserGroup> group = this.userGroupService.getUserGroup(null);

        model.addAttribute("uGroup", group);

        return "workflow/list";
    }

    /**
     * 页面跳转
     *
     * @return
     */
    @GetMapping("/add")
    public String toAddWorkFlowPage(Model model) {

        List<UserGroup> group = this.userGroupService.getUserGroup(null);

        List<CaseIdEnum> caseList = CaseIdEnum.getList(CaseIdEnum.class);

        model.addAttribute("uGroup", group);

        model.addAttribute("caseList", caseList);

        return "workflow/add";
    }

    /**
     * 页面跳转
     *
     * @return
     */
    @GetMapping("/addWorkFlowNodeInfo/{id}")
    public String toAddWorkFlowNodeInfoPage(@PathVariable(value = "id") Integer id, Model model) {

//        model.addAttribute("flowId", id);
//
//        //  展示用户组
//        List<UserGroup> group = this.userGroupService.getUserGroup(null);
//        model.addAttribute("uGroup", group);
//
//        // 查询当前审批流程
//        WorkflowInfo w = this.workflowService.selectWorkFlowById(id);
//        model.addAttribute("workflowInfo", w);
//
//        //场景id
//        List<CaseIdEnum> caseList = CaseIdEnum.getList(CaseIdEnum.class);
//        model.addAttribute("caseList", caseList);
//
//        // 查询当前用户组下的成员
//        List<Map<String, Object>> ulist = this.userGroupService.getUserByUserGroupId(w.getUserGroup());
//        model.addAttribute("ulist", ulist);
//
//        //查询当前状态下已经添加的审批人
//        List<WorkflowNodeInfo1> wlist = this.workflowService.getWorkFlowPeople(id);
//        model.addAttribute("wlist", wlist);
        return "workflow/addWorkFlowNodeInfo";
    }

    /**
     * 页面跳转
     *
     * @return
     */
    @GetMapping("/workpList/{id}")
    public String toAddWorkpPage(Model model, @PathVariable("id") Integer id) {


        model.addAttribute("id", id);
        return "workflow/workpList";
    }

    /**
     * datateablsee 列表
     *
     * @param request
     * @param basePageModel
     * @return
     */
    @ResponseBody
    @PostMapping("/list")
    public BasePageModel getWorkFlowList(HttpServletRequest request, WorkFlowVo workFlowVo, BasePageModel basePageModel) {


        Page<?> page = pageWapper2(workFlowVo, propList());

        List<WorkFlowVo> list = this.workflowService.getWorkFlowList(workFlowVo);


        return dataTableWapper(page, basePageModel);
    }

    /**
     * datateablsee 列表
     *
     * @param request
     * @param basePageModel
     * @return
     */
    @ResponseBody
    @PostMapping("/workpList")
    public BasePageModel getWorkflowNodeInfoList(HttpServletRequest request, WorkflowNodeInfoVo workflowNodeInfo, BasePageModel basePageModel) {


        Page<?> page = pageWapper2(workflowNodeInfo, propList());

        List<Map<String, Object>> list = this.workflowService.getWorkflowNodeInfoList(workflowNodeInfo);


        return dataTableWapper(page, basePageModel);
    }

    /**
     * 添加流程前的校验
     *
     * @return
     */
    @PostMapping("addCheck")
    @ResponseBody
    public Map<String, Object> addCheck(WorkflowInfo workflowInfo) {
        return this.workflowService.addCheck(workflowInfo);
    }

    /**
     * 添加流程
     *
     * @return
     */
    @PostMapping("add")
    @ResponseBody
    public Boolean add(WorkflowInfo workflowInfo) {
        return this.workflowService.add(workflowInfo) > 0;
    }

    /**
     * 删除审批人员
     *
     * @return
     */
    @PostMapping("/delete/workflow")
    @ResponseBody
    public Boolean deleteWorkflowInfo(Integer id) {

        return this.workflowService.deleteWorkflowInfo(id) > 0;
    }

    /**
     * 删除审批人员
     *
     * @return
     */
    @PostMapping("/delete/workflowNodeInfo")
    @ResponseBody
    public Boolean deleteWorkflowNodeInfo(Integer id) {

        return this.workflowService.deleteWorkflowNodeInfo(id) > 0;
    }

    /**
     * 删除审批人员
     *
     * @return
     */
    @PostMapping("/addWorkFlowNodeInfo")
    @ResponseBody
    public Boolean addWorkFlowNodeInfo(WorkflowNodeInfo1 workflowNodeInfo1) {

        return this.workflowService.addWorkFlowNodeInfo(workflowNodeInfo1) > 0;
    }


    private String[] propList() {
        return new String[]{"id", "last_visit_time"};
    }
}
