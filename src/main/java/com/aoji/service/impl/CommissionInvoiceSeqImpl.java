package com.aoji.service.impl;

import com.aoji.mapper.CommissionInvoiceSeqMapper;
import com.aoji.model.CommissionInvoiceSeq;
import com.aoji.service.CommissionInvoiceSeqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommissionInvoiceSeqImpl implements CommissionInvoiceSeqService{

    @Autowired
    CommissionInvoiceSeqMapper commissionInvoiceSeqMapper;

    @Override
    public int insert(CommissionInvoiceSeq commissionInvoiceSeq) {
        return commissionInvoiceSeqMapper.insert(commissionInvoiceSeq);
    }
}
