package com.aoji.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "failure_job")
public class FailureJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 模块
     */
    private String module;

    /**
     * http请求方式
     */
    private String method;

    /**
     * 请求参数
     */
    @Column(name = "query_param")
    private String queryParam;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "delete_status")
    private Boolean deleteStatus;

    private String url;

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
     * 获取模块
     *
     * @return module - 模块
     */
    public String getModule() {
        return module;
    }

    /**
     * 设置模块
     *
     * @param module 模块
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * 获取http请求方式
     *
     * @return method - http请求方式
     */
    public String getMethod() {
        return method;
    }

    /**
     * 设置http请求方式
     *
     * @param method http请求方式
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * 获取请求参数
     *
     * @return query_param - 请求参数
     */
    public String getQueryParam() {
        return queryParam;
    }

    /**
     * 设置请求参数
     *
     * @param queryParam 请求参数
     */
    public void setQueryParam(String queryParam) {
        this.queryParam = queryParam;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return delete_status
     */
    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * @param deleteStatus
     */
    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }
}