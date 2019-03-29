package com.aoji.mapper;

import com.aoji.model.WorkflowNodeInfo;
import com.aoji.model.WorkflowNodeInfo1;
import com.aoji.vo.WorkflowNodeInfoVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface WorkflowNodeInfoMapper1 extends Mapper<WorkflowNodeInfo1> {

    List<Map<String,Object>> getWorkflowNodeInfoList(WorkflowNodeInfoVo workflowNodeInfo);

}