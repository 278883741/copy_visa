package com.aoji.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "transfer_send")
public class TransferSend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 关联transfer_info表id
     */
    @Column(name = "transfer_id")
    private Integer transferId;

    /**
     * 操作人工号
     */
    @Column(name = "operator_no")
    private String operatorNo;

    /**
     * 操作人姓名
     */
    @Column(name = "operator_name")
    private String operatorName;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 接受人工号
     */
    @Column(name = "receive_no")
    private String receiveNo;

    /**
     * 接受人姓名
     */
    @Column(name = "receive_name")
    private String receiveName;

    /**
     * 转案类型 1-签约即转案； 2-分配； 3-转组；4-转国家
     */
    @Column(name = "transfer_type")
    private Integer transferType;

    /**
     * 操作类型 1-分配文签 2-分配制作文案 3-分配文书
     */
    @Column(name = "operator_type")
    private Integer operatorType;

    /**
     * 转案原因
     */
    @Column(name = "transfer_reason")
    private Integer transferReason;

    /**
     * 确认状态 1-接受； 2-拒绝
     */
    @Column(name = "confirm_status")
    private Integer confirmStatus;

    /**
     * 可用状态 1-可用 ； 0-不可用
     */
    @Column(name = "enable_status")
    private Boolean enableStatus;

    /**
     * 删除状态 
     */
    @Column(name = "delete_status")
    private Boolean deleteStatus;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取关联transfer_info表id
     *
     * @return transfer_id - 关联transfer_info表id
     */
    public Integer getTransferId() {
        return transferId;
    }

    /**
     * 设置关联transfer_info表id
     *
     * @param transferId 关联transfer_info表id
     */
    public void setTransferId(Integer transferId) {
        this.transferId = transferId;
    }

    /**
     * 获取操作人工号
     *
     * @return operator_no - 操作人工号
     */
    public String getOperatorNo() {
        return operatorNo;
    }

    /**
     * 设置操作人工号
     *
     * @param operatorNo 操作人工号
     */
    public void setOperatorNo(String operatorNo) {
        this.operatorNo = operatorNo;
    }

    /**
     * 获取操作人姓名
     *
     * @return operator_name - 操作人姓名
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * 设置操作人姓名
     *
     * @param operatorName 操作人姓名
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取接受人工号
     *
     * @return receive_no - 接受人工号
     */
    public String getReceiveNo() {
        return receiveNo;
    }

    /**
     * 设置接受人工号
     *
     * @param receiveNo 接受人工号
     */
    public void setReceiveNo(String receiveNo) {
        this.receiveNo = receiveNo;
    }

    /**
     * 获取接受人姓名
     *
     * @return receive_name - 接受人姓名
     */
    public String getReceiveName() {
        return receiveName;
    }

    /**
     * 设置接受人姓名
     *
     * @param receiveName 接受人姓名
     */
    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    /**
     * 获取转案类型 1-签约即转案； 2-分配； 3-转组；4-转国家
     *
     * @return transfer_type - 转案类型 1-签约即转案； 2-分配； 3-转组；4-转国家
     */
    public Integer getTransferType() {
        return transferType;
    }

    /**
     * 设置转案类型 1-签约即转案； 2-分配； 3-转组；4-转国家
     *
     * @param transferType 转案类型 1-签约即转案； 2-分配； 3-转组；4-转国家
     */
    public void setTransferType(Integer transferType) {
        this.transferType = transferType;
    }

    /**
     * 获取操作类型 1-分配文签 2-分配制作文案 3-分配文书
     *
     * @return operator_type - 操作类型 1-分配文签 2-分配制作文案 3-分配文书
     */
    public Integer getOperatorType() {
        return operatorType;
    }

    /**
     * 设置操作类型 1-分配文签 2-分配制作文案 3-分配文书
     *
     * @param operatorType 操作类型 1-分配文签 2-分配制作文案 3-分配文书
     */
    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }

    /**
     * 获取转案原因
     *
     * @return transfer_reason - 转案原因
     */
    public Integer getTransferReason() {
        return transferReason;
    }

    /**
     * 设置转案原因
     *
     * @param transferReason 转案原因
     */
    public void setTransferReason(Integer transferReason) {
        this.transferReason = transferReason;
    }

    /**
     * 获取确认状态 1-接受； 2-拒绝
     *
     * @return confirm_status - 确认状态 1-接受； 2-拒绝
     */
    public Integer getConfirmStatus() {
        return confirmStatus;
    }

    /**
     * 设置确认状态 1-接受； 2-拒绝
     *
     * @param confirmStatus 确认状态 1-接受； 2-拒绝
     */
    public void setConfirmStatus(Integer confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    /**
     * 获取可用状态 1-可用 ； 0-不可用
     *
     * @return enable_status - 可用状态 1-可用 ； 0-不可用
     */
    public Boolean getEnableStatus() {
        return enableStatus;
    }

    /**
     * 设置可用状态 1-可用 ； 0-不可用
     *
     * @param enableStatus 可用状态 1-可用 ； 0-不可用
     */
    public void setEnableStatus(Boolean enableStatus) {
        this.enableStatus = enableStatus;
    }

    /**
     * 获取删除状态 
     *
     * @return delete_status - 删除状态 
     */
    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * 设置删除状态 
     *
     * @param deleteStatus 删除状态 
     */
    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}