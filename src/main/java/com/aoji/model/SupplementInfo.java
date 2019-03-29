package com.aoji.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "supplement_info")
public class SupplementInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 申请id
     */
    @Column(name = "apply_id")
    private Integer applyId;

    /**
     * 寄件类型：1-首次递交 2-补件 3-邮寄最终成绩单/疫苗表日期 4-邮寄录取包裹
     */
    @Column(name = "supplement_type")
    private Integer supplementType;

    /**
     * 补件内容
     */
    private String content;

    /**
     * 寄出日期
     */
    @Column(name = "send_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sendDate;
    @Transient
    private String sendDate_string;
    public void setSendDate_string(String sendDate_string) {
        this.sendDate_string = sendDate_string;
    }
    public String getSendDate_string() {
        return sendDate_string;
    }

    /**
     * 快递公司编号
     */
    @Column(name = "express_company_code")
    private String expressCompanyCode;

    /**
     * 快递公司名称
     */
    @Column(name = "express_company_name")
    private String expressCompanyName;

    /**
     * 快递单号
     */
    @Column(name = "express_no")
    private String expressNo;

    /**
     * 补件附件地址
     */
    @Column(name = "supplement_attachment")
    private String supplementAttachment;

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
    @Transient
    private String deleteTime_string;
    public void setDeleteTime_string(String deleteTime_string) {
        this.deleteTime_string = deleteTime_string;
    }
    public String getDeleteTime_string() {
        return deleteTime_string;
    }

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

    @Column(name = "operator_name")
    private String operatorName;
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * 获取时间
     */
    @Column(name = "receive_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date receiveDate;
    @Transient
    private String receiveDate_string;
    public void setReceiveDate_string(String receiveDate_string) {
        this.receiveDate_string = receiveDate_string;
    }
    public String getReceiveDate_string() {
        return receiveDate_string;
    }

    public SupplementInfo(){}

    public SupplementInfo(Integer applyId,Integer supplementType){
        this.applyId=applyId;
        this.supplementType=supplementType;
        this.deleteStatus=false;
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
     * 获取申请id
     *
     * @return apply_id - 申请id
     */
    public Integer getApplyId() {
        return applyId;
    }

    /**
     * 设置申请id
     *
     * @param applyId 申请id
     */
    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    /**
     * 获取寄件类型：1-首次递交 2-补件 3-邮寄最终成绩单/疫苗表日期 4-邮寄录取包裹
     *
     * @return supplement_type - 寄件类型：1-首次递交 2-补件 3-邮寄最终成绩单/疫苗表日期 4-邮寄录取包裹
     */
    public Integer getSupplementType() {
        return supplementType;
    }

    /**
     * 设置寄件类型：1-首次递交 2-补件 3-邮寄最终成绩单/疫苗表日期 4-邮寄录取包裹
     *
     * @param supplementType 寄件类型：1-首次递交 2-补件 3-邮寄最终成绩单/疫苗表日期 4-邮寄录取包裹
     */
    public void setSupplementType(Integer supplementType) {
        this.supplementType = supplementType;
    }

    /**
     * 获取补件内容
     *
     * @return content - 补件内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置补件内容
     *
     * @param content 补件内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取寄出日期
     *
     * @return send_date - 寄出日期
     */
    public Date getSendDate() {
        return sendDate;
    }

    /**
     * 设置寄出日期
     *
     * @param sendDate 寄出日期
     */
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    /**
     * 获取快递公司编号
     *
     * @return express_company_code - 快递公司编号
     */
    public String getExpressCompanyCode() {
        return expressCompanyCode;
    }

    /**
     * 设置快递公司编号
     *
     * @param expressCompanyCode 快递公司编号
     */
    public void setExpressCompanyCode(String expressCompanyCode) {
        this.expressCompanyCode = expressCompanyCode;
    }

    /**
     * 获取快递公司名称
     *
     * @return express_company_name - 快递公司名称
     */
    public String getExpressCompanyName() {
        return expressCompanyName;
    }

    /**
     * 设置快递公司名称
     *
     * @param expressCompanyName 快递公司名称
     */
    public void setExpressCompanyName(String expressCompanyName) {
        this.expressCompanyName = expressCompanyName;
    }

    /**
     * 获取快递单号
     *
     * @return express_no - 快递单号
     */
    public String getExpressNo() {
        return expressNo;
    }

    /**
     * 设置快递单号
     *
     * @param expressNo 快递单号
     */
    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    /**
     * 获取补件附件地址
     *
     * @return supplement_attachment - 补件附件地址
     */
    public String getSupplementAttachment() {
        return supplementAttachment;
    }

    /**
     * 设置补件附件地址
     *
     * @param supplementAttachment 补件附件地址
     */
    public void setSupplementAttachment(String supplementAttachment) {
        this.supplementAttachment = supplementAttachment;
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
     * 获取获取时间
     *
     * @return receive_date - 删除时间/失效时间
     */
    public Date getReceiveDate() {
        return receiveDate;
    }

    /**
     * 设置获取时间
     *
     * @param receiveDate 获取时间
     */
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
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
}