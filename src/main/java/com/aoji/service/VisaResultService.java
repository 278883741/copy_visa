package com.aoji.service;
import com.aoji.contants.SearchOption;
import com.aoji.model.VisaResultInfo;
import java.util.List;
import java.util.Map;

/**
 * @author 赵剑飞
 * @description 签证结果的服务
 * @date Created in 下午2:29 2017/12/7
 */
public interface VisaResultService {
    /**
     * 获取实体
     * @param visaResultInfo
     * @return
     */
    VisaResultInfo get(VisaResultInfo visaResultInfo);

    /**
     * 根据id查询实体
     * @param id
     * @return
     */
    VisaResultInfo getById(Integer id);

    /**
     * 根据签证申请id查询实体
     * @param id
     * @return
     */
    VisaResultInfo getByVisaId(Integer id);

    /**
     * 根据学号查询实体
     * @param stuNo
     * @return
     */
    List<VisaResultInfo> getByStuNo(String stuNo);

    /**
     * 更新实体
     * @param visaResultInfo
     * @return
     */
    Integer update(VisaResultInfo visaResultInfo);

    /**
     * 根据id删除实体
     * @param id
     * @return
     */
    Integer delete(Integer id);

    /**
     * 添加实体
     * @param visaResultInfo
     * @return
     */
    Integer add(VisaResultInfo visaResultInfo);

    List<VisaResultInfo> getList(VisaResultInfo visaResultInfo);

    /**
     * 获取管理层查看签证结果列表
     * @param searchOption
     * @return
     */
    List<Map<String,Object>> getManegeList(SearchOption searchOption);

    /**
     * 获取我提交的 - 未审批的签证结果列表
     */
    List<Map<String,Object>> getToAuditList(VisaResultInfo visaResultInfo);
}
