package com.aoji.model.res;

import com.aoji.vo.StudentDetailVO;

public class StudentInfoRes {

    /**
     * 状态
     */
    int state;
    /**
     * 消息
     */
    String msg;
    /**
     * 学生详情数据
     */
    StudentDetailVO data;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public StudentDetailVO getData() {
        return data;
    }

    public void setData(StudentDetailVO data) {
        this.data = data;
    }
}
