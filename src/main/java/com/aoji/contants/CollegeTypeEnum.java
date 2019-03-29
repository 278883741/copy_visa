package com.aoji.contants;

public enum CollegeTypeEnum {

    MAIN("1", "主课"),
    LANGUAGE("2", "语言"),
    PREPARATORY("3", "预备");

    private String code;

    private String name;

    CollegeTypeEnum(String code, String name) {
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

    public static String getByCode(String code){
        CollegeTypeEnum[] collegeTypeEnums = CollegeTypeEnum.values();
        for(int i=0; i < collegeTypeEnums.length; i++){
            if(collegeTypeEnums[i].getCode().equals(code)){
                return collegeTypeEnums[i].getName();
            }
        }
        return null;
    }
}
