package com.aoji.model.res;

public class SignBaseResponse {

    /**
     * 状态： 0返回成功 1返回失败
     */
    int state;

    /**
     * 消息
     */
    String msg;

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
}
