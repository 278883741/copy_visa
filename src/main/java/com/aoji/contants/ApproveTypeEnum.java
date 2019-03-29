package com.aoji.contants;

/**
 * author: chenhaibo
 * description: 审批类型
 * date: 2018/3/7
 */
public enum ApproveTypeEnum {

    PASSED(2, "通过"),
    REFUSED(1, "拒绝");

    private int code;

    private String name;

    private ApproveTypeEnum(int code, String name){
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
