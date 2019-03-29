package com.aoji.contants;

public enum AgentTypeEnum {
    AOJI("0", "与澳际签约"),
    AGENT("1", "与代理直接签约"),
    CHANNEL("2", "通过代理与澳际签约");

    private String code;

    private String name;

    AgentTypeEnum(String code, String name) {
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
        AgentTypeEnum[] agentTypeEnums = AgentTypeEnum.values();
        for(int i=0; i < agentTypeEnums.length; i++){
            if(agentTypeEnums[i].getCode().equals(code)){
                return agentTypeEnums[i].getName();
            }
        }
        return null;
    }

}
