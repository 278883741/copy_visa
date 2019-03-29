package com.aoji.vo;

/**
 * @author wangjunqiang
 * @description
 * @date Created in 2018/4/21 11:13
 */
public class WorkCountVO {

    /**
     * 我提交的-待审批的总数
     */
    private Integer applyResultCount;
    /**
     * 我提交的-待审批的签证申请的总数
     */
    private Integer visaApplyCount;
    /**
     * 我提交的-待审批的获签信息的总数
     */
    private Integer visaRecordCount;
    /**
     * 我提交的-待审批的签证结果的总数
     */
    private Integer visaResultCount;
    /**
     * 我提交的-待审批的停办申请的总数
     */
    private Integer studentDelayCount;
    /**
     * 我提交的-待审批的结案申请的总数
     */
    private Integer schoolConfirmCount;

    /**
     * 待我审批 - 签证申请
     */
    private Integer toAuditVisaApplyCount;
    public void setToAuditVisaApplyCount(Integer toAuditVisaApplyCount) {
        this.toAuditVisaApplyCount = toAuditVisaApplyCount;
    }
    public Integer getToAuditVisaApplyCount() {
        return toAuditVisaApplyCount;
    }

    /**
     * 待我审批 - 获签信息
     */
    private Integer toAuditVisaRecordCount;
    public void setToAuditVisaRecordCount(Integer toAuditVisaRecordCount) {
        this.toAuditVisaRecordCount = toAuditVisaRecordCount;
    }
    public Integer getToAuditVisaRecordCount() {
        return toAuditVisaRecordCount;
    }

    /**
     * 待我审批-获签信息
     */
    private Integer toAuditCOECount;
    /**
     * 待我审批-COE/CAS/I-20
     */
    private Integer studentSettleCount;

    /**
     * 我提交的待审批首次寄出
     */
    private Integer myFirstBonusUnAuditCount;

    /**
     * 待我审批的首次寄出
     */
    private Integer FirstBonusToAuditCount;

    public Integer getFirstBonusToAuditCount() {
        return FirstBonusToAuditCount;
    }

    public void setFirstBonusToAuditCount(Integer firstBonusToAuditCount) {
        FirstBonusToAuditCount = firstBonusToAuditCount;
    }

    public Integer getMyFirstBonusUnAuditCount() {
        return myFirstBonusUnAuditCount;
    }

    public void setMyFirstBonusUnAuditCount(Integer myFirstBonusUnAuditCount) {
        this.myFirstBonusUnAuditCount = myFirstBonusUnAuditCount;
    }

    public Integer getSchoolConfirmCount() {
        return schoolConfirmCount;
    }

    public void setSchoolConfirmCount(Integer schoolConfirmCount) {
        this.schoolConfirmCount = schoolConfirmCount;
    }

    public Integer getToAuditCOECount() {
        return toAuditCOECount;
    }

    public void setToAuditCOECount(Integer toAuditCOECount) {
        this.toAuditCOECount = toAuditCOECount;
    }

    public Integer getApplyResultCount() {
        return applyResultCount;
    }

    public void setApplyResultCount(Integer applyResultCount) {
        this.applyResultCount = applyResultCount;
    }

    public Integer getVisaApplyCount() {
        return visaApplyCount;
    }

    public void setVisaApplyCount(Integer visaApplyCount) {
        this.visaApplyCount = visaApplyCount;
    }

    public Integer getVisaRecordCount() {
        return visaRecordCount;
    }

    public void setVisaRecordCount(Integer visaRecordCount) {
        this.visaRecordCount = visaRecordCount;
    }

    public Integer getVisaResultCount() {
        return visaResultCount;
    }

    public void setVisaResultCount(Integer visaResultCount) {
        this.visaResultCount = visaResultCount;
    }

    public Integer getStudentDelayCount() {
        return studentDelayCount;
    }

    public void setStudentDelayCount(Integer studentDelayCount) {
        this.studentDelayCount = studentDelayCount;
    }

    public Integer getStudentSettleCount() {
        return studentSettleCount;
    }

    public void setStudentSettleCount(Integer studentSettleCount) {
        this.studentSettleCount = studentSettleCount;
    }
}
