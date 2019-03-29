package com.aoji.service;
import com.aoji.model.*;

import java.util.List;

public interface UserGroupRelationService {
    List<UserGroupRelation> getList(UserGroupRelation userGroupRelation);
    UserGroupRelation get(UserGroupRelation userGroupRelation);

    /**
     * 根据oaId查找其作为小组负责人所在小组的全部组员的员工工号
     * @param oaId 员工工号
     * @return
     */
    List<String> getOaIdsByOAIdAndLeader(String oaId);

    Integer update(String oaid,Integer leaderStatus);

    UserGroupRelation getUserGroupRelationByOaid(String oaid);

    /**
     * 获取文签总监所负责的全部组员的员工工号
     */
    List<String> getOaidsByOaid(String oaId);
}
