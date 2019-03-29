package com.aoji.model;

import com.aoji.model.res.School;
import io.swagger.models.auth.In;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "visa_record_info")
public class VisaRecordInfo {
    @Column
    private Integer studentNation;
    public void setStudentNation(Integer studentNation) {
        this.studentNation = studentNation;
    }
    public Integer getStudentNation() {
        return studentNation;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 申请id
     */
    @Column(name = "apply_id")
    private Integer applyId;

    /**
     * 获得日期
     */
    @Column(name = "result_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date resultTime;
    public void setResultTime(Date resultTime) {
        this.resultTime = resultTime;
    }

    public Date getResultTime() {
        return resultTime;
    }

    /**
     * 内网学号
     */
    @Column(name = "student_no")
    private String studentNo;

    /**
     * 备注
     */
    @Column(name = "visa_comment")
    private String visaComment;

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
     * 操作人姓名
     */
    @Column(name = "operator_name")
    private String operatorName;

    /**
     * EVUS登记完成日期
     */
    @Column(name = "evus_complete_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date evusCompleteDate;

    /**
     * EVUS附件
     */
    @Column(name = "evus_attachment")
    private String evusAttachment;

    /**
     * 获签信息状态：1-已递交审核 2-审核中 3-审核通过 4-审核不通过
     */
    @Column(name = "audit_status")
    private Integer auditStatus;

    /**
     * 原数据关联id,关联visa_result_record
     */
    @Column(name = "FGV_ID")
    private Integer fgvId;

    /**
     * 学生姓名
     */
    @Transient
    private String studentName;

    /**
     * 获签后是否插入佣金系统标识(1-已插入,0-未插入)
     */
    @Column(name = "send_commission_status")
    private Boolean sendCommissionStatus;

    public Boolean getSendCommissionStatus() {
        return sendCommissionStatus;
    }

    public void setSendCommissionStatus(Boolean sendCommissionStatus) {
        this.sendCommissionStatus = sendCommissionStatus;
    }

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
     * 获取申请id
     *
     * @return apply_id - 申请id
     */
    public Integer getApplyId() {
        return applyId;
    }

    /**
     * 设置申请id
     *
     * @param applyId 申请id
     */
    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
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
     * 获取备注
     *
     * @return visa_comment - 备注
     */
    public String getVisaComment() {
        return visaComment;
    }

    /**
     * 设置备注
     *
     * @param visaComment 备注
     */
    public void setVisaComment(String visaComment) {
        this.visaComment = visaComment;
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
     * 获取EVUS登记完成日期
     *
     * @return evus_complete_date - EVUS登记完成日期
     */
    public Date getEvusCompleteDate() {
        return evusCompleteDate;
    }

    /**
     * 设置EVUS登记完成日期
     *
     * @param evusCompleteDate EVUS登记完成日期
     */
    public void setEvusCompleteDate(Date evusCompleteDate) {
        this.evusCompleteDate = evusCompleteDate;
    }

    /**
     * 获取EVUS附件
     *
     * @return evus_attachment - EVUS附件
     */
    public String getEvusAttachment() {
        return evusAttachment;
    }

    /**
     * 设置EVUS附件
     *
     * @param evusAttachment EVUS附件
     */
    public void setEvusAttachment(String evusAttachment) {
        this.evusAttachment = evusAttachment;
    }

    /**
     * 获取获签信息状态：1-已递交审核 2-审核中 3-审核通过 4-审核不通过
     *
     * @return audit_status - 获签信息状态：1-已递交审核 2-审核中 3-审核通过 4-审核不通过
     */
    public Integer getAuditStatus() {
        return auditStatus;
    }

    /**
     * 设置获签信息状态：1-已递交审核 2-审核中 3-审核通过 4-审核不通过
     *
     * @param auditStatus 获签信息状态：1-已递交审核 2-审核中 3-审核通过 4-审核不通过
     */
    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    /**
     * 获取原数据关联id,关联visa_result_record
     *
     * @return FGV_ID - 原数据关联id,关联visa_result_record
     */
    public Integer getFgvId() {
        return fgvId;
    }

    /**
     * 设置原数据关联id,关联visa_result_record
     *
     * @param fgvId 原数据关联id,关联visa_result_record
     */
    public void setFgvId(Integer fgvId) {
        this.fgvId = fgvId;
    }
}