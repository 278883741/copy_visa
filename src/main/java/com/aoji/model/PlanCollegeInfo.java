package com.aoji.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Table(name = "plan_college_info")
public class PlanCollegeInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 申请院校名称
     */
    @Column(name = "college_name")
    private String collegeName;

    /**
     * 申请院校id
     */
    @Column(name = "college_no")
    private Integer collegeNo;

    /**
     * 申请院校类别
     */
    private Integer collegeType;

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
     * 课程名称
     */
    @Column(name = "course_name")
    private String courseName;

    /**
     * 课程id
     */
    @Column(name = "course_id")
    private Integer courseId;

    /**
     * 意向校区
     */
    @Column(name = "school_area")
    private String schoolArea;

    /**
     * 学制
     */
    @Column(name = "school_length")
    private String schoolLength;

    /**
     * 小专业方向
     */
    @Column(name = "major_detail")
    private String majorDetail;

    /**
     * 专业链接
     */
    @Column(name = "major_url")
    private String majorUrl;

    /**
     * 申请截止日期
     */
    @Column(name = "apply_deadline")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date applyDeadline;

    /**
     * 申请截止日期备注
     */
    @Column(name = "apply_deadline_remark")
    private String applyDeadlineRemark;

    /**
     * 开课时间
     */
    @Column(name = "course_start_time")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date courseStartTime;

    /**
     * 定校备注
     */
    @Column(name = "plan_comment")
    private String planComment;

    /**
     * 是否申请减免学分
     */
    @Column(name = "reduce_credit_status")
    private Boolean reduceCreditStatus;

    /**
     * 审核状态
     */
    @Column(name = "audit_status")
    private Integer auditStatus;

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
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
     * 关联定校方案信息表id
     */
    @Column(name = "plan_id")
    private Integer planId;

    /**
     * 定校方案编号
     */
    private String planNo;

    /**
     * 学生确认状态 0-未确认 1-待确认 2-已确认
     */
    @Column(name = "student_confirm_status")
    private Integer studentConfirmStatus;

    /**
     * 专业教育学段
     */
    @Column(name = "educationLevel")
    private Integer educationLevel;

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
     * 定校国家
     */
    @Column(name = "college_country_id")
    private Integer collegeCountryId;

    /**
     * 课程备注
     */
    private Integer courseRemark;

    /**
     * 学生确认文件
     */
    private String confirmFile;

    /**
     * 是否小希院校库数据 0否  1是
     */
    private Integer xxType;

    public Integer getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(Integer educationLevel) {
        this.educationLevel = educationLevel;
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

    public Integer getStudentConfirmStatus() {
        return studentConfirmStatus;
    }

    public void setStudentConfirmStatus(Integer studentConfirmStatus) {
        this.studentConfirmStatus = studentConfirmStatus;
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

    /**
     * 获取申请院校id
     *
     * @return college_no - 申请院校id
     */
    public Integer getCollegeNo() {
        return collegeNo;
    }

    /**
     * 设置申请院校id
     *
     * @param collegeNo 申请院校id
     */
    public void setCollegeNo(Integer collegeNo) {
        this.collegeNo = collegeNo;
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
     * 获取意向校区
     *
     * @return school_area - 意向校区
     */
    public String getSchoolArea() {
        return schoolArea;
    }

    /**
     * 设置意向校区
     *
     * @param schoolArea 意向校区
     */
    public void setSchoolArea(String schoolArea) {
        this.schoolArea = schoolArea;
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
     * 获取小专业方向
     *
     * @return major_detail - 小专业方向
     */
    public String getMajorDetail() {
        return majorDetail;
    }

    /**
     * 设置小专业方向
     *
     * @param majorDetail 小专业方向
     */
    public void setMajorDetail(String majorDetail) {
        this.majorDetail = majorDetail;
    }

    /**
     * 获取专业链接
     *
     * @return major_url - 专业链接
     */
    public String getMajorUrl() {
        return majorUrl;
    }

    /**
     * 设置专业链接
     *
     * @param majorUrl 专业链接
     */
    public void setMajorUrl(String majorUrl) {
        this.majorUrl = majorUrl;
    }

    /**
     * 获取申请截止日期
     *
     * @return apply_deadline - 申请截止日期
     */
    public Date getApplyDeadline() {
        return applyDeadline;
    }

    /**
     * 设置申请截止日期
     *
     * @param applyDeadline 申请截止日期
     */
    public void setApplyDeadline(Date applyDeadline) {
        this.applyDeadline = applyDeadline;
    }

    /**
     * 获取开课时间
     *
     * @return course_start_time - 开课时间
     */
    public Date getCourseStartTime() {
        return courseStartTime;
    }

    /**
     * 设置开课时间
     *
     * @param courseStartTime 开课时间
     */
    public void setCourseStartTime(Date courseStartTime) {
        this.courseStartTime = courseStartTime;
    }

    /**
     * 获取定校备注
     *
     * @return plan_comment - 定校备注
     */
    public String getPlanComment() {
        return planComment;
    }

    /**
     * 设置定校备注
     *
     * @param planComment 定校备注
     */
    public void setPlanComment(String planComment) {
        this.planComment = planComment;
    }

    /**
     * 获取是否申请减免学分
     *
     * @return reduce_credit_status - 是否申请减免学分
     */
    public Boolean getReduceCreditStatus() {
        return reduceCreditStatus;
    }

    /**
     * 设置是否申请减免学分
     *
     * @param reduceCreditStatus 是否申请减免学分
     */
    public void setReduceCreditStatus(Boolean reduceCreditStatus) {
        this.reduceCreditStatus = reduceCreditStatus;
    }

    /**
     * 获取审核状态
     *
     * @return audit_status - 审核状态
     */
    public Integer getAuditStatus() {
        return auditStatus;
    }

    /**
     * 设置审核状态
     *
     * @param auditStatus 审核状态
     */
    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
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
     * 获取关联定校方案信息表id
     *
     * @return plan_id - 关联定校方案信息表id
     */
    public Integer getPlanId() {
        return planId;
    }

    /**
     * 设置关联定校方案信息表id
     *
     * @param planId 关联定校方案信息表id
     */
    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getCollegeType() {
        return collegeType;
    }

    public void setCollegeType(Integer collegeType) {
        this.collegeType = collegeType;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Integer getCollegeCountryId() {
        return collegeCountryId;
    }

    public void setCollegeCountryId(Integer collegeCountryId) {
        this.collegeCountryId = collegeCountryId;
    }

    public Integer getCourseRemark() {
        return courseRemark;
    }

    public void setCourseRemark(Integer courseRemark) {
        this.courseRemark = courseRemark;
    }

    public String getConfirmFile() {
        return confirmFile;
    }

    public void setConfirmFile(String confirmFile) {
        this.confirmFile = confirmFile;
    }

    public Integer getXxType() {
        return xxType;
    }

    public void setXxType(Integer xxType) {
        this.xxType = xxType;
    }

    public String getApplyDeadlineRemark() {
        return applyDeadlineRemark;
    }

    public void setApplyDeadlineRemark(String applyDeadlineRemark) {
        this.applyDeadlineRemark = applyDeadlineRemark;
    }
}