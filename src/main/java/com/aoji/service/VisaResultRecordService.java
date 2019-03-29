package com.aoji.service;
import com.aoji.model.VisaResultRecordInfo;
import java.util.List;

/**
 * @author 赵剑飞
 * @description 获签信息院校的服务
 * @date Created in 下午2:29 2017/12/7
 */
public interface VisaResultRecordService {
    /**
     * 添加实体
     * @param visaResultRecordInfo
     * @return
     */
    boolean add(VisaResultRecordInfo visaResultRecordInfo);

    VisaResultRecordInfo get(VisaResultRecordInfo visaResultRecordInfo);
    /**
     * 获取实体
     * @param id
     * @return
     */
    List<VisaResultRecordInfo> getListByRecordId(Integer id);

    /**
     * 删除实体
     * @param id
     * @return
     */
    void deleteByRecordId(Integer id);
    Integer delete(Integer id);

    /**
     * 更新实体
     * @param visaResultRecordInfo
     * @return
     */
    Integer updateOne(VisaResultRecordInfo visaResultRecordInfo);
}
