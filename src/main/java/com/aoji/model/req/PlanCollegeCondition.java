package com.aoji.model.req;

public class PlanCollegeCondition {

    String studentNo;

    Integer planId;

    Integer collegeId;

    @Override
    public String toString() {
        return "PlanCollegeCondition{" +
                "studentNo='" + studentNo + '\'' +
                ", planId=" + planId +
                ", collegeId=" + collegeId +
                '}';
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Integer collegeId) {
        this.collegeId = collegeId;
    }
}
