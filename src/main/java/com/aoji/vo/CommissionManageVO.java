package com.aoji.vo;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wangjunqiang
 * @description
 * @date Created in 2018/6/14 11:30
 */
public class CommissionManageVO {

    /**
     * 学校表id
     */
    private Integer id;

    /**
     * 佣金学生表id
     */
    private Integer studentId;

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    /**
     * 学生学号
     */
    private String studentNo;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 学生拼音
     */
    private String pinyin;

    /**
     *出生日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;

    /**
     * 获签国家
     */
    private String nationName;
    /**
     * 录入日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 录入日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date visaDate;

    public Date getVisaDate() {
        return visaDate;
    }

    public void setVisaDate(Date visaDate) {
        this.visaDate = visaDate;
    }

    /**
     * 学校名称
     */
    private String schoolName;
    /**
     * 开学时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;


    private String birthday_string;

    public String getBirthday_string() {
        return birthday_string;
    }

    public void setBirthday_string(String birtyday_string) {
        this.birthday_string = birtyday_string;
    }

    /**
     * 开学时间
     */
    private String startDate_string;

    /**
     * 开学时间(查询条件:开学时间的结束时间)
     */
    private String endDate_string;

    /**
     * 录入时间
     */
    private String createTime_string;

    /**
     * 录入时间(查询录入时间的结束时间)
     */
    private String createTimeEnd_string;

    /**
     * 学校类型
     */
    private String collegeType;
    /**
     * 课程类型
     */
    private String courseName;
    /**
     * 上课校区
     */
    private String schoolArea;
    /**
     * 结佣归属
     */
    private String comissionBelong;
    /**
     * 结佣状态
     */
    private String status;
    /**
     * 课程备注
     */
    private String remarkOne;
    /**
     * 院校备注
     */
    private String remarkTwo;

    /**
     * 结佣备注
     */
    private String remarkThree;

    /***
     * 学校学号
     */
    private String schoolNo;

    /**
     * 签约日期
     */
    private Date signDate;

    /**
     *分支机构
     */
    private String branch;

    /**
     * 签约日期
     */
    private String signDate_string;

    /**
     * 学生来源
     */
    private String studentSource;

    /**
     * 代理推荐
     */
    private String agentType;


    /**
     * 文签顾问
     * @param copyOperator
     */
    private String copyOperator;
    /**
     * 咨询顾问
     * @param consulter
     */
    private String consulter;
    /**
     * 转接顾问
     * @param transferConsulter
     */
    private String transferConsulter;

    /**
     * 课程属性:1-新学位,2-转学.3-转专业,4-转课程,5-加读语言,6-填缝,7-延期,8-原打包,9-补课
     */
    private String courseProperty;

    /**
     * 专业名称
     */
    private String majorName;

    /**
     * 课程周数
     */
    private String studyWeek;
    /**
     * 登记日期
     */
    private Date coeDate;

    /**
     * 登记日期String
     * @param coeDateStartString
     */
    private String coeDateStartString;
    /**
     * 登记日期String
     * @param coeDateEndString
     */
    private String coeDateEndString;







    public void setCourseName(String courseName) {

        this.courseName = courseName;
    }

    /**
     * 合同类型
     */
    private String contractType;

    /**
     * 查询(录入时间的最后时间)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeEnd;

    /**
     * 查询(开学时间的最后时间)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    /**
     * 已付学费
     */
    private BigDecimal paidFee;

    /**
     * 学费
     */
    private BigDecimal tuition;

    /**
     * 佣金比例
     */
    private BigDecimal rate;

    /**
     * invoiceNo
     */
    private String invoiceNo;

    /**
     * Invoice发出日期
     */
    private Date sendDate;

    private String sendDate_string;

    /**
     * invoice金额
     */
    private BigDecimal invoiceMoney;

    /**
     * InvoiceGST
     */
    private BigDecimal invoiceGst;

    /**
     * Invoice税后金额

     */
    private BigDecimal invoiceSum;

    /**
     * 到账日期
     */
    private Date returnDate;

    /**
     * 银行账户
     */
    private String bankAccount;

    /**
     * 账户币种
     */
    private String accountCurrency;

    /**
     * 到账币种
     */
    private String returnCurrency;

    /**
     * invoice币种
     */
    private String currency;

//    /**
//     * 到账金额
//     */
//    private  BigDecimal currency;

    /**
     *到账金额
     */
    private BigDecimal actualAmount;

    /**
     * 到账GST
     */
    private BigDecimal accountGst;

    /**
     * 到账税后金额
     */
    private BigDecimal accountSum;

    /**
     * 银行手续费
     */
    private BigDecimal bankFee;

    /**
     * 账户金额
     */
    private BigDecimal accountMoney;

    /**
     * 差额
     */
    private BigDecimal balance;

    /**
     * 差额原因
     */
    private String balanceType;

    /**
     * 学生属性
     */
    private String studentProperty;

    private String returnDate_string;
    //合作机构编号
    private  Integer agencyNo;
    //合作机构名称
    private  String agencyName;

    public String getReturnDate_string() {
        return returnDate_string;
    }

    public void setReturnDate_string(String returnDate_string) {
        this.returnDate_string = returnDate_string;
    }

    public String getRemarkThree() {
        return remarkThree;
    }

    public void setRemarkThree(String remarkThree) {
        this.remarkThree = remarkThree;
    }

    public String getSchoolNo() {
        return schoolNo;
    }

    public void setSchoolNo(String schoolNo) {
        this.schoolNo = schoolNo;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSignDate_string() {
        return signDate_string;
    }

    public void setSignDate_string(String signDate_string) {
        this.signDate_string = signDate_string;
    }

    public String getStudentSource() {
        return studentSource;
    }

    public void setStudentSource(String studentSource) {
        this.studentSource = studentSource;
    }

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public String getCopyOperator() {
        return copyOperator;
    }

    public void setCopyOperator(String copyOperator) {
        this.copyOperator = copyOperator;
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

    public String getCourseProperty() {
        return courseProperty;
    }

    public void setCourseProperty(String courseProperty) {
        this.courseProperty = courseProperty;
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

    public BigDecimal getPaidFee() {
        return paidFee;
    }

    public void setPaidFee(BigDecimal paidFee) {
        this.paidFee = paidFee;
    }

    public BigDecimal getTuition() {
        return tuition;
    }

    public void setTuition(BigDecimal tuition) {
        this.tuition = tuition;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public String getSendDate_string() {
        return sendDate_string;
    }

    public void setSendDate_string(String sendDate_string) {
        this.sendDate_string = sendDate_string;
    }

    public BigDecimal getInvoiceMoney() {
        return invoiceMoney;
    }

    public void setInvoiceMoney(BigDecimal invoiceMoney) {
        this.invoiceMoney = invoiceMoney;
    }

    public BigDecimal getInvoiceGst() {
        return invoiceGst;
    }

    public void setInvoiceGst(BigDecimal invoiceGst) {
        this.invoiceGst = invoiceGst;
    }

    public BigDecimal getInvoiceSum() {
        return invoiceSum;
    }

    public void setInvoiceSum(BigDecimal invoiceSum) {
        this.invoiceSum = invoiceSum;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getAccountCurrency() {
        return accountCurrency;
    }

    public void setAccountCurrency(String accountCurrency) {
        this.accountCurrency = accountCurrency;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public BigDecimal getAccountGst() {
        return accountGst;
    }

    public void setAccountGst(BigDecimal accountGst) {
        this.accountGst = accountGst;
    }

    public BigDecimal getAccountSum() {
        return accountSum;
    }

    public void setAccountSum(BigDecimal accountSum) {
        this.accountSum = accountSum;
    }

    public BigDecimal getBankFee() {
        return bankFee;
    }

    public void setBankFee(BigDecimal bankFee) {
        this.bankFee = bankFee;
    }

    public BigDecimal getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(BigDecimal accountMoney) {
        this.accountMoney = accountMoney;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(String balanceType) {
        this.balanceType = balanceType;
    }

    public String getStudentProperty() {
        return studentProperty;
    }

    public void setStudentProperty(String studentProperty) {
        this.studentProperty = studentProperty;
    }

    public String getStartDate_string() {
        return startDate_string;
    }

    public void setStartDate_string(String startDate_string) {
        this.startDate_string = startDate_string;
    }

    public String getEndDate_string() {
        return endDate_string;
    }

    public void setEndDate_string(String endDate_string) {
        this.endDate_string = endDate_string;
    }

    public String getCreateTime_string() {
        return createTime_string;
    }

    public void setCreateTime_string(String createTime_string) {
        this.createTime_string = createTime_string;
    }

    public String getCreateTimeEnd_string() {
        return createTimeEnd_string;
    }

    public void setCreateTimeEnd_string(String createTimeEnd_string) {
        this.createTimeEnd_string = createTimeEnd_string;
    }

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
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

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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

    public void setCoursename(String courseName) {
        this.courseName = courseName;
    }

    public String getSchoolArea() {
        return schoolArea;
    }

    public void setSchoolArea(String schoolArea) {
        this.schoolArea = schoolArea;
    }

    public String getComissionBelong() {
        return comissionBelong;
    }

    public void setComissionBelong(String comissionBelong) {
        this.comissionBelong = comissionBelong;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarkOne() {
        return remarkOne;
    }

    public void setRemarkOne(String remarkOne) {
        this.remarkOne = remarkOne;
    }

    public String getRemarkTwo() {
        return remarkTwo;
    }

    public void setRemarkTwo(String remarkTwo) {
        this.remarkTwo = remarkTwo;
    }

    public String getReturnCurrency() {
        return returnCurrency;
    }

    public void setReturnCurrency(String returnCurrency) {
        this.returnCurrency = returnCurrency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public Date getCoeDate() {
        return coeDate;
    }

    public void setCoeDate(Date coeDate) {
        this.coeDate = coeDate;
    }

    public String getCoeDateStartString() {
        return coeDateStartString;
    }

    public void setCoeDateStartString(String coeDateStartString) {
        this.coeDateStartString = coeDateStartString;
    }

    public String getCoeDateEndString() {
        return coeDateEndString;
    }

    public void setCoeDateEndString(String coeDateEndString) {
        this.coeDateEndString = coeDateEndString;
    }
}
