package com.aoji.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aoji.async.AsyncTask;
import com.aoji.contants.Contants;
import com.aoji.contants.CountryGroup;
import com.aoji.mapper.ChannelStudentInfoMapper;
import com.aoji.mapper.ChannelStudentNoMapper;
import com.aoji.model.*;
import com.aoji.model.res.FileRes;
import com.aoji.service.ChannelStudentService;
import com.aoji.service.CountryService;
import com.aoji.service.StudentService;
import com.aoji.service.UserService;
import com.aoji.utils.ConvertUtils;
import com.aoji.utils.HttpUtils;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class ChannelStudentController extends BaseController{
    @Autowired
    UserService userService;
    @Autowired
    ChannelStudentInfoMapper channelStudentInfoMapper;
    @Autowired
    ChannelStudentService channelStudentService;
    @Autowired
    private ChannelStudentNoMapper channelStudentNoMapper;
    @Autowired
    StudentService studentService;
    @Autowired
    CountryService countryService;
    @Autowired
    AsyncTask asyncTask;

    private static final Logger logger = LoggerFactory.getLogger(ChannelStudentController.class);

    @Value("${papersType.getFiles.url}")
    private String getFilesUrl;
    @Value("${papersType.addFile.url}")
    private String addFileUrl;
    @Value("${papersType.delFile.url}")
    private String delFileUrl;
    @Value("${upload.ks3.resDomain}")
    private String ks3Path;
    @Value("${getAgentByUser.url}")
    private String getAgentByUserUrl;

    @RequestMapping(value = "/channelStudent/addPage",method = RequestMethod.GET)
    public String add(Model model) {
        CountryInfo countryInfo = new CountryInfo();
        List<CountryInfo> list_country = countryService.getList(countryInfo);
        model.addAttribute("list_country", list_country);
        model.addAttribute("studentNo", Contants.tyPattern + channelStudentService.getStudentNo(1));

        SysUser sysUser = userService.getLoginUser();
        List<String> list_role = userService.getRolesByOaid(sysUser.getOaid());
        if(list_role.contains("同业经理") || list_role.contains("同业顾问")){
            model.addAttribute("list_agent",channelStudentService.queryAgentInfoList());
            return "channelStudent/addTY";
        }else if(list_role.contains("机构")){
            if(!StringUtils.isEmpty(sysUser.getAgentId())){
                String url = String.format(getAgentByUserUrl, sysUser.getAgentId());
                logger.info("调用获取代理信息接口url: " + url);
                String json = HttpUtils.doGet(url);
                logger.info("调用获取代理信息接口返回数据: " + json);
                if (!StringUtils.isEmpty(json)) {
                    JSONObject jsonObject = JSONObject.parseObject(json);
                    if (jsonObject.getInteger("code").equals(200)) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        if(jsonObject1 != null){
                            model.addAttribute("agentId", sysUser.getAgentId());
                            model.addAttribute("agentName", jsonObject1.getString("agentName"));
                        }
                    }
                }
            }
            return "channelStudent/add";
        }
        return null;
    }

    @RequestMapping(value = "/channelStudent/add",method = RequestMethod.POST)
    @ResponseBody
    public boolean actionAdd(ChannelStudentInfo channelStudentInfo){
        SysUser user = userService.getLoginUser();
        channelStudentInfo.setChannelStatus(1);
        channelStudentInfo.setOperatorNo(user.getOaid());
        channelStudentInfo.setBranchId(7);
        channelStudentInfo.setBranchName("澳际同业");
        channelStudentInfo.setCreateTime(new Date());
        channelStudentInfo.setDeleteStatus(false);
        boolean actionAdd = channelStudentService.add(channelStudentInfo);
        if(actionAdd==true){
            sendSubmissionMessage(channelStudentInfo);
        }
        return  actionAdd;
    }

    @RequestMapping(value = "/channelStudent/editPage",method = RequestMethod.GET)
    public String edit(Model model,Integer id){
        CountryInfo countryInfo = new CountryInfo();
        List<CountryInfo> list_country = countryService.getList(countryInfo);
        model.addAttribute("list_country",list_country);

        ChannelStudentInfo channelStudentInfo = channelStudentService.get(id);
        model.addAttribute("channelStudentInfo",channelStudentInfo);

        //
        Subject subject = SecurityUtils.getSubject();
        if(subject.hasRole("同业经理")){
            List<String> list_role = userService.getRolesByOaid(channelStudentInfo.getOperatorNo());
            if(list_role.contains("同业经理")){
                model.addAttribute("editAgent",true);
                model.addAttribute("list_agent",channelStudentService.queryAgentInfoList());
            }
        }

        return "channelStudent/edit";
    }

    @RequestMapping(value = "/channelStudent/edit",method = RequestMethod.POST)
    @ResponseBody
    public boolean actionEdit(ChannelStudentInfo channelStudentInfo){
        SysUser user = userService.getLoginUser();
        channelStudentInfo.setOperatorNo(user.getOaid());
        channelStudentInfo.setUpdateTime(new Date());

        boolean actionEdit = channelStudentService.update(channelStudentInfo);
        if(actionEdit){
            sendSubmissionMessage(channelStudentInfo);
            if(channelStudentInfo.getAuditStatus().equals(3)){
                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setStudentNo(channelStudentInfo.getStudentNo());
                studentInfo.setDeleteStatus(false);
                studentInfo = studentService.get(studentInfo);
                if(studentInfo != null){
                    studentInfo.setStudentName(channelStudentInfo.getStudentName());
                    studentInfo.setPinyin(channelStudentInfo.getPinyin());
                    studentInfo.setBirthday(channelStudentInfo.getBirthday());
                    studentInfo.setNationId(channelStudentInfo.getNationId());
                    studentInfo.setNationName(channelStudentInfo.getNationName());
                    studentInfo.setContractType(channelStudentInfo.getContractType());
                    studentInfo.setEmailAccount(channelStudentInfo.getEmail());
                    studentService.update(studentInfo);
                }
            }
        }
        return  actionEdit;
    }

    @RequestMapping(value = "/channelStudent/delPaper",method = RequestMethod.POST)
    @ResponseBody
    public Integer delPaper(String studentNo,Integer fileId){
        String message = String.format("studentNo=%s&fileId=%s", studentNo, fileId);
        String result = HttpUtils.sendPost2(delFileUrl, message);
        JSONObject jsonObject = JSON.parseObject(result);
        return jsonObject.getInteger("code");
    }

    /**
     * 机构提交材料
     * @param studentNo
     * @return
     */
    @RequestMapping(value = "/channelStudent/confirmFile",method = RequestMethod.POST)
    @ResponseBody
    public String confirmFile(String studentNo){
        SysUser sysUser = userService.getLoginUser();
        ChannelStudentInfo channelStudentInfo = channelStudentService.selectByStudentNo(studentNo);
        if(channelStudentInfo != null){
            channelStudentInfo.setAgentConfirm(true);
            channelStudentInfo.setOperatorNo(sysUser.getOaid());
            channelStudentInfo.setUpdateTime(new Date());
            boolean result =  channelStudentService.update(channelStudentInfo);
            if(result){
                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setStudentNo(studentNo);
                studentInfo.setDeleteStatus(false);
                studentInfo = studentService.get(studentInfo);

                MailEntity mailEntity = new MailEntity();
                String content = String.format("学号%s学生%s的签证材料已提交，请接收！", studentNo, studentInfo.getStudentName());
                mailEntity.setSubject(content);
                mailEntity.setContent(content);
                List<String> receives = new ArrayList<>(5);
                receives.add(ConvertUtils.ToPinyin(studentInfo.getSalesConsultant()) + "@aoji.cn");
                mailEntity.setReceiveAccounts(receives);
                mailEntity.setSendName("澳际文签系统");
                asyncTask.sendEmail(mailEntity);

                return "1";
            }
        }
        return "0";
    }

    /**
     * 跳转到同业待审批列表
     *
     * @return
     */
    @RequestMapping("/toAuditListTY")
    public String toAuditListTY() {
        return "channelStudent/toAuditListTY";
    }

    /**
     * 跳转到代理待审批列表
     *
     * @return
     */
    @RequestMapping("/toAuditListDL")
    public String toAuditListDL(CountryInfo countryInfo,Model model) {
        List<CountryInfo> countryInfoList = countryService.getList(countryInfo);
        List<CountryInfo> countryInfoNew = new ArrayList<>();
        for (CountryInfo item : countryInfoList) {
            if (item.getCountryBussid() != Contants.AMERICA_GENERAL_BUSS_ID && item.getCountryBussid() != Contants.AMERICA_TOP_BUSS_ID) {
                countryInfoNew.add(item);
            }
        }
        model.addAttribute("countryInfoList", countryInfoNew);
        return "channelStudent/toAuditListDL";
    }

    @RequestMapping(value = "toAuditListTY_query", method = RequestMethod.POST)
    @ResponseBody
    public BasePageModel getToAuditListTY(ChannelStudentInfo channelStudentInfo, String type, PageParam pageParam, BasePageModel basePageModel) {
        SysUser sysUser = userService.getLoginUser();
        if(type.equals(Contants.dlPattern)){
            channelStudentInfo.setRemark(Contants.dlPattern);
            channelStudentInfo.setOperatorNo(sysUser.getOaid());
        }
        if(type.equals(Contants.tyPattern)){
            channelStudentInfo.setRemark(Contants.tyPattern);
        }
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength());
        List<ChannelStudentInfo> list = channelStudentInfoMapper.getList(channelStudentInfo);
        basePageModel.setAaData(page);
        basePageModel.setiTotalDisplayRecords((int) page.getTotal());
        basePageModel.setiTotalRecords((int) page.getTotal());
        return basePageModel;
    }

    /**
     * 同业审批接口
     * @param channelStudentId
     * @return
     */
    @RequestMapping(value = "/channelStudent/toAudit")
    @ResponseBody
    public Boolean toAudit(String channelStudentId,String consulterNo){
        Boolean audidStatus = false;
        try{
            audidStatus = channelStudentService.auditChannelStudentById(channelStudentId, consulterNo);
            if(audidStatus==true){
                sendExamineMassage(channelStudentId,consulterNo);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return audidStatus;
    }

    /**
     * 触发节点：同业负责人“审核”并分配咨询顾问后，提醒同业文签负责人（工号在配置文件中）
     * @param channelStudentId
     */
    public  void  sendExamineMassage(String channelStudentId,String consulterNo){
        try {
            channelStudentService.sendExamineMassage(channelStudentId,consulterNo);
        } catch (Exception e) {
            logger.error("触发节点：同业负责人“审核”并分配咨询顾问后，提醒同业文签负责人失败 channelStudentId："+channelStudentId + ";异常信息:" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 触发节点：机构“提交”后，提醒同业负责人(同业经理)
     * @param channelStudentInfo
     */
    public  void sendSubmissionMessage(ChannelStudentInfo channelStudentInfo){
        try {
            channelStudentService.sendSubmissionMessage(channelStudentInfo);
        } catch (Exception e) {
            logger.info("触发节点：机构“提交”后，提醒同业负责人(同业经理)失败 channelStudentInfo"+JSONObject.toJSON(channelStudentInfo).toString());
            e.printStackTrace();
        }
    }

    @RequestMapping("/channelStudent/toAuditPage")
    public String toAuditPage(Model model,Integer channelStudentId){
        model.addAttribute("channelStudentId",channelStudentId);
        model.addAttribute("ChannelStudentConsulterList",channelStudentService.getChannelStudentConsulterList());
        return "/channelStudent/toAuditPage";
    }

    /**
     * 同业已审核列表，重新分配咨询顾问
     * @param model
     * @param studentNo
     * @return
     */
    @RequestMapping("/channelStudent/selectSalesConsultant")
    public String selectCopyOperator(Model model, String studentNo, ChannelAssignSaleconsultant channelAssignSaleconsultant, HttpServletRequest request){
        request.getSession().removeAttribute(Contants.CHANNEL_ASSIGN_SALECONSULTANT_LIST);
        model.addAttribute("studentNo",studentNo);
        request.getSession().setAttribute(Contants.CHANNEL_ASSIGN_SALECONSULTANT_LIST, channelAssignSaleconsultant);
        model.addAttribute("ChannelStudentConsulterList",channelStudentService.getChannelStudentConsulterList());
        return "/channelStudent/toAuditPage";
    }

    /**
     * 根据学号修改学生的咨询顾问
     * @param studentNo
     * @return
     */
    @RequestMapping(value = "/channelStudent/changeSalesConsultantByStudentNo")
    @ResponseBody
    public Boolean changeCopyOperatorByStudentNo(String studentNo,String consulterNo,String consulter,HttpServletRequest request){
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(studentNo);
        studentInfo.setSalesConsultantNo(consulterNo);
        studentInfo.setSalesConsultant(consulter);
        Boolean audidStatus = false;
        try{
            audidStatus = studentService.updateSalesConsultantByStudentNo(studentInfo,request);
        }catch (Exception e){
            e.printStackTrace();
        }
        return audidStatus;
    }

    /**
     * 同业已审批学生列表
     * @param model
     * @return
     */
    @RequestMapping("/channelStudent/studentList")
    public String studentList(Model model){
        List<CountryInfo> countryInfoNew = new ArrayList<>();
        countryInfoNew = this.getCountryList();
        model.addAttribute("countryInfoList", countryInfoNew);
        return "/channelStudent/studentList";
    }

    /**
     * 同业已审批学生列表
     * @param model
     * @return
     */
    @RequestMapping("/channelStudent/dLstudentList")
    public String dLStudentList(Model model){
        List<CountryInfo> countryInfoNew = new ArrayList<>();
        countryInfoNew = this.getCountryList();
        model.addAttribute("countryInfoList", countryInfoNew);
        return "/channelStudent/dLstudentList";
    }
    /**
     * 同业已审核学生列表查询（此处只查询student_info表里的有代理Id的学生）
     * @param request
     * @param studentInfo
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping(value="/channelStudent/query",method = RequestMethod.POST)
    @ResponseBody
    public BasePageModel get(HttpServletRequest request, StudentInfo studentInfo, PageParam pageParam, BasePageModel basePageModel){
        //查询当前用户角色是否为（机构）
        Boolean channelStatus = channelStudentService.roleName();
        String agentId = userService.getLoginUser().getAgentId();
        Page<?> page = pageWapper(pageParam,propList());
        List<StudentInfo> auditStudentByAgentId = channelStudentService.getAuditStudentByAgentId(studentInfo, channelStatus, agentId);
        return dataTableWapper(page,basePageModel);
    }



    public String[] propList(){
        return new String[]{};
    }

    @RequestMapping(value = "/channelStudent/editAuditPage",method = RequestMethod.GET)
    public String edit(Model model,String studentNo){
        CountryInfo countryInfo = new CountryInfo();
        List<CountryInfo> list_country = countryService.getList(countryInfo);
        model.addAttribute("list_country",list_country);

        ChannelStudentInfo channelStudentInfo = channelStudentService.selectByStudentNo(studentNo);
        model.addAttribute("channelStudentInfo",channelStudentInfo);
        return "channelStudent/edit";
    }

    /**
     * 查看学生详情
     * @param model
     * @param studentNo
     * @return
     */
    @RequestMapping(value = "/channelStudent/detail",method = RequestMethod.GET)
    public String edit(String studentNo, Model model){
        CountryInfo countryInfo = new CountryInfo();
        List<CountryInfo> list_country = countryService.getList(countryInfo);
        model.addAttribute("list_country",list_country);

        //获取数据
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(studentNo);
        studentInfo.setDeleteStatus(false);
        studentInfo = studentService.get(studentInfo);
        if (studentInfo != null) {
            model.addAttribute("studentInfo", studentInfo);
        }

        ChannelStudentInfo channelStudentInfo = channelStudentService.selectByStudentNo(studentNo);

        model.addAttribute("channelStudentInfo",channelStudentInfo);
        return "channelStudent/detail";
    }

    @RequestMapping(value = "/channelStudent/getPapersType",method = RequestMethod.POST)
    @ResponseBody
    public Object getPapersType(String studentNo ,Integer nationId, String papersType){
        Integer countryId = -1;
        CountryInfo countryInfo = new CountryInfo();
        countryInfo.setId(nationId);
        countryInfo = countryService.get(countryInfo);
        if (countryInfo != null) {
            Integer countryGroup = countryInfo.getCountryGroup();
            if (countryGroup.equals(CountryGroup.GROUP_ASIA.getCode()) || countryGroup.equals(CountryGroup.GROUP_EUROPE.getCode())) {
                countryId = countryInfo.getCountryGroup();
            }
            else{
                countryId = countryInfo.getCountryBussid();
            }
        }

        String message = String.format("studentNo=%s&paperType=%s&countryId=%s&contractNo=%s", studentNo,papersType, countryId, "0");
        Map<String,Object> map = new HashMap<>();
        logger.info("调用获取需要上传文件及类型接口url:" + getFilesUrl + ";数据为: " + message);
        String result = HttpUtils.sendPost2(getFilesUrl, message);
        logger.info("调用获取需要上传文件及类型接口结果:" + result);
        if (!StringUtils.isEmpty(result)) {
            FileRes fileRes = JSONObject.parseObject(result, FileRes.class);
            map.put("code",1);
            map.put("data",fileRes.getData());
        }
        else{
            map.put("code",0);
            map.put("data","");
        }
        return map;
    }

    @RequestMapping(value = "/channelStudent/addPaper",method = RequestMethod.POST)
    @ResponseBody
    public Integer addPaper(String studentNo,Integer countryId, String contractNo,Integer paperType,Integer paperId,String fileName ,String fileUrl){
        CountryInfo countryInfo = new CountryInfo();
        countryInfo.setId(countryId);
        countryInfo = countryService.get(countryInfo);
        if (countryInfo != null) {
            Integer countryGroup = countryInfo.getCountryGroup();
            if (countryGroup.equals(CountryGroup.GROUP_ASIA.getCode()) || countryGroup.equals(CountryGroup.GROUP_EUROPE.getCode())) {
                countryId = countryInfo.getCountryGroup();
            }
            else{
                countryId = countryInfo.getCountryBussid();
            }
        }

        String message = String.format("studentNo=%s&countryId=%s&contractNo=%s&paperType=%s&paperId=%s&fileName=%s&fileUrl=%s", studentNo, countryId, "0",paperType,paperId,fileName,ks3Path + fileUrl);
        logger.info("调用上传文件接口url:" + addFileUrl + ";数据为: " + message);
        String result = HttpUtils.sendPost2(addFileUrl, message);
        logger.info("调用上传文件接口结果:" + result);
        JSONObject jsonObject = JSON.parseObject(result);
        if(jsonObject.getInteger("code") == 0){
            JSONObject jsonObject1 = JSON.parseObject(jsonObject.get("data").toString());
            Integer fileId = jsonObject1.getInteger("fileId");
            return fileId;
        }
        return -1;
    }

    public List<CountryInfo> getCountryList(){
        List<CountryInfo> countryInfoList = countryService.getList(new CountryInfo());
        List<CountryInfo> countryInfoNew = new ArrayList<>();
        for (CountryInfo item : countryInfoList) {
            //如果当前国家名称不是美普，则将国家信息返回
            if (item.getCountryBussid() != 100010 && item.getCountryBussid() != 100011) {
                countryInfoNew.add(item);
            }
        }
        return countryInfoNew;
    }
}
