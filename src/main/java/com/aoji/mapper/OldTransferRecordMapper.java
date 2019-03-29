package com.aoji.mapper;

import com.aoji.model.OldTransferRecord;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface OldTransferRecordMapper extends Mapper<OldTransferRecord> {

    List<OldTransferRecord> selectByStudentAndOperatorType(@Param("studentNo") String studentNo,
                                                           @Param("operatorType") Integer operatorType);
}