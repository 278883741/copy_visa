package com.aoji.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.Contants;
import com.aoji.model.*;
import com.aoji.service.PermissionService;
import com.aoji.service.RoleService;
import com.github.pagehelper.Page;
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
 * @date Created in 下午6:23 2017/11/29
 */
@Controller
public class RoleController extends BaseController{

    @Autowired
    RoleService roleService;

    @Autowired
    PermissionService permissionService;


    @RequestMapping("/role")
    public String list(){
        return "role/list";
    }

    @RequestMapping(value="roles",method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel get(SysRole sysRole, PageParam pageParam, BasePageModel basePageModel){
        Page<?> page = pageWapper(pageParam,propList());
        if(!StringUtils.hasText(sysRole.getRoleName())){
            sysRole.setRoleName(null);
        }
        sysRole.setDeleteStatus(false);
        List<SysRole> roles=roleService.queryRolesByRole(sysRole);
        for(SysRole role:roles){
            if(!StringUtils.isEmpty(role.getUpdateTime())) {
                role.setUpdateTimeString(new SimpleDateFormat(Contants.datePattern).format(role.getUpdateTime()));
            }
        }
        return dataTableWapper(page,basePageModel);
    }
    public String[] propList(){
        return new String[]{"id","roleName","operatorNo","updateTimeString"};
    }

    /**
     * 跳转编辑或添加页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/role/savePage")
    public String editPage(Integer id,Model model){
        SysRole sysRole=roleService.getById(id);
        if(sysRole!=null && !StringUtils.isEmpty(sysRole.getCreatedTime())) {
            sysRole.setCreatedTime(new SimpleDateFormat(Contants.datePattern).format(sysRole.getCreatedTime()));
            sysRole.setContent("ysx");
        }
        model.addAttribute("role",sysRole);
        return "role/edit";
    }

    @RequestMapping("/role/detailPage")
    public String detailPage(Integer id,Model model){
        SysRole sysRole=roleService.getById(id);
        if(sysRole!=null && !StringUtils.isEmpty(sysRole.getCreatedTime())) {
            sysRole.setCreatedTime(new SimpleDateFormat(Contants.datePattern).format(sysRole.getCreatedTime()));
            sysRole.setContent("ysx");
        }
        model.addAttribute("role",sysRole);
        return "role/detail";
    }

    /**
     * 添加或修改角色信息
     * @param sysRole
     * @return
     */
    @RequestMapping(value="/role/save",method = RequestMethod.POST)
    @ResponseBody
    public Boolean save(SysRole sysRole){
        sysRole.setUpdateTime(new Date());
        if(StringUtils.isEmpty(sysRole.getId())){
            return roleService.add(sysRole)>0;
        }else{
            return roleService.update(sysRole)>0;
        }
    }


    /**
     * 删除角色信息
     * @param id 角色id
     * @return
     */
    @RequestMapping(value="/role/delete",method = RequestMethod.POST)
    @ResponseBody
    public Boolean delete(Integer id){
        SysRole sysRole=new SysRole(id,true,new Date());
        if(!StringUtils.isEmpty(sysRole.getId())){
            return roleService.update(sysRole)>0;
        }else{
            return  false;
        }
    }


    /**
     * 查询某个角色拥有的权限信息
     * @param parentId 父级id
     * @param roleId 角色id
     * @return
     */
    @RequestMapping(value="role/functions",method = RequestMethod.POST)
    @ResponseBody
    public TreeItem getFunctionsByTree(Integer parentId, Integer level, Integer roleId){
        TreeItem treeItem=new TreeItem();

        List<JSONObject> dataList=new ArrayList<>();

        SysPermission permission = new SysPermission();
        permission.setParentId(parentId);
        permission.setLevel(level);
        if (parentId == null){
            permission.setLevel(Integer.valueOf(0));
        }
        //根据parentId查询权限列表
        List<SysPermission> sysPermissions = permissionService.get(permission);
        //查询该角色下拥有的权限列表
        List<Integer> sysPermissionIds =  permissionService.getPermissionIdByRoleId(roleId);

        for (SysPermission sysPermission : sysPermissions) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", sysPermission.getId());
            jsonObject.put("name", sysPermission.getPermissionName());
            jsonObject.put("level",sysPermission.getLevel());
            jsonObject.put("pId", sysPermission.getParentId());
            if (sysPermissionIds.contains(sysPermission.getId())){
                jsonObject.put("checked", true);
            }
            jsonObject.put("isParent", true);
            dataList.add(jsonObject);
        }

        treeItem.setMsg(dataList.toString());
        return treeItem;
    }

    /**
     * 跳转树形权限展示页
     * @return
     */
    @RequestMapping("/role/permissionTree")
    public String permissionTreePage(){
        return "/role/permissionTree";
    }

    /**
     * 跳转树形权限展示页
     * @return
     */
    @RequestMapping("/role/permissionTreeDetail")
    public String permissionTreeDetail(){
        return "/role/permissionTreeDetail";
    }

    /**
     * 保存修改的权限
     * @param roleId 角色ID
     * @param permissionIdStr 权限ID-JSON格式
     * @param operator 操作者
     * @return
     */
    @RequestMapping(value = "/role/savePermission" ,method = {RequestMethod.POST})
    @ResponseBody
    public String savePermission(Integer roleId, String permissionIdStr, String operator){

        if(StringUtils.hasText(permissionIdStr)){
            List<Integer> permissionIds = JSON.parseArray(permissionIdStr, Integer.class);
            System.out.println(permissionIds);
            // 保存修改的权限
            Boolean result = permissionService.updateRolePermission(roleId, permissionIds, operator);
            if (result) {
                return Contants.SUCCESS_FLAG;
            }
        }
        return Contants.FAIL_FLAG;
    }

    /**
     * 跳转到添加页
     * @return
     */
    @RequestMapping(value = "/role/add")
    public String toAddPage(){
        return "/role/add";
    }


    /**
     * 添加操作
     * @param model
     * @return
     */
    @RequestMapping(value="/role/Action_add",method = RequestMethod.POST)
    @ResponseBody
    public Boolean Action_add(SysRole model){
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        model.setDeleteStatus(false);
        model.setOperatorNo("admin");//当前登录工号
        return roleService.add(model) > 0;
    }
}
