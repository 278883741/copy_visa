package com.aoji.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.Expose;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Table(name = "commission_invoice")
public class CommissionInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 关联commission_school表id
     */

    @Column(name = "school_id")
    private Integer schoolId;

    /**
     * 澳际学号
     */
    @Transient
    private String studentNo;

    /**
     * invoice编号
     */
    @Column(name = "invoice_no")
    private String invoiceNo;

    /**
     * 发出日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "send_date")
    private Date sendDate;

    /**
     * 发出日期字符串类型
     */
    @Transient
    String sendDateStr;

    /**
     * 到账日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Column(name = "return_date")
    private Date returnDate;

    /**
     * 到账日期字符串类型
     */
    @Transient
    String returnDateStr;

    /**
     * 返佣币种
     */
    private String currency;

    /**
     * invoice金额
     */
    @Column(name = "invoice_money")
    private BigDecimal invoiceMoney;

    /**
     * GST金额
     */
    @Column(name = "invoice_gst")
    private BigDecimal invoiceGst;

    /**
     * invoice税后总额
     */
    @Column(name = "invoice_sum")
    private BigDecimal invoiceSum;

    /**
     * 到账金额
     */
    private BigDecimal actualAmount;

    /**
     * invoice备注
     */
    @Column(name = "invoice_remark")
    private String invoiceRemark;

    /**
     * 支付方式
     */
    @Column(name = "pay_method")
    private String payMethod;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 删除状态
     */
    @Column(name = "delete_status")
    private Boolean deleteStatus;

    /**
     * 账户金额
     */
    @Column(name = "account_money")
    private BigDecimal accountMoney;

    /**
     * 账户gst
     */
    @Column(name = "account_gst")
    private BigDecimal accountGst;

    /**
     * 账户总额
     */
    @Column(name = "account_sum")
    private BigDecimal accountSum;

    /**
     * 账户币种
     */
    @Expose
    @Column(name = "account_currency")
    private String accountCurrency;

    /**
     * 差额
     */
    private BigDecimal balance;

    /**
     * 差额原因
     */
    @Column(name = "balance_type")
    private String balanceType;

    /**
     * 银行手续费
     */
    @Column(name = "bank_fee")
    private BigDecimal bankFee;

    /**
     * 银行手续费币种
     */
    @Column(name = "bank_fee_currency")
    private String bankFeeCurrency;

    @Column(name = "exchange_rate")
    private BigDecimal exchangeRate;

    @Column(name = "line_num")
    private String lineNum;

    @Column(name = "invoice_data")
    private String invoiceData;

    /**
     * 数据来源
     */
    @Column(name = "data_source")
    private String dataSource;

    /**
     * invoice状态
     */
    @Column(name = "invoice_status")
    private String invoiceStatus;

    @Column(name = "is_state")
    private Integer isState;
    //本次返佣比例（时时计算出来）
    @Column(name = "percent_num")
    private Integer percentNum;

    @Column(name = "pay_time")
    private Date payTime;

    @Column(name = "state_time")
    private Date stateTime;

    @Column(name = "curr_rate")
    private BigDecimal currRate;

    @Column(name = "update_id")
    private Integer updateId;

    @Column(name = "update_date")
    private Date updateDate;

    /**
     * 银行流水号
     */
    @Column(name = "bank_serial_num")
    private String bankSerialNum;

    /**
     * 银行账户
     */
    @Column(name = "bank_account")
    private String bankAccount;

    /**
     * 返佣币种
     */
    @Column(name = "return_currency")
    private String returnCurrency;

    /**
     * 佣金比例
     */
    private BigDecimal rate;

    /**
     * gst比例
     */
    private BigDecimal invoiceGstRate;

    /**
     * 渠道返佣状态
     */
    private String channelReturnStatus;

    /**
     * 渠道返佣状态用于条件查询(已标识状态)
     */
    @Transient
    private List<String> channelReturnStatusIdentified;

    @Column(name = "reference_no")
    private String referenceNo;

    @Column(name = "state_time_two")
    private Date stateTimeTwo;


    private BigDecimal tuition;

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
     * 获取关联commission_school表id
     *
     * @return school_id - 关联commission_school表id
     */
    public Integer getSchoolId() {
        return schoolId;
    }

    /**
     * 设置关联commission_school表id
     *
     * @param schoolId 关联commission_school表id
     */
    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    /**
     * 获取澳际学号
     *
     * @return student_no - 澳际学号
     */
    public String getStudentNo() {
        return studentNo;
    }

    /**
     * 设置澳际学号
     *
     * @param studentNo 澳际学号
     */
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    /**
     * 获取invoice编号
     *
     * @return invoice_no - invoice编号
     */
    public String getInvoiceNo() {
        return invoiceNo;
    }

    /**
     * 设置invoice编号
     *
     * @param invoiceNo invoice编号
     */
    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    /**
     * 获取发出日期
     *
     * @return send_date - 发出日期
     */
    public Date getSendDate() {
        return sendDate;
    }

    /**
     * 设置发出日期
     *
     * @param sendDate 发出日期
     */
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    /**
     * 获取到账日期
     *
     * @return return_date - 到账日期
     */
    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * 设置到账日期
     *
     * @param returnDate 到账日期
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * 获取返佣币种
     *
     * @return currency - 返佣币种
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 设置返佣币种
     *
     * @param currency 返佣币种
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * 获取invoice金额
     *
     * @return invoice_money - invoice金额
     */
    public BigDecimal getInvoiceMoney() {
        return invoiceMoney;
    }

    /**
     * 设置invoice金额
     *
     * @param invoiceMoney invoice金额
     */
    public void setInvoiceMoney(BigDecimal invoiceMoney) {
        this.invoiceMoney = invoiceMoney;
    }

    /**
     * 获取GST金额
     *
     * @return invoice_gst - GST金额
     */
    public BigDecimal getInvoiceGst() {
        return invoiceGst == null ? invoiceGst : invoiceGst.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 设置GST金额
     *
     * @param invoiceGst GST金额
     */
    public void setInvoiceGst(BigDecimal invoiceGst) {
        this.invoiceGst = invoiceGst;
    }

    /**
     * 获取invoice税后总额
     *
     * @return invoice_sum - invoice税后总额
     */
    public BigDecimal getInvoiceSum() {
        return invoiceSum == null ? invoiceSum : invoiceSum.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 设置invoice税后总额
     *
     * @param invoiceSum invoice税后总额
     */
    public void setInvoiceSum(BigDecimal invoiceSum) {
        this.invoiceSum = invoiceSum;
    }

    /**
     * 获取invoice备注
     *
     * @return invoice_remark - invoice备注
     */
    public String getInvoiceRemark() {
        return invoiceRemark;
    }

    /**
     * 设置invoice备注
     *
     * @param invoiceRemark invoice备注
     */
    public void setInvoiceRemark(String invoiceRemark) {
        this.invoiceRemark = invoiceRemark;
    }

    /**
     * 获取支付方式
     *
     * @return pay_method - 支付方式
     */
    public String getPayMethod() {
        return payMethod;
    }

    /**
     * 设置支付方式
     *
     * @param payMethod 支付方式
     */
    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取删除状态
     *
     * @return delete_status - 删除状态
     */
    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * 设置删除状态
     *
     * @param deleteStatus 删除状态
     */
    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    /**
     * 获取账户金额
     *
     * @return account_money - 账户金额
     */
    public BigDecimal getAccountMoney() {
        return accountMoney;
    }

    /**
     * 设置账户金额
     *
     * @param accountMoney 账户金额
     */
    public void setAccountMoney(BigDecimal accountMoney) {
        this.accountMoney = accountMoney;
    }

    /**
     * 获取账户gst
     *
     * @return account_gst - 账户gst
     */
    public BigDecimal getAccountGst() {
        return accountGst == null ? accountGst : accountGst.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 设置账户gst
     *
     * @param accountGst 账户gst
     */
    public void setAccountGst(BigDecimal accountGst) {
        this.accountGst = accountGst;
    }

    /**
     * 获取账户总额
     *
     * @return account_sum - 账户总额
     */
    public BigDecimal getAccountSum() {
        return accountSum == null ? accountSum : accountSum.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * 设置账户总额
     *
     * @param accountSum 账户总额
     */
    public void setAccountSum(BigDecimal accountSum) {
        this.accountSum = accountSum;
    }

    /**
     * 获取账户币种
     *
     * @return account_currency - 账户币种
     */
    public String getAccountCurrency() {
        return accountCurrency;
    }

    /**
     * 设置账户币种
     *
     * @param accountCurrency 账户币种
     */
    public void setAccountCurrency(String accountCurrency) {
        this.accountCurrency = accountCurrency;
    }

    /**
     * 获取差额
     *
     * @return balance - 差额
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * 设置差额
     *
     * @param balance 差额
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * 获取差额原因
     *
     * @return balance_type - 差额原因
     */
    public String getBalanceType() {
        return balanceType;
    }

    /**
     * 设置差额原因
     *
     * @param balanceType 差额原因
     */
    public void setBalanceType(String balanceType) {
        this.balanceType = balanceType;
    }

    /**
     * 获取银行手续费
     *
     * @return bank_fee - 银行手续费
     */
    public BigDecimal getBankFee() {
        return bankFee;
    }

    /**
     * 设置银行手续费
     *
     * @param bankFee 银行手续费
     */
    public void setBankFee(BigDecimal bankFee) {
        this.bankFee = bankFee;
    }

    /**
     * 获取银行手续费币种
     *
     * @return bank_fee_currency - 银行手续费币种
     */
    public String getBankFeeCurrency() {
        return bankFeeCurrency;
    }

    /**
     * 设置银行手续费币种
     *
     * @param bankFeeCurrency 银行手续费币种
     */
    public void setBankFeeCurrency(String bankFeeCurrency) {
        this.bankFeeCurrency = bankFeeCurrency;
    }

    /**
     * @return exchange_rate
     */
    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    /**
     * @param exchangeRate
     */
    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    /**
     * @return line_num
     */
    public String getLineNum() {
        return lineNum;
    }

    /**
     * @param lineNum
     */
    public void setLineNum(String lineNum) {
        this.lineNum = lineNum;
    }

    /**
     * @return invoice_data
     */
    public String getInvoiceData() {
        return invoiceData;
    }

    /**
     * @param invoiceData
     */
    public void setInvoiceData(String invoiceData) {
        this.invoiceData = invoiceData;
    }

    /**
     * 获取数据来源
     *
     * @return data_source - 数据来源
     */
    public String getDataSource() {
        return dataSource;
    }

    /**
     * 设置数据来源
     *
     * @param dataSource 数据来源
     */
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 获取invoice状态
     *
     * @return invoice_status - invoice状态
     */
    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    /**
     * 设置invoice状态
     *
     * @param invoiceStatus invoice状态
     */
    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    /**
     * @return is_state
     */
    public Integer getIsState() {
        return isState;
    }

    /**
     * @param isState
     */
    public void setIsState(Integer isState) {
        this.isState = isState;
    }

    /**
     * @return percent_num
     */
    public Integer getPercentNum() {
        return percentNum;
    }

    /**
     * @param percentNum
     */
    public void setPercentNum(Integer percentNum) {
        this.percentNum = percentNum;
    }

    /**
     * @return pay_time
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * @param payTime
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * @return state_time
     */
    public Date getStateTime() {
        return stateTime;
    }

    /**
     * @param stateTime
     */
    public void setStateTime(Date stateTime) {
        this.stateTime = stateTime;
    }

    /**
     * @return curr_rate
     */
    public BigDecimal getCurrRate() {
        return currRate;
    }

    /**
     * @param currRate
     */
    public void setCurrRate(BigDecimal currRate) {
        this.currRate = currRate;
    }

    /**
     * @return update_id
     */
    public Integer getUpdateId() {
        return updateId;
    }

    /**
     * @param updateId
     */
    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    /**
     * @return update_date
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取银行流水号
     *
     * @return bank_serial_num - 银行流水号
     */
    public String getBankSerialNum() {
        return bankSerialNum;
    }

    /**
     * 设置银行流水号
     *
     * @param bankSerialNum 银行流水号
     */
    public void setBankSerialNum(String bankSerialNum) {
        this.bankSerialNum = bankSerialNum;
    }

    /**
     * 获取银行账户
     *
     * @return bank_account - 银行账户
     */
    public String getBankAccount() {
        return bankAccount;
    }

    /**
     * 设置银行账户
     *
     * @param bankAccount 银行账户
     */
    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    /**
     * 获取返佣币种
     *
     * @return return_currency - 返佣币种
     */
    public String getReturnCurrency() {
        return returnCurrency;
    }

    /**
     * 设置返佣币种
     *
     * @param returnCurrency 返佣币种
     */
    public void setReturnCurrency(String returnCurrency) {
        this.returnCurrency = returnCurrency;
    }

    /**
     * 获取佣金比例
     *
     * @return rate - 佣金比例
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * 设置佣金比例
     *
     * @param rate 佣金比例
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * @return reference_no
     */
    public String getReferenceNo() {
        return referenceNo;
    }

    /**
     * @param referenceNo
     */
    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    /**
     * @return state_time_two
     */
    public Date getStateTimeTwo() {
        return stateTimeTwo;
    }

    /**
     * @param stateTimeTwo
     */
    public void setStateTimeTwo(Date stateTimeTwo) {
        this.stateTimeTwo = stateTimeTwo;
    }

    public String getSendDateStr() {
        return sendDateStr;
    }

    public void setSendDateStr(String sendDateStr) {
        this.sendDateStr = sendDateStr;
    }

    public String getReturnDateStr() {
        return returnDateStr;
    }

    public void setReturnDateStr(String returnDateStr) {
        this.returnDateStr = returnDateStr;
    }

    public BigDecimal getInvoiceGstRate() {
        return invoiceGstRate;
    }

    public void setInvoiceGstRate(BigDecimal invoiceGstRate) {
        this.invoiceGstRate = invoiceGstRate;
    }

    public BigDecimal getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(BigDecimal actualAmount) {
        this.actualAmount = actualAmount;
    }

    public BigDecimal getTuition() {
        return tuition;
    }

    public void setTuition(BigDecimal tuition) {
        this.tuition = tuition;
    }

    public String getChannelReturnStatus() {
        return channelReturnStatus;
    }

    public void setChannelReturnStatus(String channelReturnStatus) {
        this.channelReturnStatus = channelReturnStatus;
    }

    public List<String> getChannelReturnStatusIdentified() {
        return channelReturnStatusIdentified;
    }

    public void setChannelReturnStatusIdentified(List<String> channelReturnStatusIdentified) {
        this.channelReturnStatusIdentified = channelReturnStatusIdentified;
    }
}