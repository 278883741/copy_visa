package com.aoji.mapper;

import com.aoji.model.AgencyInfo;
import tk.mybatis.mapper.common.Mapper;
import com.aoji.model.*;
import java.util.List;


public interface AgencyInfoMapper extends Mapper<AgencyInfo> {
    List<AgencyInfo> get(AgencyInfo agencyInfo);
}