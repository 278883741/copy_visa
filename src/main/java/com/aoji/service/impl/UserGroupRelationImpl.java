package com.aoji.service.impl;

import com.aoji.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aoji.service.*;
import java.util.ArrayList;
import java.util.List;
import com.aoji.model.*;
import tk.mybatis.mapper.entity.Example;

@Service
public class UserGroupRelationImpl implements UserGroupRelationService{
    @Autowired
    UserGroupRelationMapper userGroupRelationMapper;
    @Override
    public List<UserGroupRelation> getList(UserGroupRelation userGroupRelation) {
        userGroupRelation.setDeleteStatus(false);
        List<UserGroupRelation>list = userGroupRelationMapper.select(userGroupRelation);
        return list;
    }
    @Override
    public UserGroupRelation get(UserGroupRelation userGroupRelation) {
        userGroupRelation.setDeleteStatus(false);
        List<UserGroupRelation>list = userGroupRelationMapper.select(userGroupRelation);
        if(list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<String> getOaIdsByOAIdAndLeader(String oaId) {
        return userGroupRelationMapper.getOaIdsByOAIdAndLeader(oaId);
    }

    @Override
    public Integer update(String oaid,Integer leaderStatus) {
        return userGroupRelationMapper.updateByOaid(oaid,leaderStatus);
    }

    @Override
    public UserGroupRelation getUserGroupRelationByOaid(String oaid) {
        return userGroupRelationMapper.getUserGroupRelationByOaid(oaid);
    }

    @Override
    public List<String> getOaidsByOaid(String oaId) {
        return userGroupRelationMapper.getOaIdsByOaId(oaId);
    }
}
