package com.aoji.controller;

import com.aoji.contants.CaseIdEnum;
import com.aoji.contants.Contants;
import com.aoji.contants.ContentException;
import com.aoji.mapper.AuditDingdingInfoMapper;
import com.aoji.model.*;
import com.aoji.model.res.SchoolData;
import com.aoji.service.*;
import com.aoji.vo.ApplyResultVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.StringUtil;
import com.itextpdf.xmp.impl.Base64;
import org.json.JSONObject;
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
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author yangsaixing
 * @description 申请结果控制器
 * @date Created in 下午2:25 2017/12/7
 */
@Controller
public class ApplyResultController extends BaseController {

    @Autowired
    ApplyResultService applyResultService;

    @Autowired
    ApplyCollegeService applyCollegeService;

    @Autowired
    AuditResultService auditResultService;

    @Autowired
    AuditApplyService auditApplyService;

    @Autowired
    UserService userService;

    @Autowired
    CurrencyInfoService currencyInfoService;

    @Autowired
    AuditDingdingInfoMapper auditDingdingInfoMapper;

    @Autowired
    StudentService studentService;

    @Autowired
    CountryService countryService;

    @Autowired
    VisaRecordService visaRecordService;

    @Value("${upload.ks3.resDomain}")
    private String resDomain;

    public static final Logger logger = LoggerFactory.getLogger(ApplyResultController.class);

    @Autowired
    CostService costService;




    /**
     * 跳转到（编辑注册费学费押金）列表页
     * @return
     */
    @RequestMapping("/cost/basicCostPage")
    public String basicCostPage(HttpServletRequest request , Integer costId,String studentNo,Model model){
        ApplyInfo applyInfo = new ApplyInfo();
        applyInfo.setId(costId);
        applyInfo.setStudentNo(studentNo);
        model.addAttribute("basicApplyInfo",applyInfo);
        return "applyResult/basicCost";
    }

    /**
     * 跳转到列表页
     * @return
     */
    @RequestMapping("/cost/page")
    public String costPage(HttpServletRequest request){
        String studentNo = request.getParameter("studentNo");
        StudentCostInfo studentCostInfo = new StudentCostInfo();
        studentCostInfo.setStudentNo(studentNo);
        studentCostInfo.setDeleteStatus(false);
        costService.insertStuentCostInfo(studentCostInfo);
        return "applyResult/costs";
    }
    /**
     * 跳转到列表页
     * @return
     */
    @RequestMapping(value = "/cost/list")
    @ResponseBody
    public BasePageModel costQuery(StudentCostInfo studentCostInfo,PageParam pageParam,BasePageModel basePageModel){
        Page<?> page = pageWapper(pageParam, propList());
        costService.getCostById(studentCostInfo);
        return dataTableWapper(page, basePageModel);
    }

    /**
     * 跳转到列表页
     * @return
     */
    @RequestMapping("/applyResult")
    public String list(Model model,Integer applyId){
        ApplyInfo applyInfo = applyCollegeService.getById(applyId);
        model.addAttribute("applyStatus",applyInfo.getApplyStatusCode());
        StudentInfo student = this.studentService.getStudentInfoByStudentNo(applyInfo.getStudentNo());
        model.addAttribute("nationId",student.getNationId());
        if(student.getNationStatus() == null)
        {
            model.addAttribute("nationStatus", 0);
        }else {
            model.addAttribute("nationStatus", student.getNationStatus());
        }

        return "applyResult/list";
    }

    /**
     * 保存编辑的信息
     * @param applyResultVo
     * @return
     */
    @RequestMapping(value = "/applyResult/save",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse detail(ApplyResultVo applyResultVo) {
        BaseResponse baseResponse = new BaseResponse();
        try{
           return applyResultService.saveResultAndSchool(applyResultVo);
        }catch (Exception e){
            baseResponse.setResult(false);
            return baseResponse;
        }
    }
    /**
     * 跳转到申请结果编辑页
     * @return
     */
    @RequestMapping("/applyResult/editPage")
    public String editPage(Integer id,Integer applyId,Model model,String nationId){
        ApplyResultInfo applyResult=applyResultService.getById(id);
        ApplyInfo applyInfo=applyCollegeService.getById(applyId);
        if(applyInfo==null){
            applyInfo=new ApplyInfo();
        }
        if(applyResult==null){
            applyResult=new ApplyResultInfo();
        }
        //获取所有币种
        List<CurrencyInfo> clist = this.currencyInfoService.getList(null);
        model.addAttribute("resDomain",resDomain);
        model.addAttribute("clist",clist);
        model.addAttribute("apply",applyInfo);
        model.addAttribute("applyResult",applyResult);

        //绑定院校课程专业
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(applyInfo.getStudentNo());
        studentInfo.setDeleteStatus(false);
        studentInfo = studentService.get(studentInfo);

        String studentNationName = studentInfo.getNationName();
        model.addAttribute("studentNationName",studentNationName);
        model.addAttribute("student_nation_id",StringUtils.hasText(nationId) ? nationId:studentInfo.getNationId());

        CountryInfo countryInfo = new CountryInfo();
        if(StringUtils.hasText(nationId)){
            countryInfo.setId(Integer.valueOf(nationId));
        }else{
            countryInfo.setId(studentInfo.getNationId());
        }
        countryInfo = countryService.get(countryInfo);

        List<SchoolData> listSchoolDistinct = visaRecordService.getSchool1(countryInfo.getCountryXiaoxi());
        model.addAttribute("school",listSchoolDistinct);

        Integer courseType = 0;
        if(applyInfo!=null&&applyInfo.getCourseType()!=null){
            switch (applyInfo.getCourseType()){
                case 1:courseType = 2;break;
                case 2:courseType = 3;break;
                case 3:courseType = 1;break;
                case 4:courseType = 4;break;
                case 5:courseType = 5;break;
            }
        }


        List<SchoolData> listMajor1 = visaRecordService.getMajors(applyInfo.getCollegeId(),courseType,applyInfo.getEducationSection());
        List<SchoolData> listCourse1 = visaRecordService.getDegrees(applyInfo.getCollegeId(),applyInfo.getMajorId());
        List<SchoolData> listAgency1 = visaRecordService.getCooperations(applyInfo.getCollegeId());

        model.addAttribute("listCourse1",listCourse1);
        model.addAttribute("listMajor1",listMajor1);
        model.addAttribute("listAgency1",listAgency1);

        return "applyResult/edit";
    }


    /**
     * 跳转到申请结果详情页
     * @return
     */
    @RequestMapping("/applyResult/showDetailPage")
    public String showDetailPage(Integer id,Model model,HttpServletRequest request,Integer applyId){
        SysUser user = userService.getLoginUser();
        String requestHeader = request.getHeader("user-agent");
        ApplyResultInfo applyResult= applyResultService.getById(id);
        ApplyInfo applyInfo=applyCollegeService.getById(applyId);


        List<AuditResultInfo> list_case_3 = auditResultService.getList(CaseIdEnum.ApplyResult.getCode(),id);
        model.addAttribute("list_case_3",list_case_3);
//        if(list_case_3 == null || list_case_3.size() == 0){
        //登录人是否可以审批， 具有审批权限的用户不可以修改
        AuditApplyInfo auditApplyInfo = auditApplyService.get(id,CaseIdEnum.ApplyResult.getCode());
        if (auditApplyInfo != null && user != null && user.getOaid().equals(auditApplyInfo.getOaid())){
            model.addAttribute("canApprove", true);
        }
//        }
        //待审批人
        AuditApplyInfo auditApplyInfo_apply = auditApplyService.get(id,CaseIdEnum.ApplyResult.getCode());
        if(auditApplyInfo_apply != null){
            applyResult.setAuditStatus(1);
            model.addAttribute("toAudit_apply",auditApplyInfo_apply);
            SysUser sysUser = new SysUser();
            sysUser.setOaid(auditApplyInfo_apply.getOaid());
            sysUser.setDeleteStatus(false);
            List<SysUser> list_user = userService.getList(sysUser);
            if(list_user.size() > 0){
                model.addAttribute("toAudit_apply_name",list_user.get(0).getUsername());
            }
        }else{
            model.addAttribute("notShow",1);
        }
        model.addAttribute("applyResult",applyResult);
        model.addAttribute("apply",applyInfo);
        model.addAttribute("resDomain",resDomain);
        //获取所有币种
        List<CurrencyInfo> clist = this.currencyInfoService.getList(null);
        model.addAttribute("clist",clist);
         return "applyResult/show_detail";
    }




    /**
     * 跳转到申请结果详情页
     * @return
     */
    @RequestMapping("/applyResult/detailPage")
    public String detail(Integer id,Model model,HttpServletRequest request,Integer applyId){
        SysUser user = userService.getLoginUser();
        String requestHeader = request.getHeader("user-agent");
        ApplyResultInfo applyResult= applyResultService.getById(id);
        ApplyInfo applyInfo=applyCollegeService.getById(applyId);


        List<AuditResultInfo> list_case_3 = auditResultService.getList(CaseIdEnum.ApplyResult.getCode(),id);
        model.addAttribute("list_case_3",list_case_3);
//        if(list_case_3 == null || list_case_3.size() == 0){
            //登录人是否可以审批， 具有审批权限的用户不可以修改
            AuditApplyInfo auditApplyInfo = auditApplyService.get(id,CaseIdEnum.ApplyResult.getCode());
            if (auditApplyInfo != null && user != null && user.getOaid().equals(auditApplyInfo.getOaid())){
                model.addAttribute("canApprove", true);
            }
//        }
        //待审批人
        AuditApplyInfo auditApplyInfo_apply = auditApplyService.get(id,CaseIdEnum.ApplyResult.getCode());
        if(auditApplyInfo_apply != null){
            applyResult.setAuditStatus(1);
            model.addAttribute("toAudit_apply",auditApplyInfo_apply);
            SysUser sysUser = new SysUser();
            sysUser.setOaid(auditApplyInfo_apply.getOaid());
            sysUser.setDeleteStatus(false);
            List<SysUser> list_user = userService.getList(sysUser);
            if(list_user.size() > 0){
                model.addAttribute("toAudit_apply_name",list_user.get(0).getUsername());
            }
        }else{
            model.addAttribute("notShow",1);
        }
        model.addAttribute("applyResult",applyResult);
        model.addAttribute("apply",applyInfo);
        //获取所有币种
        List<CurrencyInfo> clist = this.currencyInfoService.getList(null);
        model.addAttribute("clist",clist);
        StudentInfo studentInfo = new StudentInfo();
        String studentName = "";
        String studentNo = "";
        if(StringUtils.hasText(applyInfo.getStudentNo())){
            studentNo = applyInfo.getStudentNo();
            studentInfo = studentService.getStudentInfoByStudentNo(applyInfo.getStudentNo());
            if(studentInfo != null){
                studentName = studentInfo.getStudentName();
            }
        }
        model.addAttribute("studentNo",studentNo);
        model.addAttribute("studentName",studentName);
        if(isMobileDevice(requestHeader)){
            return "applyResult/mobile_detail";
        }else{
            return "applyResult/detail";
        }


    }

    /**
     * 审批
     * @param applyId id
     * @param type 通过 / 拒绝
     * @param remark 备注
     * @return
     */
    @RequestMapping(value = "/applyResult/approve")
    @ResponseBody
    public BaseResponse approve(Integer applyId, Integer type, String remark, Integer id, String studentNo, String updateTime){
        logger.info(MessageFormat.format(" COE approve: id:{0}; type:{1}; remark:{2}",applyId, type, remark));
        try{
            BaseResponse baseResponse = new BaseResponse();
            if(applyId == null || type == null || applyId == null){
                baseResponse.setResult(false);
                baseResponse.setErrorCode("3");
                baseResponse.setErrorMsg("请输入审批参数");
                return baseResponse;
            }
            return applyResultService.approve(id, type, remark,applyId,studentNo,updateTime,baseResponse);
        }catch(Exception e){
            throw new ContentException(1,"审批错误");
        }
    }

    /**
     * 跳转到添加课程信息页
     * @return
     */
    @RequestMapping("/course/addPage")
    public String addCoursePage(){
        return "applyResult/course/add";
    }



    /**
     * 分页查询申请结果列表
     * @param applyResultInfo
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping("/applyResult/list/query")
    @ResponseBody
    public BasePageModel get(ApplyResultInfo applyResultInfo, PageParam pageParam, BasePageModel basePageModel){
        Page<?> page = pageWapper(pageParam,propList());
       /* if(!StringUtils.hasText(sysRole.getRoleName())){
            sysRole.setRoleName(null);
        }sysRole*/
        applyResultInfo.setDeleteStatus(false);
        List<ApplyResultInfo> resultInfos=applyResultService.getByPageNPA(applyResultInfo);
        for(ApplyResultInfo result:resultInfos){
            if(!StringUtils.isEmpty(result.getCreateTime())) {
                result.setCreatedString(new SimpleDateFormat(Contants.datePattern).format(result.getCreateTime()));
            }
            if(!StringUtils.isEmpty(result.getResultDate())) {
                result.setResultDateString(new SimpleDateFormat(Contants.datePattern).format(result.getResultDate()));
            }
        }
        return dataTableWapper(page,basePageModel);
    }

    @RequestMapping("/applyResult/list/queryResult")
    @ResponseBody
    public BasePageModel queryResult(ApplyResultInfo applyResultInfo, PageParam pageParam, BasePageModel basePageModel){
        Page<?> page = pageWapper(pageParam,propList());
        applyResultInfo.setDeleteStatus(false);
        List<ApplyResultInfo> resultInfos=applyResultService.getByPageList(applyResultInfo);
        if(resultInfos != null && resultInfos.size() < 1){

        }
        for(ApplyResultInfo result:resultInfos){
            if(!StringUtils.isEmpty(result.getCreateTime())) {
                result.setCreatedString(new SimpleDateFormat(Contants.datePattern).format(result.getCreateTime()));
            }
            if(!StringUtils.isEmpty(result.getResultDate())) {
                result.setResultDateString(new SimpleDateFormat(Contants.datePattern).format(result.getResultDate()));
            }
        }
        return dataTableWapper(page,basePageModel);
    }

    public String[] propList(){
        return new String[]{"id","resultType","offerType","resultDateString","operatorNo","createdString"};
    }

    /**
     * 新增学生/学校的确认时间
     * @param request
     * @return
     */
    @RequestMapping("/applyResult/addTime")
    @ResponseBody
    public Boolean addTime(HttpServletRequest request){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String studentTime = request.getParameter("studentTime") == null ? "" : request.getParameter("studentTime");
        String schoolTime = request.getParameter("schoolTime") == null ? "" : request.getParameter("schoolTime");
        String id = request.getParameter("id") == null ? "" :request.getParameter("id");
        ApplyResultInfo applyResultInfo = new ApplyResultInfo();
        applyResultInfo.setId(Integer.valueOf(id));
        try{
            if(StringUtils.hasText(studentTime)){
                applyResultInfo.setStudentReplyDate(simpleDateFormat.parse(studentTime));
            }
            if(StringUtils.hasText(schoolTime)){
                applyResultInfo.setSchoolReplyDate(simpleDateFormat.parse(schoolTime));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        if(StringUtils.hasText(id)){
            applyResultService.update(applyResultInfo);
            return true;
        }
        return false;
    }

    /**
     * 删除申请结果
     * @param applyResultId
     * @return
     */
    @RequestMapping("applyResult/remove")
    @ResponseBody
    public Boolean remove(Integer applyResultId){
        return applyResultService.remove(applyResultId);
    }

    @RequestMapping("applyResult/addStudentTime")
    public String addStudentTime(HttpServletRequest request,Model model){
        model.addAttribute("applyResultId",request.getParameter("id"));
        return "applyResult/studentTime";
    }
    @RequestMapping("applyResult/addSchoolTime")
    public String addSchoolTime(HttpServletRequest request,Model model){
        model.addAttribute("applyResultId",request.getParameter("id"));
        return "applyResult/schoolTime";
    }

    public static boolean  isMobileDevice(String agent){
            boolean isMoblie = false;
            String[] mobileAgents = { "iphone", "android","ipad", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
                    "opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
                    "nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
                    "docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
                    "techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
                    "wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
                    "pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
                    "240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
                    "blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
                    "kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
                    "mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
                    "prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
                    "smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v",
                    "voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
                    "Googlebot-Mobile" };
                for (String mobileAgent : mobileAgents) {
                    if (agent.toLowerCase().indexOf(mobileAgent) >= 0&&agent.toLowerCase().indexOf("windows nt")<=0 &&agent.toLowerCase().indexOf("macintosh")<=0) {
                        isMoblie = true;
                        break;
                    }
                }

            return isMoblie;

    }

//    public static void main(String args[]){
//        String str = "http://192.168.0.25:8181/applyResult/detailPage?id=786547&applyId=58946&dateTime="+new Date();
//        logger.info("string:"+str);
//        logger.info("str:"+ Base64.encode(str));
//    }
}
