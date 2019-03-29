package com.aoji.vo;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wangjunqiang
 * @description
 * @date Created in 2018/8/9 10:26
 */
public class ChannelStudentInfoVo {

    /**
     * 同业学生表id
     */
    private Integer ChannelStudentId;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 出生日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;


    /**
     * 出生日期
     */
    private String agentName;
    /**
     * 学生拼音
     */
    private String pinyin;

    /**
     * 学生学号
     */
    private String studentNo;

    /**
     * 签约日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date signDate;

    /**
     * 服务进程状态
     */
    private Integer status;

    /**
     * 国家名称
     */
    private String nationName;

    /**
     * 合同类型
     */
    private Integer contractType;

    /**
     * 文签顾问
     */
    private String copyOperator;

    /**
     * 审核状态
     */
    private Integer auditStatus;

    /**
     * 实际返佣
     */
    private BigDecimal rabeBackMoney;

    public Integer getChannelStudentId() {
        return ChannelStudentId;
    }

    public void setChannelStudentId(Integer channelStudentId) {
        ChannelStudentId = channelStudentId;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    public Integer getContractType() {
        return contractType;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
    }

    public String getCopyOperator() {
        return copyOperator;
    }

    public void setCopyOperator(String copyOperator) {
        this.copyOperator = copyOperator;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public BigDecimal getRabeBackMoney() {
        return rabeBackMoney;
    }

    public void setRabeBackMoney(BigDecimal rabeBackMoney) {
        this.rabeBackMoney = rabeBackMoney;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }
}
