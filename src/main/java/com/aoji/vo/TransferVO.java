package com.aoji.vo;

import com.aoji.model.TransferSendInfo;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class TransferVO extends TransferSendInfo{

    /**
     * 查询类型  文签/外联
     */
    private String queryType;

    /**
     * 是否为经理 Y-是； N-否
     */
    private String manager;

    /**
     * 确认状态：1-通过 2-拒绝
     */
    private Integer confirmStatus;

    /**
     * 确认日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date confirmDate;

    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 合同类型
     */
    private Integer contractType;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;

    /**
     * 机构名称
     */
    private String branchName;

    /**
     * 申请院校名称
     */
    private String collegeName;

    /**
     * 意见或原因
     */
    private String comment;

    /**
     * 文签顾问
     */
    private String copyOperatorName;

    /**
     * 咨询顾问
     */
    private String salesConsultant;

    /**
     * 是否为转组
     */
    private Boolean isChangeGroup;

    public Integer getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Integer confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Integer getContractType() {
        return contractType;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCopyOperatorName() {
        return copyOperatorName;
    }

    public void setCopyOperatorName(String copyOperatorName) {
        this.copyOperatorName = copyOperatorName;
    }

    public String getSalesConsultant() {
        return salesConsultant;
    }

    public void setSalesConsultant(String salesConsultant) {
        this.salesConsultant = salesConsultant;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public Boolean getChangeGroup() {
        return isChangeGroup;
    }

    public void setChangeGroup(Boolean changeGroup) {
        isChangeGroup = changeGroup;
    }
}
