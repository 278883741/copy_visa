package com.aoji.service.impl;

import com.aoji.model.ConfirmRecord;
import com.aoji.mapper.ConfirmRecordMapper;
import com.aoji.service.ConfirmRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ConfirmRecordServiceImpl implements ConfirmRecordService {
    @Autowired
    ConfirmRecordMapper confirmRecordMapper;

    @Override
    public Integer addOne(ConfirmRecord confirmRecord){

        return confirmRecordMapper.insert(confirmRecord);
    }

    @Override
    public ConfirmRecord get(String studentNo,Integer caseId){
        Example example = new Example(ConfirmRecord.class);
        example.createCriteria().andEqualTo("studentNo", studentNo).andEqualTo("caseId", caseId).andEqualTo("deleteStatus", "false");
        List<ConfirmRecord> confirmRecords = confirmRecordMapper.selectByExample(example);
        return confirmRecords.get(confirmRecords.size()-1);
    }

}
