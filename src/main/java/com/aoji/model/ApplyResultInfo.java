package com.aoji.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "apply_result_info")
public class ApplyResultInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 申请id
     */
    @Column(name = "apply_id")
    private Integer applyId;

    /**
     * 回复途径：1-传真 2-电话 3-电子版 4-扫描件 5-原件
     */
    @Column(name = "reply_way")
    private Integer replyWay;

    /**
     * 结果类型：1-录取 2-拒绝 3-满位 4-其他
     */
    @Column(name = "result_type")
    private Integer resultType;

    /**
     * 回复日期，即申请结果到达日期
     */
    @Column(name = "result_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date resultDate;

    /**
     * 学生确认offer日期
     */
    @Column(name = "student_reply_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date studentReplyDate;

    /**
     * 学校确认offer接受日期
     */
    @Column(name = "school_reply_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date schoolReplyDate;

    /**
     * Offer类型:1-无条件offer 2-有语言条件offer 3-有其他条件offer 4-没有offer 5-其他
     */
    @Column(name = "offer_type")
    private Integer offerType;

    /**
     * offer附件地址
     */
    @Column(name = "offer_attachment")
    private String offerAttachment;


    /**
     * 回复截止日期
     */
    @Column(name = "reply_deadline")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    @Column(name="operator_name")
    private String operatorName;
    /**
     * Argue日期
     */
    @Column(name = "argue_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date argueDate;

    /**
     * 审核状态
     */
    @Column(name="audit_status")
    private Integer auditStatus;


    /**
     * 回复日期，供前台显示
     */
    @Transient
    private String resultDateString;
    /**
     * 创建日期，供前台显示
     */
    @Transient
    private String createdString;

    @Transient
    private String studentNo;

    @Transient
    private String studentName;

    /**
     * 学生是否在学生端确认,0-未确认,1-已确认
     * @return
     */
    @Column(name="student_reply")
    private  Integer studentReply;


    /**
     * 申请院校名称
     */
    @Column(name = "college_name")
    private String collegeName;

    /**
     * 申请院校编号
     */
    @Column(name = "college_id")
    private Integer collegeId;

    /**
     * 申请专业名称
     */
    @Column(name = "major_name")
    private String majorName;

    /**
     * 申请专业id
     */
    @Column(name = "major_id")
    private Integer majorId;

    /**
     * 学位名称
     */
    @Column(name = "course_name")
    private String courseName;

    /**
     * 学位id
     */
    @Column(name = "course_id")
    private Integer courseId;

    /**
     * 合作机构名称
     */
    @Column(name = "agency_name")
    private String agencyName;

    /**
     * 合作机构编号
     */
    @Column(name = "agency_no")
    private Integer agencyNo;

    /**
     * 申请类型
     */
    @Column(name = "apply_type")
    private Integer applyType;

    /**
     * 专业类型
     */
    @Column(name = "course_type")
    private Integer courseType;

    /**
     * 目标学历
     */
    @Column(name = "education_section")
    private Integer educationSection;

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public Integer getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Integer collegeId) {
        this.collegeId = collegeId;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public Integer getMajorId() {
        return majorId;
    }

    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public Integer getAgencyNo() {
        return agencyNo;
    }

    public void setAgencyNo(Integer agencyNo) {
        this.agencyNo = agencyNo;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public Integer getCourseType() {
        return courseType;
    }

    public void setCourseType(Integer courseType) {
        this.courseType = courseType;
    }

    public Integer getEducationSection() {
        return educationSection;
    }

    public void setEducationSection(Integer educationSection) {
        this.educationSection = educationSection;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getArgueDate() {
        return argueDate;
    }

    public void setArgueDate(Date argueDate) {
        this.argueDate = argueDate;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
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

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    /**
     * 获取结果类型：1-录取 2-拒绝 3-满位 4-其他
     *
     * @return result_type - 结果类型：1-录取 2-拒绝 3-满位 4-其他
     */
    public Integer getResultType() {
        return resultType;
    }

    /**
     * 设置结果类型：1-录取 2-拒绝 3-满位 4-其他
     *
     * @param resultType 结果类型：1-录取 2-拒绝 3-满位 4-其他
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
     * 获取Offer类型:1-无条件offer 2-有语言条件offer 3-有其他条件offer 4-没有offer 5-其他
     *
     * @return offer_type - Offer类型:1-无条件offer 2-有语言条件offer 3-有其他条件offer 4-没有offer 5-其他
     */
    public Integer getOfferType() {
        return offerType;
    }

    /**
     * 设置Offer类型:1-无条件offer 2-有语言条件offer 3-有其他条件offer 4-没有offer 5-其他
     *
     * @param offerType Offer类型:1-无条件offer 2-有语言条件offer 3-有其他条件offer 4-没有offer 5-其他
     */
    public void setOfferType(Integer offerType) {
        this.offerType = offerType;
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

    public String getResultDateString() {
        return resultDateString;
    }

    public void setResultDateString(String resultDateString) {
        this.resultDateString = resultDateString;
    }

    public String getCreatedString() {
        return createdString;
    }

    public void setCreatedString(String createdString) {
        this.createdString = createdString;
    }

    /**
     * 设置操作人
     *
     * @param operatorNo 操作人
     */

    public void setOperatorNo(String operatorNo) {
        this.operatorNo = operatorNo;
    }

    public Integer getStudentReply() {
        return studentReply;
    }

    public void setStudentReply(Integer studentReply) {
        this.studentReply = studentReply;
    }
}