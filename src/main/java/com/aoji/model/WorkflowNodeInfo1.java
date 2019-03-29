package com.aoji.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "workflow_node1_info")
public class WorkflowNodeInfo1 {

    public WorkflowNodeInfo1() {

    }

    /**
     * 根据流程ID 和删除状态查询为删除的审批人
     *
     * @param flowId
     * @param deleteStatus
     */
    public WorkflowNodeInfo1(Integer flowId, Boolean deleteStatus) {
        this.flowId = flowId;
        this.deleteStatus = deleteStatus;
    }

    /**
     * 根据流程ID 和删除状态查询为删除的审批人
     *
     * @param flowId
     */
    public WorkflowNodeInfo1(Integer flowId) {
        this.flowId = flowId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 流程id
     */
    @Column(name = "flow_id")
    private Integer flowId;

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
     * 审批用户id
     */
    @Column(name = "oaid")
    private String oaId;

    /**
     * 是否是最后审批节点：1-是 0-否
     */
    @Column(name = "last_status")
    private Integer lastStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getOperatorNo() {
        return operatorNo;
    }

    public void setOperatorNo(String operatorNo) {
        this.operatorNo = operatorNo;
    }

    public Integer getAuditSequence() {
        return auditSequence;
    }

    public void setAuditSequence(Integer auditSequence) {
        this.auditSequence = auditSequence;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOaId() {
        return oaId;
    }

    public void setOaId(String oaId) {
        this.oaId = oaId;
    }

    public Integer getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(Integer lastStatus) {
        this.lastStatus = lastStatus;
    }
}