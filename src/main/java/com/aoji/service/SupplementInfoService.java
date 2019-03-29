package com.aoji.service;

import com.aoji.model.SupplementInfo;

import java.util.List;

public interface SupplementInfoService {
    /**
     * 查询补件信息
     * @param supplementInfo
     * @return
     */
    List<SupplementInfo> getList(SupplementInfo supplementInfo);

    /**
     * 查询补件信息，NPA非私密附件，私密附件地址前台获取，不绑定
     * @param supplementInfo
     * @return
     */
    List<SupplementInfo> getListNPA(SupplementInfo supplementInfo);

    /**
     * 根据id获取补件信息
     * @param id
     * @return
     */
    SupplementInfo getById(Integer id);
    /**
     * 更新补件信息
     * @param supplementInfo
     * @return
     */
    Integer update( SupplementInfo supplementInfo);
    /**
     * 删除补件信息
     * @param supplementInfo
     * @return
     */
    Integer deleteOne(Integer id);
    /**
     * 添加补件信息
     * @param supplementInfo
     * @return
     */
    Integer addOne( SupplementInfo supplementInfo);

    /**
     * 根据补件参数实体查询补件信息
     * @param supplementInfo
     * @return
     */
    List<SupplementInfo> getListBySupplement(SupplementInfo supplementInfo);
}
