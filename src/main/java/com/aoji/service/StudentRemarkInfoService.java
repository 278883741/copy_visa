package com.aoji.service;

import com.aoji.model.StudentRemarkInfo;

import java.util.List;

public interface StudentRemarkInfoService {

    List<StudentRemarkInfo> select(StudentRemarkInfo studentRemarkInfo);

    int insert(StudentRemarkInfo studentRemarkInfo);
}
