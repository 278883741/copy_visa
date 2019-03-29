package com.aoji.service.impl;

import com.aoji.mapper.SysAccessLogMapper;
import com.aoji.model.SysAccessLog;
import com.aoji.service.SysAccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SysAccessLogServiceImpl implements SysAccessLogService {

    @Autowired
    SysAccessLogMapper sysAccessLogMapper;

    @Override
    public void insert(SysAccessLog sysAccessLog) {
        sysAccessLog.setCreateTime(new Date());
        sysAccessLogMapper.insert(sysAccessLog);
    }
}
