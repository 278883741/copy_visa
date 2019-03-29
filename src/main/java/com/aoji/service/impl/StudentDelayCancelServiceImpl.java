package com.aoji.service.impl;

import com.aoji.service.StudentDelayCancelService;
import com.aoji.model.StudentDelayCancel;
import com.aoji.mapper.StudentDelayCancelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class StudentDelayCancelServiceImpl implements StudentDelayCancelService {
    @Autowired
    StudentDelayCancelMapper studentDelayCancelMapper;

    @Override
    public boolean insert(StudentDelayCancel studentDelayCancel) {
        int result = studentDelayCancelMapper.insert(studentDelayCancel);
        return result > 0;
    }

    @Override
    public boolean update(StudentDelayCancel studentDelayCancel) {
        int result = studentDelayCancelMapper.updateByPrimaryKeySelective(studentDelayCancel);
        return result > 0;
    }

    @Override
    public List<StudentDelayCancel> getByStudentNo(String studentNo) {
        StudentDelayCancel studentDelayInfo = new StudentDelayCancel();
        studentDelayInfo.setDeleteStatus(false);
        studentDelayInfo.setStudentNo(studentNo);
        return studentDelayCancelMapper.select(studentDelayInfo);
    }

    @Override
    public List<StudentDelayCancel> getByDelayId(Integer delayId) {
        StudentDelayCancel studentDelayInfo = new StudentDelayCancel();
        studentDelayInfo.setDeleteStatus(false);
        studentDelayInfo.setDelayId(delayId);
        return studentDelayCancelMapper.select(studentDelayInfo);
    }

}
