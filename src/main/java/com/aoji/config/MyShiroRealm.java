package com.aoji.config;

import com.aoji.model.SysPermission;
import com.aoji.model.SysUser;
import com.aoji.service.PermissionService;
import com.aoji.service.RoleService;
import com.aoji.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author yangsaixing
 * @description
 * @date Created in 下午5:14 2017/11/16
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;

    private static final Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);
    /**
     * 认证、登录
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken userToken=(UsernamePasswordToken)authenticationToken;
        String username=userToken.getUsername();
        if(!StringUtils.hasText(username)){
            return null;
        }
        SysUser user=userService.getUserByName(username);
        if(user==null){
            return null;
        }
        return new SimpleAuthenticationInfo(user,user.getPassword(),this.getClass().getName());
    }


    /**
     * 授权 TODO
     * 当访问到页面的时候，链接配置了相应的权限或者shiro标签才会执行此方法否则不会执行，所以如果只是简单的身份认证没有权限的控制的话，那么这个方法可以不进行实现，直接返回null即可
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUser user=(SysUser)principalCollection.fromRealm(this.getClass().getName()).iterator().next();
        SimpleAuthorizationInfo simpleAuthorizationInfo=new SimpleAuthorizationInfo();
        Set<String> roles=new HashSet<>();
        if(user==null){
            logger.warn("授权失败，用户信息异常,用户{}",this.getClass().getName());
            return simpleAuthorizationInfo;
        }
        List<String> roleList = roleService.getRolesByOaId(user.getOaid());
        roleList.forEach(role -> {
            roles.add(role);
        });
        //根据用户的oaid获取用户权限
        List<SysPermission> permissionList = permissionService.getPermissionByOaid(user.getOaid());
        logger.info("权限列表，权限个数："+permissionList.size());
        Set<String> permissions=new HashSet<>();
        for (SysPermission sysPermission:permissionList) {
            permissions.add(sysPermission.getPermissionName());
        }
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.addStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }
}
