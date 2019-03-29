package com.aoji.service.impl;

import com.aoji.contants.CaseIdEnum;
import com.aoji.contants.Contants;
import com.aoji.contants.CountryEnum;
import com.aoji.contants.StudentStatus;
import com.aoji.mapper.StudentSettleRecordMapper;
import com.aoji.mapper.StudentStatusRecordMapper;
import com.aoji.model.*;
import com.aoji.model.req.SendMessageReq;
import com.aoji.service.AuditApplyService;
import com.aoji.service.StudentService;
import com.aoji.service.StudentSettleService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aoji.mapper.StudentSettleInfoMapper;
import com.aoji.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class StudentSettleServiceImpl implements StudentSettleService {
    @Autowired
    StudentSettleInfoMapper studentSettleInfoMapper;
    @Autowired
    StudentService studentService;
    @Autowired
    AuditApplyService auditApplyService;

    @Autowired
    UserService userService;

    @Autowired
    StudentStatusRecordMapper studentStatusRecordMapper;
    @Autowired
    StudentSettleRecordMapper studentSettleRecordMapper;
    @Override
    public List<StudentSettleInfo> getListByStudentNo(String studentNo){
        StudentSettleInfo studentSettleInfo = new StudentSettleInfo();
        studentSettleInfo.setStudentNo(studentNo);
        studentSettleInfo.setDeleteStatus(false);
        List<StudentSettleInfo> list = studentSettleInfoMapper.select(studentSettleInfo);
        return list;
    }
    @Override
    public StudentSettleInfo get(StudentSettleInfo studentSettleInfo) {
        studentSettleInfo.setDeleteStatus(false);
        List<StudentSettleInfo> list = studentSettleInfoMapper.select(studentSettleInfo);
        if(list.size() > 0 ){
            studentSettleInfo = list.get(0);
        }
        return studentSettleInfo;
    }
    @Override
    public boolean add(StudentSettleInfo studentSettleInfo,String operatorNo,String operatorName) {
        if(studentSettleInfoMapper.insertSelective(studentSettleInfo) > 0) {
            return auditApplyService.add(studentSettleInfo.getId(), CaseIdEnum.StudentSettle.getCode(), 1, studentSettleInfo.getStudentNo(),operatorNo,operatorName);
        }
        return false;
    }
    @Override
    public Integer update(StudentSettleInfo studentSettleInfo){
        return studentSettleInfoMapper.updateByPrimaryKeySelective(studentSettleInfo);
    }

    @Override
    public List<StudentSettleInfo> getStudentSettle(StudentSettleInfo studentSettleInfo) {
        studentSettleInfo.setDeleteStatus(false);
        List<StudentSettleInfo> list = studentSettleInfoMapper.select(studentSettleInfo);
        for(StudentSettleInfo item:list){
            if(!StringUtils.isEmpty(item.getStudentNo())){
                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setStudentNo(item.getStudentNo());
                studentInfo = studentService.get(studentInfo);
                if(studentInfo !=null && !StringUtils.isEmpty(studentInfo.getStudentName())){
                    item.setStudentName(studentInfo.getStudentName());
                }
            }
        }
        return list;
    }

    @Override
    @Transactional
    public Boolean operatorCancelSettle(String studentNo){
        SysUser sysUser = userService.getLoginUser();
        Integer i = 0;
        Integer k = 0;
        Integer n = 0;

        //更改学生状态
        StudentInfo studentInfo = studentService.getStudentInfoByStudentNo(studentNo);
        if(studentInfo != null) {
            studentInfo.setStatus(StudentStatus.INCOMPLETE.getCode());
            i = studentService.update(studentInfo);
        }
        //状态变更记录表
        StudentStatusRecord studentStatusRecord = new StudentStatusRecord();
        studentStatusRecord.setStatusCode(StudentStatus.INCOMPLETE.getCode());
        studentStatusRecord.setDeleteStatus(false);
        studentStatusRecord.setStudentNo(studentNo);
        studentStatusRecord.setCreateTime(new Date());
        studentStatusRecord.setUpdateTime(new Date());
        studentStatusRecord.setOperatorNo(sysUser.getOaid());
        Integer j = studentStatusRecordMapper.insert(studentStatusRecord);

        //遍历逻辑删除结案
        List<StudentSettleInfo> studentSettleInfos = getListByStudentNo(studentNo);
        if(studentSettleInfos.size() > 0) {
            for (StudentSettleInfo item : studentSettleInfos) {
                item.setDeleteStatus(true);
                k = studentSettleInfoMapper.updateByPrimaryKeySelective(item);

                //结案记录表
                StudentSettleRecord studentSettleRecord = new StudentSettleRecord();
                studentSettleRecord.setCreateTime(new Date());
                studentSettleRecord.setDeteleStatus(false);
                studentSettleRecord.setOperatorNo(sysUser.getOaid());
                studentSettleRecord.setOperatorName(sysUser.getUsername());
                studentSettleRecord.setSettleInfoId(item.getId());
                studentSettleRecord.setStudentNo(studentNo);
                n = studentSettleRecordMapper.insert(studentSettleRecord);
            }
        }
        if(i > 0 && j > 0 && k > 0 && n > 0){
            return true;
        }
        else{
            throw new RuntimeException("运营作废结案时出现了异常");
        }
    }

    @Override
    public List<StudentSettleInfo> checkAllSettleList(StudentSettleInfo studentSettleInfo,Integer nationId,String studentName){
        return studentSettleInfoMapper.checkAllSettleList(studentSettleInfo,nationId,studentName);
    }

}
