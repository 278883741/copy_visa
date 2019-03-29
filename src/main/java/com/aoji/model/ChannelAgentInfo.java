package com.aoji.model;

public class ChannelAgentInfo {

    // 合同链接
    private String contractPic;

    public String getContractPic() {
        return contractPic;
    }

    public void setContractPic(String contractPic) {
        this.contractPic = contractPic;
    }

    // 代理ID
    private Integer agentId;
    // 代理名称
    private String agentName;
    // 合同名称
    private String contractName;
    // 获签数量
    private Integer getVisaSum;
    // 返佣百分比
    private Integer percentNum;

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

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public Integer getGetVisaSum() {
        return getVisaSum;
    }

    public void setGetVisaSum(Integer getVisaSum) {
        this.getVisaSum = getVisaSum;
    }

    public Integer getPercentNum() {
        return percentNum;
    }

    public void setPercentNum(Integer percentNum) {
        this.percentNum = percentNum;
    }
}
