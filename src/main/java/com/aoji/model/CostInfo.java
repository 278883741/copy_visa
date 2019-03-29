package com.aoji.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "cost_info")
public class CostInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 默认费用
     */
    @Column(name = "defaultCost")
    private Double defaultcost;

    /**
     * 金额
     */
    private Double money;

    /**
     * 费用英文
     */
    @Column(name = "costEnglish")
    private String costenglish;

    /**
     * 中文费用
     */
    @Column(name = "costChinese")
    private String costchinese;

    /**
     * 操作人
     */
    @Column(name = "operator_no")
    private String operatorNo;

    /**
     * 删除状态0为未删除/可用，1为已删除/不可用
     */
    @Column(name = "delete_status")
    private Boolean deleteStatus;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
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

    public Double getDefaultcost() {
        return defaultcost;
    }

    public void setDefaultcost(Double defaultcost) {
        this.defaultcost = defaultcost;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    /**
     * 获取费用英文
     *
     * @return costEnglish - 费用英文
     */
    public String getCostenglish() {
        return costenglish;
    }

    /**
     * 设置费用英文
     *
     * @param costenglish 费用英文
     */
    public void setCostenglish(String costenglish) {
        this.costenglish = costenglish;
    }

    /**
     * 获取中文费用
     *
     * @return costChinese - 中文费用
     */
    public String getCostchinese() {
        return costchinese;
    }

    /**
     * 设置中文费用
     *
     * @param costchinese 中文费用
     */
    public void setCostchinese(String costchinese) {
        this.costchinese = costchinese;
    }

    /**
     * 获取操作人
     *
     * @return operator_no - 操作人
     */
    public String getOperatorNo() {
        return operatorNo;
    }

    /**
     * 设置操作人
     *
     * @param operatorNo 操作人
     */
    public void setOperatorNo(String operatorNo) {
        this.operatorNo = operatorNo;
    }

    /**
     * 获取删除状态0为未删除/可用，1为已删除/不可用
     *
     * @return delete_status - 删除状态0为未删除/可用，1为已删除/不可用
     */
    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * 设置删除状态0为未删除/可用，1为已删除/不可用
     *
     * @param deleteStatus 删除状态0为未删除/可用，1为已删除/不可用
     */
    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取创建日期
     *
     * @return create_time - 创建日期
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建日期
     *
     * @param createTime 创建日期
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


}