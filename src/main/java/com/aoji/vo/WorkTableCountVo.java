package com.aoji.vo;

public class WorkTableCountVo {

    /**
     * 工作消息总数
     */
    private Integer workPageCount;
    /**
     * 工作消息未读总数
     */
    private Integer workNotRead;
    /**\
     * 预警消息总数
     */
    private Integer warningPageCount;
    /**
     * 预警消息未读总数
     */
    private Integer warningNotRead;
    /**
     * 审批消息总数
     */
    private Integer approvalPageCount;
    /**
     * 审批消息未读总数
     */
    private Integer approvalNotRead;
    /**
     * 系统公告消息总数
     */
    private Integer affichePageCount;

    public Integer getAffichePageCount() {
        return affichePageCount;
    }

    public void setAffichePageCount(Integer affichePageCount) {
        this.affichePageCount = affichePageCount;
    }

    public Integer getWorkPageCount() {
        return workPageCount;
    }

    public void setWorkPageCount(Integer workPageCount) {
        this.workPageCount = workPageCount;
    }

    public Integer getWorkNotRead() {
        return workNotRead;
    }

    public void setWorkNotRead(Integer workNotRead) {
        this.workNotRead = workNotRead;
    }

    public Integer getWarningPageCount() {
        return warningPageCount;
    }

    public void setWarningPageCount(Integer warningPageCount) {
        this.warningPageCount = warningPageCount;
    }

    public Integer getWarningNotRead() {
        return warningNotRead;
    }

    public void setWarningNotRead(Integer warningNotRead) {
        this.warningNotRead = warningNotRead;
    }

    public Integer getApprovalPageCount() {
        return approvalPageCount;
    }

    public void setApprovalPageCount(Integer approvalPageCount) {
        this.approvalPageCount = approvalPageCount;
    }

    public Integer getApprovalNotRead() {
        return approvalNotRead;
    }

    public void setApprovalNotRead(Integer approvalNotRead) {
        this.approvalNotRead = approvalNotRead;
    }
}
