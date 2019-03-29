package com.aoji.model;

/**
 * author: chenhaibo
 * description: 合同费用
 * date: 2018/3/14
 */
public class ContractFee {

    private String contractFee;

    private boolean selectStatus;

    public String getContractFee() {
        return contractFee;
    }

    public void setContractFee(String contractFee) {
        this.contractFee = contractFee;
    }

    public boolean isSelectStatus() {
        return selectStatus;
    }

    public void setSelectStatus(boolean selectStatus) {
        this.selectStatus = selectStatus;
    }
}
