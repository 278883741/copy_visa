package com.aoji.service;

import com.aoji.model.FullofferApplyInfo;

public interface FullOfferApplyServices {
    /**
     * 根据申请id查询申请信息
     * @param id 申请id
     * @return
     */
    FullofferApplyInfo getById(Integer id);
}
