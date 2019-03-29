package com.aoji.model.req;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class UpdateStudentInfoReq implements Serializable{

    private static final long serialVersionUID = 8290739681402760046L;
    @ApiModelProperty(value = "学号", required = true)
    private String studentNo;
    @ApiModelProperty(value = "姓名")
    private String studentName;
    @ApiModelProperty(value = "出生日期(yyyy-MM-dd)")
    private String birthday;
    @ApiModelProperty(value = "拼音")
    private String spelling;

    private String brandId;

    private String consultantNo;

    @Override
    public String toString() {
        return "UpdateStudentInfoReq{" +
                "studentNo='" + studentNo + '\'' +
                ", studentName='" + studentName + '\'' +
                ", birthday='" + birthday + '\'' +
                ", spelling='" + spelling + '\'' +
                ", brandId='" + brandId + '\'' +
                ", consultantNo='" + consultantNo + '\'' +
                '}';
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSpelling() {
        return spelling;
    }

    public void setSpelling(String spelling) {
        this.spelling = spelling;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getConsultantNo() {
        return consultantNo;
    }

    public void setConsultantNo(String consultantNo) {
        this.consultantNo = consultantNo;
    }
}
