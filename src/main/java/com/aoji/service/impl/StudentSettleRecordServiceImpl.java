package com.aoji.service.impl;

import com.aoji.mapper.StudentSettleRecordMapper;
import com.aoji.model.StudentSettleRecord;
import com.aoji.service.StudentSettleRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentSettleRecordServiceImpl implements StudentSettleRecordService {
    @Autowired
    StudentSettleRecordMapper studentSettleRecordMapper;

    @Override
    public Integer add(StudentSettleRecord studentSettleRecord){
        return studentSettleRecordMapper.insert(studentSettleRecord);
    }
}
