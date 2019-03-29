package com.aoji.contants;

/**
 * @author Kong Lingji
 * @description 院校申请结果类型枚举类
 * @date Created in 下午3:30 2018/1/15
 */
public enum ApplyResultTypeEnum {

    OFFERRESULT_ACCEPT(1, "录取"),
    OFFERRESULT_REJECT(2, "拒绝"),
    OFFERRESULT_FULL(3, "满位"),
    SUPPLEMENTTYPE_WAITINGLIST(4, "waiting list"),
    SUPPLEMENTTYPE_PREACCEPT(5, "预录取");

    /**
     * 状态code
     */
    private int code;
    /**
     * 状态名称
     */
    private String name;

    ApplyResultTypeEnum(int index, String name) {
        this.name = name;
        this.code = index;
    }

    public static String getResultNameByType(Integer type){
        ApplyResultTypeEnum[] applyResultTypeEnums = ApplyResultTypeEnum.values();
        for(ApplyResultTypeEnum applyResultTypeEnum : applyResultTypeEnums){
            if(type.equals(applyResultTypeEnum.getCode())){
                return applyResultTypeEnum.getName();
            }
        }
        return null;
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
