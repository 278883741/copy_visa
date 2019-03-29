package com.aoji.mapper;

import com.aoji.model.PlanCollegeInfo;
import com.aoji.model.req.PlanCollegeCondition;
import com.aoji.model.req.PlanCollegeQueryReq;
import com.aoji.model.res.PlanCollege;
import com.aoji.vo.CalibrationSchemeVo;
import com.aoji.vo.PlanCollegeInfoVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface PlanCollegeInfoMapper extends Mapper<PlanCollegeInfo> {

    int getPlanCollegeInfoCount(PlanCollegeInfoVO planCollegeInfoVO);

    int getPlanCollegeCountByWorktable(PlanCollegeInfoVO planCollegeInfoVO);

    List<PlanCollegeInfoVO> getPlanCollegeInfo(PlanCollegeInfoVO planCollegeInfoVO);

    List<Map<String,Object>> calibrationSchemeList(
            @Param("calibrationSchemeVo") CalibrationSchemeVo calibrationSchemeVo,
            @Param("arrayId") Integer[] arrayId);

    List<PlanCollege> queryPlanCollege(PlanCollegeCondition planCollegeCondition);

    /**
     * 文签查看定校
     * @param planCollegeCondition
     * @return
     */
    List<PlanCollegeInfoVO> queryPlanCollegeForVisa(PlanCollegeCondition planCollegeCondition);

    boolean toVoidPlanCollageByPlanCollageId(@Param("planCollageId") Integer planCollageId,
                                             @Param("auditStatus")  Integer auditStatus);

    /**
     * 管理层查看定校方案 -- 签约系统
     * @param planCollegeQueryReq
     * @return
     */
    List<PlanCollege> queryPlanCollegeForManager(@Param("planCollegeQueryReq") PlanCollegeQueryReq planCollegeQueryReq,
                                       @Param("begin") Integer begin,
                                       @Param("end") Integer end);

    /**
     * 管理层查看定校方案总数 -- 签约系统
     * @param planCollegeQueryReq
     * @return
     */
    int queryPlanCollegeForManagerCount(@Param("planCollegeQueryReq") PlanCollegeQueryReq planCollegeQueryReq);

    /**
     * 更新申请截止日期 允许置空
     * @param planCollegeInfo
     * @return
     */
    int updateApplyDeadlineById(PlanCollegeInfo planCollegeInfo);
}