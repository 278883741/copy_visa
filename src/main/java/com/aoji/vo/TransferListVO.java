package com.aoji.vo;

import com.aoji.model.TransferInfo;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * author: chenhaibo
 * description: 转案列表
 * date:
 */
public class TransferListVO extends TransferInfo{

    @Override
    public String toString() {
        return "TransferListVO{" +
                "transferIdStr='" + transferIdStr + '\'' +
                ", transferSendType=" + transferSendType +
                ", operatorType=" + operatorType +
                ", confirmStatus=" + confirmStatus +
                ", endCaseStatus=" + endCaseStatus +
                ", operatorNo='" + operatorNo + '\'' +
                ", receiveNo='" + receiveNo + '\'' +
                ", sendId=" + sendId +
                ", operatorName='" + operatorName + '\'' +
                ", receiveName='" + receiveName + '\'' +
                ", sendTime=" + sendTime +
                ", transferReason=" + transferReason +
                ", salesConsultantNo='" + salesConsultantNo + '\'' +
                ", salesConsultant='" + salesConsultant + '\'' +
                ", copyOperatorNo='" + copyOperatorNo + '\'' +
                ", copyOperator='" + copyOperator + '\'' +
                ", copy='" + copy + '\'' +
                ", visaOperator='" + visaOperator + '\'' +
                ", receiveTime=" + receiveTime +
                ", collegeName='" + collegeName + '\'' +
                ", contractNo='" + contractNo + '\'' +
                ", contractType='" + contractType + '\'' +
                ", nationName='" + nationName + '\'' +
                ", birthday=" + birthday +
                ", branchName='" + branchName + '\'' +
                ", reason='" + reason + '\'' +
                ", canAssign=" + canAssign +
                ", canChangeGroup=" + canChangeGroup +
                ", canChangeCountry=" + canChangeCountry +
                '}';
    }

    /**
     * 转案分配 ID - 批量转案
     */
    private String transferIdStr;

    /**
     * 转案类型 1-签约即转案； 2-分配； 3-转组；4-转国家
     */
    private Integer transferSendType;

    /**
     * 操作类型 1-分配文签 2-分配制作文案 3-分配文书
     */
    private Integer operatorType;

    /**
     * 确认状态 1-接受； 2-拒绝
     */
    private Integer confirmStatus;

    /**
     * 结案状态 0-未结案；1-已结案
     */
    private Integer endCaseStatus;

    /**
     * 操作人工号
     */
    private String operatorNo;

    /**
     * 接受人工号
     */
    private String receiveNo;

    public Integer getTransferSendType() {
        return transferSendType;
    }

    public void setTransferSendType(Integer transferSendType) {
        this.transferSendType = transferSendType;
    }

    public Integer getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }

    public Integer getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Integer confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public String getOperatorNo() {
        return operatorNo;
    }

    public void setOperatorNo(String operatorNo) {
        this.operatorNo = operatorNo;
    }

    public String getReceiveNo() {
        return receiveNo;
    }

    public void setReceiveNo(String receiveNo) {
        this.receiveNo = receiveNo;
    }

//    ====================================================================================

    /**
     * transfer_send 表id
     */
    private Integer sendId;
    /**
     * 转案分配人姓名
     */
    private String operatorName;
    /**
     * 转案接收人姓名
     */
    private String receiveName;
    /**
     * 转案时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sendTime;
    /**
     * 转案原因
     */
    private Integer transferReason;
    /**
     * 咨询顾问工号
     */
    private String salesConsultantNo;
    /**
     * 咨询顾问姓名
     */
    private String salesConsultant;
    /**
     * 文签顾问工号
     */
    private String copyOperatorNo;
    /**
     * 文签顾问
     */
    private String copyOperator;
    /**
     * 文案员
     */
    private String copy;
    /**
     * 签证员（业务员）
     */
    private String visaOperator;
    /**
     * 转案接受时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date receiveTime;
    /**
     * 院校名称
     */
    private String collegeName;
    /**
     * 合同编号
     */
    private String contractNo;
    /**
     * 合同类型
     */
    private String contractType;
    /**
     * 国家
     */
    private String nationName;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;

    /**
     * 分支名称
     */
    private String branchName;

    /**
     * 拒绝原因
     */
    private String reason;

//    ========================================================================================================
    /**
     * 分配权限
     */
    private Boolean canAssign;
    /**
     * 转组权限
     */
    private Boolean canChangeGroup;
    /**
     * 转国家权限
     */
    private Boolean canChangeCountry;

    public Integer getSendId() {
        return sendId;
    }

    public void setSendId(Integer sendId) {
        this.sendId = sendId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getTransferReason() {
        return transferReason;
    }

    public void setTransferReason(Integer transferReason) {
        this.transferReason = transferReason;
    }

    public String getSalesConsultantNo() {
        return salesConsultantNo;
    }

    public void setSalesConsultantNo(String salesConsultantNo) {
        this.salesConsultantNo = salesConsultantNo;
    }

    public String getSalesConsultant() {
        return salesConsultant;
    }

    public void setSalesConsultant(String salesConsultant) {
        this.salesConsultant = salesConsultant;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
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

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCopyOperatorNo() {
        return copyOperatorNo;
    }

    public void setCopyOperatorNo(String copyOperatorNo) {
        this.copyOperatorNo = copyOperatorNo;
    }

    public String getCopyOperator() {
        return copyOperator;
    }

    public void setCopyOperator(String copyOperator) {
        this.copyOperator = copyOperator;
    }

    public String getCopy() {
        return copy;
    }

    public void setCopy(String copy) {
        this.copy = copy;
    }

    public String getVisaOperator() {
        return visaOperator;
    }

    public void setVisaOperator(String visaOperator) {
        this.visaOperator = visaOperator;
    }

    public Boolean getCanAssign() {
        return canAssign;
    }

    public void setCanAssign(Boolean canAssign) {
        this.canAssign = canAssign;
    }

    public Boolean getCanChangeGroup() {
        return canChangeGroup;
    }

    public void setCanChangeGroup(Boolean canChangeGroup) {
        this.canChangeGroup = canChangeGroup;
    }

    public Boolean getCanChangeCountry() {
        return canChangeCountry;
    }

    public void setCanChangeCountry(Boolean canChangeCountry) {
        this.canChangeCountry = canChangeCountry;
    }

    public Integer getEndCaseStatus() {
        return endCaseStatus;
    }

    public void setEndCaseStatus(Integer endCaseStatus) {
        this.endCaseStatus = endCaseStatus;
    }

    public String getTransferIdStr() {
        return transferIdStr;
    }

    public void setTransferIdStr(String transferIdStr) {
        this.transferIdStr = transferIdStr;
    }
}
