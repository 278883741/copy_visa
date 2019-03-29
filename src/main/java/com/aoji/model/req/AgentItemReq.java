package com.aoji.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Transient;
import java.io.Serializable;


@ApiModel(value="AgentItemReq",description="AgentItemReq")
public class AgentItemReq implements Serializable{
    @ApiModelProperty(value="contractId",required = false,example="澳大利亚：探亲合同3500；签证费")
    private String contractId;
    public String getContractId() {
        return contractId;
    }
    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    @ApiModelProperty(value="starttime（yyyy-MM-dd）",required = true,example="2012-10-10")
    private String starttime;
    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }
    public String getStarttime() {
        return starttime;
    }

    @ApiModelProperty(value="endtime（yyyy-MM-dd）", required = true ,example="2012-10-11")
    private String endtime;
    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
    public String getEndtime() {
        return endtime;
    }

    @ApiModelProperty(value="count",name="count",example="0",hidden = true)
    private Integer count;
    public void setCount(Integer count) {
        this.count = count;
    }
    public Integer getCount() {
        return count;
    }

    @Transient
    private  Integer percentNum;

    public Integer getPercentNum() {
        return percentNum;
    }

    public void setPercentNum(Integer percentNum) {
        this.percentNum = percentNum;
    }
}
