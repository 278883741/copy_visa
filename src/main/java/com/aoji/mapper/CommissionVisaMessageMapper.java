package com.aoji.mapper;

import com.aoji.model.CommissionVisaMessage;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CommissionVisaMessageMapper extends Mapper<CommissionVisaMessage> {



    void updateInflowTypeByVisaRecordResultId(
            @Param("visaCommissionId") Integer visaCommissionId,
            @Param("inflowType") Integer inflowType,
            @Param("sendCommissionStatus") Integer sendCommissionStatus,
            @Param("beforeData") String beforeData,
            @Param("afterData") String afterData
    );
}