package com.aoji.service;

import com.aoji.model.VisitInfo;

import java.util.List;
/**
 * @author 赵剑飞
 * @description 回访的服务
 * @date Created in 下午2:29 2017/12/7
 */
public interface VisitInfoService {
    /**
     * 添加实体
     * @param visitInfo
     * @return
     */
    Integer add(VisitInfo visitInfo);

    /**
     * 更新实体
     * @param visitInfo
     * @return
     */
    Integer update(VisitInfo visitInfo);

    /**
     * 获取回访列表
     * @param visitInfo 实体
     * @return
     */
    List<VisitInfo> getList(VisitInfo visitInfo);

    /**
     * 根据申请id查询回访
     * @param id
     * @return
     */
    VisitInfo get(Integer id);

    /**
     * 获取回访列表用于补件展示
     * @param visitInfo 实体
     * @return
     */
    List<VisitInfo> getListForSupplement(Integer applyId);
}
