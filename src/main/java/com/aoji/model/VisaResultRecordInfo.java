package com.aoji.model;

import com.aoji.model.res.School;
import com.aoji.model.res.SchoolData;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "visa_result_record_info")
public class VisaResultRecordInfo {
    @Column
    private Integer educationSection;
    public void setEducationSection(Integer educationSection) {
        this.educationSection = educationSection;
    }
    public Integer getEducationSection() {
        return educationSection;
    }

    @Transient
    public Integer counter;
    public void setI(Integer counter) {
        this.counter = counter;
    }
    public Integer getI() {
        return counter;
    }

    @Transient
    public List<SchoolData> cooperationList;
    public void setCooperationList(List<SchoolData> cooperationList) {
        this.cooperationList = cooperationList;
    }
    public List<SchoolData> getCooperationList() {
        return cooperationList;
    }

    @Transient
    public List<SchoolData> courseList;
    public void setCourseList(List<SchoolData> courseList) {
        this.courseList = courseList;
    }
    public List<SchoolData> getCourseList() {
        return courseList;
    }
    @Transient
    public List<SchoolData> majorList;
    public void setMajorList(List<SchoolData> majorList) {
        this.majorList = majorList;
    }
    public List<SchoolData> getMajorList() {
        return majorList;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 院校名称
     */
    @Column(name = "college_name")
    private String collegeName;


    /**
     * 课程名称
     */
    @Column(name = "course_name")
    private String courseName;

    /**
     * 专业名称
     */
    @Column(name = "major_name")
    private String majorName;

    /**
     * 所在校区
     */
    @Column(name = "school_area")
    private String schoolArea;

    /**
     * 院校学生号
     */
    @Column(name = "student_no")
    private String studentNo;

    /**
     * 课程类型：1-语言 2-预备 3-主课
     */
    @Column(name = "course_type")
    private Integer courseType;

    /**
     * 学制
     */
    @Column(name = "school_length")
    private String schoolLength;

    /**
     * 课程长度，周数
     */
    @Column(name = "course_length")
    private String courseLength;

    /**
     * 开课日期
     */
    @Column(name = "course_start_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date courseStartTime;

    @Column(name = "course_end_time")
    private String courseEndTime;
    public void setCourseEndTime(String courseEndTime) {
        this.courseEndTime = courseEndTime;
    }
    public String getCourseEndTime() {
        return courseEndTime;
    }

    @Transient
    private Date courseEndTimeDate;
    public void setCourseEndTimeDate(Date courseEndTimeDate) {
        this.courseEndTimeDate = courseEndTimeDate;
    }
    public Date getCourseEndTimeDate() {
        return courseEndTimeDate;
    }
    /**
     * 学费
     */
    private BigDecimal tuition;

    private BigDecimal prepaidTuition;
    public void setPrepaidTuition(BigDecimal prepaidTuition) {
        this.prepaidTuition = prepaidTuition;
    }
    public BigDecimal getPrepaidTuition() {
        return prepaidTuition;
    }

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 删除时间/失效时间
     */
    @Column(name = "delete_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
     * 院校id
     */
    @Column(name = "college_id")
    private Integer collegeId;

    /**
     * 专业id
     */
    @Column(name = "major_id")
    private Integer majorId;

    /**
     * 课程id
     */
    @Column(name = "course_id")
    private Integer courseId;

    /**
     * 获签信息表id
     */
    @Column(name = "visa_record_id")
    private Integer visaRecordId;

    private Integer currency;

    /**
     * 合作机构id
     */
    @Column(name = "cooperation_id")
    private Integer cooperationId;


    /**
     * 合作机构名称
     */
    @Column(name = "cooperation_name")
    private String cooperationName;



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
     * 获取院校名称
     *
     * @return college_name - 院校名称
     */
    public String getCollegeName() {
        return collegeName;
    }

    /**
     * 设置院校名称
     *
     * @param collegeName 院校名称
     */
    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
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
     * 获取专业名称
     *
     * @return major_name - 专业名称
     */
    public String getMajorName() {
        return majorName;
    }

    /**
     * 设置专业名称
     *
     * @param majorName 专业名称
     */
    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    /**
     * 获取所在校区
     *
     * @return school_area - 所在校区
     */
    public String getSchoolArea() {
        return schoolArea;
    }

    /**
     * 设置所在校区
     *
     * @param schoolArea 所在校区
     */
    public void setSchoolArea(String schoolArea) {
        this.schoolArea = schoolArea;
    }

    /**
     * 获取院校学生号
     *
     * @return student_no - 院校学生号
     */
    public String getStudentNo() {
        return studentNo;
    }

    /**
     * 设置院校学生号
     *
     * @param studentNo 院校学生号
     */
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
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
     * 获取课程长度，周数
     *
     * @return course_length - 课程长度，周数
     */
    public String getCourseLength() {
        return courseLength;
    }

    /**
     * 设置课程长度，周数
     *
     * @param courseLength 课程长度，周数
     */
    public void setCourseLength(String courseLength) {
        this.courseLength = courseLength;
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
     * 获取院校id
     *
     * @return college_id - 院校id
     */
    public Integer getCollegeId() {
        return collegeId;
    }

    /**
     * 设置院校id
     *
     * @param collegeId 院校id
     */
    public void setCollegeId(Integer collegeId) {
        this.collegeId = collegeId;
    }

    /**
     * 获取专业id
     *
     * @return major_id - 专业id
     */
    public Integer getMajorId() {
        return majorId;
    }

    /**
     * 设置专业id
     *
     * @param majorId 专业id
     */
    public void setMajorId(Integer majorId) {
        this.majorId = majorId;
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
     * 获取获签信息表id
     *
     * @return visa_record_id - 获签信息表id
     */
    public Integer getVisaRecordId() {
        return visaRecordId;
    }

    /**
     * 设置获签信息表id
     *
     * @param visaRecordId 获签信息表id
     */
    public void setVisaRecordId(Integer visaRecordId) {
        this.visaRecordId = visaRecordId;
    }

    /**
     * @return currency
     */
    public Integer getCurrency() {
        return currency;
    }

    /**
     * @param currency
     */
    public void setCurrency(Integer currency) {
        this.currency = currency;
    }


    public Integer getCooperationId() {
        return cooperationId;
    }

    public void setCooperationId(Integer cooperationId) {
        this.cooperationId = cooperationId;
    }

    public String getCooperationName() {
        return cooperationName;
    }

    public void setCooperationName(String cooperationName) {
        this.cooperationName = cooperationName;
    }
}