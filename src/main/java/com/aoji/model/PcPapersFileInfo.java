package com.aoji.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "pc_papers_file_info")
public class PcPapersFileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 关联国家ID
     */
    @Column(name = "country_id")
    private Integer countryId;

    /**
     * 关联证件类型ID
     */
    @Column(name = "papers_id")
    private Integer papersId;

    /**
     * 学号
     */
    @Column(name = "student_no")
    private String studentNo;

    /**
     * 文件路径
     */
    private String url;

    /**
     * 删除状态0为未删除/可用，1为已删除/不可用
     */
    @Column(name = "delete_status")
    private Boolean deleteStatus;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 删除时间
     */
    @Column(name = "delete_time")
    private Date deleteTime;

    /**
     * 上传文件原名称
     */
    @Column(name = "file_name")
    private String fileName;

    /**
     * 1-申请材料 2-签证材料
     */
    @Column(name = "papers_type")
    private Integer papersType;

    @Column(name = "contract_no")
    private String contractNo;

    @Transient
    private String createTimeString;

    public String getCreateTimeString() {
        return createTimeString;
    }

    public void setCreateTimeString(String createTimeString) {
        this.createTimeString = createTimeString;
    }

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
     * 获取关联国家ID
     *
     * @return country_id - 关联国家ID
     */
    public Integer getCountryId() {
        return countryId;
    }

    /**
     * 设置关联国家ID
     *
     * @param countryId 关联国家ID
     */
    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    /**
     * 获取关联证件类型ID
     *
     * @return papers_id - 关联证件类型ID
     */
    public Integer getPapersId() {
        return papersId;
    }

    /**
     * 设置关联证件类型ID
     *
     * @param papersId 关联证件类型ID
     */
    public void setPapersId(Integer papersId) {
        this.papersId = papersId;
    }

    /**
     * 获取学号
     *
     * @return student_no - 学号
     */
    public String getStudentNo() {
        return studentNo;
    }

    /**
     * 设置学号
     *
     * @param studentNo 学号
     */
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    /**
     * 获取文件路径
     *
     * @return url - 文件路径
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置文件路径
     *
     * @param url 文件路径
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取删除状态0为未删除/可用，1为已删除/不可用
     *
     * @return delete_status - 删除状态0为未删除/可用，1为已删除/不可用
     */
    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * 设置删除状态0为未删除/可用，1为已删除/不可用
     *
     * @param deleteStatus 删除状态0为未删除/可用，1为已删除/不可用
     */
    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
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
     * 获取删除时间
     *
     * @return delete_time - 删除时间
     */
    public Date getDeleteTime() {
        return deleteTime;
    }

    /**
     * 设置删除时间
     *
     * @param deleteTime 删除时间
     */
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    /**
     * 获取上传文件原名称
     *
     * @return file_name - 上传文件原名称
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置上传文件原名称
     *
     * @param fileName 上传文件原名称
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 获取1-申请材料 2-签证材料
     *
     * @return papers_type - 1-申请材料 2-签证材料
     */
    public Integer getPapersType() {
        return papersType;
    }

    /**
     * 设置1-申请材料 2-签证材料
     *
     * @param papersType 1-申请材料 2-签证材料
     */
    public void setPapersType(Integer papersType) {
        this.papersType = papersType;
    }

    /**
     * @return contract_no
     */
    public String getContractNo() {
        return contractNo;
    }

    /**
     * @param contractNo
     */
    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }
}