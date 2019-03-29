package com.aoji.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "currency_info")
public class CurrencyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 币种英文名称
     */
    @Column(name = "en_name")
    private String enName;

    /**
     * 币种中文名称
     */
    @Column(name = "cn_name")
    private String cnName;

    /**
     * 原sqlServer字段
     */
    @Column(name = "order_id")
    private Integer orderId;

    /**
     * @return id
     */

    /**
     * 汇率
     * @return
     */
    @Column(name = "exchange_rate")
    private BigDecimal exchangeRate;

    /**
     * 修改人编号
     * @return
     */

    @Column(name = "operator_no")
    private  String operatorNo;

    /**
     * 修改人名称
     * @return
     */
    @Column(name = "operator_name")
    private  String operatorName;

    /**
     *创建时间
     * @return
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     *修改时间时间
     * @return
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "update_time")
    private Date updateTime;




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
     * 获取币种英文名称
     *
     * @return en_name - 币种英文名称
     */
    public String getEnName() {
        return enName;
    }

    /**
     * 设置币种英文名称
     *
     * @param enName 币种英文名称
     */
    public void setEnName(String enName) {
        this.enName = enName;
    }

    /**
     * 获取币种中文名称
     *
     * @return cn_name - 币种中文名称
     */
    public String getCnName() {
        return cnName;
    }

    /**
     * 设置币种中文名称
     *
     * @param cnName 币种中文名称
     */
    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    /**
     * 获取原sqlServer字段
     *
     * @return order_id - 原sqlServer字段
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * 设置原sqlServer字段
     *
     * @param orderId 原sqlServer字段
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }


    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getOperatorNo() {
        return operatorNo;
    }

    public void setOperatorNo(String operatorNo) {
        this.operatorNo = operatorNo;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}