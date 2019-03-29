package com.aoji.model;

import com.alibaba.fastjson.annotation.JSONField;
import javax.validation.constraints.Size;

/**
* <p>
* 代理子合同管理实体
* </p>
* @author xiaomengyun
* @since 2018/08/24
*/
public class SsoAgentSubsModel {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long agentId;
    private String agentName;
    private String contractName;
    private String contractPic;
    private String startTime;
    private String endTime;
    private Integer minNum;
    private Integer maxNum;
    private Integer percentNum;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    /**
    *方法: 取得String
    *@return: String  客户ID
    */
    public Long getAgentId(){
        return this.agentId;
    }

    /**
    *方法: 设置String
    *@param: String  客户ID
    */
    public void setAgentId(Long agentId){
        this.agentId = agentId;
    }
    /**
    *方法: 取得Integer
    *@return: Integer  结构名称
    */
    public String getContractName(){
        return this.contractName;
    }

    /**
    *方法: 设置Integer
    *@param: Integer  结构名称
    */
    public void setContractName(String contractName){
        this.contractName = contractName;
    }
    /**
    *方法: 取得String
    *@return: String  附件路径
    */
    public String getContractPic(){
        return this.contractPic;
    }

    /**
    *方法: 设置String
    *@param: String  附件路径
    */
    public void setContractPic(String contractPic){
        this.contractPic = contractPic;
    }
    /**
    *方法: 取得java.util.Date
    *@return: java.util.Date  开始日期
    */
    public String getStartTime(){
        return this.startTime;
    }

    /**
    *方法: 设置java.util.Date
    *@param: java.util.Date  开始日期
    */
    public void setStartTime(String startTime){
        this.startTime = startTime;
    }
    /**
    *方法: 取得java.util.Date
    *@return: java.util.Date  结束日期
    */
    public String getEndTime(){
        return this.endTime;
    }

    /**
    *方法: 设置java.util.Date
    *@param: java.util.Date  结束日期
    */
    public void setEndTime(String endTime){
        this.endTime = endTime;
    }
    /**
    *方法: 取得Integer
    *@return: Integer  最小值
    */
    public Integer getMinNum(){
        return this.minNum;
    }

    /**
    *方法: 设置Integer
    *@param: Integer  最小值
    */
    public void setMinNum(Integer minNum){
        this.minNum = minNum;
    }
    /**
    *方法: 取得Integer
    *@return: Integer  最大值
    */
    public Integer getMaxNum(){
        return this.maxNum;
    }

    /**
    *方法: 设置Integer
    *@param: Integer  最大值
    */
    public void setMaxNum(Integer maxNum){
        this.maxNum = maxNum;
    }
    /**
    *方法: 取得Integer
    *@return: Integer  百分比
    */
    public Integer getPercentNum(){
        return this.percentNum;
    }

    /**
    *方法: 设置Integer
    *@param: Integer  百分比
    */
    public void setPercentNum(Integer percentNum){
        this.percentNum = percentNum;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }
}
