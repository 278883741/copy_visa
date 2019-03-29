package com.aoji.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "coe_attachment_info")
public class CoeAttachmentInfo {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 业务id，关联coe列表id
     */
    @Column(name = "business_id")
    private Integer businessId;

    /**
     * 申请id
     */
    @Column(name = "apply_id")
    private Integer applyId;

    /**
     * 学号
     */
    @Column(name = "student_no")
    private String studentNo;

    /**
     * 附件
     */
    private String attachment;

    /**
     * 1-申请复件，2-结果附件
     */
    @Column(name = "attachment_type")
    private Integer attachmentType;

    /**
     * 创建时间
     */
    @Column(name = "created_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;

    /**
     * 更新时间
     */
    @Column(name = "updated_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedTime;

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
     * 删除状态（默认为false）
     */
    @Column(name = "delete_status")
    private Boolean deleteStatus;

    /**
     * 获取id
     *
     * @return id - id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取业务id，关联coe列表id
     *
     * @return business_id - 业务id，关联coe列表id
     */
    public Integer getBusinessId() {
        return businessId;
    }

    /**
     * 设置业务id，关联coe列表id
     *
     * @param businessId 业务id，关联coe列表id
     */
    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    /**
     * 获取申请id
     *
     * @return apply_id - 申请id
     */
    public Integer getApplyId() {
        return applyId;
    }

    /**
     * 设置申请id
     *
     * @param applyId 申请id
     */
    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
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
     * 获取附件
     *
     * @return attachment - 附件
     */
    public String getAttachment() {
        return attachment;
    }

    /**
     * 设置附件
     *
     * @param attachment 附件
     */
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    /**
     * 获取1-申请复件，2-结果附件
     *
     * @return attachment_type - 1-申请复件，2-结果附件
     */
    public Integer getAttachmentType() {
        return attachmentType;
    }

    /**
     * 设置1-申请复件，2-结果附件
     *
     * @param attachmentType 1-申请复件，2-结果附件
     */
    public void setAttachmentType(Integer attachmentType) {
        this.attachmentType = attachmentType;
    }

    /**
     * 获取创建时间
     *
     * @return created_time - 创建时间
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * 设置创建时间
     *
     * @param createdTime 创建时间
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * 获取更新时间
     *
     * @return updated_time - 更新时间
     */
    public Date getUpdatedTime() {
        return updatedTime;
    }

    /**
     * 设置更新时间
     *
     * @param updatedTime 更新时间
     */
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
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
     * 获取删除状态（默认为false）
     *
     * @return delete_status - 删除状态（默认为false）
     */
    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * 设置删除状态（默认为false）
     *
     * @param deleteStatus 删除状态（默认为false）
     */
    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}