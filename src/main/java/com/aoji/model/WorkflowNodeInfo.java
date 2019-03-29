package com.aoji.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "workflow_node_info")
public class WorkflowNodeInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 流程id
     */
    @Column(name = "flow_id")
    private String flowId;

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 删除状态0为未删除/可用，1为已删除/不可用
     */
    @Column(name = "delete_status")
    private Boolean deleteStatus;

    /**
     * 操作人
     */
    @Column(name = "operator_no")
    private String operatorNo;

    /**
     * 审批顺序
     */
    @Column(name = "audit_sequence")
    private Integer auditSequence;


    /**
     * 操作人姓名
     */
    @Column(name = "operator_name")
    private String operatorName;

    /**
     * 审批角色
     */
    private Integer role;

    /**
     * 是否是最后审批节点：1-是 0-否
     */
    @Column(name = "last_status")
    private Integer lastStatus;

    @Transient
    private String auditUserNo;
    public void setAuditUserNo(String auditUserNo) {
        this.auditUserNo = auditUserNo;
    }
    public String getAuditUserNo() {
        return auditUserNo;
    }

    @Transient
    private String auditUserName;

    public String getAuditUserName() {
        return auditUserName;
    }

    public void setAuditUserName(String auditUser) {
        this.auditUserName = auditUser;
    }

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

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    /**
     * 获取创建日期
     *
     * @return create_time - 创建日期
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建日期
     *
     * @param createTime 创建日期
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取删除状态0为未删除/可用，1为已删除/不可用
     *
     * @return delete_status - 删除状态0为未删除/可用，1为已删除/不可用
     */
    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * 设置删除状态0为未删除/可用，1为已删除/不可用
     *
     * @param deleteStatus 删除状态0为未删除/可用，1为已删除/不可用
     */
    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    /**
     * 获取操作人
     *
     * @return operator_no - 操作人
     */
    public String getOperatorNo() {
        return operatorNo;
    }

    /**
     * 设置操作人
     *
     * @param operatorNo 操作人
     */
    public void setOperatorNo(String operatorNo) {
        this.operatorNo = operatorNo;
    }

    /**
     * 获取审批顺序
     *
     * @return audit_sequence - 审批顺序
     */
    public Integer getAuditSequence() {
        return auditSequence;
    }

    /**
     * 设置审批顺序
     *
     * @param auditSequence 审批顺序
     */
    public void setAuditSequence(Integer auditSequence) {
        this.auditSequence = auditSequence;
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
     * 获取审批角色
     *
     * @return role - 审批角色
     */
    public Integer getRole() {
        return role;
    }

    /**
     * 设置审批角色
     *
     * @param role 审批角色
     */
    public void setRole(Integer role) {
        this.role = role;
    }

    /**
     * 获取是否是最后审批节点：1-是 0-否
     *
     * @return last_status - 是否是最后审批节点：1-是 0-否
     */
    public Integer getLastStatus() {
        return lastStatus;
    }

    /**
     * 设置是否是最后审批节点：1-是 0-否
     *
     * @param lastStatus 是否是最后审批节点：1-是 0-否
     */
    public void setLastStatus(Integer lastStatus) {
        this.lastStatus = lastStatus;
    }
}