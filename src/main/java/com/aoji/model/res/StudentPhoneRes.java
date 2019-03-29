package com.aoji.model.res;

import java.util.Map;

public class StudentPhoneRes {
    /**
     * 要查询的手机号码
     */
    Map<String,String> phone;
    /**
     * 返回编码Code
     */
    String reCode;
    /**
     * 返回信息Msg
     */
    String reMsg;

    public Map<String,String> getPhone() {
        return phone;
    }

    public void setPhone(Map<String,String> phone) {
        this.phone = phone;
    }

    public String getReCode() {
        return reCode;
    }

    public void setReCode(String reCode) {
        this.reCode = reCode;
    }

    public String getReMsg() {
        return reMsg;
    }

    public void setReMsg(String reMsg) {
        this.reMsg = reMsg;
    }
}
