package com.aoji.controller;
import com.aoji.model.*;
import com.aoji.service.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author 赵剑飞
 * @description 合作结构控制器
 * @date Created in 下午2:25 2017/12/7
 */
@Controller
public class AgencyController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    AgencyService agencyService;
    @Autowired
    CountryService countryService;
    @Autowired
    UserService userService;

    /**
     * 跳转到列表页
     * @return
     */
    @RequestMapping("/agency")
    public String list(){
        return "agency/list";
    }

    /**
     * 分页查询合作机构列表
     * @param agencyInfo
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping(value="/agency/list/query",method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel get(AgencyInfo agencyInfo, PageParam pageParam, BasePageModel basePageModel){
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart()/pageParam.getiDisplayLength()+1, pageParam.getiDisplayLength());
        String[] str = new String[]{"id","agency_name","content","create_time","delete_time","delete_status","operator_no","agency_type","nation_id"};
        agencyService.get(agencyInfo);
        pageParam.setSortPro(str[pageParam.getiSortCol_0()]);
        return dataTableWapper(page,basePageModel);
    }

    /**
     * 跳转到添加页
     * @return
     */
    @RequestMapping("/agency/addPage")
    public String add(){
        return "agency/add";
    }

    /**
     * 保存添加的信息
     * @return
     */
    @RequestMapping(value="/agency/add",method = RequestMethod.POST)
    @ResponseBody
    public Boolean actionAdd(AgencyInfo agencyInfo){
        SysUser sysUser = userService.getLoginUser();
        agencyInfo.setCreateTime(new Date());
        agencyInfo.setDeleteStatus(false);
        agencyInfo.setOperatorNo(sysUser.getOaid());
        return agencyService.add(agencyInfo) > 0;
    }

    /**
     * 跳转到编辑页
     * @return
     */
    @RequestMapping("/agency/editPage")
    public String edit(Integer id,Model model){
        AgencyInfo agencyInfo= agencyService.getById(id);
        model.addAttribute("agencyInfo",agencyInfo);
        return "agency/edit";
    }

    /**
     * 保存编辑的信息
     * @param agencyInfo
     * @return
     */
    @RequestMapping(value="/agency/edit",method = RequestMethod.POST)
    @ResponseBody
    public Boolean actionEdit(AgencyInfo agencyInfo){
        SysUser sysUser = userService.getLoginUser();
        agencyInfo.setOperatorNo(sysUser.getOaid());
        return agencyService.update(agencyInfo) > 0;
    }

    /**
     * 跳转到详情页
     * @return
     */
    @RequestMapping("/agency/detailPage")
    public String detail(Integer id,Model model){
        AgencyInfo agencyInfo= agencyService.getById(id);
        model.addAttribute("agencyInfo",agencyInfo);
        return "agency/detail";
    }

    /**
     * 获取添加页中的国家信息
     * @param parentId
     * @return
     */
    @RequestMapping(value="/agency/getCountry",method = RequestMethod.POST)
    @ResponseBody
    public TreeItem getCountry(Integer parentId){
        return agencyService.getCountry(parentId);
    }

    /**
     * 获取编辑，详情页中的国家信息
     * @param checkdCountry
     * @param action
     * @return
     */
    @RequestMapping(value="/agency/getCountry_edit",method = RequestMethod.POST)
    @ResponseBody
    public TreeItem getCountryEdit(String checkdCountry,String action){
        return agencyService.getCountryEdit(checkdCountry,action);
    }

    /**
     * 启用/禁用合作结构
     * @param id
     * @param enableStatus
     * @return
     */
    @RequestMapping(value="/agency/editEnableStatus",method = RequestMethod.POST)
    @ResponseBody
    public Integer editEnableStatus(Integer id,Integer enableStatus) {
        AgencyInfo agencyInfo = new AgencyInfo();
        agencyInfo.setId(id);
        agencyInfo.setEnableStatus(enableStatus);
        return agencyService.update(agencyInfo);
    }
}
