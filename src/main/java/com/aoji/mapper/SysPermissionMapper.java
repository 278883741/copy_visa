package com.aoji.mapper;

import com.aoji.model.SysPermission;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysPermissionMapper extends Mapper<SysPermission> {
    /**
     * 根据角色查询权限ID
     * @param roleId
     * @return
     */
    List<Integer> selectPermissionIdsByRoleId(Integer roleId);

    /**
     * 根据用户的oaid获取用户对应的权限
     * @param oaid
     * @return
     */
    List<SysPermission> getPermissionByOaid(String oaid);
}