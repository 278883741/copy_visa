package com.aoji.service;

import com.aoji.model.StudentInfo;
import com.aoji.model.SysPermission;
import com.aoji.model.SysUser;
import com.aoji.model.dto.UserInfoDTO;
import com.aoji.vo.StudentInfoVo;

import java.util.List;

/**
 * @author yangsaixing
 * @description
 * @date Created in 上午9:27 2017/11/17
 */
public interface UserService {

    /**
     * 根据oaid查询用户信息
     *
     * @param oaid 用户名
     * @return
     */
    SysUser getUserByName(String oaid);

    /**
     * 查询用户信息
     *
     * @param sysUser 用户信息
     * @return
     */
    SysUser getUserInfo(SysUser sysUser);

    /**
     * 根据用户名查询用户权限
     *
     * @param username
     * @return
     */
    List<SysPermission> getFunctionByName(String username);

    /**
     * 查询用户列表
     *
     * @param sysUser
     * @return
     */
    List<SysUser> getList(SysUser sysUser);

    /**
     * 根据用户id查询信息
     *
     * @param id
     * @return
     */
    SysUser getById(Long id);

    /**
     * 更新用户信息
     *
     * @param sysUser
     * @return
     */
    Integer update(SysUser sysUser);

    /**
     * 根据工号查询信息
     *
     * @param oaid
     */
    UserInfoDTO getUserInfoDTOByOaid(String oaid);

    /**
     * 根据国家组和角色查询
     *
     * @param countryBussId 国家对应的内网Id
     * @param role
     * @return
     */
    List<SysUser> getByCountryAndRole(Integer countryBussId, String role, Boolean leaderStatus);

    /**
     * 更新用户信息
     *
     * @param sysUser
     * @return
     */
    Integer add(SysUser sysUser);

    /**
     * 根据角色名查询用户
     *
     * @param roleName
     * @return
     */
    List<SysUser> getByRoleName(String roleName);

    /**
     * 根据工号添加访问该应用的权限
     *
     * @param oaid
     * @return
     */
    Boolean grantAppLogin(String oaid);

    /**
     * 获取当前登录用户
     *
     * @return
     */
    SysUser getLoginUser();

    /**
     * 获取当前登录用户的权限
     *
     * @return
     */
    String getLoginUserByRoleName(StudentInfoVo studentInfo);

    Boolean isChannelStaus();

    Boolean isPlannManager();

    /**
     * 根据组长oaid查询组员
     *
     * @return
     */
    List<SysUser> getByLeaderOaid(String oaid);

    /**
     * 根据oaid查询同国家线其他组长id
     *
     * @return
     */
    List<SysUser> getOtherLeaders(String oaid);

    /**
     * 根据国家和用户组查询用户  （角色为文案经理或文案顾问）
     * @param userGroup
     * @param countryGroup
     * @return
     */
    List<SysUser> getByUserGroupAndCountry(Integer userGroup, Integer countryGroup, Boolean leaderStatus);

    boolean userEnable(String oaId);
    /**
     * 根据用户id查询用户所拥有角色
     */
    List<String> getRolesByOaid(String oaid);

    /**
     * 初始化用户（单点登录）
     * @param sysUser 用户信息
     */
    boolean initUserForSSO(SysUser sysUser);
}
