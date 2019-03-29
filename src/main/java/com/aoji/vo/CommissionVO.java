package com.aoji.vo;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wangjunqiang
 * @description
 * @date Created in 2018/6/19 16:11
 */
public class CommissionVO {


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
     * 出生日期
     */
    private String birthday;

    /**
     * 登记日期
     */
    private String visaDate;

    /**
     * 合同类型
     */
    private String contractType;

    /**
     * 代理推荐
     */
    private String agentRecommend;

    /**
     * 代理名称
     */
    private String agentName;

    /**
     * 国家
     */
    private String nationName;

    /**
     * 学生来源
     */
    private String studentSource;


    /**
     * 备注1
     */
    private String StudentRemark;


    /**
     * 澳洲代理
     */
    private String ausAgent;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 专业名称
     */
    private String magirName;

    /**
     * 课程类型
     */
    private String courseType;

    /**
     * 上课校区
     */
    private String schoolArea;

    /**
     * 学校类型
     */
    private String collegeType;

    /**
     * 开课时间
     */
    private String startDate;

    /**
     * 结课时间
     */
    private String endDate;

    /**
     * 学校学号
     */
    private String schoolNo;

    /**
     * 课程周数
     */
    private Long studyWeek;

    /**
     * 学费
     */
    private Long tuition;

    /**
     * 学生属性
     */
    private String studentProperty;

    /**
     * 课程属性
     */
    private String courseProperty;

    /**
     * 结佣归属
     */
    private String commissionBelong;


    /**
     * 佣金比例
     */
    private Long schoolRate;

    /**
     * 佣金币种
     */
    private String commissionCurrency;

    /**
     * 佣金金额
     */
    private BigDecimal commissionMoney;

    /**
     * 已付学费币种
     */



    /**
     * 学费币种
     */
    private String tuitionCurrency;


    /**
     * 结佣状态
     */
    private String status;


    /**
     *文签顾问
     */
    private String copyOperator;


    /**
     * 顾问分支
     */
    private String consulterBranch;

    /**
     * 顾问
     */
    private String consulter;

    /**
     * 转接顾问
     */
    private String transferConsulter;

    /**
     * 备注1
     */
    private String studentRemark;


    /**
     *备注2
     */
    private String schoolRemark;

    /**
     * offer附件
     */
    private String offerFile;

    /**
     * coe附件
     */
    private String coeFile;

    /**
     * 邮件附件
     */
    private String emailFile;


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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getVisaDate() {
        return visaDate;
    }

    public void setVisaDate(String visaDate) {
        this.visaDate = visaDate;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getAgentRecommend() {
        return agentRecommend;
    }

    public void setAgentRecommend(String agentRecommend) {
        this.agentRecommend = agentRecommend;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    public String getStudentSource() {
        return studentSource;
    }

    public void setStudentSource(String studentSource) {
        this.studentSource = studentSource;
    }

    public String getStudentRemark() {
        return StudentRemark;
    }

    public void setStudentRemark(String studentRemark) {
        StudentRemark = studentRemark;
    }

    public String getSchoolRemark() {
        return schoolRemark;
    }

    public void setSchoolRemark(String schoolRemark) {
        this.schoolRemark = schoolRemark;
    }

    public String getOfferFile() {
        return offerFile;
    }

    public void setOfferFile(String offerFile) {
        this.offerFile = offerFile;
    }

    public String getCoeFile() {
        return coeFile;
    }

    public void setCoeFile(String coeFile) {
        this.coeFile = coeFile;
    }

    public String getEmailFile() {
        return emailFile;
    }

    public void setEmailFile(String emailFile) {
        this.emailFile = emailFile;
    }

    public String getAusAgent() {
        return ausAgent;
    }

    public void setAusAgent(String ausAgent) {
        this.ausAgent = ausAgent;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getMagirName() {
        return magirName;
    }

    public void setMagirName(String magirName) {
        this.magirName = magirName;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getSchoolArea() {
        return schoolArea;
    }

    public void setSchoolArea(String schoolArea) {
        this.schoolArea = schoolArea;
    }

    public String getCollegeType() {
        return collegeType;
    }

    public void setCollegeType(String collegeType) {
        this.collegeType = collegeType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSchoolNo() {
        return schoolNo;
    }

    public void setSchoolNo(String schoolNo) {
        this.schoolNo = schoolNo;
    }

    public Long getStudyWeek() {
        return studyWeek;
    }

    public void setStudyWeek(Long studyWeek) {
        this.studyWeek = studyWeek;
    }

    public Long getTuition() {
        return tuition;
    }

    public void setTuition(Long tuition) {
        this.tuition = tuition;
    }

    public String getStudentProperty() {
        return studentProperty;
    }

    public void setStudentProperty(String studentProperty) {
        this.studentProperty = studentProperty;
    }

    public String getCourseProperty() {
        return courseProperty;
    }

    public void setCourseProperty(String courseProperty) {
        this.courseProperty = courseProperty;
    }

    public String getCommissionBelong() {
        return commissionBelong;
    }

    public void setCommissionBelong(String commissionBelong) {
        this.commissionBelong = commissionBelong;
    }

    public Long getSchoolRate() {
        return schoolRate;
    }

    public void setSchoolRate(Long schoolRate) {
        this.schoolRate = schoolRate;
    }

    public String getCommissionCurrency() {
        return commissionCurrency;
    }

    public void setCommissionCurrency(String commissionCurrency) {
        this.commissionCurrency = commissionCurrency;
    }

    public BigDecimal getCommissionMoney() {
        return commissionMoney;
    }

    public void setCommissionMoney(BigDecimal commissionMoney) {
        this.commissionMoney = commissionMoney;
    }

    public String getTuitionCurrency() {
        return tuitionCurrency;
    }

    public void setTuitionCurrency(String tuitionCurrency) {
        this.tuitionCurrency = tuitionCurrency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyOperator() {
        return copyOperator;
    }

    public void setCopyOperator(String copyOperator) {
        this.copyOperator = copyOperator;
    }

    public String getConsulterBranch() {
        return consulterBranch;
    }

    public void setConsulterBranch(String consulterBranch) {
        this.consulterBranch = consulterBranch;
    }

    public String getConsulter() {
        return consulter;
    }

    public void setConsulter(String consulter) {
        this.consulter = consulter;
    }

    public String getTransferConsulter() {
        return transferConsulter;
    }

    public void setTransferConsulter(String transferConsulter) {
        this.transferConsulter = transferConsulter;
    }
}
