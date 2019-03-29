package com.aoji.service.impl;

import com.aoji.contants.ApproveTypeEnum;
import com.aoji.contants.CaseIdEnum;
import com.aoji.contants.Contants;
import com.aoji.contants.ContentException;
import com.aoji.controller.UserController;
import com.aoji.mapper.AuditResultInfoMapper;
import com.aoji.mapper.StudentDelayInfoMapper;
import com.aoji.mapper.StudentSettleInfoMapper;
import com.aoji.model.*;
import com.aoji.service.*;
import com.aoji.vo.StudentInfoVo;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class StudentDelayServiceImpl implements StudentDelayService{

    private Logger logger = LoggerFactory.getLogger(StudentDelayServiceImpl.class);

    @Autowired
    StudentDelayInfoMapper studentDelayInfoMapper;
    @Autowired
    AuditResultService auditResultService;
    @Autowired
    AuditApplyService auditApplyService;
    @Autowired
    StudentService studentService;
    @Autowired
    UserService userService;
    @Autowired
    StudentDelayService studentDelayService;

    @Autowired
    private StudentSettleInfoMapper studentSettleInfoMapper;

    @Autowired
    private AuditResultInfoMapper auditResultInfoMapper;

    @Value("${upload.ks3.resDomain}")
    private String resDomain;

    @Override
    public boolean insert(StudentDelayInfo studentDelayInfo) {
        studentDelayInfo.setCreateTime(new Date());
        studentDelayInfo.setUpdateTime(new Date());
        studentDelayInfo.setDeleteStatus(false);
        int result = studentDelayInfoMapper.insert(studentDelayInfo);
        return result > 0;
    }

    @Override
    public boolean update(StudentDelayInfo studentDelayInfo) {
        int result = studentDelayInfoMapper.updateByPrimaryKeySelective(studentDelayInfo);
        return result > 0;
    }

    @Override
    public List<StudentDelayInfo> getList(String studentNo) {
        StudentDelayInfo studentDelayInfo = new StudentDelayInfo();
        studentDelayInfo.setDeleteStatus(false);
        studentDelayInfo.setStudentNo(studentNo);
        List<StudentDelayInfo> studentDelayInfos = studentDelayInfoMapper.select(studentDelayInfo);
        if(studentDelayInfos != null && studentDelayInfos.size() >0){
            for (StudentDelayInfo studentDelayInfo1:studentDelayInfos) {
                if(StringUtils.hasText(studentDelayInfo1.getAttachment()) && !studentDelayInfo1.getAttachment().contains(resDomain)){
                    studentDelayInfo1.setAttachment( resDomain +studentDelayInfo1.getAttachment());
                }
            }
        }
        return studentDelayInfos;
    }

    @Override
    public List<StudentDelayInfo> get(StudentDelayInfo studentDelayInfo) {
//        StudentDelayInfo studentDelayInfo = new StudentDelayInfo();
//        studentDelayInfo.setStudentNo(studentNo);
        List<StudentDelayInfo> studentDelayInfos = studentDelayInfoMapper.select(studentDelayInfo);
        for(StudentDelayInfo item:studentDelayInfos){
            if(!StringUtils.isEmpty(item.getOperatorNo())){
                SysUser user = userService.getUserByName(item.getOperatorNo());
                if(!StringUtils.isEmpty(user.getUsername())){
                    item.setOperatorName(user.getUsername());
                }
            }
            if(!StringUtils.isEmpty(item.getStudentNo())){
                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setStudentNo(item.getStudentNo());
                studentInfo = studentService.get(studentInfo);
                if(studentInfo !=null && !StringUtils.isEmpty(studentInfo.getStudentName())){
                    item.setStudentName(studentInfo.getStudentName());
                }
            }
        }
        return studentDelayInfos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String delayApprove(Integer studentId, Integer type, String updateTime, String reason, String studentNo) {
        List<StudentDelayInfo> studentDelayInfos = studentDelayService.getList(studentNo);
        AuditApplyInfo auditApplyInfo = auditApplyService.get(studentDelayInfos.get(studentDelayInfos.size()-1).getId(), CaseIdEnum.ApproveDelay.getCode());
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
        if(auditApplyInfo == null){
            throw new ContentException(1, "无审批申请");
        }
        if(!StringUtils.isEmpty(studentNo)) {
//            List<StudentDelayInfo> studentDelayInfos = getList(studentNo);
            if (!updateTime.equals(DateFormatUtils.format(studentDelayInfos.get(studentDelayInfos.size() - 1).getUpdateTime(), Contants.timePattern))) {
//            logger.error("COE信息被更新！！");

                jsonObject.put("code", 0);
            } else {
                SysUser sysUser = userService.getLoginUser();

                // 修改审批信息
                AuditResultInfo auditResultInfo = new AuditResultInfo();
                auditResultInfo.setApplyStatus(type);
                auditResultInfo.setReason(reason);
                auditApplyInfo.setLastAudit(auditApplyInfo.getLastAudit());
                auditResultInfo.setApplyId(auditApplyInfo.getId());
                auditResultInfo.setBusinessId(studentDelayInfos.get(studentDelayInfos.size()-1).getId());
                auditResultInfo.setCaseId(CaseIdEnum.ApproveDelay.getCode());
                auditResultInfo.setOperatorNo(sysUser.getOaid());
                auditResultInfo.setOperatorName(sysUser.getUsername());
                auditResultInfo.setCreateTime(new Date());
                auditResultInfo.setUpdateTime(new Date());
                auditResultInfo.setStudentNo(studentNo);
                Boolean isOk = auditResultService.add(auditResultInfo);
                if (!isOk) {
                    throw new ContentException(1, "审批失败");
                }

                //修改业务数据审批状态
                if (Integer.valueOf(ApproveTypeEnum.PASSED.getCode()).equals(type)) {
                    studentDelayInfos.get(studentDelayInfos.size() - 1).setAuditStatus(3);
                    Boolean result = studentDelayService.update(studentDelayInfos.get(studentDelayInfos.size() - 1));
                    if (result == false) {
                        throw new ContentException(1, "修改业务数据审批状态失败");
                    }
                }
                else{
                    studentDelayInfos.get(studentDelayInfos.size() - 1).setAuditStatus(4);
                    Boolean result = studentDelayService.update(studentDelayInfos.get(studentDelayInfos.size() - 1));
                    if (result == false) {
                        throw new ContentException(1, "修改业务数据审批状态失败");
                    }

                }

                // 修改学生状态
                if (Integer.valueOf(ApproveTypeEnum.PASSED.getCode()).equals(type)) {
                    StudentInfo studentInfo = new StudentInfo();
                    studentInfo.setId(studentId);
                    studentInfo.setStudentStatus(Contants.STUDENT_STATUS_DELAY);
                    int updateResult = studentService.update(studentInfo);
                    if (updateResult < 0) {
                        throw new ContentException(1, "修改学生状态失败");
                    }
                }
                jsonObject.put("code", 1);
            }
        }
        return  jsonObject.toString();
    }

    @Override
    public void testInsertSettle() {
//        List<StudentInfoVo> studentInfoVoList = studentDelayInfoMapper.getStudentInfoVo();
//        for (StudentInfoVo studentInfoVo:studentInfoVoList) {
//            StudentSettleInfo studentSettleInfo = new StudentSettleInfo();
//            studentSettleInfo.setDeleteStatus(false);
//            studentSettleInfo.setStudentName(studentInfoVo.getStudentName());
//            studentSettleInfo.setStudentNo(studentInfoVo.getStudentNo());
//            studentSettleInfo.setAuditStatus(3);
//            studentSettleInfo.setReason(4);
//            studentSettleInfo.setOperatorName("admin1");
//            studentSettleInfo.setOperatorNo("admin1");
//            studentSettleInfo.setCreateTime(studentInfoVo.getCreateTime());
//            studentSettleInfoMapper.insert(studentSettleInfo);
//        }
//        List<StudentSettleInfo> studentSettleInfoList = studentSettleInfoMapper.getStudentSettleByOperator();
//        for (StudentSettleInfo studentSettleInfo1:studentSettleInfoList) {
//            AuditResultInfo auditResultInfo = new AuditResultInfo();
//            auditResultInfo.setCaseId(11);
//            auditResultInfo.setBusinessId(studentSettleInfo1.getId());
//            auditResultInfo.setApplyStatus(2);
//            auditResultInfo.setCreateTime(new Date());
//            auditResultInfo.setDeleteStatus(false);
//            auditResultInfo.setOperatorNo("admin_0531");
//            auditResultInfo.setOperatorName("admin_0531");
//            auditResultInfo.setReason("同意");
//            auditResultInfo.setApplyId(-2);
//            auditResultInfoMapper.insert(auditResultInfo);
//        }
    }
}
