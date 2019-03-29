package com.aoji.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "student_settle_info")
public class StudentSettleInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 内网学号
     */
    @Column(name = "student_no")
    private String studentNo;

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
     * 操作人名称
     */
    @Column(name = "operator_name")
    private String operatorName;

    /**
     * 学生确认截图
     */
    private String attachment;

    /**
     * 结案原因：1-单文书结案 2-单申请结案 3-其他 4-自动结案 5-退费结案
     */
    private Integer reason;

    /**
     * (旧系统)结案原因：
     */
    @Column(name = "end_case_reason")
    private  String  endCaseReason;

    /**
     * 审核状态：1-已递交 2-审核中 3-审核通过 4-审核不通过
     */
    @Column(name = "audit_status")
    private Integer auditStatus;

    /**
     * 套磁信
     */
    private String closeLetter;

    public String getCloseLetter() {
        return closeLetter;
    }

    public void setCloseLetter(String closeLetter) {
        this.closeLetter = closeLetter;
    }

    @Transient
    private String studentName;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Transient
    private String salesConsultant;

    @Transient
    private String copyOperator;

    @Transient
    private Date signDate;

    @Transient
    private Integer nationId;
    public void setNationId(Integer nationId) {
        this.nationId = nationId;
    }
    public Integer getNationId() {
        return nationId;
    }

    @Transient
    private String nationName;

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public String getSalesConsultant() {
        return salesConsultant;
    }

    public void setSalesConsultant(String salesConsultant) {
        this.salesConsultant = salesConsultant;
    }

    public String getCopyOperator() {
        return copyOperator;
    }

    public void setCopyOperator(String copyOperator) {
        this.copyOperator = copyOperator;
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
     * 获取操作人名称
     *
     * @return operator_name - 操作人名称
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * 设置操作人名称
     *
     * @param operatorName 操作人名称
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    /**
     * 获取学生确认截图
     *
     * @return attachment - 学生确认截图
     */
    public String getAttachment() {
        return attachment;
    }

    /**
     * 设置学生确认截图
     *
     * @param attachment 学生确认截图
     */
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    /**
     * 获取结案原因：1-单文书结案 2-单申请结案 3-其他 4-自动结案 5-退费结案
     *
     * @return reason - 结案原因：1-单文书结案 2-单申请结案 3-其他 4-自动结案 5-退费结案
     */
    public Integer getReason() {
        return reason;
    }

    /**
     * 设置结案原因：1-单文书结案 2-单申请结案 3-其他 4-自动结案 5-退费结案
     *
     * @param reason 结案原因：1-单文书结案 2-单申请结案 3-其他 4-自动结案 5-退费结案
     */
    public void setReason(Integer reason) {
        this.reason = reason;
    }

    /**
     * 获取审核状态：1-已递交 2-审核中 3-审核通过 4-审核不通过
     *
     * @return audit_status - 审核状态：1-已递交 2-审核中 3-审核通过 4-审核不通过
     */
    public Integer getAuditStatus() {
        return auditStatus;
    }

    /**
     * 设置审核状态：1-已递交 2-审核中 3-审核通过 4-审核不通过
     *
     * @param auditStatus 审核状态：1-已递交 2-审核中 3-审核通过 4-审核不通过
     */
    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    /**
     * 旧系统结案原因
     * @return
     */
    public String getEndCaseReason() {
        return endCaseReason;
    }

    /**
     * 旧系统结案原因
     * @param endCaseReason
     */
    public void setEndCaseReason(String endCaseReason) {
        this.endCaseReason = endCaseReason;
    }
}