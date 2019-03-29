package com.aoji.contants;

public enum ContractTypeEnum {

    ALL("1", "全程"),
    APPLICATION("2", "单申请"),
    COPY("3", "单文书"),
    VISA("4", "单签证"),
    PREPARATORY("5", "预科合同");

    private String code;

    private String name;

    ContractTypeEnum(String code, String name) {
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
        ContractTypeEnum[] contractTypeEnums = ContractTypeEnum.values();
        for(int i=0; i < contractTypeEnums.length; i++){
            if(contractTypeEnums[i].getCode().equals(code)){
                return contractTypeEnums[i].getName();
            }
        }
        return null;
    }
}
