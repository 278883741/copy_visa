package com.aoji.service;

import com.aoji.model.StudentSettleInfo;

import java.util.List;

public interface StudentSettleService {
    /**
     * 查询用户列表
     * @param studentNo
     * @return
     */
    List<StudentSettleInfo> getListByStudentNo(String studentNo);
    StudentSettleInfo get(StudentSettleInfo studentSettleInfo);
    boolean add(StudentSettleInfo studentSettleInfo,String operatorNo,String operatorName);
    Integer update(StudentSettleInfo studentSettleInfo);

    List<StudentSettleInfo> getStudentSettle(StudentSettleInfo studentSettleInfo);

    Boolean operatorCancelSettle(String studentNo);

    List<StudentSettleInfo> checkAllSettleList(StudentSettleInfo studentSettleInfo,Integer nationId,String studentName);
}
