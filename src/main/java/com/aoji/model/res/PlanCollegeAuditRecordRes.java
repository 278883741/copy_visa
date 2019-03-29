package com.aoji.model.res;

import com.aoji.model.BaseResponse;
import com.aoji.model.PlanCollegeRecord;

import java.util.List;

public class PlanCollegeAuditRecordRes extends BaseResponse {

    List<PlanCollegeRecord> data;

    public List<PlanCollegeRecord> getData() {
        return data;
    }

    public void setData(List<PlanCollegeRecord> data) {
        this.data = data;
    }
}
