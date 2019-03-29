package com.aoji.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "plan_info")
public class PlanInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 内网学号
     */
    @Column(name = "student_no")
    private String studentNo;

    /**
     * 留学国家
     */
    private Integer nation;

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

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

    /**
     * 申请类别：0-直录 1-双录 2-纯语言
     */
    @Column(name = "apply_type")
    private Integer applyType;

    /**
     * 合作机构
     */
    private Integer agency;

    /**
     * 是否加申
     */
    @Column(name = "add_status")
    private Boolean addStatus;

    /**
     * 旧表提供id供关联 plan_college_info表
     */
    @Column(name = "plan_id")
    private Integer planId;

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

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
     * 获取内网学号
     *
     * @return student_no - 内网学号
     */
    public String getStudentNo() {
        return studentNo;
    }

    /**
     * 设置内网学号
     *
     * @param studentNo 内网学号
     */
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    /**
     * 获取留学国家
     *
     * @return nation - 留学国家
     */
    public Integer getNation() {
        return nation;
    }

    /**
     * 设置留学国家
     *
     * @param nation 留学国家
     */
    public void setNation(Integer nation) {
        this.nation = nation;
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
     * 获取申请类别：0-直录 1-双录 2-纯语言
     *
     * @return apply_type - 申请类别：0-直录 1-双录 2-纯语言
     */
    public Integer getApplyType() {
        return applyType;
    }

    /**
     * 设置申请类别：0-直录 1-双录 2-纯语言
     *
     * @param applyType 申请类别：0-直录 1-双录 2-纯语言
     */
    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    /**
     * 获取合作机构
     *
     * @return agency - 合作机构
     */
    public Integer getAgency() {
        return agency;
    }

    /**
     * 设置合作机构
     *
     * @param agency 合作机构
     */
    public void setAgency(Integer agency) {
        this.agency = agency;
    }

    /**
     * 获取是否加申
     *
     * @return add_status - 是否加申
     */
    public Boolean getAddStatus() {
        return addStatus;
    }

    /**
     * 设置是否加申
     *
     * @param addStatus 是否加申
     */
    public void setAddStatus(Boolean addStatus) {
        this.addStatus = addStatus;
    }
}