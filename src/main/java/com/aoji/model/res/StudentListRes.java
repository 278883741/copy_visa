package com.aoji.model.res;

import com.aoji.model.bo.StudentInfoBO;

import java.util.List;

public class StudentListRes {

    List<StudentInfoBO> studentInfoBOList;

    Integer count;

    public List<StudentInfoBO> getStudentInfoBOList() {
        return studentInfoBOList;
    }

    public void setStudentInfoBOList(List<StudentInfoBO> studentInfoBOList) {
        this.studentInfoBOList = studentInfoBOList;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
