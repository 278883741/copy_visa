package com.aoji.service;

import com.aoji.model.CommissionRecordInfo;

import java.util.List;

public interface CommissionRecordService {
    List<CommissionRecordInfo> getList(CommissionRecordInfo commissionRecordInfo,String dateStart, String dateEnd);

    Integer add(CommissionRecordInfo commissionRecordInfo);

    CommissionRecordInfo getById(Integer id);

    Integer update(CommissionRecordInfo commissionRecordInfo);

    Integer delete(Integer id);
}
