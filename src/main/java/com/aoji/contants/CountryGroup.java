package com.aoji.contants;

import java.util.HashMap;

/**
 * 国家组枚举类
 */
public enum CountryGroup {
    GROUP_CHANNEL(99,"渠道组", Contants.CHANNEL_GROUP),
    GROUP_AYUSTRALIA_NEW(101,"澳新组", Contants.AYUSTRALIA_NEW),
    GROUP_ENGLAND(102, "英国组", Contants.ENGLAND),
    GROUP_CANADA(103, "加拿大组", Contants.CANADA),
    GROUP_AMERICA_H(104, "美高组", Contants.AMERICA_H),
    GROUP_AMERICA_C(105, "美普组", Contants.AMERICA_C),
    GROUP_ASIA(106, "亚洲组", Contants.ASIA),
    GROUP_EUROPE(107, "欧洲组", Contants.EUROPE),
    GROUP_PLAN(88,"规划组", Contants.PLAN);


    /**
     * 状态code
     */
    private int code;
    /**
     * 状态名称
     */
    private String name;
    /**
     * 服务代码
     */
    private String serviceCode;

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

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    CountryGroup(int index, String name, String serviceCode) {
        this.name = name;
        this.code = index;
        this.serviceCode = serviceCode;
    }
    /**
     * 根据状态code查询状态名称
     * @param code
     * @return
     */
    public static String getNameByCode(int code){
        CountryGroup[] studentStatuses = CountryGroup.values();
        for(CountryGroup ss : studentStatuses){
            if(ss.getCode() == code){
                return ss.getName();
            }
        }
        return null;
    }


    public final static HashMap<Integer, String> map = new HashMap<Integer, String>();
    public static HashMap<Integer, String> get(){
        if(map.size() == 0){
            map.put(101, "澳新组");
            map.put(102, "英国组");
            map.put(103, "加拿大组");
            map.put(104, "美高组");
            map.put(105, "美普组");
            map.put(106, "亚洲组");
            map.put(107, "欧洲组");
        }
        return map;
    }
}
