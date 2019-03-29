package com.aoji.service.impl;

import com.aoji.contants.Contants;
import com.aoji.contants.ErrorMessage;
import com.aoji.mapper.SysRoleMapper;
import com.aoji.mapper.SysUserRoleMapper;
import com.aoji.model.StudentInfo;
import com.aoji.model.SysRole;
import com.aoji.model.SysUser;
import com.aoji.model.SysUserRole;
import com.aoji.service.RoleService;
import com.aoji.service.StudentService;
import com.aoji.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author yangsaixing
 * @description
 * @date Created in 下午6:44 2017/11/29
 */
@Service
public class RoleServiceImpl implements RoleService{

    public static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    SysRoleMapper sysRoleMapper;
    @Autowired
    UserService userService;
    @Autowired
    SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    StudentService studentService;

    @Override
    public List<SysRole> queryRolesByRole(SysRole sysRole) {
        Example example = new Example(SysRole.class);
        if("".equals(sysRole.getRoleName()) || sysRole.getRoleName() == null){
            example.createCriteria().andEqualTo("deleteStatus",false);
        }
        else {
            example.createCriteria().andEqualTo("deleteStatus", false).andLike("roleName", "%"+sysRole.getRoleName()+"%");
        }
        List<SysRole> list= sysRoleMapper.selectByExample(example);
        return list;
    }

    @Override
    public SysRole getById(Integer id) {
        return sysRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer update(SysRole sysRole) {
        return sysRoleMapper.updateByPrimaryKeySelective(sysRole);
    }

    @Override
    public Integer add(SysRole sysRole) {
        SysUser sysUser = userService.getLoginUser();
        sysRole.setCreateTime(new Date());
        sysRole.setDeleteStatus(false);
        sysRole.setUpdateTime(new Date());
        sysRole.setOperatorNo(sysUser.getOaid());//当前登录工号
        return sysRoleMapper.insertSelective(sysRole);
    }

    @Override
    public List<Integer> getRoleIdByUserId(Integer userId) {
        return sysRoleMapper.getRoleIdByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveUserRole(Integer userId, List<Integer> roleIds, String oaid) {
        SysUser sysUser = userService.getLoginUser();
        if(sysUser.getOaid() == null){
            logger.error(ErrorMessage.SESSION_TIMEOUT);
            return false;
        }
        //删除用户角色
        sysUserRoleMapper.removeRoleByUserId(userId);
        //保存新的角色
        for(Integer roleId : roleIds){
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setCreated(new Date());
            sysUserRole.setDeleteStatus(false);
            sysUserRole.setOperator(sysUser.getOaid());
            sysUserRole.setUserId(userId);
            sysUserRole.setRoleId(roleId);
            sysUserRole.setOaId(oaid);
            int insertResult = sysUserRoleMapper.insert(sysUserRole);
            if(insertResult < 1){
                return false;
            }
        }
        return true;
    }

    @Override
    public List<SysUserRole> getUserByRoleId(Integer roleId,String studentNo) {
        SysUserRole sysUserRole=new SysUserRole() ;
        sysUserRole.setDeleteStatus(false);
        sysUserRole.setRoleId(roleId);
        return sysUserRoleMapper.select(sysUserRole);
    }

    @Override
    public SysUserRole getSysUserRole(SysUserRole sysUserRole){
        sysUserRole.setDeleteStatus(false);
        List<SysUserRole> list = sysUserRoleMapper.select(sysUserRole);
        if(list != null && list.size() >0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<String> getRolesByOaId(String oaId) {
        return sysRoleMapper.getRoleByOaId(oaId);
    }

    @Override
    public SysRole get(SysRole sysRole){
        sysRole.setDeleteStatus(false);
        List<SysRole> list = sysRoleMapper.select(sysRole);
        if(list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<SysUserRole> getOaidByRoleName(String channelmanager) {
        return sysRoleMapper.getOaidByRoleName(channelmanager);
    }
}
