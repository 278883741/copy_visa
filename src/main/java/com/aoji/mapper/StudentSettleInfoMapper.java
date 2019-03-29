package com.aoji.mapper;

import com.aoji.model.StudentInfo;
import com.aoji.model.StudentSettleInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StudentSettleInfoMapper extends Mapper<StudentSettleInfo> {

    List<StudentSettleInfo> getStudentSettleByOperator();

    List<StudentInfo> getSettleList(@Param("studentInfo") StudentInfo studentInfo);

    List<StudentSettleInfo> checkAllSettleList(@Param("studentSettleInfo")StudentSettleInfo studentSettleInfo,@Param("nationId")Integer nationId,@Param("studentName")String studentName);

    }