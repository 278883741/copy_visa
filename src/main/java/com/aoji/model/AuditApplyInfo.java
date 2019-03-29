package com.aoji.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "audit_apply_info")
public class AuditApplyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 场景id：1-签证申请 2-签证结果 3-获签信息 4-院校申请 5-院校申请结果 6-寄出绩效 8-后续申请结果
     */
    @Column(name = "case_id")
    private Integer caseId;

    /**
     * 待审批人工号
     */
    private String oaid;
    @Column(name = "oa_name")
    private String oaName;
    public void setOaName(String oaName) {
        this.oaName = oaName;
    }
    public String getOaName() {
        return oaName;
    }
    /**
     * 关联业务id
     */
    @Column(name = "business_id")
    private Integer businessId;

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    private Date createTime;
    @Transient
    private String createTime_string;
    public void setCreateTime_string(String createTime_string) {
        this.createTime_string = createTime_string;
    }
    public String getCreateTime_string() {
        return createTime_string;
    }
    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;
    @Transient
    private String updateTime_string;
    public void setUpdateTime_string(String updateTime_string) {
        this.updateTime_string = updateTime_string;
    }
    public String getUpdateTime_string() {
        return updateTime_string;
    }
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
     * 是否是最后审批人
     */
    @Column(name = "last_audit")
    private Integer lastAudit;

    /**
     * 审批顺序
     */
    @Column(name = "audit_sequence")
    private Integer auditSequence;

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
     * 获取场景id：1-签证申请 2-签证结果 3-获签信息 4-院校申请 5-院校申请结果 6-寄出绩效 8-后续申请结果
     *
     * @return case_id - 场景id：1-签证申请 2-签证结果 3-获签信息 4-院校申请 5-院校申请结果 6-寄出绩效 8-后续申请结果
     */
    public Integer getCaseId() {
        return caseId;
    }

    /**
     * 设置场景id：1-签证申请 2-签证结果 3-获签信息 4-院校申请 5-院校申请结果 6-寄出绩效 8-后续申请结果
     *
     * @param caseId 场景id：1-签证申请 2-签证结果 3-获签信息 4-院校申请 5-院校申请结果 6-寄出绩效 8-后续申请结果
     */
    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    /**
     * 获取待审批人工号
     *
     * @return oaid - 待审批人工号
     */
    public String getOaid() {
        return oaid;
    }

    /**
     * 设置待审批人工号
     *
     * @param oaid 待审批人工号
     */
    public void setOaid(String oaid) {
        this.oaid = oaid;
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
     * 获取是否是最后审批人
     *
     * @return last_audit - 是否是最后审批人
     */
    public Integer getLastAudit() {
        return lastAudit;
    }

    /**
     * 设置是否是最后审批人
     *
     * @param lastAudit 是否是最后审批人
     */
    public void setLastAudit(Integer lastAudit) {
        this.lastAudit = lastAudit;
    }

    /**
     * 获取审批顺序
     *
     * @return audit_sequence - 审批顺序
     */
    public Integer getAuditSequence() {
        return auditSequence;
    }

    /**
     * 设置审批顺序
     *
     * @param auditSequence 审批顺序
     */
    public void setAuditSequence(Integer auditSequence) {
        this.auditSequence = auditSequence;
    }
}