package com.aoji.model.res;

import com.aoji.model.BaseResponse;

import java.util.List;

public class PlanCollegeRes extends BaseResponse{

    Integer count;

    List<PlanCollege> planColleges;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<PlanCollege> getPlanColleges() {
        return planColleges;
    }

    public void setPlanColleges(List<PlanCollege> planColleges) {
        this.planColleges = planColleges;
    }
}
