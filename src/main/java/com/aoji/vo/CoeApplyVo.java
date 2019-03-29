package com.aoji.vo;

import com.aoji.model.ApplyInfo;
import com.aoji.model.ApplyResultInfo;
import com.aoji.model.CoeApplyInfo;
import com.aoji.model.SupplementInfo;

public class CoeApplyVo {
    private ApplyInfo applyInfo;

    private ApplyResultInfo applyResultInfo;

    private CoeApplyInfo coeApplyInfo;
    //补件信息，类型为3
    private SupplementInfo supplementInfo3;
    //补件信息，类型为4
    private SupplementInfo supplementInfo4;

    private Integer nationId;

    public ApplyInfo getApplyInfo() {
        return applyInfo;
    }

    public void setApplyInfo(ApplyInfo applyInfo) {
        this.applyInfo = applyInfo;
    }

    public ApplyResultInfo getApplyResultInfo() {
        return applyResultInfo;
    }

    public void setApplyResultInfo(ApplyResultInfo applyResultInfo) {
        this.applyResultInfo = applyResultInfo;
    }

    public CoeApplyInfo getCoeApplyInfo() {
        return coeApplyInfo;
    }

    public void setCoeApplyInfo(CoeApplyInfo coeApplyInfo) {
        this.coeApplyInfo = coeApplyInfo;
    }

    public SupplementInfo getSupplementInfo3() {
        return supplementInfo3;
    }

    public void setSupplementInfo3(SupplementInfo supplementInfo3) {
        this.supplementInfo3 = supplementInfo3;
    }

    public SupplementInfo getSupplementInfo4() {
        return supplementInfo4;
    }

    public void setSupplementInfo4(SupplementInfo supplementInfo4) {
        this.supplementInfo4 = supplementInfo4;
    }

    public Integer getNationId() {
        return nationId;
    }

    public void setNationId(Integer nationId) {
        this.nationId = nationId;
    }
}
