package com.aoji.service;

import com.aoji.model.CountryInfo;
import com.aoji.vo.CalibrationSchemeVo;

import java.util.List;
import java.util.Map;

public interface TransferCaseService {



    List<Map<String,Object>> transferCaseListByVisaManagerOrDirector(CalibrationSchemeVo calibrationSchemeVo, Integer[] arrayId);

}
