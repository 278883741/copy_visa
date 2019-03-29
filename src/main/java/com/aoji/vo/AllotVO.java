package com.aoji.vo;

/**
 * @author wangjunqiang
 * @description
 * @date Created in 2018/5/24 18:01
 */
public class AllotVO {

    private String studentNo;

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    private Integer allotType;

    private String AllotOperator;

    private String reason;

    public Integer getAllotType() {
        return allotType;
    }

    public void setAllotType(Integer allotType) {
        this.allotType = allotType;
    }

    public String getAllotOperator() {
        return AllotOperator;
    }

    public void setAllotOperator(String allotOperator) {
        AllotOperator = allotOperator;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
