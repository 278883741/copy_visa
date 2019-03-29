package com.aoji.mapper;

import com.aoji.model.NewsInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface NewsInfoMapper extends Mapper<NewsInfo> {

    List<NewsInfo> queryList(@Param("pageIndex") Integer pageIndex);
}