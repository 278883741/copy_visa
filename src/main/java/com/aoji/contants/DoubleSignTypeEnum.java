package com.aoji.contants;

/**
 * 双签类型
 */
public enum DoubleSignTypeEnum {

    SINGLE("SINGLE", "单签", 0),
    AUS_NEW("AUS-NEW", "澳新", 1),
    UK_HK("UK_HK", "英港", 2),
    SG_HK("SG_HK", "新港", 3);

    /**
     * 双签类型
     */
    private String type;

    /**
     * 国家名称
     */
    private String nationName;

    /**
     * 国家状态 - 学生表
     */
    private int nationStatus;

    DoubleSignTypeEnum(String type, String nationName, int nationStatus) {
        this.type = type;
        this.nationName = nationName;
        this.nationStatus = nationStatus;
    }

    public static String getNationNameByType(String type){
        DoubleSignTypeEnum[] doubleSignTypeEnums = DoubleSignTypeEnum.values();
        for(DoubleSignTypeEnum doubleSignTypeEnum : doubleSignTypeEnums){
            if(doubleSignTypeEnum.getType().equals(type)){
                return doubleSignTypeEnum.getNationName();
            }
        }
        return null;
    }

    public static int getNationStatusByCode(String type){
        DoubleSignTypeEnum[] doubleSignTypeEnums = DoubleSignTypeEnum.values();
        for(DoubleSignTypeEnum doubleSignTypeEnum : doubleSignTypeEnums){
            if(doubleSignTypeEnum.getType().equals(type)){
                return doubleSignTypeEnum.getNationStatus();
            }
        }
        return SINGLE.getNationStatus();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    public int getNationStatus() {
        return nationStatus;
    }

    public void setNationStatus(int nationStatus) {
        this.nationStatus = nationStatus;
    }
}
