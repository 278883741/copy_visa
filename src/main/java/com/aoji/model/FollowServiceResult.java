package com.aoji.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "follow_service_result")
public class FollowServiceResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 申请id,关联后续申请表id
     */
    @Column(name = "apply_id")
    private Integer applyId;

    /**
     * 回复途径：1-传真 2-电话 3-电子版 4-扫描件 5-原件
     */
    @Column(name = "reply_way")
    private Integer replyWay;

    /**
     * 结果类型：1-录取 2-拒绝 
     */
    @Column(name = "result_type")
    private Integer resultType;

    /**
     * 回复日期，即申请结果到达日期
     */
    @Column(name = "result_date")
    private Date resultDate;

    /**
     * 学生确认offer日期
     */
    @Column(name = "student_reply_date")
    private Date studentReplyDate;

    /**
     * 学校确认offer接受日期
     */
    @Column(name = "school_reply_date")
    private Date schoolReplyDate;

    /**
     * offer附件地址
     */
    @Column(name = "offer_attachment")
    private String offerAttachment;

    /**
     * 回复截止日期
     */
    @Column(name = "reply_deadline")
    private Date replyDeadline;

    /**
     * 拒绝原因
     */
    @Column(name = "reply_reason")
    private String replyReason;

    /**
     * 备注
     */
    @Column(name = "result_comment")
    private String resultComment;

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 删除时间/失效时间
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
     * Argue日期
     */
    @Column(name = "argue_date")
    private Date argueDate;

    /**
     * offer结果状态：1-结果已递交 2-结果审核通过 3-结果审核不通过
     */
    @Column(name = "audit_status")
    private Integer auditStatus;

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
     * 获取申请id,关联后续申请表id
     *
     * @return apply_id - 申请id,关联后续申请表id
     */
    public Integer getApplyId() {
        return applyId;
    }

    /**
     * 设置申请id,关联后续申请表id
     *
     * @param applyId 申请id,关联后续申请表id
     */
    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    /**
     * 获取回复途径：1-传真 2-电话 3-电子版 4-扫描件 5-原件
     *
     * @return reply_way - 回复途径：1-传真 2-电话 3-电子版 4-扫描件 5-原件
     */
    public Integer getReplyWay() {
        return replyWay;
    }

    /**
     * 设置回复途径：1-传真 2-电话 3-电子版 4-扫描件 5-原件
     *
     * @param replyWay 回复途径：1-传真 2-电话 3-电子版 4-扫描件 5-原件
     */
    public void setReplyWay(Integer replyWay) {
        this.replyWay = replyWay;
    }

    /**
     * 获取结果类型：1-录取 2-拒绝 
     *
     * @return result_type - 结果类型：1-录取 2-拒绝 
     */
    public Integer getResultType() {
        return resultType;
    }

    /**
     * 设置结果类型：1-录取 2-拒绝 
     *
     * @param resultType 结果类型：1-录取 2-拒绝 
     */
    public void setResultType(Integer resultType) {
        this.resultType = resultType;
    }

    /**
     * 获取回复日期，即申请结果到达日期
     *
     * @return result_date - 回复日期，即申请结果到达日期
     */
    public Date getResultDate() {
        return resultDate;
    }

    /**
     * 设置回复日期，即申请结果到达日期
     *
     * @param resultDate 回复日期，即申请结果到达日期
     */
    public void setResultDate(Date resultDate) {
        this.resultDate = resultDate;
    }

    /**
     * 获取学生确认offer日期
     *
     * @return student_reply_date - 学生确认offer日期
     */
    public Date getStudentReplyDate() {
        return studentReplyDate;
    }

    /**
     * 设置学生确认offer日期
     *
     * @param studentReplyDate 学生确认offer日期
     */
    public void setStudentReplyDate(Date studentReplyDate) {
        this.studentReplyDate = studentReplyDate;
    }

    /**
     * 获取学校确认offer接受日期
     *
     * @return school_reply_date - 学校确认offer接受日期
     */
    public Date getSchoolReplyDate() {
        return schoolReplyDate;
    }

    /**
     * 设置学校确认offer接受日期
     *
     * @param schoolReplyDate 学校确认offer接受日期
     */
    public void setSchoolReplyDate(Date schoolReplyDate) {
        this.schoolReplyDate = schoolReplyDate;
    }

    /**
     * 获取offer附件地址
     *
     * @return offer_attachment - offer附件地址
     */
    public String getOfferAttachment() {
        return offerAttachment;
    }

    /**
     * 设置offer附件地址
     *
     * @param offerAttachment offer附件地址
     */
    public void setOfferAttachment(String offerAttachment) {
        this.offerAttachment = offerAttachment;
    }

    /**
     * 获取回复截止日期
     *
     * @return reply_deadline - 回复截止日期
     */
    public Date getReplyDeadline() {
        return replyDeadline;
    }

    /**
     * 设置回复截止日期
     *
     * @param replyDeadline 回复截止日期
     */
    public void setReplyDeadline(Date replyDeadline) {
        this.replyDeadline = replyDeadline;
    }

    /**
     * 获取拒绝原因
     *
     * @return reply_reason - 拒绝原因
     */
    public String getReplyReason() {
        return replyReason;
    }

    /**
     * 设置拒绝原因
     *
     * @param replyReason 拒绝原因
     */
    public void setReplyReason(String replyReason) {
        this.replyReason = replyReason;
    }

    /**
     * 获取备注
     *
     * @return result_comment - 备注
     */
    public String getResultComment() {
        return resultComment;
    }

    /**
     * 设置备注
     *
     * @param resultComment 备注
     */
    public void setResultComment(String resultComment) {
        this.resultComment = resultComment;
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
     * @return update_time - 删除时间/失效时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置删除时间/失效时间
     *
     * @param updateTime 删除时间/失效时间
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
     * 获取Argue日期
     *
     * @return argue_date - Argue日期
     */
    public Date getArgueDate() {
        return argueDate;
    }

    /**
     * 设置Argue日期
     *
     * @param argueDate Argue日期
     */
    public void setArgueDate(Date argueDate) {
        this.argueDate = argueDate;
    }

    /**
     * 获取offer结果状态：1-结果已递交 2-结果审核通过 3-结果审核不通过
     *
     * @return audit_status - offer结果状态：1-结果已递交 2-结果审核通过 3-结果审核不通过
     */
    public Integer getAuditStatus() {
        return auditStatus;
    }

    /**
     * 设置offer结果状态：1-结果已递交 2-结果审核通过 3-结果审核不通过
     *
     * @param auditStatus offer结果状态：1-结果已递交 2-结果审核通过 3-结果审核不通过
     */
    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }
}