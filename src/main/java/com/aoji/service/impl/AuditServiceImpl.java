package com.aoji.service.impl;

import com.aoji.mapper.AuditInfoMapper;
import com.aoji.model.AuditInfo;
import com.aoji.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yangsaixing
 * @description
 * @date Created in 下午6:43 2017/12/12
 */
@Service
public class AuditServiceImpl implements AuditService {

    @Autowired
    AuditInfoMapper auditInfoMapper;

    @Override
    public List<AuditInfo> queryListByParam(AuditInfo auditInfo) {
        return auditInfoMapper.select(auditInfo);
    }

    @Override
    public Integer addAuditInfo(AuditInfo auditInfo) {
        return auditInfoMapper.insertSelective(auditInfo);
    }
}
