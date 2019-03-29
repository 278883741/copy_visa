package com.aoji.service;
import com.aoji.model.AgencyInfo;
import com.aoji.model.TreeItem;

import java.util.List;

/**
 * @author 赵剑飞
 * @description 合作机构的服务
 * @date Created in 下午2:29 2017/12/7
 */
public interface AgencyService {
    /**
     * 获取合作机构列表
     * @param agencyInfo 实体
     * @return
     */
    List<AgencyInfo> get(AgencyInfo agencyInfo);

    /**
     * 根据申请id查询合作机构
     * @param id
     * @return
     */
    AgencyInfo getById(Integer id);

    /**
     * 更新实体
     * @param agencyInfo
     * @return
     */
    Integer update(AgencyInfo agencyInfo);

    /**
     * 根据id删除实体
     * @param id
     * @return
     */
    Integer delete(Integer id);

    /**
     * 添加实体
     * @param agencyInfo
     * @return
     */
    Integer add(AgencyInfo agencyInfo);

    /**
     * 获取添加页中的国家信息
     * @param parentId
     * @return
     */
    TreeItem getCountry(Integer parentId);

    /**
     * 获取编辑，详情页中的国家信息
     * @param checkdCountry
     * @param action
     * @return
     */
    TreeItem getCountryEdit(String checkdCountry,String action);
}
