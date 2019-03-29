package com.aoji.model;

import com.aoji.model.res.School;
import com.aoji.model.res.SchoolData;

import java.util.Date;
import java.util.List;

public class CacheItem {
//    public List<SchoolData> list ;
//    public void setData(List<SchoolData> list) {
//        this.list = list;
//    }
//    public List<SchoolData> getData() {
//        return list;
//    }

    public Date expireDate;
    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
    public Date getExpireDate() {
        return expireDate;
    }

    private Object data;
    public void setData(Object data) {
        this.data = data;
    }
    public Object getData() {
        return data;
    }
}
