package com.aoji.model;

/**
 * author: 赵剑飞
 * description: 根据学号与标识获取顾问或客服的回访内容
 * date: 2018/3/13
 */
public class Revisit {
    /**
     * 回访学号
     */
    private String sid;
    public void setSid(String sid) {
        this.sid = sid;
    }
    public String getSid() {
        return sid;
    }

    /**
     * 回访内容
     */
    private String content;
    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    /**
     * 回访人姓名
     */
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    /**
     * 回访时间
     */
    private String itime;
    public void setItime(String itime) {
        this.itime = itime;
    }
    public String getItime() {
        return itime;
    }
}
