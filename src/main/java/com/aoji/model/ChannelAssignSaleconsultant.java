package com.aoji.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "channel_assign_saleconsultant")
public class ChannelAssignSaleconsultant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 学号
     */
    @Column(name = "student_no")
    private String studentNo;

    /**
     * 学生名称
     */
    @Column(name = "student_name")
    private String studentName;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 原咨询顾问名称
     */
    @Column(name = "old_sales_consultant")
    private String oldSalesConsultant;

    /**
     * 原咨询顾问工号
     */
    @Column(name = "old_sales_consultant_no")
    private String oldSalesConsultantNo;

    /**
     * 新咨询顾问名称
     */
    @Column(name = "new_sales_consultant")
    private String newSalesConsultant;

    /**
     * 新咨询顾问工号
     */
    @Column(name = "new_sales_consultant_no")
    private String newSalesConsultantNo;

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
     * 操作人名称
     */
    @Column(name = "operator_name")
    private String operatorName;

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
     * 获取学生名称
     *
     * @return student_name - 学生名称
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * 设置学生名称
     *
     * @param studentName 学生名称
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
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
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取原咨询顾问名称
     *
     * @return old_sales_consultant - 原咨询顾问名称
     */
    public String getOldSalesConsultant() {
        return oldSalesConsultant;
    }

    /**
     * 设置原咨询顾问名称
     *
     * @param oldSalesConsultant 原咨询顾问名称
     */
    public void setOldSalesConsultant(String oldSalesConsultant) {
        this.oldSalesConsultant = oldSalesConsultant;
    }

    /**
     * 获取原咨询顾问工号
     *
     * @return old_sales_consultant_no - 原咨询顾问工号
     */
    public String getOldSalesConsultantNo() {
        return oldSalesConsultantNo;
    }

    /**
     * 设置原咨询顾问工号
     *
     * @param oldSalesConsultantNo 原咨询顾问工号
     */
    public void setOldSalesConsultantNo(String oldSalesConsultantNo) {
        this.oldSalesConsultantNo = oldSalesConsultantNo;
    }

    /**
     * 获取新咨询顾问名称
     *
     * @return new_sales_consultant - 新咨询顾问名称
     */
    public String getNewSalesConsultant() {
        return newSalesConsultant;
    }

    /**
     * 设置新咨询顾问名称
     *
     * @param newSalesConsultant 新咨询顾问名称
     */
    public void setNewSalesConsultant(String newSalesConsultant) {
        this.newSalesConsultant = newSalesConsultant;
    }

    /**
     * 获取新咨询顾问工号
     *
     * @return new_sales_consultant_no - 新咨询顾问工号
     */
    public String getNewSalesConsultantNo() {
        return newSalesConsultantNo;
    }

    /**
     * 设置新咨询顾问工号
     *
     * @param newSalesConsultantNo 新咨询顾问工号
     */
    public void setNewSalesConsultantNo(String newSalesConsultantNo) {
        this.newSalesConsultantNo = newSalesConsultantNo;
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
     * 获取操作人名称
     *
     * @return operator_name - 操作人名称
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * 设置操作人名称
     *
     * @param operatorName 操作人名称
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
}