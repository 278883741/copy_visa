package com.aoji.service.impl;

import com.aoji.mapper.StudentRemarkInfoMapper;
import com.aoji.model.StudentRemarkInfo;
import com.aoji.service.StudentRemarkInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StudentRemarkInfoServiceImpl implements StudentRemarkInfoService {

    @Autowired
    StudentRemarkInfoMapper studentRemarkInfoMapper;

    @Override
    public List<StudentRemarkInfo> select(StudentRemarkInfo studentRemarkInfo) {
        studentRemarkInfo.setDeleteStatus(false);
        return studentRemarkInfoMapper.select(studentRemarkInfo);
    }

    @Override
    public int insert(StudentRemarkInfo studentRemarkInfo) {
        studentRemarkInfo.setCreateTime(new Date());
        studentRemarkInfo.setDeleteStatus(false);
        return studentRemarkInfoMapper.insert(studentRemarkInfo);
    }
}
