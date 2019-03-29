package com.aoji.controller;

import com.aoji.contants.Contants;
import com.aoji.contants.StudentStatus;
import com.aoji.mapper.BranchInfoMapper;
import com.aoji.mapper.PlanCollegeRecordMapper;
import com.aoji.model.*;
import com.aoji.model.req.PlanCollegeCondition;
import com.aoji.model.req.PlanCollegeReq;
import com.aoji.model.req.SendMessageReq;
import com.aoji.service.*;
import com.aoji.utils.HttpUtils;
import com.aoji.vo.PlanCollegeInfoVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.*;

//import com.aoji.service.PlanCollegeRecordService;

/**
 * author: chenhaibo
 * description: 院校申请计划管理控制类
 * date: 2018/1/25
 */
@Controller
public class PlanCollegeController extends BaseController {
    public static final Logger logger = LoggerFactory.getLogger(PlanCollegeController.class);
    @Autowired
    PlanCollegeInfoService planCollegeInfoService;
    @Autowired
    UserService userService;
    @Autowired
    TransferSendService transferSendService;
    @Autowired
    PlanCollegeRecordMapper planCollegeRecordMapper;
    @Autowired
    RoleService roleService;
    @Autowired
    StudentService studentService;
    @Autowired
    UserTaskRelationService userTaskRelationService;
    @Autowired
    BranchInfoMapper branchInfoMapper;
    @Autowired
    PlanCollegeRecordService planCollegeRecordService;

    @Autowired
    FileService fileService;

    @Value("${insertMessage.enable}")
    private Boolean insertMessageEnable;
    @Value("${insertMessage.url}")
    private String insertMessageUrl;

    @Value("${plan.college.info.url}")
    private String planCollegeInfoUrl;

    @Value("${plan.college.audit.url}")
    private String planCollegeAuditUrl;

    /**
     * 跳转列表页
     *
     * @return
     */
    @RequestMapping("/planCollege/list")
    public String list(Model model) {
        model.addAttribute("planCollegeInfoUrl", planCollegeInfoUrl);
        model.addAttribute("planCollegeAuditUrl", planCollegeAuditUrl);
        return "/planCollege/list";
    }

    /**
     * 列表数据
     *
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping("/planCollege/list/data")
    @ResponseBody
    public BasePageModel listData(PlanCollegeInfoVO planCollegeInfoVO, PageParam pageParam, BasePageModel basePageModel) {
        SysUser sysUser = userService.getLoginUser();
        if (sysUser == null || sysUser.getOaid() == null) {
            return dataTableWapper(new Page<>(), basePageModel);
        }
        planCollegeInfoVO.setCopyManager(SecurityUtils.getSubject().hasRole(Contants.ROLE_VISA_MANAGER));
        if (!StringUtils.hasText(planCollegeInfoVO.getStudentNo())) {
            planCollegeInfoVO.setStudentNo(null);
        }
        if (!StringUtils.hasText(planCollegeInfoVO.getStudentName())) {
            planCollegeInfoVO.setStudentName(null);
        }
        planCollegeInfoVO.setCopyOperatorNo(sysUser.getOaid());
        int planCollegeInfoCount = planCollegeInfoService.getPlanCollegeCount(planCollegeInfoVO);
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength(), false);
        List<PlanCollegeInfoVO> list = planCollegeInfoService.getPlanCollege(planCollegeInfoVO);
        page.setTotal(planCollegeInfoCount);
        return dataTableWapper(page, basePageModel);
    }

    /**
     * 新列表分页数据
     *
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping(value = "/planCollege/list", method = RequestMethod.POST)
    @ResponseBody
    public BasePageModel planCollegeByPage(PlanCollegeInfoVO planCollegeInfoVO, PageParam pageParam, BasePageModel basePageModel) {
        this.pageListPrepare(planCollegeInfoVO);
        if (!StringUtils.hasText(planCollegeInfoVO.getStudentNo())) {
            planCollegeInfoVO.setStudentNo(null);
        }
        if (!StringUtils.hasText(planCollegeInfoVO.getStudentName())) {
            planCollegeInfoVO.setStudentName(null);
        }
        /* =====================标记标记================================ */
        SysUser sysUser = userService.getLoginUser();
        List<String> listRoles = roleService.getRolesByOaId(sysUser.getOaid());
        if(listRoles.contains("规划顾问") || listRoles.contains("规划经理")){
            planCollegeInfoVO.setCurrUserNo(sysUser.getOaid());
            if(listRoles.contains("规划顾问")){
                planCollegeInfoVO.setPlanRole("规划顾问");
            }
            else if(listRoles.contains("规划经理")){
                planCollegeInfoVO.setPlanRole("规划经理");
            }
        }
        else{
            planCollegeInfoVO.setPlanRole("");
        }
        /* =====================标记标记================================ */
        // 重写count
        int planCollegeInfoCount = planCollegeInfoService.getPlanCollegeCount(planCollegeInfoVO);
        Page<?> page = pageWapper3(pageParam, propList());
        List<PlanCollegeInfoVO> planCollegeInfoVOList = this.planCollegeInfoService.planCollegeByPage(planCollegeInfoVO);
        if(planCollegeInfoVOList.size() > 0){
            for(PlanCollegeInfoVO item:planCollegeInfoVOList){
                if(item.getAuditStatus() != null && item.getAuditStatus() == 1){
                    if(StringUtils.hasText(item.getStudentNo())){
                        StudentInfo studentInfo = new StudentInfo();
                        studentInfo = studentService.getStudentInfoByStudentNo(item.getStudentNo());
                        if(studentInfo != null) {
                            //同业学生
                            if(studentInfo.getChannelStatus() != null && studentInfo.getChannelStatus().equals(1)){
                                item.setStudentConfirmStatus(2);
                            }
                        }
                    }
                }
            }
        }
        page.setTotal(planCollegeInfoCount);
        return dataTableWapper(page, basePageModel);

    }


    /**
     * 工作台待确认定校
     *
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping(value = "/planCollege/list/worktable", method = RequestMethod.POST)
    @ResponseBody
    public BasePageModel worktablePlanCollegeByPage(PlanCollegeInfoVO planCollegeInfoVO, PageParam pageParam, BasePageModel basePageModel) {
        this.pageListPrepare(planCollegeInfoVO);
        if (!StringUtils.hasText(planCollegeInfoVO.getStudentNo())) {
            planCollegeInfoVO.setStudentNo(null);
        }
        if (!StringUtils.hasText(planCollegeInfoVO.getStudentName())) {
            planCollegeInfoVO.setStudentName(null);
        }
        /* =====================标记标记================================ */
        SysUser sysUser = userService.getLoginUser();
        List<String> listRoles = roleService.getRolesByOaId(sysUser.getOaid());
        if(listRoles.contains("规划顾问") || listRoles.contains("规划经理")){
            planCollegeInfoVO.setCurrUserNo(sysUser.getOaid());
            if(listRoles.contains("规划顾问")){
                planCollegeInfoVO.setPlanRole("规划顾问");
            }
            else if(listRoles.contains("规划经理")){
                planCollegeInfoVO.setPlanRole("规划经理");
            }
        }
        else{
            planCollegeInfoVO.setPlanRole("");
        }
        /* =====================标记标记================================ */
        // 重写count
        int planCollegeInfoCount = planCollegeInfoService.getPlanCollegeCountByWorktable(planCollegeInfoVO);
        Page<?> page = pageWapper3(pageParam, propList());
        List<PlanCollegeInfoVO> dataList = new ArrayList<>();
        List<PlanCollegeInfoVO> planCollegeInfoVOList = this.planCollegeInfoService.planCollegeByPage(planCollegeInfoVO);
        if(planCollegeInfoVOList.size() > 0){
            for(PlanCollegeInfoVO item:planCollegeInfoVOList){
                if(item.getAuditStatus() != null && item.getAuditStatus() == 1){
                    if(StringUtils.hasText(item.getStudentNo())){
                        StudentInfo studentInfo = studentService.getStudentInfoByStudentNo(item.getStudentNo());
                        if (studentInfo != null) {
                            //同业学生
                            if (studentInfo.getChannelStatus() != null && studentInfo.getChannelStatus().equals(1)) {
                                item.setStudentConfirmStatus(2);
                            }
                            if(!studentInfo.getStatus().equals(StudentStatus.COMPLETED.getCode())) {
                                dataList.add(item);
                            }
                        }
                    }
                }
            }
        }
        basePageModel.setiTotalRecords(planCollegeInfoCount);
        basePageModel.setAaData(dataList);
        basePageModel.setiTotalDisplayRecords(planCollegeInfoCount);
//        page.setTotal(planCollegeInfoCount);
        return basePageModel;
//        return dataTableWapper(page, basePageModel);
    }

    /**
     * 分页准备数据
     * 去掉了运行时判断当前用户非空的判断，如果用户为空，shiro会拦截跳转
     */
    private void pageListPrepare(PlanCollegeInfoVO planCollegeInfoVO) {

        SysUser sysUser = userService.getLoginUser();

        planCollegeInfoVO.setCopyManager(SecurityUtils.getSubject().hasRole(Contants.ROLE_VISA_MANAGER));

        planCollegeInfoVO.setCopyOperatorNo(sysUser.getOaid());
    }

    /**
     * 排序列表
     *
     * @return
     */
    public String[] propList() {
        return new String[]{""};
    }

    /**
     * 修改状态
     *
     * @return
     */
    @RequestMapping("/planCollege/updateStatus")
    @ResponseBody
    public Boolean updateStatus(PlanCollegeReq req) {
        logger.info("PlanCollegeReq : " + req.toString());
        try {
            Boolean result = planCollegeInfoService.updateAndInsertApplyInfo(req);
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * 分配外联
     *
     * @param req
     * @return
     */
    @RequestMapping("/planCollege/assign")
    @ResponseBody
    public Boolean assign(PlanCollegeReq req) {
        logger.info("PlanCollegeReq : " + req.toString());
        try {
            Boolean result = planCollegeInfoService.assignConnector(req);
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * 分配外联 -- 院校申请入口
     * @return
     */
    @RequestMapping("/apply/assign")
    @ResponseBody
    public Boolean assignFofApply(Integer applyId, String oaid, String connector) {
        try {
            Boolean result = planCollegeInfoService.assignConnector(applyId, oaid, connector);
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * 外联顾问
     *
     * @param model
     * @return
     */
    @RequestMapping("/planCollege/connector")
    public String selectConnector(Model model) {
        //外联顾问
        List<SysUser> users = userService.getByRoleName(Contants.ROLE_OUTREACH_CONSULTANT);
        //外联经理
        List<SysUser> users1 = userService.getByRoleName(Contants.ROLE_OUTREACH_MANAGER);
        users.addAll(users1);
        // 定义一个中文排序器
        Comparator c = Collator.getInstance(Locale.CHINA);
        Collections.sort(users, (u1, u2) -> {
            return c.compare(u1.getUsername(), u2.getUsername());
        });

        model.addAttribute("users", users);
        return "/planCollege/connector";
    }

    /**
     * 原因列表
     *
     * @param model
     * @return
     */
    @RequestMapping("/planCollege/reasonList")
    public String selectReason(Model model) {
        return "/planCollege/reasonList";
    }

    /**
     * 规划经理（顾问）审核定校方案，只限美高
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/plan/planAuditByPlanningConsultant", method = RequestMethod.POST)
    @ResponseBody
    public Integer auditPlanCollege(Integer id ,String studentNo) {
        SysUser sysUser = userService.getLoginUser();
        PlanCollegeInfo planCollegeInfo0 = new PlanCollegeInfo();
        planCollegeInfo0.setId(id);
        PlanCollegeInfo planCollegeInfo = planCollegeInfoService.get(planCollegeInfo0);
        if(planCollegeInfo != null) {
            planCollegeInfo.setAuditStatus(6);
            planCollegeInfo.setStudentConfirmStatus(1);
            planCollegeInfo.setUpdateTime(new Date());
            Integer success = planCollegeInfoService.update(planCollegeInfo);

            PlanCollegeRecord planCollegeRecord = new PlanCollegeRecord();
//            planCollegeRecord.setRejectReason("规划经理（顾问）审核定校方案");
            //1-文签，2-小希学生确认，3-运营学生确认，4-规划已审核，5-销售
            planCollegeRecord.setType(4);
            planCollegeRecord.setResult(1);
            planCollegeRecord.setPlanCollegeId(id);
            planCollegeRecord.setCreateTime(new Date());
            planCollegeRecord.setDeleteStatus(false);
            planCollegeRecord.setOperatorNo(sysUser.getOaid());
            planCollegeRecord.setOperatorName(sysUser.getUsername());
            planCollegeRecord.setUpdateTime(new Date());
            planCollegeRecord.setCanConfirmTime(new Date());

            if(StringUtils.hasText(studentNo)) {
            StudentInfo studentInfo = studentService.getStudentInfoByStudentNo(studentNo);
            SendMessageReq sendMessageReq = new SendMessageReq();
                if(StringUtils.hasText(studentInfo.getCopyOperatorNo())) {
                    sendMessageReq.setOaid(studentInfo.getCopyOperatorNo());
                }
                if(sysUser!= null){
                    sendMessageReq.setOperatorNo(sysUser.getOaid());
                }
                sendMessageReq.setTemplateCode("collegeConfirmed");
                sendMessageReq.setStudentNo(studentNo);
                sendMessageReq.setTaskType(2);
                Map<String, String> map = new HashMap<String, String>();
                map.put("studentNo", studentInfo.getStudentNo());
                map.put("studentName", studentInfo.getStudentName());
                map.put("collegeName", planCollegeInfo.getCollegeName());
                sendMessageReq.setTemplateParam(map);
                userTaskRelationService.sendMessage(sendMessageReq);


                //小希学生端消息接口
                if(success>0) {
                    if(insertMessageEnable){
                        Date now = new Date();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String message = String.format("studentNoOrResourceNo=%s&type=%s&taskType=%s&title=%s&content=%s&messageTime=%s", studentNo,"1", "4", "定校方案", "您好。您的顾问已经把定校方案发给您了，赶快去确认吧", simpleDateFormat.format(now));
                        String s = HttpUtils.sendPost2(insertMessageUrl, message);
                        Integer i = 1;
                    }
                }
            }
            return planCollegeRecordMapper.insert(planCollegeRecord);
        }
        return -1;
    }


    /**
     * 学生确认
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/plan/studentConfirm", method = RequestMethod.POST)
    @ResponseBody
    public Integer studentConfirm(Integer id,String studentNo) {
        if(id != null && studentNo != null) {
            return planCollegeInfoService.studentConfirm(id, studentNo);
        }else{
            return -1;
        }
    }

    /**
     * 定校列表 -- 院校申请页查看
     * @return
     */
    @RequestMapping("/planCollege/planList")
    public String Planlist() {
        return "/planCollege/planList";
    }

    /**
     * 列表数据
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping("/planCollege/planList/data")
    @ResponseBody
    public BasePageModel listData(PlanCollegeCondition planCollegeCondition, PageParam pageParam, BasePageModel basePageModel) {
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength(), true);
        planCollegeInfoService.queryPlanCollegeForVisa(planCollegeCondition);
        return dataTableWapper(page, basePageModel);
    }

    @RequestMapping("/selectStudentPlanCollegeDetails")
    public  String  selectStudentPlanCollegeDetails(PlanCollegeCondition planCollegeCondition,Integer planCollegeId,Model model){
        if(planCollegeId==null){
            return "planCollege/planCollegeDetails";
        }
        planCollegeCondition.setCollegeId(planCollegeId);
        logger.info("院校Id:"+planCollegeId);
        List<PlanCollegeInfoVO> planCollegeInfoVOS = planCollegeInfoService.queryPlanCollegeForVisa(planCollegeCondition);
        if(planCollegeInfoVOS==null  || planCollegeInfoVOS.size()<1){
            return "planCollege/planCollegeDetails";
        }

        if(StringUtils.hasText(planCollegeInfoVOS.get(0).getConfirmFile())){
            String privateUrl = fileService.getPrivateUrl(planCollegeInfoVOS.get(0).getConfirmFile());
            planCollegeInfoVOS.get(0).setConfirmFile(privateUrl);
            logger.info("文件设置私密访问："+planCollegeInfoVOS.get(0).getConfirmFile());
        }
        model.addAttribute("planCollegeInfo",planCollegeInfoVOS.get(0));
        return "planCollege/planCollegeDetails";
    }

    /**
     * 定校方案审批信息列表
     * @return
     */
    @RequestMapping("/planCollege/AuditResultList")
    public String AuditResultList(Integer planCollegeId,Model model) {
        model.addAttribute("planCollegeId",planCollegeId);
        return "/planCollege/planCollegeAuditResultList";
    }

    /**
     * 定校方案审批信息列表数据
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping("/planCollege/getAuditResultList")
    @ResponseBody
    public BasePageModel getAuditResultList(Integer planCollegeId, PageParam pageParam, BasePageModel basePageModel) {
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength(), true);
        PlanCollegeRecord planCollegeRecord = new PlanCollegeRecord();
        planCollegeRecord.setPlanCollegeId(planCollegeId);
        planCollegeRecord.setDeleteStatus(false);
        planCollegeRecordService.getList(planCollegeRecord);
        return dataTableWapper(page, basePageModel);
    }


}
