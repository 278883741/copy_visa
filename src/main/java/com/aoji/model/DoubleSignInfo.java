package com.aoji.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "double_sign_info")
public class DoubleSignInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 双签类型
     */
    @Column(name = "double_sign_type")
    private String doubleSignType;

    /**
     * 双签类型Code
     */
    @Column(name = "double_sign_code")
    private Integer doubleSignCode;

    /**
     * 关联国家表ID
     */
    @Column(name = "nation_id")
    private Integer nationId;

    /**
     * 国家名称
     */
    @Column(name = "nation_name")
    private String nationName;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 删除状态
     */
    @Column(name = "delete_status")
    private Boolean deleteStatus;

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
     * 获取双签类型
     *
     * @return double_sign_type - 双签类型
     */
    public String getDoubleSignType() {
        return doubleSignType;
    }

    /**
     * 设置双签类型
     *
     * @param doubleSignType 双签类型
     */
    public void setDoubleSignType(String doubleSignType) {
        this.doubleSignType = doubleSignType;
    }

    /**
     * 获取双签类型Code
     *
     * @return double_sign_code - 双签类型Code
     */
    public Integer getDoubleSignCode() {
        return doubleSignCode;
    }

    /**
     * 设置双签类型Code
     *
     * @param doubleSignCode 双签类型Code
     */
    public void setDoubleSignCode(Integer doubleSignCode) {
        this.doubleSignCode = doubleSignCode;
    }

    /**
     * 获取关联国家表ID
     *
     * @return nation_id - 关联国家表ID
     */
    public Integer getNationId() {
        return nationId;
    }

    /**
     * 设置关联国家表ID
     *
     * @param nationId 关联国家表ID
     */
    public void setNationId(Integer nationId) {
        this.nationId = nationId;
    }

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取删除状态
     *
     * @return delete_status - 删除状态
     */
    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * 设置删除状态
     *
     * @param deleteStatus 删除状态
     */
    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}