package com.aoji.model.req;

public class CoeAuditReq {
    //coe申请id
    private Integer coeApplyId;
    //审批类型
    private Integer type;
    //拒绝原因
    private String remark;
    //院校申请id
    private Integer applyId;
    //更新时间
    private String updateTime;

    @Override
    public String toString() {
        return "CoeAuditReq{" +
                "coeApplyId=" + coeApplyId +
                ", type=" + type +
                ", remark='" + remark + '\'' +
                ", applyId=" + applyId +
                ", updateTime=" + updateTime +
                '}';
    }

    public Integer getCoeApplyId() {
        return coeApplyId;
    }

    public void setCoeApplyId(Integer coeApplyId) {
        this.coeApplyId = coeApplyId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
