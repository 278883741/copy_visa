package com.aoji.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "transfer_info")
public class TransferInfo {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 院校申请id
     */
    @Column(name = "apply_id")
    private Integer applyId;

    /**
     * 学号
     */
    @Column(name = "student_no")
    private String studentNo;

    /**
     * 学生姓名
     */
    @Column(name = "student_name")
    private String studentName;

    /**
     * 转案类型  1-文签； 2外联
     */
    @Column(name = "transfer_type")
    private Integer transferType;

    /**
     * 所属国家组
     */
    @Column(name = "country_group")
    private Integer countryGroup;

    /**
     * 所属用户组
     */
    @Column(name = "user_group")
    private Integer userGroup;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 是否删除
     */
    @Column(name = "delete_status")
    private Boolean deleteStatus;

    /**
     * 转案说明
     */
    private String comment;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取院校申请id
     *
     * @return apply_id - 院校申请id
     */
    public Integer getApplyId() {
        return applyId;
    }

    /**
     * 设置院校申请id
     *
     * @param applyId 院校申请id
     */
    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
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

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * 获取转案类型  1-文签； 2外联
     *
     * @return transfer_type - 转案类型  1-文签； 2外联
     */
    public Integer getTransferType() {
        return transferType;
    }

    /**
     * 设置转案类型  1-文签； 2外联
     *
     * @param transferType 转案类型  1-文签； 2外联
     */
    public void setTransferType(Integer transferType) {
        this.transferType = transferType;
    }

    /**
     * 获取所属国家组
     *
     * @return country_group - 所属国家组
     */
    public Integer getCountryGroup() {
        return countryGroup;
    }

    /**
     * 设置所属国家组
     *
     * @param countryGroup 所属国家组
     */
    public void setCountryGroup(Integer countryGroup) {
        this.countryGroup = countryGroup;
    }

    /**
     * 获取所属用户组
     *
     * @return user_group - 所属用户组
     */
    public Integer getUserGroup() {
        return userGroup;
    }

    /**
     * 设置所属用户组
     *
     * @param userGroup 所属用户组
     */
    public void setUserGroup(Integer userGroup) {
        this.userGroup = userGroup;
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
     * 获取是否删除
     *
     * @return delete_status - 是否删除
     */
    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * 设置是否删除
     *
     * @param deleteStatus 是否删除
     */
    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    /**
     * 获取转案说明
     *
     * @return comment - 转案说明
     */
    public String getComment() {
        return comment;
    }

    /**
     * 设置转案说明
     *
     * @param comment 转案说明
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
}