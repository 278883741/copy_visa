package com.aoji.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "visit_remark_info")
public class VisitRemarkInfo {
    /**
     * 备注id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 学号
     */
    @Column(name = "student_no")
    private String studentNo;

    /**
     * 备注内容
     */
    private String remark;

    /**
     * 提醒时间
     */
    @Column(name = "remind_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date remindTime;

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
     * 备注状态：0-待提醒，1-已提醒
     */
    @Column(name = "remind_status")
    private Integer remindStatus;

    @Column(name = "delete_status")
    private Boolean deleteStatus;

    /**
     * 获取备注id
     *
     * @return id - 备注id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置备注id
     *
     * @param id 备注id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取学号
     *
     * @return student_no - 学号
     */
    public String getStudentNo() {
        return studentNo;
    }

    /**
     * 设置学号
     *
     * @param studentNo 学号
     */
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    /**
     * 获取备注内容
     *
     * @return remark - 备注内容
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注内容
     *
     * @param remark 备注内容
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取提醒时间
     *
     * @return remind_time - 提醒时间
     */
    public Date getRemindTime() {
        return remindTime;
    }

    /**
     * 设置提醒时间
     *
     * @param remindTime 提醒时间
     */
    public void setRemindTime(Date remindTime) {
        this.remindTime = remindTime;
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
     * 获取备注状态：0-待提醒，1-已提醒
     *
     * @return remind_status - 备注状态：0-待提醒，1-已提醒
     */
    public Integer getRemindStatus() {
        return remindStatus;
    }

    /**
     * 设置备注状态：0-待提醒，1-已提醒
     *
     * @param remindStatus 备注状态：0-待提醒，1-已提醒
     */
    public void setRemindStatus(Integer remindStatus) {
        this.remindStatus = remindStatus;
    }

    /**
     * @return delete_status
     */
    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * @param deleteStatus
     */
    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}