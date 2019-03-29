package com.aoji.service.impl;

import com.aoji.mapper.SysRolePermissionMapper;
import com.aoji.mapper.SysUserRoleMapper;
import com.aoji.model.SysRolePermission;
import com.aoji.model.SysUserRole;
import com.aoji.service.SystemManagerService;
import com.aoji.vo.SysRolePermissionVO;
import com.aoji.vo.SysUserRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SystemManagerServiceImpl implements SystemManagerService{

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public List<SysUserRoleVO> selectSysUserRole(SysUserRoleVO sysUserRoleVO) {
        return sysUserRoleMapper.selectSysUserRole(sysUserRoleVO);
    }

    @Override
    public List<SysRolePermissionVO> selectSysRolePermission(SysRolePermissionVO sysRolePermissionVO) {
        return sysRolePermissionMapper.selectSysRolePermission(sysRolePermissionVO);
    }

    @Override
    public boolean removeSysUserRole(Integer sysUserRoleId) {
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setId(sysUserRoleId);
        sysUserRole.setDeleted(new Date());
        sysUserRole.setDeleteStatus(true);
        return sysUserRoleMapper.updateByPrimaryKeySelective(sysUserRole) > 0;
    }

    @Override
    public boolean removeSysRolePermission(Integer sysRolePermissionId) {
        SysRolePermission sysRolePermission = new SysRolePermission();
        sysRolePermission.setId(sysRolePermissionId);
        sysRolePermission.setDeleted(new Date());
        sysRolePermission.setDeleteStatus(true);
        return sysRolePermissionMapper.updateByPrimaryKeySelective(sysRolePermission) > 0;
    }
}
