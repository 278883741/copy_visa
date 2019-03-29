package com.aoji.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "commission_bank_account")
public class CommissionBankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 账号
     */
    @Column(name = "account_number")
    private String accountNumber;

    /**
     * 账户名称
     */
    @Column(name = "account_name")
    private String accountName;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 状态； 启用/禁用/冻结等
     */
    private String status;

    /**
     * 删除状态 1-已删除；0-未删除
     */
    @Column(name = "delete_status")
    private Boolean deleteStatus;

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
     * 获取账号
     *
     * @return account_number - 账号
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * 设置账号
     *
     * @param accountNumber 账号
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * 获取账户名称
     *
     * @return account_name - 账户名称
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 设置账户名称
     *
     * @param accountName 账户名称
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
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
     * 获取状态； 启用/禁用/冻结等
     *
     * @return status - 状态； 启用/禁用/冻结等
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态； 启用/禁用/冻结等
     *
     * @param status 状态； 启用/禁用/冻结等
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取删除状态 1-已删除；0-未删除
     *
     * @return delete_status - 删除状态 1-已删除；0-未删除
     */
    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * 设置删除状态 1-已删除；0-未删除
     *
     * @param deleteStatus 删除状态 1-已删除；0-未删除
     */
    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}