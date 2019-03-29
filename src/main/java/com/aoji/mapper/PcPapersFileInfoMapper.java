package com.aoji.mapper;

import com.aoji.model.PcPapersFileInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PcPapersFileInfoMapper extends Mapper<PcPapersFileInfo> {
    List<PcPapersFileInfo> getList(@Param("pcPapersFileInfo") PcPapersFileInfo pcPapersFileInfo);
}