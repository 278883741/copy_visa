package com.aoji.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Table(name = "student_delay_info")
public class StudentDelayInfo {
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
     * 操作人
     */
    @Column(name = "operator_no")
    private String operatorNo;

    /**
     * 学生确认截图
     */
    private String attachment;

    /**
     * 下次联系时间
     */
    @Column(name = "contact_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date contactDate;

    /**
     * 停缓办原因
     */
    private String reason;

    /**
     * 停缓办类型
     */
    private String delayType;

    /**
     * 停缓办备注
     */
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 学生姓名
     */
    @Transient
    private String studentName;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Column(name = "audit_status")
    private Integer auditStatus;

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
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
     * 获取下次联系时间
     *
     * @return contact_date - 下次联系时间
     */
    public Date getContactDate() {
        return contactDate;
    }

    /**
     * 设置下次联系时间
     *
     * @param contactDate 下次联系时间
     */
    public void setContactDate(Date contactDate) {
        this.contactDate = contactDate;
    }

    /**
     * 获取停缓办原因
     *
     * @return reason - 停缓办原因
     */
    public String getReason() {
        return reason;
    }

    /**
     * 设置停缓办原因
     *
     * @param reason 停缓办原因
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    @Transient
    private String operatorName;
    public String getOperatorName(){ return operatorName; }
    public void setOperatorName(String operatorName){ this.operatorName = operatorName; }

    @Transient
    private String createTimeString;
    public String getCreateTimeString(){ return createTimeString; }
    public void setCreateTimeString(String createTimeString){ this.createTimeString = createTimeString; }

    @Transient
    private String cancelTimeString;
    public String getCancelTimeString(){ return cancelTimeString; }
    public void setCancelTimeString(String cancelTimeString){ this.cancelTimeString = cancelTimeString; }

    public String getDelayType() {
        return delayType;
    }

    public void setDelayType(String delayType) {
        this.delayType = delayType;
    }
}