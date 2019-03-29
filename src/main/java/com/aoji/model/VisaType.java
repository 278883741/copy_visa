package com.aoji.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "visa_type")
public class VisaType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 是否是学生签证
     */
    @Column(name = "student_visa_status")
    private Boolean studentVisaStatus;
    @Transient
    private String studentVisaStatus_string;
    public void setStudentVisaStatus_string(String studentVisaStatus_string) {
        this.studentVisaStatus_string = studentVisaStatus_string;
    }
    public String getStudentVisaStatus_string() {
        return studentVisaStatus_string;
    }
    /**
     * 国家id
     */
    private Integer nation;

    /**
     * 签证名称
     */
    @Column(name = "visa_name")
    private String visaName;

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    private Date createTime;
    @Transient
    private String createTime_string;
    public void setCreateTime_string(String createTime_string) {
        this.createTime_string = createTime_string;
    }
    public String getCreateTime_string() {
        return createTime_string;
    }
    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;
    @Transient
    private String updateTime_string;
    public String getUpdateTime_string() {
        return updateTime_string;
    }
    public void setUpdateTime_string(String updateTime_string) {
        this.updateTime_string = updateTime_string;
    }
    /**
     * 删除状态0为未删除/可用，1为已删除/不可用
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
     * 签证类型
     */
    private Integer type;

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
     * 获取是否是学生签证
     *
     * @return student_visa_status - 是否是学生签证
     */
    public Boolean getStudentVisaStatus() {
        return studentVisaStatus;
    }

    /**
     * 设置是否是学生签证
     *
     * @param studentVisaStatus 是否是学生签证
     */
    public void setStudentVisaStatus(Boolean studentVisaStatus) {
        this.studentVisaStatus = studentVisaStatus;
    }

    /**
     * 获取国家id
     *
     * @return nation - 国家id
     */
    public Integer getNation() {
        return nation;
    }

    /**
     * 设置国家id
     *
     * @param nation 国家id
     */
    public void setNation(Integer nation) {
        this.nation = nation;
    }

    /**
     * 获取签证名称
     *
     * @return visa_name - 签证名称
     */
    public String getVisaName() {
        return visaName;
    }

    /**
     * 设置签证名称
     *
     * @param visaName 签证名称
     */
    public void setVisaName(String visaName) {
        this.visaName = visaName;
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

    /**
     * 获取签证类型
     *
     * @return type - 签证类型
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置签证类型
     *
     * @param type 签证类型
     */
    public void setType(Integer type) {
        this.type = type;
    }
}