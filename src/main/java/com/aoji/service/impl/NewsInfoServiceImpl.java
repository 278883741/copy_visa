package com.aoji.service.impl;

import com.aoji.mapper.NewsInfoMapper;
import com.aoji.model.NewsInfo;
import com.aoji.service.NewsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsInfoServiceImpl implements NewsInfoService {

    @Autowired
    private NewsInfoMapper newsInfoMapper;

    @Override
    public List<NewsInfo> newsInfoList(Integer pageIndex) {
        pageIndex = (pageIndex-1)*10;
        return newsInfoMapper.queryList(pageIndex);
    }
}
