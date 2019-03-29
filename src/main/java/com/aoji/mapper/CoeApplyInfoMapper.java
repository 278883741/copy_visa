package com.aoji.mapper;

import com.aoji.model.CoeApplyInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CoeApplyInfoMapper extends Mapper<CoeApplyInfo> {
    List<CoeApplyInfo> getToAuditCOEList(@Param("oaid") String oaid,@Param("studentNo") String studentNo,@Param("oaName") String oaName);




}