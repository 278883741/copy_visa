package com.aoji.model.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
@ApiModel(value="AgentReq",description="AgentReq")
public class AgentReq implements Serializable {
    @ApiModelProperty(value="agentid",name="agentid",example="998")
    private Integer agentid;
    public void setAgentid(Integer agentid) {
        this.agentid = agentid;
    }
    public Integer getAgentid() {
        return agentid;
    }

    @ApiModelProperty(value="id数组",hidden = false)
    private List<AgentItemReq> list;
    public void setList(List<AgentItemReq> list) {
        this.list = list;
    }
    public List<AgentItemReq> getList() {
        return list;
    }
}
