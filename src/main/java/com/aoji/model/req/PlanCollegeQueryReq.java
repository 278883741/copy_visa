package com.aoji.model.req;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class PlanCollegeQueryReq {

    @ApiModelProperty(value = "学号")
    String studentNo;

    @ApiModelProperty(value = "分支ID")
    List<Integer> branchIds;

    @ApiModelProperty(value = "国家ID")
    List<Integer> countryIds;

    @ApiModelProperty(value = "录入人编号")
    String operatorNo;

    @ApiModelProperty(value = "审批状态")
    Integer auditStatus;

    @ApiModelProperty(value = "当前页号")
    Integer page;

    @ApiModelProperty(value = "每页数量")
    Integer pageSize;

    @Override
    public String toString() {
        return "PlanCollegeQueryReq{" +
                "studentNo='" + studentNo + '\'' +
                ", branchIds=" + branchIds +
                ", countryIds=" + countryIds +
                ", operatorNo='" + operatorNo + '\'' +
                ", auditStatus=" + auditStatus +
                ", page=" + page +
                ", pageSize=" + pageSize +
                '}';
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public List<Integer> getBranchIds() {
        return branchIds;
    }

    public void setBranchIds(List<Integer> branchIds) {
        this.branchIds = branchIds;
    }

    public List<Integer> getCountryIds() {
        return countryIds;
    }

    public void setCountryIds(List<Integer> countryIds) {
        this.countryIds = countryIds;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOperatorNo() {
        return operatorNo;
    }

    public void setOperatorNo(String operatorNo) {
        this.operatorNo = operatorNo;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }
}
