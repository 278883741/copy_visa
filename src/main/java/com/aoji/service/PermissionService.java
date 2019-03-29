package com.aoji.service;

import com.aoji.model.SysPermission;

import java.util.List;

/**
 * author: chenhaibo
 * description: 用户权限管理
 * date: 2017/12/28
 */
public interface PermissionService {

    /**
     * 获取权限列表
     * @param sysPermission
     * @return
     */
    List<SysPermission> getList(SysPermission sysPermission);

    /**
     * 获取权限列表 （select）
     * @param sysPermission
     * @return
     */
    List<SysPermission> get(SysPermission sysPermission);

    /**
     * 根据角色ID获取权限ID
     * @param roleId
     * @return
     */
    List<Integer> getPermissionIdByRoleId(Integer roleId);

    /**
     * 修改角色权限
     * @param roleId
     * @param permissionIds
     * @return
     */
    Boolean updateRolePermission(Integer roleId, List<Integer> permissionIds, String operator);

    Integer update( SysPermission sysPermission);
    Integer deleteOne(Integer id);
    Integer addOne( SysPermission sysPermission);

    /**
     * 根据ID查询权限
     * @param id
     * @return
     */
    SysPermission getPermissionById(Integer id);

    /**
     * 根据用户的oaid或许用户的权限
     * @param oaid
     * @return
     */
    List<SysPermission> getPermissionByOaid(String oaid);


}
