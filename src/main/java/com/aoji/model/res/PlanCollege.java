package com.aoji.model.res;

import com.aoji.model.PlanCollegeInfo;

public class PlanCollege extends PlanCollegeInfo{

    /**
     * 申请类别：0-直录 1-双录 2-纯语言
     */
    private Integer applyType;

    /**
     * 是否加申
     */
    private Boolean addStatus;

    /**
     * 定校方案id
     */
    private Integer planId;

    /**
     * 内网学号
     */
    private String studentNo;

    /**
     * 留学国家
     */
    private Integer nation;

    /**
     * 合作机构
     */
//    private Integer agency;

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public Boolean getAddStatus() {
        return addStatus;
    }

    public void setAddStatus(Boolean addStatus) {
        this.addStatus = addStatus;
    }

    @Override
    public Integer getPlanId() {
        return planId;
    }

    @Override
    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public Integer getNation() {
        return nation;
    }

    public void setNation(Integer nation) {
        this.nation = nation;
    }

//    public Integer getAgency() {
//        return agency;
//    }
//
//    public void setAgency(Integer agency) {
//        this.agency = agency;
//    }
}
