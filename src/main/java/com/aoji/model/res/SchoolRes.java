package com.aoji.model.res;

import java.util.List;

/**
 * author: 赵剑飞
 * description: 查询院校接口返回
 * date: 2018/3/13
 */
public class SchoolRes {
    private Integer code;
    public void setCode(Integer code) {
        this.code = code;
    }
    public Integer getCode() {
        return code;
    }

    private String message;
    public void setMsg(String message) {
        this.message = message;
    }
    public String getMsg() {
        return message;
    }

    private String data;
    public void setData(String data) {
        this.data = data;
    }
    public String getData() {
        return data;
    }
}
