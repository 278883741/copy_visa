package com.aoji.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangsaixing
 * @description
 * @date Created in 上午10:48 2017/11/30
 */
public class TreeItem {

    private boolean success = true;
    private String msg = "操作成功";
    private Object obj = null;
    private Map<String, Object> attributes = new HashMap<String, Object>();

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
}
