package com.aoji.service.impl;

import com.aoji.contants.Contants;
import com.aoji.mapper.StudentStatusRecordMapper;
import com.aoji.model.StudentSettleRecord;
import com.aoji.model.StudentStatusRecord;
import com.aoji.service.StudentSettleRecordService;
import com.aoji.service.StudentStatusRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


@Service
public class StudentStatusRecordServiceImpl implements StudentStatusRecordService {
    @Autowired
    StudentStatusRecordMapper studentStatusRecordMapper;

    @Override
    public StudentStatusRecord get(String studentNo, Integer status){
        Example example = new Example(StudentStatusRecord.class);

        example.createCriteria().andEqualTo("deleteStatus",false).andEqualTo("studentNo",studentNo).andEqualTo("statusCode",status);

        List<StudentStatusRecord> list = studentStatusRecordMapper.selectByExample(example);

        if(list.size()>0) {
            return list.get(list.size() - 1);
        }
        else{
            return null;
        }
    }
}
