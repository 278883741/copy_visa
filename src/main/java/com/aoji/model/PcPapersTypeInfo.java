package com.aoji.model;

import javax.persistence.*;

@Table(name = "pc_papers_type_info")
public class PcPapersTypeInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 证件名称
     */
    @Column(name = "papers_name")
    private String papersName;

    /**
     * 是否必须上传
     */
    @Column(name = "need_upload")
    private Boolean needUpload;

    /**
     * 材料阶段：1申请材料 2签证材料
     */
    @Column(name = "papers_type")
    private Integer papersType;

    /**
     * 国家id
     */
    @Column(name = "country_id")
    private Integer countryId;

    /**
     * 是否为申请和签证共用材料
     */
    @Column(name = "is_public")
    private Boolean isPublic;

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
     * 获取证件名称
     *
     * @return papers_name - 证件名称
     */
    public String getPapersName() {
        return papersName;
    }

    /**
     * 设置证件名称
     *
     * @param papersName 证件名称
     */
    public void setPapersName(String papersName) {
        this.papersName = papersName;
    }

    /**
     * 获取是否必须上传
     *
     * @return need_upload - 是否必须上传
     */
    public Boolean getNeedUpload() {
        return needUpload;
    }

    /**
     * 设置是否必须上传
     *
     * @param needUpload 是否必须上传
     */
    public void setNeedUpload(Boolean needUpload) {
        this.needUpload = needUpload;
    }

    /**
     * 获取材料阶段：1申请材料 2签证材料
     *
     * @return papers_type - 材料阶段：1申请材料 2签证材料
     */
    public Integer getPapersType() {
        return papersType;
    }

    /**
     * 设置材料阶段：1申请材料 2签证材料
     *
     * @param papersType 材料阶段：1申请材料 2签证材料
     */
    public void setPapersType(Integer papersType) {
        this.papersType = papersType;
    }

    /**
     * 获取国家id
     *
     * @return country_id - 国家id
     */
    public Integer getCountryId() {
        return countryId;
    }

    /**
     * 设置国家id
     *
     * @param countryId 国家id
     */
    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    /**
     * 获取是否为申请和签证共用材料
     *
     * @return is_public - 是否为申请和签证共用材料
     */
    public Boolean getIsPublic() {
        return isPublic;
    }

    /**
     * 设置是否为申请和签证共用材料
     *
     * @param isPublic 是否为申请和签证共用材料
     */
    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }
}