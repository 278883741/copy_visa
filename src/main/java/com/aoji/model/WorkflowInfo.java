package com.aoji.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "workflow_info")
public class WorkflowInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
     * 留学国家组
     */
    @Column(name = "nation_id")
    private Integer nationId;

    /**
     * 操作人姓名
     */
    @Column(name = "operator_name")
    private String operatorName;

    /**
     * 审核周期
     */
    @Column(name = "audit_cycle")
    private Integer auditCycle;

    /**
     * 场景id--1-签证申请 2-签证结果 3-获签信息 4-院校申请 5-院校申请结果 6-coe审核
     */
    @Column(name = "case_id")
    private Integer caseId;




    @Transient
    private Integer userGroup;
    public void setUserGroup(Integer userGroup) {
        this.userGroup = userGroup;
    }
    public Integer getUserGroup() {
        return userGroup;
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
     * 获取留学国家组
     *
     * @return nation_id - 留学国家组
     */
    public Integer getNationId() {
        return nationId;
    }

    /**
     * 设置留学国家组
     *
     * @param nationId 留学国家组
     */
    public void setNationId(Integer nationId) {
        this.nationId = nationId;
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
     * 获取审核周期
     *
     * @return audit_cycle - 审核周期
     */
    public Integer getAuditCycle() {
        return auditCycle;
    }

    /**
     * 设置审核周期
     *
     * @param auditCycle 审核周期
     */
    public void setAuditCycle(Integer auditCycle) {
        this.auditCycle = auditCycle;
    }

    /**
     * 获取场景id--1-签证申请 2-签证结果 3-获签信息 4-院校申请 5-院校申请结果 6-coe审核
     *
     * @return case_id - 场景id--1-签证申请 2-签证结果 3-获签信息 4-院校申请 5-院校申请结果 6-coe审核
     */
    public Integer getCaseId() {
        return caseId;
    }

    /**
     * 设置场景id--1-签证申请 2-签证结果 3-获签信息 4-院校申请 5-院校申请结果 6-coe审核
     *
     * @param caseId 场景id--1-签证申请 2-签证结果 3-获签信息 4-院校申请 5-院校申请结果 6-coe审核
     */
    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }
}