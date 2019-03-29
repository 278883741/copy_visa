package com.aoji.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.Contants;
import com.aoji.contants.ContentException;
import com.aoji.contants.CountryGroup;
import com.aoji.model.*;
import com.aoji.service.CountryService;
import com.aoji.service.UserGroupService;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wangjunqiang
 * @description 国家组控制层
 * @date Created in 下午5:07 2017/11/21
 */
@Controller
public class UserGroupController extends BaseController {

    private Logger logger= LoggerFactory.getLogger(UserGroupController.class);

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private CountryService countryService;

    /**
     * 用户组列表页
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("userGroup")
    public String userGroup(HttpServletRequest request,Model model){
        return "userGroup/userGroupList";
    }

    /**
     * 用户组列表数据
     * @param userGroup
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping("userGroup/Data")
    @ResponseBody
    public BasePageModel resultListData(UserGroup userGroup, PageParam pageParam, BasePageModel basePageModel){
        Page<?> page = pageWapper(pageParam,propList());
        userGroup.setDeleteStatus(false);
        List<UserGroup> followServiceInfos = userGroupService.getUserGroupByGroupName(userGroup);
        return dataTableWapper(page,basePageModel);
    }

    /**
     * 用户组删除
     * @param request
     * @return
     */
    @RequestMapping("userGroup/toUpdate")
    public String toUpdate(HttpServletRequest request){
        String id = request.getParameter("id")== null ? "" : request.getParameter("id");
        if(StringUtils.hasText(id)){
            UserGroup userGroup = new UserGroup();
            userGroup.setId(Integer.valueOf(id));
            userGroup.setDeleteStatus(true);
            userGroupService.toUpdateUserGroup(userGroup);
        }
        return  "redirect:/userGroup";
    }

    /**
     * 用户组修改页
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("userGroup/updateGroup")
    public String updateGroup(HttpServletRequest request,Model model){
        String id = request.getParameter("id");
        UserGroup userGroup = new UserGroup();
        if(StringUtils.hasText(id)){
             userGroup = userGroupService.getUserGroupById(id);
        }
        model.addAttribute("id",id);
        model.addAttribute("userGroup",userGroup);
        return "/userGroup/updatePage";
    }

    /**
     * 用户组修改数据
     * @param data
     * @return
     */
    @RequestMapping("userGroup/save")
    @ResponseBody
    public boolean save(@RequestParam("data")String data){
        Object obj = JSONArray.parse(data);
        try{
            //id
            String id = ((JSONObject)obj).getString("id");
            //小组名称
            String groupName = ((JSONObject)obj).getString("groupName");
            //所属国家
            String nation = ((JSONObject)obj).getString("nation");
            //操作人
            String operatorNo = ((JSONObject)obj).getString("operatorNo");
            UserGroup userGroup = new UserGroup();
            userGroup.setGroupName(groupName);
            userGroup.setNation(Integer.valueOf(nation));
            if(StringUtils.hasText(id)){
                userGroup.setId(Integer.valueOf(id));
                Integer updateResult = userGroupService.toUpdateUserGroup(userGroup);
                if(updateResult > 0){
                    return true;
                }
            }else{
                userGroup.setDeleteStatus(false);
                userGroup.setOperatorNo(operatorNo);
                userGroup.setCreateTime(new Date());
                Integer saveResult = userGroupService.saveUserGroup(userGroup);
                if(saveResult > 0){
                    return true;
                }
            }
        }catch(Exception e){
          throw new ContentException(1,"国家组修改/新增失败");
        }
        return false;
    }

    /**
     * 添加用户组页
     * @return
     */
    @RequestMapping("userGroup/addPage")
    public String addPage(){
        return "userGroup/addPage";
    }

    /**
     * 添加用户组页
     * @return
     */
    @RequestMapping("userGroup/add")
    public String add(HttpServletRequest request,Model model){
        CountryInfo countryInfo = new CountryInfo();
        String id = request.getParameter("id");
        String type = request.getParameter("type");
        UserGroup userGroup = new UserGroup();
        userGroup = userGroupService.getUserGroupById(id);
        model.addAttribute("userGroup",userGroup);
        model.addAttribute("type",type);
        return "userGroup/add_page";
    }

    @RequestMapping("userGroup/nationTree")
    public String nationTree(){
        return "userGroup/nationTree";
    }

    /**
     * 获取国家树
     * @param groupName
     * @return
     */
    @RequestMapping(value="userGroup/getNationTree",method = {RequestMethod.POST})
    @ResponseBody
    public TreeItem getNationTree(String groupName){
        TreeItem treeItem=new TreeItem();
        List<JSONObject> dataList=new ArrayList<>();
        CountryInfo countryInfo = new CountryInfo();
        List<CountryInfo> countryInfoList = countryService.getGroupList(countryInfo);
        List<String> nationList = userGroupService.getNationList(groupName);
        for (CountryInfo countryInfo1:countryInfoList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", countryInfo1.getCountryGroup());
            jsonObject.put("name", CountryGroup.getNameByCode(countryInfo1.getCountryGroup()));
            if(nationList.contains(countryInfo1.getCountryGroup()+"")){
                    jsonObject.put("checked",true);
            }
            jsonObject.put("isParent", false);
            dataList.add(jsonObject);
        }
        treeItem.setMsg(dataList.toString());
        return treeItem;
    }

    @RequestMapping("userGroup/saveGroup")
    @ResponseBody
    public String saveNationGroup(@RequestParam("data")String data){
        Object obj = JSONArray.parse(data);
        //id
        String id = ((JSONObject)obj).getString("id");
        Integer nationId = ((JSONObject)obj).getInteger("nation");
        String groupName = ((JSONObject)obj).getString("groupName");
        String operator = ((JSONObject)obj).getString("operator");
        if(StringUtils.hasText(nationId+"") && StringUtils.hasText(groupName)) {
            boolean saveResult = userGroupService.saveNationGroup(nationId,operator,groupName,id);
            if(saveResult){
                return Contants.SUCCESS_FLAG;
            }
        }
        return Contants.FAIL_FLAG;

    }

    /**
     * 启用/禁用
     * @param id
     * @param enableStatus
     * @return
     */
    @RequestMapping(value="/userGroup/editEnableStatus",method = RequestMethod.POST)
    @ResponseBody
    public Integer editEnableStatus(Integer id,Integer enableStatus) {
        UserGroup userGroup = new UserGroup();
        userGroup.setId(id);
        userGroup.setEnableStatus(enableStatus);
        return userGroupService.toUpdateUserGroup(userGroup);
    }

    public String[] propList(){
        return new String[]{};
    }
}
