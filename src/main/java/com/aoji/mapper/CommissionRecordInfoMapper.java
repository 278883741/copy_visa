package com.aoji.mapper;

import com.aoji.model.CommissionRecordInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CommissionRecordInfoMapper extends Mapper<CommissionRecordInfo> {
    List<CommissionRecordInfo> getList(@Param("commissionRecordInfo") CommissionRecordInfo commissionRecordInfo,
                                       @Param("dateStart") String dateStart, @Param("dateEnd") String dateEnd);
}