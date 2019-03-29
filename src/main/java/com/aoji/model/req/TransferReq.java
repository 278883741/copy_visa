package com.aoji.model.req;

import io.swagger.annotations.ApiModelProperty;

public class TransferReq {

    @ApiModelProperty(value = "学号", required = true)
    private String studentNo;

    @ApiModelProperty(value = "学生姓名", required = true)
    private String studentName;

    @ApiModelProperty(value = "拼音", required = true)
    private String spelling;

    @ApiModelProperty(value = "出生日期（yyyy-MM-dd）", required = true, example = "1990-10-10")
    private String birthday;

    @ApiModelProperty(value = "合同条款编号", required = true)
    private String confeeid;

    @ApiModelProperty(value = "合同名称", required = true)
    private String contractNo;

    @ApiModelProperty(value = "合同类型", required = true)
    private Integer contractType;

    @ApiModelProperty(value = "双签类型", required = true)
    private String doubleSignType;

    @ApiModelProperty(value = "是否为美国A类", required = true)
    private Boolean usaAStatus;

    @ApiModelProperty(value = "签约日期（yyyy-MM-dd）", required = true, example = "2018-01-10")
    private String signDate;

    @ApiModelProperty(value = "留学国家内网Id", required = true)
    private Integer country;

    /**
     * 国家名称
     */
    private String countryName;

    @ApiModelProperty(value = "操作人", required = true)
    private String operatorNo;

    @ApiModelProperty(value = "操作人姓名", required = true)
    private String operatorName;

    @ApiModelProperty(value = "分支ID", required = true)
    private Integer brandId;

    @ApiModelProperty(value = "分支机构名称", required = true)
    private String brandName;

    @ApiModelProperty(value = "咨询顾问", required = true)
    private String consultant;

    @ApiModelProperty(value = "咨询顾问工号", required = true)
    private String consultantNo;

    @ApiModelProperty(value = "渠道状态（1-与代理直接签约 0-与公司签约  2-通过代理与澳际签约）", required = true)
    private Integer channelStatus;

    @ApiModelProperty(value = "备注信息")
    private String comment;

    @ApiModelProperty(value = "资源编号", required = true)
    private String resourceId;
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
    public String getResourceId() {
        return resourceId;
    }

    // 代理id
    private Integer agentId;
    // 代理名称
    private String agentName;

    @Override
    public String toString() {
        return "TransferReq{" +
                "studentNo='" + studentNo + '\'' +
                ", studentName='" + studentName + '\'' +
                ", spelling='" + spelling + '\'' +
                ", birthday='" + birthday + '\'' +
                ", confeeid='" + confeeid + '\'' +
                ", contractNo='" + contractNo + '\'' +
                ", contractType=" + contractType +
                ", doubleSignType='" + doubleSignType + '\'' +
                ", usaAStatus=" + usaAStatus +
                ", signDate='" + signDate + '\'' +
                ", country=" + country +
                ", countryName='" + countryName + '\'' +
                ", operatorNo='" + operatorNo + '\'' +
                ", operatorName='" + operatorName + '\'' +
                ", brandId=" + brandId +
                ", brandName='" + brandName + '\'' +
                ", consultant='" + consultant + '\'' +
                ", consultantNo='" + consultantNo + '\'' +
                ", channelStatus=" + channelStatus +
                ", comment='" + comment + '\'' +
                ", resourceId='" + resourceId + '\'' +
                ", agentId=" + agentId +
                ", agentName='" + agentName + '\'' +
                '}';
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

    public String getSpelling() {
        return spelling;
    }

    public void setSpelling(String spelling) {
        this.spelling = spelling;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getOperatorNo() {
        return operatorNo;
    }

    public void setOperatorNo(String operatorNo) {
        this.operatorNo = operatorNo;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Integer getContractType() {
        return contractType;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Boolean getUsaAStatus() {
        return usaAStatus;
    }

    public void setUsaAStatus(Boolean usaAStatus) {
        this.usaAStatus = usaAStatus;
    }

    public Integer getChannelStatus() {
        return channelStatus;
    }

    public void setChannelStatus(Integer channelStatus) {
        this.channelStatus = channelStatus;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getConfeeid() {
        return confeeid;
    }

    public void setConfeeid(String confeeid) {
        this.confeeid = confeeid;
    }

    public String getDoubleSignType() {
        return doubleSignType;
    }

    public void setDoubleSignType(String doubleSignType) {
        this.doubleSignType = doubleSignType;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }
}
