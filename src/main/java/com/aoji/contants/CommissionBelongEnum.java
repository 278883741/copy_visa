package com.aoji.contants;

public enum CommissionBelongEnum {

    AEA("1", "AEA"),
    ECIE("2", "ECIE"),
    BAEC("3", "BAEC");

    private String code;

    private String name;

    CommissionBelongEnum(String code, String name) {
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
        CommissionBelongEnum[] commissionBelongEnums = CommissionBelongEnum.values();
        for(int i=0; i < commissionBelongEnums.length; i++){
            if(commissionBelongEnums[i].getCode().equals(code)){
                return commissionBelongEnums[i].getName();
            }
        }
        return null;
    }
}
