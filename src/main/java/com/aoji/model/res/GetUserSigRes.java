package com.aoji.model.res;

import java.util.Map;

public class GetUserSigRes extends BaseRes{

    private Map<String, String> data;

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
