package com.aoji.contants;

import org.springframework.util.StringUtils;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum CountryConfeeidEnum {
    Australia("1594","澳大利亚"),
    NewZealand("1597","新西兰"),
    England ("1601", "英国"),
    America("1649", "美国"),
    Canada("0", "加拿大");

    /**
     * Confeeid 合同条款编号
     */
    private String code;
    /**
     * 国家名称
     */
    private String name;

     CountryConfeeidEnum(String code, String name){
        this.code = code;
        this.name = name;
    }

    /**
     * 根据国家取Confeeid
     * @param name
     * @return
     */
    public static String getCodeByName(String name){
         CountryConfeeidEnum[] countryConfeeidEnums = CountryConfeeidEnum.values();
         for(CountryConfeeidEnum countryConfeeidEnum : countryConfeeidEnums){
             if(countryConfeeidEnum.getName().equals(name)){
                 return countryConfeeidEnum.getCode();
             }
         }
         return null;
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

}
