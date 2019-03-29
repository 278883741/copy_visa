package com.aoji.service;

import com.aoji.model.BaseResponse;
import com.aoji.model.CoeApplyInfo;
import com.aoji.model.req.CoeAuditReq;
import com.aoji.vo.CoeApplyVo;

import java.util.List;

public interface CoeApplyService {
    Boolean saveCoeApplyAndSupplement(CoeApplyVo coeApplyVo);

    /**
     * 更新申请记录实体
     * @param applyInfo
     * @return
     */
    Integer update(CoeApplyInfo coeApplyInfo);

    /**
     * 根据申请id查询申请详情
     * @param applyId 申请id
     * @return
     */
    CoeApplyVo getApplyDetail(Integer applyId,Integer id);

    /**
     * 根据id查询申请详情
     * @param  id
     * @return
     */
    CoeApplyInfo getById(Integer id);

    /**
     * 查询coe列表
     * @param coeApplyInfo
     * @return
     */
    List<CoeApplyInfo> getList(CoeApplyInfo coeApplyInfo);

    /**
     * 查询coe列表，NPA非私密附件，私密附件地址前台获取，不绑定
     * @param coeApplyInfo
     * @return
     */
    List<CoeApplyInfo> getListNPA(CoeApplyInfo coeApplyInfo);

    CoeApplyInfo get(CoeApplyInfo coeApplyInfo);

    /**
     * 审批
     * @return
     */
    BaseResponse approve(CoeAuditReq coeAuditReq,Integer nationId);

    Integer delete(Integer id);
}
