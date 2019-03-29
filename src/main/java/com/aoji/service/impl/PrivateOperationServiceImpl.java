package com.aoji.service.impl;

import com.aoji.mapper.PrivateOperationRecordMapper;
import com.aoji.model.PrivateOperationRecord;
import com.aoji.service.PrivateOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrivateOperationServiceImpl implements PrivateOperationService {
    @Autowired
    PrivateOperationRecordMapper privateOperationRecordMapper;

    @Override
    public Integer add(PrivateOperationRecord privateOperationRecord){
        return privateOperationRecordMapper.insert(privateOperationRecord);
    }
}
