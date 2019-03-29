package com.aoji.vo;

import com.aoji.model.PlanCollegeInfo;
import org.springframework.util.StringUtils;

import javax.persistence.Transient;

public class PlanCollegeInfoVO extends PlanCollegeInfo {

    /**
     * 内网学号
     */
    private String studentNo;

    /**
     * 申请类别：0-直录 1-双录 2-纯语言
     */
    private Integer applyType;

    /**
     * 合作机构
     */
    private String agency;

    /**
     * 留学国家
     */
    private Integer nation;

    /**
     * 国家名称
     */
    private String nationName;

    /**
     * 文签顾问工号
     */
    private String copyOperatorNo;

    /**
     * 是否为文签经理
     */
    private Boolean isCopyManager;
    /**
     * 姓名
     */
    private String studentName;
    /**
     * 分支机构
     */
    private String branchName;
    /**
     * 咨询顾问
     */
    private String salesConsultant;

    /**
     * 外联顾问工号
     */
    private String connector;

    /**
     * 外联顾问姓名
     */
    private String connectorName;

    private Integer hasConnector;

    private Integer studentConfirmStatus;

    /**
     * 是否加申
     */
    private Boolean addStatus;

    // =================================================================
    // 专业教育学段
    private String educationLevelName;
    // 课程备注
    private String courseRemarkName;
    // 定校国家名称
    private String collegeCountryName;

    public String getEducationLevelName() {
        return educationLevelName;
    }

    public void setEducationLevelName(String educationLevelName) {
        this.educationLevelName = educationLevelName;
    }

    public String getCourseRemarkName() {
        return courseRemarkName;
    }

    public void setCourseRemarkName(String courseRemarkName) {
        this.courseRemarkName = courseRemarkName;
    }

    public String getCollegeCountryName() {
        return collegeCountryName;
    }

    public void setCollegeCountryName(String collegeCountryName) {
        this.collegeCountryName = collegeCountryName;
    }

    // =================================================================

    @Override
    public Integer getStudentConfirmStatus() {
        return studentConfirmStatus;
    }

    @Override
    public void setStudentConfirmStatus(Integer studentConfirmStatus) {
        this.studentConfirmStatus = studentConfirmStatus;
    }

    @Transient
    public String auditUserId;
    public void setAuditUserId(String auditUserId) {
        this.auditUserId = auditUserId;
    }
    public String getAuditUserId() {
        return auditUserId;
    }

    @Transient
    private Integer nationId;
    public void setNationId(Integer nationId) {
        this.nationId = nationId;
    }
    public Integer getNationId() {
        return nationId;
    }

    @Transient
    private Integer usaStatus;
    public void setUsaStatus(Integer usaStatus) {
        this.usaStatus = usaStatus;
    }
    public Integer getUsaStatus() {
        return usaStatus;
    }

    @Transient
    private String planRole;
    public void setPlanRole(String planRole) {
        this.planRole = planRole;
    }
    public String getPlanRole() {
        return planRole;
    }
    @Transient
    private String currUserNo;
    public void setCurrUserNo(String currUserNo) {
        this.currUserNo = currUserNo;
    }
    public String getCurrUserNo() {
        return currUserNo;
    }

    public String getStudentNo() {
        if (!StringUtils.hasText(this.studentNo)) {
            return null;
        } else {
            return studentNo;
        }
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public Integer getNation() {
        return nation;
    }

    public void setNation(Integer nation) {
        this.nation = nation;
    }

    public String getCopyOperatorNo() {
        return copyOperatorNo;
    }

    public void setCopyOperatorNo(String copyOperatorNo) {
        this.copyOperatorNo = copyOperatorNo;
    }

    public Boolean getCopyManager() {
        return isCopyManager;
    }

    public void setCopyManager(Boolean copyManager) {
        isCopyManager = copyManager;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
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

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    public String getConnectorName() {
        return connectorName;
    }

    public void setConnectorName(String connectorName) {
        this.connectorName = connectorName;
    }

    public Integer getHasConnector() {
        return hasConnector;
    }

    public void setHasConnector(Integer hasConnector) {
        this.hasConnector = hasConnector;
    }

    public Boolean getAddStatus() {
        return addStatus;
    }

    public void setAddStatus(Boolean addStatus) {
        this.addStatus = addStatus;
    }

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }
}
