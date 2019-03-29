package com.aoji.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class XiStudentInfoVo {
    @ApiModelProperty("学生id")
    private Integer id;
    @ApiModelProperty("学号")
    private String studentNo;
    @ApiModelProperty("学生姓名")
    private String studentName;
    @ApiModelProperty("服务进程状态")
    private Integer status;
    @ApiModelProperty("签约国家id")
    private Integer nationId;
    @ApiModelProperty("签约国家")
    private String nationName;
    @ApiModelProperty("签约时间")
    private String signDate;
    @ApiModelProperty("服务进程状态")
    private String updateTime;
    @ApiModelProperty("定校方案进程时间")
    private String planTime;
    @ApiModelProperty("院校申请进程时间")
    private String applyTime;
    @ApiModelProperty("签证进程时间")
    private String visaTime;
    @ApiModelProperty("结案进程时间")
    private String settleTime;
    @ApiModelProperty("合同名称")
    private String contractNo;
    @ApiModelProperty("邮箱")
    private String emailAccount;
    @ApiModelProperty("分支名称")
    private String branchName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getNationId() {
        return nationId;
    }

    public void setNationId(Integer nationId) {
        this.nationId = nationId;
    }

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getPlanTime() {
        return planTime;
    }

    public void setPlanTime(String planTime) {
        this.planTime = planTime;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getVisaTime() {
        return visaTime;
    }

    public void setVisaTime(String visaTime) {
        this.visaTime = visaTime;
    }

    public String getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(String settleTime) {
        this.settleTime = settleTime;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getEmailAccount() {
        return emailAccount;
    }

    public void setEmailAccount(String emailAccount) {
        this.emailAccount = emailAccount;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
