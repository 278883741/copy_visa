package com.aoji.service;

import com.aoji.model.BaseResponse;
import com.aoji.model.FollowServiceResult;
import com.aoji.model.SysUser;
import org.json.JSONObject;

import java.util.List;


public interface FollowServiceResultService {

    /**
     * 获取数据库中后续申请结果的数据
     * @param followServiceResult
     * @return
     */
    List<FollowServiceResult> getResultList(FollowServiceResult followServiceResult);

    /**
     * 根据id获取申请结果信息
     * @param id
     * @return
     */
    FollowServiceResult getFollowServiceResultById(String id);

    /**
     * 修改学生申请表
     * @param followServiceResult
     * @return
     */
    int update(FollowServiceResult followServiceResult);

    int updateFollowResult(FollowServiceResult followServiceResult);

    int save(FollowServiceResult followServiceResult);

    FollowServiceResult get(FollowServiceResult followServiceResult);

    BaseResponse approve(Integer applyId, Integer type, String remark, String studentNo, String updateTime);

    List<SysUser> getSysUserByRoleName(String roleName);

    List<String> getOaidsByRoleName(String roleName);
}
