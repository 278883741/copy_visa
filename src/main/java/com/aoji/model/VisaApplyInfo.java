package com.aoji.model;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import javax.persistence.*;

@Table(name = "visa_apply_info")
public class VisaApplyInfo {
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
     * 学生学号
     */
    @Column(name = "student_no")
    private String studentNo;

    /**
     * 签证方式：1-电签 2-普签
     */
    @Column(name = "visa_way")
    private Integer visaWay;
    @Transient
    private String visaWay_string;
    public void setVisaWay_string(String visaWay_string) {
        this.visaWay_string = visaWay_string;
    }
    public String getVisaWay_string() {
        return visaWay_string;
    }

    /**
     * 签证类型
     */
    @Column(name = "visa_type")
    private Integer visaType;
    @Transient
    private String visaType_string;
    public void setVisaType_string(String visaType_string) {
        this.visaType_string = visaType_string;
    }
    public String getVisaType_string() {
        return visaType_string;
    }

    /**
     * 送件日期
     */
    @Column(name = "send_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendDate;

    /**
     * 备注
     */
    @Column(name = "visa_comment")
    private String visaComment;

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
    @Column(name = "operator_name")
    private String operatorName;
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * 签证申请审核状态：1-已递交 2- 审核中 3-审核通过 4-审核不通过
     */
    @Column(name = "apply_audit_status")
    private Integer applyAuditStatus;

    /**
     * DS160编号
     */
    @Column(name = "ds160_no")
    private String ds160No;

    /**
     * DS160安全问答答案
     */
    @Column(name = "ds160_answer")
    private String ds160Answer;

    /**
     * 审案员
     */
    @Column(name = "check_no")
    private String checkNo;
    @Column(name = "check_name")
    private String checkName;
    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }
    public String getCheckName() {
        return checkName;
    }

    /**
     * 1：学生签证 0：非学生签证
     */
    @Column(name = "student_visa_status")
    private Boolean studentVisaStatus;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "payment_slip")
    private String paymentSlip;
    public void setPaymentSlip(String paymentSlip) {
        this.paymentSlip = paymentSlip;
    }
    public String getPaymentSlip() {
        return paymentSlip;
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

    /**
     * 用于展示学生所属国家
     */
    @Transient
    private String nationName;
    public void setNationName(String nationName) {
        this.nationName = nationName;
    }
    public String getNationName() {
        return nationName;
    }

    /**
     * 用于展示签证类型
     */
    @Transient
    private String visaTypeDisplay;
    public void setVisaTypeDisplay(String visaTypeDisplay) {
        this.visaTypeDisplay = visaTypeDisplay;
    }
    public String getVisaTypeDisplay() {
        return visaTypeDisplay;
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
     * 获取签证方式：1-电签 2-普签
     *
     * @return visa_way - 签证方式：1-电签 2-普签
     */
    public Integer getVisaWay() {
        return visaWay;
    }

    /**
     * 设置签证方式：1-电签 2-普签
     *
     * @param visaWay 签证方式：1-电签 2-普签
     */
    public void setVisaWay(Integer visaWay) {
        this.visaWay = visaWay;
    }

    /**
     * 获取签证类型
     *
     * @return visa_type - 签证类型
     */
    public Integer getVisaType() {
        return visaType;
    }

    /**
     * 设置签证类型
     *
     * @param visaType 签证类型
     */
    public void setVisaType(Integer visaType) {
        this.visaType = visaType;
    }

    /**
     * 获取送件日期
     *
     * @return send_date - 送件日期
     */
    public Date getSendDate() {
        return sendDate;
    }

    /**
     * 设置送件日期
     *
     * @param sendDate 送件日期
     */
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
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
     * 获取签证申请审核状态：1-已递交 2- 审核中 3-审核通过 4-审核不通过
     *
     * @return apply_audit_status - 签证申请审核状态：1-已递交 2- 审核中 3-审核通过 4-审核不通过
     */
    public Integer getApplyAuditStatus() {
        return applyAuditStatus;
    }

    /**
     * 设置签证申请审核状态：1-已递交 2- 审核中 3-审核通过 4-审核不通过
     *
     * @param applyAuditStatus 签证申请审核状态：1-已递交 2- 审核中 3-审核通过 4-审核不通过
     */
    public void setApplyAuditStatus(Integer applyAuditStatus) {
        this.applyAuditStatus = applyAuditStatus;
    }

    /**
     * 获取DS160编号
     *
     * @return ds160_no - DS160编号
     */
    public String getDs160No() {
        return ds160No;
    }

    /**
     * 设置DS160编号
     *
     * @param ds160No DS160编号
     */
    public void setDs160No(String ds160No) {
        this.ds160No = ds160No;
    }

    /**
     * 获取DS160安全问答答案
     *
     * @return ds160_answer - DS160安全问答答案
     */
    public String getDs160Answer() {
        return ds160Answer;
    }

    /**
     * 设置DS160安全问答答案
     *
     * @param ds160Answer DS160安全问答答案
     */
    public void setDs160Answer(String ds160Answer) {
        this.ds160Answer = ds160Answer;
    }

    /**
     * 获取审案员
     *
     * @return check_no - 审案员
     */
    public String getCheckNo() {
        return checkNo;
    }

    /**
     * 设置审案员
     *
     * @param checkNo 审案员
     */
    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo;
    }

    /**
     * 获取1：学生签证 0：非学生签证
     *
     * @return student_visa_status - 1：学生签证 0：非学生签证
     */
    public Boolean getStudentVisaStatus() {
        return studentVisaStatus;
    }

    /**
     * 设置1：学生签证 0：非学生签证
     *
     * @param studentVisaStatus 1：学生签证 0：非学生签证
     */
    public void setStudentVisaStatus(Boolean studentVisaStatus) {
        this.studentVisaStatus = studentVisaStatus;
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