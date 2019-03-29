package com.aoji.mapper;

import com.aoji.contants.SearchOption;
import com.aoji.contants.manager.SearchOptionVisaApply;
import com.aoji.model.VisaApplyInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface VisaApplyInfoMapper extends Mapper<VisaApplyInfo> {
    /**
     * 获取管理层查看签证申请列表
     * @param searchOption
     * @return
     */
    List<Map<String,Object>> getManageList(@Param(value = "searchOption") SearchOptionVisaApply searchOption);

    List<Map<String,Object>> getUnPassVisaApplyList(@Param(value = "oaId") String oaId);

    /**
     * 获取待我审批的签证申请
     * @param oaid
     * @return
     */
    List<VisaApplyInfo> selectToAuditList(@Param("oaid") String oaid);

    /**
     * 获取我提交的 - 未审批的签证申请列表
     * @param oaId
     * @return
     */
    List<Map<String, Object>> getToAuditListWithMySubmited(@Param("oaId") String oaId);
}