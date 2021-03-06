package com.aoji.vo;

import com.aoji.model.PageParam;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Table(name = "student_info")
public class StudentInfoVo extends PageParam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 内网学号
     */
    @Column(name = "student_no")
    private String studentNo;

    /**
     * 学生姓名
     */
    @Column(name = "student_name")
    private String studentName;

    /**
     * 合同编号
     */
    @Column(name = "contract_no")
    private String contractNo;

    /**
     * 合同类型
     */
    private Integer contractType;

    /**
     * 分支机构
     */
    @Column(name = "branch_id")
    private Integer branchId;

    /**
     * 分支机构名称
     */
    @Column(name = "branch_name")
    private String branchName;

    /**
     * 销售顾问
     */
    private String salesConsultant;

    /**
     * 销售顾问工号
     */
    private String salesConsultantNo;

    private String confeeid;

    public String getConfeeid() {
        return confeeid;
    }

    public void setConfeeid(String confeeid) {
        this.confeeid = confeeid;
    }

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 是否为渠道文案
     */
    private Integer channelStatus;

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
     * 学生材料
     */
    @Column(name = "student_material")
    private String studentMaterial;

    /**
     * 电子邮箱账户
     */
    @Column(name = "email_account")
    private String emailAccount;

    /**
     * 电子邮箱密码
     */
    @Column(name = "email_password")
    private String emailPassword;

    /**
     * 规划顾问工号
     */
    @Column(name = "planning_consultant_no")
    private String planningConsultantNo;

    public String getPlanningConsultantNo() {
        return planningConsultantNo;
    }

    public void setPlanningConsultantNo(String planningConsultantNo) {
        this.planningConsultantNo = planningConsultantNo;
    }

    /**
     * 文签顾问
     */
    @Column(name = "copy_operator")
    private String copyOperator;

    /**
     * 文签顾问工号
     */
    private String copyOperatorNo;

    /**
     * 销售顾问
     */
    @Transient
    private String connector;

    /**
     * 制作文案
     */
    private String copyMaker;

    /**
     * 制作文案工号
     */
    private String copyMakerNo;

    /**
     * 是否为美高
     */
    private Integer usaStatus;

    /**
     * 最新回访时间
     */
    @Column(name = "last_visit_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date lastVisitTime;

    /**
     * 注册小希状态0为已注册，1为未注册
     */
    @Column(name = "register_status")
    private Boolean registerStatus;

    public Boolean getRegisterStatus() {
        return registerStatus;
    }

    public void setRegisterStatus(Boolean registerStatus) {
        this.registerStatus = registerStatus;
    }

    public Date getLastVisitTime() {
        return lastVisitTime;
    }

    public void setLastVisitTime(Date lastVisitTime) {
        this.lastVisitTime = lastVisitTime;
    }



    public Integer getUsaStatus() {
        return usaStatus;
    }

    public void setUsaStatus(Integer usaStatus) {
        this.usaStatus = usaStatus;
    }

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    public String getCopyOperator() {
        return copyOperator;
    }

    public void setCopyOperator(String copyOperator) {
        this.copyOperator = copyOperator;
    }

    public String getEmailAccount() {
        return emailAccount;
    }

    public void setEmailAccount(String emailAccount) {
        this.emailAccount = emailAccount;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    public String getStudentMaterial() {
        return studentMaterial;
    }

    public void setStudentMaterial(String studentMaterial) {
        this.studentMaterial = studentMaterial;
    }

    /**
     * 操作人
     */
    @Column(name = "operator_no")
    private String operatorNo;

    /**
     * 留学国家
     */
    @Column(name = "nation_id")
    private Integer nationId;

    /**
     * 留学国家名称
     */
    @Column(name = "nation_name")
    private String nationName;

    /**
     * 出生日期
     */
    private Date birthday;


    /**
     * 服务进程状态
     */
    private Integer status;

    /**
     * 首次寄出绩效审核状态
     */
    private Integer firstBonusStatus;

    /**
     * 最终寄出绩效审核状态
     */
    private Integer finallyBonusStatus;

    /**
     * 拼音
     */
    @Column(name = "pinyin")
    private String pinyin;

    /**
     * 签约日期
     */
    @Column(name = "sign_date")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date signDate;

    /**
     * 文案员工号
     */
    @Column(name = "copy_no")
    private String copyNo;

    /**
     * 文案员
     */
    @Column(name = "copy")
    private String copy;

    /**
     * 签证员工号
     */
    @Column(name = "visa_operator_no")
    private String visaOperatorNo;

    /**
     * 签证员

     */
    @Column(name = "visa_operator")
    private String visaOperator;

    /**
     * 文书员工号
     */
    @Column(name = "copier_no")
    private String copierNo;

    /**
     * 文书员
     */
    @Column(name = "copier")
    private String copier;

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


    @Transient
    private String birthdayString;
    public String getBirthdayString(){ return birthdayString; }
    public void setBirthdayString(String birthdayString){ this.birthdayString = birthdayString; }

    @Transient
    private String signDateString;
    public String getSignDateString(){ return signDateString; }
    public void setSignDateString(String signDateString){ this.signDateString = signDateString; }

    //签约开始日期
    @Transient
    private String dateStart;
    public String getDateStart(){ return dateStart; }
    public void setDateStart(String dateStart){ this.dateStart = dateStart; }

    //签约结束日期
    @Transient
    private String dateEnd;
    public String getDateEnd(){ return dateEnd; }
    public void setDateEnd(String dateEnd){ this.dateEnd = dateEnd; }

    /* 首次递签日期 */
    @Transient
    private String visaSendDateStart;
    public void setVisaSendDateStart(String visaSendDateStart) {
        this.visaSendDateStart = visaSendDateStart;
    }
    public String getVisaSendDateStart() {
        return visaSendDateStart;
    }
    @Transient
    private String visaSendDateEnd;
    public void setVisaSendDateEnd(String visaSendDateEnd) {
        this.visaSendDateEnd = visaSendDateEnd;
    }
    public String getVisaSendDateEnd() {
        return visaSendDateEnd;
    }

    /* 分配文签顾问日期 */
    @Transient
    private String visaOperatorDateStart;
    public void setVisaOperatorDateStart(String visaOperatorDateStart) {
        this.visaOperatorDateStart = visaOperatorDateStart;
    }
    public String getVisaOperatorDateStart() {
        return visaOperatorDateStart;
    }
    @Transient
    private String visaOperatorDateEnd;
    public void setVisaOperatorDateEnd(String visaOperatorDateEnd) {
        this.visaOperatorDateEnd = visaOperatorDateEnd;
    }
    public String getVisaOperatorDateEnd() {
        return visaOperatorDateEnd;
    }

    //高级查询生日，代理名称
    @Transient
    private String birthdayStart;
    @Transient
    private String birthdayEnd;
    @Transient
    private String agencyName;

    public String getBirthdayStart() {
        return birthdayStart;
    }

    public void setBirthdayStart(String birthdayStart) {
        this.birthdayStart = birthdayStart;
    }

    public String getBirthdayEnd() {
        return birthdayEnd;
    }

    public void setBirthdayEnd(String birthdayEnd) {
        this.birthdayEnd = birthdayEnd;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

//    //最近一次回访日期
//    @Transient
//    private String lastVisitDate;
//    public String getLastVisitDate(){ return lastVisitDate; }
//    public void setLastVisitDate(String lastVisitDate){ this.lastVisitDate = lastVisitDate; }

    /**
     * 学生状态：1-正常 2-缓办 3-停办
     */
    @Column(name = "student_status")
    private Integer  studentStatus;

    private String headmasterName;

    private String headmasterNo;

    public String getHeadmasterName() {
        return headmasterName;
    }

    public void setHeadmasterName(String headmasterName) {
        this.headmasterName = headmasterName;
    }

    public String getHeadmasterNo() {
        return headmasterNo;
    }

    public void setHeadmasterNo(String headmasterNo) {
        this.headmasterNo = headmasterNo;
    }

    public Integer getStudentStatus() {
        return studentStatus;
    }

    public void setStudentStatus(Integer studentStatus) {
        this.studentStatus = studentStatus;
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
     * 获取合同编号
     *
     * @return contract_no - 合同编号
     */
    public String getContractNo() {
        return contractNo;
    }

    /**
     * 设置合同编号
     *
     * @param contractNo 合同编号
     */
    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    /**
     * 获取分支机构
     *
     * @return branch_id - 分支机构
     */
    public Integer getBranchId() {
        return branchId;
    }

    /**
     * 设置分支机构
     *
     * @param branchId 分支机构
     */
    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    /**
     * 获取分支机构名称
     *
     * @return branch_name - 分支机构名称
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * 设置分支机构名称
     *
     * @param branchName 分支机构名称
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
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
     * 获取留学国家
     *
     * @return nation_id - 留学国家
     */
    public Integer getNationId() {
        return nationId;
    }

    /**
     * 设置留学国家
     *
     * @param nationId 留学国家
     */
    public void setNationId(Integer nationId) {
        this.nationId = nationId;
    }

    /**
     * 获取留学国家名称
     *
     * @return nation_name - 留学国家名称
     */
    public String getNationName() {
        return nationName;
    }

    /**
     * 设置留学国家名称
     *
     * @param nationName 留学国家名称
     */
    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    /**
     * 获取出生日期
     *
     * @return birthday - 出生日期
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 设置出生日期
     *
     * @param birthday 出生日期
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取服务进程状态
     *
     * @return status - 服务进程状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置服务进程状态
     *
     * @param status 服务进程状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取首次寄出绩效审核状态
     *
     * @return status - 服务进程状态
     */
    public Integer getFirstBonusStatus() {
        return firstBonusStatus;
    }

    /**
     * 设置首次寄出绩效审核状态
     *
     * @param firstBonusStatus 服务进程状态
     */
    public void setFirstBonusStatus(Integer firstBonusStatus) {
        this.firstBonusStatus = firstBonusStatus;
    }

    /**
     * 获取最终寄出绩效审核状态
     *
     * @return status - 服务进程状态
     */
    public Integer getFinallyBonusStatus() {
        return finallyBonusStatus;
    }

    /**
     * 设置最终寄出绩效审核状态
     *
     * @param finallyBonusStatus 服务进程状态
     */
    public void setFinallyBonusStatus(Integer finallyBonusStatus) {
        this.finallyBonusStatus = finallyBonusStatus;
    }

    /**
     * 获取拼音
     *
     * @return pinyin - 拼音
     */
    public String getPinyin() {
        return pinyin;
    }

    /**
     * 设置拼音
     *
     * @param pinyin 拼音
     */
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    /**
     * 获取签约日期
     *
     * @return sign_date - 签约日期
     */
    public Date getSignDate() {
        return signDate;
    }

    /**
     * 设置签约日期
     *
     * @param signDate 签约日期
     */
    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public String getCopyMaker() {
        return copyMaker;
    }

    public void setCopyMaker(String copyMaker) {
        this.copyMaker = copyMaker;
    }

    public String getCopyOperatorNo() {
        return copyOperatorNo;
    }

    public void setCopyOperatorNo(String copyOperatorNo) {
        this.copyOperatorNo = copyOperatorNo;
    }

    public String getCopyMakerNo() {
        return copyMakerNo;
    }

    public void setCopyMakerNo(String copyMakerNo) {
        this.copyMakerNo = copyMakerNo;
    }

    public Integer getContractType() {
        return contractType;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
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

    public Integer getChannelStatus() {
        return channelStatus;
    }

    public void setChannelStatus(Integer channelStatus) {
        this.channelStatus = channelStatus;
    }
}