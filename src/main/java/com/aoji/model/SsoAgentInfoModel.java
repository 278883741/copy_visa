package com.aoji.model;


import java.util.List;

/**
 * <p>
 * 代理管理实体
 * </p>
 *
 * @author xiaomengyun
 * @since 2018/08/24
 */
public class SsoAgentInfoModel {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Integer branchId;
    private String agentName;
    private String linkMan;
    private String address;
    private String zipCode;
    private String phone;
    private String email;
    private String province;
    private String agentType;
    private Integer openerId;
    private String officeFlag;
    private String signTime;
    private String startDate;
    private String endDate;
    private String mutual;
    private String bankAccountName;
    private String bankName;
    private String bankAccountNo;
    private String pictureName;
    private Integer recorderId;
    private String userName;
    private String password;
    private List<SsoAgentSubsModel> agentSubs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    /**
     * 方法: 取得Integer
     *
     * @return: Integer  分支ID
     */
    public Integer getBranchId() {
        return this.branchId;
    }

    /**
     * 方法: 设置Integer
     *
     * @param: Integer  分支ID
     */
    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    /**
     * 方法: 取得String
     *
     * @return: String  客户姓名
     */
    public String getAgentName() {
        return this.agentName;
    }

    /**
     * 方法: 设置String
     *
     * @param: String  客户姓名
     */
    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    /**
     * 方法: 取得String
     *
     * @return: String  联系人
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * 方法: 设置String
     *
     * @param: String  联系人
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 方法: 取得String
     *
     * @return: String  邮箱
     */
    public String getZipCode() {
        return this.zipCode;
    }

    /**
     * 方法: 设置String
     *
     * @param: String  邮箱
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * 方法: 取得String
     *
     * @return: String  电话
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * 方法: 设置String
     *
     * @param: String  电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 方法: 取得String
     *
     * @return: String  邮箱
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * 方法: 设置String
     *
     * @param: String  邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 方法: 取得String
     *
     * @return: String  省份
     */
    public String getProvince() {
        return this.province;
    }

    /**
     * 方法: 设置String
     *
     * @param: String  省份
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 方法: 取得String
     *
     * @return: String  客户类型
     */
    public String getAgentType() {
        return this.agentType;
    }

    /**
     * 方法: 设置String
     *
     * @param: String  客户类型
     */
    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    /**
     * 方法: 取得Integer
     *
     * @return: Integer  opener_id
     */
    public Integer getOpenerId() {
        return this.openerId;
    }

    /**
     * 方法: 设置Integer
     *
     * @param: Integer  opener_id
     */
    public void setOpenerId(Integer openerId) {
        this.openerId = openerId;
    }

    /**
     * 方法: 取得String
     *
     * @return: String  办公标识
     */
    public String getOfficeFlag() {
        return this.officeFlag;
    }

    /**
     * 方法: 设置String
     *
     * @param: String  办公标识
     */
    public void setOfficeFlag(String officeFlag) {
        this.officeFlag = officeFlag;
    }

    /**
     * 方法: 取得String
     *
     * @return: String  签约日期
     */
    public String getSignTime() {
        return this.signTime;
    }

    /**
     * 方法: 设置String
     *
     * @param: String  签约日期
     */
    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * 方法: 取得String
     *
     * @return: String  Mutual
     */
    public String getMutual() {
        return this.mutual;
    }

    /**
     * 方法: 设置String
     *
     * @param: String  Mutual
     */
    public void setMutual(String mutual) {
        this.mutual = mutual;
    }

    /**
     * 方法: 取得String
     *
     * @return: String  银行账号名
     */
    public String getBankAccountName() {
        return this.bankAccountName;
    }

    /**
     * 方法: 设置String
     *
     * @param: String  银行账号名
     */
    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    /**
     * 方法: 取得String
     *
     * @return: String  银行名称
     */
    public String getBankName() {
        return this.bankName;
    }

    /**
     * 方法: 设置String
     *
     * @param: String  银行名称
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * 方法: 取得String
     *
     * @return: String  银行卡号
     */
    public String getBankAccountNo() {
        return this.bankAccountNo;
    }

    /**
     * 方法: 设置String
     *
     * @param: String  银行卡号
     */
    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    /**
     * 方法: 取得String
     *
     * @return: String  附件地址
     */
    public String getPictureName() {
        return this.pictureName;
    }

    /**
     * 方法: 设置String
     *
     * @param: String  附件地址
     */
    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    /**
     * 方法: 取得Integer
     *
     * @return: Integer  记录ID
     */
    public Integer getRecorderId() {
        return this.recorderId;
    }

    /**
     * 方法: 设置Integer
     *
     * @param: Integer  记录ID
     */
    public void setRecorderId(Integer recorderId) {
        this.recorderId = recorderId;
    }

    /**
     * 方法: 取得String
     *
     * @return: String  用户名
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * 方法: 设置String
     *
     * @param: String  用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 方法: 取得String
     *
     * @return: String  密码
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * 方法: 设置String
     *
     * @param: String  密码
     */
    public void setPassword(String password) {
        this.password = password;
    }


    public List<SsoAgentSubsModel> getAgentSubs() {
        return agentSubs;
    }

    public void setAgentSubs(List<SsoAgentSubsModel> agentSubs) {
        this.agentSubs = agentSubs;
    }

}
