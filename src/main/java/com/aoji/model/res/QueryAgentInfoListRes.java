package com.aoji.model.res;

/**
 * author: 赵剑飞
 * description: 根据省份获取代理列表
 * date: 2018/3/13
 */
public class QueryAgentInfoListRes {
    private String id;
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    private String agentName;
    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }
    public String getAgentName() {
        return agentName;
    }
}
