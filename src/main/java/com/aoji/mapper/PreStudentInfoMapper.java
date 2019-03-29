package com.aoji.mapper;

import com.aoji.model.PreStudentInfo;
import com.aoji.model.StudentInfo;
import com.aoji.model.SysUser;
import com.aoji.vo.StudentInfoVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PreStudentInfoMapper extends Mapper<PreStudentInfo> {

    List<PreStudentInfo> selectPreStudentInfo(@Param("preStudentInfo")PreStudentInfo preStudentInfo,@Param("roleName") String roleName);

    List<SysUser> getAllotTeacher();

    Integer updatePreStudentInfoByStudentNo(@Param("preStudentInfo")PreStudentInfo preStudentInfo);

    Integer updateIsTransferByStudentNo(String studentNo);

    List<StudentInfoVo> getStudentInfo();

    Integer getOldNationId(String nationName);
}