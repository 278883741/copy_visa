package com.aoji.mapper;

import com.aoji.model.AuditDingdingInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface AuditDingdingInfoMapper extends Mapper<AuditDingdingInfo> {
    /**
     * 根据场景id及业务关联Id修改成功时间及发送状态
     * @param auditDingdingInfo
     * @return
     */
    int updateAuditDingSendStatusByBussinessIdAndCaseId(@Param("auditDingdingInfo") AuditDingdingInfo auditDingdingInfo);

    /**
     * 根据场景id及业务关联Id修改审批时间，审批状态及审批人工号
     * @param auditDingdingInfo
     * @return
     */
    int updateAudiDingAuditStatusByBussinessIdAndCaseId(@Param("auditDingdingInfo")AuditDingdingInfo auditDingdingInfo);
}