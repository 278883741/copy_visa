package com.aoji.model.req;
/**
 * author: chenhaibo
 * description: 修改院校申请的请求类
 * date: 2018/2/6
 */
public class PlanCollegeReq {

    private Integer id; // 院校申请id
    private Integer planId; //定校方案id
    private Integer status; // 状态  1-接受/2-驳回
    private String reason; // 拒绝原因
    private String remark; // 备注
    private String oaid; // 外联顾问工号
    private String connector; // 外联姓名
    private String studentNo; //学号

    @Override
    public String toString() {
        return "PlanCollegeReq{" +
                "id=" + id +
                ", planId=" + planId +
                ", status=" + status +
                ", reason='" + reason + '\'' +
                ", oaid='" + oaid + '\'' +
                ", connector='" + connector + '\'' +
                ", studentNo='" + studentNo + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOaid() {
        return oaid;
    }

    public void setOaid(String oaid) {
        this.oaid = oaid;
    }

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
