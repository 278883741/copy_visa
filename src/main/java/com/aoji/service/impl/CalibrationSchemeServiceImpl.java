package com.aoji.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.CollegeAuditStatusEnum;
import com.aoji.mapper.CountryInfoMapper;
import com.aoji.mapper.PlanCollegeInfoMapper;
import com.aoji.model.CountryInfo;
import com.aoji.service.CalibrationSchemeService;
import com.aoji.vo.CalibrationSchemeVo;
import com.aoji.vo.PlanCollegeInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CalibrationSchemeServiceImpl implements CalibrationSchemeService {

    public static final Logger logger = LoggerFactory.getLogger(CalibrationSchemeServiceImpl.class);

    @Autowired
    private PlanCollegeInfoMapper planCollegeInfoMapper;

    @Autowired
    private CountryInfoMapper countryInfoMapper;


    @Override
    public List<Map<String, Object>> calibrationSchemeList(CalibrationSchemeVo calibrationSchemeVo, Integer[] arrayId) {


        return planCollegeInfoMapper.calibrationSchemeList(calibrationSchemeVo,arrayId);
    }

    @Override
    public JSONObject toVoidPlanCollage(Integer planCollageId,Integer auditStatus) {
        JSONObject jsonObjectPlanCollage  = new JSONObject();
        boolean a =  planCollegeInfoMapper.toVoidPlanCollageByPlanCollageId(planCollageId,auditStatus);
        if(!a){
            jsonObjectPlanCollage.put("result",false);
            logger.info("定校方案院校信息删除失败！planCollageId:"+planCollageId);
        }else{
            jsonObjectPlanCollage.put("result",true);
            logger.info("定校方案院校信息删除成功！planCollageId:"+planCollageId);
        }
        return jsonObjectPlanCollage;
    }


}
