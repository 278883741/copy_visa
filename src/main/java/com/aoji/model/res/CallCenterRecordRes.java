package com.aoji.model.res;

import java.util.List;

public class CallCenterRecordRes {

    private int code;

    private String msg;

    List<CallCenterRecord> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<CallCenterRecord> getData() {
        return data;
    }

    public void setData(List<CallCenterRecord> data) {
        this.data = data;
    }
}
