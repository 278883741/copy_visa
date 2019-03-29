package com.aoji.controller;

import com.alibaba.fastjson.JSONArray;
import com.aoji.contants.Contants;
import com.aoji.model.*;
import com.aoji.service.SupplementInfoService;
import com.aoji.service.ExpressCompanyService;
import com.aoji.service.UserService;
import com.aoji.service.VisitInfoService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class SupplementInfoController {
    @Autowired
    SupplementInfoService supplementInfoService;

    @Autowired
    ExpressCompanyService expressCompanyService;

    @Autowired
    UserService userService;

    @Autowired
    VisitInfoService visitInfoService;

    @Value("${upload.ks3.resDomain}")
    private String resDomain;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    //    @RequestMapping("/supplementInfo")
//    public String list(Model model){
//        model.addAttribute("resDomain",resDomain);
//        return "supplementInfo/newList";
//    }
    @RequestMapping("/supplementInfo")
    public String list(Model model) {
        model.addAttribute("resDomain",resDomain);
        return "supplementInfo/list";
    }

    @RequestMapping(value = "supplementInfo_query", method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel get(SupplementInfo supplementInfo, PageParam pageParam, BasePageModel basePageModel) {
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength());
        String[] str = new String[]{"id", "supplement_type", "send_date", "express_company_name", "express_no", "create_time", "operator_no"};
        supplementInfoService.getListNPA(supplementInfo);
        pageParam.setSortPro(str[pageParam.getiSortCol_0()]);
        basePageModel.setAaData(page);
        basePageModel.setiTotalDisplayRecords((int) page.getTotal());
        basePageModel.setiTotalRecords((int) page.getTotal());
        return basePageModel;
    }

    @RequestMapping(value = "supplementInfoNew_query", method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel get(Integer applyId, PageParam pageParam, BasePageModel basePageModel) {
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength());
        String[] str = new String[]{"id", "supplement_type", "send_date", "express_company_name", "express_no", "create_time", "operator_no"};
        visitInfoService.getListForSupplement(applyId);
        pageParam.setSortPro(str[pageParam.getiSortCol_0()]);
        basePageModel.setAaData(page);
        basePageModel.setiTotalDisplayRecords((int) page.getTotal());
        basePageModel.setiTotalRecords((int) page.getTotal());
        return basePageModel;
    }

    public String[] propList() {
        return new String[]{"id", "supplement_type", "send_date", "express_company_name", "express_no", "create_time", "operator_no"};
    }


    @RequestMapping("/supplementInfo/detail")
    public String detail(Integer id, ExpressCompany expressCompany, Model model) {
        SupplementInfo supplementInfo = supplementInfoService.getById(id);
        model.addAttribute("supplementInfo", supplementInfo);
        List<ExpressCompany> expressCompanyList = expressCompanyService.getList(new ExpressCompany());
        model.addAttribute("expressCompanyList", expressCompanyList);
        model.addAttribute("resDomain",resDomain);
        return "supplementInfo/detail";
    }

    @RequestMapping("/supplementInfo/add")
    public String add(ExpressCompany expressCompany, Model model) {
        List<ExpressCompany> expressCompanyList = expressCompanyService.getList(expressCompany);
        model.addAttribute("expressCompanyList", expressCompanyList);
        return "supplementInfo/add";
    }

    @RequestMapping("/supplementInfo/edit")
    public String edit(Integer id, Model model) {

        SupplementInfo supplementInfo = supplementInfoService.getById(id);

        model.addAttribute("supplementInfo", supplementInfo);

        List<ExpressCompany> expressCompanyList = expressCompanyService.getList(new ExpressCompany());

        model.addAttribute("expressCompanyList", expressCompanyList);

        model.addAttribute("resDomain", resDomain);

        return "supplementInfo/edit";
    }

    @RequestMapping(value = "/supplementInfo/Action_add", method = RequestMethod.POST)
    @ResponseBody
    public Boolean Action_add(SupplementInfo model) {
        SysUser sysUser = userService.getLoginUser();
        String time = model.getSendDate_string();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            model.setSendDate(sdf.parse(time));
        } catch (Exception ex) {

        }
        model.setCreateTime(new Date());
        model.setDeleteTime(null);
        model.setDeleteStatus(false);
        model.setOperatorNo(sysUser.getOaid());//当前登录工号
        model.setOperatorName(sysUser.getUsername());
        return supplementInfoService.addOne(model) > 0;
    }

    @RequestMapping(value = "/supplementInfo/Action_edit", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> Action_edit(SupplementInfo model) {

        Map<String, Object> map = new HashMap<>();
        SysUser sysUser = userService.getLoginUser();

        String time = model.getSendDate_string();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            model.setSendDate(sdf.parse(time));
        } catch (Exception ex) {

        }

        //只有操作人可以修改
        System.out.println(model.getOperatorNo());

        System.out.println(sysUser.getOaid());

        if (!model.getOperatorNo().equals(sysUser.getOaid())) {

            logger.info("当前登录人不是此条记录的操作人，无法修改!");

            map.put("code", false);
            map.put("msg", "当前登录人不是此条记录的操作人，无法修改!");

            return map;
        } else {

            model.setOperatorNo(sysUser.getOaid());//当前登录工号

            model.setOperatorName(sysUser.getUsername());

            map.put("code", supplementInfoService.update(model) > 0);

            return map;
        }


    }

    @RequestMapping(value = "/supplementInfo/Action_del", method = RequestMethod.POST)
    @ResponseBody
    public String Action_del(Integer id) {
        SysUser sysUser = userService.getLoginUser();
        SupplementInfo supplementInfo = supplementInfoService.getById(id);
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
        //只有操作人可以删除
        if (!supplementInfo.getOperatorNo().equals(sysUser.getOaid())) {
            logger.info("当前登录人不是此条记录的操作人，无法删除");
            jsonObject.put("code", 2);
            return jsonObject.toString();
        }
        Integer result = supplementInfoService.deleteOne(id);
        if(result > 0){//保存成功
            jsonObject.put("code", 0);
            return jsonObject.toString();
        }else{//保存失败
            jsonObject.put("code", 1);
            return jsonObject.toString();
        }
    }

    @RequestMapping(value = "/supplementInfo/Action_getOneById", method = RequestMethod.POST)
    @ResponseBody
    public String Action_getOneById(Integer id) {
        SupplementInfo supplementInfo = supplementInfoService.getById(id);
        supplementInfo.setSendDate_string(new SimpleDateFormat(Contants.datePattern).format(supplementInfo.getSendDate()));
        supplementInfo.setCreateTime_string(new SimpleDateFormat(Contants.datePattern).format(supplementInfo.getCreateTime()));
        String object = JSONArray.toJSONString(supplementInfo);
        return object;
    }


}
