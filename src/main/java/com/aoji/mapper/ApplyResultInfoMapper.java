package com.aoji.mapper;

import com.aoji.model.ApplyResultInfo;
import com.aoji.model.StudentInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ApplyResultInfoMapper extends Mapper<ApplyResultInfo> {

    /**
     * 根据planId查询申请结果状态
     * @param planId
     * @return
     */
    List<ApplyResultInfo> getStatusByPlanId(Integer planId);

    ApplyResultInfo getAppResultById(@Param("id") Integer id);

    StudentInfo getStudentInfoByApplyId(String applyId);

    List<ApplyResultInfo> getApplyResultInfo(String operatorNo);
}