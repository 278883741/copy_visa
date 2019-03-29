package com.aoji.controller;

import com.aoji.contants.Contants;
import com.aoji.contants.ContentException;
import com.aoji.contants.StudentStatus;
import com.aoji.mapper.ApplyInfoMapper;
import com.aoji.mapper.DoubleSignInfoMapper;
import com.aoji.model.*;
import com.aoji.model.res.SchoolData;
import com.aoji.service.*;
import com.aoji.vo.ApplyCollegeVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangsaixing
 * @description 申请院校控制器
 * @date Created in 下午2:25 2017/12/7
 */
@Controller
public class ApplyController extends BaseController {

    @Autowired
    ApplyCollegeService applyCollegeService;
    @Autowired
    ExpressCompanyService expressCompanyService;

    @Autowired
    SupplementInfoService supplementInfoService;

    @Autowired
    StudentCopyInfoService studentCopyInfoService;

    @Autowired
    StudentService studentService;
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @Autowired
    PlanCollegeInfoService planCollegeInfoService;

    @Autowired
    VisaRecordService visaRecordService;

    @Autowired
    ApplyInfoMapper applyInfoMapper;

    @Autowired
    CoeApplyService coeApplyService;

    @Autowired
    AuditResultService auditResultService;

    @Autowired
    ApplyResultService applyResultService;

    @Autowired
    CountryService countryService;

    @Autowired
    DoubleSignService doubleSignService;

    @Autowired
    BranchService branchService;

    @Value("${upload.ks3.resDomain}")
    private String resDomain;

    @Value("${plan.college.info.url}")
    private String planCollegeInfoUrl;

    @Value("${plan.college.list.url}")
    private String planCollegeListUrl;

//    @Value("${old.visa.onschool}")
//    private String lodVisaOnSchool;

    /**
     * 跳转到列表页
     *
     * @return
     */
    @RequestMapping("/apply")
    public String list(Model model, String studentNo) {
        Subject subject = SecurityUtils.getSubject();
        StudentInfo student = this.studentService.getStudentInfoByStudentNo(studentNo);
        ApplyInfo applyInfo = new ApplyInfo();

        model.addAttribute("nationId",student.getNationId());
        if(student.getNationStatus() == null)
        {
            model.addAttribute("nationStatus", 0);
        }else {
            model.addAttribute("nationStatus", student.getNationStatus());
        }
        applyInfo.setStudentNo(studentNo);
        List<ApplyInfo> applyInfos = applyInfoMapper.selectByPlanCourseStatus(applyInfo);
        if(applyInfos.size()>0){
            model.addAttribute("planCourseStatus", 1);
        }
        model.addAttribute("nation", student.getNationId());
        model.addAttribute("hasPermission_visit_list", subject.isPermitted("visit:list"));

        SysUser user = userService.getLoginUser();
        model.addAttribute("loginUser",user.getOaid());

        return "apply/list";
    }


    /**
     * 跳转到申请编辑页
     *
     * @return
     */
    @RequestMapping("/apply/editPage")
    public String editPage(Integer id, Model model,String nationId) {
        ApplyCollegeVo apply = applyCollegeService.getApplyDetailNPA(id);
        if (apply != null && apply.getApply() != null && StringUtils.hasText(apply.getApply().getStudentNo())) {
            model.addAttribute("collegeCopyList", studentCopyInfoService.queryByStudentNo(apply.getApply().getStudentNo()));
        } else if (apply != null && apply.getApply() != null && StringUtils.hasText(apply.getApply().getCollegeMaterial()) && !apply.getApply().getCollegeMaterial().contains(resDomain)) {
            apply.getApply().setCollegeMaterial(resDomain + apply.getApply().getCollegeMaterial());
        }
        if (apply != null && apply.getApply() != null && !StringUtils.isEmpty(apply.getApply().getPlanCollegeId())) {
            PlanCollegeInfo planCollegeInfo = getPlanDetail(apply.getApply().getPlanCollegeId());
            if (planCollegeInfo != null) {
                model.addAttribute("planCollegeInfo",planCollegeInfo);
                model.addAttribute("planId", planCollegeInfo.getPlanId());
                model.addAttribute("planCollegeId", planCollegeInfo.getId());
            }
        }
        model.addAttribute("resDomain", resDomain);
        model.addAttribute("apply", apply);
        model.addAttribute("planCollegeInfoUrl", planCollegeInfoUrl);
        model.addAttribute("planCollegeListUrl", planCollegeListUrl);

        //绑定院校课程专业
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(apply.getApply().getStudentNo());
        studentInfo.setDeleteStatus(false);
        studentInfo = studentService.get(studentInfo);

        String studentNationName = studentInfo.getNationName();
        model.addAttribute("studentNationName",studentNationName);

        CountryInfo countryInfo = new CountryInfo();
        if(StringUtils.hasText(nationId)){
            countryInfo.setId(Integer.valueOf(nationId));
        }else{
            countryInfo.setId(studentInfo.getNationId());
        }
        model.addAttribute("student_nation_id",StringUtils.hasText(nationId) ? nationId:studentInfo.getNationId());
        countryInfo = countryService.get(countryInfo);

        List<SchoolData> listSchoolDistinct = visaRecordService.getSchool1(countryInfo.getCountryXiaoxi());
        model.addAttribute("school",listSchoolDistinct);

        Integer courseType = 0;
        if(apply!=null&&apply.getApply()!=null&&apply.getApply().getCourseType()!=null){
            switch (apply.getApply().getCourseType()){
                case 1:courseType = 2;break;
                case 2:courseType = 3;break;
                case 3:courseType = 1;break;
                case 4:courseType = 4;break;
                case 5:courseType = 5;break;
            }
        }


        List<SchoolData> listMajor1 = visaRecordService.getMajors(apply.getApply().getCollegeId(),courseType,apply.getApply().getEducationSection());
        List<SchoolData> listCourse1 = visaRecordService.getDegrees(apply.getApply().getCollegeId(),apply.getApply().getMajorId());
        List<SchoolData> listAgency1 = visaRecordService.getCooperations(apply.getApply().getCollegeId());

        model.addAttribute("listCourse1",listCourse1);
        model.addAttribute("listMajor1",listMajor1);
        model.addAttribute("listAgency1",listAgency1);


        List<String> roleNames = new ArrayList<String>();
        roleNames.add("外联顾问");
        roleNames.add("外联经理");
        boolean[] roleName = SecurityUtils.getSubject().hasRoles(roleNames);
        if (roleName[0] == false && roleName[1] == false) {
            model.addAttribute("roleName", false);
        } else {
            model.addAttribute("roleName", true);
        }

        return "apply/edit";
    }

    /**
     * 跳转到申请编辑页
     *
     * @return
     */
    @RequestMapping("/apply/editOthers")
    public String editOthers(Integer id, Model model) {
        ApplyCollegeVo apply = applyCollegeService.getApplyDetailNPA(id);
        if (apply != null && apply.getApply() != null && StringUtils.hasText(apply.getApply().getStudentNo())) {
            model.addAttribute("collegeCopyList", studentCopyInfoService.queryByStudentNo(apply.getApply().getStudentNo()));
        } else if (apply != null && apply.getApply() != null && StringUtils.hasText(apply.getApply().getCollegeMaterial()) && !apply.getApply().getCollegeMaterial().contains(resDomain)) {
            apply.getApply().setCollegeMaterial(resDomain + apply.getApply().getCollegeMaterial());
        }
        if (apply != null && apply.getApply() != null && !StringUtils.isEmpty(apply.getApply().getPlanCollegeId())) {
            PlanCollegeInfo planCollegeInfo = getPlanDetail(apply.getApply().getPlanCollegeId());
            if (planCollegeInfo != null) {
                model.addAttribute("planCollegeInfo",planCollegeInfo);
                model.addAttribute("planId", planCollegeInfo.getPlanId());
                model.addAttribute("planCollegeId", planCollegeInfo.getId());
            }
        }
        model.addAttribute("resDomain", resDomain);
        model.addAttribute("apply", apply);
        model.addAttribute("planCollegeInfoUrl", planCollegeInfoUrl);
        model.addAttribute("planCollegeListUrl", planCollegeListUrl);

        List<String> roleNames = new ArrayList<String>();
        roleNames.add("外联顾问");
        roleNames.add("外联经理");
        boolean[] roleName = SecurityUtils.getSubject().hasRoles(roleNames);
        if (roleName[0] == false && roleName[1] == false) {
            model.addAttribute("roleName", false);
        } else {
            model.addAttribute("roleName", true);
        }

        return "apply/editOthers";
    }

    /**
     * 跳转到申请添加页
     *
     * @return
     */
    @RequestMapping("/apply/addPage")
    public String addPage(String studentNo,Model model,String nationId) {

        //绑定院校课程专业
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(studentNo);
        studentInfo.setDeleteStatus(false);
        studentInfo = studentService.get(studentInfo);
        String studentNationName = studentInfo.getNationName();

        CountryInfo countryInfo = new CountryInfo();
        if(StringUtils.hasText(nationId)){
            countryInfo.setId(Integer.valueOf(nationId));
        }else{
            countryInfo.setId(studentInfo.getNationId());
        }
        countryInfo = countryService.get(countryInfo);
        model.addAttribute("student_nation_id",StringUtils.hasText(nationId) ? nationId:studentInfo.getNationId());
        List<SchoolData> listSchool = visaRecordService.getSchool1(countryInfo.getCountryXiaoxi());

        model.addAttribute("resDomain", resDomain);
        model.addAttribute("planCollegeInfoUrl", planCollegeInfoUrl);
        model.addAttribute("planCollegeListUrl", planCollegeListUrl);
        model.addAttribute("school",listSchool);
		model.addAttribute("studentNationName",studentNationName);
		if(studentInfo.getAgentId() != null){
			model.addAttribute("isAgent","true");
        }

        return "apply/add";
    }


    @RequestMapping("/nation/add")
    public String nationAdd(String studentNo,Model model,Integer type,Integer id,Integer applyId,Integer nationStatus){
        model.addAttribute("studentNo",studentNo);
        model.addAttribute("type",type);
        model.addAttribute("schoolId",id);
        model.addAttribute("applyId",applyId);
        model.addAttribute("nationStatus",nationStatus);

        DoubleSignInfo doubleSignInfo = new DoubleSignInfo();
        doubleSignInfo.setDeleteStatus(false);
        doubleSignInfo.setDoubleSignCode(nationStatus);
        List<DoubleSignInfo> listNation = doubleSignService.getList(doubleSignInfo);
        model.addAttribute("nation",listNation);
        return "apply/add_nation_page";
    }

    /**
     * @param planCollegeId
     * @return
     */
    private PlanCollegeInfo getPlanDetail(Integer planCollegeId) {
        PlanCollegeInfo planCollegeInfo = new PlanCollegeInfo();
        planCollegeInfo.setId(planCollegeId);
        planCollegeInfo.setDeleteStatus(false);
        return planCollegeInfoService.get(planCollegeInfo);
    }


    /**
     * 跳转到申请详情页
     *
     * @return
     */
    @RequestMapping("/apply/detailPage")
    public String detail(Integer id, Model model) {
        ApplyCollegeVo apply = applyCollegeService.getApplyDetailNPA(id);
        if (apply != null && apply.getApply() != null && !StringUtils.isEmpty(apply.getApply().getPlanCollegeId())) {
            PlanCollegeInfo planCollegeInfo = getPlanDetail(apply.getApply().getPlanCollegeId());
            if (planCollegeInfo != null) {
                model.addAttribute("planCollegeInfo",planCollegeInfo);
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
        model.addAttribute("planCollegeInfoUrl", planCollegeInfoUrl);
        model.addAttribute("planCollegeListUrl", planCollegeListUrl);
        model.addAttribute("resDomain", resDomain);
        return "apply/detail";
    }


    @RequestMapping(value = "/apply/save", method = RequestMethod.POST)
    @ResponseBody
    public Boolean save(ApplyCollegeVo applyCollegeVo) {
        try {
            SysUser sysUser = userService.getLoginUser();
            Boolean updateStudentStatus = false;
            Boolean saveApply = false;
            if(StringUtils.hasText(applyCollegeVo.getApply().getStudentNo())) {
                StudentInfo student = studentService.getStudentInfoByStudentNo(applyCollegeVo.getApply().getStudentNo());
                if(!StringUtils.isEmpty(student.getStatus())){
                    if(student.getStatus()<= StudentStatus.NO_COLLEGE_APPLY.getCode()) {
                        updateStudentStatus =  studentService.updateProcessStatus(student.getStudentNo(), StudentStatus.NO_COLLEGE_APPLY.getCode(), sysUser.getOaid());
                        saveApply = applyCollegeService.saveApplyAndSupplement(applyCollegeVo);
                    }else{
                        updateStudentStatus=true;
                        saveApply = applyCollegeService.saveApplyAndSupplement(applyCollegeVo);
                    }
                }
                return updateStudentStatus && saveApply;
            }
            else return false;
        } catch (ContentException e) {
            return false;
        }
    }

    @RequestMapping(value = "/apply/add", method = RequestMethod.POST)
    @ResponseBody
    public Integer add(ApplyInfo applyInfo) {
        try {
            return applyCollegeService.insert(applyInfo);
        } catch (ContentException e) {
            return 0;
        }
    }


    /**
     * 分页查询申请结果列表
     *
     * @param applyInfo
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping("/apply/list/query")
    @ResponseBody
    public BasePageModel get(ApplyInfo applyInfo, PageParam pageParam, BasePageModel basePageModel) {
        SysUser user = userService.getLoginUser();
        List<String> roles = roleService.getRolesByOaId(user.getOaid());
        Page<?> page = pageWapper(pageParam, propList());
        applyInfo.setDeleteStatus(false);
        List<ApplyInfo> resultInfos = applyCollegeService.getByPage(applyInfo, roles, user.getOaid());
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
            //获取审批时间，区分美国与其他国家
            if(apply.getNationId().equals(4)){
                CoeApplyInfo coeApplyInfo = new CoeApplyInfo();
                coeApplyInfo.setApplyId(apply.getId());
                coeApplyInfo.setStudentNo(apply.getStudentNo());
                coeApplyInfo.setDeleteStatus(false);
                coeApplyInfo.setApplyStatus(2);

                CoeApplyInfo result = coeApplyService.get(coeApplyInfo);

                if(result != null) {
                    List<AuditResultInfo> list = auditResultService.getList(4, result.getId());
                    for (AuditResultInfo item : list) {
                        if (item.getApplyStatus().equals(2)) {
                            apply.setCoeAuditTime(item.getCreateTime());
                        }
                    }
                }
            }
            else{
                ApplyResultInfo applyResultInfo = new ApplyResultInfo();
                applyResultInfo.setApplyId(apply.getId());
                applyResultInfo.setStudentNo(apply.getStudentNo());
                applyResultInfo.setDeleteStatus(false);
                applyResultInfo.setResultType(1);
                List<ApplyResultInfo> applyResultList = applyResultService.getByPageNPA(applyResultInfo);

                if(applyResultList != null && applyResultList.size()>0) {
                    List<AuditResultInfo> list = auditResultService.getList(3, applyResultList.get(0).getId());
                    for (AuditResultInfo item : list) {
                        if (item.getApplyStatus().equals(2)) {
                            apply.setOfferAuditTime(item.getCreateTime());
                        }
                    }
                }
            }
        }
        return dataTableWapper(page, basePageModel);
    }

    /**
     * 接受offer
     *
     * @param applyId 申请id
     * @return
     */
    @RequestMapping(value = "/apply/acceptOffer", method = RequestMethod.POST)
    @ResponseBody
    public Boolean acceptOffer(Integer applyId) {
        return applyCollegeService.acceptOffer(applyId);
    }

    public String[] propList() {
        return new String[]{"id", "applyStatusCode", "applyType", "collegeName", "courseName", "agreementCollegeStatus", "majorName", "applyWay", "sendDateString", "connector", "operatorNo", "updateTimeString"};
    }

    /**
     * 获取当前留学国家
     *
     * @param studentNo 学号
     * @return
     */
    @RequestMapping(value = "/apply/getNationIdByStudentNo", method = RequestMethod.POST)
    @ResponseBody
    public Integer getNationIdByStudentNo(String studentNo) {
        StudentInfo studentInfo = studentService.getStudentInfoByStudentNo(studentNo);
        return studentInfo.getNationId();
    }

    /**
     * 删除申请结果
     *
     * @param applyId
     * @return
     */
    @RequestMapping("applyInfo/remove")
    @ResponseBody
    public Boolean remove(Integer applyId) {
        return applyCollegeService.removeApply(applyId);
    }


    /**
     * 查询注册费学费押金
     * @return
     */
    @RequestMapping(value = "/cost/basicCostApplylist")
    @ResponseBody
    public BasePageModel costQuery(ApplyInfo applyInfo,PageParam pageParam,BasePageModel basePageModel){
        Page<?> page = pageWapper(pageParam, propList());
         applyCollegeService.basicCostApplylist(applyInfo);

        return dataTableWapper(page, basePageModel);
    }


    /**
     * 编辑学生基本费用
     * @param
     * @param applyInfo
     */
    @RequestMapping(value = "/cost/basicEditFee",method = RequestMethod.POST)
    @ResponseBody
    public Boolean basicEditFee(ApplyInfo  applyInfo) {
        return applyCollegeService.basicEditFee(applyInfo);
    }

    /**
     * 跳转到 管理层查看-所有院校申请列表
     *
     * @return
     */
    @RequestMapping("/management/applyList")
    public String checkAllApplyList(Model model) {
        CountryInfo countryInfo = new CountryInfo();
        BranchInfo branchInfo = new BranchInfo();
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
        return "operation/applyList";
    }


    /**
     * 管理层查看-所有院校申请列表
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping("/management/checkAllApplyList")
    @ResponseBody
    public BasePageModel checkAllApplyList(ApplyInfo applyInfo,String dateStart, String dateEnd,PageParam pageParam, BasePageModel basePageModel) {
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength(), true);
        applyCollegeService.checkAllApplyList(applyInfo,dateStart,dateEnd);
        return dataTableWapper(page, basePageModel);
    }

}
