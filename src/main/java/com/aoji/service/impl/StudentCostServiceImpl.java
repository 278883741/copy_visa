package com.aoji.service.impl;

import com.aoji.mapper.ApplyInfoMapper;
import com.aoji.mapper.StudentCostInfoMapper;
import com.aoji.model.ApplyInfo;
import com.aoji.model.StudentCostInfo;
import com.aoji.service.StudentCostService;
import com.aoji.vo.StudentCostInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentCostServiceImpl implements StudentCostService{

    @Autowired
    private StudentCostInfoMapper studentCostInfoMapper;

    @Autowired
    private ApplyInfoMapper applyInfoMapper;

    @Override
    public StudentCostInfoVO getStudentCostInfo(String studentNo) {
        StudentCostInfoVO studentCostInfoVO = studentCostInfoMapper.getStudentCostInfoVO(studentNo);
                //根据学生的学号获取学生的院校
            if(studentCostInfoVO != null && StringUtils.hasText(studentCostInfoVO.getStudentNo())){
                List<String> collegeList = applyInfoMapper.getCollegeByStudentNo(studentCostInfoVO.getStudentNo());
                if(collegeList != null && collegeList.size() > 0){
                    List<String> colleges = new ArrayList<>();
                    for(int j = 0; j < collegeList.size(); j++){
                        colleges.add(collegeList.get(j));
                        if (j == 2){
                            break;
                        }
                    }
                    studentCostInfoVO.setCollege(colleges);
                }
            }

        return studentCostInfoVO;
    }
}
