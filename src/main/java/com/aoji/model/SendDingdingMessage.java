package com.aoji.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "send_dingding_message")
public class SendDingdingMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 学号
     */
    @Column(name = "student_no")
    private String studentNo;

    /**
     * 接收消息跳转url
     */
    @Column(name = "send_link")
    private String sendLink;

    /**
     * 发送时间
     */
    @Column(name = "send_time")
    private Date sendTime;

    /**
     * 发送状态: 0失败/1:成功
     */
    @Column(name = "send_status")
    private Boolean sendStatus;

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

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 钉钉消息内容
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 钉钉消息内容
     */
    private String content;

    /**
     * 钉钉消息来源：1-同业负责人审核 2-机构提交
     */
    private Integer type;
    /**
     * 钉钉发送消息接口及内容
     */
    @Transient
    private  String dingdingMessageApiMessage;
    /**
     * 接收人工号
     */
    @Transient
    private  String sendOaid;


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
     * 获取接收消息跳转url
     *
     * @return send_link - 接收消息跳转url
     */
    public String getSendLink() {
        return sendLink;
    }

    /**
     * 设置接收消息跳转url
     *
     * @param sendLink 接收消息跳转url
     */
    public void setSendLink(String sendLink) {
        this.sendLink = sendLink;
    }

    /**
     * 获取发送时间
     *
     * @return send_time - 发送时间
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * 设置发送时间
     *
     * @param sendTime 发送时间
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 获取发送状态: 0失败/1:成功
     *
     * @return send_status - 发送状态: 0失败/1:成功
     */
    public Boolean getSendStatus() {
        return sendStatus;
    }

    /**
     * 设置发送状态: 0失败/1:成功
     *
     * @param sendStatus 发送状态: 0失败/1:成功
     */
    public void setSendStatus(Boolean sendStatus) {
        this.sendStatus = sendStatus;
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

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取钉钉消息内容
     *
     * @return update_time - 钉钉消息内容
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置钉钉消息内容
     *
     * @param updateTime 钉钉消息内容
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取钉钉消息内容
     *
     * @return content - 钉钉消息内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置钉钉消息内容
     *
     * @param content 钉钉消息内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取钉钉消息来源：1-同业负责人审核 2-机构提交
     *
     * @return type - 钉钉消息来源：1-同业负责人审核 2-机构提交
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置钉钉消息来源：1-同业负责人审核 2-机构提交
     *
     * @param type 钉钉消息来源：1-同业负责人审核 2-机构提交
     */
    public void setType(Integer type) {
        this.type = type;
    }

    public String getDingdingMessageApiMessage() {
        return dingdingMessageApiMessage;
    }

    public void setDingdingMessageApiMessage(String dingdingMessageApiMessage) {
        this.dingdingMessageApiMessage = dingdingMessageApiMessage;
    }

    public String getSendOaid() {
        return sendOaid;
    }

    public void setSendOaid(String sendOaid) {
        this.sendOaid = sendOaid;
    }
}