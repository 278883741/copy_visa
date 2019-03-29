package com.aoji.model;

import java.util.Date;
import javax.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

@Table(name = "audit_result_info")
public class AuditResultInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 场景id：1-签证申请 2-签证结果
     */
    @Column(name = "case_id")
    private Integer caseId;

    /**
     * 关联待审批表id
     */
    @Column(name = "apply_id")
    private Integer applyId;

    /**
     * 审批状态：1- 审批不通过 2-审批通过
     */
    @Column(name = "apply_status")
    private Integer applyStatus;
    @Transient
    private String applyStatus_string;
    public void setApplyStatus_string(String applyStatus_string) {
        this.applyStatus_string = applyStatus_string;
    }
    public String getApplyStatus_string() {
        return applyStatus_string;
    }

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
     * 关联业务id
     */
    @Column(name = "business_id")
    private Integer businessId;

    /**
     * 是否是最后审批人
     */
    @Transient
    private Integer lastAudit;
    public void setLastAudit(Integer lastAudit) {
        this.lastAudit = lastAudit;
    }
    public Integer getLastAudit() {
        return lastAudit;
    }

    @Transient
    private String studentNo;
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }
    public String getStudentNo() {
        return studentNo;
    }

    @Transient
    private String businessUpdateTime;
    public void setBusinessUpdateTime(String bisinessUpdateTime) {
        this.businessUpdateTime = bisinessUpdateTime;
    }
    public String getBusinessUpdateTime() {
        return businessUpdateTime;
    }

    @Transient
    private String createTimeString;

    public String getCreateTimeString() {
        return createTimeString;
    }

    public void setCreateTimeString(String createTimeString) {
        this.createTimeString = createTimeString;
    }

    /**
     * 审批意见
     */
    private String reason;

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
     * 获取场景id：1-签证申请 2-签证结果
     *
     * @return case_id - 场景id：1-签证申请 2-签证结果
     */
    public Integer getCaseId() {
        return caseId;
    }

    /**
     * 设置场景id：1-签证申请 2-签证结果
     *
     * @param caseId 场景id：1-签证申请 2-签证结果
     */
    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    /**
     * 获取关联待审批表id
     *
     * @return apply_id - 关联待审批表id
     */
    public Integer getApplyId() {
        return applyId;
    }

    /**
     * 设置关联待审批表id
     *
     * @param applyId 关联待审批表id
     */
    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    /**
     * 获取审批状态：1- 审批不通过 2-审批通过
     *
     * @return apply_status - 审批状态：1- 审批不通过 2-审批通过
     */
    public Integer getApplyStatus() {
        return applyStatus;
    }

    /**
     * 设置审批状态：1- 审批不通过 2-审批通过
     *
     * @param applyStatus 审批状态：1- 审批不通过 2-审批通过
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

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
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
     * 获取关联业务id
     *
     * @return business_id - 关联业务id
     */
    public Integer getBusinessId() {
        return businessId;
    }

    /**
     * 设置关联业务id
     *
     * @param businessId 关联业务id
     */
    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    /**
     * 获取审批意见
     *
     * @return reason - 审批意见
     */
    public String getReason() {
        return reason;
    }

    /**
     * 设置审批意见
     *
     * @param reason 审批意见
     */
    public void setReason(String reason) {
        this.reason = reason;
    }
}