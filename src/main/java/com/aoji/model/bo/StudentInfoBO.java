package com.aoji.model.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import java.text.MessageFormat;
import java.util.Date;

public class StudentInfoBO {

    /**
     * 内网学号
     */
    @Column(name = "student_no")
    @ApiModelProperty(value = "学号")
    private String studentNo;

    /**
     * 学生姓名
     */
    @Column(name = "student_name")
    @ApiModelProperty(value = "学生姓名")
    private String studentName;

    /**
     * 合同编号
     */
    @Column(name = "contract_no")
    @ApiModelProperty(value = "合同名称")
    private String contractNo;

    /**
     * 合同类型
     */
    @ApiModelProperty(value = "合同类型")
    private String contractType;

    /**
     * 分支机构名称
     */
    @Column(name = "branch_name")
    @ApiModelProperty(value = "分支机构名称")
    private String branchName;

    /**
     * 销售顾问
     */
    @ApiModelProperty(value = "销售顾问")
    private String salesConsultant;

    /**
     * 销售顾问工号
     */
    @ApiModelProperty(value = "销售顾问工号")
    private String salesConsultantNo;

    /**
     * 文签顾问
     */
    @Column(name = "copy_operator")
    @ApiModelProperty(value = "文签顾问")
    private String copyOperator;

    /**
     * 文签顾问工号
     */
    @ApiModelProperty(value = "文签顾问工号")
    private String copyOperatorNo;

    /**
     * 制作文案
     */
    @ApiModelProperty(value = "制作文案")
    private String copyMaker;

    /**
     * 制作文案工号
     */
    @ApiModelProperty(value = "制作文案工号")
    private String copyMakerNo;

    /**
     * 留学国家名称
     */
    @Column(name = "nation_name")
    @ApiModelProperty(value = "留学国家名称")
    private String nationName;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "出生日期")
    private Date birthday;


    /**
     * 服务进程状态
     */
    @ApiModelProperty(value = "服务进程状态")
    private String statusName;

    /**
     * 首次寄出绩效审核状态
     */
    @ApiModelProperty(value = "首次寄出绩效审核状态")
    private String firstBonusStatus;

    /**
     * 最终寄出绩效审核状态
     */
    @ApiModelProperty(value = "最终寄出绩效审核状态")
    private String finallyBonusStatus;

    /**
     * 拼音
     */
    @Column(name = "pinyin")
    @ApiModelProperty(value = "拼音")
    private String pinyin;

    /**
     * 签约日期
     */
    @Column(name = "sign_date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "签约日期")
    private Date signDate;

    /**
     * 文案员工号
     */
    @Column(name = "copy_no")
    @ApiModelProperty(value = "文案员工号")
    private String copyNo;

    /**
     * 文案员
     */
    @Column(name = "copy")
    @ApiModelProperty(value = "文案员")
    private String copy;

    /**
     * 签证员工号
     */
    @Column(name = "visa_operator_no")
    @ApiModelProperty(value = "签证员工号")
    private String visaOperatorNo;

    /**
     * 签证员

     */
    @Column(name = "visa_operator")
    @ApiModelProperty(value = "签证员")
    private String visaOperator;

    /**
     * 文书员工号
     */
    @Column(name = "copier_no")
    @ApiModelProperty(value = "文书员工号")
    private String copierNo;

    /**
     * 文书员
     */
    @Column(name = "copier")
    @ApiModelProperty(value = "文书员")
    private String copier;

    /**
     * 最新回访时间
     */
    @Column(name = "last_visit_time")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "最近一次回访时间")
    private Date lastVisitTime;

    /**
     * 学生状态：1-正常 2-停办
     */
    @Column(name = "student_status")
    @ApiModelProperty(value = "学生状态")
    private String studentStatus;

    /**
     * 学生详情url
     */
    @ApiModelProperty(value = "学生详情页url")
    private String studentInfoURL;

    /**
     * 结案申请url
     */
//    private String settleApplyURL;

    public String getStudentInfoURL() {
        return MessageFormat.format(studentInfoURL, studentNo);
    }

    public void setStudentInfoURL(String studentInfoURL) {
        this.studentInfoURL = studentInfoURL;
    }

//    public String getSettleApplyURL() {
//        return settleApplyURL;
//    }
//
//    public void setSettleApplyURL(String settleApplyURL) {
//        this.settleApplyURL = settleApplyURL;
//    }

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

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getSalesConsultant() {
        return salesConsultant;
    }

    public void setSalesConsultant(String salesConsultant) {
        this.salesConsultant = salesConsultant;
    }

    public String getSalesConsultantNo() {
        return salesConsultantNo;
    }

    public void setSalesConsultantNo(String salesConsultantNo) {
        this.salesConsultantNo = salesConsultantNo;
    }

    public String getCopyOperator() {
        return copyOperator;
    }

    public void setCopyOperator(String copyOperator) {
        this.copyOperator = copyOperator;
    }

    public String getCopyOperatorNo() {
        return copyOperatorNo;
    }

    public void setCopyOperatorNo(String copyOperatorNo) {
        this.copyOperatorNo = copyOperatorNo;
    }

    public String getCopyMaker() {
        return copyMaker;
    }

    public void setCopyMaker(String copyMaker) {
        this.copyMaker = copyMaker;
    }

    public String getCopyMakerNo() {
        return copyMakerNo;
    }

    public void setCopyMakerNo(String copyMakerNo) {
        this.copyMakerNo = copyMakerNo;
    }

    public Date getLastVisitTime() {
        return lastVisitTime;
    }

    public void setLastVisitTime(Date lastVisitTime) {
        this.lastVisitTime = lastVisitTime;
    }

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getFirstBonusStatus() {
        return firstBonusStatus;
    }

    public void setFirstBonusStatus(String firstBonusStatus) {
        this.firstBonusStatus = firstBonusStatus;
    }

    public String getFinallyBonusStatus() {
        return finallyBonusStatus;
    }

    public void setFinallyBonusStatus(String finallyBonusStatus) {
        this.finallyBonusStatus = finallyBonusStatus;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public String getCopyNo() {
        return copyNo;
    }

    public void setCopyNo(String copyNo) {
        this.copyNo = copyNo;
    }

    public String getCopy() {
        return copy;
    }

    public void setCopy(String copy) {
        this.copy = copy;
    }

    public String getVisaOperatorNo() {
        return visaOperatorNo;
    }

    public void setVisaOperatorNo(String visaOperatorNo) {
        this.visaOperatorNo = visaOperatorNo;
    }

    public String getVisaOperator() {
        return visaOperator;
    }

    public void setVisaOperator(String visaOperator) {
        this.visaOperator = visaOperator;
    }

    public String getCopierNo() {
        return copierNo;
    }

    public void setCopierNo(String copierNo) {
        this.copierNo = copierNo;
    }

    public String getCopier() {
        return copier;
    }

    public void setCopier(String copier) {
        this.copier = copier;
    }

    public String getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(String studentStatus) {
        this.studentStatus = studentStatus;
    }
}
