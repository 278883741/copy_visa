package com.aoji.service.impl;

import com.aoji.mapper.FailureJobMapper;
import com.aoji.model.FailureJob;
import com.aoji.service.FailureJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author zhaojianfei
 * @description 异步任务表
 * @date Created in 下午2:31 2018/09/7
 */
@Service
public class FailureJobServiceImpl implements FailureJobService {
    @Autowired
    FailureJobMapper failureJobMapper;
    @Override
    public List<FailureJob> getList(FailureJob failureJob) {
        failureJob.setDeleteStatus(false);
        return failureJobMapper.select(failureJob);
    }

    @Override
    public Integer add(FailureJob failureJob){
        return failureJobMapper.insertSelective(failureJob);
    }

    @Override
    public Integer update(FailureJob failureJob) {
        return failureJobMapper.updateByPrimaryKey(failureJob);
    }
}
