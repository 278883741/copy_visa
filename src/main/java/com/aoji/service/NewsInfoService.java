package com.aoji.service;

import com.aoji.model.NewsInfo;

import java.util.List;

public interface NewsInfoService {

    List<NewsInfo> newsInfoList(Integer pageIndex);
}
