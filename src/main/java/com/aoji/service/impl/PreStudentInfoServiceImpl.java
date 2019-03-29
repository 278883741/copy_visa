package com.aoji.service.impl;

import com.aoji.mapper.PreStudentInfoMapper;
import com.aoji.mapper.SysRoleMapper;
import com.aoji.model.CountryInfo;
import com.aoji.model.PreStudentInfo;
import com.aoji.model.StudentInfo;
import com.aoji.model.SysUser;
import com.aoji.model.req.TransferReq;
import com.aoji.model.res.TransferRes;
import com.aoji.service.CountryService;
import com.aoji.service.PreStudentInfoService;
import com.aoji.service.TransferSendService;
import com.aoji.service.UserService;
import com.aoji.vo.StudentInfoVo;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author wangjunqiang
 * @description
 * @date Created in 2018/5/17 17:28
 */
@Service
public class PreStudentInfoServiceImpl implements PreStudentInfoService {

    public static final Logger logger = LoggerFactory.getLogger(PreStudentInfoServiceImpl.class);

    @Autowired
     PreStudentInfoMapper preStudentInfoMapper;

    @Autowired
    private UserService userService;

    @Autowired
    CountryService countryService;

    @Autowired
    private TransferSendService transferSendService;

    @Autowired
    private SysRoleMapper sysRoleMapper;



    @Override
    public List<PreStudentInfo> preStudentInfoList(PreStudentInfo preStudentInfo,String roleName) {
        //查询oaid对应的角色
        return preStudentInfoMapper.selectPreStudentInfo(preStudentInfo,roleName);
    }

    @Override
    public List<SysUser> getAllotTeacher() {
        return preStudentInfoMapper.getAllotTeacher();
    }

    @Override
    public Integer updatePreStudentByStudentNo(String studentNo, String oaid) {
        SysUser sysUser = userService.getUserByName(oaid);
        String userName = "";
        if(sysUser != null && StringUtils.hasText(sysUser.getUsername())){
            userName = sysUser.getUsername();
        }
        PreStudentInfo preStudentInfo = new PreStudentInfo();
        preStudentInfo.setStudentNo(studentNo);
        preStudentInfo.setHeadmasterName(userName);
        preStudentInfo.setHeadmasterNo(oaid);
        preStudentInfo.setOperatorNo(sysUser.getOaid());
        preStudentInfo.setOperatorName(sysUser.getUsername());
        return preStudentInfoMapper.updatePreStudentInfoByStudentNo(preStudentInfo);
    }

    @Override
    public int insert(PreStudentInfo preStudentInfo) {
        preStudentInfo.setDeleteStatus(false);
        preStudentInfo.setCreateTime(new Date());
        preStudentInfo.setIsTransfer(0);
        Integer oldNationId = preStudentInfo.getOldNationId();
        if(oldNationId.equals(59)){
            preStudentInfo.setNationId(0);
        }else if(oldNationId.equals(28) || oldNationId.equals(29)){
            preStudentInfo.setNationId(2);
        }else if(oldNationId.equals(61) || oldNationId.equals(49)){
            preStudentInfo.setNationId(18);
        }else if(oldNationId.equals(54)){
            preStudentInfo.setNationId(3);
        }
        CountryInfo countryInfo = new CountryInfo();
        countryInfo.setCountryBussid(oldNationId);
        countryInfo = countryService.get(countryInfo);
        preStudentInfo.setNationName(countryInfo.getCountryName());
        preStudentInfo.setIsTransfer(0);
        preStudentInfo.setIsAllot(0);
        return preStudentInfoMapper.insert(preStudentInfo);
    }

    @Override
    public TransferRes toTranfer(String studentNo, String remark) {
        TransferReq transferReq = new TransferReq();
        TransferRes transferRes = new TransferRes();
        PreStudentInfo preStudentInfo = new PreStudentInfo();
        preStudentInfo.setDeleteStatus(false);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
            preStudentInfo.setStudentNo(studentNo);
            List<PreStudentInfo> preStudentInfoList = preStudentInfoMapper.select(preStudentInfo);
            if(preStudentInfoList != null && preStudentInfoList.size() >=1){
                preStudentInfo = preStudentInfoList.get(0);
                transferReq.setStudentNo(studentNo);
                transferReq.setStudentName(preStudentInfo.getStudentName());
                transferReq.setSpelling(preStudentInfo.getPinyin());
                transferReq.setBirthday(simpleDateFormat.format(preStudentInfo.getBirthday()));
                transferReq.setContractNo(preStudentInfo.getContractNo());
                transferReq.setContractType(preStudentInfo.getContractType());
                transferReq.setUsaAStatus(preStudentInfo.getUsaStatus());
                transferReq.setSignDate(simpleDateFormat.format(preStudentInfo.getSignDate()));
                transferReq.setCountry(preStudentInfo.getNationId());
                transferReq.setConfeeid(preStudentInfo.getConfeeid());
                transferReq.setCountryName(preStudentInfo.getNationName());
                transferReq.setOperatorName(preStudentInfo.getOperatorName());
                transferReq.setOperatorNo(preStudentInfo.getOperatorNo());
                transferReq.setBrandId(preStudentInfo.getBranchId());
                transferReq.setBrandName(preStudentInfo.getBranchName());
                transferReq.setConsultantNo(preStudentInfo.getSalesConsultantNo());
                transferReq.setConsultant(preStudentInfo.getSalesConsultant());
                transferReq.setChannelStatus(preStudentInfo.getChannelStatus());
                if(!StringUtils.hasText(preStudentInfo.getContent())){
                    transferReq.setComment("--预科备注："+remark+"--");

                }else{
                    transferReq.setComment(preStudentInfo.getContent()+"--预科备注："+remark+"--");
                }

                transferRes = transferSendService.doTransfer(transferReq);
            }else {
                transferRes.setResult(false);
                transferRes.setErrorMsg("此预科学生不存在");
                logger.info("此预科学生不存在!studentNo"+studentNo);
            }

            if(transferRes.isResult()){
                PreStudentInfo preStudentInfo1 = new PreStudentInfo();
                preStudentInfo1 = preStudentInfoList.get(0);
                if(!StringUtils.hasText(preStudentInfo.getContent())){
                    preStudentInfo1.setContent("--预科备注："+remark+"--");

                }else{
                    preStudentInfo1.setContent(preStudentInfo.getContent()+"--预科备注："+remark+"--");

                }
                preStudentInfoMapper.updateByPrimaryKey(preStudentInfo1);
                preStudentInfoMapper.updateIsTransferByStudentNo(studentNo);

            }
        }catch(Exception e){
            logger.error("toTransfer to Error!:"+ e.getMessage());
            e.printStackTrace();
        }
        return transferRes;
    }

    @Override
    public String getRoleName() {
        SysUser sysUser = userService.getLoginUser();
        String roleName = "";
        List<String> roles = sysRoleMapper.getRoleByOaId(sysUser.getOaid());
        if (roles != null && roles.size() > 0) {
            if(roles.contains("预科主管")){
                roleName = "预科主管";
            }
        }
        return roleName;
    }

    @Override
    public void test(){

        List<StudentInfoVo> studentList =  preStudentInfoMapper.getStudentInfo();
        for (StudentInfoVo studentInfo:studentList) {
            PreStudentInfo preStudentInfo = new PreStudentInfo();
            preStudentInfo.setStudentNo(studentInfo.getStudentNo());
            preStudentInfo.setStudentName(studentInfo.getStudentName());
            preStudentInfo.setDeleteStatus(false);
            preStudentInfo.setOperatorNo(studentInfo.getOperatorNo());
            preStudentInfo.setHeadmasterNo(studentInfo.getHeadmasterNo());
            preStudentInfo.setHeadmasterName(studentInfo.getHeadmasterName());
            preStudentInfo.setNationId(studentInfo.getNationId());
            preStudentInfo.setBirthday(studentInfo.getBirthday());
            preStudentInfo.setBranchId(studentInfo.getBranchId());
            preStudentInfo.setBranchName(studentInfo.getBranchName());
            preStudentInfo.setChannelStatus(studentInfo.getChannelStatus());
            preStudentInfo.setContractNo(studentInfo.getContractNo());
            preStudentInfo.setContractType(studentInfo.getContractType());
            preStudentInfo.setCreateTime(studentInfo.getCreateTime());
            preStudentInfo.setOldNationId(preStudentInfoMapper.getOldNationId(studentInfo.getNationName()));
            preStudentInfo.setPinyin(studentInfo.getPinyin());
            preStudentInfo.setSalesConsultant(studentInfo.getSalesConsultant());
            preStudentInfo.setSalesConsultantNo(studentInfo.getSalesConsultantNo());
            preStudentInfo.setSignDate(studentInfo.getSignDate());
            preStudentInfo.setConfeeid(studentInfo.getConfeeid());
            preStudentInfo.setIsTransfer(0);
            preStudentInfo.setNationName(studentInfo.getNationName());
            preStudentInfo.setStatus(studentInfo.getStatus());
            preStudentInfo.setUpdateTime(studentInfo.getUpdateTime());
            preStudentInfo.setUsaStatus(false);
            preStudentInfoMapper.insert(preStudentInfo);
        }
    }



}
