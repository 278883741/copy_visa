package com.aoji.mapper;

import com.aoji.config.util.FinanceModel;
import com.aoji.model.CostInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface CostInfoMapper extends Mapper<CostInfo> {

    List<Map> queryCosts();
}