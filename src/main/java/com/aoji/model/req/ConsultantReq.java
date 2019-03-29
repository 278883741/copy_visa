package com.aoji.model.req;

import io.swagger.annotations.ApiModelProperty;

public class ConsultantReq {

    @ApiModelProperty(value = "学号", required = true)
    private String studentNo;

    @ApiModelProperty(value = "咨询顾问姓名", required = true)
    private String consultant;

    @ApiModelProperty(value = "咨询顾问工号", required = true)
    private String consultantNo;

    @Override
    public String toString() {
        return "ConsultantReq{" +
                "studentNo='" + studentNo + '\'' +
                ", consultant='" + consultant + '\'' +
                ", consultantNo='" + consultantNo + '\'' +
                '}';
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getConsultant() {
        return consultant;
    }

    public void setConsultant(String consultant) {
        this.consultant = consultant;
    }

    public String getConsultantNo() {
        return consultantNo;
    }

    public void setConsultantNo(String consultantNo) {
        this.consultantNo = consultantNo;
    }
}
