package com.aoji.vo;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wangjunqiang
 * @description
 * @date Created in 2018/7/6 16:55
 */
public class CommissionManageSaveVO {


    /**
     * 学校学号
     */
    private String schoolNo;

    /**
     * 代理名称
     */
    private  String agentName;

    /**
     * 上课周数
     */
    private String courseLength;

    public String getCourseLength() {
        return courseLength;
    }

    public void setCourseLength(String courseLength) {
        this.courseLength = courseLength;
    }

    public String getSchoolNo() {
        return schoolNo;
    }

    public void setSchoolNo(String schoolNo) {
        this.schoolNo = schoolNo;
    }

    /**
     * 已付学费
     */
    private BigDecimal prepaidTuition;
    /**
     * 获签表id
     */
    private Integer visaRecordId;

    /**
     * 获签信息院校表
     */
    private  Integer visaRecordResultId;

    /**
     * 专业教育学段 2-中小学,3-本科,4-硕士,5-博士,9-专科,10-非学历,11-证书,12-副学士
     */
    private Integer educationSection;

    /**
     * 学号
     */
    private String studentNo;

    /**
     * 姓名
     */
    private String studentName;

    /**
     * 拼音
     */
    private String pinyin;

    /**
     * 生日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;

    /**
     * 合同类型
     */
    private String contractType;

    /**
     * 国家id
     */
    private Integer nationId;

    /**
     * 国家名称
     */
    private String nationName;

    /**
     * 分支结构名称
     */
    private String branchName;

    /**
     * 获签创建日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date visaCreateTime;

    /**
     * 代理类型
     */
    private String agentType;

    /**
     * 学生创建日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date studentCreateTime;

    /**
     * 服务状态
     */
    private String status;

    /**
     * 获签id
     */
    private Integer visaId;

    /**
     * 院校名称
     */
    private String collegeName;

    /**
     * 院校id
     */
    private String collegeId;

    /**
     * 校区
     */
    private String schoolArea;

    /**
     * 开课日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date courseStartTime;

    /**
     * 结课日期
     */
    private String courseEndTime;

    /**
     * 上课周数
     */
    private String studyWeek;

    /**
     * 课程id
     */
    private int courseId;

    /**
     * 学校类型
     */
    private String collegeType;

    /**
     * 课程类型
     */
    private String courseType;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 专业名称
     */
    private String majorName;

    /**
     * 专业id
     */
    private String majorId;

    /**
     * 学费
     */
    private BigDecimal tuition;

    /**
     * 学费/已付学费币种
     */
    private String currency;

    /**
     * 学校录入日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date schoolCreateTime;

    /**
     * 学生签约日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date signDate;

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    /**
     * 文签顾问
     * @return
     */
    private String copyOperator;

    /**
     * 咨询顾问
     */
    private String salesConsultant;

    /**
     * 分支机构id
     */
    private String brachId;

    /**
     * 学生备注
     */
    private String visaRemark;


    /**
     * 合作机构id
     */
    private Integer cooperationId;


    /**
     * 合作机构名称
     */
    private String cooperationName;

    public String getVisaRemark() {
        return visaRemark;
    }

    public void setVisaRemark(String visaRemark) {
        this.visaRemark = visaRemark;
    }

    public String getBrachId() {
        return brachId;
    }

    public void setBrachId(String brachId) {
        this.brachId = brachId;
    }

    public String getSalesConsultant() {
        return salesConsultant;
    }

    public void setSalesConsultant(String salesConsultant) {
        this.salesConsultant = salesConsultant;
    }

    public String getCopyOperator() {
        return copyOperator;
    }

    public void setCopyOperator(String copyOperator) {
        this.copyOperator = copyOperator;
    }

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public Integer getNationId() {
        return nationId;
    }

    public void setNationId(Integer nationId) {
        this.nationId = nationId;
    }

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Date getVisaCreateTime() {
        return visaCreateTime;
    }

    public void setVisaCreateTime(Date visaCreateTime) {
        this.visaCreateTime = visaCreateTime;
    }

    public Date getStudentCreateTime() {
        return studentCreateTime;
    }

    public void setStudentCreateTime(Date studentCreateTime) {
        this.studentCreateTime = studentCreateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getVisaId() {
        return visaId;
    }

    public void setVisaId(Integer visaId) {
        this.visaId = visaId;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId;
    }

    public String getSchoolArea() {
        return schoolArea;
    }

    public void setSchoolArea(String schoolArea) {
        this.schoolArea = schoolArea;
    }

    public Date getCourseStartTime() {
        return courseStartTime;
    }

    public void setCourseStartTime(Date courseStartTime) {
        this.courseStartTime = courseStartTime;
    }

    public String getCourseEndTime() {
        return courseEndTime;
    }

    public void setCourseEndTime(String courseEndTime) {
        this.courseEndTime = courseEndTime;
    }

    public String getStudyWeek() {
        return studyWeek;
    }

    public void setStudyWeek(String studyWeek) {
        this.studyWeek = studyWeek;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCollegeType() {
        return collegeType;
    }

    public void setCollegeType(String collegeType) {
        this.collegeType = collegeType;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
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

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }

    public BigDecimal getTuition() {
        return tuition;
    }

    public void setTuition(BigDecimal tuition) {
        this.tuition = tuition;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getSchoolCreateTime() {
        return schoolCreateTime;
    }

    public void setSchoolCreateTime(Date schoolCreateTime) {
        this.schoolCreateTime = schoolCreateTime;
    }

    public Integer getVisaRecordId() {
        return visaRecordId;
    }

    public void setVisaRecordId(Integer visaRecordId) {
        this.visaRecordId = visaRecordId;
    }

    public BigDecimal getPrepaidTuition() {
        return prepaidTuition;
    }

    public void setPrepaidTuition(BigDecimal prepaidTuition) {
        this.prepaidTuition = prepaidTuition;
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

    public Integer getVisaRecordResultId() {
        return visaRecordResultId;
    }

    public void setVisaRecordResultId(Integer visaRecordResultId) {
        this.visaRecordResultId = visaRecordResultId;
    }

    public Integer getEducationSection() {
        return educationSection;
    }

    public void setEducationSection(Integer educationSection) {
        this.educationSection = educationSection;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }
}
