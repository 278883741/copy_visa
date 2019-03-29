package com.aoji.service.impl;

import com.alibaba.fastjson.JSON;
import com.aoji.contants.Contants;
import com.aoji.contants.InflowTypeEnum;
import com.aoji.mapper.CommissionSchoolMapper;
import com.aoji.mapper.CommissionStudentMapper;
import com.aoji.mapper.CommissionVisaMessageMapper;
import com.aoji.mapper.VisaResultRecordInfoMapper;
import com.aoji.model.*;
import com.aoji.service.CommissionSchoolService;
import com.aoji.service.CountryService;
import com.aoji.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author wangjunqiang
 * @description
 * @date Created in 2018/6/22 16:06
 */
@Service
public class CommissionSchoolServiceImpl implements CommissionSchoolService {

    public static final Logger logger = LoggerFactory.getLogger(CommissionSchoolServiceImpl.class);

    @Autowired
    private CommissionSchoolMapper commissionSchoolMapper;

    @Autowired
    private CommissionStudentMapper commissionStudentMapper;

    @Autowired
    private VisaResultRecordInfoMapper visaResultRecordInfoMapper;

    @Autowired
    private CommissionVisaMessageMapper commissionVisaMessageMapper;

    @Autowired
    private CountryService countryService;

    @Autowired
    FileService fileService;

    @Value("${upload.ks3.resDomain}")
    private String resDomain;

    //佣金编辑页需要用到的
    @Value("${upload.ks3.newResDomain}")
    private String newResDomain;
    @Value("${commissionImageUrl}")
    private String commissionImageUrl;
    @Value("${upload.ks3.partUrl}")
    private String partUrl;
    @Value("${upload.ks3.applyUrl}")
    private String applyUrl;



    @Override
    public List<CommissionSchool> getCommissionSchoolList(CommissionSchool commissionSchool) {
        commissionSchool.setDeleteStatus(false);
        List<CommissionSchool> commissionSchoolList = commissionSchoolMapper.select(commissionSchool);
        for (CommissionSchool commissionSchools:commissionSchoolList) {
            if(StringUtils.hasText(commissionSchools.getOfferFile())){
                String filesUrl = getFilesUrl(commissionSchools.getOfferFile());
                commissionSchools.setOfferFile(filesUrl);
            }
            if(StringUtils.hasText(commissionSchools.getCoeFile())){
                String filesUrl = getFilesUrl(commissionSchools.getCoeFile());
                commissionSchools.setCoeFile(filesUrl);
            }
            if(StringUtils.hasText(commissionSchools.getEmailFile())){
                String filesUrl = getFilesUrl(commissionSchools.getEmailFile());
                commissionSchools.setEmailFile(filesUrl);
            }
        }
        return commissionSchoolList;
    }
    /**
     * 检查当前offerFile,coeFile,emailFile字段中存储的路径是否是多个路径，以及是否需要设置私密访问
     */
    private String getFilesUrl(String oneOfFile) {
        String stringFile="" ;
        String [] arrayOneOfFile = oneOfFile.split(",");
        if(arrayOneOfFile.length>0){
            for (String  oneOfFiles:arrayOneOfFile) {
                if(oneOfFiles.contains(this.newResDomain)){
                    oneOfFiles = oneOfFiles.replace(this.newResDomain,"");
                    stringFile+=(oneOfFiles)+",";
                }
                if(oneOfFiles.contains(partUrl)){
                    stringFile+=(fileService.getPrivateUrl(oneOfFiles))+",";
                }
                if(oneOfFiles.contains(this.commissionImageUrl)){
                    stringFile+=oneOfFiles+",";
                }
                if(oneOfFiles.contains(this.applyUrl)){
                    stringFile+=(fileService.getPrivateUrl(oneOfFiles))+",";
                }
            }

        }
        return stringFile;
    }

    @Override
    public Integer editCommissionSchool(CommissionSchool commissionSchool) throws  Exception {
        //佣金院校信息修改之后，看当前数据是否为获签同步过来的，如果是，则修改获签数据
        updateVisaResultRecordById(commissionSchool);
        commissionSchool.setDeleteStatus(false);
        if(StringUtils.hasText(commissionSchool.getOfferFile())){
            String s = setFilesUrl(commissionSchool.getOfferFile());
            commissionSchool.setOfferFile(s);
        }

        if(StringUtils.hasText(commissionSchool.getCoeFile()) || commissionSchool.getCoeFile().contains(this.newResDomain)){
            String s = setFilesUrl(commissionSchool.getCoeFile());
            commissionSchool.setCoeFile(s);

        }
        if(StringUtils.hasText(commissionSchool.getEmailFile()) || commissionSchool.getEmailFile().contains(this.newResDomain)){
            String s = setFilesUrl(commissionSchool.getEmailFile());
            commissionSchool.setEmailFile(s);
        }
        return commissionSchoolMapper.updateByPrimaryKeySelective(commissionSchool);


    }

    private void updateVisaResultRecordById(CommissionSchool commissionSchool) throws Exception{
            logger.info("关联文签获签表Id:"+commissionSchool.getVisaCommissionId());
            if(commissionSchool.getVisaCommissionId()!=null&&!"".equals(commissionSchool.getVisaCommissionId())){
                //在修改文签院校数据之前，先查一下原文签院校数据，用于保存在CommissionVisaMessage表中，后期做数据检验
                VisaResultRecordInfo beforeVisa = visaResultRecordInfoMapper.selectByPrimaryKey(commissionSchool.getVisaCommissionId());
                String beforeData = "";
                if(beforeVisa!=null){
                    //原文签数据
                    beforeData = JSON.toJSONString(beforeVisa);
                    logger.info("原文签院校数据{}"+ JSON.toJSONString(beforeVisa));
                }
                VisaResultRecordInfo visaResultRecordInfo = new VisaResultRecordInfo();
                visaResultRecordInfo.setId(commissionSchool.getVisaCommissionId());
                visaResultRecordInfo.setDeleteStatus(false);
                visaResultRecordInfo.setCourseLength(commissionSchool.getStudyWeek());
                visaResultRecordInfo.setCollegeName(commissionSchool.getSchoolName());
                Integer collegeId = null;
                if(StringUtils.hasText(commissionSchool.getCollegeId())){
                    collegeId = Integer.valueOf(commissionSchool.getCollegeId());
                }
                visaResultRecordInfo.setCollegeId(collegeId);
                visaResultRecordInfo.setSchoolArea(commissionSchool.getSchoolArea());
                visaResultRecordInfo.setCourseStartTime(commissionSchool.getStartDate());
                visaResultRecordInfo.setPrepaidTuition(commissionSchool.getPaidFee());
                visaResultRecordInfo.setCooperationId(commissionSchool.getAgencyNo());
                visaResultRecordInfo.setCooperationName(commissionSchool.getAgencyName());
                SimpleDateFormat formatter = new SimpleDateFormat(Contants.timePattern);
                String endDate = "";
                if(commissionSchool.getEndDate()!=null && !"".equals(commissionSchool.getEndDate())){
                    endDate = formatter.format(commissionSchool.getEndDate());
                }
                visaResultRecordInfo.setCourseEndTime(endDate);
                visaResultRecordInfo.setCourseId(commissionSchool.getCourseId());
                if(StringUtils.hasText(commissionSchool.getCollegeType())){
                    Integer collegeType = Integer.valueOf(commissionSchool.getCollegeType());
                    if(commissionSchool.getCollegeType().equals("2")){
                        visaResultRecordInfo.setCourseType(1);
                    }else if(commissionSchool.getCollegeType().equals("3")) {
                        visaResultRecordInfo.setCourseType(2);
                    }else if (commissionSchool.getCollegeType().equals("1")){
                        visaResultRecordInfo.setCourseType(3);
                    }else {
                        visaResultRecordInfo.setCourseType(collegeType);
                    }
                }
                visaResultRecordInfo.setCourseName(commissionSchool.getCourseName());
                Integer majorId = null;
                if(StringUtils.hasText(commissionSchool.getMajorId())){
                    majorId = Integer.valueOf(commissionSchool.getMajorId());
                }
                visaResultRecordInfo.setMajorId(majorId);
                visaResultRecordInfo.setMajorName(commissionSchool.getMajorName());
                visaResultRecordInfo.setTuition(commissionSchool.getTuition());
                Integer currency = null;
                if(StringUtils.hasText(commissionSchool.getTuitionCurrency())){
                    currency = Integer.valueOf(commissionSchool.getTuitionCurrency());
                }
                visaResultRecordInfo.setCurrency(currency);
                visaResultRecordInfo.setUpdateTime(new Date());
                visaResultRecordInfo.setStudentNo(commissionSchool.getStudentNo());
                visaResultRecordInfo.setEducationSection(commissionSchool.getEducationSection());
                int result = visaResultRecordInfoMapper.updateByPrimaryKeySelective(visaResultRecordInfo);

                //佣金修改的数据
                String afterData = JSON.toJSONString(visaResultRecordInfo);
                logger.info("佣金修改的数据信息{}"+afterData);
                //无论成功与否，都将数据流入类型更改为佣金同步到文签
                if(result>0){
                    //成功更改数据流入类型及之前的导入失败状态改为导入成功
                    commissionVisaMessageMapper.updateInflowTypeByVisaRecordResultId(commissionSchool.getVisaCommissionId(), InflowTypeEnum.COMMISSIONTOVISA.getCode(),1,beforeData,afterData);
                    logger.info("获签同步到佣金成功，院校Id:"+commissionSchool.getVisaCommissionId()+"数据流入类型:"+InflowTypeEnum.COMMISSIONTOVISA.getCode()+"是否导出到佣金系统:"+1);
                }else{
                    //失败不仅更改数据流入类型，还更改send_commission_status状态为0
                    commissionVisaMessageMapper.updateInflowTypeByVisaRecordResultId(commissionSchool.getVisaCommissionId(), InflowTypeEnum.COMMISSIONTOVISA.getCode(),0,beforeData,afterData);
                    logger.info("获签同步到佣金失败，院校Id:"+commissionSchool.getVisaCommissionId()+"数据流入类型:"+InflowTypeEnum.COMMISSIONTOVISA.getCode()+"是否导出到佣金系统:"+0);
                }
            }

    }

    /**
     * 检查当前offerFile,coeFile,emailFile字段中存储的路径是否是多个路径
     */
    public String setFilesUrl(String oneOfFile) {
        String stringFile="" ;
        String [] arrayOneOfFile = oneOfFile.split(",");
        if(arrayOneOfFile.length>0){
            for (String  oneOfFiles:arrayOneOfFile) {
                oneOfFiles = oneOfFiles.replace(this.newResDomain, "").replace("\"","").replace("[","").replace("]","");
                if (oneOfFiles.contains("?")) {
                    int i = oneOfFiles.indexOf("?");//首先获取字符的位置
                    String newStr = oneOfFiles.substring(0, i);//再对字符串进行截取，获得想要得到的字符串
                    stringFile += newStr + ",";
                } else {
                    stringFile += oneOfFiles + ",";
                }
            }
        }
        return stringFile;
    }



    @Override
    public Boolean removeCommissionSchool(String schoolId) {
        CommissionSchool commissionSchool = new CommissionSchool();
        commissionSchool.setId(Integer.valueOf(schoolId));
        commissionSchool.setDeleteStatus(true);
        return commissionSchoolMapper.updateByPrimaryKeySelective(commissionSchool) > 0;
    }

    @Override
    public Integer editCommissionStudent(CommissionStudent commissionStudent) {
        commissionStudent.setDeleteStatus(false);
        if(commissionStudent.getNationId() != null){
            CountryInfo countryInfo = new CountryInfo();
            countryInfo.setId(commissionStudent.getNationId());
            countryInfo = countryService.get(countryInfo);
            if(countryInfo != null){
                commissionStudent.setNationName(countryInfo.getCountryName());
            }
        }
        return commissionStudentMapper.updateByPrimaryKeySelective(commissionStudent);
    }

    @Override
    public Integer addCommissionSchool(CommissionSchool commissionSchool) {
        commissionSchool.setDeleteStatus(false);
        if(StringUtils.hasText(commissionSchool.getOfferFile())){
            String s = setFilesUrl(commissionSchool.getOfferFile());
            commissionSchool.setOfferFile(s);
        }

        if(StringUtils.hasText(commissionSchool.getCoeFile())){
            String s = setFilesUrl(commissionSchool.getCoeFile());
            commissionSchool.setCoeFile(s);

        }
        if(StringUtils.hasText(commissionSchool.getEmailFile())){
            String s = setFilesUrl(commissionSchool.getEmailFile());
            commissionSchool.setEmailFile(s);
        }

        return commissionSchoolMapper.insert(commissionSchool);
    }
}
