package com.aoji.mapper;

import com.aoji.model.FollowServiceResult;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface FollowServiceResultMapper extends Mapper<FollowServiceResult> {

    FollowServiceResult getFollowServiceResultById(@Param("id") String id);

    int updateFollowServicResult(@Param("followServiceResult")FollowServiceResult followServiceResult);




}