package com.aoji.controller;

import com.alibaba.fastjson.JSONArray;
import com.aoji.mapper.CommissionStudentMapper;
import com.aoji.model.*;
import com.aoji.model.res.School;
import com.aoji.model.res.SchoolData;
import com.aoji.service.*;
import com.aoji.vo.CommissionManageVO;
import com.aoji.vo.CommissionVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.mysql.jdbc.StringUtils;
import org.json.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wangjunqiang
 * @description 佣金信息管理列表
 * @date Created in 2018/6/11 9:32
 */
@Controller
public class CommissionManageController extends BaseController {


    public static final Logger logger = LoggerFactory.getLogger(CommissionManageController.class);

    @Autowired
    private CommissionManageService commissionManageService;

    @Autowired
    private CommissionSchoolService commissionSchoolService;


    @Autowired
    private VisaRecordService visaRecordService;

    @Autowired
    private CurrencyInfoService currencyInfoService;


    @Autowired
    private BranchService branchService;

    @Autowired
    private CommissionAusAgentService commissionAusAgentService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private CommissionRecordService commissionRecordService;

    @Autowired
    private UserService userService;

    @Value("${upload.ks3.resDomain}")
    private String resDomain;
    //佣金编辑页需要用到的
    @Value("${upload.ks3.newResDomain}")
    private String newResDomain;


    @Value("${commissionImageUrl}")
    private String commissionImageUrl;


    @RequestMapping("commissionManage/list")
    public String getCommissionManageList(Model model){
        //获取国家
        CountryInfo countryInfo = new CountryInfo();
        model.addAttribute("countryList",countryService.getList(countryInfo));
        return "commissionManage/commissionList";
    }

    @RequestMapping(value="commissionManage_queryStus",method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel get(HttpServletRequest request,CommissionManageVO commissionManageVO, PageParam pageParam, BasePageModel basePageModel){
        //获取登录用户的权限
        Integer roleStatus = commissionManageService.loginRoleName();
        Page<?> page = pageWapper(pageParam,propList());
        request.getSession().removeAttribute("commissionManageExcelList");
        Gson gson = new Gson();

        String toJson = gson.toJson(commissionManageVO);

        CommissionManageVO commissionManage = gson.fromJson(toJson, CommissionManageVO.class);
        request.getSession().setAttribute("commissionManageExcelList",commissionManage);


        commissionManageService.getCommissionManageList(commissionManageVO,roleStatus);
        return dataTableWapper(page,basePageModel);
    }

    @RequestMapping("commissionManage/edit")
    public String commissionManageEdit(Model model,Integer studentId){
        commissionManageService.getCommissionList(model,studentId);
        return "commissionManage/commissionEdit";
    }


    @RequestMapping("commissionManage/add")
    public String commissionManageAdd(Model model){
        commissionManageService.addCommissionManage(null,model);
        //visaRecordService.getCooperationsBycollegeId(item.getCollegeId());
        return "commissionManage/commissionAdd";
    }


    @RequestMapping("commissionManage/save")
    @ResponseBody
    public Boolean commissionManageSave(CommissionStudent commissionStudent, CommissionSchool commissionSchool){
        return commissionManageService.commissionManageSave(commissionStudent,commissionSchool);
    }

    @RequestMapping("commissionManage/editCommission")
    public String editCommission(Integer schoolId,Model model,Integer studentId){
        CommissionSchool commissionSchool = new CommissionSchool();
        commissionSchool.setId(schoolId);
        List<CommissionSchool> commissionSchoolList = commissionSchoolService.getCommissionSchoolList(commissionSchool);
        if(commissionSchoolList != null && commissionSchoolList.size() > 0 ){
            model.addAttribute("commissionSchool",commissionSchoolList.get(0));

            //获取合作机构
            List<SchoolData> cooperations = visaRecordService.getCooperations(Integer.valueOf(commissionSchoolList.get(0).getCollegeId()));
            model.addAttribute("cooperations",cooperations);

            //获取院校库
            Integer studentNationId = null;
            if(commissionSchoolList.get(0).getStudentId()!=null){
                CommissionStudent commissionStudent = new CommissionStudent();
                commissionStudent.setId(commissionSchoolList.get(0).getStudentId());
                commissionStudent.setDeleteStatus(false);
                List<CommissionStudent> commissionStudentList = commissionManageService.getCommissionStudentList(commissionStudent);
                if(commissionStudentList != null && commissionStudentList.size() > 0){
                    if(commissionStudentList.get(0).getNationId() != null){
                        studentNationId = commissionStudentList.get(0).getNationId();
                    }
                }
            }
            CountryInfo countryInfo = new CountryInfo();
            countryInfo.setId(studentNationId);
            countryInfo = countryService.get(countryInfo);
            List<SchoolData> listSchool = visaRecordService.getSchool1(countryInfo.getCountryXiaoxi());
            model.addAttribute("school",listSchool);
            for (CommissionSchool commissionSchool1:commissionSchoolList) {
                //此处判断参数是否为null，否则无法进行数据类型转换（NumberFormatException）
                if(org.springframework.util.StringUtils.hasText(commissionSchool1.getCollegeId())&&
                        org.springframework.util.StringUtils.hasText(commissionSchool1.getCollegeType())){
                    Integer collegeId = Integer.valueOf(commissionSchool1.getCollegeId());
                    Integer collegeType = Integer.valueOf(commissionSchool1.getCollegeType());
                    model.addAttribute("listMajor",visaRecordService.getMajors(collegeId,collegeType,commissionSchool1.getEducationSection() == null ? -1 :commissionSchool1.getEducationSection() ));

                }
                if(org.springframework.util.StringUtils.hasText(commissionSchool1.getMajorId())){
                    Integer collegeId = Integer.valueOf(commissionSchool1.getCollegeId());
                    model.addAttribute("listCourse",visaRecordService.getDegrees(collegeId,Integer.valueOf(commissionSchool1.getMajorId())));
                }
            }
        }
        model.addAttribute("studentId",studentId);
        model.addAttribute("schoolId",schoolId);
        //获取币种
        model.addAttribute("currencyInfos",  currencyInfoService.getList(new CurrencyInfo()));
        //获取顾问分支
        BranchInfo branchInfo = new BranchInfo();
        branchInfo.setIsdel(0);
        model.addAttribute("advisers",branchService.getList(branchInfo));
        //获取澳洲代理
        CommissionAusAgent commissionAusAgent = new CommissionAusAgent();
        model.addAttribute("commissionAusAgent",commissionAusAgentService.getCommissionAusAgentList(commissionAusAgent));
        model.addAttribute("newResDomain",newResDomain);
        model.addAttribute("commissionImageUrl",commissionImageUrl);
        return "commissionManage/editCommission";
    }

    //查询文签数据库，佣金编辑页，院校，专业，课程，等数据的回显（此方法用于区分editCommission）
    @RequestMapping(value = "commissionManage/editOtherCommission")
    public  String  editOtherCommission(Integer schoolId,Model model,Integer studentId){

        List<CommissionSchool> commissionSchoolList = commissionSchoolService.getCommissionSchoolList(new CommissionSchool(schoolId));

        if(commissionSchoolList!=null&&commissionSchoolList.size()>0){
            model.addAttribute("commissionSchool",commissionSchoolList.get(0));
        }
        model.addAttribute("studentId",studentId);
        model.addAttribute("schoolId",schoolId);
        //获取币种
        model.addAttribute("currencyInfos",  currencyInfoService.getList(new CurrencyInfo()));
        //获取顾问分支
        BranchInfo branchInfo = new BranchInfo();
        branchInfo.setIsdel(0);
        model.addAttribute("advisers",branchService.getList(branchInfo));
        //获取澳洲代理
        CommissionAusAgent commissionAusAgent = new CommissionAusAgent();
        model.addAttribute("commissionAusAgent",commissionAusAgentService.getCommissionAusAgentList(commissionAusAgent));
        model.addAttribute("newResDomain",newResDomain);
        model.addAttribute("commissionImageUrl",commissionImageUrl);
        return "commissionManage/editOtherCommission";

    }

    @RequestMapping("commissionManage/addCommission")
    public String addCommission(Integer studentId,Model model){
        model.addAttribute("studentId",studentId);
        model.addAttribute("commissionSchool",new CommissionSchool());
        commissionManageService.addCommissionManage(studentId,model);
        return "commissionManage/addCommission";
    }

    @RequestMapping("commissionManage/addSchool")
    @ResponseBody
    public Boolean addSchool(CommissionSchool commissionSchool,String studentNo){
        commissionSchool.setStudentNo(studentNo);
        return commissionSchoolService.addCommissionSchool(commissionSchool) ==1 ;
    }
    @RequestMapping("commissionManage/editSchool")
    @ResponseBody
    public Boolean editSchool(CommissionSchool commissionSchool,Integer schoolId){
        try {
            logger.info("当前院校Id:"+schoolId);
            commissionSchool.setId(schoolId);
            return commissionSchoolService.editCommissionSchool(commissionSchool) == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  false;
    }

    @RequestMapping("commissionManage/editStudent")
    @ResponseBody
    public Boolean editSchool(CommissionStudent commissionStudent){
        return commissionSchoolService.editCommissionStudent(commissionStudent) == 1;
    }



    @RequestMapping("commissionManage/remove")
    @ResponseBody
    public Boolean remove(String schoolId){
        return commissionSchoolService.removeCommissionSchool(schoolId);
    }


    /**
     * Excel 文件下载（下载全部信息，展示内容包含列表当中所有字段）
     *
     * @param response
     */
    @RequestMapping("/commissionManage/excel")
    public void excel(HttpServletRequest request, HttpServletResponse response) {

        try {
            commissionManageService.testExcel(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value="/commission/getSchool",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ResponseBody
    public  String getCourse(Integer nationId){
        CountryInfo countryInfo = new CountryInfo();
        countryInfo.setId(nationId);
        countryInfo = countryService.get(countryInfo);
        List<SchoolData> listSchool = visaRecordService.getSchool1(countryInfo.getCountryXiaoxi());
        return JSONArray.toJSONString(listSchool);
    }

    @RequestMapping("commissionManage/getOffer")
    public String getOffer(String studentNo,Model model){
        List<String> offerList = commissionManageService.getOffer(studentNo);
        List<String> offers = new ArrayList<>();
        for (String offer:offerList) {
            if(!offer.contains(resDomain)){
              offer = resDomain+offer;
            }
            offers.add(offer);
        }
        model.addAttribute("offerList",offers);
        return "commissionManage/offerList";
    }

    @RequestMapping("commissionManage/getCoe")
    public String getCoe(String studentNo,Model model){
        List<String> coeList = commissionManageService.getCoe(studentNo);
        List<String> coes = new ArrayList<>();
        for (String coe:coeList) {
            if(!coe.contains(resDomain)){
                coe = resDomain+coe;
            }
            coes.add(coe);
        }
        model.addAttribute("coeList",coes);
        return "commissionManage/coeList";
    }

    public String[] propList(){
        return new String[]{};
    }

    /**
     * 跳转到佣金记录列表
     *
     * @return
     */
    @RequestMapping("/commission/record/list")
    public String toFirstBonusList() {
        return "commissionRecord/list";
    }

    /**
     * 佣金记录列表
     */
    @RequestMapping(value = "commission/record/list/query", method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel getCommissionRecordList(String dateStart, String dateEnd, PageParam pageParam, BasePageModel basePageModel) {
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength());
        CommissionRecordInfo commissionRecordInfo = new CommissionRecordInfo();
        commissionRecordInfo.setDeleteStatus(false);
        List<CommissionRecordInfo> list = commissionRecordService.getList(commissionRecordInfo,dateStart,dateEnd);
        basePageModel.setAaData(page);
        basePageModel.setiTotalDisplayRecords((int) page.getTotal());
        basePageModel.setiTotalRecords((int) page.getTotal());
        return basePageModel;
    }

    @RequestMapping("/commission/record/add")
    public String add(CountryInfo countryInfo, Model model) {
        List<CountryInfo> countryInfoList = countryService.getList(countryInfo);
        List<CountryInfo> countryInfoNew = new ArrayList<>();
        for (CountryInfo item : countryInfoList) {
            if (item.getCountryBussid() != 100010 && item.getCountryBussid() != 100011) {
                countryInfoNew.add(item);
            }
        }
        model.addAttribute("countryInfoList", countryInfoNew);
        return "commissionRecord/add";
    }

    @RequestMapping(value = "/commission/add", method = RequestMethod.POST)
    @ResponseBody
    public Boolean Action_add(CommissionRecordInfo model) {
        SysUser sysUser = userService.getLoginUser();
        model.setCreateTime(new Date());
        model.setUpdateTime(null);
        model.setDeleteStatus(false);
        model.setOperatorNo(sysUser.getOaid());//当前登录工号
        model.setOperatorName(sysUser.getUsername());
        return commissionRecordService.add(model) > 0;
    }

    @RequestMapping("/commission/record/edit")
    public String edit(CountryInfo countryInfo,Integer id, Model model) {

        CommissionRecordInfo commissionRecordInfo = commissionRecordService.getById(id);

        model.addAttribute("commissionRecord", commissionRecordInfo);

        List<CountryInfo> countryInfoList = countryService.getList(countryInfo);
        List<CountryInfo> countryInfoNew = new ArrayList<>();
        for (CountryInfo item : countryInfoList) {
            if (item.getCountryBussid() != 100010 && item.getCountryBussid() != 100011) {
                countryInfoNew.add(item);
            }
        }
        model.addAttribute("countryInfoList", countryInfoNew);
        return "commissionRecord/edit";
    }

    @RequestMapping(value = "/commission/edit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> Action_edit(CommissionRecordInfo commissionRecordInfo) {

        Map<String, Object> map = new HashMap<>();
        SysUser sysUser = userService.getLoginUser();

        //只有操作人可以修改

        if (!commissionRecordInfo.getOperatorNo().equals(sysUser.getOaid())) {

            logger.info("当前登录人不是此条记录的操作人，无法修改!");

            map.put("code", false);
            map.put("msg", "当前登录人不是此条记录的操作人，无法修改!");

            return map;
        } else {

            commissionRecordInfo.setOperatorNo(sysUser.getOaid());//当前登录工号

            commissionRecordInfo.setOperatorName(sysUser.getUsername());

            map.put("code", commissionRecordService.update(commissionRecordInfo) > 0);

            return map;
        }


    }

    @RequestMapping(value = "/commission/delete", method = RequestMethod.POST)
    @ResponseBody
    public String Action_del(Integer id) {
        SysUser sysUser = userService.getLoginUser();
        CommissionRecordInfo commissionRecordInfo = commissionRecordService.getById(id);
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
        //只有操作人可以删除
        if (!commissionRecordInfo.getOperatorNo().equals(sysUser.getOaid())) {
            logger.info("当前登录人不是此条记录的操作人，无法删除");
            jsonObject.put("code", 2);
            return jsonObject.toString();
        }
        Integer result = commissionRecordService.delete(id);
        if(result > 0){//保存成功
            jsonObject.put("code", 0);
            return jsonObject.toString();
        }else{//保存失败
            jsonObject.put("code", 1);
            return jsonObject.toString();
        }
    }
}
