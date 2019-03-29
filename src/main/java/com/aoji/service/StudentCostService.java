package com.aoji.service;

import com.aoji.model.StudentCostInfo;
import com.aoji.vo.StudentCostInfoVO;

import java.util.List;

public interface StudentCostService {

    StudentCostInfoVO getStudentCostInfo(String studentNo);
}
