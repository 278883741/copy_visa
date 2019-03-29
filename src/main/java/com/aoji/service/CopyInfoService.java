package com.aoji.service;

import com.aoji.model.StudentCopyInfo;

import java.util.List;

public interface CopyInfoService {

    /**
     * 查询文书列表
     * @param studentCopyInfo
     * @return
     */
    List<StudentCopyInfo> getList(StudentCopyInfo studentCopyInfo);

    /**
     * 查询文书列表（排序）
     * @param studentCopyInfo
     * @return
     */
    List<StudentCopyInfo> getListByExample(StudentCopyInfo studentCopyInfo);
}
