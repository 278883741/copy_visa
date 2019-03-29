package com.aoji.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("佣金统计管理")
public class CommissionReportNewVO {

    @ApiModelProperty("姓名")
    String studentName;

    @ApiModelProperty("拼音")
    String spelling;

    @ApiModelProperty("澳际学号")
    private String studentNo;

    @ApiModelProperty("机构类型")
    String agentType;

    @ApiModelProperty("出生日期")
    Date birthday;

    @ApiModelProperty("留学国家")
    String nationName;

    @ApiModelProperty("获签日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    Date visaDate;

    @ApiModelProperty("签约日期")
    Date signDate;

    @ApiModelProperty("登记日期")
    Date coeDate;

    @ApiModelProperty("顾问分支")
    String branch;

    @ApiModelProperty("学生来源")
    String studentSource;

    @ApiModelProperty("合同类型")
    String contractType;

    @ApiModelProperty("代理类型")
    String agentName;

    @ApiModelProperty("咨询顾问")
    String consulter;

    @ApiModelProperty("转接顾问")
    String transferConsulter;

    @ApiModelProperty("文签顾问")
    String copyOperator;

    @ApiModelProperty("学生备注")
    String schoolRemark2;

    @ApiModelProperty("结佣归属")
    String commissionBelong;

    @ApiModelProperty("佣金备注")
    String schoolRemark;

    @ApiModelProperty("学校名称")
    String schoolName;

    @ApiModelProperty("上课校区")
    String schoolArea;

    @ApiModelProperty("学校学号")
    String schoolNo;

    @ApiModelProperty("院校类型")
    String collegeType;

    @ApiModelProperty("课程类型")
    String courseName;

    @ApiModelProperty("专业名称")
    String majorName;

    @ApiModelProperty("课程周数")
    String studyWeek;

    @ApiModelProperty("开课日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    Date startDate;

    @ApiModelProperty("学费")
    BigDecimal tuition;

    @ApiModelProperty("返佣比例")
    BigDecimal rate;

    @ApiModelProperty("INV—NO")
    private String invoiceNo;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty("发出日期")
    private Date sendDate;

    /**
     * 返佣币种
     */
    @ApiModelProperty("返佣币种")
    private String currency;

    @ApiModelProperty("INV金额")
    private BigDecimal invoiceMoney;

    @ApiModelProperty("INV-GST")
    private BigDecimal invoiceGst;

    @ApiModelProperty("INV税后总额")
    private BigDecimal invoiceSum;

    @ApiModelProperty("银行账户")
    private String bankAccount;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty("到账日期")
    private Date returnDate;

    @ApiModelProperty("返佣币种")
    private String returnCurrency;

    @ApiModelProperty("到账金额")
    private BigDecimal actualAmount;

    @ApiModelProperty("到账GST")
    private BigDecimal accountGst;

    @ApiModelProperty("到账税后总额")
    private BigDecimal accountSum;

    @ApiModelProperty("银行手续费")
    private BigDecimal bankFee;

    @ApiModelProperty("银行手续费币种")
    private String bankFeeCurrency;

    @ApiModelProperty("账户币种")
    private String accountCurrency;

    @ApiModelProperty("账户金额")
    private BigDecimal accountMoney;

    @ApiModelProperty("差额")
    private BigDecimal balance;

    @ApiModelProperty("差额原因")
    private String balanceType;

    @ApiModelProperty("学生属性")
    String studentProperty;

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

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
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

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Date getCoeDate() {
        return coeDate;
    }

    public void setCoeDate(Date coeDate) {
        this.coeDate = coeDate;
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

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
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

    public String getCommissionBelong() {
        return commissionBelong;
    }

    public void setCommissionBelong(String commissionBelong) {
        this.commissionBelong = commissionBelong;
    }

    public String getSchoolRemark() {
        return schoolRemark;
    }

    public void setSchoolRemark(String schoolRemark) {
        this.schoolRemark = schoolRemark;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolArea() {
        return schoolArea;
    }

    public void setSchoolArea(String schoolArea) {
        this.schoolArea = schoolArea;
    }

    public String getSchoolNo() {
        return schoolNo;
    }

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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnCurrency() {
        return returnCurrency;
    }

    public void setReturnCurrency(String returnCurrency) {
        this.returnCurrency = returnCurrency;
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

    public String getBankFeeCurrency() {
        return bankFeeCurrency;
    }

    public void setBankFeeCurrency(String bankFeeCurrency) {
        this.bankFeeCurrency = bankFeeCurrency;
    }

    public String getAccountCurrency() {
        return accountCurrency;
    }

    public void setAccountCurrency(String accountCurrency) {
        this.accountCurrency = accountCurrency;
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
}