package com.aoji.vo;

import com.aoji.model.CommissionInvoice;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class CommissionReportVO extends CommissionInvoice{

    /**
     * 姓名
     */
    String studentName;

    /**
     * 拼音
     */
    String spelling;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    Date birthday;

    /**
     * 出生日期字符串类型
     */
    String birthdayStr;

    /**
     * 留学国家
     */
    String nationName;

    /**
     * 获签日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    Date visaDate;

    /**
     * 获签日期字符串类型
     */
    String visaDateStr;

    /**
     * 代理名称
     */
    String agentName;

    /**
     * 学费
     */
    BigDecimal tuition;

    /**
     * 学费币种
     */
    String tuitionCurrency;

    /**
     * 咨询顾问
     */
    String consulter;

    /**
     * 学校名称
     */
    String schoolName;

    /**
     * 学校学号
     */
    String schoolNo;

    /**
     * 课程名称
     */
    String courseName;

    /**
     * 专业名称
     */
    String majorName;

    /**
     * 上课周数
     */
    String studyWeek;

    /**
     * 开课日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    Date startDate;

    /**
     * 开课日期字符串类型
     */
    String startDateStr;

    /**
     * 校区
     */
    String schoolArea;

    /**
     * 结佣状态
     */
    String status;

    /**
     * 结佣归属
     */
    String commissionBelong;

    /**
     * 返佣比例
     */
    BigDecimal schoolRate;

    /**
     * gst比例
     */
    BigDecimal gstRate;

    /**
     * 佣金备注
     */
    String schoolRemark;

    /**
     * 院校类型
     */
    String collegeType;

    // ==================================================================================================================

    String agentType;
    Date signDate;
    Date coeDate;
    String signDateStr;
    String branch;
    String studentSource;
    String contractType;
    String transferConsulter;
    String copyOperator;
    String schoolRemark2;
    String studentProperty;


    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public String getSignDateStr() {
        return signDateStr;
    }

    public void setSignDateStr(String signDateStr) {
        this.signDateStr = signDateStr;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getStudentSource() {
        return studentSource;
    }

    public void setStudentSource(String studentSource) {
        this.studentSource = studentSource;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getTransferConsulter() {
        return transferConsulter;
    }

    public void setTransferConsulter(String transferConsulter) {
        this.transferConsulter = transferConsulter;
    }

    public String getCopyOperator() {
        return copyOperator;
    }

    public void setCopyOperator(String copyOperator) {
        this.copyOperator = copyOperator;
    }

    public String getSchoolRemark2() {
        return schoolRemark2;
    }

    public void setSchoolRemark2(String schoolRemark2) {
        this.schoolRemark2 = schoolRemark2;
    }

    public String getStudentProperty() {
        return studentProperty;
    }

    public void setStudentProperty(String studentProperty) {
        this.studentProperty = studentProperty;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSpelling() {
        return spelling;
    }

    public void setSpelling(String spelling) {
        this.spelling = spelling;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    public Date getVisaDate() {
        return visaDate;
    }

    public void setVisaDate(Date visaDate) {
        this.visaDate = visaDate;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public BigDecimal getTuition() {
        return tuition;
    }

    public void setTuition(BigDecimal tuition) {
        this.tuition = tuition;
    }

    public String getTuitionCurrency() {
        return tuitionCurrency;
    }

    public void setTuitionCurrency(String tuitionCurrency) {
        this.tuitionCurrency = tuitionCurrency;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolNo() {
        return schoolNo;
    }

    public void setSchoolNo(String schoolNo) {
        this.schoolNo = schoolNo;
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

    public String getStudyWeek() {
        return studyWeek;
    }

    public void setStudyWeek(String studyWeek) {
        this.studyWeek = studyWeek;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getSchoolArea() {
        return schoolArea;
    }

    public void setSchoolArea(String schoolArea) {
        this.schoolArea = schoolArea;
    }

    public String getStatus() {
        return status;
    }



    public String getConsulter() {
        return consulter;
    }

    public void setConsulter(String consulter) {
        this.consulter = consulter;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCommissionBelong() {
        return commissionBelong;
    }

    public void setCommissionBelong(String commissionBelong) {
        this.commissionBelong = commissionBelong;
    }

    public BigDecimal getSchoolRate() {
        return schoolRate;
    }

    public void setSchoolRate(BigDecimal schoolRate) {
        this.schoolRate = schoolRate;
    }

    public BigDecimal getGstRate() {
        return gstRate;
    }

    public void setGstRate(BigDecimal gstRate) {
        this.gstRate = gstRate;
    }

    public String getCollegeType() {
        return collegeType;
    }

    public void setCollegeType(String collegeType) {
        this.collegeType = collegeType;
    }

    public String getSchoolRemark() {
        return schoolRemark;
    }

    public void setSchoolRemark(String schoolRemark) {
        this.schoolRemark = schoolRemark;
    }

    public String getBirthdayStr() {
        return birthdayStr;
    }

    public void setBirthdayStr(String birthdayStr) {
        this.birthdayStr = birthdayStr;
    }

    public String getVisaDateStr() {
        return visaDateStr;
    }

    public void setVisaDateStr(String visaDateStr) {
        this.visaDateStr = visaDateStr;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public Date getCoeDate() {
        return coeDate;
    }

    public void setCoeDate(Date coeDate) {
        this.coeDate = coeDate;
    }
}
