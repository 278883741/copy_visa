package com.aoji.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Table(name = "plan_college_record")
public class PlanCollegeRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 驳回原因
     */
    @Column(name = "reject_reason")
    private String rejectReason;

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
     * 操作人姓名
     */
    private String operatorName;

    /**
     * 结果：1-接受 2-驳回
     */
    private Integer result;

    /**
     * 操作类型： 1-文签系统接收或拒绝
     */
    private Integer type;

    /**
     * 定校方案的院校id
     */
    @Column(name = "plan_college_id")
    private Integer planCollegeId;

    /**
     * 学生可以确认定校方案时间
     */
    @Column(name = "can_confirm_time")
    private Date canConfirmTime;

    /**
     * 备注
     */
//    private String remark;

    public Date getCanConfirmTime() {
        return canConfirmTime;
    }

    public void setCanConfirmTime(Date canConfirmTime) {
        this.canConfirmTime = canConfirmTime;
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
     * 获取驳回原因
     *
     * @return reject_reason - 驳回原因
     */
    public String getRejectReason() {
        return rejectReason;
    }

    /**
     * 设置驳回原因
     *
     * @param rejectReason 驳回原因
     */
    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
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
     * 获取结果：1-接受 2-驳回
     *
     * @return result - 结果：1-接受 2-驳回
     */
    public Integer getResult() {
        return result;
    }

    /**
     * 设置结果：1-接受 2-驳回
     *
     * @param result 结果：1-接受 2-驳回
     */
    public void setResult(Integer result) {
        this.result = result;
    }

    /**
     * 获取定校方案的院校id
     *
     * @return plan_college_id - 定校方案的院校id
     */
    public Integer getPlanCollegeId() {
        return planCollegeId;
    }

    /**
     * 设置定校方案的院校id
     *
     * @param planCollegeId 定校方案的院校id
     */
    public void setPlanCollegeId(Integer planCollegeId) {
        this.planCollegeId = planCollegeId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

//    public String getRemark() {
//        return remark;
//    }
//
//    public void setRemark(String remark) {
//        this.remark = remark;
//    }
}