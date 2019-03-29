package com.aoji.service;

import com.aoji.model.FollowServiceInfo;

import java.util.List;

/**
 * author: chenhaibo
 * description: 后续管理接口
 * date: 2017/12/26
 */
public interface FollowServiceInfoService {

    /**
     * 查询列表
     * @param followServiceInfo
     * @return
     */
    List<FollowServiceInfo> getList(FollowServiceInfo followServiceInfo);

    /**
     * 查询列表（按创建时间倒序排列）
     * @param followServiceInfo
     * @return
     */
    List<FollowServiceInfo> getListByExample(FollowServiceInfo followServiceInfo);

    /**
     * 添加
     * @param followServiceInfo
     * @return
     */
    int insert(FollowServiceInfo followServiceInfo);

    /**
     * 修改
     * @param followServiceInfo
     * @return
     */
    int updateById(FollowServiceInfo followServiceInfo);

    int  deleteFollowServiceInfo(FollowServiceInfo followServiceInfo);

}
