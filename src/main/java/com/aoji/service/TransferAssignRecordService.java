package com.aoji.service;

import com.aoji.model.TransferAssignRecord;

import java.util.List;

public interface TransferAssignRecordService {

    /**
     * 添加分配记录
     * @param studentNo
     * @param type
     * @param operatorNo
     * @return
     */
    boolean insert(String studentNo, String type, String operatorNo);

    /**
     * 查询分配记录
     * @param studentNo 学号
     * @param type 类型 copy_visa:文签;  outreach-外联
     * @return
     */
    List<TransferAssignRecord> getbyStudentNo(String studentNo, String type);
}
