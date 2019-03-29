package com.aoji.service.impl;
import com.aoji.contants.*;
import com.aoji.mapper.VisaResultInfoMapper;
import com.aoji.model.*;
import com.aoji.model.req.SendMessageReq;
import com.aoji.service.*;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaojianfei
 * @description 签证结果信息表
 * @date Created in 下午2:31 2017/12/7
 */
@Service
public class VisaResultServiceImpl implements VisaResultService {
    @Autowired
    VisaResultInfoMapper visaResultInfoMapper;
    @Autowired
    StudentService studentService;
    @Autowired
    UserTaskRelationService userTaskRelationService;
    @Autowired
    UserService userService;
    @Autowired
    ApplyCollegeService applyCollegeService;
    @Autowired
    AuditApplyService auditApplyService;
    @Autowired
    AuditResultService auditResultService;
    @Autowired
    FileService fileService;

    @Value("${upload.ks3.resDomain}")
    private String resDomain;

    @Override
    public VisaResultInfo get(VisaResultInfo visaResultInfo){
        visaResultInfo.setDeleteStatus(false);
        List<VisaResultInfo> list= visaResultInfoMapper.select(visaResultInfo);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public VisaResultInfo getById(Integer id) {
        VisaResultInfo visaResultInfo = new VisaResultInfo();
        visaResultInfo.setId(id);
        visaResultInfo.setDeleteStatus(false);
        List<VisaResultInfo> list = visaResultInfoMapper.select(visaResultInfo);
        if(list != null && list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public VisaResultInfo getByVisaId(Integer id) {
        VisaResultInfo visaResultInfo = new VisaResultInfo();
        visaResultInfo.setVisaId(id);
        visaResultInfo.setDeleteStatus(false);
        List<VisaResultInfo> list = visaResultInfoMapper.select(visaResultInfo);
        VisaResultInfo model = null;
        if(list.size() > 0){
            model = list.get(0);
        }
        return model;
    }

    @Override
    public List<VisaResultInfo> getByStuNo(String stuNo) {
        VisaResultInfo visaResultInfo = new VisaResultInfo();
        visaResultInfo.setStudentNo(stuNo);
        List<VisaResultInfo> list = this.getList(visaResultInfo);
        return list;
    }

    @Override
    public Integer update(VisaResultInfo visaResultInfo) {
        return visaResultInfoMapper.updateByPrimaryKeySelective(visaResultInfo);
    }

    @Override
    public Integer delete(Integer id){
        SysUser sysUser = userService.getLoginUser();
        VisaResultInfo visaResultInfo = getById(id);
        if(visaResultInfo != null){
            AuditApplyInfo auditApplyInfo = new AuditApplyInfo();
            auditApplyInfo.setBusinessId(visaResultInfo.getId());
            auditApplyInfo.setDeleteStatus(false);
            auditApplyInfo.setCaseId(CaseIdEnum.VisaResult.getCode());
            auditApplyInfo = auditApplyService.get(auditApplyInfo);
            if(auditApplyInfo != null){
                auditApplyInfo.setDeleteStatus(true);
                auditApplyInfo.setUpdateTime(new Date());
                auditApplyInfo.setOperatorNo(sysUser.getOaid());
                auditApplyInfo.setOperatorName(sysUser.getUsername());
                auditApplyService.update(auditApplyInfo);
            }

            visaResultInfo.setDeleteStatus(true);
            visaResultInfo.setUpdateTime(new Date());
            visaResultInfo.setOperatorNo(sysUser.getOaid());
            visaResultInfo.setOperatorName(sysUser.getUsername());
            return visaResultInfoMapper.updateByPrimaryKey(visaResultInfo);
        }
       return  0;
    }

    @Override
    public Integer add(VisaResultInfo visaResultInfo){
        SysUser sysUser = userService.getLoginUser();
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(visaResultInfo.getStudentNo());
        studentInfo.setDeleteStatus(false);
        studentInfo = studentService.get(studentInfo);

        // 澳新美加英 - 给学生外联老师发工作消息
        if(studentInfo.getNationId().equals(CountryEnum.Australia.getCode()) || studentInfo.getNationId().equals(CountryEnum.NewZealand.getCode()) || studentInfo.getNationId().equals(CountryEnum.America.getCode())
                ||studentInfo.getNationId().equals(CountryEnum.Canada.getCode()) || studentInfo.getNationId().equals(CountryEnum.England.getCode()) || studentInfo.getNationId().equals(40) || studentInfo.getNationId().equals(41)) {
            List<String> list = applyCollegeService.getApplyInfoByStudentNo(studentInfo.getStudentNo());
            for(String item:list) {
                SendMessageReq sendMessageReq = new SendMessageReq();
                sendMessageReq.setOaid(item);
                sendMessageReq.setTemplateCode("visaResultConfirm");
                sendMessageReq.setOperatorNo(sysUser.getOaid());
                sendMessageReq.setStudentNo(visaResultInfo.getStudentNo());
                sendMessageReq.setTaskType(Contants.WORK_MESSAGE);
                Map<String, String> map = new HashMap<String, String>();
                map.put("studentNo", visaResultInfo.getStudentNo());
                map.put("studentName", studentInfo.getStudentName());
                sendMessageReq.setTemplateParam(map);
                userTaskRelationService.sendMessage(sendMessageReq);
            }
        }
        return visaResultInfoMapper.insertSelective(visaResultInfo);
    }

    @Override
    public List<VisaResultInfo> getList(VisaResultInfo visaResultInfo) {
        visaResultInfo.setDeleteStatus(false);
        List<VisaResultInfo> list = visaResultInfoMapper.select(visaResultInfo);
        for (VisaResultInfo item:list){
            String attachment = item.getAttachment();
            if(attachment!=null){
                if(attachment.contains(this.resDomain)){
                    attachment = attachment.replace(this.resDomain,"");
                }
                item.setAttachment(fileService.getPrivateUrl(attachment));
            }

        }
        return list;
    }

    @Override
    public List<Map<String,Object>> getManegeList(SearchOption searchOption) {
        List<Map<String,Object>> list = visaResultInfoMapper.getManageList(searchOption);
        list.forEach(item -> {
                    if (!StringUtils.isEmpty(item.get("attachment"))) {
                        item.put("attachment", fileService.getPrivateUrl(item.get("attachment").toString()));
                    }
                }
        );
        return list;
    }

    @Override
    public List<Map<String, Object>> getToAuditList(VisaResultInfo visaResultInfo) {
        return visaResultInfoMapper.getToAuditList(visaResultInfo);
    }
}
