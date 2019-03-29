package com.aoji.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "commission_invoice_seq")
public class CommissionInvoiceSeq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 佣金院校id
     */
    @Column(name = "school_id")
    private Integer schoolId;

    /**
     * 排序号
     */
    private Integer number;

    /**
     * 系统生成的随机字符串，区分一次操作
     */
    @Column(name = "temp_key")
    private String tempKey;

    /**
     * invoice编号
     */
    @Column(name = "invoice_no")
    private String invoiceNo;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

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
     * 获取佣金院校id
     *
     * @return school_id - 佣金院校id
     */
    public Integer getSchoolId() {
        return schoolId;
    }

    /**
     * 设置佣金院校id
     *
     * @param schoolId 佣金院校id
     */
    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    /**
     * 获取排序号
     *
     * @return number - 排序号
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * 设置排序号
     *
     * @param number 排序号
     */
    public void setNumber(Integer number) {
        this.number = number;
    }

    /**
     * 获取系统生成的随机字符串，区分一次操作
     *
     * @return temp_key - 系统生成的随机字符串，区分一次操作
     */
    public String getTempKey() {
        return tempKey;
    }

    /**
     * 设置系统生成的随机字符串，区分一次操作
     *
     * @param tempKey 系统生成的随机字符串，区分一次操作
     */
    public void setTempKey(String tempKey) {
        this.tempKey = tempKey;
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
}