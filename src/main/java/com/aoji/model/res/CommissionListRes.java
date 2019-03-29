package com.aoji.model.res;

import com.aoji.model.BaseResponse;

import java.util.List;

/**
 * author: chenhaibo
 * description: OA内网代理返佣列表接口返回值
 * date: 2018/9/10
 */
public class CommissionListRes extends BaseResponse{

    List<CommissionInfo> commissionInfoList;

    public List<CommissionInfo> getCommissionInfoList() {
        return commissionInfoList;
    }

    public void setCommissionInfoList(List<CommissionInfo> commissionInfoList) {
        this.commissionInfoList = commissionInfoList;
    }
}
