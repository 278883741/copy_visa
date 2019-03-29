package com.aoji.mapper;

import com.aoji.model.SupplementInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface SupplementInfoMapper extends Mapper<SupplementInfo> {

    Integer updateAttachment(@Param("copyUrl")String copyUrl,@Param("applyId")String applyId);

    SupplementInfo getSupplementInfoByApplyId(@Param("applyId") String applyId);
}