package com.aoji.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "commission_school")
public class CommissionSchool {

    public CommissionSchool(){

    }

    public  CommissionSchool(Integer id){
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 关联获签信息院校表id
     */
    @Column(name = "visa_commission_id")
    private  Integer visaCommissionId;

    @Column(name = "student_id")
    private Integer studentId;



    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    /**
     * 学号
     */
    @Column(name = "student_no")
    private String studentNo;

    /**
     * 学校名称
     */
    @Column(name = "school_name")
    private String schoolName;

    /**
     * 校区
     */
    @Column(name = "school_area")
    private String schoolArea;


    /**
     * 学校类型
     */
    @Column(name = "college_type")
    private String collegeType;

    /**
     * 开课日期
     */
    @Column(name = "start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /**
     * 结束日期
     */
    @Column(name = "end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /**
     * 上课周数
     */
    @Column(name = "study_week")
    private String studyWeek;

    /**
     * 课程id
     */
    @Column(name = "course_id")
    private Integer courseId;

    /**
     * 课程类型
     */
    @Column(name = "course_type")
    private String courseType;

    /**
     * 课程名称
     */
    @Column(name = "course_name")
    private String courseName;


    /**
     * 专业教育学段 2-中小学,3-本科,4-硕士,5-博士,9-其他
     */
    @Column(name = "education_section")
    private Integer educationSection;

    /**
     * 专业名称
     */
    @Column(name = "major_name")
    private String majorName;

    /**
     * 学费
     */
    private BigDecimal tuition;

    /**
     * 已付学费币种
     */
    @Column(name = "paid_tuition_currency")
    private String paidTuitionCurrency;

    /**
     * 学费币种
     */
    @Column(name = "tuition_currency")
    private String tuitionCurrency;

    /**
     * 结佣币种
     */
    @Column(name = "commission_currency")
    private String commissionCurrency;

    /**
     * 课程备注
     */
    @Column(name = "course_remark")
    private String courseRemark;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 支付金额
     */
    @Column(name = "paid_fee")
    private BigDecimal paidFee;

    /**
     * 返佣比例
     */
    @Column(name = "school_rate")
    private BigDecimal schoolRate;

    /**
     * gst比例
     */
    @Column(name = "gst_rate")
    private BigDecimal gstRate;

    /**
     * 课程属性
     */
    @Column(name = "course_property")
    private String courseProperty;

    /**
     * 学生属性
     */
    @Column(name = "student_property")
    private String studentProperty;

    /**
     * 结佣归属
     */
    @Column(name = "commission_belong")
    private String commissionBelong;

    /**
     * 健康保险
     */
    @Column(name = "healthy_fee")
    private BigDecimal healthyFee;

    /**
     * 结佣状态
     */
    private String status;

    /**
     * 是否删除
     */
    @Column(name = "delete_status")
    private Boolean deleteStatus;

    /**
     * offer附件
     */
    @Column(name = "offer_file")
    private String offerFile;

    /**
     * coe附件
     */
    @Column(name = "coe_file")
    private String coeFile;

    /**
     * 邮件附件
     */
    @Column(name = "email_file")
    private String emailFile;

    /**
     * 顾问
     */
    private String consulter;

    /**
     * 转接顾问
     */
    @Column(name = "transfer_consulter")
    private String transferConsulter;

    /**
     * 顾问分支
     */
    @Column(name = "consulter_branch")
    private String consulterBranch;

    /**
     * 分支
     */
    @Column(name = "branch_id")
    private String branchId;

    /**
     * 分支
     */
    @Column(name = "branch")
    private String branch;

    /**
     * 备注1
     */
    @Column(name = "school_remark")
    private String schoolRemark;

    /**
     * 备注2
     */
    @Column(name = "school_remark2")
    private String schoolRemark2;

    @Column(name = "update_id")
    private String updateId;

    @Column(name = "update_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateDate;

    @Column(name = "special_id")
    private Integer specialId;

    /**
     *文签顾问
     */
    @Column(name = "copy_operator")
    private String copyOperator;

    /**
     *院校id
     */
    @Column(name = "college_id")
    private String collegeId;

    /**
     *院校id
     */
    @Column(name = "major_id")
    private String majorId;

    /**
     * 合作机构编号
     * @return
     */
    @Column(name = "agency_no")
    private Integer agencyNo;

    /**
     * 合作机构名称
     * @return
     */
    @Column(name = "agency_name")
    private String agencyName;

    public String getSchoolRemark2() {
        return schoolRemark2;
    }

    public void setSchoolRemark2(String schoolRemark2) {
        this.schoolRemark2 = schoolRemark2;
    }

    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId;
    }

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }

    public String getPaidTuitionCurrency() {
        return paidTuitionCurrency;
    }

    public void setPaidTuitionCurrency(String paidTuitionCurrency) {
        this.paidTuitionCurrency = paidTuitionCurrency;
    }

    public String getSchoolRemark() {
        return schoolRemark;
    }

    public void setSchoolRemark(String schoolRemark) {
        this.schoolRemark = schoolRemark;
    }

    public String getCopyOperator() {
        return copyOperator;
    }

    public void setCopyOperator(String copyOperator) {
        this.copyOperator = copyOperator;
    }

    /**
     * 学校学号
     */
    @Column(name = "school_no")
    private String schoolNo;

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
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
     * 获取学号
     *
     * @return student_no - 学号
     */
    public String getStudentNo() {
        return studentNo;
    }

    /**
     * 设置学号
     *
     * @param studentNo 学号
     */
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    /**
     * 获取学校名称
     *
     * @return school_name - 学校名称
     */
    public String getSchoolName() {
        return schoolName;
    }

    /**
     * 设置学校名称
     *
     * @param schoolName 学校名称
     */
    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    /**
     * 获取校区
     *
     * @return school_area - 校区
     */
    public String getSchoolArea() {
        return schoolArea;
    }

    /**
     * 设置校区
     *
     * @param schoolArea 校区
     */
    public void setSchoolArea(String schoolArea) {
        this.schoolArea = schoolArea;
    }

    /**
     * 获取开课日期
     *
     * @return start_date - 开课日期
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 设置开课日期
     *
     * @param startDate 开课日期
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 获取结束日期
     *
     * @return end_date - 结束日期
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 设置结束日期
     *
     * @param endDate 结束日期
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * 获取上课周数
     *
     * @return study_week - 上课周数
     */
    public String getStudyWeek() {
        return studyWeek;
    }

    /**
     * 设置上课周数
     *
     * @param studyWeek 上课周数
     */
    public void setStudyWeek(String studyWeek) {
        this.studyWeek = studyWeek;
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

    /**
     * 获取课程类型
     *
     * @return course_type - 课程类型
     */
    public String getCourseType() {
        return courseType;
    }

    /**
     * 设置课程类型
     *
     * @param courseType 课程类型
     */
    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    /**
     * 获取学费
     *
     * @return tuition - 学费
     */
    public BigDecimal getTuition() {
        return tuition;
    }

    /**
     * 设置学费
     *
     * @param tuition 学费
     */
    public void setTuition(BigDecimal tuition) {
        this.tuition = tuition;
    }

    /**
     * 获取学费币种
     *
     * @return tuition_currency - 学费币种
     */
    public String getTuitionCurrency() {
        return tuitionCurrency;
    }

    /**
     * 设置学费币种
     *
     * @param tuitionCurrency 学费币种
     */
    public void setTuitionCurrency(String tuitionCurrency) {
        this.tuitionCurrency = tuitionCurrency;
    }

    /**
     * 获取结佣币种
     *
     * @return commission_currency - 结佣币种
     */
    public String getCommissionCurrency() {
        return commissionCurrency;
    }

    /**
     * 设置结佣币种
     *
     * @param commissionCurrency 结佣币种
     */
    public void setCommissionCurrency(String commissionCurrency) {
        this.commissionCurrency = commissionCurrency;
    }

    /**
     * 获取课程备注
     *
     * @return course_remark - 课程备注
     */
    public String getCourseRemark() {
        return courseRemark;
    }

    /**
     * 设置课程备注
     *
     * @param courseRemark 课程备注
     */
    public void setCourseRemark(String courseRemark) {
        this.courseRemark = courseRemark;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取支付金额
     *
     * @return paid_fee - 支付金额
     */
    public BigDecimal getPaidFee() {
        return paidFee;
    }

    /**
     * 设置支付金额
     *
     * @param paidFee 支付金额
     */
    public void setPaidFee(BigDecimal paidFee) {
        this.paidFee = paidFee;
    }

    /**
     * 获取返佣比例
     *
     * @return school_rate - 返佣比例
     */
    public BigDecimal getSchoolRate() {
        return schoolRate;
    }

    /**
     * 设置返佣比例
     *
     * @param schoolRate 返佣比例
     */
    public void setSchoolRate(BigDecimal schoolRate) {
        this.schoolRate = schoolRate;
    }

    /**
     * 获取gst比例
     *
     * @return gst_rate - gst比例
     */
    public BigDecimal getGstRate() {
        return gstRate;
    }

    /**
     * 设置gst比例
     *
     * @param gstRate gst比例
     */
    public void setGstRate(BigDecimal gstRate) {
        this.gstRate = gstRate;
    }

    /**
     * 获取课程属性
     *
     * @return course_property - 课程属性
     */
    public String getCourseProperty() {
        return courseProperty;
    }

    /**
     * 设置课程属性
     *
     * @param courseProperty 课程属性
     */
    public void setCourseProperty(String courseProperty) {
        this.courseProperty = courseProperty;
    }

    /**
     * 获取学生属性
     *
     * @return student_property - 学生属性
     */
    public String getStudentProperty() {
        return studentProperty;
    }

    /**
     * 设置学生属性
     *
     * @param studentProperty 学生属性
     */
    public void setStudentProperty(String studentProperty) {
        this.studentProperty = studentProperty;
    }

    /**
     * 获取结佣归属
     *
     * @return commission_belong - 结佣归属
     */
    public String getCommissionBelong() {
        return commissionBelong;
    }

    /**
     * 设置结佣归属
     *
     * @param commissionBelong 结佣归属
     */
    public void setCommissionBelong(String commissionBelong) {
        this.commissionBelong = commissionBelong;
    }

    /**
     * 获取健康保险
     *
     * @return healthy_fee - 健康保险
     */
    public BigDecimal getHealthyFee() {
        return healthyFee;
    }

    /**
     * 设置健康保险
     *
     * @param healthyFee 健康保险
     */
    public void setHealthyFee(BigDecimal healthyFee) {
        this.healthyFee = healthyFee;
    }

    /**
     * 获取结佣状态
     *
     * @return status - 结佣状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置结佣状态
     *
     * @param status 结佣状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取是否删除
     *
     * @return delete_status - 是否删除
     */
    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * 设置是否删除
     *
     * @param deleteStatus 是否删除
     */
    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    /**
     * 获取offer附件
     *
     * @return offer_file - offer附件
     */
    public String getOfferFile() {
        return offerFile;
    }

    /**
     * 设置offer附件
     *
     * @param offerFile offer附件
     */
    public void setOfferFile(String offerFile) {
        this.offerFile = offerFile;
    }

    /**
     * 获取coe附件
     *
     * @return coe_file - coe附件
     */
    public String getCoeFile() {
        return coeFile;
    }

    /**
     * 设置coe附件
     *
     * @param coeFile coe附件
     */
    public void setCoeFile(String coeFile) {
        this.coeFile = coeFile;
    }

    /**
     * 获取邮件附件
     *
     * @return email_file - 邮件附件
     */
    public String getEmailFile() {
        return emailFile;
    }

    /**
     * 设置邮件附件
     *
     * @param emailFile 邮件附件
     */
    public void setEmailFile(String emailFile) {
        this.emailFile = emailFile;
    }

    /**
     * 获取顾问
     *
     * @return consulter - 顾问
     */
    public String getConsulter() {
        return consulter;
    }

    /**
     * 设置顾问
     *
     * @param consulter 顾问
     */
    public void setConsulter(String consulter) {
        this.consulter = consulter;
    }

    /**
     * 获取转接顾问
     *
     * @return transfer_consulter - 转接顾问
     */
    public String getTransferConsulter() {
        return transferConsulter;
    }

    /**
     * 设置转接顾问
     *
     * @param transferConsulter 转接顾问
     */
    public void setTransferConsulter(String transferConsulter) {
        this.transferConsulter = transferConsulter;
    }

    /**
     * 获取顾问分支
     *
     * @return consulter_branch - 顾问分支
     */
    public String getConsulterBranch() {
        return consulterBranch;
    }

    /**
     * 设置顾问分支
     *
     * @param consulterBranch 顾问分支
     */
    public void setConsulterBranch(String consulterBranch) {
        this.consulterBranch = consulterBranch;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return schoolRemark;
    }

    /**
     * 设置备注
     *
     * @param schoolRemark 备注
     */
    public void setRemark(String schoolRemark) {
        this.schoolRemark = schoolRemark;
    }

    /**
     * @return update_id
     */
    public String getUpdateId() {
        return updateId;
    }

    /**
     * @param updateId
     */
    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    /**
     * @return update_date
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * @return special_id
     */
    public Integer getSpecialId() {
        return specialId;
    }

    /**
     * @param specialId
     */
    public void setSpecialId(Integer specialId) {
        this.specialId = specialId;
    }

    /**
     * 获取学校学号
     *
     * @return school_no - 学校学号
     */
    public String getSchoolNo() {
        return schoolNo;
    }

    /**
     * 设置学校学号
     *
     * @param schoolNo 学校学号
     */
    public void setSchoolNo(String schoolNo) {
        this.schoolNo = schoolNo;
    }

    public String getCollegeType() {
        return collegeType;
    }

    public void setCollegeType(String collegeType) {
        this.collegeType = collegeType;
    }


    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public Integer getEducationSection() {
        return educationSection;
    }

    public void setEducationSection(Integer educationSection) {
        this.educationSection = educationSection;
    }

    public Integer getAgencyNo() {
        return agencyNo;
    }

    public void setAgencyNo(Integer agencyNo) {
        this.agencyNo = agencyNo;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public Integer getVisaCommissionId() {
        return visaCommissionId;
    }

    public void setVisaCommissionId(Integer visaCommissionId) {
        this.visaCommissionId = visaCommissionId;
    }
}