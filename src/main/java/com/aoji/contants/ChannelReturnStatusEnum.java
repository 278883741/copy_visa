package com.aoji.contants;

public enum ChannelReturnStatusEnum {

    UNCONFIRM("未确认"),
    CONFIRMED("已确认"),
    IDENTIFIED("已标识"),
    PAID("已付款");

    private String name;

    private ChannelReturnStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
