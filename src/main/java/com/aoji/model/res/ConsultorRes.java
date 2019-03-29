package com.aoji.model.res;

/**
 * author: chenhaibo
 * description: 销售系统查询文签顾问返回
 * date: 2018/3/13
 */
public class ConsultorRes {

    /**
     * 状态
     */
    int state;
    /**
     * 消息
     */
    String msg;

    /**
     * 销售顾问信息
     */
    Consultor data;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Consultor getData() {
        return data;
    }

    public void setData(Consultor data) {
        this.data = data;
    }
}
