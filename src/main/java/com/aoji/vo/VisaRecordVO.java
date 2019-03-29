package com.aoji.vo;

import com.aoji.model.VisaRecordInfo;
import com.aoji.model.VisaResultRecordInfo;

import java.util.List;

public class VisaRecordVO {
    private VisaRecordInfo visaRecordInfo;
    public void setVisaRecordInfo(VisaRecordInfo visaRecordInfo) {
        this.visaRecordInfo = visaRecordInfo;
    }
    public VisaRecordInfo getVisaRecordInfo() {
        return visaRecordInfo;
    }

    private List<VisaResultRecordInfo> list;
    public void setList(List<VisaResultRecordInfo> list) {
        this.list = list;
    }
    public List<VisaResultRecordInfo> getList() {
        return list;
    }
}
