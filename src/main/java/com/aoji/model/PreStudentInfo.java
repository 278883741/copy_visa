package com.aoji.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "pre_student_info")
public class PreStudentInfo {
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
    @Column(name = "contract_type")
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
     * 创建日期
     */
    @Column(name = "create_time")
    private Date createTime;

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
     * 操作人
     */
    @Column(name = "operator_no")
    private String operatorNo;

    /**
     * 操作人
     */
    @Column(name = "operator_name")
    private String operatorName;

    /**
     * 留学国家
     */
    @Column(name = "nation_id")
    private Integer nationId;

    /**
     * 旧留学国家id
     */
    @Column(name = "old_nation_id")
    private Integer oldNationId;

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
     * 拼音
     */
    private String pinyin;

    /**
     * 签约日期
     */
    @Column(name = "sign_date")
    private Date signDate;

    /**
     * 预科班主任姓名
     */
    @Column(name = "headmaster_name")
    private String headmasterName;

    /**
     * 预科班主任工号
     */
    @Column(name = "headmaster_no")
    private String headmasterNo;

    /**
     * 是否美高：1-是 0-否
     */
    @Column(name = "usa_status")
    private Boolean usaStatus;

    /**
     * 学生服务进程
     */
    private Integer status;

    /**
     * 渠道标识：1-与代理直接签约 0-与公司签约  2-通过代理与澳际签约
     */
    @Column(name = "channel_status")
    private Integer channelStatus;

    @Column(name = "sales_consultant")
    private String salesConsultant;

    @Column(name = "sales_consultant_no")
    private String salesConsultantNo;

    /**
     * 合同条款编号
     */
    private String confeeid;

    /**
     * 是否已经转案
     */
    @Column(name = "is_transfer")
    private Integer isTransfer;

    /**
     * 是否已分配
     */
    @Column(name = "is_allot")
    private Integer isAllot;

    /**
     * 转案备注
     */
    @Column(name = "content")
    private String content;

//    public PreStudentInfo(PreStudentInfo preStudentInfo){
//        this.isAllot = 0;
//        this.isTransfer = 0;
//        this.createTime=new Date();
//        this.operatorNo=preStudentInfo.getOperatorNo();
//    }
//
//    public PreStudentInfo(){
//
//    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsAllot() {
        return isAllot;
    }

    public void setIsAllot(Integer isAllot) {
        this.isAllot = isAllot;
    }

    public Integer getIsTransfer() {
        return isTransfer;
    }

    public void setIsTransfer(Integer isTransfer) {
        this.isTransfer = isTransfer;
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

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
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
     * 获取合同类型
     *
     * @return contract_type - 合同类型
     */
    public Integer getContractType() {
        return contractType;
    }

    /**
     * 设置合同类型
     *
     * @param contractType 合同类型
     */
    public void setContractType(Integer contractType) {
        this.contractType = contractType;
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
     * 获取旧留学国家id
     *
     * @return old_nation_id - 旧留学国家id
     */
    public Integer getOldNationId() {
        return oldNationId;
    }

    /**
     * 设置旧留学国家id
     *
     * @param oldNationId 旧留学国家id
     */
    public void setOldNationId(Integer oldNationId) {
        this.oldNationId = oldNationId;
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

    /**
     * 获取预科班主任姓名
     *
     * @return headmaster_name - 预科班主任姓名
     */
    public String getHeadmasterName() {
        return headmasterName;
    }

    /**
     * 设置预科班主任姓名
     *
     * @param headmasterName 预科班主任姓名
     */
    public void setHeadmasterName(String headmasterName) {
        this.headmasterName = headmasterName;
    }

    /**
     * 获取预科班主任工号
     *
     * @return headmaster_no - 预科班主任工号
     */
    public String getHeadmasterNo() {
        return headmasterNo;
    }

    /**
     * 设置预科班主任工号
     *
     * @param headmasterNo 预科班主任工号
     */
    public void setHeadmasterNo(String headmasterNo) {
        this.headmasterNo = headmasterNo;
    }

    /**
     * 获取是否美高：1-是 0-否
     *
     * @return usa_status - 是否美高：1-是 0-否
     */
    public Boolean getUsaStatus() {
        return usaStatus;
    }

    /**
     * 设置是否美高：1-是 0-否
     *
     * @param usaStatus 是否美高：1-是 0-否
     */
    public void setUsaStatus(Boolean usaStatus) {
        this.usaStatus = usaStatus;
    }

    /**
     * 获取学生服务进程
     *
     * @return status - 学生服务进程
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置学生服务进程
     *
     * @param status 学生服务进程
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取渠道标识：1-与代理直接签约 0-与公司签约  2-通过代理与澳际签约
     *
     * @return channel_status - 渠道标识：1-与代理直接签约 0-与公司签约  2-通过代理与澳际签约
     */
    public Integer getChannelStatus() {
        return channelStatus;
    }

    /**
     * 设置渠道标识：1-与代理直接签约 0-与公司签约  2-通过代理与澳际签约
     *
     * @param channelStatus 渠道标识：1-与代理直接签约 0-与公司签约  2-通过代理与澳际签约
     */
    public void setChannelStatus(Integer channelStatus) {
        this.channelStatus = channelStatus;
    }

    /**
     * @return sales_consultant
     */
    public String getSalesConsultant() {
        return salesConsultant;
    }

    /**
     * @param salesConsultant
     */
    public void setSalesConsultant(String salesConsultant) {
        this.salesConsultant = salesConsultant;
    }

    /**
     * @return sales_consultant_no
     */
    public String getSalesConsultantNo() {
        return salesConsultantNo;
    }

    /**
     * @param salesConsultantNo
     */
    public void setSalesConsultantNo(String salesConsultantNo) {
        this.salesConsultantNo = salesConsultantNo;
    }

    /**
     * 获取合同条款编号
     *
     * @return confeeid - 合同条款编号
     */
    public String getConfeeid() {
        return confeeid;
    }

    /**
     * 设置合同条款编号
     *
     * @param confeeid 合同条款编号
     */
    public void setConfeeid(String confeeid) {
        this.confeeid = confeeid;
    }
}