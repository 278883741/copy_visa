package com.aoji.contants;

/**
 * author: chenhaibo
 * description: MQ消息类型
 * date: 2018/10/19
 */
public enum MQTypeEnum {

    SEND("SEND", "发送消息"),
    RECEIVE("RECEIVE", "接收消息");

    private String code;
    private String name;

    private MQTypeEnum(String code, String name){
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
