package com.aoji.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "channel_student_info")
public class ChannelStudentInfo {

    public ChannelStudentInfo() {

    }
    public ChannelStudentInfo(Boolean deleteStatus,Integer id) {
        this.deleteStatus = deleteStatus;
        this.id = id;
    }



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
     * 合同类型 1-全程； 2-单申请； 3-单文书； 4-单签证
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 拼音
     */
    private String pinyin;

    /**
     * 是否美高：1-是 0-否
     */
    @Column(name = "usa_status")
    private Integer usaStatus;

    /**
     * 性别：1-男 2-女
     */
    private Integer gender;

    /**
     * 代理qq
     */
    @Column(name = "agent_qq")
    private String agentQq;

    /**
     * 代理id
     */
    @Column(name = "agent_id")
    private Integer agentId;

    /**
     * 代理名称
     */
    @Column(name = "agent_name")
    private String agentName;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 学生序号
     */
    @Column(name = "student_sort")
    private String studentSort;

    /**
     * 渠道标识：1-与代理直接签约（同业） 0-与公司签约  2-通过代理与澳际签约（渠道）
     */
    @Column(name = "channel_status")
    private Integer channelStatus;

    /**
     * 备注
     */
    private String remark;

    /**
     * 审核状态：1-未提交 2-未审核 3-已审核
     */
    @Column(name = "audit_status")
    private Integer auditStatus;

    /**
     * 签约日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "sign_date")
    private Date signDate;

    /**
     * 学号
     */
    @Column(name = "student_no")
    private String studentNo;

    @Column(name="transfer_remark")
    private String transferRemark;
    public void setTransferRemark(String transferRemark) {
        this.transferRemark = transferRemark;
    }
    public String getTransferRemark() {
        return transferRemark;
    }

    @Column(name="agent_confirm")
    private boolean agentConfirm;
    public void setAgentConfirm(boolean agentConfirm) {
        this.agentConfirm = agentConfirm;
    }
    public boolean isAgentConfirm() {
        return agentConfirm;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
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
     * 获取合同类型 1-全程； 2-单申请； 3-单文书； 4-单签证
     *
     * @return contract_type - 合同类型 1-全程； 2-单申请； 3-单文书； 4-单签证
     */
    public Integer getContractType() {
        return contractType;
    }

    /**
     * 设置合同类型 1-全程； 2-单申请； 3-单文书； 4-单签证
     *
     * @param contractType 合同类型 1-全程； 2-单申请； 3-单文书； 4-单签证
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
     * 获取是否美高：1-是 0-否
     *
     * @return usa_status - 是否美高：1-是 0-否
     */
    public Integer getUsaStatus() {
        return usaStatus;
    }

    /**
     * 设置是否美高：1-是 0-否
     *
     * @param usaStatus 是否美高：1-是 0-否
     */
    public void setUsaStatus(Integer usaStatus) {
        this.usaStatus = usaStatus;
    }

    /**
     * 获取性别：1-男 2-女
     *
     * @return gender - 性别：1-男 2-女
     */
    public Integer getGender() {
        return gender;
    }

    /**
     * 设置性别：1-男 2-女
     *
     * @param gender 性别：1-男 2-女
     */
    public void setGender(Integer gender) {
        this.gender = gender;
    }

    /**
     * 获取代理qq
     *
     * @return agent_qq - 代理qq
     */
    public String getAgentQq() {
        return agentQq;
    }

    /**
     * 设置代理qq
     *
     * @param agentQq 代理qq
     */
    public void setAgentQq(String agentQq) {
        this.agentQq = agentQq;
    }

    /**
     * 获取代理id
     *
     * @return agent_id - 代理id
     */
    public Integer getAgentId() {
        return agentId;
    }

    /**
     * 设置代理id
     *
     * @param agentId 代理id
     */
    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    /**
     * 获取代理名称
     *
     * @return agent_name - 代理名称
     */
    public String getAgentName() {
        return agentName;
    }

    /**
     * 设置代理名称
     *
     * @param agentName 代理名称
     */
    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    /**
     * 获取电子邮箱
     *
     * @return email - 电子邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置电子邮箱
     *
     * @param email 电子邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取学生序号
     *
     * @return student_sort - 学生序号
     */
    public String getStudentSort() {
        return studentSort;
    }

    /**
     * 设置学生序号
     *
     * @param studentSort 学生序号
     */
    public void setStudentSort(String studentSort) {
        this.studentSort = studentSort;
    }

    /**
     * 获取渠道标识：1-与代理直接签约（同业） 0-与公司签约  2-通过代理与澳际签约（渠道）
     *
     * @return channel_status - 渠道标识：1-与代理直接签约（同业） 0-与公司签约  2-通过代理与澳际签约（渠道）
     */
    public Integer getChannelStatus() {
        return channelStatus;
    }

    /**
     * 设置渠道标识：1-与代理直接签约（同业） 0-与公司签约  2-通过代理与澳际签约（渠道）
     *
     * @param channelStatus 渠道标识：1-与代理直接签约（同业） 0-与公司签约  2-通过代理与澳际签约（渠道）
     */
    public void setChannelStatus(Integer channelStatus) {
        this.channelStatus = channelStatus;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取审核状态：1-未提交 2-未审核 3-已审核
     *
     * @return audit_status - 审核状态：1-未提交 2-未审核 3-已审核
     */
    public Integer getAuditStatus() {
        return auditStatus;
    }

    /**
     * 设置审核状态：1-未提交 2-未审核 3-已审核
     *
     * @param auditStatus 审核状态：1-未提交 2-未审核 3-已审核
     */
    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }
}