package com.aoji.service;

import com.aoji.model.SysAccessLog;

public interface SysAccessLogService {

    /**
     * 插入记录
     * @param accessLog
     */
    void insert(SysAccessLog accessLog);
}
