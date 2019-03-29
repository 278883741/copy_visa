package com.aoji.mapper;

import com.aoji.model.StudentDelayInfo;
import com.aoji.vo.StudentInfoVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StudentDelayInfoMapper extends Mapper<StudentDelayInfo> {

    List<StudentInfoVo> getStudentInfoVo();
}