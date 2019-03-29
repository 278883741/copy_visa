package com.aoji.model;

import javax.persistence.*;

@Table(name = "old_transfer_record")
public class OldTransferRecord {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
     * 分配人工号
     */
    @Column(name = "send_no")
    private String sendNo;

    /**
     * 分配人姓名
     */
    @Column(name = "send_name")
    private String sendName;

    /**
     * 分配时间
     */
    @Column(name = "send_time")
    private String sendTime;

    /**
     * 接受人工号
     */
    @Column(name = "receive_no")
    private String receiveNo;

    /**
     * 接收人姓名
     */
    @Column(name = "receive_name")
    private String receiveName;

    /**
     * 接收时间
     */
    @Column(name = "receive_time")
    private String receiveTime;

    /**
     * 操作类型
     */
    @Column(name = "operator_type")
    private String operatorType;

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
     * 获取学生姓名
     *
     * @return student_name - 学生姓名
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * 设置学生姓名
     *
     * @param studentName 学生姓名
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * 获取分配人工号
     *
     * @return send_no - 分配人工号
     */
    public String getSendNo() {
        return sendNo;
    }

    /**
     * 设置分配人工号
     *
     * @param sendNo 分配人工号
     */
    public void setSendNo(String sendNo) {
        this.sendNo = sendNo;
    }

    /**
     * 获取分配人姓名
     *
     * @return send_name - 分配人姓名
     */
    public String getSendName() {
        return sendName;
    }

    /**
     * 设置分配人姓名
     *
     * @param sendName 分配人姓名
     */
    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    /**
     * 获取分配时间
     *
     * @return send_time - 分配时间
     */
    public String getSendTime() {
        return sendTime;
    }

    /**
     * 设置分配时间
     *
     * @param sendTime 分配时间
     */
    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 获取接受人工号
     *
     * @return receive_no - 接受人工号
     */
    public String getReceiveNo() {
        return receiveNo;
    }

    /**
     * 设置接受人工号
     *
     * @param receiveNo 接受人工号
     */
    public void setReceiveNo(String receiveNo) {
        this.receiveNo = receiveNo;
    }

    /**
     * 获取接收人姓名
     *
     * @return receive_name - 接收人姓名
     */
    public String getReceiveName() {
        return receiveName;
    }

    /**
     * 设置接收人姓名
     *
     * @param receiveName 接收人姓名
     */
    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    /**
     * 获取接收时间
     *
     * @return receive_time - 接收时间
     */
    public String getReceiveTime() {
        return receiveTime;
    }

    /**
     * 设置接收时间
     *
     * @param receiveTime 接收时间
     */
    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    /**
     * 获取操作类型
     *
     * @return operator_type - 操作类型
     */
    public String getOperatorType() {
        return operatorType;
    }

    /**
     * 设置操作类型
     *
     * @param operatorType 操作类型
     */
    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }
}