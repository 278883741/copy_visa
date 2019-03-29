package com.aoji.mapper;

import com.aoji.model.SysRolePermission;
import com.aoji.vo.SysRolePermissionVO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysRolePermissionMapper extends Mapper<SysRolePermission> {
    /**
     * 根据角色ids查询权限id集合
     * @param roleIds 角色ids
     * @return
     */
    List<Integer> getPermissionIds(List<Integer> roleIds);

    /**
     * 根据角色ID和权限ID逻辑删除
     * @param roleId
     * @return
     */
    int removeByRoleIdAndPermissionId(Integer roleId);

    /**
     * 角色-权限关联查询
     * @param sysRolePermissionVO
     * @return
     */
    List<SysRolePermissionVO> selectSysRolePermission(SysRolePermissionVO sysRolePermissionVO);
}