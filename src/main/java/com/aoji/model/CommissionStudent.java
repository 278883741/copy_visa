package com.aoji.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "commission_student")
public class CommissionStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 学号
     */
    @Column(name = "student_no")
    private String studentNo;

    /**
     * 姓名
     */
    @Column(name = "student_name")
    private String studentName;

    /**
     * 拼音
     */
    private String spelling;

    /**
     * 出生日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 合同类型
     */
    @Column(name = "contract_type")
    private String contractType;

    /**
     * 国家id
     */
    @Column(name = "nation_id")
    private Integer nationId;

    /**
     * 国家
     */
    @Column(name = "nation_name")
    private String nationName;

    /**
     * 分支机构
     */
    private String branch;

    /**
     * 代理类型
     */
    @Column(name = "agent_type")
    private String agentType;

    /**
     * 机构名称
     */
    @Column(name = "agent_name")
    private String agentName;

    /**
     * 学生来源
     */
    @Column(name = "student_source")
    private String studentSource;

    /**
     * 获签日期
     */
    @Column(name = "visa_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date visaDate;

    @Column(name = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 登记日期
     */
    @Column(name = "coe_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date coeDate;

    public Date getCoeDate() {
        return coeDate;
    }

    public void setCoeDate(Date coeDate) {
        this.coeDate = coeDate;
    }

    /**
     * 状态
     */
    private String status;

    /**
     * 备注
     */
    @Column(name = "student_remark")
    private String studentRemark;

    /**
     * 是否删除
     */
    @Column(name = "delete_status")
    private Boolean deleteStatus;

    @Column(name = "record_id")
    private String recordId;

    @Column(name = "record_date")
    private Date recordDate;

    @Column(name = "isValidate")
    private String isvalidate;


    /**
     * 澳洲代理
     */
    @Column(name = "aus_agent")
    private String ausAgent;

    /**
     * 澳洲代理id
     */
    @Column(name = "aus_agent_id")
    private Integer ausAgentId;

    /**
     * 签约日期
     */
    @Column(name = "sign_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date signDate;

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public String getStudentRemark() {
        return studentRemark;
    }

    public void setStudentRemark(String studentRemark) {
        this.studentRemark = studentRemark;
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
     * 获取姓名
     *
     * @return student_name - 姓名
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * 设置姓名
     *
     * @param studentName 姓名
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * 获取拼音
     *
     * @return spelling - 拼音
     */
    public String getSpelling() {
        return spelling;
    }

    /**
     * 设置拼音
     *
     * @param spelling 拼音
     */
    public void setSpelling(String spelling) {
        this.spelling = spelling;
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
     * 获取合同类型
     *
     * @return contract_type - 合同类型
     */
    public String getContractType() {
        return contractType;
    }

    /**
     * 设置合同类型
     *
     * @param contractType 合同类型
     */
    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    /**
     * 获取国家id
     *
     * @return nation_id - 国家id
     */
    public Integer getNationId() {
        return nationId;
    }

    /**
     * 设置国家id
     *
     * @param nationId 国家id
     */
    public void setNationId(Integer nationId) {
        this.nationId = nationId;
    }

    /**
     * 获取国家
     *
     * @return nation_name - 国家
     */
    public String getNationName() {
        return nationName;
    }

    /**
     * 设置国家
     *
     * @param nationName 国家
     */
    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    /**
     * 获取分支机构
     *
     * @return branch - 分支机构
     */
    public String getBranch() {
        return branch;
    }

    /**
     * 设置分支机构
     *
     * @param branch 分支机构
     */
    public void setBranch(String branch) {
        this.branch = branch;
    }

    /**
     * 获取代理类型
     *
     * @return agent_type - 代理类型
     */
    public String getAgentType() {
        return agentType;
    }

    /**
     * 设置代理类型
     *
     * @param agentType 代理类型
     */
    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    /**
     * 获取机构名称
     *
     * @return agent_name - 机构名称
     */
    public String getAgentName() {
        return agentName;
    }

    /**
     * 设置机构名称
     *
     * @param agentName 机构名称
     */
    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    /**
     * 获取学生来源
     *
     * @return student_source - 学生来源
     */
    public String getStudentSource() {
        return studentSource;
    }

    /**
     * 设置学生来源
     *
     * @param studentSource 学生来源
     */
    public void setStudentSource(String studentSource) {
        this.studentSource = studentSource;
    }

    /**
     * 获取获签日期
     *
     * @return visa_date - 获签日期
     */
    public Date getVisaDate() {
        return visaDate;
    }

    /**
     * 设置获签日期
     *
     * @param visaDate 获签日期
     */
    public void setVisaDate(Date visaDate) {
        this.visaDate = visaDate;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取状态
     *
     * @return status - 状态
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return studentRemark;
    }

    /**
     * 设置备注
     *
     * @param studentRemark 备注
     */
    public void setRemark(String studentRemark) {
        this.studentRemark = studentRemark;
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
     * @return record_id
     */
    public String getRecordId() {
        return recordId;
    }

    /**
     * @param recordId
     */
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    /**
     * @return record_date
     */
    public Date getRecordDate() {
        return recordDate;
    }

    /**
     * @param recordDate
     */
    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    /**
     * @return isValidate
     */
    public String getIsvalidate() {
        return isvalidate;
    }

    /**
     * @param isvalidate
     */
    public void setIsvalidate(String isvalidate) {
        this.isvalidate = isvalidate;
    }

    /**
     * 获取澳洲代理
     *
     * @return aus_agent - 澳洲代理
     */
    public String getAusAgent() {
        return ausAgent;
    }

    /**
     * 设置澳洲代理
     *
     * @param ausAgent 澳洲代理
     */
    public void setAusAgent(String ausAgent) {
        this.ausAgent = ausAgent;
    }

    /**
     * 获取澳洲代理id
     *
     * @return aus_agent_id - 澳洲代理id
     */
    public Integer getAusAgentId() {
        return ausAgentId;
    }

    /**
     * 设置澳洲代理id
     *
     * @param ausAgentId 澳洲代理id
     */
    public void setAusAgentId(Integer ausAgentId) {
        this.ausAgentId = ausAgentId;
    }
}