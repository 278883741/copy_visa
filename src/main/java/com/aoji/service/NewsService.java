package com.aoji.service;

import com.aoji.model.NewsInfo;

import java.util.List;

/**
 * @author yangsaixing
 * @description 公告接口
 * @date Created in 下午3:29 2018/1/25
 */
public interface NewsService {

    /**
     * 根据公告实体分页查询公告列表信息
     * @param newsInfo 公告实体
     * @return
     */
    List<NewsInfo> queryByPage(NewsInfo newsInfo);

    /**
     * 根据公告id查询公告信息
     * @param id 公告id
     * @return
     */
    NewsInfo queryById(Integer id);
    /**
     * 添加公告信息
     * @param newsInfo 公告参数
     * @return
     */
    Integer addNews(NewsInfo newsInfo);

    /**
     * 更新公告信息
     * @param newsInfo 公告参数
     * @return
     */
    Integer updateNews(NewsInfo newsInfo);
}
