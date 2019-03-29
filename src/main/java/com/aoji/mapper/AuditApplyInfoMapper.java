package com.aoji.mapper;

import com.aoji.model.AuditApplyInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface AuditApplyInfoMapper extends Mapper<AuditApplyInfo> {

    AuditApplyInfo getAuditApplyInfo(@Param("bussinessId") String bussinessId, @Param("caseId") String caseId);
    List<Map<String,Object>> queryAuditUser();
}