package com.aoji.controller;

import com.aoji.model.BasePageModel;
import com.aoji.model.PageParam;
import com.aoji.service.SystemManagerService;
import com.aoji.vo.SysRolePermissionVO;
import com.aoji.vo.SysUserRoleVO;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * author: chenhaibo
 * description: 系统管理控制类
 * date: 2018/11/19
 */
@Controller
@RequestMapping("system")
public class SystemManagerController extends BaseController{

    @Autowired
    SystemManagerService systemManagerService;

    /**
     * 用户 - 角色 列表
     * @return
     */
    @RequestMapping("user/role/list")
    public String userRoleList(){
        return "/system/user_role";
    }

    /**
     * 用户 - 角色 列表数据
     * @param sysUserRoleVO
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @PostMapping("user/role/list/data")
    @ResponseBody
    public BasePageModel userRoleListData(SysUserRoleVO sysUserRoleVO, PageParam pageParam, BasePageModel basePageModel){
        Page<?> page = pageWapper(pageParam, null);
        systemManagerService.selectSysUserRole(sysUserRoleVO);
        return dataTableWapper(page, basePageModel);
    }

    /**
     * 移除用户角色
     * @param userRoleId
     * @return
     */
    @PostMapping("user/role/remove")
    @ResponseBody
    public boolean removeUserRole(Integer userRoleId){
        if(userRoleId == null){ return false; }
        return systemManagerService.removeSysUserRole(userRoleId);
    }

    /**
     * 角色 - 权限 列表
     * @return
     */
    @RequestMapping("/role/permission/list")
    public String rolePermissiom(){
        return "/system/role_permission";
    }

    /**
     * 角色 - 权限 列表数据
     * @param sysRolePermissionVO
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @PostMapping("role/permission/list/data")
    @ResponseBody
    public BasePageModel rolePermissionListData(SysRolePermissionVO sysRolePermissionVO, PageParam pageParam, BasePageModel basePageModel){
        Page<?> page = pageWapper(pageParam, null);
        systemManagerService.selectSysRolePermission(sysRolePermissionVO);
        return dataTableWapper(page, basePageModel);
    }

    /**
     * 移除角色权限
     * @param rolePermissionId
     * @return
     */
    @PostMapping("role/permission/remove")
    @ResponseBody
    public boolean removeRolePermission(Integer rolePermissionId){
        if(rolePermissionId == null) { return false; }
        return systemManagerService.removeSysRolePermission(rolePermissionId);
    }
}
