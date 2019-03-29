package com.aoji.model.res;

import java.math.BigDecimal;
import java.util.Date;

/**
 * author: chenhaibo
 * description: OA返佣金列表单元
 * date: 2018/9/10
 */
public class CommissionInfo {

    String studentNo;

    String studentName;

    String spelling;

    Date birthday;

    String invoiceNo;

    String agentName;

    BigDecimal accountMoney;

    Date returnDate;

    Date getVisaDate;

    String accountCurrency;

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

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public BigDecimal getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(BigDecimal accountMoney) {
        this.accountMoney = accountMoney;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getGetVisaDate() {
        return getVisaDate;
    }

    public void setGetVisaDate(Date getVisaDate) {
        this.getVisaDate = getVisaDate;
    }

    public String getAccountCurrency() {
        return accountCurrency;
    }

    public void setAccountCurrency(String accountCurrency) {
        this.accountCurrency = accountCurrency;
    }
}
