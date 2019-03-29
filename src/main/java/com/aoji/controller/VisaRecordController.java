package com.aoji.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.CaseIdEnum;
import com.aoji.contants.Contants;
import com.aoji.contants.DoubleSignTypeEnum;
import com.aoji.contants.FollowTypeEnum;
import com.aoji.model.*;
import com.aoji.model.res.School;
import com.aoji.model.res.SchoolData;
import com.aoji.model.res.SchoolRes;
import com.aoji.service.*;
import com.aoji.mapper.VisaRecordInfoMapper;
import com.aoji.utils.HttpUtils;
import com.aoji.vo.VisaRecordVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mysql.jdbc.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 赵剑飞
 * @description 获签信息控制器
 * @date Created in 下午2:25 2017/12/7
 */
@Controller
public class VisaRecordController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    VisaResultService visaResultService;
    @Autowired
    VisaRecordService visaRecordService;
    @Autowired
    ApplyCollegeService applyCollegeService;
    @Autowired
    VisaResultRecordService visaResultRecordService;
    @Autowired
    AuditApplyService auditApplyService;
    @Autowired
    AuditResultService auditResultService;
    @Autowired
    UserService userService;
    @Autowired
    StudentService studentService;
    @Autowired
    CountryService countryService;
    @Autowired
    FollowServiceInfoService followServiceInfoService;
    @Autowired
    CurrencyInfoService currencyInfoService;
    @Autowired
    VisaRecordInfoMapper visaRecordInfoMapper;
    @Autowired
    RoleService roleService;
    @Autowired
    VisaApplyService visaApplyService;
    @Autowired
    DoubleSignService doubleSignService;

    @Value("${upload.ks3.resDomain}")
    private String resDomain;

    /**
     * 跳转到添加页
     * @return
     */
    @RequestMapping("/visaRecord/addPage")
    public String add(String studentNo, Integer doubleSignId, Model model){
        FollowServiceInfo followServiceInfo = new FollowServiceInfo();
        followServiceInfo.setStudentNo(studentNo);
        followServiceInfo.setDeleteStatus(false);
        List<FollowServiceInfo> list_followInfo = followServiceInfoService.getList(followServiceInfo);
        if(list_followInfo.size() > 0){
            for (FollowServiceInfo item:list_followInfo){
                if(item.getVisitType() == FollowTypeEnum.GUARDIANSHIP.getCode()){
                    model.addAttribute("guardianFee",item.getFee());
                    break;
                }
            }
        }

        // 1.获取签证结果表的获签日期数据
        List<VisaResultInfo> visaResultInfos = visaResultService.getByStuNo(studentNo);
        if(visaResultInfos.size() > 0) {
            for(VisaResultInfo item : visaResultInfos){
                if(item.getVisaStatus().equals(Contants.APPLYSTATUS_ACCEPT) && item.getVisaResult().equals(1)){
                    model.addAttribute("resultTime", item.getResultTime());
                    break;
                }
            }
        }

        VisaRecordInfo visaRecordInfo = new VisaRecordInfo();
        visaRecordInfo.setStudentNo(studentNo);
        visaRecordInfo = visaRecordService.get(visaRecordInfo);
        if (visaRecordInfo != null){
            VisaApplyInfo visaApplyInfo = new VisaApplyInfo();
            visaApplyInfo.setStudentNo(studentNo);
            visaApplyInfo.setDeleteStatus(false);
            List<VisaApplyInfo> list = visaApplyService.getList(visaApplyInfo);
            if(list != null && list.size()>0) {
                visaApplyInfo = list.get(0);
                return "redirect:/visaApply/detailPage?id="+ visaApplyInfo.getId()+"&studentNo=" + studentNo;
            }else{
                return "redirect:/visaRecord/detailPage?studentNo=" + studentNo;
            }
        }

        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(studentNo);
        studentInfo.setDeleteStatus(false);
        studentInfo = studentService.get(studentInfo);
        Integer nationId = studentInfo.getNationId();
        Integer nationStatus = studentInfo.getNationStatus();
        if(doubleSignId != null){
            DoubleSignInfo doubleSignInfo = new DoubleSignInfo();
            doubleSignInfo.setId(doubleSignId);
            doubleSignInfo.setDeleteStatus(false);
            doubleSignInfo = doubleSignService.get(doubleSignInfo);
            if(doubleSignInfo != null){
                nationId = doubleSignInfo.getNationId();
                if (nationStatus.equals(DoubleSignTypeEnum.AUS_NEW.getNationStatus()) || nationStatus.equals(DoubleSignTypeEnum.UK_HK.getNationStatus()) || nationStatus.equals(DoubleSignTypeEnum.SG_HK.getNationStatus())) {
                    model.addAttribute("doubleSignCountry", doubleSignInfo);
                }
            }
        }

        if(nationId.equals(4) || nationId.equals(40)|| nationId.equals(41)){
            model.addAttribute("isAmerica","1");
        }
        model.addAttribute("nationStatus",studentInfo.getNationStatus());
        model.addAttribute("nation",nationId);
        model.addAttribute("contractType",studentInfo.getContractType());
        model.addAttribute("stuNo",studentNo);

        CountryInfo countryInfo = new CountryInfo();
        countryInfo.setId(nationId);
        countryInfo = countryService.get(countryInfo);
        model.addAttribute("school",visaRecordService.getSchool1(countryInfo.getCountryXiaoxi()));

        // 币种
        CurrencyInfo currencyInfo = new CurrencyInfo();
        List<CurrencyInfo> list_currencyInfo = currencyInfoService.getList(currencyInfo);
        model.addAttribute("list_currencyInfo",list_currencyInfo);

        /* 为了适配添加其他课程功能，再前端用js clone 样式跑飞，要是哪位好汉看不过去帮我改改哈 */
        List<Integer> listOtherCourse = new ArrayList<Integer>();
        for (Integer i = 0; i <= 15; i++) {
            listOtherCourse.add(i);
        }
        model.addAttribute("listOtherCourse", listOtherCourse);
        return "visaRecord/add";
    }

    /**
     * 保存获签信息
     * @param visaRecordVO
     * @return
     */
    @RequestMapping(value="/visaRecord/save",method = RequestMethod.POST)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Object actionAdd(VisaRecordVO visaRecordVO){
        Map<String, String> map = new HashMap<>(5);
        VisaRecordInfo visaRecordInfo = visaRecordVO.getVisaRecordInfo();
        visaRecordInfo.setAuditStatus(Contants.APPLYSTATUS_SUBMIT);
        visaRecordInfo.setCreateTime(new Date());
        visaRecordInfo.setUpdateTime(new Date());
        visaRecordInfo.setDeleteStatus(false);
        visaRecordInfo.setOperatorNo(userService.getLoginUser().getOaid());
        visaRecordInfo.setOperatorName(userService.getLoginUser().getUsername());
        boolean i = visaRecordService.add(visaRecordInfo);
        List<VisaResultRecordInfo> list = visaRecordVO.getList();
        if (i && list != null && list.size() > 0) {
            for (VisaResultRecordInfo item : list) {
                item.setDeleteStatus(false);
                item.setCreateTime(new Date());
                item.setOperatorNo(userService.getLoginUser().getOaid());
                item.setOperatorName(userService.getLoginUser().getUsername());
                item.setVisaRecordId(visaRecordInfo.getId());
                visaResultRecordService.add(item);
            }
        }
        map.put("code", "0");
        map.put("message", "操作成功");
        return map;
    }

    /**
     * 删除获签详细信息
     * @param id
     * @return
     */
    @RequestMapping(value="/visaRecord/delete",method = RequestMethod.POST)
    @ResponseBody
    public void actionDelete(Integer id){
        visaResultRecordService.delete(id);
    }

    /**
     * 跳转到编辑页
     * @return
     */
    @RequestMapping("/visaRecord/editPage")
    public String edit(String studentNo, Model model){
        FollowServiceInfo followServiceInfo = new FollowServiceInfo();
        followServiceInfo.setStudentNo(studentNo);
        followServiceInfo.setDeleteStatus(false);
        List<FollowServiceInfo> list_followInfo = followServiceInfoService.getList(followServiceInfo);
        if(list_followInfo.size() > 0){
            for (FollowServiceInfo item:list_followInfo){
                if(item.getVisitType() == FollowTypeEnum.GUARDIANSHIP.getCode()){
                    model.addAttribute("guardianFee",item.getFee());
                    break;
                }
            }
        }

        VisaRecordInfo visaRecordInfo = new VisaRecordInfo();
        visaRecordInfo.setStudentNo(studentNo);
        visaRecordInfo.setDeleteStatus(false);
        visaRecordInfo = visaRecordService.get(visaRecordInfo);
        model.addAttribute("visaRecordInfo",visaRecordInfo);

        model.addAttribute("stuNo",studentNo);
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(studentNo);
        studentInfo.setDeleteStatus(false);
        studentInfo = studentService.get(studentInfo);
        model.addAttribute("nationStatus",studentInfo.getNationStatus());

        Integer nationStatus = studentInfo.getNationStatus();
        Integer nationId = studentInfo.getNationId();
        if(visaRecordInfo.getStudentNation() != null){
            DoubleSignInfo doubleSignInfo = new DoubleSignInfo();
            doubleSignInfo.setId(visaRecordInfo.getStudentNation());
            doubleSignInfo.setDeleteStatus(false);
            doubleSignInfo = doubleSignService.get(doubleSignInfo);
            if(doubleSignInfo != null){
                nationId = doubleSignInfo.getNationId();
                if (nationStatus.equals(DoubleSignTypeEnum.AUS_NEW.getNationStatus()) || nationStatus.equals(DoubleSignTypeEnum.UK_HK.getNationStatus()) || nationStatus.equals(DoubleSignTypeEnum.SG_HK.getNationStatus())) {
                    model.addAttribute("doubleSignCountry", doubleSignInfo);
                }
            }
        }

        model.addAttribute("nation",nationId);
        if(nationId.equals(4) || nationId.equals(40)|| nationId.equals(41)){
            model.addAttribute("isAmerica","1");
            model.addAttribute("nation",4);
        }
        model.addAttribute("contractType",studentInfo.getContractType());
        CurrencyInfo currencyInfo = new CurrencyInfo();
        List<CurrencyInfo> list_currencyInfo = currencyInfoService.getList(currencyInfo);
        model.addAttribute("list_currencyInfo",list_currencyInfo);

        List<SchoolData> listSchool = new ArrayList<>();
        CountryInfo countryInfo = new CountryInfo();
        countryInfo.setId(nationId);
        countryInfo = countryService.get(countryInfo);
        listSchool = visaRecordService.getSchool1(countryInfo.getCountryXiaoxi());
        model.addAttribute("school",listSchool);

        List<VisaResultRecordInfo> list = visaResultRecordService.getListByRecordId(visaRecordInfo.getId());
        Integer counter = 0;
        for(VisaResultRecordInfo item:list){
            item.setI(counter);
            counter++;
            SimpleDateFormat sdf = new SimpleDateFormat(Contants.datePattern);
            try {
                if (!StringUtils.isNullOrEmpty(item.getCourseEndTime())) {
                    item.setCourseEndTimeDate(sdf.parse(item.getCourseEndTime()));
                }
            } catch (Exception ex) {

            }

            List<School> listCourse = new ArrayList<School>();

            Integer courseType = item.getCourseType();
            switch (item.getCourseType()){
                case 1:courseType = 2;break;
                case 2:courseType = 3;break;
                case 3:courseType = 1;break;
            }

            item.setMajorList(visaRecordService.getMajors(item.getCollegeId(),courseType,item.getEducationSection()));
            item.setCourseList(visaRecordService.getDegrees(item.getCollegeId(),item.getMajorId()));
            item.setCooperationList(visaRecordService.getCooperations(item.getCollegeId()));
        }
        model.addAttribute("list",list);

        model.addAttribute("resDomain",resDomain);

        /* 为了适配添加其他课程功能，再前端用js clone 样式跑飞，要是哪位好汉看不过去帮我改改哈 */
        List<Integer> listOtherCourse = new ArrayList<Integer>();
        for(Integer i = counter;i<=15;i++){
            listOtherCourse.add(i);
        }
        model.addAttribute("listOtherCourse",listOtherCourse);
        return "visaRecord/edit";
    }

    /**
     * 编辑获签信息
     * @param visaRecordVO
     * @return
     */
    @RequestMapping(value="/visaRecord/edit",method = RequestMethod.POST)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Object actionEdit(VisaRecordVO visaRecordVO){
        Map<String, String> map = new HashMap<>(5);
        VisaRecordInfo visaRecordInfo = visaRecordVO.getVisaRecordInfo();
        VisaRecordInfo visaRecordInfoTemp = new VisaRecordInfo();
        visaRecordInfoTemp.setId(visaRecordInfo.getId());
        visaRecordInfoTemp.setDeleteStatus(false);
        visaRecordInfoTemp = visaRecordService.get(visaRecordInfoTemp);

        visaRecordInfo.setUpdateTime(new Date());
        visaRecordInfo.setOperatorNo(userService.getLoginUser().getOaid());
        visaRecordInfo.setOperatorName(userService.getLoginUser().getUsername());

        Integer auditStatus = visaRecordInfoTemp.getAuditStatus();
        if (auditStatus.equals(Contants.APPLYSTATUS_SUBMIT)) {
            if(auditApplyService.get(visaRecordInfoTemp.getId(),CaseIdEnum.VisaRecord.getCode()) == null){
                auditApplyService.add(visaRecordInfoTemp.getId(), CaseIdEnum.VisaRecord.getCode(), 1, visaRecordInfoTemp.getStudentNo(),"","");
            }
        } else if (auditStatus.equals(Contants.APPLYSTATUS_REJECT)) {
            visaRecordInfo.setAuditStatus(Contants.APPLYSTATUS_SUBMIT);
            auditApplyService.add(visaRecordInfoTemp.getId(), CaseIdEnum.VisaRecord.getCode(), 1, visaRecordInfoTemp.getStudentNo(), "","");
        }
        boolean i = visaRecordService.update(visaRecordInfo) > 0;

        if(i){
            List<VisaResultRecordInfo> list = visaRecordVO.getList();
            if(list != null){
                for(VisaResultRecordInfo item:list){
                    Date date = new Date();
                    item.setUpdateTime(date);
                    item.setOperatorNo(userService.getLoginUser().getOaid());
                    item.setOperatorName(userService.getLoginUser().getUsername());
                    if(item.getId() == null){
                        item.setVisaRecordId(visaRecordInfo.getId());
                        item.setCreateTime(date);
                        item.setDeleteStatus(false);
                        visaResultRecordService.add(item);
                    }else{
                        visaResultRecordService.updateOne(item);
                    }
                }
            }
            map.put("code", "1");
        }
        return  map;
    }

    /**
     * 跳转到详情页
     * @return
     */
    @RequestMapping("/visaRecord/detailPage")
    public String detail(String studentNo,Model model){
        FollowServiceInfo followServiceInfo = new FollowServiceInfo();
        followServiceInfo.setStudentNo(studentNo);
        followServiceInfo.setDeleteStatus(false);
        List<FollowServiceInfo> list_followInfo = followServiceInfoService.getList(followServiceInfo);
        if(list_followInfo.size() > 0){
            for (FollowServiceInfo item:list_followInfo){
                if(item.getVisitType() == FollowTypeEnum.GUARDIANSHIP.getCode()){
                    model.addAttribute("guardianFee",item.getFee());
                    break;
                }
            }
        }

        VisaRecordInfo visaRecordInfo = new VisaRecordInfo();
        visaRecordInfo.setStudentNo(studentNo);
        visaRecordInfo.setDeleteStatus(false);
        visaRecordInfo = visaRecordService.get(visaRecordInfo);

        List<VisaResultRecordInfo> list = visaResultRecordService.getListByRecordId(visaRecordInfo.getId());
        for(VisaResultRecordInfo item:list){
            SimpleDateFormat sdf = new SimpleDateFormat(Contants.datePattern);
            try {
                if(!StringUtils.isNullOrEmpty(item.getCourseEndTime())){
                    item.setCourseEndTimeDate(sdf.parse(item.getCourseEndTime()));
                }
            }
            catch (Exception ex) {

            }

        }
        model.addAttribute("list",list);
        model.addAttribute("visaRecordInfo",visaRecordInfo);

        model.addAttribute("stuNo",studentNo);
        AuditApplyInfo auditApplyInfo = auditApplyService.get(visaRecordInfo.getId(),CaseIdEnum.VisaRecord.getCode());
        model.addAttribute("toAudit",auditApplyInfo);
        if(auditApplyInfo != null) {
            model.addAttribute("toAudit_oaId", auditApplyInfo.getOaid());
            model.addAttribute("toAudit_name", auditApplyInfo.getOaName());

            if(auditApplyService.currUserWithPermission("审批获签信息",userService.getLoginUser())){
                model.addAttribute("isAuditUser", true);
            }
            else{
                model.addAttribute("isAuditUser", false);
            }
        }

        List<AuditResultInfo> list_auditResult = auditResultService.getList(CaseIdEnum.VisaRecord.getCode(),visaRecordInfo.getId());
        model.addAttribute("list_auditResult",list_auditResult);

        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(studentNo);
        studentInfo.setDeleteStatus(false);
        studentInfo = studentService.get(studentInfo);
        model.addAttribute("studentInfo",studentInfo);
        if(studentInfo.getNationId().equals(4) || studentInfo.getNationId().equals(40) || studentInfo.getNationId().equals(41)){
            model.addAttribute("isAmerica","1");
        }

        CurrencyInfo currencyInfo = new CurrencyInfo();
        List<CurrencyInfo> list_currencyInfo = currencyInfoService.getList(currencyInfo);
        model.addAttribute("list_currencyInfo",list_currencyInfo);
        return "visaRecord/detail";
    }

    @RequestMapping(value="/visaRecord/getSchool",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public  String getSchool(Integer nationId){
        CountryInfo countryInfo = new CountryInfo();
        countryInfo.setId(nationId);
        countryInfo = countryService.get(countryInfo);
        List<SchoolData> list = visaRecordService.getSchool1(countryInfo.getCountryXiaoxi());
        return JSONArray.toJSONString(list);
    }

    @RequestMapping(value="/visaRecord/getCooperation",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public  String getCooperation(Integer schoolID){
        List<SchoolData> list = visaRecordService.getCooperations(schoolID);
        return JSONArray.toJSONString(list);
    }

    @RequestMapping(value="/visaRecord/getMajor",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public  String getMajor(Integer schoolID,Integer majorType,Integer educationSection){
        List<SchoolData> list = visaRecordService.getMajors(schoolID,majorType,educationSection);
        return JSONArray.toJSONString(list);
    }

    @RequestMapping(value="/visaRecord/getCourse",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public  String getCourse(Integer schoolID, Integer majorId){
        List<SchoolData> list = visaRecordService.getDegrees(schoolID,majorId);
        return JSONArray.toJSONString(list);
    }

    /**
     * 分页查询获签结果列表
     * @param visaRecordInfo
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping(value="visaRecord/list/query",method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel get(VisaRecordInfo visaRecordInfo, PageParam pageParam, BasePageModel basePageModel){
        String[] str = new String[]{"id","student_no","operator_name","create_time"};
        Page<?> page =pageWapper(pageParam,str);
        List<VisaRecordInfo> resultInfos = visaRecordService.getList(visaRecordInfo);
        for(VisaRecordInfo visa:resultInfos){
            if(org.springframework.util.StringUtils.hasText(visa.getStudentNo())){
                StudentInfo studentInfo=studentService.getStudentInfoByStudentNo(visa.getStudentNo());
                if(studentInfo!=null) {
                    visa.setStudentName(studentInfo.getStudentName());
                }
            }
        }
        return dataTableWapper(page,basePageModel);
    }

    /**
     * 查询工作台"待我审批的-获签信息"详细信息
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping(value="toAuditVisaRecord/todo/query",method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel getToAuditGetVisaList(PageParam pageParam, BasePageModel basePageModel){
        String[] str = new String[]{"id","student_no","create_time","operator_name" };
        Page<?> page=pageWapper(pageParam,str);
        SysUser sysUser = userService.getLoginUser();
        String oaid = sysUser.getOaid();
        List<VisaRecordInfo> visaRecordInfoList=visaRecordInfoMapper.selectToAuditGetVisaList(oaid);
        for(VisaRecordInfo visa:visaRecordInfoList){
            if(org.springframework.util.StringUtils.hasText(visa.getStudentNo())){
                StudentInfo studentInfo= studentService.getStudentInfoByStudentNo(visa.getStudentNo());
                if(studentInfo!=null) {
                    visa.setStudentName(studentInfo.getStudentName());
                }
            }
        }
        return dataTableWapper(page,basePageModel);
    }
}


