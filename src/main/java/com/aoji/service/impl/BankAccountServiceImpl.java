package com.aoji.service.impl;

import com.aoji.mapper.CommissionBankAccountMapper;
import com.aoji.model.CommissionBankAccount;
import com.aoji.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BankAccountServiceImpl implements BankAccountService{

    @Autowired
    CommissionBankAccountMapper commissionBankAccountMapper;

    @Override
    public List<CommissionBankAccount> getList(CommissionBankAccount commissionBankAccount) {
        commissionBankAccount.setDeleteStatus(false);
        return commissionBankAccountMapper.select(commissionBankAccount);
    }
}
