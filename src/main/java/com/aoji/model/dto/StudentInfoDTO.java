package com.aoji.model.dto;

import com.aoji.model.StudentInfo;

public class StudentInfoDTO extends StudentInfo{

    /**
     * 服务代码
     */
    private String serviceCode;
    /**
     * 服务名称
     */
    private String serviceName;

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
