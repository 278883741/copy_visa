package com.aoji.vo;

import com.aoji.model.UserTaskRelation;

public class MessageVO extends UserTaskRelation{

    /**
     * 跳转地址
     */
    private String url;

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }
}
