package com.aoji.service;
import com.aoji.model.AuditResultInfo;
import java.util.List;
/**
 * @author 赵剑飞
 * @description 审批结果的服务
 * @date Created in 下午2:29 2017/12/7
 */
public interface AuditResultService {
    /**
     * 添加实体
     * @param auditResultInfo
     * @return
     */
    boolean add(AuditResultInfo auditResultInfo);

    /**
     * 更新实体
     * @param auditResultInfo
     * @return
     */
    Integer update(AuditResultInfo auditResultInfo);
//    <T> List<T> getList(Integer caseId,Integer businessId,Class<T> tClass);

    /**
     * 根据场景id及业务id获取审批结果列表
     * @param caseId
     * @param businessId
     * @return
     */
    List<AuditResultInfo> getList(Integer caseId,Integer businessId);
}
