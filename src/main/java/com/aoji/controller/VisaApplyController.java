package com.aoji.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.*;
import com.aoji.contants.manager.SearchOptionVisaApply;
import com.aoji.mapper.VisaApplyInfoMapper;
import com.aoji.model.*;
import com.aoji.service.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.beans.Transient;
import java.math.BigDecimal;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;

/**
 * @author 赵剑飞
 * @description 签证申请控制器
 * @date Created in 下午2:25 2017/12/7
 */
@Controller
public class VisaApplyController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(VisaApplyController.class);
    @Autowired
    VisaApplyService visaApplyService;
    @Autowired
    VisaResultService visaResultService;
    @Autowired
    AuditApplyService auditApplyService;
    @Autowired
    AuditResultService auditResultService;
    @Autowired
    ApplyCollegeService applyCollegeService;
    @Autowired
    StudentService studentService;
    @Autowired
    UserGroupRelationService userGroupRelationService;
    @Autowired
    UserService userService;
    @Autowired
    CountryService countryService;
    @Autowired
    VisaApplyInfoMapper visaApplyInfoMapper;
    @Autowired
    RoleService roleService;
    @Autowired
    VisaRecordService visaRecordService;
    @Autowired
    VisaResultRecordService visaResultRecordService;
    @Autowired
    FollowServiceInfoService followServiceInfoService;
    @Autowired
    CurrencyInfoService currencyInfoService;
    @Autowired
    DoubleSignService doubleSignService;
    @Autowired
    BranchService branchService;

    @Value("${upload.ks3.resDomain}")
    private String resDomain;

    /**
     * 跳转到列表页
     *
     * @return
     */
    @RequestMapping("/visaApply")
    public String list(String studentNo, Model model) {
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(studentNo);
        studentInfo.setDeleteStatus(false);
        studentInfo = studentService.get(studentInfo);
        if (studentInfo != null) {
            model.addAttribute("nationStatus", studentInfo.getNationStatus());
        }

        /* 非同业除了拒签且审批同意的签证结果以外还有其他的签证结果不可添加签证申请 */
        boolean canAddVisaApply = true;
        if(!studentInfo.getChannelStatus().equals(1)){
            VisaApplyInfo visaApplyInfo = new VisaApplyInfo();
            visaApplyInfo.setStudentNo(studentNo);
            visaApplyInfo = visaApplyService.get(visaApplyInfo);
            if (visaApplyInfo != null) {
                VisaResultInfo visaResultInfo = new VisaResultInfo();
                visaResultInfo.setStudentNo(studentNo);
                List<VisaResultInfo> list = visaResultService.getList(visaResultInfo);
                if (list != null && list.size() > 0) {
                    for (VisaResultInfo item : list) {
                        if (item.getVisaResult().equals(0) && item.getVisaStatus().equals(Contants.APPLYSTATUS_ACCEPT)) {

                        } else {
                            canAddVisaApply = false;
                            break;
                        }
                    }
                }
            }
        }
        VisaRecordInfo visaRecordInfo = new VisaRecordInfo();
        visaRecordInfo.setStudentNo(studentNo);
        visaRecordInfo.setDeleteStatus(false);
        visaRecordInfo = visaRecordService.get(visaRecordInfo);
        if (visaRecordInfo != null) {
            model.addAttribute("has_visaRecord", 1);
        }else{
            model.addAttribute("has_visaRecord", 0);
        }

        model.addAttribute("canAddVisaApply", canAddVisaApply);
        model.addAttribute("studentNo", studentNo);

        SysUser sysUser = userService.getLoginUser();
        List<String> listRoles = roleService.getRolesByOaId(sysUser.getOaid());
        model.addAttribute("canEdit", listRoles.contains("文案经理") || listRoles.contains("签证经理") || listRoles.contains("文签总监"));
        return "visaApply/list";
    }



    @RequestMapping("/visaApply/chooseCountry")
    public String chooseCountry(String studentNo,String type, Model model) {
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setDeleteStatus(false);
        studentInfo.setStudentNo(studentNo);
        studentInfo = studentService.get(studentInfo);
        Integer nationStatus = studentInfo.getNationStatus();
        if (studentInfo != null) {
            model.addAttribute("studentNo", studentNo);

            DoubleSignInfo doubleSignInfo = new DoubleSignInfo();
            doubleSignInfo.setDoubleSignCode(nationStatus);
            doubleSignInfo.setDeleteStatus(false);
            List<DoubleSignInfo> list_doubleSignCountry = doubleSignService.getList(doubleSignInfo);
            if (list_doubleSignCountry != null && list_doubleSignCountry.size() > 0) {
                model.addAttribute("list_doubleSignCountry", list_doubleSignCountry);
            }
        }
        model.addAttribute("type",type);
        return "visaApply/chooseCountry";
    }

    /**
     * 分页查询签证申请列表
     *
     * @param visaApplyInfo
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping(value = "visaApply/list/query", method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel get(VisaApplyInfo visaApplyInfo, PageParam pageParam, BasePageModel basePageModel) {
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength());
        String[] str = new String[]{"id", "student_no", "visa_way", "visa_type", "send_date", "visa_comment", "create_time", "delete_time", "delete_status", "operator_no", "apply_audit_status"};
        visaApplyService.getList(visaApplyInfo);
        pageParam.setSortPro(str[pageParam.getiSortCol_0()]);
        return dataTableWapper(page, basePageModel);
    }

    @RequestMapping("/visaApply/manageList")
    public String manageList(Model model){
        CountryInfo countryInfo = new CountryInfo();
        model.addAttribute("countryList",countryService.getList(countryInfo));

        BranchInfo branchInfo = new BranchInfo();
        model.addAttribute("branchList",branchService.getList(branchInfo));
        return "visaApply/manageList";
    }

    /**
     * 分页查询签证申请列表
     *
     * @param searchOption
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping(value = "visaApply/manageList/query", method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel manageGet(SearchOptionVisaApply searchOption, PageParam pageParam, BasePageModel basePageModel) {
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength());
        String[] str = new String[]{"id", "student_no", "send_date", "create_time", "operator_no", "apply_audit_status","visa_way","student_name","operator_name","visa_id","visa_status","visaResult_createTime"};
        visaApplyService.getManegeList(searchOption);
        return dataTableWapper(page, basePageModel);
    }


    /**
     * 跳转到添加页
     *
     * @return
     */
    @RequestMapping("/visaApply/addPage")
    public String add(String studentNo, Integer doubleSignId, Model model) {
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(studentNo);
        studentInfo = studentService.get(studentInfo);
        model.addAttribute("studentInfo", studentInfo);

        // 英国和加拿大显示审案员为当前顾问的组长
        if (studentInfo.getNationId().equals(CountryEnum.England.getCode()) || studentInfo.getNationId().equals(CountryEnum.Canada.getCode())) {
            String check = getTrial();
            if (!StringUtils.isEmpty(check)) {
                String[] strings = check.split("&");
                model.addAttribute("checkNo", strings[0]);
                model.addAttribute("checkName", strings[1]);
            }
        }
        model.addAttribute("country", studentInfo.getNationId());

        Integer nationStatus = studentInfo.getNationStatus();
        Integer nationId = 0;
        if (nationStatus.equals(DoubleSignTypeEnum.AUS_NEW.getNationStatus()) || nationStatus.equals(DoubleSignTypeEnum.UK_HK.getNationStatus()) || nationStatus.equals(DoubleSignTypeEnum.SG_HK.getNationStatus())) {
            if(doubleSignId != null){
                DoubleSignInfo doubleSignInfo = new DoubleSignInfo();
                doubleSignInfo.setId(doubleSignId);
                doubleSignInfo.setDeleteStatus(false);
                doubleSignInfo = doubleSignService.get(doubleSignInfo);
                if(doubleSignInfo != null){
                    nationId = doubleSignInfo.getNationId();
                    model.addAttribute("doubleSignCountry", doubleSignInfo);
                }
            }
        }else{
            nationId = studentInfo.getNationId();
        }
        model.addAttribute("VisaType", this.getVisaType(nationId, null));
        return "visaApply/add";
    }

    /**
     * 保存添加的信息
     *
     * @param visaApplyInfo
     * @return
     */
    @RequestMapping(value = "/visaApply/add", method = RequestMethod.POST)
    @ResponseBody
    public Boolean actionAdd(VisaApplyInfo visaApplyInfo) {
        SysUser sysUser = userService.getLoginUser();
        visaApplyInfo.setCreateTime(new Date());
        visaApplyInfo.setUpdateTime(new Date());
        visaApplyInfo.setDeleteStatus(false);
        visaApplyInfo.setOperatorNo(sysUser.getOaid());
        visaApplyInfo.setOperatorName(sysUser.getUsername());
        visaApplyInfo.setApplyAuditStatus(Contants.APPLYSTATUS_SUBMIT);
        return visaApplyService.add(visaApplyInfo);
    }

    /**
     * 删除信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/visaApply/delete", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public Boolean actionDelete(Integer id) {
        VisaApplyInfo visaApplyInfo = new VisaApplyInfo();
        visaApplyInfo.setId(id);
        visaApplyInfo.setDeleteStatus(false);
        visaApplyInfo = visaApplyService.get(visaApplyInfo);
        if (visaApplyInfo == null) {
            return false;
        }
        return visaApplyService.delete(visaApplyInfo) > 0;
    }

    /**
     * 跳转到编辑页
     *
     * @return
     */
    @RequestMapping("/visaApply/editPage")
    public String edit(Integer id, Model model) {
        VisaApplyInfo visaApplyInfo = new VisaApplyInfo();
        visaApplyInfo.setId(id);
        visaApplyInfo = visaApplyService.get(visaApplyInfo);
        model.addAttribute("visaApplyInfo", visaApplyInfo);

        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(visaApplyInfo.getStudentNo());
        studentInfo = studentService.get(studentInfo);
        model.addAttribute("studentInfo", studentInfo);
        model.addAttribute("country", studentInfo.getNationId());


        Integer nationStatus = studentInfo.getNationStatus();
        if (nationStatus.equals(DoubleSignTypeEnum.AUS_NEW.getNationStatus()) || nationStatus.equals(DoubleSignTypeEnum.UK_HK.getNationStatus()) || nationStatus.equals(DoubleSignTypeEnum.SG_HK.getNationStatus())) {
            DoubleSignInfo doubleSignInfo = new DoubleSignInfo();
            doubleSignInfo.setId(visaApplyInfo.getStudentNation());
            doubleSignInfo.setDeleteStatus(false);
            doubleSignInfo = doubleSignService.get(doubleSignInfo);
            if (doubleSignInfo != null) {
                model.addAttribute("doubleSignCountry", doubleSignInfo);
                model.addAttribute("VisaType", this.getVisaType(doubleSignInfo.getNationId(), visaApplyInfo.getStudentVisaStatus() ? 1 : 0));
                model.addAttribute("country", doubleSignInfo.getNationId());
            }
        }

        //签证结果
        VisaResultInfo visaResultInfo = visaResultService.getByVisaId(id);
        if (visaResultInfo != null) {
            model.addAttribute("hasResult", "1");
            model.addAttribute("visaResultInfo", visaResultInfo);
        }


        model.addAttribute("resDomain", resDomain);
        return "visaApply/edit";
    }

    /**
     * 保存编辑的信息
     *
     * @param visaApplyInfo
     * @return
     */
    @RequestMapping(value = "/visaApply/save", method = RequestMethod.POST)
    @ResponseBody
    public String actionEdit(VisaApplyInfo visaApplyInfo) {
        SysUser sysUser = userService.getLoginUser();
        VisaApplyInfo visaApplyInfo1 = new VisaApplyInfo();
        visaApplyInfo1.setId(visaApplyInfo.getId());
        visaApplyInfo1.setDeleteStatus(false);
        visaApplyInfo1 = visaApplyService.get(visaApplyInfo1);
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();

        if (visaApplyInfo.getVisaType() == null) {
            visaApplyInfo.setVisaType(-1);
        }
        if (visaApplyInfo1.getApplyAuditStatus().equals(Contants.APPLYSTATUS_AUDITING) || visaApplyInfo1.getApplyAuditStatus().equals(Contants.APPLYSTATUS_ACCEPT)) {
            jsonObject.put("code", 0);
        } else {
            Integer auditStatus = visaApplyInfo.getApplyAuditStatus();
            if (auditStatus.equals(Contants.APPLYSTATUS_REJECT)) {
                visaApplyInfo.setApplyAuditStatus(Contants.APPLYSTATUS_SUBMIT);
                auditApplyService.add(visaApplyInfo.getId(), CaseIdEnum.VisaApply.getCode(), 1, visaApplyInfo.getStudentNo(), "", "");
            } else if (auditStatus.equals(Contants.APPLYSTATUS_SUBMIT)) {
                if (auditApplyService.get(visaApplyInfo.getId(), CaseIdEnum.VisaApply.getCode()) == null) {
                    auditApplyService.add(visaApplyInfo.getId(), CaseIdEnum.VisaApply.getCode(), 1, visaApplyInfo.getStudentNo(), "", "");
                }
            }
            visaApplyInfo.setOperatorNo(sysUser.getOaid());
            visaApplyInfo.setOperatorName(sysUser.getUsername());
            visaApplyInfo.setUpdateTime(new Date());
            if (visaApplyService.update(visaApplyInfo) > 0) {
                jsonObject.put("code", 1);
            } else {
                jsonObject.put("code", 2);
            }
        }
        return jsonObject.toString();
    }

    /**
     * 跳转到详情页
     *
     * @return
     */
    @RequestMapping("/visaApply/detailPage")
    public String detail(Integer id, String studentNo, Model model) {
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(studentNo);
        studentInfo.setDeleteStatus(false);
        studentInfo = studentService.get(studentInfo);
        if (studentInfo != null) {
            model.addAttribute("studentInfo", studentInfo);
            if (studentInfo.getNationId().equals(4) || studentInfo.getNationId().equals(40) || studentInfo.getNationId().equals(41)) {
                model.addAttribute("isAmerica", "1");
            }
            model.addAttribute("country", studentInfo.getNationId());
            model.addAttribute("stuNo", studentNo);
        }

        VisaApplyInfo visaApplyInfo = new VisaApplyInfo();
        visaApplyInfo.setId(id);
        visaApplyInfo.setStudentNo(studentNo);
        visaApplyInfo = visaApplyService.get(visaApplyInfo);
        if (visaApplyInfo != null) {
            model.addAttribute("visaApplyInfo", visaApplyInfo);
            // 签证申请的待审批
            model.addAttribute("toAudit_apply", auditApplyService.get(visaApplyInfo.getId(), CaseIdEnum.VisaApply.getCode()));
            // 签证申请的审批结果
            model.addAttribute("list_case_5", auditResultService.getList(CaseIdEnum.VisaApply.getCode(), visaApplyInfo.getId()));
            // 签证申请中的签证类型
            List<VisaType> list = new ArrayList<VisaType>();
            list = visaApplyService.getVisaTypeList();
            for (VisaType item : list) {
                if (item.getId().equals(visaApplyInfo.getVisaType())) {
                    model.addAttribute("VisaName", item.getVisaName());
                    break;
                }
            }

            if (studentInfo != null) {
                Integer nationStatus = studentInfo.getNationStatus();
                if (nationStatus.equals(DoubleSignTypeEnum.AUS_NEW.getNationStatus()) || nationStatus.equals(DoubleSignTypeEnum.UK_HK.getNationStatus()) || nationStatus.equals(DoubleSignTypeEnum.SG_HK.getNationStatus())) {
                    DoubleSignInfo doubleSignInfo = new DoubleSignInfo();
                    doubleSignInfo.setId(visaApplyInfo.getStudentNation());
                    doubleSignInfo.setDeleteStatus(false);
                    model.addAttribute("doubleSignCountry", doubleSignService.get(doubleSignInfo));
                    model.addAttribute("country", doubleSignInfo.getNationId());
                }
            }
        }

        VisaResultInfo visaResultInfo = visaResultService.getByVisaId(id);
        if (visaResultInfo != null) {
            model.addAttribute("visaResultInfo", visaResultInfo);
            // 签证结果的待审批
            model.addAttribute("toAudit_result", auditApplyService.get(visaResultInfo.getId(), CaseIdEnum.VisaResult.getCode()));
            // 签证结果的审批结果
            model.addAttribute("list_case_6", auditResultService.getList(CaseIdEnum.VisaResult.getCode(), visaResultInfo.getId()));
        }

        /* 获签 */
        VisaRecordInfo visaRecordInfo = new VisaRecordInfo();
        visaRecordInfo.setStudentNo(studentNo);
        visaRecordInfo.setDeleteStatus(false);
        visaRecordInfo = visaRecordService.get(visaRecordInfo);
        if (visaRecordInfo != null) {
            model.addAttribute("visaRecordInfo", visaRecordInfo);

            FollowServiceInfo followServiceInfo = new FollowServiceInfo();
            followServiceInfo.setStudentNo(studentNo);
            followServiceInfo.setDeleteStatus(false);
            List<FollowServiceInfo> list_followInfo = followServiceInfoService.getList(followServiceInfo);
            if (list_followInfo.size() > 0) {
                for (FollowServiceInfo item : list_followInfo) {
                    if (item.getVisitType() == FollowTypeEnum.GUARDIANSHIP.getCode()) {
                        model.addAttribute("guardianFee", item.getFee());
                        break;
                    }
                }
            }

            List<VisaResultRecordInfo> list_visaResultRecordInfo = visaResultRecordService.getListByRecordId(visaRecordInfo.getId());
            for (VisaResultRecordInfo item : list_visaResultRecordInfo) {
                SimpleDateFormat sdf = new SimpleDateFormat(Contants.datePattern);
                try {
                    if (!com.mysql.jdbc.StringUtils.isNullOrEmpty(item.getCourseEndTime())) {
                        item.setCourseEndTimeDate(sdf.parse(item.getCourseEndTime()));
                    }
                } catch (Exception ex) {

                }
            }
            model.addAttribute("list_visaResultRecordInfo", list_visaResultRecordInfo);
            // 获签信息的待审批信息
            AuditApplyInfo auditApplyInfo = auditApplyService.get(visaRecordInfo.getId(), CaseIdEnum.VisaRecord.getCode());
            if (auditApplyInfo != null) {
                model.addAttribute("toAudit", auditApplyInfo);
                model.addAttribute("toAudit_oaId", auditApplyInfo.getOaid());
                model.addAttribute("toAudit_name", auditApplyInfo.getOaName());
            }
            // 获签信息的审批结果
            model.addAttribute("list_auditResult", auditResultService.getList(CaseIdEnum.VisaRecord.getCode(), visaRecordInfo.getId()));
            CurrencyInfo currencyInfo = new CurrencyInfo();
            List<CurrencyInfo> list_currencyInfo = currencyInfoService.getList(currencyInfo);
            model.addAttribute("list_currencyInfo", list_currencyInfo);
        }
        /* 获签 */
        return "visaApply/detail";
    }

    /**
     * 根据是否是学生签证及国家获取签证申请中的签证类型
     *
     * @param nationId
     * @return
     */
    public List<VisaType> getVisaType(Integer nationId, Integer studentVisaStatus) {
        List<VisaType> list = new ArrayList<>();
        if (nationId.equals(40) || nationId.equals(41)) {
            list = visaApplyService.getVisaTypeList(4);
        } else {
            list = visaApplyService.getVisaTypeList(nationId);
        }
        if (studentVisaStatus != null) {
            boolean param_studentVisaStatus = studentVisaStatus.equals(1) ? true : false;
            list = list.stream().filter(item -> item.getStudentVisaStatus().equals(param_studentVisaStatus)).collect(Collectors.toList());
        }
        return list;
    }

    /**
     * 获取签证分类
     *
     * @return
     */
    @RequestMapping(value = "/visaApply/getVisaTypeListBy", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String actionGetVisaTypeListBy(Integer studentVisaStatus, Integer nation) {
        return JSONArray.toJSONString(visaApplyService.getVisaTypeListBy(studentVisaStatus, nation));
    }

    /**
     * 获取审案员
     *
     * @return
     */
    public String getTrial() {
        // 1.获取当前用户所在组
        SysUser sysUser = userService.getLoginUser();
        UserGroupRelation userGroupRelation = new UserGroupRelation();
        userGroupRelation.setOaid(sysUser.getOaid());
        userGroupRelation.setDeleteStatus(false);
        userGroupRelation = userGroupRelationService.get(userGroupRelation);
        // 2.获取上边组组长
        if (userGroupRelation != null) {
            UserGroupRelation userGroupRelation1 = new UserGroupRelation();
            userGroupRelation1.setLeaderStatus(true);
            userGroupRelation1.setGroupId(userGroupRelation.getGroupId());
            userGroupRelation1.setDeleteStatus(false);
            userGroupRelation1 = userGroupRelationService.get(userGroupRelation1);
            // 3.获取人员姓名
            if (userGroupRelation1 != null) {
                SysUser sysUserResult = new SysUser();
                sysUserResult.setOaid(userGroupRelation1.getOaid());
                sysUserResult.setDeleteStatus(false);
                List<SysUser> list = userService.getList(sysUserResult);
                if (list != null && list.size() > 0) {
                    return list.get(0).getOaid() + "&" + list.get(0).getUsername();
                }
            }
        }
        return "";
    }

    /**
     * 查询工作台"我提交的 - 待审批的签证申请"详细信息
     *
     * @param visaApplyInfo
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping(value = "visaApply/todo/query", method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel getTodoList(VisaApplyInfo visaApplyInfo, PageParam pageParam, BasePageModel basePageModel) {
        String[] str = new String[]{"id", "student_no", "create_time", "operator_name"};
        Page<?> page = pageWapper(pageParam, str);
        SysUser sysUser = userService.getLoginUser();
        String oaid = sysUser.getOaid();
        visaApplyService.getToAuditListWithMySubmited(oaid);
        return dataTableWapper(page, basePageModel);
    }

    /**
     * 查询工作台"待我审批的 - 签证申请"详细信息
     *
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping(value = "toAuditVisaApply/todo/query", method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel getToAuditList(PageParam pageParam, BasePageModel basePageModel) {

        String[] str = new String[]{"id", "student_no", "create_time", "operator_name"};
        Page<?> page = pageWapper(pageParam, str);
        SysUser sysUser = userService.getLoginUser();
        String oaid = sysUser.getOaid();
        List<VisaApplyInfo> visaApplyInfoList = visaApplyService.getToAuditList(oaid);
        for (VisaApplyInfo visa : visaApplyInfoList) {
            if (org.springframework.util.StringUtils.hasText(visa.getStudentNo())) {
                StudentInfo studentInfo = studentService.getStudentInfoByStudentNo(visa.getStudentNo());
                if (studentInfo != null) {
                    visa.setStudentName(studentInfo.getStudentName());
                }
            }
        }
        return dataTableWapper(page, basePageModel);
    }
}