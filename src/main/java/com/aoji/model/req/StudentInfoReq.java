package com.aoji.model.req;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class StudentInfoReq {

    @ApiModelProperty(value = "学号")
    String studentNo;

    @ApiModelProperty(value = "学生姓名")
    String studentName;

    @ApiModelProperty(value = "签约日期开始")
    String signDateStart;

    @ApiModelProperty(value = "签约日期结束")
    String signDateEnd;

    @ApiModelProperty(value = "学生状态")
    Integer studentStatus;

    @ApiModelProperty(value = "学生进程状态")
    Integer processStatus;

    @ApiModelProperty(value = "分支ID")
    List<Integer> branchIds;

    @ApiModelProperty(value = "国家ID")
    List<Integer> countryIds;

    @ApiModelProperty(value = "管理者标识（false代表顾问）", required = true)
    Boolean managerFlag;

    @ApiModelProperty(value = "顾问工号")
    String consultorNo;

    @ApiModelProperty(value = "当前页号")
    Integer page;

    @ApiModelProperty(value = "每页数量")
    Integer pageSize;

    @Override
    public String toString() {
        return "StudentInfoReq{" +
                "studentNo='" + studentNo + '\'' +
                ", studentName='" + studentName + '\'' +
                ", signDateStart='" + signDateStart + '\'' +
                ", signDateEnd='" + signDateEnd + '\'' +
                ", studentStatus='" + studentStatus + '\'' +
                ", processStatus='" + processStatus + '\'' +
                ", branchIds=" + branchIds +
                ", countryIds=" + countryIds +
                ", managerFlag=" + managerFlag +
                ", consultorNo='" + consultorNo + '\'' +
                ", page=" + page +
                ", pageSize=" + pageSize +
                '}';
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

    public Boolean getManagerFlag() {
        return managerFlag;
    }

    public void setManagerFlag(Boolean managerFlag) {
        this.managerFlag = managerFlag;
    }

    public String getConsultorNo() {
        return consultorNo;
    }

    public void setConsultorNo(String consultorNo) {
        this.consultorNo = consultorNo;
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

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSignDateStart() {
        return signDateStart;
    }

    public void setSignDateStart(String signDateStart) {
        this.signDateStart = signDateStart;
    }

    public String getSignDateEnd() {
        return signDateEnd + " 23:59:59";
    }

    public void setSignDateEnd(String signDateEnd) {
        this.signDateEnd = signDateEnd;
    }

    public Integer getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(Integer studentStatus) {
        this.studentStatus = studentStatus;
    }

    public Integer getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Integer processStatus) {
        this.processStatus = processStatus;
    }
}
