package com.aoji.service;

import com.aoji.model.BaseResponse;
import com.aoji.model.PlanCollegeInfo;
import com.aoji.model.req.PlanCollegeCondition;
import com.aoji.model.req.PlanCollegeInfoReq;
import com.aoji.model.req.PlanCollegeQueryReq;
import com.aoji.model.req.PlanCollegeReq;
import com.aoji.model.res.PlanCollege;
import com.aoji.model.res.PlanCollegeRes;
import com.aoji.vo.PlanCollegeInfoVO;

import java.util.List;

/**
 * author: chenhaibo
 * description: 定校方案管理
 * date: 2018/1/25
 */
public interface PlanCollegeInfoService {

    /**
     * 定校方案列表
     * @param planCollegeInfoVO
     * @return
     */
    List<PlanCollegeInfoVO> getPlanCollege(PlanCollegeInfoVO planCollegeInfoVO);

    /**
     * 定校方案总数
     * @param planCollegeInfoVO
     * @return
     */
    int getPlanCollegeCount(PlanCollegeInfoVO planCollegeInfoVO);

    /**
     * 工作台待确认定校方案总数
     * @param planCollegeInfoVO
     * @return
     */
    int getPlanCollegeCountByWorktable(PlanCollegeInfoVO planCollegeInfoVO);

    /**
     * 修改状态
     * @param req
     * @return
     */
    Boolean updateAndInsertApplyInfo(PlanCollegeReq req);

    Integer update(PlanCollegeInfo planCollegeInfo);
    PlanCollegeInfo get(PlanCollegeInfo planCollegeInfo);

    /**
     * 分配外联顾问
     * @param req
     * @return
     */
    Boolean assignConnector(PlanCollegeReq req);

    /**
     * 分配外联顾问
     * @param applyId
     * @param oaid
     * @param connector
     * @return
     */
    Boolean assignConnector(Integer applyId, String oaid, String connector);

    /**
     * 分页查询定校方案
     * @param planCollegeInfoVO
     * @return
     */
    List<PlanCollegeInfoVO> planCollegeByPage(PlanCollegeInfoVO planCollegeInfoVO);

    /**
     * 运营人员操作学生确认定校方案
     * @param id
     * @param studentNo
     * @return
     */
    Integer studentConfirm(Integer id, String studentNo);

    /**
     * 添加、修改、作废定校方案
     * @param planCollegeInfoReq
     * @return
     */
    BaseResponse mergePlanCollege(PlanCollegeInfoReq planCollegeInfoReq);

    /**
     * 查询定校方案 -- 签约系统
     * @return
     */
    List<PlanCollege> queryPlanCollege(PlanCollegeCondition planCollegeCondition);

    /**
     * 管理层查看定校方案 -- 签约系统
     * @param planCollegeQueryReq
     * @return
     */
    PlanCollegeRes queryPlanCollege(PlanCollegeQueryReq planCollegeQueryReq);

    /**
     * 查询定校方案 -- 文签
     * @param planCollegeCondition
     * @return
     */
    List<PlanCollegeInfoVO> queryPlanCollegeForVisa(PlanCollegeCondition planCollegeCondition);
}
