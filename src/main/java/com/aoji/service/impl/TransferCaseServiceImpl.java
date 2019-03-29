package com.aoji.service.impl;

import com.aoji.mapper.CountryInfoMapper;
import com.aoji.mapper.PlanCollegeInfoMapper;
import com.aoji.mapper.TransferInfoMapper;
import com.aoji.service.TransferCaseService;
import com.aoji.vo.CalibrationSchemeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TransferCaseServiceImpl  implements TransferCaseService{

    @Autowired
    private PlanCollegeInfoMapper planCollegeInfoMapper;

    @Autowired
    private CountryInfoMapper countryInfoMapper;

    @Autowired
    private TransferInfoMapper transferInfoMapper;

    @Override
    public List<Map<String, Object>> transferCaseListByVisaManagerOrDirector(CalibrationSchemeVo calibrationSchemeVo,Integer[] arrayId) {
        return transferInfoMapper.transferCaseListByVisaManagerOrDirector(calibrationSchemeVo,arrayId);
    }



}
