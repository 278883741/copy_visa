package com.aoji.controller;

import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.Contants;
import com.aoji.model.BasePageModel;
import com.aoji.model.CountryInfo;
import com.aoji.model.PageParam;
import com.aoji.model.SysUser;
import com.aoji.service.*;
import com.aoji.vo.CalibrationSchemeVo;
import com.aoji.vo.PlanCollegeInfoVO;
import com.github.pagehelper.Page;
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

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author huyanlong
 * @description 运营管理-定校方案页面
 * @date Created in 下午6:33 2018/8/2
 */
@Controller
public class CalibrationSchemeController extends BaseController {

    public static final Logger logger = LoggerFactory.getLogger(CalibrationSchemeController.class);

    @Value("${plan.college.info.url}")
    private String planCollegeInfoUrl;

    @Value("${plan.college.audit.url}")
    private String planCollegeAuditUrl;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    PlanCollegeInfoService planCollegeInfoService;

    @Autowired
    private CalibrationSchemeService calibrationSchemeService;

    @Autowired
    CountryService countryService;

    @RequestMapping("/calibrationScheme")
    public String list(Model model) {
        logger.info("进入calibrationScheme，跳转至calibrationScheme/list，本方法用于根据国家线，区分权限，查询国家列表");
        model.addAttribute("planCollegeInfoUrl", planCollegeInfoUrl);
        model.addAttribute("planCollegeAuditUrl", planCollegeAuditUrl);
        SysUser sysUser = userService.getLoginUser();
        List<String> roles = roleService.getRolesByOaId(sysUser.getOaid());
        logger.info("获取当前用户的所有角色：roles"+roles);
        if (roles.contains(Contants.ROLE_VISA_MANAGER) || roles.contains(Contants.ROLE_VISA_DIRECTOR)) {
            logger.info("按国家线区分权限，只查询文签总监/文签经理所对应国家列表，参数为当前用户的Oaid:"+sysUser.getOaid());
            List<CountryInfo>  countryInfoList = countryService.selectCountryNameByVisaManagerOrDirector(sysUser.getOaid());
            logger.info("查询出来的国家名称:"+countryInfoList);
            if(countryInfoList!=null&&countryInfoList.size()>0){
                model.addAttribute("listCountryName",countryInfoList);
            }
        }else{
            logger.info("此处不区分国家线，所以入参Oaid传null");
            List<CountryInfo>  countryInfoList = countryService.selectCountryNameByVisaManagerOrDirector(null);
            logger.info("数据为全部的国家名称："+countryInfoList);
            model.addAttribute("listCountryName",countryInfoList);
        }
        return "calibrationScheme/list" ;
    }


    /**
     * 分页查询所有学生的定校方案
     *
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping(value = "/calibrationScheme/list", method = RequestMethod.POST)
    @ResponseBody
    public BasePageModel calibrationSchemeList(CalibrationSchemeVo calibrationSchemeVo, PageParam pageParam, BasePageModel basePageModel) {
        logger.info("进入/calibrationScheme/list，本方法用于分页查询所有定校方案列表，入参：calibrationSchemeVo"+calibrationSchemeVo);
        SysUser sysUser = userService.getLoginUser();
        List<String> roles = roleService.getRolesByOaId(sysUser.getOaid());
        //按国家线区分权限，文签总监/文签经理只许查看本国家线用户信息
        Page<?> page = null;
        logger.info("获取当前用户的所有角色：roles"+roles);
        if (roles.contains(Contants.ROLE_VISA_MANAGER) || roles.contains(Contants.ROLE_VISA_DIRECTOR)) {
            logger.info("按国家线区分权限，只查询文签总监/文签经理所对应国家列表，参数为当前用户的Oaid:"+sysUser.getOaid());
            List<CountryInfo>  countryInfoList = countryService.selectCountryNameByVisaManagerOrDirector(sysUser.getOaid());
            logger.info("查询出来的国家名称:"+countryInfoList);
            Integer [] arrayId = null;
            if(countryInfoList!=null&&countryInfoList.size()>0){
                logger.info("将国家名称Id遍历，放入数组");
                Integer countryId =null;
                String ids = "";
                for (CountryInfo countryNameList:countryInfoList) {
                    countryId = countryNameList.getId();
                    ids += countryId+",";
                }
                String[] split = ids.split(",");
                arrayId = new   Integer[split.length];
                for(int i=0;i<split.length;i++){
                    arrayId[i] = Integer.parseInt(split[i]);
                }
            }
            page = pageWapper(pageParam, propList());
            logger.info("查询定校方案列表，入参：查询条件，权限，国家Id：calibrationSchemeVo"+calibrationSchemeVo+"arrayId:"+arrayId);
            List<Map<String, Object>> list = calibrationSchemeService.calibrationSchemeList(calibrationSchemeVo,arrayId);
            logger.info("定校方案列表返回数据：list"+list);
        } else{
            page = pageWapper(pageParam, propList());
            logger.info("查询定校方案列表，入参：查询条件，calibrationSchemeVo"+calibrationSchemeVo);
            List<Map<String, Object>> list = calibrationSchemeService.calibrationSchemeList(calibrationSchemeVo,null);
            logger.info("定校方案列表返回数据：list"+list);
        }
        return dataTableWapper(page, basePageModel);
    }
    @RequestMapping("/calibrationScheme/toVoidPlanCollage")
    @ResponseBody
    public JSONObject toVoidPlanCollage(Integer id,Integer auditStatus){
        JSONObject jsonObject = new JSONObject();

        if(id==null || "".equals(id)){
            jsonObject.put("result",false);
            logger.info("参数id错误 id :"+id);
            return jsonObject;
        }
        if(auditStatus==null || "".equals(auditStatus)){
            jsonObject.put("result",false);
            logger.info("参数auditStatus错误 auditStatus :"+auditStatus);
            return jsonObject;
        }
        JSONObject result = calibrationSchemeService.toVoidPlanCollage(id,auditStatus);
        jsonObject.put("data",result);
        return  jsonObject;
    }


    /**
     * 排序列表
     *
     * @return
     */
    public String[] propList() {
        return new String[]{""};
    }

}
