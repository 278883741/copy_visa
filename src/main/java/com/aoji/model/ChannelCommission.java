package com.aoji.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "channel_commission")
public class ChannelCommission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 关联commission_student表id
     */
    @Column(name = "student_id")
    private Integer studentId;

    /**
     * 关联commission_invoice表id
     */
    @Column(name = "invoice_id")
    private Integer invoiceId;

    /**
     * 获签数量
     */
    @Column(name = "get_visa_sum")
    private Integer getVisaSum;

    /**
     * 渠道返佣比例
     */
    @Column(name = "channel_return_rate")
    private Integer channelReturnRate;

    /**
     * 应返金额 （账户金额 * 渠道返佣比例）
     */
    @Column(name = "return_money")
    private BigDecimal returnMoney;

    /**
     * 汇率
     */
    @Column(name = "exchange_rate")
    private BigDecimal exchangeRate;

    /**
     * 应返人民币 （应返金额 * 汇率）
     */
    @Column(name = "return_money_cny")
    private BigDecimal returnMoneyCny;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "delete_status")
    private Boolean deleteStatus;

    /**
     * 修改人工号
     */
    @Column(name = "operator_no")
    private String operatorNo;

    /**
     * 修改人名称
     */
    @Column(name = "operator_name")
    private String operatorName;

    /**
     * 付款日期
     */
    @Column(name = "payment_date")
    private Date paymentDate;

    /**
     * 备注
     */

    @Column(name = "result_comment")
    private String resultComment;

    /**
     * 本次应返金额
     */

    @Column(name = "this_return_money")
    private BigDecimal thisReturnMoney;

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
     * 获取关联commission_student表id
     *
     * @return student_id - 关联commission_student表id
     */
    public Integer getStudentId() {
        return studentId;
    }

    /**
     * 设置关联commission_student表id
     *
     * @param studentId 关联commission_student表id
     */
    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    /**
     * 获取关联commission_invoice表id
     *
     * @return invoice_id - 关联commission_invoice表id
     */
    public Integer getInvoiceId() {
        return invoiceId;
    }

    /**
     * 设置关联commission_invoice表id
     *
     * @param invoiceId 关联commission_invoice表id
     */
    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * 获取获签数量
     *
     * @return get_visa_sum - 获签数量
     */
    public Integer getGetVisaSum() {
        return getVisaSum;
    }

    /**
     * 设置获签数量
     *
     * @param getVisaSum 获签数量
     */
    public void setGetVisaSum(Integer getVisaSum) {
        this.getVisaSum = getVisaSum;
    }

    /**
     * 获取渠道返佣比例
     *
     * @return channel_return_rate - 渠道返佣比例
     */
    public Integer getChannelReturnRate() {
        return channelReturnRate;
    }

    /**
     * 设置渠道返佣比例
     *
     * @param channelReturnRate 渠道返佣比例
     */
    public void setChannelReturnRate(Integer channelReturnRate) {
        this.channelReturnRate = channelReturnRate;
    }

    /**
     * 获取应返金额 （账户金额 * 渠道返佣比例）
     *
     * @return return_money - 应返金额 （账户金额 * 渠道返佣比例）
     */
    public BigDecimal getReturnMoney() {
        return returnMoney;
    }

    /**
     * 设置应返金额 （账户金额 * 渠道返佣比例）
     *
     * @param returnMoney 应返金额 （账户金额 * 渠道返佣比例）
     */
    public void setReturnMoney(BigDecimal returnMoney) {
        this.returnMoney = returnMoney;
    }

    /**
     * 获取汇率
     *
     * @return exchange_rate - 汇率
     */
    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    /**
     * 设置汇率
     *
     * @param exchangeRate 汇率
     */
    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    /**
     * 获取应返人民币 （应返金额 * 汇率）
     *
     * @return return_money_cny - 应返人民币 （应返金额 * 汇率）
     */
    public BigDecimal getReturnMoneyCny() {
        return returnMoneyCny;
    }

    /**
     * 设置应返人民币 （应返金额 * 汇率）
     *
     * @param returnMoneyCny 应返人民币 （应返金额 * 汇率）
     */
    public void setReturnMoneyCny(BigDecimal returnMoneyCny) {
        this.returnMoneyCny = returnMoneyCny;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return delete_status
     */
    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * @param deleteStatus
     */
    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    /**
     * 获取修改人工号
     *
     * @return operator_no - 修改人工号
     */
    public String getOperatorNo() {
        return operatorNo;
    }

    /**
     * 设置修改人工号
     *
     * @param operatorNo 修改人工号
     */
    public void setOperatorNo(String operatorNo) {
        this.operatorNo = operatorNo;
    }

    /**
     * 获取修改人名称
     *
     * @return operator_name - 修改人名称
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * 设置修改人名称
     *
     * @param operatorName 修改人名称
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    /**
     * 获取付款日期
     *
     * @return payment_date - 付款日期
     */
    public Date getPaymentDate() {
        return paymentDate;
    }

    /**
     * 设置付款日期
     *
     * @param paymentDate 付款日期
     */
    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getResultComment() {
        return resultComment;
    }

    public void setResultComment(String resultComment) {
        this.resultComment = resultComment;
    }

    public BigDecimal getThisReturnMoney() {
        return thisReturnMoney;
    }

    public void setThisReturnMoney(BigDecimal thisReturnMoney) {
        this.thisReturnMoney = thisReturnMoney;
    }
}