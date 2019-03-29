package com.aoji.service;

import com.aoji.model.SysUser;
import com.aoji.model.UserGroup;

import java.util.List;
import java.util.Map;

public interface UserGroupService {

    /**
     * 获取用户组信息(模糊查询)
     *
     * @param userGroup
     * @return
     */
    List<UserGroup> getUserGroupByGroupName(UserGroup userGroup);

    /**
     * 获取用户组信息
     *
     * @param userGroup
     * @return
     */
    List<UserGroup> getUserGroup(UserGroup userGroup);

    /**
     * 修改用户组
     *
     * @param userGroup
     * @return
     */
    int toUpdateUserGroup(UserGroup userGroup);

    /**
     * 根据id或许用户组
     *
     * @param id
     * @return
     */
    UserGroup getUserGroupById(String id);

    /**
     * 添加用户组
     *
     * @param userGroup
     * @return
     */
    int saveUserGroup(UserGroup userGroup);

    /**
     * 根据id获取用户组id
     *
     * @param userId
     * @return
     */
    List<Integer> getGroupIds(Integer userId);

    boolean saveUserGroup(Integer id, List<Integer> roleIds, String operator);

    boolean saveNationGroup(Integer nationId, String operator, String groupNameame, String id);

    List<String> getNationList(String groupName);

    /**
     * 根据用户组的id，获取当前用户组下的所有用户成员
     *
     * @param userGroup
     * @return
     */
    List<Map<String, Object>> getUserByUserGroupId(Integer userGroup);

    List<UserGroup> getUserGroupByoaidAndLeaderStatus(String oaid, Boolean leaderStatus);
}
