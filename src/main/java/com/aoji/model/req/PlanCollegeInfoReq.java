package com.aoji.model.req;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * author: chenhaibo
 * description: 操作定校方案请求参数
 * date: 2018/9/5
 */
public class PlanCollegeInfoReq {

    @ApiModelProperty(value = "定校Id")
    private Integer planId;
    @ApiModelProperty(value = "定校单元Id")
    private Integer collegeId;

//    @ApiModelProperty(value = "平台顾问添加")
//    private boolean platformConsultant;

    /**
     * 内网学号
     */
    @ApiModelProperty(value = "内网学号")
    private String studentNo;

    /**
     * 留学国家
     */
    @ApiModelProperty(value = "留学国家")
    private Integer nation;

    /**
     * 申请类别：0-直录 1-双录 2-纯语言
     */
    @ApiModelProperty(value = "申请类别：0-直录 1-双录 2-纯语言")
    private Integer applyType;

    /**
     * 合作机构
     */
//    @ApiModelProperty(value = "合作机构")
//    private Integer agency;

    /**
     * 是否加申
     */
    @ApiModelProperty(value = "是否加申")
    private Boolean addStatus;

    /**
     * 申请院校名称
     */
    @ApiModelProperty(value = "申请院校名称")
    private String collegeName;

    /**
     * 申请院校id
     */
    @ApiModelProperty(value = "申请院校id")
    private Integer collegeNo;

    /**
     * 申请院校类别
     */
    @ApiModelProperty(value = "申请院校类别")
    private Integer collegeType;

    /**
     * 申请专业名称
     */
    @ApiModelProperty(value = "申请专业名称")
    private String majorName;

    /**
     * 申请专业id
     */
    @ApiModelProperty(value = "申请专业id")
    private Integer majorId;

    /**
     * 课程名称
     */
    @ApiModelProperty(value = "课程名称")
    private String courseName;

    /**
     * 课程id
     */
    @ApiModelProperty(value = "课程id")
    private Integer courseId;

    /**
     * 意向校区
     */
    @ApiModelProperty(value = "意向校区")
    private String schoolArea;

    /**
     * 学制
     */
    @ApiModelProperty(value = "学制")
    private String schoolLength;

    /**
     * 小专业方向
     */
    @ApiModelProperty(value = "小专业方向")
    private String majorDetail;

    /**
     * 专业链接
     */
    @ApiModelProperty(value = "专业链接")
    private String majorUrl;

    /**
     * 申请截止日期
     */
    @ApiModelProperty(value = "申请截止日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date applyDeadline;

    @ApiModelProperty(value = "申请截止日期备注")
    private String applyDeadlineRemark;

    /**
     * 开课时间
     */
    @ApiModelProperty(value = "开课时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date courseStartTime;

    /**
     * 定校备注
     */
    @ApiModelProperty(value = "定校备注")
    private String planComment;

    /**
     * 是否申请减免学分
     */
    @ApiModelProperty(value = "是否申请减免学分")
    private Boolean reduceCreditStatus;

    /**
     * 审核状态
     */
    @ApiModelProperty(value = "审核状态")
    private Integer auditStatus;

    /**
     * 审核备注
     */
    @ApiModelProperty(value = "审核备注")
    private String auditRemark;

    /**
     * 删除状态0为未删除/可用，1为已删除/不可用
     */
    @ApiModelProperty(value = "删除状态")
    private Boolean deleteStatus;

    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人工号")
    private String operatorNo;

    /**
     * 操作人
     */
//    @ApiModelProperty(value = "操作人会员Id")
//    private String memberId;

    /**
     * 操作人姓名
     */
    @ApiModelProperty(value = "操作人姓名")
    private String operatorName;

    /**
     * 定校方案编号
     */
    @ApiModelProperty(value = "定校方案编号")
    private String planNo;

    /**
     * 专业教育学段
     */
    @ApiModelProperty(value = "专业教育学段")
    private Integer educationLevel;

    /**
     * 合作机构名称
     */
    @ApiModelProperty(value = "合作机构名称")
    private String agencyName;

    /**
     * 合作机构编号
     */
    @ApiModelProperty(value = "合作机构编号")
    private Integer agencyNo;

    /**
     * 定校国家
     */
    @ApiModelProperty(value = "定校国家")
    private Integer collegeCountryId;

    /**
     * 课程备注
     */
    @ApiModelProperty(value = "课程备注")
    private Integer courseRemark;

    /**
     * 学生确认文件
     */
    @ApiModelProperty(value = "学生确认文件")
    private String confirmFile;

    /**
     * 是否小希院校库数据 0否  1是
     */
    @ApiModelProperty(value = "是否小希院校库数据 0否  1是")
    private Integer xxType;

    @Override
    public String toString() {
        return "PlanCollegeInfoReq{" +
                "planId=" + planId +
                ", collegeId=" + collegeId +
                ", studentNo='" + studentNo + '\'' +
                ", nation=" + nation +
                ", applyType=" + applyType +
                ", addStatus=" + addStatus +
                ", collegeName='" + collegeName + '\'' +
                ", collegeNo=" + collegeNo +
                ", collegeType=" + collegeType +
                ", majorName='" + majorName + '\'' +
                ", majorId=" + majorId +
                ", courseName='" + courseName + '\'' +
                ", courseId=" + courseId +
                ", schoolArea='" + schoolArea + '\'' +
                ", schoolLength='" + schoolLength + '\'' +
                ", majorDetail='" + majorDetail + '\'' +
                ", majorUrl='" + majorUrl + '\'' +
                ", applyDeadline=" + applyDeadline +
                ", applyDeadlineRemark='" + applyDeadlineRemark + '\'' +
                ", courseStartTime=" + courseStartTime +
                ", planComment='" + planComment + '\'' +
                ", reduceCreditStatus=" + reduceCreditStatus +
                ", auditStatus=" + auditStatus +
                ", auditRemark='" + auditRemark + '\'' +
                ", deleteStatus=" + deleteStatus +
                ", operatorNo='" + operatorNo + '\'' +
                ", operatorName='" + operatorName + '\'' +
                ", planNo='" + planNo + '\'' +
                ", educationLevel=" + educationLevel +
                ", agencyName='" + agencyName + '\'' +
                ", agencyNo=" + agencyNo +
                ", collegeCountryId=" + collegeCountryId +
                ", courseRemark=" + courseRemark +
                ", confirmFile='" + confirmFile + '\'' +
                ", xxType=" + xxType +
                '}';
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public Integer getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Integer collegeId) {
        this.collegeId = collegeId;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public Integer getNation() {
        return nation;
    }

    public void setNation(Integer nation) {
        this.nation = nation;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

//    public Integer getAgency() {
//        return agency;
//    }
//
//    public void setAgency(Integer agency) {
//        this.agency = agency;
//    }

    public Boolean getAddStatus() {
        return addStatus;
    }

    public void setAddStatus(Boolean addStatus) {
        this.addStatus = addStatus;
    }

    public Integer getCollegeType() {
        return collegeType;
    }

    public void setCollegeType(Integer collegeType) {
        this.collegeType = collegeType;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

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

    public String getAuditRemark() {
        return auditRemark;
    }

    public void setAuditRemark(String auditRemark) {
        this.auditRemark = auditRemark;
    }

    public String getApplyDeadlineRemark() {
        return applyDeadlineRemark;
    }

    public void setApplyDeadlineRemark(String applyDeadlineRemark) {
        this.applyDeadlineRemark = applyDeadlineRemark;
    }
}