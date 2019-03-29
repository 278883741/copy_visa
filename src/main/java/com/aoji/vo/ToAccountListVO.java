package com.aoji.vo;

import com.aoji.model.CommissionInvoice;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class ToAccountListVO extends CommissionInvoice{
    //签约时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date signDate;
    //到账时间 （yyyy-MM格式，存储查询条件，不做展示）
    @DateTimeFormat(pattern = "yyyy-MM")
    private Date returnDateCondition;
    //渠道返佣列表Id
    private Integer channelCommId;
    // 学生表id
    private Integer studentId;
    // 姓名
    private String studentName;
    // 拼音
    private String spelling;
    // 获签国家
    private String nationName;
    // 出生日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;
    // 机构名称
    private String agentName;
    // 机构ID
    private Integer agentId;
    // 获签日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date visaDate;
    // 合同名称
    private String contractName;
    // 合同链接
    private String contractUrl;
    // 院校名称
    private String schoolName;
    // 专业名称
    private String majorName;
    // 课程名称
    private String courseName;
    // 开课时间
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;

    //==============================================================================================
    // 获签数量
    private Integer getVisaSum;
    // 上次返佣比例
    private BigDecimal channelReturnRate;
    // 累计已返金额
    private BigDecimal returnMoney;
    // 应返MRB金额
    private BigDecimal returnMoneyCny;

    //付款日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private  Date paymentDate;

    //付款日期查询（开始时间）
    private  String paymentDateStart;

    //付款日期查询（结束时间）
    private  String paymentDateEnd;

    //备注
    private  String resultComment;

    // 跳转代理学生列表标志 true - 允许
    private boolean toAgentStudentListFlag;

    //本次应返金额
    @Transient
    private  BigDecimal thisReturnMoney;

    //上次标识时间
    private Date lastCreatTime;



    //

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSpelling() {
        return spelling;
    }

    public void setSpelling(String spelling) {
        this.spelling = spelling;
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

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Date getVisaDate() {
        return visaDate;
    }

    public void setVisaDate(Date visaDate) {
        this.visaDate = visaDate;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getGetVisaSum() {
        return getVisaSum;
    }

    public void setGetVisaSum(Integer getVisaSum) {
        this.getVisaSum = getVisaSum;
    }

    public BigDecimal getChannelReturnRate() {
        return channelReturnRate;
    }

    public void setChannelReturnRate(BigDecimal channelReturnRate) {
        this.channelReturnRate = channelReturnRate;
    }

    public BigDecimal getReturnMoney() {
        return returnMoney == null ? returnMoney : returnMoney.setScale(2, RoundingMode.HALF_UP);
    }

    public void setReturnMoney(BigDecimal returnMoney) {
        this.returnMoney = returnMoney;
    }

    public BigDecimal getReturnMoneyCny() {
        return returnMoneyCny == null ? returnMoneyCny : returnMoneyCny.setScale(2, RoundingMode.HALF_UP);
    }

    public void setReturnMoneyCny(BigDecimal returnMoneyCny) {
        this.returnMoneyCny = returnMoneyCny;
    }

    public Integer getChannelCommId() {
        return channelCommId;
    }

    public void setChannelCommId(Integer channelCommId) {
        this.channelCommId = channelCommId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentDateStart() {
        return paymentDateStart;
    }

    public void setPaymentDateStart(String paymentDateStart) {
        this.paymentDateStart = paymentDateStart;
    }

    public String getPaymentDateEnd() {
        return paymentDateEnd;
    }

    public void setPaymentDateEnd(String paymentDateEnd) {
        this.paymentDateEnd = paymentDateEnd;
    }

    public String getContractUrl() {
        return contractUrl;
    }

    public void setContractUrl(String contractUrl) {
        this.contractUrl = contractUrl;
    }

    public String getResultComment() {
        return resultComment;
    }

    public void setResultComment(String resultComment) {
        this.resultComment = resultComment;
    }

    public Date getReturnDateCondition() {
        return returnDateCondition;
    }

    public void setReturnDateCondition(Date returnDateCondition) {
        this.returnDateCondition = returnDateCondition;
    }

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    public boolean isToAgentStudentListFlag() {
        return toAgentStudentListFlag;
    }

    public void setToAgentStudentListFlag(boolean toAgentStudentListFlag) {
        this.toAgentStudentListFlag = toAgentStudentListFlag;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }



    public BigDecimal getThisReturnMoney() {
        return thisReturnMoney;
    }

    public void setThisReturnMoney(BigDecimal thisReturnMoney) {
        this.thisReturnMoney = thisReturnMoney;
    }


    public Date getLastCreatTime() {
        return lastCreatTime;
    }

    public void setLastCreatTime(Date lastCreatTime) {
        this.lastCreatTime = lastCreatTime;
    }
}
