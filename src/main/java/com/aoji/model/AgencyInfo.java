package com.aoji.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "agency_info")
public class AgencyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 合作机构名称
     */
    @Column(name = "agency_name")
    private String agencyName;

    /**
     * 备注
     */
    private String content;

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    private Date createTime;
    @Transient
    private String createTime_string;
    public void setCreateTime_string(String createTime_string) {
        this.createTime_string = createTime_string;
    }
    public String getCreateTime_string() {
        return createTime_string;
    }

    /**
     * 删除时间/失效时间
     */
    @Column(name = "delete_time")
    private Date deleteTime;

    /**
     * 删除状态0为未删除/可用，1为已删除/不可用
     */
    @Column(name = "delete_status")
    private Boolean deleteStatus;

    /**
     * 操作人
     */
    @Column(name = "operator_no")
    private String operatorNo;
    @Transient
    private String operatorName;
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
    public String getOperatorName() {
        return operatorName;
    }
    /**
     * 机构类型：1-接机 2-保险 3-监护 4-住宿 5-院校
     */
    @Column(name = "agency_type")
    private Integer agencyType;
    @Transient
    private String agencyType_string;
    public void setAgencyType_string(String agencyType_string) {
        this.agencyType_string = agencyType_string;
    }
    public String getAgencyType_string() {
        return agencyType_string;
    }

    /**
     * 适用国家，关联国家信息表id
     */
    @Column(name = "nation_id")
    private String nationId;
    @Transient
    private String nationName;
    public void setNationName(String nationName) {
        this.nationName = nationName;
    }
    public String getNationName() {
        return nationName;
    }
    @Transient
    private  String nationNameMore;
    public void setNationNameMore(String nationNameMore) {
        this.nationNameMore = nationNameMore;
    }
    public String getNationNameMore() {
        return nationNameMore;
    }

    /**
     * 是否启用：1-启用 0-禁用
     */
    @Column(name = "enable_status")
    private Integer enableStatus;

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
     * 获取合作机构名称
     *
     * @return agency_name - 合作机构名称
     */
    public String getAgencyName() {
        return agencyName;
    }

    /**
     * 设置合作机构名称
     *
     * @param agencyName 合作机构名称
     */
    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    /**
     * 获取备注
     *
     * @return content - 备注
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置备注
     *
     * @param content 备注
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取创建日期
     *
     * @return create_time - 创建日期
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建日期
     *
     * @param createTime 创建日期
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取删除时间/失效时间
     *
     * @return delete_time - 删除时间/失效时间
     */
    public Date getDeleteTime() {
        return deleteTime;
    }

    /**
     * 设置删除时间/失效时间
     *
     * @param deleteTime 删除时间/失效时间
     */
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
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
     * 获取操作人
     *
     * @return operator_no - 操作人
     */
    public String getOperatorNo() {
        return operatorNo;
    }

    /**
     * 设置操作人
     *
     * @param operatorNo 操作人
     */
    public void setOperatorNo(String operatorNo) {
        this.operatorNo = operatorNo;
    }

    /**
     * 获取机构类型：1-接机 2-保险 3-监护 4-住宿 5-院校
     *
     * @return agency_type - 机构类型：1-接机 2-保险 3-监护 4-住宿 5-院校
     */
    public Integer getAgencyType() {
        return agencyType;
    }

    /**
     * 设置机构类型：1-接机 2-保险 3-监护 4-住宿 5-院校
     *
     * @param agencyType 机构类型：1-接机 2-保险 3-监护 4-住宿 5-院校
     */
    public void setAgencyType(Integer agencyType) {
        this.agencyType = agencyType;
    }

    /**
     * 获取适用国家，关联国家信息表id
     *
     * @return nation_id - 适用国家，关联国家信息表id
     */
    public String getNationId() {
        return nationId;
    }

    /**
     * 设置适用国家，关联国家信息表id
     *
     * @param nationId 适用国家，关联国家信息表id
     */
    public void setNationId(String nationId) {
        this.nationId = nationId;
    }

    /**
     * 获取是否启用：1-启用 0-禁用
     *
     * @return enable_status - 是否启用：1-启用 0-禁用
     */
    public Integer getEnableStatus() {
        return enableStatus;
    }

    /**
     * 设置是否启用：1-启用 0-禁用
     *
     * @param enableStatus 是否启用：1-启用 0-禁用
     */
    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }
}