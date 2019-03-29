package com.aoji.model;

import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import javax.persistence.*;

@Table(name = "visa_result_info")
public class VisaResultInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 学生学号
     */
    @Column(name = "student_no")
    private String studentNo;

    /**
     * 签证申请id
     */
    @Column(name = "visa_id")
    private Integer visaId;

    /**
     * 签证结果：1-获签 0-拒签 2-撤签
     */
    @Column(name = "visa_result")
    private Integer visaResult;
    @Transient
    private String visaResult_string;
    public void setVisaResult_string(String visaResult_string) {
        this.visaResult_string = visaResult_string;
    }
    public String getVisaResult_string() {
        return visaResult_string;
    }

    /**
     * 获得日期
     */
    @Column(name = "result_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date resultTime;

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 删除时间/失效时间
     */
    @Column(name = "delete_time")
    private Date deleteTime;

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
     * 签证结果状态：1-已递交 2-审核中 2-审核通过 4-审核不通过
     */
    @Column(name = "visa_status")
    private Integer visaStatus;
    @Transient
    private String visaStatus_String;
    public void setVisaStatus_String(String visaStatus_String) {
        this.visaStatus_String = visaStatus_String;
    }
    public String getVisaStatus_String() {
        return visaStatus_String;
    }

    /**
     * 签证结果附件
     */
    private String attachment;

    /**
     * 签证到期日
     */
    @Column(name = "due_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dueDate;

    /**
     * 拒签分析
     */
    @Column(name = "reject_reason")
    private String rejectReason;

    /**
     * 护照号
     */
    @Column(name = "passport_no")
    private String passportNo;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 操作人姓名
     */
    @Column(name="operator_name")
    private String operatorName;
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

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
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
     * 获取学生学号
     *
     * @return student_no - 学生学号
     */
    public String getStudentNo() {
        return studentNo;
    }

    /**
     * 设置学生学号
     *
     * @param studentNo 学生学号
     */
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    /**
     * 获取签证申请id
     *
     * @return visa_id - 签证申请id
     */
    public Integer getVisaId() {
        return visaId;
    }

    /**
     * 设置签证申请id
     *
     * @param visaId 签证申请id
     */
    public void setVisaId(Integer visaId) {
        this.visaId = visaId;
    }

    /**
     * 获取签证结果：1-获签 0-拒签 2-撤签
     *
     * @return visa_result - 签证结果：1-获签 0-拒签 2-撤签
     */
    public Integer getVisaResult() {
        return visaResult;
    }

    /**
     * 设置签证结果：1-获签 0-拒签 2-撤签
     *
     * @param visaResult 签证结果：1-获签 0-拒签 2-撤签
     */
    public void setVisaResult(Integer visaResult) {
        this.visaResult = visaResult;
    }

    /**
     * 获取获得日期
     *
     * @return result_time - 获得日期
     */
    public Date getResultTime() {
        return resultTime;
    }

    /**
     * 设置获得日期
     *
     * @param resultTime 获得日期
     */
    public void setResultTime(Date resultTime) {
        this.resultTime = resultTime;
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
     * 获取删除时间/失效时间
     *
     * @return delete_time - 删除时间/失效时间
     */
    public Date getDeleteTime() {
        return deleteTime;
    }

    /**
     * 设置删除时间/失效时间
     *
     * @param deleteTime 删除时间/失效时间
     */
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
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
     * 获取签证结果状态：1-已递交 2-审核中 2-审核通过 4-审核不通过
     *
     * @return visa_status - 签证结果状态：1-已递交 2-审核中 2-审核通过 4-审核不通过
     */
    public Integer getVisaStatus() {
        return visaStatus;
    }

    /**
     * 设置签证结果状态：1-已递交 2-审核中 2-审核通过 4-审核不通过
     *
     * @param visaStatus 签证结果状态：1-已递交 2-审核中 2-审核通过 4-审核不通过
     */
    public void setVisaStatus(Integer visaStatus) {
        this.visaStatus = visaStatus;
    }

    /**
     * 获取签证结果附件
     *
     * @return attachment - 签证结果附件
     */
    public String getAttachment() {
        return attachment;
    }

    /**
     * 设置签证结果附件
     *
     * @param attachment 签证结果附件
     */
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    /**
     * 获取签证到期日
     *
     * @return due_date - 签证到期日
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * 设置签证到期日
     *
     * @param dueDate 签证到期日
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * 获取拒签分析
     *
     * @return reject_reason - 拒签分析
     */
    public String getRejectReason() {
        return rejectReason;
    }

    /**
     * 设置拒签分析
     *
     * @param rejectReason 拒签分析
     */
    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    /**
     * 获取护照号
     *
     * @return passport_no - 护照号
     */
    public String getPassportNo() {
        return passportNo;
    }

    /**
     * 设置护照号
     *
     * @param passportNo 护照号
     */
    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}