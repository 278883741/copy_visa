package com.aoji.model;

import java.util.List;

/**
 * @author yangsaixing
 * @description 发送邮件实体类
 * @date Created in 下午1:22 2017/11/29
 */
public class MailEntity {

    /***
     * 邮件主题
     */
    private String subject;

    /***
     * 邮件发件人
     */
    private String sendAccount;

    /**
     * 发件人名称
     */
    private String sendName;

    /**
     * 发送人账号密码，若开启独立码，此处应是独立密码。
     */
    private String sendPassword;

    /***
     * 邮件抄送方
     */
    private List<String> ccAccounts;

    /***
     * 邮件收件人
     */
    private List<String> receiveAccounts;

    /**
     * 传输协议
     */
    private String protocol;

    /**
     * 邮件正文
     */
    private String content;

    /**
     * 附件集合
     */
    private List<String> attachments;

    public List<String> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<String> attachments) {
        this.attachments = attachments;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSendAccount() {
        return sendAccount;
    }

    public void setSendAccount(String sendAccount) {
        this.sendAccount = sendAccount;
    }

    public List<String> getCcAccounts() {
        return ccAccounts;
    }

    public void setCcAccounts(List<String> ccAccounts) {
        this.ccAccounts = ccAccounts;
    }

    public List<String> getReceiveAccounts() {
        return receiveAccounts;
    }

    public void setReceiveAccounts(List<String> receiveAccounts) {
        this.receiveAccounts = receiveAccounts;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getSendPassword() {
        return sendPassword;
    }

    public void setSendPassword(String sendPassword) {
        this.sendPassword = sendPassword;
    }
}
