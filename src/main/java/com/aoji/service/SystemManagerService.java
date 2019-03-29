package com.aoji.service;

import com.aoji.vo.SysRolePermissionVO;
import com.aoji.vo.SysUserRoleVO;

import java.util.List;

/**
 * author: chenhaibo
 * description: 系统管理相关接口
 * date: 2018/11/19
 */
public interface SystemManagerService {

    /**
     * 用户-角色列表 查询
     * @param sysUserRoleVO
     * @return
     */
    List<SysUserRoleVO> selectSysUserRole(SysUserRoleVO sysUserRoleVO);

    /**
     * 移除用户角色
     * @param sysUserRoleId
     * @return
     */
    boolean removeSysUserRole(Integer sysUserRoleId);

    /**
     * 角色-权限列表 查询
     * @param sysRolePermissionVO
     * @return
     */
    List<SysRolePermissionVO> selectSysRolePermission(SysRolePermissionVO sysRolePermissionVO);

    /**
     * 移除角色权限
     * @param sysRolePermissionId
     * @return
     */
    boolean removeSysRolePermission(Integer sysRolePermissionId);
}
