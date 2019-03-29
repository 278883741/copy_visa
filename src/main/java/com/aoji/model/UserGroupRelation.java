package com.aoji.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_group_relation")
public class UserGroupRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 员工工号
     */
    private String oaid;

    /**
     * 是否是组负责人
     */
    @Column(name = "leader_status")
    private Boolean leaderStatus;

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

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

    /**
     * 小组id
     */
    @Column(name = "group_id")
    private Integer groupId;

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
     * 获取员工工号
     *
     * @return oaid - 员工工号
     */
    public String getOaid() {
        return oaid;
    }

    /**
     * 设置员工工号
     *
     * @param oaid 员工工号
     */
    public void setOaid(String oaid) {
        this.oaid = oaid;
    }

    /**
     * 获取是否是组负责人
     *
     * @return leader_status - 是否是组负责人
     */
    public Boolean getLeaderStatus() {
        return leaderStatus;
    }

    /**
     * 设置是否是组负责人
     *
     * @param leaderStatus 是否是组负责人
     */
    public void setLeaderStatus(Boolean leaderStatus) {
        this.leaderStatus = leaderStatus;
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
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
     * 获取小组id
     *
     * @return group_id - 小组id
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * 设置小组id
     *
     * @param groupId 小组id
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}