package com.aoji.mapper;

import com.aoji.model.UserGroupRelation;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserGroupRelationMapper extends Mapper<UserGroupRelation> {

    int deleteUserGroup(@Param("oaid") String oaid);

    /**
     * 根据oaId查找其作为小组负责人所在小组的全部组员的员工工号
     * @param oaId
     * @return
     */
    List<String> getOaIdsByOAIdAndLeader(String oaId);

    Integer updateByOaid(@Param("oaid")String oaid ,@Param("leaderStatus") Integer leaderStatus);

    UserGroupRelation getUserGroupRelationByOaid(@Param("oaid") String oaid);

    /**
     * 获取文签总监负责的全部组员的员工工号
     * @param oaId
     * @return
     */
    List<String> getOaIdsByOaId(String oaId);


    List<String> getPlanningConsultantsByOaid(String oaid);

}