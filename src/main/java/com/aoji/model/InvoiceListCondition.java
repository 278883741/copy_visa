package com.aoji.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class InvoiceListCondition {

    /**
     * invoice表id
     */
    Integer invoiceId;

    /**
     * 关联院校表id集合
     */
    List<Integer> schoolIds;

    /**
     * 生成的随机字符串，通过该字符串去commission_invoice_seq中获取勾选顺序
     */
    String tempKey;

    /**
     * 学校id拼接的字符串
     */
    String schoolIdStr;

    /**
     * InvoiceId集合
     */
    List<Integer> invoiceIds;

    /**
     * InvoiceId拼接的字符串
     */
    String invoiceIdStr;

    /**
     * Invoice编号
     */
    String invoiceNo;

    /**
     * 发出日期起
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date sendDateBegin;

    /**
     * 发出日期止
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date sendDateEnd;

    /**
     * 留学国家
     */
    Integer nationId;

    /**
     * 操作类型  1-添加 ；
     */
    Integer operatorType;

    //======================  佣金统计列表条件 ↓↓ ========================

    /**
     * 到账日期起
     */
    String returnDateStart;

    /**
     * 到账日期止
     */
    String returnDateEnd;

    /**
     * 获签日期起
     */
    String visaDateStart;

    /**
     * 获签日期止
     */
    String visaDateEnd;

    public List<Integer> getSchoolIds() {
        return schoolIds;
    }

    public void setSchoolIds(List<Integer> schoolIds) {
        this.schoolIds = schoolIds;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Date getSendDateBegin() {
        return sendDateBegin;
    }

    public void setSendDateBegin(Date sendDateBegin) {
        this.sendDateBegin = sendDateBegin;
    }

    public Date getSendDateEnd() {
        return sendDateEnd;
    }

    public void setSendDateEnd(Date sendDateEnd) {
        this.sendDateEnd = sendDateEnd;
    }

    public Integer getNationId() {
        return nationId;
    }

    public void setNationId(Integer nationId) {
        this.nationId = nationId;
    }

    public Integer getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getSchoolIdStr() {
        return schoolIdStr;
    }

    public void setSchoolIdStr(String schoolIdStr) {
        this.schoolIdStr = schoolIdStr;
    }

    public String getReturnDateStart() {
        return returnDateStart;
    }

    public void setReturnDateStart(String returnDateStart) {
        this.returnDateStart = returnDateStart;
    }

    public String getReturnDateEnd() {
        return returnDateEnd;
    }

    public void setReturnDateEnd(String returnDateEnd) {
        this.returnDateEnd = returnDateEnd;
    }

    public String getVisaDateStart() {
        return visaDateStart;
    }

    public void setVisaDateStart(String visaDateStart) {
        this.visaDateStart = visaDateStart;
    }

    public String getVisaDateEnd() {
        return visaDateEnd;
    }

    public void setVisaDateEnd(String visaDateEnd) {
        this.visaDateEnd = visaDateEnd;
    }

    public List<Integer> getInvoiceIds() {
        return invoiceIds;
    }

    public void setInvoiceIds(List<Integer> invoiceIds) {
        this.invoiceIds = invoiceIds;
    }

    public String getInvoiceIdStr() {
        return invoiceIdStr;
    }

    public void setInvoiceIdStr(String invoiceIdStr) {
        this.invoiceIdStr = invoiceIdStr;
    }

    public String getTempKey() {
        return tempKey;
    }

    public void setTempKey(String tempKey) {
        this.tempKey = tempKey;
    }
}
