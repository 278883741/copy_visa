package com.aoji.model;

import java.util.List;

public class RevisitRes {
    private Integer state;
    public void setState(Integer state) {
        this.state = state;
    }
    public Integer getState() {
        return state;
    }

    private String msg;
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    private List<Revisit> data;
    public void setData(List<Revisit> data) {
        this.data = data;
    }
    public List<Revisit> getData() {
        return data;
    }
}
