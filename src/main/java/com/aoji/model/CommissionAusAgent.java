package com.aoji.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "commission_aus_agent")
public class CommissionAusAgent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 代理名称
     */
    @Column(name = "agent_name")
    private String agentName;

    /**
     * 是否删除 0-未删除 1-删除
     */
    @Column(name = "delete_status")
    private Boolean deleteStatus;

    /**
     * 操作人工号
     */
    @Column(name = "operator_no")
    private String operatorNo;

    /**
     * 操作人姓名
     */
    @Column(name = "operator_name")
    private String operatorName;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取代理名称
     *
     * @return agent_name - 代理名称
     */
    public String getAgentName() {
        return agentName;
    }

    /**
     * 设置代理名称
     *
     * @param agentName 代理名称
     */
    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    /**
     * 获取是否删除 0-未删除 1-删除
     *
     * @return delete_status - 是否删除 0-未删除 1-删除
     */
    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * 设置是否删除 0-未删除 1-删除
     *
     * @param deleteStatus 是否删除 0-未删除 1-删除
     */
    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
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

    /**
     * 获取修改时间
     *
     * @return create_time - 修改时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置修改时间
     *
     * @param createTime 修改时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}