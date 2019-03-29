package com.aoji.vo;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * @author wangjunqiang
 * @description
 * @date Created in 2018/6/22 10:05
 */
public class SchoolInvoiceVO {

    /**
     * school表 id
     */
    private Integer id;

    /**
     * invoiceId
     */
    private Integer invoiceId;

    /**
     * 学号
     */
    private String studentNo;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 到账金额
     */
    private BigDecimal actualAmount;

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    /**
     * 学校类型
     */
    private String collegeType;

    /**
     * 开课日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    /**
     * 结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    /**
     * 结佣备注
     */
    private String remark;

    /**
     * gst比例
     */
    private BigDecimal gstRate;

    /**
     * 结佣状态
     */
    private String status;



    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 专业名称
     */
    private String majorName;

    /**
     * 关联commission_school表id
     */
    private Integer schoolId;


    /**
     * invoice编号
     */
    private String invoiceNo;

    /**
     * 到账日期
     */
    private Date returnDate;

    /**
     * invoice税后总额
     */
    private BigDecimal invoiceSum;

    /**
     * 账户总额
     */
    private BigDecimal accountSum;

    /**
     * 到账GST
     */
    private BigDecimal accountGst;

    /**
     * 账户金额
     */
    private BigDecimal accountMoney;

    /**
     * 结佣备注
     */
    private String schoolRemark;


    /**
     * 合作机构编号
     * @return
     */
    private Integer agencyNo;

    /**
     * 合作机构名称
     * @return
     */
    private String agencyName;

    /**
     * 付款状态
     * @return
     */
    private String channelReturnStatus;

    /**
     * 付款时间
     * @return
     */
    private Date paymentDate;

    public String getSchoolRemark() {
        return schoolRemark;
    }

    public void setSchoolRemark(String schoolRemark) {
        this.schoolRemark = schoolRemark;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getCollegeType() {
        return collegeType;
    }

    public void setCollegeType(String collegeType) {
        this.collegeType = collegeType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public BigDecimal getGstRate() {
        return gstRate;
    }

    public void setGstRate(BigDecimal gstRate) {
        this.gstRate = gstRate;
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

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public BigDecimal getInvoiceSum() {
        return invoiceSum;
    }

    public void setInvoiceSum(BigDecimal invoiceSum) {
        this.invoiceSum = invoiceSum;
    }

    public BigDecimal getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(BigDecimal accountMoney) {
        this.accountMoney = accountMoney;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getAccountSum() {
        return accountSum;
    }

    public void setAccountSum(BigDecimal accountSum) {
        this.accountSum = accountSum;
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

    public BigDecimal getAccountGst() {
        return accountGst == null ? accountGst : accountGst.setScale(2, RoundingMode.HALF_UP);
    }

    public void setAccountGst(BigDecimal accountGst) {
        this.accountGst = accountGst;
    }

    public String getChannelReturnStatus() {
        return channelReturnStatus;
    }

    public void setChannelReturnStatus(String channelReturnStatus) {
        this.channelReturnStatus = channelReturnStatus;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
}
