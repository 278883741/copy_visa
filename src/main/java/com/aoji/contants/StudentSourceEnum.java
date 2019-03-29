package com.aoji.contants;

public enum StudentSourceEnum {

    WALK_IN("1", "Walk-in"),
    AOJI("3", "国内澳际"),
    SUB_AGENT("2", "Sub-agent");

    private String code;

    private String name;

    StudentSourceEnum(String code, String name) {
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
        StudentSourceEnum[] studentSourceEnums = StudentSourceEnum.values();
        for(int i=0; i < studentSourceEnums.length; i++){
            if(studentSourceEnums[i].getCode().equals(code)){
                return studentSourceEnums[i].getName();
            }
        }
        return null;
    }

}
