package com.aoji.mapper;

import com.aoji.model.SysUserRole;
import com.aoji.vo.SysUserRoleVO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysUserRoleMapper extends Mapper<SysUserRole> {
    /**
     * 根据用户id查询用户角色ids
     * @param userId 用户id
     * @return
     */
    List<Integer> getRoleIdsByUserId(Long userId);

    /**
     * 根据用户ID逻辑删除用户角色
     * @param userId
     * @return
     */
    Integer removeRoleByUserId(Integer userId);

    /**
     * 用户-角色关联查询
     * @param sysUserRoleVO
     * @return
     */
    List<SysUserRoleVO> selectSysUserRole(SysUserRoleVO sysUserRoleVO);

    List<SysUserRole> getSysUsertest();

    int insertList(Integer id);
}