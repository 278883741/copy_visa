package com.aoji.service;

import com.alibaba.fastjson.JSONObject;
import com.aoji.model.CountryInfo;
import com.aoji.vo.CalibrationSchemeVo;
import com.aoji.vo.PlanCollegeInfoVO;

import java.util.List;
import java.util.Map;

public interface CalibrationSchemeService {


    List<Map<String, Object>> calibrationSchemeList(CalibrationSchemeVo calibrationSchemeVo, Integer[] arrayId);


    JSONObject toVoidPlanCollage(Integer planCollageId, Integer auditStatus);
}
