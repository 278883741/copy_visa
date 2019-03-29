package com.aoji.mapper;

import com.aoji.model.StudentCostInfo;
import com.aoji.vo.StudentCostInfoVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StudentCostInfoMapper extends Mapper<StudentCostInfo> {

    Integer insertStudentCost(@Param("studentCostInfo") StudentCostInfo studentCostInfo);

    StudentCostInfoVO getStudentCostInfoVO(String studentNo);
}