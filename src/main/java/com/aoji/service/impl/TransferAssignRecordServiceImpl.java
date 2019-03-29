package com.aoji.service.impl;

import com.aoji.mapper.TransferAssignRecordMapper;
import com.aoji.model.TransferAssignRecord;
import com.aoji.service.TransferAssignRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class TransferAssignRecordServiceImpl implements TransferAssignRecordService{

    @Autowired
    TransferAssignRecordMapper transferAssignRecordMapper;

    @Override
    public boolean insert(String studentNo, String type, String operatorNo) {
        TransferAssignRecord transferAssignRecord = new TransferAssignRecord();
        transferAssignRecord.setCreateTime(new Date());
        transferAssignRecord.setStudentNo(studentNo);
        transferAssignRecord.setOperatorNo(operatorNo);
        transferAssignRecord.setType(type);
        int result = transferAssignRecordMapper.insert(transferAssignRecord);
        return result > 0;
    }

    @Override
    public List<TransferAssignRecord> getbyStudentNo(String studentNo, String type) {
        return transferAssignRecordMapper.getbyStudentNo(studentNo, type);
    }
}
