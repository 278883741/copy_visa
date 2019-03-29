package com.aoji.model;

import javax.persistence.*;

@Table(name = "country_info")
public class CountryInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 对应业务系统国家id，文签系统不采用此id
     */
    @Column(name = "country_bussid")
    private Integer countryBussid;

    /**
     * 国家名称
     */
    @Column(name = "country_name")
    private String countryName;

    /**
     * 国家组别：101:澳新组 102:英国组 103:加拿大组 104:美高组 105:美普组 106:亚洲组  107:欧洲组
     */
    @Column(name = "country_group")
    private Integer countryGroup;

    /**
     * 对应小希的国家id
     */
    @Column(name = "country_xiaoxi")
    private Integer countryXiaoxi;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取对应业务系统国家id，文签系统不采用此id
     *
     * @return country_bussid - 对应业务系统国家id，文签系统不采用此id
     */
    public Integer getCountryBussid() {
        return countryBussid;
    }

    /**
     * 设置对应业务系统国家id，文签系统不采用此id
     *
     * @param countryBussid 对应业务系统国家id，文签系统不采用此id
     */
    public void setCountryBussid(Integer countryBussid) {
        this.countryBussid = countryBussid;
    }

    /**
     * 获取国家名称
     *
     * @return country_name - 国家名称
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * 设置国家名称
     *
     * @param countryName 国家名称
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * 获取国家组别：101:澳新组 102:英国组 103:加拿大组 104:美高组 105:美普组 106:亚洲组  107:欧洲组
     *
     * @return country_group - 国家组别：101:澳新组 102:英国组 103:加拿大组 104:美高组 105:美普组 106:亚洲组  107:欧洲组
     */
    public Integer getCountryGroup() {
        return countryGroup;
    }

    /**
     * 设置国家组别：101:澳新组 102:英国组 103:加拿大组 104:美高组 105:美普组 106:亚洲组  107:欧洲组
     *
     * @param countryGroup 国家组别：101:澳新组 102:英国组 103:加拿大组 104:美高组 105:美普组 106:亚洲组  107:欧洲组
     */
    public void setCountryGroup(Integer countryGroup) {
        this.countryGroup = countryGroup;
    }

    /**
     * 获取对应小希的国家id
     *
     * @return country_xiaoxi - 对应小希的国家id
     */
    public Integer getCountryXiaoxi() {
        return countryXiaoxi;
    }

    /**
     * 设置对应小希的国家id
     *
     * @param countryXiaoxi 对应小希的国家id
     */
    public void setCountryXiaoxi(Integer countryXiaoxi) {
        this.countryXiaoxi = countryXiaoxi;
    }
}