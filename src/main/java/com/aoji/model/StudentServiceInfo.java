package com.aoji.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "student_service_info")
public class StudentServiceInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 内网学号
     */
    @Column(name = "student_no")
    private String studentNo;

    /**
     * 服务id，对应service_info表id
     */
    @Column(name = "service_id")
    private Integer serviceId;

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
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

    /**
     * 服务是否完成
     */
    @Column(name = "complete_status")
    private Boolean completeStatus;

    /**
     * 服务是否完成
     */
    @Column(name = "remark")
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
     * 获取内网学号
     *
     * @return student_no - 内网学号
     */
    public String getStudentNo() {
        return studentNo;
    }

    /**
     * 设置内网学号
     *
     * @param studentNo 内网学号
     */
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    /**
     * 获取服务id，对应service_info表id
     *
     * @return service_id - 服务id，对应service_info表id
     */
    public Integer getServiceId() {
        return serviceId;
    }

    /**
     * 设置服务id，对应service_info表id
     *
     * @param serviceId 服务id，对应service_info表id
     */
    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
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
     * @return delete_time - 更新时间
     */
    public Date getDeleteTime() {
        return deleteTime;
    }

    /**
     * 设置更新时间
     *
     * @param deleteTime 更新时间
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
     * 获取服务是否完成
     *
     * @return complete_status - 服务是否完成
     */
    public Boolean getCompleteStatus() {
        return completeStatus;
    }

    /**
     * 设置服务是否完成
     *
     * @param completeStatus 服务是否完成
     */
    public void setCompleteStatus(Boolean completeStatus) {
        this.completeStatus = completeStatus;
    }
}