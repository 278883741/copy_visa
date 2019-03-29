package com.aoji.mapper;

import com.aoji.contants.SearchOption;
import com.aoji.model.VisaResultInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface VisaResultInfoMapper extends Mapper<VisaResultInfo> {
    /**
     * 获取管理层查看签证结果列表
     * @param searchOption
     * @return
     */
    List<Map<String,Object>> getManageList(@Param(value = "searchOption") SearchOption searchOption);

    /**
     * 获取我提交的 - 未审批的签证结果列表
     * @param visaResultInfo
     * @return
     */
    List<Map<String,Object>> getToAuditList(@Param(value = "visaResultInfo") VisaResultInfo visaResultInfo);
}