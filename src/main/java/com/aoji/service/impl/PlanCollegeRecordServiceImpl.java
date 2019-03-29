package com.aoji.service.impl;

import com.aoji.mapper.PlanCollegeRecordMapper;
import com.aoji.model.PlanCollegeRecord;
import com.aoji.service.PlanCollegeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class PlanCollegeRecordServiceImpl implements PlanCollegeRecordService {

    @Autowired
    PlanCollegeRecordMapper planCollegeRecordMapper;

    @Override
    public List<PlanCollegeRecord> getList(PlanCollegeRecord planCollegeRecord) {
        Example example = new Example(PlanCollegeRecord.class);
        example.createCriteria().andEqualTo("deleteStatus", false)
                .andEqualTo("planCollegeId", planCollegeRecord.getPlanCollegeId());
//                .andEqualTo("type", planCollegeRecord.getType());
        example.setOrderByClause("create_time desc");
        return planCollegeRecordMapper.selectByExample(example);
    }
}
