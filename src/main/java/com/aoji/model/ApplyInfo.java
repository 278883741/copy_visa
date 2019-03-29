package com.aoji.model;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "apply_info")
public class ApplyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 内网学号
     */
    @Column(name = "student_no")
    private String studentNo;

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
     * 合作机构名称
     */
    @Column(name = "agency_name")
    private String agencyName;

    /**
     * 合作机构编号
     */
    @Column(name = "agency_no")
    private Integer agencyNo;

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


    /**
     * 申请寄出日期
     */
    @Column(name = "send_time")
    private Date sendTime;

    /**
     * 申请专业名称
     */
    @Column(name = "major_name")
    private String majorName;

    /**
     * 院校计划id
     */
    private Integer planCollegeId;

    /**
     * 申请专业id
     */
    @Column(name = "major_id")
    private Integer majorId;

    /**
     * 申请专业细节
     */
    @Column(name = "major_detail")
    private String majorDetail;

    /**
     * 院校学术申请材料
     */
    @Column(name = "college_material")
    private String collegeMaterial;

    public String getCollegeMaterial() {
        return collegeMaterial;
    }

    public void setCollegeMaterial(String collegeMaterial) {
        this.collegeMaterial = collegeMaterial;
    }

    public String getCollegeCopy() {
        return collegeCopy;
    }

    public void setCollegeCopy(String collegeCopy) {
        this.collegeCopy = collegeCopy;
    }

    /**
     * 文书材料
     */
    @Column(name = "college_copy")
    private String collegeCopy;


    /**
     * 课程名称
     */
    @Column(name = "course_name")
    private String courseName;

    /**
     * 课程长度
     */
    private String courseLength;

    public String getCourseLength() {
        return courseLength;
    }

    public void setCourseLength(String courseLength) {
        this.courseLength = courseLength;
    }

    /**
     * 课程类型：1-语言 2-预备 3-主课
     */
    @Column(name = "course_type")
    private Integer courseType;

    @Transient
    private String courseType_String;
    public void setCourseType_String(String courseType_String) {
        this.courseType_String = courseType_String;
    }
    public String getCourseType_String() {
        return courseType_String;
    }

    @Transient
    private Date coeAuditTime;

    public Date getCoeAuditTime() {
        return coeAuditTime;
    }

    public void setCoeAuditTime(Date coeAuditTime) {
        this.coeAuditTime = coeAuditTime;
    }

    @Transient
    private Date offerAuditTime;

    public Date getOfferAuditTime() {
        return offerAuditTime;
    }

    public void setOfferAuditTime(Date offerAuditTime) {
        this.offerAuditTime = offerAuditTime;
    }

    /**
     * 更新时间，供前台显示。
     */
    @Transient
    private String updateTimeString;

    /**
     * 学制
     */
    @Column(name = "school_length")
    private String schoolLength;

    /**
     * 所在校区
     */
//    private String schoolArea;


    /**
     * 开课日期
     */
    @Column(name = "course_start_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date courseStartTime;

    @Transient
    private String courseStartTime_string;
    public void setCourseStartTime_string(String courseStartTime_string) {
        this.courseStartTime_string = courseStartTime_string;
    }
    public String getCourseStartTime_string() {
        return courseStartTime_string;
    }

    @Transient
    private Integer status;
    @Transient
    private Integer branchId;
    @Transient
    private String branchName;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    @Transient
    private Integer nationId;
    public void setNationId(Integer nationId) {
        this.nationId = nationId;
    }
    public Integer getNationId() {
        return nationId;
    }

    @Transient
    private String nationName;

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    @Transient
    private String studentName;
    @Transient
    private String salesConsultant;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSalesConsultant() {
        return salesConsultant;
    }

    public void setSalesConsultant(String salesConsultant) {
        this.salesConsultant = salesConsultant;
    }

    /**
     * 文案专员
     */
    @Column(name = "copy_operator")
    private String copyOperator;

    /**
     * 外联专员
     */
    private String connector;

    /**
     * 外联专员姓名
     */
    private String connectorName;

    /**
     * 申请状态：10-未提交院校申请 11-未补件   20-未收到院校结果 30-申请结果为拒绝 40-确认录取已完成 50-确认拒绝已完成
     */
    @Column(name = "apply_status_code")
    private Integer applyStatusCode;


    //private String applyStatusName;

    /**
     * 是否延期开学
     */
    @Column(name = "delay_status")
    private Boolean delayStatus;

    /**
     * 延期开学日期
     */
    @Column(name = "delay_date")
    private Date delayDate;

    /**
     * 押金金额
     */
    @Column(name = "deposit_amount")
    private BigDecimal depositAmount;

    /**
     * 注册费
     */
    @Column(name = "registration_fee")
    private BigDecimal registrationFee;




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
     * 操作人名称
     */
//    @Transient
    @Column(name = "operator_name")
    private String operatorName;

    /**
     * 押金支付截止日期
     */
    @Column(name = "deposit_payment_deadline")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date depositPaymentDeadline;

    /**
     * 申请类型：1-主课申请 2-语言申请。（默认为主课申请）
     */
    @Column(name = "apply_type")
    private Integer applyType;



    /**
     * 申请途径：1-邮寄 2-网申 3-邮件
     */
    @Column(name = "apply_way")
    private Integer applyWay;

    /**
     * 实际申请提交日期
     */
    @Column(name = "commit_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date commitDate;

    /**
     * 学费金额
     */
    private BigDecimal tuition;

    /**
     * 课程id
     */
    @Column(name = "course_id")
    private Integer courseId;

    /**
     * 定校方案id

     */
    @Column(name = "plan_id")
    private Integer planId;

    /**
     * 审理周期
     */
    @Column(name="reply_cycle")
    private Integer replyCycle;

    /**
     * 申请寄出日期，供前台显示
     */
    @Transient
    private String sendDateString;

    /**
     * 奖学金金额
     */
    @Column(name = "scholarship_amount")
    private BigDecimal scholarshipAmount;

    /**
     * 学校学号
     */
    @Column(name = "school_no")
    private String schoolNo;

    /**
     * 录入人工号
     */
    @Column(name="input_no")
    private String inputNo;
    /**
     * 录入人姓名
     */
    @Transient
    private String inputName;

    /**
     * 学费金额单位，关联币种表
     */
    @Column(name="tuition_unit")
    private Integer tuitionUnit;

    /**
     * 奖学金金额单位，关联币种表
     */
    @Column(name="scholarship_amount_unit")
    private Integer scholarshipAmountUnit;

    /**
     * 押金金额单位，关联币种表
     */
    @Column(name="deposit_amount_unit")
    private Integer depositAmountUnit;


    /**
     * 添加操作人
     */
    @Column(name = "add_operator_no")
    private String addOperatorNo;

    /**
     * 退回操作人
     */
    @Column(name = "reject_operator_no")
    private String rejectOperatorNo;

    public String getAddOperatorNo() {
        return addOperatorNo;
    }

    public void setAddOperatorNo(String addOperatorNo) {
        this.addOperatorNo = addOperatorNo;
    }

    public String getRejectOperatorNo() {
        return rejectOperatorNo;
    }

    public void setRejectOperatorNo(String rejectOperatorNo) {
        this.rejectOperatorNo = rejectOperatorNo;
    }

    /**
     * 是否设置为入读课程:1-是 0-否
     */

    @Column(name="plan_course_status")
    private  Integer  planCourseStatus;

    /**
     * 接受offer日期
     */
    @Column(name = "accept_offer_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date acceptOfferTime;

    /**
     * 专业教育学段
     */
    @Column(name = "education_section")
    private Integer educationSection;

    /**
     * 学生申请院校国家id
     */
    @Column(name = "student_nation_id")
    private String studentNationId;

    public String getStudentNationId() {
        return studentNationId;
    }

    public void setStudentNationId(String studentNationId) {
        this.studentNationId = studentNationId;
    }

    public Integer getEducationSection() {
        return educationSection;
    }

    public void setEducationSection(Integer educationSection) {
        this.educationSection = educationSection;
    }

    public Date getAcceptOfferTime() {
        return acceptOfferTime;
    }

    public void setAcceptOfferTime(Date acceptOfferTime) {
        this.acceptOfferTime = acceptOfferTime;
    }


    public Integer getPlanCourseStatus() {
        return planCourseStatus;
    }

    public void setPlanCourseStatus(Integer planCourseStatus) {
        this.planCourseStatus = planCourseStatus;
    }

    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }

    public Integer getTuitionUnit() {
        return tuitionUnit;
    }

    public void setTuitionUnit(Integer tuitionUnit) {
        this.tuitionUnit = tuitionUnit;
    }

    public Integer getScholarshipAmountUnit() {
        return scholarshipAmountUnit;
    }

    public void setScholarshipAmountUnit(Integer scholarshipAmountUnit) {
        this.scholarshipAmountUnit = scholarshipAmountUnit;
    }

    public Integer getDepositAmountUnit() {
        return depositAmountUnit;
    }

    public void setDepositAmountUnit(Integer depositAmountUnit) {
        this.depositAmountUnit = depositAmountUnit;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getInputNo() {
        return inputNo;
    }

    public void setInputNo(String inputNo) {
        this.inputNo = inputNo;
    }

    public String getInputName() {
        return inputName;
    }

    public void setInputName(String inputName) {
        this.inputName = inputName;
    }

    public BigDecimal getScholarshipAmount() {
        return scholarshipAmount;
    }

    public void setScholarshipAmount(BigDecimal scholarshipAmount) {
        this.scholarshipAmount = scholarshipAmount;
    }

    public String getSchoolNo() {
        return schoolNo;
    }

    public void setSchoolNo(String schoolNo) {
        this.schoolNo = schoolNo;
    }

    public String getSendDateString() {
        return sendDateString;
    }

    public void setSendDateString(String sendDateString) {
        this.sendDateString = sendDateString;
    }

    public Integer getReplyCycle() {
        return replyCycle;
    }

    public void setReplyCycle(Integer replyCycle) {
        this.replyCycle = replyCycle;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getUpdateTimeString() {
        return updateTimeString;
    }

    public void setUpdateTimeString(String updateTimeString) {
        this.updateTimeString = updateTimeString;
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
     * 获取申请院校名称
     *
     * @return college_name - 申请院校名称
     */
    public String getCollegeName() {
        return collegeName;
    }

    /**
     * 设置申请院校名称
     *
     * @param collegeName 申请院校名称
     */
    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public Integer getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Integer collegeId) {
        this.collegeId = collegeId;
    }

    /**
     * 获取申请寄出日期
     *
     * @return send_time - 申请寄出日期
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * 设置申请寄出日期
     *
     * @param sendTime 申请寄出日期
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 获取申请专业名称
     *
     * @return major_name - 申请专业名称
     */
    public String getMajorName() {
        return majorName;
    }

    /**
     * 设置申请专业名称
     *
     * @param majorName 申请专业名称
     */
    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    /**
     * 获取申请专业id
     *
     * @return major_id - 申请专业id
     */
    public Integer getMajorId() {
        return majorId;
    }

    /**
     * 设置申请专业id
     *
     * @param majorId 申请专业id
     */
    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
    }

    /**
     * 获取申请专业细节
     *
     * @return major_detail - 申请专业细节
     */
    public String getMajorDetail() {
        return majorDetail;
    }

    /**
     * 设置申请专业细节
     *
     * @param majorDetail 申请专业细节
     */
    public void setMajorDetail(String majorDetail) {
        this.majorDetail = majorDetail;
    }

    /**
     * 获取课程名称
     *
     * @return course_name - 课程名称
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * 设置课程名称
     *
     * @param courseName 课程名称
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * 获取课程类型：1-语言 2-预备 3-主课
     *
     * @return course_type - 课程类型：1-语言 2-预备 3-主课
     */
    public Integer getCourseType() {
        return courseType;
    }

    /**
     * 设置课程类型：1-语言 2-预备 3-主课
     *
     * @param courseType 课程类型：1-语言 2-预备 3-主课
     */
    public void setCourseType(Integer courseType) {
        this.courseType = courseType;
    }

    /**
     * 获取学制
     *
     * @return school_length - 学制
     */
    public String getSchoolLength() {
        return schoolLength;
    }

    /**
     * 设置学制
     *
     * @param schoolLength 学制
     */
    public void setSchoolLength(String schoolLength) {
        this.schoolLength = schoolLength;
    }



    /**
     * 获取开课日期
     *
     * @return course_start_time - 开课日期
     */
    public Date getCourseStartTime() {
        return courseStartTime;
    }

    /**
     * 设置开课日期
     *
     * @param courseStartTime 开课日期
     */
    public void setCourseStartTime(Date courseStartTime) {
        this.courseStartTime = courseStartTime;
    }

    /**
     * 获取文案专员
     *
     * @return copy_operator - 文案专员
     */
    public String getCopyOperator() {
        return copyOperator;
    }

    /**
     * 设置文案专员
     *
     * @param copyOperator 文案专员
     */
    public void setCopyOperator(String copyOperator) {
        this.copyOperator = copyOperator;
    }

    /**
     * 获取外联专员
     *
     * @return connector - 外联专员
     */
    public String getConnector() {
        return connector;
    }

    /**
     * 设置外联专员
     *
     * @param connector 外联专员
     */
    public void setConnector(String connector) {
        this.connector = connector;
    }

    /**
     * 获取申请状态编号
     *
     * @return apply_status_code - 申请状态编号
     */
    public Integer getApplyStatusCode() {
        return applyStatusCode;
    }

    /**
     * 设置申请状态编号
     *
     * @param applyStatusCode 申请状态编号
     */
    public void setApplyStatusCode(Integer applyStatusCode) {
        this.applyStatusCode = applyStatusCode;
    }


    /**
     * 获取是否延期开学
     *
     * @return delay_status - 是否延期开学
     */
    public Boolean getDelayStatus() {
        return delayStatus;
    }

    /**
     * 设置是否延期开学
     *
     * @param delayStatus 是否延期开学
     */
    public void setDelayStatus(Boolean delayStatus) {
        this.delayStatus = delayStatus;
    }

    /**
     * 获取延期开学日期
     *
     * @return delay_date - 延期开学日期
     */
    public Date getDelayDate() {
        return delayDate;
    }

    /**
     * 设置延期开学日期
     *
     * @param delayDate 延期开学日期
     */
    public void setDelayDate(Date delayDate) {
        this.delayDate = delayDate;
    }

    /**
     * 获取押金金额
     *
     * @return deposit_amount - 押金金额
     */
    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    /**
     * 设置押金金额
     *
     * @param depositAmount 押金金额
     */
    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
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
     * 获取押金支付截止日期
     *
     * @return deposit_payment_deadline - 押金支付截止日期
     */
    public Date getDepositPaymentDeadline() {
        return depositPaymentDeadline;
    }

    /**
     * 设置押金支付截止日期
     *
     * @param depositPaymentDeadline 押金支付截止日期
     */
    public void setDepositPaymentDeadline(Date depositPaymentDeadline) {
        this.depositPaymentDeadline = depositPaymentDeadline;
    }

    /**
     * 获取申请类型：1-主课申请 2-语言申请。（默认为主课申请）
     *
     * @return apply_type - 申请类型：1-主课申请 2-语言申请。（默认为主课申请）
     */
    public Integer getApplyType() {
        return applyType;
    }

    /**
     * 设置申请类型：1-主课申请 2-语言申请。（默认为主课申请）
     *
     * @param applyType 申请类型：1-主课申请 2-语言申请。（默认为主课申请）
     */
    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }


    /**
     * 获取申请途径：1-邮寄 2-网申 3-邮件
     *
     * @return apply_way - 申请途径：1-邮寄 2-网申 3-邮件
     */
    public Integer getApplyWay() {
        return applyWay;
    }

    /**
     * 设置申请途径：1-邮寄 2-网申 3-邮件
     *
     * @param applyWay 申请途径：1-邮寄 2-网申 3-邮件
     */
    public void setApplyWay(Integer applyWay) {
        this.applyWay = applyWay;
    }

    /**
     * 获取实际申请提交日期
     *
     * @return commit_date - 实际申请提交日期
     */
    public Date getCommitDate() {
        return commitDate;
    }

    /**
     * 设置实际申请提交日期
     *
     * @param commitDate 实际申请提交日期
     */
    public void setCommitDate(Date commitDate) {
        this.commitDate = commitDate;
    }

    /**
     * 获取学费金额
     *
     * @return tuition - 学费金额
     */
    public BigDecimal getTuition() {
        return tuition;
    }

    /**
     * 设置学费金额
     *
     * @param tuition 学费金额
     */
    public void setTuition(BigDecimal tuition) {
        this.tuition = tuition;
    }

    /**
     * 获取课程id
     *
     * @return course_id - 课程id
     */
    public Integer getCourseId() {
        return courseId;
    }

    /**
     * 设置课程id
     *
     * @param courseId 课程id
     */
    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

//    public String getSchoolArea() {
//        return schoolArea;
//    }
//
//    public void setSchoolArea(String schoolArea) {
//        this.schoolArea = schoolArea;
//    }

    public Integer getPlanCollegeId() {
        return planCollegeId;
    }

    public void setPlanCollegeId(Integer planCollegeId) {
        this.planCollegeId = planCollegeId;
    }


    public BigDecimal getRegistrationFee() {
        return registrationFee;
    }

    public void setRegistrationFee(BigDecimal registrationFee) {
        this.registrationFee = registrationFee;
    }

}