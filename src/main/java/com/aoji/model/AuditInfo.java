package com.aoji.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "audit_info")
public class AuditInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 场景id：1-签证申请 2-签证结果
     */
    @Column(name = "case_id")
    private Integer caseId;

    /**
     * 提交审批人
     */
    @Column(name = "commit_audit")
    private String commitAudit;

    /**
     * 待审批人
     */
    @Column(name = "wait_audit")
    private Integer waitAudit;

    /**
     * 内网学号
     */
    @Column(name = "student_no")
    private String studentNo;

    /**
     * 关联业务id
     */
    @Column(name = "apply_id")
    private Integer applyId;

    /**
     * 审批状态：1-待审批 2-审批不通过 3-审批通过
     */
    @Column(name = "apply_status")
    private Integer applyStatus;

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 删除状态0为未删除/可用，1为已删除/不可用
     */
    @Column(name = "delete_status")
    private Boolean deleteStatus;

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
     * 获取场景id
     *
     * @return case_id - 场景id
     */
    public Integer getCaseId() {
        return caseId;
    }

    /**
     * 设置场景id
     *
     * @param caseId 场景id
     */
    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    /**
     * 获取提交审批人
     *
     * @return commit_audit - 提交审批人
     */
    public String getCommitAudit() {
        return commitAudit;
    }

    /**
     * 设置提交审批人
     *
     * @param commitAudit 提交审批人
     */
    public void setCommitAudit(String commitAudit) {
        this.commitAudit = commitAudit;
    }

    /**
     * 获取待审批人
     *
     * @return wait_audit - 待审批人
     */
    public Integer getWaitAudit() {
        return waitAudit;
    }

    /**
     * 设置待审批人
     *
     * @param waitAudit 待审批人
     */
    public void setWaitAudit(Integer waitAudit) {
        this.waitAudit = waitAudit;
    }

    /**
     * 获取内网学号
     *
     * @return student_no - 内网学号
     */
    public String getStudentNo() {
        return studentNo;
    }

    /**
     * 设置内网学号
     *
     * @param studentNo 内网学号
     */
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    /**
     * 获取关联业务id
     *
     * @return apply_id - 关联业务id
     */
    public Integer getApplyId() {
        return applyId;
    }

    /**
     * 设置关联业务id
     *
     * @param applyId 关联业务id
     */
    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    /**
     * 获取审批状态：1-待审批 2-审批不通过 3-审批通过
     *
     * @return apply_status - 审批状态：1-待审批 2-审批不通过 3-审批通过
     */
    public Integer getApplyStatus() {
        return applyStatus;
    }

    /**
     * 设置审批状态：1-待审批 2-审批不通过 3-审批通过
     *
     * @param applyStatus 审批状态：1-待审批 2-审批不通过 3-审批通过
     */
    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
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

    public Date getUpdateTime() {
        return updateTime;
    }

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
}