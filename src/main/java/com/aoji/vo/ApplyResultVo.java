package com.aoji.vo;

import com.aoji.model.ApplyInfo;
import com.aoji.model.ApplyResultInfo;

/**
 * @author yangsaixing
 * @description
 * @date Created in 下午4:26 2017/12/21
 */
public class ApplyResultVo {

    private ApplyInfo apply;
    private ApplyResultInfo result;

    public ApplyInfo getApply() {
        return apply;
    }

    public void setApply(ApplyInfo apply) {
        this.apply = apply;
    }

    public ApplyResultInfo getResult() {
        return result;
    }

    public void setResult(ApplyResultInfo result) {
        this.result = result;
    }
}
