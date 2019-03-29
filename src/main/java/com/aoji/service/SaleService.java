package com.aoji.service;

import com.aoji.model.ApplyInfo;
import com.aoji.model.SysUser;


public interface SaleService {

    /**
     * 根据id查询信息
     * @param id
     * @return
     */
    ApplyInfo getById(Integer id);

    /**
     * 更新OFFOR信息
     * @param applyInfo
     * @return
     */
    Integer update(ApplyInfo applyInfo);

    Boolean sendMessageByOperatorNo(SysUser sysUser,ApplyInfo applyInfo);
}
