package com.aoji.contants.manager;

public class SearchOptionVisaApply {
    /**
     * 学号
     */
    private String studentNo;
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }
    public String getStudentNo() {
        return studentNo;
    }

    /**
     * 学生姓名
     */
    private String studentName;
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }
    public String getStudentName() {
        return studentName;
    }

    /**
     * 文签顾问
     */
    private String copyOperator;
    public void setCopyOperator(String copyOperator) {
        this.copyOperator = copyOperator;
    }
    public String getCopyOperator() {
        return copyOperator;
    }

    /**
     * 国家
     */
    private Integer nationId;
    public void setNationId(Integer nationId) {
        this.nationId = nationId;
    }
    public Integer getNationId() {
        return nationId;
    }

    /**
     * 分支
     */
    private Integer branchId;
    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }
    public Integer getBranchId() {
        return branchId;
    }



    /**
     * 是否审批
     */
    private Integer isAudited;
    public void setIsAudited(Integer isAudited) {
        this.isAudited = isAudited;
    }
    public Integer getIsAudited() {
        return isAudited;
    }

    /**
     * 签证分类
     */
    private Integer studentVisaStatus;
    public void setStudentVisaStatus(Integer studentVisaStatus) {
        this.studentVisaStatus = studentVisaStatus;
    }
    public Integer getStudentVisaStatus() {
        return studentVisaStatus;
    }

    /**
     * 送件时间
     */
    private String sendDateStart;
    public void setSendDateStart(String sendDateStart) {
        this.sendDateStart = sendDateStart;
    }
    public String getSendDateStart() {
        return sendDateStart;
    }

    private String sendDateEnd;
    public void setSendDateEnd(String sendDateEnd) {
        this.sendDateEnd = sendDateEnd;
    }
    public String getSendDateEnd() {
        return sendDateEnd;
    }

    /**
     * 创建时间（提交时间）
     */
    private String createDateStart;
    public void setCreateDateStart(String createDateStart) {
        this.createDateStart = createDateStart;
    }
    public String getCreateDateStart() {
        return createDateStart;
    }

    private String createDateEnd;
    public void setCreateDateEnd(String createDateEnd) {
        this.createDateEnd = createDateEnd;
    }
    public String getCreateDateEnd() {
        return createDateEnd;
    }



    /**
     * 是否含有签证结果
     */
    private Integer hasVisaResult;
    public void setHasVisaResult(Integer hasVisaResult) {
        this.hasVisaResult = hasVisaResult;
    }
    public Integer getHasVisaResult() {
        return hasVisaResult;
    }

    /**
     * 签证结果是否审批
     */
    private Integer visaResultIsAudited;
    public void setVisaResultIsAudited(Integer visaResultIsAudited) {
        this.visaResultIsAudited = visaResultIsAudited;
    }
    public Integer getVisaResultIsAudited() {
        return visaResultIsAudited;
    }

    /**
     * 签证结果提交时间
     */
    private String visaResultCreateDateStart;
    public void setVisaResultCreateDateStart(String visaResultCreateDateStart) {
        this.visaResultCreateDateStart = visaResultCreateDateStart;
    }
    public String getVisaResultCreateDateStart() {
        return visaResultCreateDateStart;
    }

    private String visaResultCreateDateEnd;
    public void setVisaResultCreateDateEnd(String visaResultCreateDateEnd) {
        this.visaResultCreateDateEnd = visaResultCreateDateEnd;
    }
    public String getVisaResultCreateDateEnd() {
        return visaResultCreateDateEnd;
    }
}
