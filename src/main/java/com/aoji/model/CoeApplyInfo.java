package com.aoji.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;

@Table(name = "coe_apply_info")
public class CoeApplyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public CoeApplyInfo() {
    }

    public CoeApplyInfo(Integer applyId, Integer applyStatus) {
        this.applyId = applyId;
        this.applyStatus = applyStatus;
    }

    /**
     * 内网学号
     */
    @Column(name = "student_no")
    private String studentNo;

    /**
     * 备注
     */
    @Column(name = "apply_comment")
    private String applyComment;

    /**
     * 申请状态：1-申请已提交 2-审核已通过 3-审核不通过
     */
    @Column(name = "apply_status")
    private Integer applyStatus;

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
     * 操作人
     */
    @Column(name = "operator_no")
    private String operatorNo;

    /**
     * 操作人姓名
     */
    private String operatorName;

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    /**
     * 是否通过学校购买保险
     */
    @Column(name = "insurance_buy_status")
    private byte[] insuranceBuyStatus;

    /**
     * Coe到达日期
     */
    @Column(name = "coe_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date coeDate;

    /**
     * 申请id
     */
    @Column(name = "apply_id")
    private Integer applyId;

    /**
     * 上传文件附件地址
     */
    @Column(name = "attachment")
    private String attachment;

    /**
     * 上传申请附件地址
     */
    @Column(name = "apply_attachment")
    private String applyAttachment;

    /**
     * 上传预科附件地址
     */
    @Column(name = "pre_attachment")
    private String preAttachment;

    /**
     * 上传语言附件地址
     */
    @Column(name = "language_attachment")
    private String languageAttachment;

    public String getPreAttachment() {
        return preAttachment;
    }

    public void setPreAttachment(String preAttachment) {
        this.preAttachment = preAttachment;
    }

    public String getLanguageAttachment() {
        return languageAttachment;
    }

    public void setLanguageAttachment(String languageAttachment) {
        this.languageAttachment = languageAttachment;
    }

    /**
     * Coe申请日期
     */
    @Column(name = "apply_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applyDate;

    @Transient
    private String studentName;
    public String getStudentName(){ return studentName; }
    public void setStudentName(String studentName){ this.studentName = studentName; }

    /**
     * 确认操作人
     */
    @Column(name = "confirm_operator_no")
    private String confirmOperatorNo;

    /**
     * 确认操作人姓名
     */
    @Column(name = "confirm_operator_name")
    private String confirmOperatorName;

    /**
     * 确认日期
     */
    @Column(name = "confirm_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date confirmTime;

    @Transient
    private String oaName;

    public String getOaName() {
        return oaName;
    }

    public void setOaName(String oaName) {
        this.oaName = oaName;
    }

    public String getConfirmOperatorNo() {
        return confirmOperatorNo;
    }

    public void setConfirmOperatorNo(String confirmOperatorNo) {
        this.confirmOperatorNo = confirmOperatorNo;
    }

    public String getConfirmOperatorName() {
        return confirmOperatorName;
    }

    public void setConfirmOperatorName(String confirmOperatorName) {
        this.confirmOperatorName = confirmOperatorName;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
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
     * 获取备注
     *
     * @return apply_comment - 备注
     */
    public String getApplyComment() {
        return applyComment;
    }

    /**
     * 设置备注
     *
     * @param applyComment 备注
     */
    public void setApplyComment(String applyComment) {
        this.applyComment = applyComment;
    }

    /**
     * 获取申请状态：1-申请已提交 2-审核已通过 3-审核不通过
     *
     * @return apply_status - 申请状态：1-申请已提交 2-审核已通过 3-审核不通过
     */
    public Integer getApplyStatus() {
        return applyStatus;
    }

    /**
     * 设置申请状态：1-申请已提交 2-审核已通过 3-审核不通过
     *
     * @param applyStatus 申请状态：1-申请已提交 2-审核已通过 3-审核不通过
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
     * 获取是否通过学校购买保险
     *
     * @return insurance_buy_status - 是否通过学校购买保险
     */
    public byte[] getInsuranceBuyStatus() {
        return insuranceBuyStatus;
    }

    /**
     * 设置是否通过学校购买保险
     *
     * @param insuranceBuyStatus 是否通过学校购买保险
     */
    public void setInsuranceBuyStatus(byte[] insuranceBuyStatus) {
        this.insuranceBuyStatus = insuranceBuyStatus;
    }

    /**
     * 获取Coe到达日期
     *
     * @return coe_date - Coe到达日期
     */
    public Date getCoeDate() {
        return coeDate;
    }

    /**
     * 设置Coe到达日期
     *
     * @param coeDate Coe到达日期
     */
    public void setCoeDate(Date coeDate) {
        this.coeDate = coeDate;
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

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getApplyAttachment() {
        return applyAttachment;
    }

    public void setApplyAttachment(String applyAttachment) {
        this.applyAttachment = applyAttachment;
    }

    /**
     * 获取Coe申请日期
     *
     * @return apply_date - Coe申请日期
     */
    public Date getApplyDate() {
        return applyDate;
    }

    /**
     * 设置Coe申请日期
     *
     * @param applyDate Coe申请日期
     */
    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }


    @Override
    public String toString() {
        return "CoeApplyInfo{" +
                "id=" + id +
                ", studentNo='" + studentNo + '\'' +
                ", applyComment='" + applyComment + '\'' +
                ", applyStatus=" + applyStatus +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteStatus=" + deleteStatus +
                ", operatorNo='" + operatorNo + '\'' +
                ", insuranceBuyStatus=" + Arrays.toString(insuranceBuyStatus) +
                ", coeDate=" + coeDate +
                ", applyId=" + applyId +
                '}';
    }
}