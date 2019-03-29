package com.aoji.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "student_note_relation")
public class StudentNoteRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 短信信息模板表id
     */
    @Column(name = "templete_id")
    private Integer templeteId;

    /**
     * 发送人工号
     */
    @Column(name = "send_oaid")
    private String sendOaid;

    /**
     * 发送人姓名
     */
    @Column(name = "send_name")
    private String sendName;

    /**
     * 接收学生学号
     */
    @Column(name = "receive_student_no")
    private String receiveStudentNo;

    /**
     * 接收学生姓名
     */
    @Column(name = "receive_student_name")
    private String receiveStudentName;

    /**
     * 接收学生电话号码
     */
    @Column(name = "receive_student_phoneNumber")
    private String receiveStudentPhoneNumber;

    /**
     * 接收信息
     */
    @Column(name = "receive_message")
    private String receiveMessage;

    /**
     * 跳转链接
     */
    @Column(name = "receive_url")
    private String receiveUrl;

    /**
     * 阅读状态 0:未读/1:已读
     */
    @Column(name = "read_status")
    private Boolean readStatus;

    /**
     * 发送时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 删除状态 0:未删除/1:删除
     */
    @Column(name = "delete_status")
    private Boolean deleteStatus;

    public String getReceiveStudentPhoneNumber() {
        return receiveStudentPhoneNumber;
    }

    public void setReceiveStudentPhoneNumber(String receiveStudentPhoneNumber) {
        this.receiveStudentPhoneNumber = receiveStudentPhoneNumber;
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
     * 获取短信信息模板表id
     *
     * @return templete_id - 短信信息模板表id
     */
    public Integer getTempleteId() {
        return templeteId;
    }

    /**
     * 设置短信信息模板表id
     *
     * @param templeteId 短信信息模板表id
     */
    public void setTempleteId(Integer templeteId) {
        this.templeteId = templeteId;
    }

    /**
     * 获取发送人工号
     *
     * @return send_oaid - 发送人工号
     */
    public String getSendOaid() {
        return sendOaid;
    }

    /**
     * 设置发送人工号
     *
     * @param sendOaid 发送人工号
     */
    public void setSendOaid(String sendOaid) {
        this.sendOaid = sendOaid;
    }

    /**
     * 获取发送人姓名
     *
     * @return send_name - 发送人姓名
     */
    public String getSendName() {
        return sendName;
    }

    /**
     * 设置发送人姓名
     *
     * @param sendName 发送人姓名
     */
    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    /**
     * 获取接收学生学号
     *
     * @return receive_student_no - 接收学生学号
     */
    public String getReceiveStudentNo() {
        return receiveStudentNo;
    }

    /**
     * 设置接收学生学号
     *
     * @param receiveStudentNo 接收学生学号
     */
    public void setReceiveStudentNo(String receiveStudentNo) {
        this.receiveStudentNo = receiveStudentNo;
    }

    /**
     * 获取接收学生姓名
     *
     * @return receive_student_name - 接收学生姓名
     */
    public String getReceiveStudentName() {
        return receiveStudentName;
    }

    /**
     * 设置接收学生姓名
     *
     * @param receiveStudentName 接收学生姓名
     */
    public void setReceiveStudentName(String receiveStudentName) {
        this.receiveStudentName = receiveStudentName;
    }

    /**
     * 获取接收信息
     *
     * @return receive_message - 接收信息
     */
    public String getReceiveMessage() {
        return receiveMessage;
    }

    /**
     * 设置接收信息
     *
     * @param receiveMessage 接收信息
     */
    public void setReceiveMessage(String receiveMessage) {
        this.receiveMessage = receiveMessage;
    }

    /**
     * 获取跳转链接
     *
     * @return receive_url - 跳转链接
     */
    public String getReceiveUrl() {
        return receiveUrl;
    }

    /**
     * 设置跳转链接
     *
     * @param receiveUrl 跳转链接
     */
    public void setReceiveUrl(String receiveUrl) {
        this.receiveUrl = receiveUrl;
    }

    /**
     * 获取阅读状态 0:未读/1:已读
     *
     * @return read_status - 阅读状态 0:未读/1:已读
     */
    public Boolean getReadStatus() {
        return readStatus;
    }

    /**
     * 设置阅读状态 0:未读/1:已读
     *
     * @param readStatus 阅读状态 0:未读/1:已读
     */
    public void setReadStatus(Boolean readStatus) {
        this.readStatus = readStatus;
    }

    /**
     * 获取发送时间
     *
     * @return create_time - 发送时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置发送时间
     *
     * @param createTime 发送时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取删除状态 0:未删除/1:删除
     *
     * @return delete_status - 删除状态 0:未删除/1:删除
     */
    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * 设置删除状态 0:未删除/1:删除
     *
     * @param deleteStatus 删除状态 0:未删除/1:删除
     */
    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}