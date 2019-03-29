package com.aoji.service;
import com.aoji.model.VisaRecordInfo;
import com.aoji.model.res.School;
import com.aoji.model.res.SchoolData;
import io.swagger.models.auth.In;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 赵剑飞
 * @description 获签信息的服务
 * @date Created in 下午2:29 2017/12/7
 */
public interface VisaRecordService {
    /**
     * 添加实体
     * @param visaRecordInfo
     * @return
     */
    boolean add(VisaRecordInfo visaRecordInfo);

    /**
     * 获取获签信息实体
     * @param visaRecordInfo
     * @return
     */
    VisaRecordInfo get(VisaRecordInfo visaRecordInfo);

    /**
     * 更新实体
     * @param visaRecordInfo
     * @return
     */
    Integer update(VisaRecordInfo visaRecordInfo);

    /**
     * 获取列表
     * @param recordInfo
     * @return
     */
    List<VisaRecordInfo> getList(VisaRecordInfo recordInfo);

    /**
     * 获取小希院校库 -- 院校
     * @param nationId
     * @return
     */
    List<SchoolData> getSchool1(Integer nationId);

    /**
     * 获取合作机构
     * @param schoolId
     * @return
     */
    List<SchoolData> getCooperations(Integer schoolId);

    /**
     * 获取小希院校库 -- 专业
     * @param schoolId
     * @param majorType
     * @param educationSection
     * @return
     */
    List<SchoolData> getMajors(Integer schoolId,Integer majorType,Integer educationSection);

    /**
     * 获取小希院校库 -- 学位
     * @param schoolId
     * @param majorId
     * @return
     */
    List<SchoolData> getDegrees(Integer schoolId,Integer majorId);

    /**
     * 根据代理id获取获签学生数
     * @param agentId
     * @param startDate
     * @param endDate
     * @return
     */
    Integer getCountByAgentId(Integer agentId, Date startDate, Date endDate);

    /**
     * 获取 我提交的-审批未通过的获签信息 列表
     * @param oaId
     * @return
     */
    List<Map<String,Object>> getUnPassVisaRecordList(String oaId);
}
