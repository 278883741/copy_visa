package com.aoji.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "audit_dingding_info")
public class AuditDingdingInfo {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 场景id：1.文案管理-定效方案 2.院校申请-首次寄出绩效 3.院校申请-申请结果 4.院校申请-COE/CAS 5-签证申请 6-签证结果 7-获签信息 8.后续申请结果 9-院校申请-最终寄出绩效 10-申请缓办
     */
    @Column(name = "case_id")
    private Integer caseId;

    /**
     * 关联业务id
     */
    @Column(name = "bussiness_id")
    private Integer bussinessId;

    /**
     * 发送钉钉消息链接
     */
    @Column(name = "send_link")
    private String sendLink;

    /**
     * 发送时间
     */
    @Column(name = "send_time")
    private Date sendTime;

    /**
     * 发送状态: 0成功/1:失败
     */
    @Column(name = "send_status")
    private Boolean sendStatus;

    /**
     * 成功时间
     */
    @Column(name = "success_time")
    private Date successTime;

    /**
     * 审批时间
     */
    @Column(name = "audit_time")
    private Date auditTime;

    /**
     * 审批人工号
     */
    @Column(name = "audit_opterator_no")
    private String auditOpteratorNo;

    /**
     * 申请结果id
     */
    @Column(name = "applyResult_id")
    private String applyResultId;

    /**
     * 申请结果id
     */
    @Column(name = "apply_id")
    private String applyId;

    /**
     * 申请结果id
     */
    @Column(name = "student_no")
    private String studentNo;


    /**
     * 申请结果表的修改时间
     */
    @Column(name = "applyResult_updateTime")
    private String applyResultUpdateTime;

    /**
     * 申请结果id
     */
    @Column(name = "audit_status")
    private Boolean auditStatus;

    public Boolean getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Boolean auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getApplyResultId() {
        return applyResultId;
    }

    public void setApplyResultId(String applyResultId) {
        this.applyResultId = applyResultId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getApplyResultUpdateTime() {
        return applyResultUpdateTime;
    }

    public void setApplyResultUpdateTime(String applyResultUpdateTime) {
        this.applyResultUpdateTime = applyResultUpdateTime;
    }

    /**
     * 获取id
     *
     * @return id - id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取场景id：1.文案管理-定效方案 2.院校申请-首次寄出绩效 3.院校申请-申请结果 4.院校申请-COE/CAS 5-签证申请 6-签证结果 7-获签信息 8.后续申请结果 9-院校申请-最终寄出绩效 10-申请缓办
     *
     * @return case_id - 场景id：1.文案管理-定效方案 2.院校申请-首次寄出绩效 3.院校申请-申请结果 4.院校申请-COE/CAS 5-签证申请 6-签证结果 7-获签信息 8.后续申请结果 9-院校申请-最终寄出绩效 10-申请缓办
     */
    public Integer getCaseId() {
        return caseId;
    }

    /**
     * 设置场景id：1.文案管理-定效方案 2.院校申请-首次寄出绩效 3.院校申请-申请结果 4.院校申请-COE/CAS 5-签证申请 6-签证结果 7-获签信息 8.后续申请结果 9-院校申请-最终寄出绩效 10-申请缓办
     *
     * @param caseId 场景id：1.文案管理-定效方案 2.院校申请-首次寄出绩效 3.院校申请-申请结果 4.院校申请-COE/CAS 5-签证申请 6-签证结果 7-获签信息 8.后续申请结果 9-院校申请-最终寄出绩效 10-申请缓办
     */
    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
    }

    /**
     * 获取关联业务id
     *
     * @return bussiness_id - 关联业务id
     */
    public Integer getBussinessId() {
        return bussinessId;
    }

    /**
     * 设置关联业务id
     *
     * @param bussinessId 关联业务id
     */
    public void setBussinessId(Integer bussinessId) {
        this.bussinessId = bussinessId;
    }

    /**
     * 获取发送钉钉消息链接
     *
     * @return send_link - 发送钉钉消息链接
     */
    public String getSendLink() {
        return sendLink;
    }

    /**
     * 设置发送钉钉消息链接
     *
     * @param sendLink 发送钉钉消息链接
     */
    public void setSendLink(String sendLink) {
        this.sendLink = sendLink;
    }

    /**
     * 获取发送时间
     *
     * @return send_time - 发送时间
     */
    public Date getSendTime() {
        return sendTime;
    }


    /**
     * 设置发送时间
     *
     * @param sendTime 发送时间
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Boolean getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Boolean sendStatus) {
        this.sendStatus = sendStatus;
    }

    /**
     * 获取成功时间
     *
     * @return success_time - 成功时间
     */
    public Date getSuccessTime() {
        return successTime;
    }

    /**
     * 设置成功时间
     *
     * @param successTime 成功时间
     */
    public void setSuccessTime(Date successTime) {
        this.successTime = successTime;
    }

    /**
     * 获取审批时间
     *
     * @return audit_time - 审批时间
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * 设置审批时间
     *
     * @param auditTime 审批时间
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    /**
     * 获取审批人工号
     *
     * @return audit_opterator_no - 审批人工号
     */
    public String getAuditOpteratorNo() {
        return auditOpteratorNo;
    }

    /**
     * 设置审批人工号
     *
     * @param auditOpteratorNo 审批人工号
     */
    public void setAuditOpteratorNo(String auditOpteratorNo) {
        this.auditOpteratorNo = auditOpteratorNo;
    }
}