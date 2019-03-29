package com.aoji.contants;
/**
 * author: chenhaibo
 * description: 后续类型枚举
 * date: 2018/3/5
 */
public enum FollowTypeEnum {

    GUARDIANSHIP(3, "监护"),
    INSURANCE(2, "保险"),
    PICK_UP(1, "接机"),
    MAJOR_STAY(4, "主课住宿"),
    LANGUAGE_STAY(5, "语言住宿");

    private int code;

    private String name;

    private FollowTypeEnum(int code, String name){
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
