package com.aoji.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "message_queue_info")
public class MessageQueueInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 消息类型  发送 、接收
     */
    @Column(name = "type")
    private String type;

    /**
     * 关联业务ID
     */
    @Column(name = "business_key")
    private String businessKey;

    /**
     * 业务类型代码
     */
    @Column(name = "business_type_code")
    private String businessTypeCode;

    /**
     * 消息体 - JSON格式
     */
    @Column(name = "message_body")
    private String messageBody;

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
     * 下次重试时间
     */
    @Column(name = "next_retry_time")
    private Date nextRetryTime;

    /**
     * 重试次数
     */
    @Column(name = "retry_count")
    private Integer retryCount;

    /**
     * 发送状态  0-发送中/已接受（默认）；1-成功 ; 2-失败
     */
    @Column(name = "status")
    private Integer status;

//    /**
//     * 接收状态  0-接收消息成功（默认）；1-消费成功 ; 2-消费失败
//     */
//    @Column(name = "receive_status")
//    private Integer receiveStatus;

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

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    /**
     * 获取业务类型代码
     *
     * @return business_type_code - 业务类型代码
     */
    public String getBusinessTypeCode() {
        return businessTypeCode;
    }

    /**
     * 设置业务类型代码
     *
     * @param businessTypeCode 业务类型代码
     */
    public void setBusinessTypeCode(String businessTypeCode) {
        this.businessTypeCode = businessTypeCode;
    }

    /**
     * 获取消息体 - JSON格式
     *
     * @return message_body - 消息体 - JSON格式
     */
    public String getMessageBody() {
        return messageBody;
    }

    /**
     * 设置消息体 - JSON格式
     *
     * @param messageBody 消息体 - JSON格式
     */
    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
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
     * 获取下次重试时间
     *
     * @return next_retry_time - 下次重试时间
     */
    public Date getNextRetryTime() {
        return nextRetryTime;
    }

    /**
     * 设置下次重试时间
     *
     * @param nextRetryTime 下次重试时间
     */
    public void setNextRetryTime(Date nextRetryTime) {
        this.nextRetryTime = nextRetryTime;
    }

    /**
     * 获取重试次数
     *
     * @return retry_count - 重试次数
     */
    public Integer getRetryCount() {
        return retryCount;
    }

    /**
     * 设置重试次数
     *
     * @param retryCount 重试次数
     */
    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public Integer getReceiveStatus() {
//        return receiveStatus;
//    }
//
//    public void setReceiveStatus(Integer receiveStatus) {
//        this.receiveStatus = receiveStatus;
//    }
}