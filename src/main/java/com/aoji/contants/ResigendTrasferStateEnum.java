package com.aoji.contants;
/**
 * author: chenhaibo
 * description: 离职转接类型
 * date: 2018/6/13
 */
public enum ResigendTrasferStateEnum {

    COPY_OPERATOR(1, "文签顾问"),
    COPY_MAKER(2, "制作文案"),
    OUTREACH(3, "外联顾问"),
    COPY(4, "文书员"),
    COPIER(5, "文案员"),
    VISA_OPERATOR(6, "业务员");

    private int code;

    private String name;

    private ResigendTrasferStateEnum(int code, String name){
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
