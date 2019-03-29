package com.aoji.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "express_company")
public class ExpressCompany {
    /**
     * 表id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 快递公司名称
     */
    @Column(name = "company_name")
    private String companyName;

    /**
     * 快递公司编码
     */
    @Column(name = "company_code")
    private String companyCode;

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 删除时间/失效时间
     */
    @Column(name = "delete_time")
    private Date deleteTime;

    /**
     * 删除状态0为未删除/可用，1为已删除/不可用
     */
    @Column(name = "delete_status")
    private Boolean deleteStatus;

    /**
     * 操作人
     */
    @Column(name = "operator_no")
    private String operatorNo;

    public  ExpressCompany(){}

    public ExpressCompany(Boolean deleteStatus){
        this.deleteStatus=deleteStatus;
    }
    /**
     * 获取表id
     *
     * @return id - 表id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置表id
     *
     * @param id 表id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取快递公司名称
     *
     * @return company_name - 快递公司名称
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * 设置快递公司名称
     *
     * @param companyName 快递公司名称
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 获取快递公司编码
     *
     * @return company_code - 快递公司编码
     */
    public String getCompanyCode() {
        return companyCode;
    }

    /**
     * 设置快递公司编码
     *
     * @param companyCode 快递公司编码
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
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

    /**
     * 获取删除时间/失效时间
     *
     * @return delete_time - 删除时间/失效时间
     */
    public Date getDeleteTime() {
        return deleteTime;
    }

    /**
     * 设置删除时间/失效时间
     *
     * @param deleteTime 删除时间/失效时间
     */
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
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
}