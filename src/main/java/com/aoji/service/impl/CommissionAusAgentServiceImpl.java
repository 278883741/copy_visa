package com.aoji.service.impl;

import com.aoji.mapper.CommissionAusAgentMapper;
import com.aoji.model.CommissionAusAgent;
import com.aoji.model.SysUser;
import com.aoji.service.CommissionAusAgentService;
import com.aoji.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wangjunqiang
 * @description
 * @date Created in 2018/6/29 10:37
 */
@Service
public class CommissionAusAgentServiceImpl implements CommissionAusAgentService {

    @Autowired
    private CommissionAusAgentMapper commissionAusAgentMapper;

    @Autowired
    private UserService userService;


    @Override
    public List<CommissionAusAgent> getCommissionAusAgentList(CommissionAusAgent commissionAusAgent) {
        Example example = new Example(CommissionAusAgent.class);
        if(StringUtils.hasText(commissionAusAgent.getAgentName())){
            example.createCriteria().andEqualTo("deleteStatus",false).andEqualTo("agentName",commissionAusAgent.getAgentName());
        }else{
            example.createCriteria().andEqualTo("deleteStatus",false);
        }
        List<CommissionAusAgent> commissionAusAgentList = commissionAusAgentMapper.selectByExample(example);
        return commissionAusAgentList;
    }

    @Override
    public List<CommissionAusAgent> getCommissionAusAgentById(String id,Model model) {
        CommissionAusAgent commissionAusAgent = new CommissionAusAgent();
        if(StringUtils.hasText(id)){
           commissionAusAgent.setDeleteStatus(false);
           commissionAusAgent.setId(Integer.valueOf(id));
           List<CommissionAusAgent> commissionAusAgentList = commissionAusAgentMapper.select(commissionAusAgent);
           if(commissionAusAgentList != null && commissionAusAgentList.size() > 0){
               commissionAusAgent = commissionAusAgentList.get(0);
           }
        }
        model.addAttribute("ausAgent",commissionAusAgent);
        return null;
    }

    @Override
    public Boolean saveCommissionAusAgent(CommissionAusAgent commissionAusAgent) {
        SysUser sysUser = userService.getLoginUser();
        if(commissionAusAgent.getId() == null){
            commissionAusAgent.setDeleteStatus(false);
            commissionAusAgent.setCreateTime(new Date());
            commissionAusAgent.setOperatorNo(sysUser.getOaid());
            commissionAusAgent.setOperatorName(sysUser.getUsername());
            return commissionAusAgentMapper.insert(commissionAusAgent) == 1;
        }else{
            return commissionAusAgentMapper.updateByPrimaryKeySelective(commissionAusAgent) == 1;
        }
    }

    @Override
    public Boolean removeCommissionAusAgent(String id) {
        CommissionAusAgent commissionAusAgent = new CommissionAusAgent();
        commissionAusAgent.setId(Integer.valueOf(id));
        commissionAusAgent.setDeleteStatus(true);
        return commissionAusAgentMapper.updateByPrimaryKey(commissionAusAgent) == 1;
    }
}
