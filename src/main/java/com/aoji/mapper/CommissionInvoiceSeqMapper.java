package com.aoji.mapper;

import com.aoji.model.CommissionInvoiceSeq;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface CommissionInvoiceSeqMapper extends Mapper<CommissionInvoiceSeq> {

    int updateInvoiceByTempKey(@Param("tempKey") String tempKey,
                           @Param("invoiceNo") String invoiceNo);
}