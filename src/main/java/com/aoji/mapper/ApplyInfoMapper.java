package com.aoji.mapper;

import com.aoji.model.ApplyInfo;
import com.aoji.model.CostInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ApplyInfoMapper extends Mapper<ApplyInfo> {

    List<String> getApplyInfoByStudentNo(String studentNo);

    List<String> getCollegeByStudentNo(String studentNo);

    Integer updateCopyUrl(@Param("copyUrl") String copyUrl,@Param("studentNo") String studentNo,@Param("applyId")String applyId);

    ApplyInfo selectByPlanCollegeId(Integer planCollegeId);

    Integer getSumTuition(@Param("costInfo") CostInfo costInfo,@Param("applyInfo") ApplyInfo applyInfo);
    Integer getSumTuition(@Param("applyInfo") ApplyInfo applyInfo);

    List<ApplyInfo> selectByPlanCourseStatus(@Param("applyInfo") ApplyInfo applyInfo);

    ApplyInfo basicCostApplylist(@Param("applyInfo") ApplyInfo applyInfo);

    List<ApplyInfo> checkAllApplyList(@Param("applyInfo") ApplyInfo applyInfo,@Param("dateStart") String dateStart,@Param("dateEnd") String dateEnd);
}