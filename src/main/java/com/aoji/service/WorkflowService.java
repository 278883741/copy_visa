package com.aoji.service;

import com.aoji.model.WorkflowInfo;
import com.aoji.model.WorkflowNodeInfo;
import com.aoji.model.WorkflowNodeInfo1;
import com.aoji.vo.WorkFlowVo;
import com.aoji.vo.WorkflowNodeInfoVo;

import java.util.List;
import java.util.Map;

/**
 * @author yangsaixing
 * @description 工作流服务
 * @date Created in 上午10:33 2018/1/8
 */
public interface WorkflowService {
    /**
     * 根据工作流code和节点顺序查询节点信息
     *
     * @param workflowInfo
     * @param sequence
     * @return
     */
    WorkflowNodeInfo queryRoleByCode(WorkflowInfo workflowInfo, Integer sequence, Integer nationGroup, String studentNo);

    /**
     * 根据工作流code查询工作流信息
     *
     * @param flowCode 工作流code
     * @return
     */
    WorkflowInfo queryWorkflowByCode(String flowCode);

    // =================================================================================================================

    /**
     * datateablsee 列表
     *
     * @param workFlowVo
     * @return
     */
    List<WorkFlowVo> getWorkFlowList(WorkFlowVo workFlowVo);

    /**
     * 添加审批流程前的校验
     *
     * @return
     */
    Map<String, Object> addCheck(WorkflowInfo workflowInfo);

    /**
     * 添加审批流程
     *
     * @param workflowInfo
     * @return
     */
    int add(WorkflowInfo workflowInfo);

    /**
     * 根据流程id获取当前流程
     *
     * @param id
     * @return
     */
    WorkflowInfo selectWorkFlowById(Integer id);

    /**
     * 获取已经为当前分组当前场景下的审批人
     *
     * @param id
     * @return
     */
    List<WorkflowNodeInfo1> getWorkFlowPeople(Integer id);

    /**
     * 获取操作人列表
     *
     * @param workflowNodeInfo
     * @return
     */
    List<Map<String, Object>> getWorkflowNodeInfoList(WorkflowNodeInfoVo workflowNodeInfo);

    /**
     * 删除审批人
     *
     * @param id
     * @return
     */
    int deleteWorkflowNodeInfo(Integer id);

    /**
     * 添加具体审批人
     *
     * @param workflowNodeInfo1
     * @return
     */
    int addWorkFlowNodeInfo(WorkflowNodeInfo1 workflowNodeInfo1);

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteWorkflowInfo(Integer id);
}
