package com.aoji.mapper;

import com.aoji.model.TransferAssignRecord;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TransferAssignRecordMapper extends Mapper<TransferAssignRecord> {

    List<TransferAssignRecord> getbyStudentNo(@Param("studentNo") String studentNo,
                                              @Param("type") String type);
}