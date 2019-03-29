package com.aoji.contants;

public enum CountryEnum {
    Australia(1,"澳大利亚"),
    NewZealand(2,"新西兰"),
    England (3, "英国"),
    America(4, "美国"),
    Canada(5, "加拿大");

    private int code;
    private String name;

    private CountryEnum(int code, String name){
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
