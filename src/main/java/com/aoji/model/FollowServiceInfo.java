package com.aoji.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "follow_service_info")
public class FollowServiceInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 申请id
     */
    private Integer applyId;

    /**
     * 内网学号
     */
    @Column(name = "student_no")
    private String studentNo;

    /**
     * 申请日期
     */
    @Column(name = "apply_date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date applyDate;

    /**
     * 备注
     */
    @Column(name = "apply_content")
    private String applyContent;

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
    private String operatorName;

    /**
     * 申请状态：1-已提交 2-已录取 3-已拒绝
     */
    @Column(name = "apply_status")
    private Integer applyStatus;

    /**
     * 合作机构
     */
    private Integer agency;

    /**
     * 机构名称
     */
    @Transient
    private String agencyName;

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    /**
     * 服务类型：1-接机 2-保险 3-监护 4-主课住宿 5-语言住宿
     */
    @Column(name = "visit_type")
    private Integer visitType;

    /**
     * 购买保险途径：1-学校 2-合作机构
     */
    @Column(name = "apply_way")
    private Integer applyWay;

    /**
     * 保险类型:1-OSHC 2-OVHC
     */
    @Column(name = "insurance_type")
    private Integer insuranceType;

    /**
     * 是否已付后续费用
     */
    @Column(name = "payment_status")
    private Boolean paymentStatus;

    /**
     * 保险开始时间
     */
    @Column(name = "safe_start_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date safeStartTime;


    /**
     * 保险结束时间
     */
    @Column(name = "safe_end_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date safeEndTime;

    /**
     * 申请表附件
     */
    private String attachment;

    /**
     * 费用 （澳元AUD）
     */
    private BigDecimal fee;

    /**
     * 费用单位
     */
    private Integer feeUnit;

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public Integer getFeeUnit() {
        return feeUnit;
    }

    public void setFeeUnit(Integer feeUnit) {
        this.feeUnit = feeUnit;
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
     * 获取申请日期
     *
     * @return apply_date - 申请日期
     */
    public Date getApplyDate() {
        return applyDate;
    }

    /**
     * 设置申请日期
     *
     * @param applyDate 申请日期
     */
    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    /**
     * 获取备注
     *
     * @return apply_content - 备注
     */
    public String getApplyContent() {
        return applyContent;
    }

    /**
     * 设置备注
     *
     * @param applyContent 备注
     */
    public void setApplyContent(String applyContent) {
        this.applyContent = applyContent;
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
     * 获取申请状态：1-已提交 2-已录取 3-已拒绝
     *
     * @return apply_status - 申请状态：1-已提交 2-已录取 3-已拒绝
     */
    public Integer getApplyStatus() {
        return applyStatus;
    }

    /**
     * 设置申请状态：1-已提交 2-已录取 3-已拒绝
     *
     * @param applyStatus 申请状态：1-已提交 2-已录取 3-已拒绝
     */
    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    /**
     * 获取合作机构
     *
     * @return agency - 合作机构
     */
    public Integer getAgency() {
        return agency;
    }

    /**
     * 设置合作机构
     *
     * @param agency 合作机构
     */
    public void setAgency(Integer agency) {
        this.agency = agency;
    }

    public Integer getVisitType() {
        return visitType;
    }

    public void setVisitType(Integer visitType) {
        this.visitType = visitType;
    }

    /**
     * 获取购买保险途径：1-学校 2-合作机构
     *
     * @return apply_way - 购买保险途径：1-学校 2-合作机构
     */
    public Integer getApplyWay() {
        return applyWay;
    }

    /**
     * 设置购买保险途径：1-学校 2-合作机构
     *
     * @param applyWay 购买保险途径：1-学校 2-合作机构
     */
    public void setApplyWay(Integer applyWay) {
        this.applyWay = applyWay;
    }

    /**
     * 获取保险类型:1-OSHC 2-OVHC
     *
     * @return insurance_type - 保险类型:1-OSHC 2-OVHC
     */
    public Integer getInsuranceType() {
        return insuranceType;
    }

    /**
     * 设置保险类型:1-OSHC 2-OVHC
     *
     * @param insuranceType 保险类型:1-OSHC 2-OVHC
     */
    public void setInsuranceType(Integer insuranceType) {
        this.insuranceType = insuranceType;
    }

    /**
     * 获取是否已付后续费用
     *
     * @return payment_status - 是否已付后续费用
     */
    public Boolean getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * 设置是否已付后续费用
     *
     * @param paymentStatus 是否已付后续费用
     */
    public void setPaymentStatus(Boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getSafeStartTime() {
        return safeStartTime;
    }

    public void setSafeStartTime(Date safeStartTime) {
        this.safeStartTime = safeStartTime;
    }

    public Date getSafeEndTime() {
        return safeEndTime;
    }

    public void setSafeEndTime(Date safeEndTime) {
        this.safeEndTime = safeEndTime;
    }

    /**
     * 获取申请表附件
     *
     * @return attachment - 申请表附件
     */
    public String getAttachment() {
        return attachment;
    }

    /**
     * 设置申请表附件
     *
     * @param attachment 申请表附件
     */
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
}