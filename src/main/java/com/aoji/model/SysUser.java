package com.aoji.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "sys_user")
public class SysUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 代理id
     */
    private String agentId;

    /**
     * 旧工号
     */
    private String oaid;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;


    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 操作人
     */
    @Column(name = "operator_no")
    private String operatorNo;

    /**
     * 逻辑删除状态
     */
    @Column(name="delete_status")
    private Boolean deleteStatus;

    /**
     * 更新时间
     */
    @Column(name="update_time")
    private Date updateTime;


    /**
     * 所属部门id
     */
    @Column(name = "department_id")
    private Integer departmentId;
    /**
     * 所属部门名称
     */
    @Column(name = "department_name")
    private String departmentName;

    /**
     * 是否启用：1-启用 0-禁用
     */
    @Column(name="enable_status")
    private Integer enableStatus;

    /**
     * 是否为内部顾问：1-内部 0-外部
     */
    @Column(name = "is_inner")
    private Integer isInner;

    /**
     * 供前台展示，更新时间
     */
    @Transient
    private String updateTimeString;

    @Override
    public String toString() {
        return "SysUser{" +
                "agentId='" + agentId + '\'' +
                ", id=" + id +
                ", oaid='" + oaid + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", operatorNo='" + operatorNo + '\'' +
                ", deleteStatus=" + deleteStatus +
                ", updateTime=" + updateTime +
                ", departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", enableStatus=" + enableStatus +
                ", updateTimeString='" + updateTimeString + '\'' +
                '}';
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
    public String getAgentId() {
        return agentId;
    }

    public String getUpdateTimeString() {
        return updateTimeString;
    }

    public void setUpdateTimeString(String updateTimeString) {
        this.updateTimeString = updateTimeString;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getIsInner() {
        return isInner;
    }

    public void setIsInner(Integer isInner) {
        this.isInner = isInner;
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取账号
     *
     * @return username - 账号
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置账号
     *
     * @param username 账号
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOperatorNo() {
        return operatorNo;
    }

    public void setOperatorNo(String operatorNo) {
        this.operatorNo = operatorNo;
    }

    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    public String getOaid() {
        return oaid;
    }

    public void setOaid(String oaid) {
        this.oaid = oaid;
    }

}