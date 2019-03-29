package com.aoji.vo;

import com.aoji.model.ApplyInfo;
import com.aoji.model.ExpressCompany;
import com.aoji.model.SupplementInfo;

import java.util.List;

/**
 * @author yangsaixing
 * @description
 * @date Created in 上午11:35 2017/12/21
 */
public class ApplyCollegeVo {

    private ApplyInfo apply;

    private SupplementInfo supplementInfo;

    private List<ExpressCompany> expressCompanyList;

    public ApplyInfo getApply() {
        return apply;
    }

    public void setApply(ApplyInfo apply) {
        this.apply = apply;
    }

    public SupplementInfo getSupplementInfo() {
        return supplementInfo;
    }

    public void setSupplementInfo(SupplementInfo supplementInfo) {
        this.supplementInfo = supplementInfo;
    }

    public List<ExpressCompany> getExpressCompanyList() {
        return expressCompanyList;
    }

    public void setExpressCompanyList(List<ExpressCompany> expressCompanyList) {
        this.expressCompanyList = expressCompanyList;
    }
}
