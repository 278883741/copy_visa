package com.aoji.mapper;

import com.aoji.model.SysRole;
import com.aoji.model.SysUserRole;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysRoleMapper extends Mapper<SysRole> {

    List<Integer> getRoleIdByUserId(Integer userId);

    /**
     * 根据员工工号查询角色信息
     * @param oaId 员工工号
     * @return
     */
    List<String> getRoleByOaId(String oaId);



    /**
     * 根据角色信息查询员工工号
     * @return
     */
    List<SysUserRole> getOaidByRoleName(String channelmanager);

}