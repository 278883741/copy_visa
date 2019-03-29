package com.aoji.service;

import com.aoji.model.StudentSettleRecord;

public interface StudentSettleRecordService {
    /**
     * 添加补件信息
     * @param studentSettleRecord
     * @return
     */
    Integer add(StudentSettleRecord studentSettleRecord);
}
