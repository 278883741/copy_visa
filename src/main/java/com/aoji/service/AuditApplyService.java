package com.aoji.service;

import com.aoji.model.AuditApplyInfo;
import com.aoji.model.SysUser;
import com.aoji.model.WorkflowNodeInfo;

import java.util.List;
import java.util.Map;

/**
 * @author 赵剑飞
 * @description 待审批的服务
 * @date Created in 下午2:29 2017/12/7
 */
public interface AuditApplyService {

    /**
     * 获取当前类型审批人
     *
     * @param businessId
     * @param caseId
     * @param sequence
     * @param studentNo
     * @return
     */
    WorkflowNodeInfo getWorkflwInfo(Integer businessId, Integer caseId, Integer sequence, String studentNo);

    /**
     * 添加实体
     *
     * @param businessId 业务id
     * @param caseId     场景id
     * @param sequence   审批顺序
     * @param studentNo  学号
     * @return
     */
    Boolean add(Integer businessId, Integer caseId, Integer sequence, String studentNo, String operatorNo,String operatorName);

    /**
     * 添加实体
     *
     * @param auditApplyInfo
     * @return
     */
    Integer insert(AuditApplyInfo auditApplyInfo);

    /**
     * 根据业务id及场景id删除实体
     *
     * @param businessId
     * @param caseId
     * @return
     */
    Integer delete(Integer businessId, Integer caseId);

    /**
     * 更新实体
     *
     * @param auditApplyInfo
     * @return
     */
    Integer update(AuditApplyInfo auditApplyInfo);

    /**
     * 根据业务id及场景id查询实体
     *
     * @param businessId
     * @param caseId
     * @return
     */
    AuditApplyInfo get(Integer businessId, Integer caseId);

    /**
     * 查询实体
     *
     * @param auditApplyInfo
     * @return
     */
    AuditApplyInfo get(AuditApplyInfo auditApplyInfo);

    /**
     * 查询实体
     *
     * @param auditApplyInfo
     * @return
     */
    List<AuditApplyInfo> getList(AuditApplyInfo auditApplyInfo);

    /**
     * 查询当前用户是否有相应审批权限
     *
     * @param roleName 角色名称
     * @return
     */
    boolean currUserWithPermission(String roleName, SysUser sysUser);

    /**
     * 获取各模块审批人清单
     * @return
     */
    List<Map<String,Object>> queryAuditUser();
}
