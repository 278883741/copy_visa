package com.aoji.service;

import com.aoji.model.BaseResponse;
import com.aoji.model.StudentCopyInfo;
import com.aoji.model.StudentInfo;

import java.util.List;

public interface StudentCopyInfoService {

    /**
     * 修改/增加学校文书
     * @param studentCopyInfo
     * @param id
     * @return
     */
    int saveStudentCopyInfo(StudentCopyInfo studentCopyInfo,String id);

    /**
     * 根据学号查询文书信息
     * @param studentNo 学号
     * @return
     */
    List<StudentCopyInfo> queryByStudentNo(String studentNo);

    /**
     * 查询文书信息
     * @param studentCopyInfo
     * @return
     */
    StudentCopyInfo query(StudentCopyInfo studentCopyInfo);

    /**
     * 删除
     * @param studentCopyInfo
     * @return
     */
    int delete(StudentCopyInfo studentCopyInfo);

    Integer updateApplyInfo(String copyUrl,String studentNo,String applyId);

    Integer updateSupplementInfo(String copyUrl,String applyId);

    BaseResponse insertStudentCopyInfo(StudentCopyInfo studentCopyInfo) throws Exception;


}
