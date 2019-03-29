package com.aoji.service;

import com.aoji.model.StudentStatusRecord;

public interface StudentStatusRecordService {
    StudentStatusRecord get(String studentNo,Integer status);
}
