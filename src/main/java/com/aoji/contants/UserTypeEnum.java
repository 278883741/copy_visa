package com.aoji.contants;

public enum UserTypeEnum {

    TY("TY", "同业"),

    VISA("WQ","文签");

    private String code;

    private String name;

    private UserTypeEnum(String code, String name){
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
