package com.aoji.vo;
/**
 * @author huyanlong
 * @description
 * @date Created in 2018/9/4 17:12
 */
public class CalibrationSchemeVo {

    //国家Id
    private Integer  cid;
    //定校方案院校信息表Id
    private Integer  id;

    /**
     * 关联定校方案信息表id
     */
    private  Integer planId;

    /**
     * 国家名称
     */
    private String countryName;

    /**
     * 内网学号
     */
    private String studentNo;

    /**
     * 姓名
     */
    private String studentName;
    /**
     * 拼音
     */
    private String pinyin;

    /**
     * 申请院校名称
     */
    private String collegeName;

    /**
     * 申请专业名称
     */
    private String majorName;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 分支机构
     */
    private String branchName;
    /**
     * 咨询顾问
     */
    private String salesConsultant;

    /**
     * 文签顾问
     */
    private String copyOperator;

    /**
     * 审核状态：1-已递交 2-审核中 3-审核通过 4-审核不通过
     */
    private Integer auditStatus;

    /**
     * 确认状态 1-接受； 2-拒绝
     */
    private Integer confirmStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
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

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
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

    public Integer getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Integer confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }
}
