package com.aoji.vo;

import com.aoji.model.CommissionInvoice;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class InvoiceListVO extends CommissionInvoice{

    private String studentId;

    /**
     * 生成的随机字符串，通过该字符串去commission_invoice_seq中获取勾选顺序
     */
    String tempKey;

    /**
     * 拼音
     */
    private String spelling;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;

    /**
     * 学校学号
     */
    private String schoolNo;

    /**
     * 获签国家
     */
    private String nationName;

    /**
     * 获签国家Id
     */
    private Integer nationId;

    /**
     * 学校类型
     */
    private String collegeType;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 专业名称
     */
    private String majorName;

    /**
     * 学费
     */
    private BigDecimal schoolTuition;

    /**
     * 学费币种
     */
    private String tuitionCurrency;

    /**
     * 节佣归属
     */
    private String commissionBelong;

    /**
     * 课程周数
     */
    private String studyWeek;

    /**
     * 开课日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /**
     * 需要添加的学校id
     */
    private String schoolIdStr;

    /**
     * InvoiceId拼接的字符串
     */
    private String invoiceIdStr;

    /**
     * 返佣比例
     */
    private BigDecimal schoolRate;

    /**
     * gst比例
     */
    private BigDecimal gstRate;

    /**
     * 结佣状态
     */
    private String status;

    /**
     * 佣金备注
     */
    String schoolRemark;

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

    public String getSchoolNo() {
        return schoolNo;
    }

    public void setSchoolNo(String schoolNo) {
        this.schoolNo = schoolNo;
    }

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
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

    public BigDecimal getSchoolTuition() {
        return schoolTuition;
    }

    public void setSchoolTuition(BigDecimal schoolTuition) {
        this.schoolTuition = schoolTuition;
    }

    public String getCommissionBelong() {
        return commissionBelong;
    }

    public void setCommissionBelong(String commissionBelong) {
        this.commissionBelong = commissionBelong;
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

    public String getSchoolIdStr() {
        return schoolIdStr;
    }

    public void setSchoolIdStr(String schoolIdStr) {
        this.schoolIdStr = schoolIdStr;
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

    public String getInvoiceIdStr() {
        return invoiceIdStr;
    }

    public void setInvoiceIdStr(String invoiceIdStr) {
        this.invoiceIdStr = invoiceIdStr;
    }

    public void setGstRate(BigDecimal gstRate) {
        this.gstRate = gstRate;
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

    public String getSchoolRemark() {
        return schoolRemark;
    }

    public void setSchoolRemark(String schoolRemark) {
        this.schoolRemark = schoolRemark;
    }

    public Integer getNationId() {
        return nationId;
    }

    public void setNationId(Integer nationId) {
        this.nationId = nationId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTempKey() {
        return tempKey;
    }

    public void setTempKey(String tempKey) {
        this.tempKey = tempKey;
    }
}
