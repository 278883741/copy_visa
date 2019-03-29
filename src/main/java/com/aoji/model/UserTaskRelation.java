package com.aoji.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_task_relation")
public class UserTaskRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 接收员工工号
     */
    private String oaid;

    /**
     * 任务模板id，对应task_template_info表id
     */
    @Column(name = "template_id")
    private Integer templateId;

    /**
     * 学生内网学号
     */
    @Column(name = "student_no")
    private String studentNo;

    /**
     * 是否已读
     */
    @Column(name = "read_status")
    private Boolean readStatus;

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
     * 任务级别：1-预警消息 2-审批消息 3-工作消息 4-公告信息
     */
    @Column(name = "task_type")
    private Integer taskType;

    /**
     * 消息内容
     */
    private String content;
    /**
     * 跳转url
     */
    private String url;
//    private String url;
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
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
     * 获取接收员工工号
     *
     * @return oaid - 接收员工工号
     */
    public String getOaid() {
        return oaid;
    }

    /**
     * 设置接收员工工号
     *
     * @param oaid 接收员工工号
     */
    public void setOaid(String oaid) {
        this.oaid = oaid;
    }

    /**
     * 获取任务模板id，对应task_template_info表id
     *
     * @return template_id - 任务模板id，对应task_template_info表id
     */
    public Integer getTemplateId() {
        return templateId;
    }

    /**
     * 设置任务模板id，对应task_template_info表id
     *
     * @param templateId 任务模板id，对应task_template_info表id
     */
    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    /**
     * 获取学生内网学号
     *
     * @return student_no - 学生内网学号
     */
    public String getStudentNo() {
        return studentNo;
    }

    /**
     * 设置学生内网学号
     *
     * @param studentNo 学生内网学号
     */
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    /**
     * 获取是否已读
     *
     * @return read_status - 是否已读
     */
    public Boolean getReadStatus() {
        return readStatus;
    }

    /**
     * 设置是否已读
     *
     * @param readStatus 是否已读
     */
    public void setReadStatus(Boolean readStatus) {
        this.readStatus = readStatus;
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
     * 获取任务级别：1-预警消息 2-审批消息 3-工作消息 4-公告信息
     *
     * @return task_type - 任务级别：1-预警消息 2-审批消息 3-工作消息 4-公告信息
     */
    public Integer getTaskType() {
        return taskType;
    }

    /**
     * 设置任务级别：1-预警消息 2-审批消息 3-工作消息 4-公告信息
     *
     * @param taskType 任务级别：1-预警消息 2-审批消息 3-工作消息 4-公告信息
     */
    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}