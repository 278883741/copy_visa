package com.aoji.controller;
import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.CaseIdEnum;
import com.aoji.contants.Contants;
import com.aoji.contants.SearchOption;
import com.aoji.contants.StudentStatus;
import com.aoji.model.*;
import com.aoji.service.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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

import java.util.*;
import java.text.SimpleDateFormat;

@Controller
public class VisaResultController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    VisaResultService visaResultInfoService;
    @Autowired
    AuditApplyService auditApplyService;
    @Autowired
    UserService userService;
    @Autowired
    StudentService studentService;
    @Autowired
    VisaRecordService visaRecordService;
    @Autowired
    ChannelStudentService channelStudentService;
    @Autowired
    CountryService countryService;
    @Autowired
    VisaResultService visaResultService;

    @Value("${upload.ks3.resDomain}")
    private String resDomain;

    @RequestMapping("/visaResult/manageList")
    public String manageList(Model model){
        CountryInfo countryInfo = new CountryInfo();
        model.addAttribute("countryList",countryService.getList(countryInfo));
        return "visaResult/manageList";
    }

    /**
     * 分页查询管理层签证结果列表
     *
     * @param searchOption
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping(value = "visaResult/manageList/query", method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel manageGet(SearchOption searchOption, PageParam pageParam, BasePageModel basePageModel) {
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength());
        String[] str = new String[]{"id", "student_no","student_name", "visa_result", "create_time", "operator_no", "apply_audit_status","operator_name","attachment","visaApplyId"};
        visaResultService.getManegeList(searchOption);
        pageParam.setSortPro(str[pageParam.getiSortCol_0()]);
        return dataTableWapper(page, basePageModel);
    }

    @RequestMapping(value="/visaResult/add",method = RequestMethod.POST)
    @ResponseBody
    public Object add(VisaResultInfo visaResultInfo){
        Map<String, String> map = new HashMap<>(5);
        try{
            SysUser sysUser = userService.getLoginUser();
            visaResultInfo.setCreateTime(new Date());
            visaResultInfo.setDeleteStatus(false);
            visaResultInfo.setOperatorNo(sysUser.getOaid());
            visaResultInfo.setOperatorName(sysUser.getUsername());
            visaResultInfo.setUpdateTime(new Date());
            visaResultInfo.setVisaStatus(Contants.APPLYSTATUS_SUBMIT);
            Integer i = visaResultInfoService.add(visaResultInfo);
            Integer businessId = visaResultInfo.getId();
            if (i > 0) {
                auditApplyService.add(businessId, CaseIdEnum.VisaResult.getCode(), 1, visaResultInfo.getStudentNo(), "", "");
                map.put("code", "0");
                map.put("message", "操作成功");
            }
        }catch (Exception e){
            map.put("code", "1");
            map.put("message", e.getMessage());
        }
        return map;
    }

    @RequestMapping(value="/visaResult/delete",method = RequestMethod.POST)
    @ResponseBody
    public boolean actionDelete(Integer id){
        return visaResultInfoService.delete(id) > 0;
    }

    @RequestMapping(value="/visaResult/save",method = RequestMethod.POST)
    @ResponseBody
    public String actionEdit(VisaResultInfo visaResultInfo){
        VisaResultInfo visaResultInfo1 = new VisaResultInfo();
        visaResultInfo1.setId(visaResultInfo.getId());
        visaResultInfo1.setDeleteStatus(false);
        visaResultInfo1 = visaResultInfoService.get(visaResultInfo1);
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
        if(visaResultInfo1.getVisaStatus().equals(Contants.APPLYSTATUS_AUDITING) || visaResultInfo1.getVisaStatus().equals(Contants.APPLYSTATUS_ACCEPT)){
            jsonObject.put("code", 0);
        }
        else {
            Integer auditStatus = visaResultInfo.getVisaStatus();
            boolean needSendMessage = true;
            if (auditStatus.equals(Contants.APPLYSTATUS_SUBMIT)) {
                if(auditApplyService.get(visaResultInfo.getId(),CaseIdEnum.VisaResult.getCode()) == null){
                    auditApplyService.add(visaResultInfo.getId(), CaseIdEnum.VisaResult.getCode(), 1, visaResultInfo.getStudentNo(),"","");
                }
            } else if (auditStatus.equals(Contants.APPLYSTATUS_REJECT)) {
                visaResultInfo.setVisaStatus(Contants.APPLYSTATUS_SUBMIT);
                auditApplyService.add(visaResultInfo.getId(), CaseIdEnum.VisaResult.getCode(), 1, visaResultInfo.getStudentNo(), "","");
            }
            visaResultInfo.setUpdateTime(new Date());

            String evusAttachment = visaResultInfo.getAttachment();
            if(!com.mysql.jdbc.StringUtils.isNullOrEmpty(evusAttachment)){
                evusAttachment = evusAttachment.replaceAll(resDomain,"");
                visaResultInfo.setAttachment(evusAttachment);
            }

            if( visaResultInfoService.update(visaResultInfo) > 0){
                jsonObject.put("code", 1);
            }
            else{
                jsonObject.put("code", 2);
            }
        }
        return  jsonObject.toString();
    }

    /**
     * 分页查询获签结果列表
     * @param visaResultInfo
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping(value="visaResult/list/query",method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel get(VisaResultInfo visaResultInfo, PageParam pageParam, BasePageModel basePageModel){
        String[] str = new String[]{"id","student_no","operator_name","create_time"};
        Page<?> page =pageWapper(pageParam,str);
        visaResultInfoService.getToAuditList(visaResultInfo);
        return dataTableWapper(page,basePageModel);
    }
}
