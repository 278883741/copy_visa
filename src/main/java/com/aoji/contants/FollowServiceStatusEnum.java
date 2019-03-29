package com.aoji.contants;

public enum FollowServiceStatusEnum {

    SUBMITTED(1, "已提交"),
    ACCEPTED(2, "已录取"),
    REFUSED(3, "已拒绝");

    private int code;

    private String name;

    private FollowServiceStatusEnum(int code, String name){
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
