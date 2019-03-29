package com.aoji.service;

import com.aoji.model.AuditInfo;

import java.util.List;

/**
 * @author yangsaixing
 * @description
 * @date Created in 下午6:38 2017/12/12
 */
public interface AuditService {

    /**
     * 查询审批列表
     * @param auditInfo
     * @return
     */
    List<AuditInfo> queryListByParam(AuditInfo auditInfo);

    /**
     * 添加审核信息
     * @param auditInfo
     * @return
     */
    Integer addAuditInfo(AuditInfo auditInfo);
}
