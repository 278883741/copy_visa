package com.aoji.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.Contants;
import com.aoji.model.*;
import com.aoji.service.RoleService;
import com.aoji.service.UserGroupRelationService;
import com.aoji.service.UserGroupService;
import com.aoji.service.UserService;
import com.github.pagehelper.Page;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yangsaixing
 * @description
 * @date Created in 下午5:07 2017/11/21
 */
@Controller
public class UserController extends BaseController {
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserGroupService userGroupService;

    @Autowired
    UserGroupRelationService userGroupRelationService;

    private Logger logger= LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/user/editPage")
    public String editPage(Long id,Model model,String oaid){
        SysUser sysUser=userService.getById(id);
        model.addAttribute("user",sysUser);
        SysUser currentUser = (SysUser)SecurityUtils.getSubject().getPrincipal();
        UserGroupRelation userGroupRelation = userGroupRelationService.getUserGroupRelationByOaid(oaid);
        model.addAttribute("userGroupRelation",userGroupRelation);
        model.addAttribute("oaid",oaid);
        model.addAttribute("current", currentUser);
        return "user/edit";
    }

    @RequestMapping("/user/detail")
    public String detail(Long id,Model model){
        SysUser sysUser= userService.getById(id);
        if(sysUser != null && sysUser.getOaid() != null){
            UserGroupRelation userGroupRelation = userGroupRelationService.getUserGroupRelationByOaid(sysUser.getOaid());
            model.addAttribute("userGroupRelation",userGroupRelation);
        }
        model.addAttribute("user",sysUser);
        return "user/detail";
    }

    @RequestMapping("/user")
    public String list(){
        return "user/list";
    }

    @RequestMapping(value="/user/edit",method = RequestMethod.POST)
    @ResponseBody
    public Boolean edit(SysUser sysUser,Integer isLeader,String jobNumber ){
        if(StringUtils.isEmpty(sysUser.getId())){
            logger.error("更新用户信息-->用户id不存在");
            return false;
        }
        if(isLeader == 1){
          userGroupRelationService.update(jobNumber,1);
        }else if(isLeader == 2){
            userGroupRelationService.update(jobNumber,0);
        }
        return userService.update(sysUser)>0;
    }
    @RequestMapping(value="queryStus",method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel get(SysUser sysUser, PageParam pageParam, BasePageModel basePageModel){
        Page<?> page = pageWapper(pageParam,propList());
        if(!StringUtils.hasText(sysUser.getOaid())){
            sysUser.setOaid(null);
        }
        if(!StringUtils.hasText(sysUser.getUsername())){
            sysUser.setUsername(null);
        }
        //page.setOrderBy(str[pageParam.getiSortCol_0()]+" "+pageParam.getsSortDir_0());
        List<SysUser> userList=userService.getList(sysUser);
        for(SysUser user:userList){
            if(!StringUtils.isEmpty(user.getUpdateTime())) {
                user.setUpdateTimeString(new SimpleDateFormat(Contants.datePattern).format(user.getUpdateTime()));
            }
        }
        return dataTableWapper(page,basePageModel);
    }

    public String[] propList(){
        return new String[]{"id","oaid","username","enableStatus","operatorNo","updateTimeString"};
    }

    /**
     * 跳转角色选择页
     * @return
     */
    @RequestMapping("/user/roleTreeDetail")
    public String roleTreeDetail(){
        return "/user/roleTreeDetail";
    }

    @RequestMapping("/user/groupTreeDetail")
    public String groupTreeDetail(){
        return "/user/groupTreeDetail";
    }

    /**
     * 跳转角色选择页
     * @return
     */
    @RequestMapping("/user/roleTree")
    public String roleTree(){
        return "/user/roleTree";
    }

    @RequestMapping("/user/groupTree")
    public String groupTree(){
        return "/user/groupTree";
    }

    @RequestMapping(value = "/user/group",method = {RequestMethod.POST})
    @ResponseBody
    public TreeItem getGroupById(Integer userId){
        TreeItem treeItem=new TreeItem();
        List<JSONObject> dataList=new ArrayList<>();
        UserGroup userGroup =  new UserGroup();
        userGroup.setDeleteStatus(false);
        List<UserGroup> userGroupList = userGroupService.getUserGroup(userGroup);
        List<Integer> groupIds = userGroupService.getGroupIds(userId);
        for (UserGroup group:userGroupList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", group.getId());
            jsonObject.put("name", group.getGroupName());
            if(groupIds.contains(group.getId())){
                jsonObject.put("checked",true);
            }
            jsonObject.put("isParent", false);
            dataList.add(jsonObject);
        }
        treeItem.setMsg(dataList.toString());
        return treeItem;
    }

    /**
     * 角色树数据
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/roles", method = {RequestMethod.POST})
    @ResponseBody
    public TreeItem getRolesByUser(Integer userId){
        TreeItem treeItem=new TreeItem();

        List<JSONObject> dataList=new ArrayList<>();

        SysRole sysRole = new SysRole();
        sysRole.setDeleteStatus(false);
        List<SysRole> sysRoles = roleService.queryRolesByRole(sysRole);

        List<Integer> roleIds = roleService.getRoleIdByUserId(userId);

        for (SysRole role : sysRoles) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", role.getId());
            jsonObject.put("name", role.getRoleName());
            //jsonObject.put("icon","/ztree/css/img/diy/1_close.png");
            if (roleIds.contains(role.getId())){
                jsonObject.put("checked", true);
            }
            jsonObject.put("isParent", false);
            dataList.add(jsonObject);
        }

        treeItem.setMsg(dataList.toString());
        return treeItem;
    }

    /**
     * 保存用户角色关系
     * @param userId
     * @param roleIdStr
     * @param operator
     * @return
     */
    @RequestMapping("/user/saveRole")
    @ResponseBody
    public String saveRole(Integer userId, String roleIdStr, String oaid){
        if(StringUtils.hasText(roleIdStr)){
            List<Integer> roleIds = JSON.parseArray(roleIdStr, Integer.class);
            boolean saveResult = roleService.saveUserRole(userId, roleIds, oaid);
            if(saveResult){
                return Contants.SUCCESS_FLAG;
            }
        }
        return Contants.FAIL_FLAG;
    }
    @RequestMapping("/user/saveGroup")
    @ResponseBody
    public String saveGroup(Integer userId,String roleIdStr, String operator){
        if(StringUtils.hasText(roleIdStr) && StringUtils.hasText(userId+"")) {
            List<Integer> roleIds = JSON.parseArray(roleIdStr, Integer.class);
            boolean saveResult = userGroupService.saveUserGroup(userId, roleIds,operator);
            if(saveResult){
                return Contants.SUCCESS_FLAG;
            }
        }
        return Contants.FAIL_FLAG;
    }


    /**
     * 跳转到添加页
     * @return
     */
    @RequestMapping(value = "/user/add")
    public String toAddPage(){
        return "/user/add";
    }

    /**
     * 添加操作
     * @param model
     * @return
     */
    @RequestMapping(value="/user/add",method = RequestMethod.POST)
    @ResponseBody
    public Boolean Action_add(SysUser model){
        SysUser sysUser = userService.getLoginUser();
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        model.setDeleteStatus(false);
        model.setEnableStatus(1);
        model.setOperatorNo(sysUser.getOaid());
        Boolean result1 = userService.add(model) > 0;
        Boolean result2 = userService.grantAppLogin(model.getOaid());
//        return result1 && result2;

        return result1;
    }
    @RequestMapping(value="/user/editEnableStatus",method = RequestMethod.POST)
    @ResponseBody
    public Integer editEnableStatus(Integer id,Integer enableStatus){
        SysUser sysUser = new SysUser();
        sysUser.setId(Long.valueOf(id));
        sysUser.setEnableStatus(enableStatus);
        return userService.update(sysUser);
    }
}
