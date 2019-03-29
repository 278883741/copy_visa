package com.aoji.contants;

/**
 * author: chenhaibo
 * description: 消息发送状态
 * date: 2018/10/10
 */
public enum MQStatusEnum {

    SEND_SENDING(0, "发送中"),
    SEND_SUCCESS(1, "发送成功"),
    SEND_FAILURE(2, "发送失败"),

    RECEIVE_SUCCESS(0, "接收成功"),
    CONSUME_SUCCESS(1, "消费成功"),
    CONSUME_FAILURE(2, "消费失败");

    private int code;
    private String name;

    private MQStatusEnum(int code, String name){
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
