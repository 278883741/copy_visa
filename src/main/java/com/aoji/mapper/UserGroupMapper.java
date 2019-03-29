package com.aoji.mapper;

import com.aoji.model.UserGroup;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface UserGroupMapper extends Mapper<UserGroup> {

    List<UserGroup> getUserGroup(@Param("groupName") String groupName);

    UserGroup getUserGroupById(@Param("id") String id);

    List<Integer> getGroupIds(@Param("id") Integer id);

    Integer deleteUserGroup(@Param("id") Integer id);

    List<String> selectNationByGroupName(@Param("groupName") String groupName);

    /**
     * 根据用户组的id，获取当前用户组下的所有用户成员
     *
     * @param userGroup
     * @return
     */
    List<Map<String, Object>> getUserByUserGroupId(@Param("userGroup") Integer userGroup);

    List<UserGroup> getByoaidAndLeaderStatus(@Param("oaid") String oaid,
                                             @Param("leaderStatus") Boolean leaderStatus);

    List<String> getNation(String oaid);

}