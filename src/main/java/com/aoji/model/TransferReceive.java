package com.aoji.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "transfer_receive")
public class TransferReceive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 关联transfer_send表id
     */
    @Column(name = "send_id")
    private Integer sendId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

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
     * 确认状态
     */
    @Column(name = "confirm_status")
    private Integer confirmStatus;

    /**
     * 拒绝原因
     */
    private String reason;

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
     * 获取关联transfer_send表id
     *
     * @return send_id - 关联transfer_send表id
     */
    public Integer getSendId() {
        return sendId;
    }

    /**
     * 设置关联transfer_send表id
     *
     * @param sendId 关联transfer_send表id
     */
    public void setSendId(Integer sendId) {
        this.sendId = sendId;
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
     * 获取确认状态
     *
     * @return confirm_status - 确认状态
     */
    public Integer getConfirmStatus() {
        return confirmStatus;
    }

    /**
     * 设置确认状态
     *
     * @param confirmStatus 确认状态
     */
    public void setConfirmStatus(Integer confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    /**
     * 获取拒绝原因
     *
     * @return reason - 拒绝原因
     */
    public String getReason() {
        return reason;
    }

    /**
     * 设置拒绝原因
     *
     * @param reason 拒绝原因
     */
    public void setReason(String reason) {
        this.reason = reason;
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