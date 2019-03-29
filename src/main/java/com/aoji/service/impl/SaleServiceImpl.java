package com.aoji.service.impl;

import com.aoji.contants.Contants;
import com.aoji.mapper.ApplyInfoMapper;

import com.aoji.model.ApplyInfo;

import com.aoji.model.StudentInfo;
import com.aoji.model.SysUser;
import com.aoji.model.req.SendMessageReq;
import com.aoji.service.SaleService;
import com.aoji.service.StudentService;
import com.aoji.service.UserTaskRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SaleServiceImpl implements SaleService{
    @Autowired
    private ApplyInfoMapper applyInfoMapper;

    @Autowired
    private UserTaskRelationService userTaskRelationService;

    @Autowired
    StudentService studentService;

    @Override
    public ApplyInfo getById(Integer id) {

        return applyInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer update(ApplyInfo applyInfo) {

        return applyInfoMapper.updateByPrimaryKeySelective(applyInfo);
    }

    @Override
    public Boolean sendMessageByOperatorNo(SysUser sysUser, ApplyInfo applyInfo) {
        //给操作人发送设置入读院校成功的消息
        SendMessageReq sendMessageReq1 = new SendMessageReq();
        sendMessageReq1.setOaid(applyInfo.getOperatorNo());
        sendMessageImpl(sendMessageReq1,sysUser,applyInfo);
        //给外联顾问发送设置入读院校成功的消息
        StudentInfo studentInfoByStudentNo = studentService.getStudentInfoByStudentNo(applyInfo.getStudentNo());
        SendMessageReq sendMessageReq2 = new SendMessageReq();
        sendMessageReq2.setOaid(studentInfoByStudentNo.getSalesConsultantNo());
        sendMessageImpl(sendMessageReq2,sysUser,applyInfo);
        return true;
    }
    public Boolean sendMessageImpl(SendMessageReq sendMessageReq,SysUser sysUser, ApplyInfo applyInfo){
        sendMessageReq.setTemplateCode("toReadSchool");
        sendMessageReq.setOperatorNo(sysUser.getOaid());
        sendMessageReq.setStudentNo(applyInfo.getStudentNo());
        sendMessageReq.setTaskType(Contants.WORK_MESSAGE);
        StudentInfo studentInfoByStudentNo = studentService.getStudentInfoByStudentNo(applyInfo.getStudentNo());
        Map<String, String> map = new HashMap<String, String>();
        map.put("studentNo", applyInfo.getStudentNo());
        map.put("studentName", studentInfoByStudentNo.getStudentName());
        sendMessageReq.setTemplateParam(map);
        return userTaskRelationService.sendMessage(sendMessageReq);
    }
}
