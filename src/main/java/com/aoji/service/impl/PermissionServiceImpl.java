package com.aoji.service.impl;

import com.aoji.contants.Contants;
import com.aoji.mapper.SysPermissionMapper;
import com.aoji.mapper.SysRolePermissionMapper;
import com.aoji.model.SysPermission;
import com.aoji.model.SysRolePermission;
import com.aoji.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;
//import tk.mybatis.mapper.entity.Example;
//
//import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    SysPermissionMapper sysPermissionMapper;

    @Autowired
    SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public List<SysPermission> getList(SysPermission sysPermission){
        Example example = new Example(SysPermission.class);
        if("".equals(sysPermission.getPermissionName()) || sysPermission.getPermissionName() == null){
            example.createCriteria().andEqualTo("deleteStatus",false);
        }
        else {
            example.createCriteria().andEqualTo("deleteStatus", false).andLike("permissionName", "%"+sysPermission.getPermissionName()+"%");
        }
        List<SysPermission> list= sysPermissionMapper.selectByExample(example);
        for(SysPermission item:list) {
            if (!StringUtils.isEmpty(item.getCreated())) {
                item.setCreatedTime(new SimpleDateFormat(Contants.datePattern).format(item.getCreated()));
            }
        }
        return list;
    }

    @Override
    public List<SysPermission> get(SysPermission sysPermission) {
        sysPermission.setDeleteStatus(false);
        return sysPermissionMapper.select(sysPermission);
    }

    @Override
    public List<Integer> getPermissionIdByRoleId(Integer roleId) {
        return sysPermissionMapper.selectPermissionIdsByRoleId(roleId);
    }


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Boolean updateRolePermission(Integer roleId, List<Integer> permissionIds, String operator) {
        // 删除该角色的所有权限
        sysRolePermissionMapper.removeByRoleIdAndPermissionId(roleId);
        // 添加新的权限
        Date now = new Date();
        for (Integer permissionId : permissionIds){
            SysRolePermission sysRolePermission = new SysRolePermission();
            sysRolePermission.setDeleteStatus(false);
            sysRolePermission.setRoleId(roleId);
            sysRolePermission.setPermissionId(permissionId);
            sysRolePermission.setCreated(now);
            sysRolePermission.setOperator(operator);
            sysRolePermissionMapper.insert(sysRolePermission);
        }
        return true;
    }

    @Override
    public Integer update(SysPermission sysPermission) {
        return sysPermissionMapper.updateByPrimaryKeySelective(sysPermission);
    }

    @Override
    public Integer deleteOne(Integer id){
        return sysPermissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer addOne(SysPermission sysPermission){
        return sysPermissionMapper.insert(sysPermission);
    }

    @Override
    public SysPermission getPermissionById(Integer id) {
        SysPermission sysPermission = new SysPermission();
        sysPermission.setDeleteStatus(false);
        sysPermission.setId(id);
        List<SysPermission> sysPermissions = sysPermissionMapper.select(sysPermission);
        if(sysPermissions != null && sysPermissions.size() > 0){
            return sysPermissions.get(0);
        }
        return null;
    }

    @Override
    public List<SysPermission> getPermissionByOaid(String oaid) {
        return sysPermissionMapper.getPermissionByOaid(oaid);
    }

}
