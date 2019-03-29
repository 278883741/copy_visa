package com.aoji.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_access_log")
public class SysAccessLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "member_id")
    private String memberId;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;

    @Column(name = "access_type")
    private String accessType;

    @Column(name = "ip_address")
    private String ipAddress;

    /**
     * 请求url
     */
    @Column(name = "request_url")
    private String requestUrl;

    /**
     * 请求参数
     */
    @Column(name = "request_param")
    private String requestParam;

    /**
     * 响应
     */
    private String response;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
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
     * @return member_id
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * @param memberId
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取用户名
     *
     * @return user_name - 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名
     *
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return access_type
     */
    public String getAccessType() {
        return accessType;
    }

    /**
     * @param accessType
     */
    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    /**
     * @return ip_address
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * 获取请求url
     *
     * @return request_url - 请求url
     */
    public String getRequestUrl() {
        return requestUrl;
    }

    /**
     * 设置请求url
     *
     * @param requestUrl 请求url
     */
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    /**
     * 获取请求参数
     *
     * @return request_param - 请求参数
     */
    public String getRequestParam() {
        return requestParam;
    }

    /**
     * 设置请求参数
     *
     * @param requestParam 请求参数
     */
    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    /**
     * 获取响应
     *
     * @return response - 响应
     */
    public String getResponse() {
        return response;
    }

    /**
     * 设置响应
     *
     * @param response 响应
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}