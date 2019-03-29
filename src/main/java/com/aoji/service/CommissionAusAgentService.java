package com.aoji.service;

import com.aoji.model.CommissionAusAgent;
import org.springframework.ui.Model;

import java.util.List;

/**
 * @author wangjunqiang
 * @description
 * @date Created in 2018/6/29 10:35
 */
public interface CommissionAusAgentService {

    /**
     * 获取澳洲代理
     * @return
     */
    List<CommissionAusAgent> getCommissionAusAgentList(CommissionAusAgent commissionAusAgent);

    List<CommissionAusAgent> getCommissionAusAgentById(String id, Model model);

    Boolean saveCommissionAusAgent(CommissionAusAgent commissionAusAgent);

    Boolean removeCommissionAusAgent(String id);
}
