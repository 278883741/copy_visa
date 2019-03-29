package com.aoji.mapper;

import com.aoji.model.StudentCopyInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface StudentCopyInfoMapper extends Mapper<StudentCopyInfo> {

    StudentCopyInfo getStudentCopyInfoByApplyId(@Param("studentNo") String studentNo, @Param("applyId") Integer applyId);
}