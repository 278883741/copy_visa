package com.aoji.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "commission_record_info")
public class CommissionRecordInfo {
    /**
     * 佣金信息记录表id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 佣金到达日期
     */
    @Column(name = "commission_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date commissionDate;

    /**
     * 国家id
     */
    @Column(name = "nation_id")
    private Integer nationId;

    /**
     * 国家名称
     */
    @Column(name = "nation_name")
    private String nationName;

    /**
     * 院校id
     */
    @Column(name = "college_id")
    private Integer collegeId;

    /**
     * 院校名称
     */
    @Column(name = "college_name")
    private String collegeName;

    /**
     * 账户
     */
    private String account;

    /**
     * 金额
     */
    private String money;

    /**
     * 币种
     */
    private String currency;

    /**
     * 佣金类型：1-市场费，2-Bonus，3-后续佣金，4-外训机票款
     */
    @Column(name = "commission_type")
    private Integer commissionType;

    /**
     * invoice编号
     */
    @Column(name = "invoice_id")
    private String invoiceId;

    /**
     * 目标数
     */
    @Column(name = "target_num")
    private String targetNum;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 删除状态
     */
    @Column(name = "delete_status")
    private Boolean deleteStatus;

    /**
     * 操作人工号
     */
    @Column(name = "operator_no")
    private String operatorNo;

    /**
     * 操作人姓名
     */
    @Column(name = "operator_name")
    private String operatorName;

    /**
     * 获取佣金信息记录表id
     *
     * @return id - 佣金信息记录表id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置佣金信息记录表id
     *
     * @param id 佣金信息记录表id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取佣金到达日期
     *
     * @return commission_date - 佣金到达日期
     */
    public Date getCommissionDate() {
        return commissionDate;
    }

    /**
     * 设置佣金到达日期
     *
     * @param commissionDate 佣金到达日期
     */
    public void setCommissionDate(Date commissionDate) {
        this.commissionDate = commissionDate;
    }

    /**
     * 获取国家id
     *
     * @return nation_id - 国家id
     */
    public Integer getNationId() {
        return nationId;
    }

    /**
     * 设置国家id
     *
     * @param nationId 国家id
     */
    public void setNationId(Integer nationId) {
        this.nationId = nationId;
    }

    /**
     * 获取国家名称
     *
     * @return nation_name - 国家名称
     */
    public String getNationName() {
        return nationName;
    }

    /**
     * 设置国家名称
     *
     * @param nationName 国家名称
     */
    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    /**
     * 获取院校id
     *
     * @return college_id - 院校id
     */
    public Integer getCollegeId() {
        return collegeId;
    }

    /**
     * 设置院校id
     *
     * @param collegeId 院校id
     */
    public void setCollegeId(Integer collegeId) {
        this.collegeId = collegeId;
    }

    /**
     * 获取院校名称
     *
     * @return college_name - 院校名称
     */
    public String getCollegeName() {
        return collegeName;
    }

    /**
     * 设置院校名称
     *
     * @param collegeName 院校名称
     */
    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    /**
     * 获取账户
     *
     * @return account - 账户
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置账户
     *
     * @param account 账户
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取金额
     *
     * @return money - 金额
     */
    public String getMoney() {
        return money;
    }

    /**
     * 设置金额
     *
     * @param money 金额
     */
    public void setMoney(String money) {
        this.money = money;
    }

    /**
     * 获取币种
     *
     * @return currency - 币种
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * 设置币种
     *
     * @param currency 币种
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * 获取佣金类型：1-市场费，2-Bonus，3-后续佣金，4-外训机票款
     *
     * @return commission_type - 佣金类型：1-市场费，2-Bonus，3-后续佣金，4-外训机票款
     */
    public Integer getCommissionType() {
        return commissionType;
    }

    /**
     * 设置佣金类型：1-市场费，2-Bonus，3-后续佣金，4-外训机票款
     *
     * @param commissionType 佣金类型：1-市场费，2-Bonus，3-后续佣金，4-外训机票款
     */
    public void setCommissionType(Integer commissionType) {
        this.commissionType = commissionType;
    }

    /**
     * 获取invoice编号
     *
     * @return invoice_id - invoice编号
     */
    public String getInvoiceId() {
        return invoiceId;
    }

    /**
     * 设置invoice编号
     *
     * @param invoiceId invoice编号
     */
    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * 获取目标数
     *
     * @return target_num - 目标数
     */
    public String getTargetNum() {
        return targetNum;
    }

    /**
     * 设置目标数
     *
     * @param targetNum 目标数
     */
    public void setTargetNum(String targetNum) {
        this.targetNum = targetNum;
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
     * 获取操作人工号
     *
     * @return operator_no - 操作人工号
     */
    public String getOperatorNo() {
        return operatorNo;
    }

    /**
     * 设置操作人工号
     *
     * @param operatorNo 操作人工号
     */
    public void setOperatorNo(String operatorNo) {
        this.operatorNo = operatorNo;
    }

    /**
     * 获取操作人姓名
     *
     * @return operator_name - 操作人姓名
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * 设置操作人姓名
     *
     * @param operatorName 操作人姓名
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
}