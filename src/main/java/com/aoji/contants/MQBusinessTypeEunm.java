package com.aoji.contants;

/**
 * author: chenhaibo
 * description: MessageQueueInfo businessType枚举
 * date: 2018/10/19
 */
public enum MQBusinessTypeEunm {

    SYNC_STUDENT_INFO("SYNC_STUDENT_INFO", "同步学生信息"),
    SYNC_REGISTER_STATUS("SYNC_REGISTER_STATUS", "同步小希注册状态");

    private String code;
    private String name;

    private MQBusinessTypeEunm(String code, String name){
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
