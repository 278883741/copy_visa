package com.aoji.service;

import com.aoji.model.SysRole;
import com.aoji.model.SysUserRole;

import java.util.List;

/**
 * @author yangsaixing
 * @description
 * @date Created in 下午6:42 2017/11/29
 */
public interface RoleService {

    /**
     * 根据角色实体查询角色列表
     * @param sysRole 角色实体
     * @return
     */
    List<SysRole> queryRolesByRole(SysRole sysRole);

    /**
     * 根据角色id查询角色信息
     * @param id
     * @return
     */
    SysRole getById(Integer id);

    /**
     * 更新角色信息
     * @param sysRole
     * @return
     */
    Integer update(SysRole sysRole);
    /**
     * 保存角色信息
     * @param sysRole
     * @return
     */
    Integer add(SysRole sysRole);

    //List<JSONObject> queryTrees(Integer parentId);

    /**
     * 查询用户拥有的角色ID
     * @param userId
     * @return
     */
    List<Integer> getRoleIdByUserId(Integer userId);

    /**
     * 保存用户角色关系
     * @param userId 用户ID
     * @param roleIds 角色ID集合
     * @param oaid 用户工号
     * @return
     */
    Boolean saveUserRole(Integer userId, List<Integer> roleIds, String oaid);

    /**
     * 根据角色id查询用户信息
     * @param roleId 角色id
     * @return
     */
    List<SysUserRole> getUserByRoleId(Integer roleId,String studentNo);

    SysUserRole getSysUserRole(SysUserRole sysUserRole);
    /**
     * 根据员工oa查询角色信息
     * @param oaId oaId
     * @return
     */
    List<String> getRolesByOaId(String oaId);

    SysRole get(SysRole sysRole);

    List<SysUserRole> getOaidByRoleName(String channelmanager);


}
