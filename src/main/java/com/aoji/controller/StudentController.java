package com.aoji.controller;

import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.CaseIdEnum;
import com.aoji.contants.Contants;
import com.aoji.contants.StudentStatus;
import com.aoji.mapper.*;
import com.aoji.model.*;
import com.aoji.model.res.Consultor;
import com.aoji.model.res.StudentInfoRes;
import com.aoji.service.*;
import com.aoji.utils.HttpUtils;
import com.aoji.vo.ApplyCollegeVo;
import com.aoji.vo.StudentDetailVO;
import com.aoji.vo.StudentInfoVo;
import com.aoji.vo.StudentProcessStatusVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class StudentController extends BaseController {
    @Autowired
    StudentService studentService;

    @Autowired
    CountryService countryService;

    @Autowired
    BranchService branchService;

    @Autowired
    StudentDelayService studentDelayService;

    @Autowired
    AuditApplyService auditApplyService;

    @Autowired
    VisitInfoMapper visitInfoMapper;

    @Autowired
    ConfirmRecordService confirmRecordService;

    @Autowired
    UserService userService;

    @Autowired
    AuditResultService auditResultService;

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    StudentDelayCancelService studentDelayCancelService;

    @Autowired
    StudentDelayCancelMapper studentDelayCancelMapper;

    @Autowired
    StudentSettleService studentSettleService;
    @Autowired
    UserTaskRelationService userTaskRelationService;

    @Autowired
    VisaResultService visaResultService;

    @Autowired
    PcPapersFileInfoMapper pcPapersFileInfoMapper;

    @Autowired
    PcPapersTypeInfoMapper pcPapersTypeInfoMapper;

    @Autowired
    StudentSettleInfoMapper studentSettleInfoMapper;

    @Autowired
    AuditResultInfoMapper auditResultInfoMapper;

    @Autowired
    private CommissionManageService commissionManageService;

    @Autowired
    CoeApplyService coeApplyService;

    @Autowired
    PrivateOperationService privateOperationService;

    @Value("${student.detail.url}")
    private String studentDetailUrl;

    @Value("${upload.ks3.resDomain}")
    private String resDomain;

    @Value("${plan.college.info.url}")
    private String planCollegeInfoUrl;

    @Value("${plan.college.list.url}")
    private String planCollegeListUrl;

    @Value("${student.Revisit.url}")
    private String revisitUrl;

    @Value("${sign.callCenter.win.url}")
    private String signCallCenterWinUrl;

    @Value("${sign.callCenter.mac.url}")
    private String signCallCenterMacUrl;

    @Autowired
    PlanCollegeInfoService planCollegeInfoService;

    @Autowired
    ApplyCollegeService applyCollegeService;

    @Autowired
    VisitInfoService visitInfoService;

    @Autowired
    SupplementInfoService supplementInfoService;

    @Autowired
    ApplyResultService applyResultService;

    private Logger logger = LoggerFactory.getLogger(StudentController.class);
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";


    /**
     * Excel 文件下载（下载全部信息，展示内容包含列表当中所有字段）
     *
     * @param response
     */
    @RequestMapping(value = "/student/excel",method = RequestMethod.GET)
    public void excel(HttpServletRequest request, HttpServletResponse response) {

        this.studentService.excel(request, response);

    }

    @RequestMapping("/studentInfo")
    public String list(CountryInfo countryInfo, BranchInfo branchInfo, Model model, HttpServletRequest request) {
        SysUser user = userService.getLoginUser();
        String oaid = request.getParameter("oaid");
        String status = request.getParameter("status");
        model.addAttribute("status", status);
        model.addAttribute("oaid", oaid);
        List<CountryInfo> countryInfoList = countryService.getList(countryInfo);
        List<CountryInfo> countryInfoNew = new ArrayList<>();
        for (CountryInfo item : countryInfoList) {
            if (item.getCountryBussid() != 100010 && item.getCountryBussid() != 100011) {
                countryInfoNew.add(item);
            }
        }
        model.addAttribute("countryInfoList", countryInfoNew);
        List<BranchInfo> branchInfoList = branchService.getList(branchInfo);
        model.addAttribute("branchInfoList", branchInfoList);
        HttpSession session = request.getSession();
        if (user != null) {
            session.setAttribute("functions", userService.getFunctionByName(user.getOaid()));
        }
        List<String> roleNames = new ArrayList<String>();
        roleNames.add("外联顾问");
        roleNames.add("外联经理");
        boolean[] roleName = SecurityUtils.getSubject().hasRoles(roleNames);
        if (roleName[0] == false && roleName[1] == false) {
            model.addAttribute("roleName", false);
        } else {
            model.addAttribute("roleName", true);
        }
        return "studentInfo/list";
    }

    @RequestMapping(value = "studentInfo_query", method = RequestMethod.GET)
    @ResponseBody
    @Deprecated
    public BasePageModel get(StudentInfo studentInfo, PageParam pageParam, BasePageModel basePageModel) {
        SysUser sysUser = userService.getLoginUser();
        String roleName = "";
        if (sysUser.getOaid() != null) {
            studentInfo.setCopyOperatorNo(sysUser.getOaid());
            studentInfo.setCopyMakerNo(sysUser.getOaid());
            studentInfo.setCopyNo(sysUser.getOaid());
            studentInfo.setCopierNo(sysUser.getOaid());
            studentInfo.setVisaOperatorNo(sysUser.getOaid());
            //查询oaid对应的角色
            List<String> roles = sysRoleMapper.getRoleByOaId(sysUser.getOaid());
            if (roles != null && roles.size() == 1) {
                roleName = roles.get(0);
            } else if (roles.size() > 1) {
                if (roles.contains("总经理")) {
                    roleName = "总经理";
                } else if (roles.contains("文签总监")) {
                    roleName = "文签总监";
                } else if (roles.contains("文签副总监")) {
                    roleName = "文签副总监";
                } else if (roles.contains("文案经理")) {
                    roleName = "文案经理";
                } else if (roles.contains("外联经理")) {
                    roleName = "外联经理";
                } else if (roles.contains("外联顾问")) {
                    roleName = "外联顾问";
                } else if (roles.contains("文案顾问")) {
                    roleName = "文案顾问";
                }
            }
        }
        studentInfo.setDeleteStatus(false);
        //根据登录用户获取用户组
        Boolean isChannelStatus = userService.isChannelStaus();

        int listCount = studentService.getListCount(studentInfo, roleName, isChannelStatus);

        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength(), false);

        List<StudentInfo> studentInfos = studentService.getList(studentInfo, roleName);

        PageInfo<StudentInfo> pageInfo = new PageInfo<StudentInfo>(studentInfos);

        for (StudentInfo studentInfoResult : studentInfos) {
            if (!StringUtils.isEmpty(studentInfoResult.getBirthday())) {
                studentInfoResult.setBirthdayString(new SimpleDateFormat(Contants.datePattern).format(studentInfoResult.getBirthday()));
            }
            if (!StringUtils.isEmpty(studentInfoResult.getSignDate())) {
                studentInfoResult.setSignDateString(new SimpleDateFormat(Contants.datePattern).format(studentInfoResult.getSignDate()));
            }
        }

        page.setTotal(listCount);

//        pageParam.setSortPro(str[pageParam.getiSortCol_0()]);
//        pageParam.setiSortingCols("合同编号");
//        pageParam.setiSortCol_0(6);
        basePageModel.setAaData(page);
        basePageModel.setiTotalDisplayRecords((int) page.getTotal());
        basePageModel.setiTotalRecords((int) page.getTotal());
        return basePageModel;
    }

    /**
     * datatables 列表展示页
     *
     * @param studentInfo
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping(value = "studentInfo/list", method = RequestMethod.POST)
    @ResponseBody
    public BasePageModel getStudentINfoList(HttpServletRequest request, StudentInfoVo studentInfo, PageParam pageParam, BasePageModel basePageModel) {

        /**
         * 此方法不放入service原因是 PageHelper 会将下一条sql 做 count 处理，并记录给 page对象，所以分页总数数据不用考虑
         */
        String loginUserByRoleName = this.userService.getLoginUserByRoleName(studentInfo);

        //根据登录用户获取用户组
        Boolean isChannelStatus = userService.isChannelStaus();

        //根据登录用户查询改用户是否是规划经理
        Boolean isPlannManager = userService.isPlannManager();

        Page<?> page = pageWapper2(studentInfo, propList());

        List<StudentInfo> goodsSerialList = this.studentService.getStudentINfoList(request, studentInfo, loginUserByRoleName,isChannelStatus,isPlannManager);

        return dataTableWapper(page, basePageModel);
    }

    /**
     * 跳转学生信息详情页
     *
     * @param studentNo
     * @param model
     * @return
     */
    @RequestMapping("/student/detail")
    public String toDetailPage(String studentNo, Model model) {
        if(!StringUtils.hasText(studentNo)){
            return "/studentInfo/detail";
        }
        SysUser sysUser = userService.getLoginUser();
        //获取数据
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(studentNo);
        studentInfo.setDeleteStatus(false);
        studentInfo = studentService.get(studentInfo);
        if (studentInfo != null) {
            model.addAttribute("currentStatus", studentInfo.getStatus());
            model.addAttribute("studentInfo", studentInfo);
        }

        List<String> serviceCodes = studentService.getServiceByStudentNo(studentNo);

        List<StudentProcessStatusVO> studentProcessStatusVOS = StudentStatus.getStatusByServiceCode(serviceCodes);

        model.addAttribute("studentProcessStatusVOS", studentProcessStatusVOS);

        //学生信息
        String json = HttpUtils.doGet(MessageFormat.format(studentDetailUrl, studentNo));
        logger.info("StudentDetail RS : " + json);
        StudentInfoRes studentInfoRes = (StudentInfoRes) JSONObject.parseObject(json, StudentInfoRes.class);
        if (studentInfoRes != null && studentInfoRes.getState() == 0) {
            StudentDetailVO studentDetailVO = studentInfoRes.getData();
            BranchInfo branchInfo = new BranchInfo();
            try {
                branchInfo.setBranchId(Integer.valueOf(studentDetailVO.getBranch()));
                List<BranchInfo> branchInfos = branchService.getList(branchInfo);
                if (!branchInfos.isEmpty()) {
                    studentDetailVO.setBranchName(branchInfos.get(0).getBranchName());
                }
                CountryInfo countryInfo = new CountryInfo();
                countryInfo.setCountryBussid(Integer.valueOf(studentDetailVO.getNation()));
                List<CountryInfo> countryInfos = countryService.getList(countryInfo);
                if (!countryInfos.isEmpty()) {
                    studentDetailVO.setNationName(countryInfos.get(0).getCountryName());
                }
                Consultor consultor = studentService.getConsultorByStudentNo(studentNo);
                if (consultor != null) {
                    studentDetailVO.setConsultorName(consultor.getConsultorname());
                }
            } catch (NumberFormatException e) {
                logger.error(e.getMessage());
            }
            model.addAttribute("studentDetail", studentDetailVO);
        }
        return "/studentInfo/detail";
    }

    @PostMapping(value = "/student/getCallCenterUrl")
    @ResponseBody
    public String getCallcenterUrl(String studentNo, String platFrom){
        String callCenterBaseUrl;
        if(StringUtils.hasText(studentNo)){
            if(Contants.PLAT_FROM_WINDOW.equals(platFrom)){
                callCenterBaseUrl = signCallCenterWinUrl;
            }else if(Contants.PLAT_FROM_MAC.equals(platFrom)){
                callCenterBaseUrl = signCallCenterMacUrl;
            }else{
                return null;
            }
            SysUser sysUser = userService.getLoginUser();
            Map<String,String> phone = studentService.GetPhoneNumByStudentNo(studentNo);
            if(phone != null && StringUtils.hasText(phone.get("phone"))) {
                String callCenterUrl = MessageFormat.format(callCenterBaseUrl, phone.get("phone"), sysUser.getOaid(), studentNo);
                logger.info("CC地址获取："+callCenterUrl);
                return callCenterUrl;
            }
        }
        return null;
    }

    @PostMapping(value = "/student/getCallCenterUrl/new")
    @ResponseBody
    public String getCallcenterUrlNew(String studentNo, String studentName){
        return studentService.getCallCenterUrl(studentNo, studentName);
    }

    @PostMapping(value = "/student/checkPhoneNumber")
    @ResponseBody
    public String checkPhoneNumber(String studentNo){
        if(StringUtils.hasText(studentNo)){
            SysUser sysUser = userService.getLoginUser();
            Map<String,String> phone = studentService.GetPhoneNumByStudentNo(studentNo);
            if(phone != null && StringUtils.hasText(phone.get("phone"))) {
                PrivateOperationRecord privateOperationRecord = new PrivateOperationRecord();
                privateOperationRecord.setStudentNo(studentNo);
                privateOperationRecord.setCreateTime(new Date());
                privateOperationRecord.setOperatorNo(sysUser.getOaid());
                privateOperationRecord.setOperatorName(sysUser.getUsername());
                privateOperationRecord.setOperationType(Contants.PRIVATE_OPERATION_TYPE_CHECK_PHONE);
                privateOperationService.add(privateOperationRecord);
                logger.info(sysUser.getOaid()+"成功查看了："+studentNo+"的手机号码");
                return phone.get("phone");
            }
            logger.info(sysUser.getOaid()+"查看了："+studentNo+"的手机号码，但手机号码为空");
        }
        logger.info("查看手机号码时发生错误，传入学号为空");
        return null;
    }

    @PostMapping(value = "/student/checkSalesConsultant")
    @ResponseBody
    public String checkSalesConsultant(String salesConsultantNo,String studentNo){
        if(StringUtils.hasText(salesConsultantNo)){
            SysUser sysUser = userService.getLoginUser();
            String result = applyResultService.getStaffInfo(salesConsultantNo);
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (!jsonObject.getInteger("code").equals(200)) {
                logger.info("调用资源系统查看员工信息接口返回数据code码不为200");
                return null;
            }
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
//            JSONObject staffType = jsonObject1.getJSONObject("staffType");
            String mobile  = jsonObject1.getString("Mobile");
            String email   = jsonObject1.getString("Email");
            if(result != null && (mobile != null || email != null)) {
                PrivateOperationRecord privateOperationRecord = new PrivateOperationRecord();
                privateOperationRecord.setStudentNo(studentNo);
                privateOperationRecord.setCreateTime(new Date());
                privateOperationRecord.setOperatorNo(sysUser.getOaid());
                privateOperationRecord.setOperatorName(sysUser.getUsername());
                privateOperationRecord.setOperationType(Contants.PRIVATE_OPERATION_TYPE_CHECK_SALES);
                privateOperationService.add(privateOperationRecord);
                logger.info(sysUser.getOaid()+"成功查看了："+studentNo+"的咨询顾问联系方式");
                return mobile + "/" + email;
            }
            logger.info(sysUser.getOaid()+"查看了："+studentNo+"的咨询顾问联系方式，但咨询顾问联系方式为空");
            return "空";
        }
        logger.info("查看咨询顾问联系方式时发生错误，传入咨询顾问memberId为空");
        return null;
    }

    /**
     * 修改邮箱或密码
     *
     * @param studentNo
     * @param value
     * @param type
     * @return
     */
    @RequestMapping("/student/update")
    @ResponseBody
    public Boolean updateEmailOrPassword(String studentNo, String value, String type) {
        if (!StringUtils.hasText(studentNo) || !StringUtils.hasText(value) || !StringUtils.hasText(type)) {
            logger.error(MessageFormat.format("updateEmailOrPassword failed!! studentNo:{0}; value:{1}; type:{2}",
                    studentNo, value, type));
            return false;
        }
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(studentNo);
        if (EMAIL.equals(type)) {
            //TODO 调用外部接口

            studentInfo.setEmailAccount(value);
        } else if (PASSWORD.equals(type)) {
            studentInfo.setEmailPassword(value);
        } else {
            logger.error(MessageFormat.format("type error!! type:{0}", type));
            return false;
        }
        return studentService.updateByStudentNo(studentInfo);
    }

    /**
     * 跳转申请缓办页面
     *
     * @param studentNo
     * @return
     */
    @RequestMapping("/student/delay")
    public String toApproveDelayPage(Integer id,String studentNo,Integer type, Model model) {
        model.addAttribute("resDomain", resDomain);
        SysUser sysUser = userService.getLoginUser();
        if (sysUser.getOaid() == null) {
            return "/studentInfo/delay";
        }
        StudentDelayInfo studentDelayInfo0 = new StudentDelayInfo();
        studentDelayInfo0.setDeleteStatus(false);
        studentDelayInfo0.setStudentNo(studentNo);
        if(id != null){
            studentDelayInfo0.setId(id);
        }
        List<StudentDelayInfo> studentDelayInfos = studentDelayService.get(studentDelayInfo0);
        if (studentDelayInfos.size() > 0) {
            List<AuditResultInfo> auditResultInfos = auditResultService.getList(CaseIdEnum.ApproveDelay.getCode(), studentDelayInfos.get(studentDelayInfos.size() - 1).getId());
            model.addAttribute("auditResultSize", auditResultInfos.size());
        } else {
            model.addAttribute("auditResultSize", "0");
        }

        //获取员工工号对应的角色
        String roleName = "";
        List<String> roles = sysRoleMapper.getRoleByOaId(sysUser.getOaid());
        if (roles != null && roles.size() == 1) {
            roleName = roles.get(0);
        } else if (roles.size() > 1) {
            if (roles.contains("总经理")) {
                roleName = "总经理";
            } else if (roles.contains("文签总监")) {
                roleName = "文签总监";
            } else if (roles.contains("文案经理")) {
                roleName = "文案经理";
            } else if (roles.contains("外联经理")) {
                roleName = "外联经理";
            } else if (roles.contains("外联顾问")) {
                roleName = "外联顾问";
            } else if (roles.contains("文案顾问")) {
                roleName = "文案顾问";
            }
        }

        // 查询缓办申请
//        List<StudentDelayInfo> studentDelayInfos = studentDelayService.getList(studentNo);
        if(type != null && type.equals(1)) {
            model.addAttribute("studentDelayInfo", null);
            model.addAttribute("canSave", true);
        }else{
            if (studentDelayInfos.size() > 0) {
                StudentDelayInfo studentDelayInfo = studentDelayInfos.get(studentDelayInfos.size() - 1);
                if (studentDelayInfo.getContactDate() != null) {
                    model.addAttribute("status", false);
                } else {
                    model.addAttribute("status", true);
                }
                model.addAttribute("studentDelayInfo", studentDelayInfo);

                StudentInfo studentInfo = studentService.getStudentInfoByStudentNo(studentNo);

                model.addAttribute("businessId", studentDelayInfo.getId());

                if (Integer.valueOf(1).equals(studentInfo.getStudentStatus())) {
//                List<AuditResultInfo> auditResultInfos = auditResultService.getList(CaseIdEnum.ApproveDelay.getCode(), studentInfo.getId());
//                if(auditResultInfos.size() > 0){
//                    //已有审批结果
//                    model.addAttribute("auditResultInfos",auditResultInfos);
//                }
                    // 根据待审批人判断当前用户是否为审批人
                    AuditApplyInfo auditApplyInfo = auditApplyService.get(studentDelayInfo.getId(), CaseIdEnum.ApproveDelay.getCode());
                    model.addAttribute("auditApplyInfo", auditApplyInfo);

                    //能否审批
                    if (auditApplyInfo != null) {
                        model.addAttribute("canApproveDelay", true);
                    }
                    //待审批人
                    if (auditApplyInfo != null && auditApplyInfo.getOaid() != null) {
//                    SysUser sysUser1 = new SysUser();
//                    sysUser1.setDeleteStatus(false);
//                    sysUser1.setOaid(auditApplyInfo.getOaid());
//                    List<SysUser> sysUsers = userService.getList(sysUser1);
//                    if(!sysUsers.isEmpty()){
                        model.addAttribute("waitAuditNo", auditApplyInfo.getOaid());
                        model.addAttribute("waitAuditName", auditApplyInfo.getOaName());
//                    }
                        model.addAttribute("hasAuditInfo", true);
                    }

                    //能否保存
                    if (roles.contains("文案顾问") || roles.contains("文案经理")) {
                        model.addAttribute("canSave", true);
                    }

//                if(studentDelayInfo != null && studentDelayInfo.getOperatorNo().equals(sysUser.getOaid())){
//                    model.addAttribute("canSave", true);
//                }
                }


            } else {
                model.addAttribute("studentDelayInfo", null);
                model.addAttribute("canSave", true);
            }
        }
        return "/studentInfo/delay";
    }

    /**
     * 跳转手动结案页面
     *
     * @param studentNo
     * @return
     */
    @RequestMapping("/student/settle")
    public String toSettlePage(String studentNo, Model model) {
        model.addAttribute("resDomain", resDomain);
        SysUser sysUser = userService.getLoginUser();
        if (sysUser.getOaid() == null) {
            return "/studentInfo/settle";
        }
        // 查询结案申请
        List<StudentSettleInfo> studentSettleInfos = studentSettleService.getListByStudentNo(studentNo);
        if (studentSettleInfos.size() == 0) {
            model.addAttribute("isNull", true);
            logger.error("当前无结案申请信息");
            return "/studentInfo/settle";
        }

        List<AuditResultInfo> auditResultInfos = auditResultService.getList(CaseIdEnum.ApproveDelay.getCode(), studentSettleInfos.get(studentSettleInfos.size() - 1).getId());
        model.addAttribute("auditResultSize", auditResultInfos.size());


        if (studentSettleInfos.size() > 0) {
            StudentSettleInfo studentSettleInfo = studentSettleInfos.get(studentSettleInfos.size() - 1);
            model.addAttribute("studentSettleInfo", studentSettleInfo);

            StudentInfo studentInfo = studentService.getStudentInfoByStudentNo(studentNo);

            model.addAttribute("businessId", studentSettleInfo.getId());

            // 审批结果内容
            List<AuditResultInfo> list_case_11 = auditResultService.getList(CaseIdEnum.StudentSettle.getCode(), studentSettleInfo.getId());
            model.addAttribute("list_case_11", list_case_11);
            // 待审批内容
            AuditApplyInfo auditApplyInfo = auditApplyService.get(studentSettleInfo.getId(), CaseIdEnum.StudentSettle.getCode());
            model.addAttribute("toAudit", auditApplyInfo);
            if (auditApplyInfo != null) {
                model.addAttribute("canApproveSettle", true);
                // 当前登录人是否有审批权限及是否是当前当审批节点审批人
                if (sysUser.getOaid().equals(auditApplyInfo.getOaid())) {
                    model.addAttribute("isAuditUser", true);
                } else {
                    model.addAttribute("isAuditUser", false);
                }
            }


        } else {
            model.addAttribute("studentSettleInfo", null);
        }
        return "/studentInfo/settle";
    }

    /**
     * 保存缓办申请
     *
     * @param studentSettleInfo
     * @return
     */
    @RequestMapping(value = "/settle/save", method = {RequestMethod.POST})
    @ResponseBody
    public Integer approveDelay(StudentSettleInfo studentSettleInfo) {
        return studentSettleService.update(studentSettleInfo);
    }


    /**
     * 保存缓办申请
     *
     * @param studentDelayInfo
     * @return
     */
    @RequestMapping(value = "/student/approveDelay", method = {RequestMethod.POST})
    @ResponseBody
    public String approveDelay(StudentDelayInfo studentDelayInfo, Integer auditResultSize) {
        return studentService.approveDelay(studentDelayInfo, auditResultSize);
    }

    /**
     * 缓办审批
     *
     * @param type
     * @param remark
     * @param studentNo
     * @return
     */
    @RequestMapping("/student/delay/approve")
    @ResponseBody
    public String delayApprove(Integer type, String remark, String updateTime, String studentNo) {
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
        if (type == null) {
            logger.error(MessageFormat.format("delayApprove failed!! type:{1}", type));
            jsonObject.put("code", 2);
            return jsonObject.toString();
        }
        StudentInfo studentInfo = studentService.getStudentInfoByStudentNo(studentNo);
        if (studentInfo == null) {
            logger.error(MessageFormat.format("studentNo error! studentNo:{0}", studentNo));
            jsonObject.put("code", 2);
            return jsonObject.toString();
        }
        return studentDelayService.delayApprove(studentInfo.getId(), type, updateTime, remark, studentNo);
    }

    /**
     * 首次审批
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/student/Action_auditFirst", method = RequestMethod.POST)
    @ResponseBody
    public Integer Action_auditFirst(Integer id) {
        SysUser sysUser = userService.getLoginUser();
        StudentInfo studentInfo = studentService.getById(id);
        studentInfo.setFirstBonusStatus(2);
        studentService.update(studentInfo);

        ConfirmRecord confirmRecord = new ConfirmRecord();
        confirmRecord.setBusinessId(id);
        confirmRecord.setCaseId(1);
        confirmRecord.setCreateTime(new Date());
        confirmRecord.setDeleteStatus(false);
        confirmRecord.setOperatorNo(sysUser.getOaid());
        confirmRecord.setOperatorName(sysUser.getUsername());
        confirmRecord.setStudentNo(studentInfo.getStudentNo());
        confirmRecord.setUpdateTime(null);
        return confirmRecordService.addOne(confirmRecord);
    }

    /**
     * 最终审批
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/student/Action_auditLast", method = RequestMethod.POST)
    @ResponseBody
    public Integer Action_auditLast(Integer id) {
        SysUser sysUser = userService.getLoginUser();
        StudentInfo studentInfo = studentService.getById(id);
        studentInfo.setFinallyBonusStatus(2);
        studentService.update(studentInfo);

        ConfirmRecord confirmRecord = new ConfirmRecord();
        confirmRecord.setBusinessId(id);
        confirmRecord.setCaseId(2);
        confirmRecord.setCreateTime(new Date());
        confirmRecord.setDeleteStatus(false);
        confirmRecord.setOperatorNo(sysUser.getOaid());
        confirmRecord.setOperatorName(sysUser.getUsername());
        confirmRecord.setStudentNo(studentInfo.getStudentNo());
        confirmRecord.setUpdateTime(null);
        return confirmRecordService.addOne(confirmRecord);
    }

    @RequestMapping("/student/delay/checkStatus")
    @ResponseBody
    public String checkStatus(String date, String studentNo) {
        StudentDelayInfo studentDelayInfo = new StudentDelayInfo();
        studentDelayInfo.setDeleteStatus(false);
        studentDelayInfo.setStudentNo(studentNo);
        List<StudentDelayInfo> studentDelayInfos = studentDelayService.get(studentDelayInfo);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Contants.timePattern);
        JSONObject jsonObject = new JSONObject();

        if (date.equals(simpleDateFormat.format(studentDelayInfos.get(0).getUpdateTime()))) {
            jsonObject.put("code", 1);
        } else {
            jsonObject.put("code", 0);
        }
        return jsonObject.toString();
    }


//    @RequestMapping("/test")
//    public String test() {
//        List<SysUserRole> list = sysUserRoleMapper.getSysUsertest();
//        for (SysUserRole user:list) {
//            SysUserRole sysUser = new SysUserRole();
////            sysUser.setOaId(user.getOaid());
////            sysUser.setDeleteStatus(false);
////            sysUser.setUserId(user.getId().intValue());
////            sysUser.setRoleId(25);
////            sysUser.setCreated(new Date());
////            sysUser.setDeleted(new Date());
////            sysUser.setOperator("测试");
//            sysUser.setId(user.getId().intValue());
//            int inserResult = sysUserRoleMapper.insertList(sysUser.getId());
//
//        }
//
//        return null;
//    }

    @RequestMapping("/GDUnAudit")
    public String list(CountryInfo countryInfo, Model model) {
        List<CountryInfo> countryInfoList = countryService.getList(countryInfo);
        List<CountryInfo> countryInfoNew = new ArrayList<>();
        for (CountryInfo item : countryInfoList) {
            if (item.getCountryBussid() != 100010 && item.getCountryBussid() != 100011) {
                countryInfoNew.add(item);
            }
        }
        model.addAttribute("countryInfoList", countryInfoNew);
        return "studentInfo/GDUnAudit";
    }

    @RequestMapping(value = "GDUnAudit_query", method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel getGDUnAudit(String studentNo, Integer Type, String nationName, PageParam pageParam, BasePageModel basePageModel) {
        SysUser sysUser = userService.getLoginUser();
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength());
//        String[] str = new String[]{"id","status","studentNo","studentName","birthdayString","nationName","contractNo","branchName","signDate","firstBonusStatus","finallyBonusStatus","lastVisitDate"};
//        studentInfo.setDeleteStatus(false);
        List<StudentInfo> list = studentService.getGDUnAuditInfo(Type,studentNo,nationName);
//        pageParam.setSortPro(str[pageParam.getiSortCol_0()]);
//        pageParam.setiSortingCols("合同编号");
//        pageParam.setiSortCol_0(6);
        basePageModel.setAaData(page);
        basePageModel.setiTotalDisplayRecords((int) page.getTotal());
        basePageModel.setiTotalRecords((int) page.getTotal());
        return basePageModel;
    }


    @RequestMapping(value = "/studentSettle/query", method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel get(StudentSettleInfo studentSettleInfo, PageParam pageParam, BasePageModel basePageModel) {
        Page<?> page = pageWapper(pageParam, propList());
        List<StudentSettleInfo> studentSettleInfos = studentSettleService.getStudentSettle(studentSettleInfo);
        for (StudentSettleInfo item : studentSettleInfos) {
            if (item != null && StringUtils.hasText(item.getAttachment()) && !item.getAttachment().contains(resDomain)) {
                item.setAttachment(resDomain + item.getAttachment());
            }
        }
        return dataTableWapper(page, basePageModel);
    }


    /**
     * 跳转到待回访学生列表
     *
     * @return
     */
    @RequestMapping("/toDoVisitList")
    public String toDoVisitList(HttpServletRequest request,Model model) {
        model.addAttribute("role",request.getParameter("role"));
        request.getSession().removeAttribute("excelVisitInfoQuery");
        return "studentInfo/toDoVisitList";
    }

    /**
     * 分页查询待回访学生列表
     *
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping(value = "/student/toDoVisitList/query", method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel getToDoVisit(StudentInfo studentInfo, PageParam pageParam, BasePageModel basePageModel,HttpServletRequest request) {
        request.getSession().setAttribute("excelVisitInfoQuery",studentInfo);
        SysUser sysUser = userService.getLoginUser();

        String roleName = request.getParameter("roleName");
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength());
        String[] str = new String[]{"student_no", "student_name", "pinyin", "birthday", "sales_consultant", "copy_operator", "copy_maker", "copier", "copy", "visa_operator", "branch_name","sign_date","status","last_visit_time"};
        studentService.getStudentListByOaId(studentInfo, sysUser.getOaid(), roleName);
        basePageModel.setAaData(page);
        basePageModel.setiTotalDisplayRecords((int) page.getTotal());
        basePageModel.setiTotalRecords((int) page.getTotal());
        return basePageModel;
    }

    @RequestMapping("/student/excelVisitInfo")
    public void excelVisitInfo(HttpServletRequest request, HttpServletResponse response) {
        this.studentService.excelVisitInfo(request, response);
    }

    /**
     * 跳转到咨询顾问查看的学生信息页面(学生详细信息展示)
     *
     * @return
     */
    @RequestMapping("/consultant/student")
    public String consultant(String studentNo, Model model) {

        if(!StringUtils.hasText(studentNo)) {
            return "studentInfo/out_detail";
        }
        model.addAttribute("resDomain", resDomain);
            //学生信息
            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setStudentNo(studentNo);
            studentInfo.setDeleteStatus(false);
            studentInfo = studentService.get(studentInfo);
            if (studentInfo != null) {
                model.addAttribute("studentInfo", studentInfo);
            }
            //若代理id不为空时，查询同业学生表信息
            StudentDetailVO studentDetailVO = new StudentDetailVO();
            ChannelStudentInfo channelStudentInfo = studentService.getStudentMessageByStudentNo(studentNo);
            if (channelStudentInfo != null && channelStudentInfo.getAgentId() != null) {
                studentDetailVO =  studentService.getChannelStudentInfo(channelStudentInfo, studentDetailVO, studentNo, 1);
            } else {
                studentDetailVO =  studentService.getChannelStudentInfo(null,studentDetailVO, studentNo, 2);
            }
            model.addAttribute("studentDetail", studentDetailVO);


        //结案信息
        StudentSettleInfo studentSettleInfo = new StudentSettleInfo();
        studentSettleInfo.setStudentNo(studentNo);
        studentSettleInfo.setDeleteStatus(false);
        StudentSettleInfo studentSettleInfo1 = studentSettleService.get(studentSettleInfo);
        model.addAttribute("settle",studentSettleInfo1 );
        if(studentSettleInfo1!=null  && studentSettleInfo1.getId()!=null){
            List<AuditResultInfo> auditResultInfoList = auditResultService.getList(CaseIdEnum.StudentSettle.getCode(),studentSettleInfo1.getId());
            if(auditResultInfoList!=null){
                model.addAttribute("auditResultInfoList",auditResultInfoList);
            }
        }

        //停办信息
        StudentDelayInfo studentDelayInfo = new StudentDelayInfo();
        studentDelayInfo.setStudentNo(studentNo);
        studentDelayInfo.setDeleteStatus(false);
        model.addAttribute("delays", studentDelayService.get(studentDelayInfo));
        //获签信息
        List<VisaResultInfo> byStuNo = visaResultService.getByStuNo(studentNo);

        List<VisaResultInfo> list = new ArrayList<>();

        for (VisaResultInfo visaResultInfo : byStuNo) {
            if (visaResultInfo.getVisaStatus().equals(3)) {
                list.add(visaResultInfo);
            }
        }
        model.addAttribute("visas", list);

        return "studentInfo/out_detail";
    }


    /**
     * 分页查询院校申请结果列表（销售顾问）
     *
     * @param applyInfo
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping("/consultant/student/query")
    @ResponseBody
    public BasePageModel get(ApplyInfo applyInfo, PageParam pageParam, BasePageModel basePageModel) {
        Page<?> page = pageWapper(pageParam, propList());
        applyInfo.setDeleteStatus(false);

        List<ApplyInfo> resultInfos = applyCollegeService.getSaleByPage(applyInfo);
        for (ApplyInfo apply : resultInfos) {
            if (!StringUtils.isEmpty(apply.getStudentNo())) {
                Integer nationId = studentService.getStudentInfoByStudentNo(applyInfo.getStudentNo()).getNationId();
                apply.setNationId(nationId);
            }
            SupplementInfo supplementInfo = new SupplementInfo();
            supplementInfo.setApplyId(apply.getId());
            supplementInfo.setSupplementType(Contants.SUPPLEMENTTYPE_FIRT);
            supplementInfo.setDeleteStatus(false);
            if (!StringUtils.isEmpty(apply.getCommitDate())) {
                apply.setSendDateString(new SimpleDateFormat(Contants.datePattern).format(apply.getCommitDate()));
            }
            if (!StringUtils.isEmpty(apply.getUpdateTime())) {
                apply.setUpdateTimeString(new SimpleDateFormat(Contants.datePattern).format(apply.getUpdateTime()));
            }
            if (StringUtils.hasText(apply.getOperatorNo())) {
                SysUser sysUser = userService.getUserByName(apply.getOperatorNo());
                if (sysUser != null && StringUtils.hasText(sysUser.getUsername())) {
                    apply.setOperatorName(sysUser.getUsername());
                }
            }
        }
        return dataTableWapper(page, basePageModel);
    }


    /**
     * 跳转到申请详情页（销售顾问）
     *
     * @return
     */
    @RequestMapping("/apply/detailPage/sale")
    public String detail(Integer id, Model model) {
        logger.info("跳转到申请详情页（销售顾问）"+"   id"+id);
        ApplyCollegeVo apply = applyCollegeService.getApplyDetail(id);
        if (apply != null && apply.getApply() != null && !StringUtils.isEmpty(apply.getApply().getPlanId())) {
            PlanCollegeInfo planCollegeInfo = getPlanDetail(apply.getApply().getPlanId());
            if (planCollegeInfo != null) {
                model.addAttribute("planId", planCollegeInfo.getPlanId());
                model.addAttribute("planCollegeId", planCollegeInfo.getId());
            }
        }

        if (apply.getApply().getStudentNo() != null && apply.getApply().getStudentNo() != "") {
            Integer nation = this.studentService.getStudentInfoByStudentNo(apply.getApply().getStudentNo()).getNationId();
            if (!StringUtils.isEmpty(nation)) {
                model.addAttribute("nation", nation);
            }
        }
        model.addAttribute("apply", apply);

        //补件信息
        if(apply.getSupplementInfo()!=null){
                SupplementInfo supplementInfo = new SupplementInfo();
                supplementInfo.setExpressNo(apply.getSupplementInfo().getExpressNo());
                supplementInfo.setApplyId(apply.getApply().getId());
                List<SupplementInfo> list = supplementInfoService.getList(supplementInfo);
                model.addAttribute("list_Supplement_Message", list);
        }


        //coe
        if(apply.getApply()!=null){
                CoeApplyInfo coeApplyInfo = new CoeApplyInfo();
                coeApplyInfo.setDeleteStatus(false);
                coeApplyInfo.setStudentNo(apply.getApply().getStudentNo());
                coeApplyInfo.setApplyId(apply.getApply().getId());
                List<CoeApplyInfo> list = coeApplyService.getList(coeApplyInfo);
                for(CoeApplyInfo item:list){
                    if(!StringUtils.isEmpty(item.getStudentNo())){
                        item.setStudentName(studentService.getStudentInfoByStudentNo(item.getStudentNo()).getStudentName());
                    }
                }
                model.addAttribute("list_Coe_Message", list);
        }


        // offer
            ApplyResultInfo applyResultInfo = new ApplyResultInfo();
            applyResultInfo.setDeleteStatus(false);
            applyResultInfo.setApplyId(apply.getApply().getId());
            List<ApplyResultInfo> resultInfos=applyResultService.getByPage(applyResultInfo);
            for(ApplyResultInfo result:resultInfos){
                if(!StringUtils.isEmpty(result.getCreateTime())) {
                    result.setCreatedString(new SimpleDateFormat(Contants.datePattern).format(result.getCreateTime()));
                }
                if(!StringUtils.isEmpty(result.getResultDate())) {
                    result.setResultDateString(new SimpleDateFormat(Contants.datePattern).format(result.getResultDate()));
                }
            }
            model.addAttribute("list_Offer_Message", resultInfos);
        return "apply/sale";
    }

    /**
     * 跳转到申请详情页（销售顾问）
     *
     * @param planCollegeId
     * @return
     */
    private PlanCollegeInfo getPlanDetail(Integer planCollegeId) {
        PlanCollegeInfo planCollegeInfo = new PlanCollegeInfo();
        planCollegeInfo.setPlanId(planCollegeId);
        planCollegeInfo.setDeleteStatus(false);
        return planCollegeInfoService.get(planCollegeInfo);
    }


    /**
     * 分页查询回访信息列表（销售顾问）
     *
     * @param visitInfo
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping(value = "/sale/list/query", method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel get(VisitInfo visitInfo, PageParam pageParam, BasePageModel basePageModel) {

        visitInfo.setDeleteStatus(false);
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart()/pageParam.getiDisplayLength()+1, pageParam.getiDisplayLength());
        String[] str = new String[]{"id","student_no","content","create_time","operator_name"};
        visitInfoService.getList(visitInfo);
        pageParam.setSortPro(str[pageParam.getiSortCol_0()]);
        return dataTableWapper(page,basePageModel);
    }
    /**
     * 分页查询补件信息列表（销售顾问）
     *
     * @param
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping(value = "saleInfo_query", method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel get(SupplementInfo supplementInfo, PageParam pageParam, BasePageModel basePageModel) {
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength());
        String[] str = new String[]{"id", "supplement_type", "send_date",  "create_time", "operator_no"};
        supplementInfoService.getListNPA(supplementInfo);
        pageParam.setSortPro(str[pageParam.getiSortCol_0()]);
        basePageModel.setAaData(page);
        basePageModel.setiTotalDisplayRecords((int) page.getTotal());
        basePageModel.setiTotalRecords((int) page.getTotal());
        return basePageModel;
    }

    @RequestMapping("/search")
    public String search(CountryInfo countryInfo, BranchInfo branchInfo, Model model, HttpServletRequest request) {
        SysUser user = userService.getLoginUser();
        String oaid = request.getParameter("oaid");
        String status = request.getParameter("status");
        model.addAttribute("status", status);
        model.addAttribute("oaid", oaid);
        List<CountryInfo> countryInfoList = countryService.getList(countryInfo);
        List<CountryInfo> countryInfoNew = new ArrayList<>();
        for (CountryInfo item : countryInfoList) {
            if (item.getCountryBussid() != 100010 && item.getCountryBussid() != 100011) {
                countryInfoNew.add(item);
            }
        }
        model.addAttribute("countryInfoList", countryInfoNew);
        List<BranchInfo> branchInfoList = branchService.getList(branchInfo);
        model.addAttribute("branchInfoList", branchInfoList);
        HttpSession session = request.getSession();
        if (user != null) {
            session.setAttribute("functions", userService.getFunctionByName(user.getOaid()));
        }
        return "studentInfo/search";
    }

    @RequestMapping("student/addCollege")
    public String addStudentTime(HttpServletRequest request,Model model){
        model.addAttribute("studentNo",request.getParameter("studentNo"));
        return "studentInfo/addCollege";
    }

    @RequestMapping("student/addService")
    @ResponseBody
    public Boolean addService(String studentNo,String addMessage){
        return studentService.addStudentService(studentNo,addMessage) ==1;
    }


    private String[] propList() {
        String[] str = new String[]{"sign_date", "last_visit_time"};
        return str;
    }

    @RequestMapping("/doubleSendAuditInfo")
    public String doubleSendAuditInfo(String studentNo,Integer caseId, Model model) {
        SysUser user = userService.getLoginUser();
        if(!StringUtils.isEmpty(studentNo)) {
            ConfirmRecord info = confirmRecordService.get(studentNo,caseId);
            model.addAttribute("info", info);
        }
        return "studentInfo/doubleSendAuditInfo";
    }

//    @RequestMapping("settle/insert")
//    public void getTest(){
//        studentDelayService.testInsertSettle();
//    }
    /**
     * 跳转到材料清单列表
     *
     * @return
     */
    @RequestMapping("/material")
    public String toMaterialList(String studentNo,Model model) {
        Integer countryId = studentService.getStudentInfoByStudentNo(studentNo).getNationId();
        model.addAttribute("countryId",countryId);

        return "studentInfo/materialList";
    }

    @RequestMapping(value="/materialList/getPapersId",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public  List<PcPapersTypeInfo> getPapersId(Integer countryId,Integer papersType, HttpServletRequest request){

        PcPapersTypeInfo pcPapersTypeInfo = new PcPapersTypeInfo();

        CountryInfo countryInfo = new CountryInfo();
        countryInfo.setId(countryId);
        Integer countryBusinessId = countryService.get(countryInfo).getCountryBussid();

        pcPapersTypeInfo.setCountryId(countryBusinessId);

        pcPapersTypeInfo.setPapersType(papersType);

        return pcPapersTypeInfoMapper.select(pcPapersTypeInfo);
    }

    /**
     * 材料清单
     *
     * @param
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping(value = "/material/list", method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel get(PcPapersFileInfo pcPapersFileInfo, PageParam pageParam, BasePageModel basePageModel,HttpServletRequest request) {
        request.getSession().removeAttribute("pcPapersFileList");
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength());
        String[] str = new String[]{"id","papers_id","papers_type","url","file_name","student_no","create_time","delete_status"};
        List<PcPapersFileInfo> list = pcPapersFileInfoMapper.getList(pcPapersFileInfo);

        pageParam.setSortPro(str[pageParam.getiSortCol_0()]);
        basePageModel.setAaData(page);
        basePageModel.setiTotalDisplayRecords((int) page.getTotal());
        basePageModel.setiTotalRecords((int) page.getTotal());
        request.getSession().setAttribute("pcPapersFileList",list);
        return basePageModel;
    }

    @RequestMapping("/student/updateConfee")
    @ResponseBody
    public Boolean updateConfee(String studentNo, String confee) {
        SysUser sysUser = userService.getLoginUser();
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(studentNo);
        studentInfo = studentService.get(studentInfo);
        studentInfo.setConfeeid(confee);
        if(studentService.update(studentInfo) > 0){
            ConfirmRecord confirmRecord = new ConfirmRecord();
            confirmRecord.setCaseId(3);
            confirmRecord.setStudentNo(studentNo);
            confirmRecord.setCreateTime(new Date());
            confirmRecord.setUpdateTime(new Date());
            confirmRecord.setOperatorNo(sysUser.getOaid());
            confirmRecord.setOperatorName(sysUser.getUsername());
            confirmRecord.setDeleteStatus(false);
            return confirmRecordService.addOne(confirmRecord) >0;
        }
        return false;
    }

    @RequestMapping("/operator/settle")
    @ResponseBody
    public Boolean operatorUpdateSettle(String studentNo, Integer reason, String remark) {
        String studentName = studentService.getStudentInfoByStudentNo(studentNo).getStudentName();
        List<StudentSettleInfo> studentSettleInfos = studentSettleService.getListByStudentNo(studentNo);
        if(studentSettleInfos.size()>0) {
            for (StudentSettleInfo item : studentSettleInfos) {
                item.setDeleteStatus(true);
                studentSettleService.update(item);
            }
        }
        return operatorUpdateSettleMethod(studentNo,studentName,reason,remark);
    }

    public Boolean operatorUpdateSettleMethod(String studentNo, String studentName, Integer reason, String remark) {
        SysUser sysUser = userService.getLoginUser();
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(studentNo);
        StudentInfo studentInfo1 = studentService.getStudentInfoByStudentNo(studentNo);
        studentInfo1.setStatus(90);
        Integer result0 = studentService.update(studentInfo1);

        StudentSettleInfo studentSettleInfo = new StudentSettleInfo();
        studentSettleInfo.setDeleteStatus(false);
        studentSettleInfo.setStudentNo(studentNo);
        studentSettleInfo.setStudentName(studentName);
        studentSettleInfo.setAuditStatus(3);
        studentSettleInfo.setReason(reason);
        studentSettleInfo.setCreateTime(new Date());
        studentSettleInfo.setUpdateTime(new Date());
        studentSettleInfo.setOperatorNo(sysUser.getOaid());
        studentSettleInfo.setOperatorName(sysUser.getUsername());
        Integer result1 = studentSettleInfoMapper.insert(studentSettleInfo);

        List<StudentSettleInfo> studentSettleInfos = studentSettleService.getListByStudentNo(studentNo);
        Integer businessId = studentSettleInfos.get(0).getId();

        AuditResultInfo auditResultInfo = new AuditResultInfo();
        auditResultInfo.setStudentNo(studentNo);
        auditResultInfo.setDeleteStatus(false);
        auditResultInfo.setBusinessId(businessId);
        auditResultInfo.setCaseId(11);
        auditResultInfo.setApplyStatus(2);
        auditResultInfo.setReason(remark);
        auditResultInfo.setApplyId(0);
        auditResultInfo.setCreateTime(new Date());
        auditResultInfo.setUpdateTime(new Date());
        auditResultInfo.setOperatorNo(sysUser.getOaid());
        auditResultInfo.setOperatorName(sysUser.getUsername());
        Integer result2 = auditResultInfoMapper.insert(auditResultInfo);

        return (result0>0 && result1 > 0 && result2 > 0);
    }

    @RequestMapping("/student/disableStudent")
    @ResponseBody
    public Boolean disableStudent(String studentNo,String remark) {
        SysUser sysUser = userService.getLoginUser();
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(studentNo);
        studentInfo = studentService.get(studentInfo);
        studentInfo.setDeleteStatus(true);
        studentInfo.setRemark(remark);
        studentInfo.setUpdateTime(new Date());
        if(studentService.update(studentInfo) > 0) {
            ConfirmRecord confirmRecord = new ConfirmRecord();
            confirmRecord.setCaseId(5);
            confirmRecord.setStudentNo(studentNo);
            confirmRecord.setCreateTime(new Date());
            confirmRecord.setDeleteStatus(false);
            confirmRecord.setOperatorNo(sysUser.getOaid());
            confirmRecord.setOperatorName(sysUser.getUsername());
            confirmRecordService.addOne(confirmRecord);
        }
        return true;
    }

    /**
     * 跳转到结案列表
     *
     * @return
     */
    @RequestMapping("/settleList")
    public String toSettleList(String studentNo,Model model) {
//        Integer countryId = studentService.getStudentInfoByStudentNo(studentNo).getNationId();
//        model.addAttribute("countryId",countryId);

        return "studentInfo/settleList";
    }

    @RequestMapping(value = "settleList_query", method = RequestMethod.POST)
    @ResponseBody
    public BasePageModel getSettleList(StudentInfo studentInfo, PageParam pageParam, BasePageModel basePageModel) {
        SysUser sysUser = userService.getLoginUser();
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength());
        List<StudentInfo> list = studentService.getSettleList(studentInfo);
        basePageModel.setAaData(page);
        basePageModel.setiTotalDisplayRecords((int) page.getTotal());
        basePageModel.setiTotalRecords((int) page.getTotal());
        return basePageModel;
    }

    @RequestMapping("/operator/cancelSettle")
    @ResponseBody
    public Boolean operatorCancelSettle(String studentNo) {
        if (studentNo == null) {
            return false;
        } else {
            return studentSettleService.operatorCancelSettle(studentNo);
        }
    }

    /**
     * 跳转到首次寄出待审核列表
     *
     * @return
     */
    @RequestMapping("/firstBonusList")
    public String toFirstBonusList() {
        return "workTable/firstBonusList";
    }

    /**
     * 我提交的首次寄出待审核列表
     */
    @RequestMapping(value = "firstBonus/query", method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel getFirstBonusList(PageParam pageParam, BasePageModel basePageModel) {
        SysUser sysUser = userService.getLoginUser();
        String oaid = sysUser.getOaid();
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength());
        List<StudentInfo> list = studentService.getFirstBonusList(oaid);
        basePageModel.setAaData(page);
        basePageModel.setiTotalDisplayRecords((int) page.getTotal());
        basePageModel.setiTotalRecords((int) page.getTotal());
        return basePageModel;
    }

    /**
     * 跳转到我提交的首次寄出待审核列表
     *
     * @return
     */
    @RequestMapping("/toAuditFirstBonusList")
    public String toAuditFirstBonusList() {
        return "workTable/toAuditFirstBonusList";
    }

    /**
     * 我提交的首次寄出待审核列表
     */
    @RequestMapping(value = "toAuditFirstBonus/query", method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel getToAuditFirstBonus(StudentInfo studentInfo,PageParam pageParam, BasePageModel basePageModel) {
        SysUser sysUser = userService.getLoginUser();
        String oaid = sysUser.getOaid();
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength());
        List<StudentInfo> list = studentService.getToAuditFirstBonusList(oaid,studentInfo);
        basePageModel.setAaData(page);
        basePageModel.setiTotalDisplayRecords((int) page.getTotal());
        basePageModel.setiTotalRecords((int) page.getTotal());
        return basePageModel;
    }

    @RequestMapping("/student/edit")
    public String edit(Model model){
        List<CountryInfo> countryInfoList = countryService.getList(new CountryInfo());
        List<CountryInfo> countryInfoNew = new ArrayList<>();
        for (CountryInfo item : countryInfoList) {
            if (item.getCountryBussid() != 100010 && item.getCountryBussid() != 100011) {
                countryInfoNew.add(item);
            }
        }
        model.addAttribute("countryInfoList", countryInfoNew);
        return "studentInfo/edit";
    }

    @PostMapping("/student/edit/save")
    @ResponseBody
    public boolean editStudentInfo(StudentInfo studentInfo){
        return studentService.updateStudentInfo(studentInfo);
    }

    @RequestMapping("/management/settleList")
    public String checkAllSettleList(Model model){
        List<CountryInfo> countryInfoList = countryService.getList(new CountryInfo());
        List<CountryInfo> countryInfoNew = new ArrayList<>();
        for (CountryInfo item : countryInfoList) {
            if (item.getCountryBussid() != 100010 && item.getCountryBussid() != 100011) {
                countryInfoNew.add(item);
            }
        }
        model.addAttribute("countryInfoList", countryInfoNew);
        return "operation/settleList";
    }

    @PostMapping("/management/checkAllSettleList")
    @ResponseBody
    public BasePageModel checkAllSettleList(StudentSettleInfo studentSettleInfo,Integer nationId, String studentName, PageParam pageParam, BasePageModel basePageModel) {
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength(), true);
        studentSettleService.checkAllSettleList(studentSettleInfo,nationId,studentName);
        return dataTableWapper(page, basePageModel);
    }
}

