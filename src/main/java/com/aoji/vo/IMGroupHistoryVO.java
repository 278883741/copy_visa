package com.aoji.vo;

/**
 * author: chenhaibo
 * description: 群组历史消息视图类
 * date: 2019/1/3
 */
public class IMGroupHistoryVO {

    /**
     * 群组ID
     */
    private String groupId;
    /**
     * 发送者账号
     */
    private String fromAccount;
    /**
     * 内容
     */
    private String content;
    /**
     * 消息类型 1-文本 2-图片 3-文件
     */
    private Integer messageType;
    /**
     * 发送时间
     */
    private Long sendTime;
    /**
     * 消息时间戳
     */
    private Long msgTimeStamp;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    public Long getMsgTimeStamp() {
        return msgTimeStamp;
    }

    public void setMsgTimeStamp(Long msgTimeStamp) {
        this.msgTimeStamp = msgTimeStamp;
    }
}
