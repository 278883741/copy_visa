package com.aoji.mapper;

import com.aoji.model.WorkflowInfo;
import com.aoji.vo.WorkFlowVo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface WorkflowInfoMapper extends Mapper<WorkflowInfo> {
    List<WorkFlowVo> getWorkFlowList(WorkFlowVo workFlowVo);
}
