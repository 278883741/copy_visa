package com.aoji.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "student_settle_record")
public class StudentSettleRecord {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 关联student_settle_info表id
     */
    @Column(name = "settle_info_id")
    private Integer settleInfoId;

    /**
     * 学号
     */
    @Column(name = "student_no")
    private String studentNo;

    /**
     * 操作人工号
     */
    @Column(name = "operator_no")
    private String operatorNo;

    /**
     * 操作人姓名
     */
    @Column(name = "operator_name")
    private String operatorName;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 删除标识
     */
    @Column(name = "detele_status")
    private Boolean deteleStatus;

    /**
     * 获取id
     *
     * @return id - id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取关联student_settle_info表id
     *
     * @return settle_info_id - 关联student_settle_info表id
     */
    public Integer getSettleInfoId() {
        return settleInfoId;
    }

    /**
     * 设置关联student_settle_info表id
     *
     * @param settleInfoId 关联student_settle_info表id
     */
    public void setSettleInfoId(Integer settleInfoId) {
        this.settleInfoId = settleInfoId;
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
     * 获取操作人工号
     *
     * @return operator_no - 操作人工号
     */
    public String getOperatorNo() {
        return operatorNo;
    }

    /**
     * 设置操作人工号
     *
     * @param operatorNo 操作人工号
     */
    public void setOperatorNo(String operatorNo) {
        this.operatorNo = operatorNo;
    }

    /**
     * 获取操作人姓名
     *
     * @return operator_name - 操作人姓名
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * 设置操作人姓名
     *
     * @param operatorName 操作人姓名
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
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
     * 获取删除标识
     *
     * @return detele_status - 删除标识
     */
    public Boolean getDeteleStatus() {
        return deteleStatus;
    }

    /**
     * 设置删除标识
     *
     * @param deteleStatus 删除标识
     */
    public void setDeteleStatus(Boolean deteleStatus) {
        this.deteleStatus = deteleStatus;
    }
}