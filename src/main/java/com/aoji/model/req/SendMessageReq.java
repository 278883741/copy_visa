package com.aoji.model.req;

import java.util.HashMap;
import java.util.Map;

public class SendMessageReq {

    /**
     * 接收员工工号
     */
    private String oaid;

    /**
     * 模板code
     */
    private String templateCode;

    /**
     * 模板参数
     */
    private Map<String, String> templateParam = new HashMap<String, String>();

    /**
     * 学生内网学号
     */
    private String studentNo;

    /**
     * 操作人
     */
    private String operatorNo;

    /**
     * 任务级别：1-预警消息 2-审批消息 3-工作消息
     */
    private Integer taskType;

    private String url;
    @Override
    public String toString() {
        return "SendMessageReq{" +
                "oaid='" + oaid + '\'' +
                ", templateCode='" + templateCode + '\'' +
                ", studentNo='" + studentNo + '\'' +
                ", operatorNo='" + operatorNo + '\'' +
                ", taskType=" + taskType +
                '}';
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOaid() {
        return oaid;
    }

    public void setOaid(String oaid) {
        this.oaid = oaid;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getOperatorNo() {
        return operatorNo;
    }

    public void setOperatorNo(String operatorNo) {
        this.operatorNo = operatorNo;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public Map<String, String> getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(Map<String, String> templateParam) {
        this.templateParam = templateParam;
    }
}
