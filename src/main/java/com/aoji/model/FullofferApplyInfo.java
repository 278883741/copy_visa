package com.aoji.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "fulloffer_apply_info")
public class FullofferApplyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 内网学号
     */
    @Column(name = "student_no")
    private String studentNo;

    /**
     * 备注
     */
    @Column(name = "apply_comment")
    private String applyComment;

    /**
     * 申请状态：1-申请已提交 2-审核已通过 3-审核不通过
     */
    @Column(name = "apply_status")
    private Integer applyStatus;

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
     * 获取备注
     *
     * @return apply_comment - 备注
     */
    public String getApplyComment() {
        return applyComment;
    }

    /**
     * 设置备注
     *
     * @param applyComment 备注
     */
    public void setApplyComment(String applyComment) {
        this.applyComment = applyComment;
    }

    /**
     * 获取申请状态：1-申请已提交 2-审核已通过 3-审核不通过
     *
     * @return apply_status - 申请状态：1-申请已提交 2-审核已通过 3-审核不通过
     */
    public Integer getApplyStatus() {
        return applyStatus;
    }

    /**
     * 设置申请状态：1-申请已提交 2-审核已通过 3-审核不通过
     *
     * @param applyStatus 申请状态：1-申请已提交 2-审核已通过 3-审核不通过
     */
    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
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