package com.aoji.service;
import com.aoji.contants.SearchOption;
import com.aoji.contants.manager.SearchOptionVisaApply;
import com.aoji.model.VisaApplyInfo;
import com.aoji.model.VisaType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 赵剑飞
 * @description 签证申请的服务
 * @date Created in 下午2:29 2017/12/7
 */
public interface VisaApplyService {
    /**
     * 获取签证申请实体
     * @param visaApplyInfo 实体
     * @return
     */
    VisaApplyInfo get(VisaApplyInfo visaApplyInfo);

    /**
     * 获取签证申请实体列表
     * @param visaApplyInfo
     * @return
     */
    List<VisaApplyInfo> getList(VisaApplyInfo visaApplyInfo);

    /**
     * 获取管理层查看签证申请列表
     * @param searchOption
     * @return
     */
    List<Map<String,Object>> getManegeList(SearchOptionVisaApply searchOption);
    List<VisaApplyInfo> getListByOpeator(VisaApplyInfo visaApplyInfo);

    /**
     * 更新实体
     * @param visaApplyInfo
     * @return
     */
    Integer update(VisaApplyInfo visaApplyInfo);

    /**
     * 添加实体
     * @param visaApplyInfo
     * @return
     */
    boolean add(VisaApplyInfo visaApplyInfo);

    HashMap<Integer,String> getVisaWay();

    String getVisaTypeName(Integer key);

    /**
     * 获取签证类型
     * @return
     */
    List<VisaType> getVisaTypeList();
    /**
     * 获取签证类型
     * @return
     */
    List<VisaType> getVisaTypeList(Integer nation);

    /**
     * 根据是否是学生签证及国家获取签证类型
     * @param studentVisaStatus
     * @param nation
     * @return
     */
    List<VisaType> getVisaTypeListBy(Integer studentVisaStatus,Integer nation);


    /**
     * 获取待办事项列表
     * @param visaApplyInfo
     * @return
     */
    List<VisaApplyInfo> getTODOList(VisaApplyInfo visaApplyInfo);

    /**
     * 删除签证申请
     * @param visaApplyInfo
     * @return
     */
    Integer delete(VisaApplyInfo visaApplyInfo);

    /**
     * 获取 我提交的-审批未通过的签证申请 列表
     * @param oaId
     * @return
     */
    List<Map<String,Object>> getUnPassVisaApplyList(String oaId);

    /**
     * "待我审批的 - 签证申请"详细信息
     * @param oaId
     * @return
     */
    List<VisaApplyInfo> getToAuditList(String oaId);

    /**
     * 获取我提交的 - 未审批的签证申请列表
     */
    List<Map<String,Object>> getToAuditListWithMySubmited(String oaId);
}
