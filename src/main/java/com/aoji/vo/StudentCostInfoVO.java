package com.aoji.vo;

import java.util.List;

/**
 * @author wangjunqiang
 * @description
 * @date Created in 2018/5/10 17:53
 */
public class StudentCostInfoVO {

    private String studentNo;

    private Integer countMoney;

    private List<String> college;

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public Integer getCountMoney() {
        return countMoney;
    }

    public void setCountMoney(Integer countMoney) {
        this.countMoney = countMoney;
    }

    public List<String> getCollege() {
        return college;
    }

    public void setCollege(List<String> college) {
        this.college = college;
    }
}
