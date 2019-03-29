package com.aoji.service.impl;

import com.aoji.contants.Contants;
import com.aoji.mapper.SysUserMapper;
import com.aoji.mapper.UserGroupMapper;
import com.aoji.mapper.UserGroupRelationMapper;
import com.aoji.model.SysUser;
import com.aoji.model.UserGroup;
import com.aoji.model.UserGroupRelation;
import com.aoji.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserGroupSerivceImpl implements UserGroupService {

    @Autowired
    private UserGroupMapper userGroupMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private UserGroupRelationMapper userGroupRelationMapper;

    @Override
    public List<UserGroup> getUserGroupByGroupName(UserGroup userGroup) {
        String groupName = null;
        if (StringUtils.hasText(userGroup.getGroupName())) {
            groupName = '%' + userGroup.getGroupName() + '%';
        }
        List<UserGroup> userGroupList = userGroupMapper.getUserGroup(groupName);
        return userGroupList;
    }

    @Override
    public List<UserGroup> getUserGroup(UserGroup userGroup) {
        return userGroupMapper.select(userGroup);
    }

    @Override
    public int toUpdateUserGroup(UserGroup userGroup) {
        Example example = new Example(UserGroup.class);
        example.createCriteria().andEqualTo("id", userGroup.getId()).andEqualTo("deleteStatus", 0);
        return userGroupMapper.updateByExampleSelective(userGroup, example);
    }

    @Override
    public UserGroup getUserGroupById(String id) {
        UserGroup userGroup = userGroupMapper.getUserGroupById(id);
        return userGroup;
    }

    @Override
    public int saveUserGroup(UserGroup userGroup) {
        return userGroupMapper.insert(userGroup);
    }

    @Override
    public List<Integer> getGroupIds(Integer userId) {
        List<Integer> groupIds = userGroupMapper.getGroupIds(userId);
        return groupIds;
    }

    @Override
    public boolean saveUserGroup(Integer id, List<Integer> roleIds, String operator) {
        SysUser sysUser = new SysUser();
        sysUser.setId(Long.valueOf(id));
        sysUser = sysUserMapper.selectByPrimaryKey(sysUser);
        //删除oaid对应的用户组信息
        userGroupRelationMapper.deleteUserGroup(sysUser.getOaid());
        //增加小组消息
        for (Integer groupid : roleIds) {
            UserGroupRelation userGroupRelation = new UserGroupRelation();
            userGroupRelation.setCreateTime(new Date());
            userGroupRelation.setLeaderStatus(false);
            userGroupRelation.setDeleteStatus(false);
            userGroupRelation.setGroupId(groupid);
            userGroupRelation.setOaid(sysUser.getOaid());
            userGroupRelation.setOperatorNo(operator);
            int insertResult = userGroupRelationMapper.insert(userGroupRelation);
            if (insertResult < 1) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean saveNationGroup(Integer nationId, String operator, String groupName, String id) {
        UserGroup userGroupNew = new UserGroup();
        if (StringUtils.hasText(id)) {
            userGroupNew.setGroupName(groupName);
            userGroupNew.setDeleteStatus(false);
            userGroupNew.setOperatorNo(operator);
            userGroupNew.setUpdateTime(new Date());
            userGroupNew.setNation(nationId);
            userGroupNew.setId(Integer.valueOf(id));
            int updateResult = userGroupMapper.updateByPrimaryKeySelective(userGroupNew);
            if (updateResult < 1) {
                return false;
            }
        } else {
            userGroupNew.setGroupName(groupName);
            userGroupNew.setCreateTime(new Date());
            userGroupNew.setDeleteStatus(false);
            userGroupNew.setOperatorNo(operator);
            userGroupNew.setNation(nationId);
            int insertResult = userGroupMapper.insert(userGroupNew);
            if (insertResult < 1) {
                return false;
            }
        }

        return true;
    }

    @Override
    public List<String> getNationList(String groupName) {
        return userGroupMapper.selectNationByGroupName(groupName);
    }

    @Override
    public List<UserGroup> getUserGroupByoaidAndLeaderStatus(String oaid, Boolean leaderStatus) {
        leaderStatus = null;
        return userGroupMapper.getByoaidAndLeaderStatus(oaid, leaderStatus);
    }

    /**
     * 根据用户组的id，获取当前用户组下的所有用户成员
     *
     * @param userGroup
     * @return
     */
    @Override
    public List<Map<String, Object>> getUserByUserGroupId(Integer userGroup) {

        return this.userGroupMapper.getUserByUserGroupId(userGroup);

    }
}
