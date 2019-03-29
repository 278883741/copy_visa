package com.aoji.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.CaseIdEnum;
import com.aoji.contants.StudentStatus;
import com.aoji.mapper.VisaRecordInfoMapper;
import com.aoji.model.*;
import com.aoji.model.res.School;
import com.aoji.model.res.SchoolData;
import com.aoji.model.res.SchoolRes;
import com.aoji.service.*;
import com.aoji.utils.CacheUtils;
import com.aoji.utils.HttpUtils;
import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zhaojianfei
 * @description 获签信息表
 * @date Created in 下午2:31 2017/12/7
 */
@Service
public class VisaRecordServiceImpl implements VisaRecordService{
    private Logger logger = LoggerFactory.getLogger(VisaRecordServiceImpl.class);

    @Autowired
    VisaRecordInfoMapper visaRecordInfoMapper;
    @Autowired
    AuditApplyService auditApplyService;
    @Autowired
    StudentService studentService;
    @Autowired
    UserService userService;
    @Autowired
    CountryService countryService;
    @Value("${upload.ks3.resDomain}")
    private String resDomain;

    @Value("${student.school.url}")
    private String studentSchoolUrl;
    @Value("${student.cooperation.url}")
    private String studentCooperationUrl;
    @Value("${student.major.url}")
    private String studentMajorUrl;
    @Value("${student.degrees.url}")
    private String studentDegreesUrl;

    @Override
    public boolean add(VisaRecordInfo visaRecordInfo){
        SysUser sysUser = userService.getLoginUser();
        if(visaRecordInfoMapper.insertSelective(visaRecordInfo) > 0){
            return auditApplyService.add(visaRecordInfo.getId(), CaseIdEnum.VisaRecord.getCode(), 1, visaRecordInfo.getStudentNo(),sysUser.getOaid(),sysUser.getUsername());
        }
        return false;
    }
    @Override
    public VisaRecordInfo get(VisaRecordInfo visaRecordInfo){
        visaRecordInfo.setDeleteStatus(false);
        List<VisaRecordInfo> list = visaRecordInfoMapper.select(visaRecordInfo);
        VisaRecordInfo model = null;
        if(list.size() > 0){
            model = list.get(0);
        }
        return model;
    }
    @Override
    public Integer update(VisaRecordInfo visaRecordInfo){
        return visaRecordInfoMapper.updateByPrimaryKeySelective(visaRecordInfo);
    }

    @Override
    public List<SchoolData> getSchool1(Integer nationId) {
        String key = "visaRecord_school_" + nationId;
        return getData(key,String.format(studentSchoolUrl,nationId));
    }

    @Override
    public List<SchoolData> getCooperations(Integer schoolId){
        String key = "visaRecord_school_cooperation_" + schoolId;
        List<SchoolData> data = getData(key, String.format(studentCooperationUrl, schoolId));
        return data;
    }

    @Override
    public List<SchoolData> getMajors(Integer schoolId,Integer majorType,Integer educationSection){
        String key = "visaRecord_major_" + schoolId + "_" + majorType + "_" + educationSection;
        return getData(key,String.format(studentMajorUrl, schoolId,majorType,educationSection));
    }

    @Override
    public List<SchoolData> getDegrees(Integer schoolId,Integer majorId){
        String key = "visaRecord_school_" + schoolId + "_" + majorId;
        return getData(key,String.format(studentDegreesUrl, schoolId,majorId));
    }

    public List<SchoolData> getData(String key,String url){
        CacheItem cacheItem = CacheUtils.getCache(key);
        if (cacheItem == null) {
            List<SchoolData> list = this.getSchoolData(url);
            if (list != null && list.size() > 0) {
                CacheUtils.setCache(key, list,10);
                return list;
            }
        }
        else {
            return (List<SchoolData>)cacheItem.getData();
        }
        return new ArrayList<SchoolData>();
    }

    private List<SchoolData> getSchoolData(String url){
        logger.info("调取小希院校库链接: " + url);
        String json = HttpUtils.doGet(url);
        logger.info("调取小希院校库返回数据: " + json);
        List<SchoolData> list = new ArrayList<>();
        SchoolRes schoolRes = JSONObject.parseObject(json, SchoolRes.class);
        if (schoolRes.getCode() != null && schoolRes.getCode().equals(0)) {
            JSONObject jsonObject = JSONObject.parseObject(schoolRes.getData());
            list = JSONObject.parseArray(jsonObject.get("list").toString(), SchoolData.class);
        }
        return list;
    }

    @Override
    public List<VisaRecordInfo> getList(VisaRecordInfo visaRecordInfo) {
        visaRecordInfo.setDeleteStatus(false);
        return visaRecordInfoMapper.select(visaRecordInfo);
    }

    @Override
    public Integer getCountByAgentId(Integer agentId,Date startDate,Date endDate){
        return visaRecordInfoMapper.getCountByAgentId(agentId, startDate, endDate);
    }

    @Override
    public List<Map<String, Object>> getUnPassVisaRecordList(String oaId) {
        return visaRecordInfoMapper.getUnPassVisaRecordList(oaId);
    }
}
