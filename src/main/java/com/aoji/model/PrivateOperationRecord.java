package com.aoji.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "private_operation_record")
public class PrivateOperationRecord {
    /**
     * 隐私记录表id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

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
     * 操作类型：1-查看学生手机号
     */
    @Column(name = "operation_type")
    private Integer operationType;

    /**
     * 获取隐私记录表id
     *
     * @return id - 隐私记录表id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置隐私记录表id
     *
     * @param id 隐私记录表id
     */
    public void setId(Integer id) {
        this.id = id;
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
     * 获取操作类型：1-查看学生手机号
     *
     * @return operation_type - 操作类型：1-查看学生手机号
     */
    public Integer getOperationType() {
        return operationType;
    }

    /**
     * 设置操作类型：1-查看学生手机号
     *
     * @param operationType 操作类型：1-查看学生手机号
     */
    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }
}