package com.aoji.service.impl;

import com.aoji.mapper.NewsInfoMapper;
import com.aoji.model.NewsInfo;
import com.aoji.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author yangsaixing
 * @description
 * @date Created in 下午3:34 2018/1/25
 */
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    NewsInfoMapper newsInfoMapper;

    private Logger logger= LoggerFactory.getLogger(NewsServiceImpl.class);

    @Override
    public List<NewsInfo> queryByPage(NewsInfo newsInfo) {
        return newsInfoMapper.select(newsInfo);
    }

    @Override
    public NewsInfo queryById(Integer id) {
        return newsInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer addNews(NewsInfo newsInfo) {
        return newsInfoMapper.insert(newsInfo);
    }

    @Override
    public Integer updateNews(NewsInfo newsInfo) {
        Example example=new Example(NewsInfo.class);
        example.createCriteria().andEqualTo("id",newsInfo.getId()).andEqualTo("deleteStatus",false);
        return newsInfoMapper.updateByExampleSelective(newsInfo,example);
    }
}
