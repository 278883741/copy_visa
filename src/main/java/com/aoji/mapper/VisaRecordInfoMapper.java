package com.aoji.mapper;

import com.aoji.model.VisaRecordInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface VisaRecordInfoMapper extends Mapper<VisaRecordInfo> {
    List<VisaRecordInfo> selectToAuditGetVisaList(@Param("oaid") String oaid);
    Integer getCountByAgentId(@Param("agentId") Integer agentId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
    List<Map<String,Object>> getUnPassVisaRecordList(@Param(value = "oaId") String oaId);
}