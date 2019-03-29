package com.aoji.service;

import com.aoji.model.CommissionBankAccount;

import java.util.List;

public interface BankAccountService {

    List<CommissionBankAccount> getList(CommissionBankAccount commissionBankAccount);
}
