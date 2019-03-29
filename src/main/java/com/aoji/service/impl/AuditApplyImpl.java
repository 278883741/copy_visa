package com.aoji.service.impl;

import com.aoji.contants.CaseIdEnum;
import com.aoji.contants.ContentException;
import com.aoji.mapper.AuditApplyInfoMapper;
import com.aoji.mapper.AuditResultInfoMapper;
import com.aoji.model.*;
import com.aoji.model.req.SendMessageReq;
import com.aoji.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @author zhaojianfei
 * @description 待审批信息表
 * @date Created in 下午2:31 2017/12/7
 */
@Service
public class AuditApplyImpl implements AuditApplyService {
    @Autowired
    AuditApplyInfoMapper auditApplyInfoMapper;
    @Autowired
    AuditResultService auditResultService;
    @Autowired
    WorkflowService workflowService;
    @Autowired
    AuditApplyService auditApplyService;
    @Autowired
    UserTaskRelationService userTaskRelationService;
    @Autowired
    StudentService studentService;
    @Autowired
    UserGroupRelationService userGroupRelationService;
    @Autowired
    UserGroupService userGroupService;
    @Autowired
    PlanInfoService planInfoService;
    @Autowired
    PlanCollegeInfoService planCollegeInfoService;
    @Autowired
    CoeApplyService coeApplyService;
    @Autowired
    VisaResultService visaResultService;
    @Autowired
    VisaRecordService visaRecordService;
    @Autowired
    VisaApplyService visaApplyService;
    @Autowired
    FollowServiceInfoService followServiceInfoService;
    @Autowired
    FollowServiceResultService followServiceResultService;
    @Autowired
    UserService userService;
    @Autowired
    CountryService countryService;
    @Autowired
    AuditResultInfoMapper auditResultInfoMapper;
    @Autowired
    RoleService roleService;

    @Override
    public WorkflowNodeInfo getWorkflwInfo(Integer businessId, Integer caseId, Integer sequence, String studentNo) {
        // 查询学生留学国家所对应的国家组
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(studentNo);
        studentInfo.setDeleteStatus(false);
        studentInfo = studentService.get(studentInfo);
        if(studentInfo == null){
            throw new ContentException(3002,"学生不存在");
        }
        CountryInfo countryInfo = new CountryInfo();
        countryInfo.setId(studentInfo.getNationId());
        List<CountryInfo> list_country = countryService.getList(countryInfo);
        if(list_country.size() > 0){
            countryInfo = list_country.get(0);
        }
        Integer studentVisaGroup = countryInfo.getCountryGroup();
        if(studentVisaGroup.equals(105)){
            if(studentInfo.getUsaStatus() != null) {
                // 美高
                if (studentInfo.getUsaStatus().equals(1)) {
                    studentVisaGroup = 104;
                }
                // 美普
                if (studentInfo.getUsaStatus().equals(0)) {
                    studentVisaGroup = 105;
                }
            }
        }

        WorkflowInfo workflowInfo = new WorkflowInfo();
        workflowInfo.setNationId(studentVisaGroup);
        workflowInfo.setDeleteStatus(false);

        if (caseId.equals(CaseIdEnum.FinallyBonus.getCode())) {
            workflowInfo.setCaseId(CaseIdEnum.FirstBonus.getCode());
        } else if (caseId.equals(CaseIdEnum.ApproveDelay.getCode())) {
            // 1.获取规划经理
            SysRole sysRole = new SysRole();
            sysRole.setRoleName("规划经理");
            List<SysRole> listRole = roleService.queryRolesByRole(sysRole);
            SysUser sysUserPlan = new SysUser();
            if(listRole.size() >0){
                sysRole = listRole.get(0);
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setRoleId(sysRole.getId());
                sysUserRole = roleService.getSysUserRole(sysUserRole);
                sysUserPlan = userService.getUserByName(sysUserRole.getOaId());
            }
            // 规划顾问不为空，并且文签经理为空或者文签经理刚到规划经理这步
            if(!StringUtils.isEmpty(studentInfo.getPlanningConsultantNo()) && (StringUtils.isEmpty(studentInfo.getCopyOperatorNo()) || studentInfo.getCopyOperatorNo().equals(sysUserPlan.getOaid()))){
                SysUser sysUser = userService.getLoginUser();
                List<String> listRoles = roleService.getRolesByOaId(sysUser.getOaid());
                if(listRoles.contains("规划顾问")){
                    WorkflowNodeInfo workflowNodeInfo = new WorkflowNodeInfo();
                    workflowNodeInfo.setAuditUserNo(sysUserPlan.getOaid());
                    workflowNodeInfo.setAuditUserName(sysUserPlan.getUsername());
                    workflowNodeInfo.setLastStatus(1);
                    workflowNodeInfo.setAuditSequence(1);
                    return workflowNodeInfo;
                }
                else{
                    workflowInfo.setCaseId(CaseIdEnum.VisaRecord.getCode());
                }
            }

            else{
                workflowInfo.setCaseId(CaseIdEnum.VisaRecord.getCode());
            }
        } else {
            workflowInfo.setCaseId(caseId);
        }
        return workflowService.queryRoleByCode(workflowInfo, sequence, studentVisaGroup, studentNo);
    }

    @Override
    public Boolean add(Integer businessId, Integer caseId, Integer sequence, String studentNo,String operatorNo,String operatorName) {
        // 1.通过学生留学国家获取国家组,然后获取审批流
        SysUser sysUser = userService.getLoginUser();
        AuditApplyInfo auditApplyInfonew = new AuditApplyInfo();
        auditApplyInfonew = auditApplyInfoMapper.getAuditApplyInfo(String.valueOf(businessId),String.valueOf(caseId));

        WorkflowNodeInfo workflowNodeInfo = this.getWorkflwInfo(businessId, caseId, sequence, studentNo);
        // 2.通过国家及caseId获取审批节点人信息
        AuditApplyInfo auditApplyInfo = new AuditApplyInfo();
        auditApplyInfo.setCaseId(caseId);
        auditApplyInfo.setBusinessId(businessId);
        auditApplyInfo.setCreateTime(new Date());
        if(!StringUtils.isEmpty(operatorNo) && !StringUtils.isEmpty(operatorName)){
            auditApplyInfo.setOperatorNo(operatorNo);
            auditApplyInfo.setOperatorName(operatorName);
        }else if(auditApplyInfonew != null){
            auditApplyInfo.setOperatorNo(auditApplyInfonew.getOperatorNo());
            auditApplyInfo.setOperatorName(auditApplyInfonew.getOperatorName());
        }else if(sysUser!= null){
            auditApplyInfo.setOperatorNo(sysUser.getOaid());
            auditApplyInfo.setOperatorName(sysUser.getUsername());
        }
        auditApplyInfo.setOaid(workflowNodeInfo.getAuditUserNo());
        auditApplyInfo.setOaName(workflowNodeInfo.getAuditUserName());
        auditApplyInfo.setLastAudit(workflowNodeInfo.getLastStatus());
        auditApplyInfo.setAuditSequence(workflowNodeInfo.getAuditSequence());
        Integer result = auditApplyService.insert(auditApplyInfo);
        // 3.插入通知 -> 通知待审批人进行审批
        if (result > 0) {
            SendMessageReq sendMessageReq = new SendMessageReq();
            sendMessageReq.setOaid(workflowNodeInfo.getAuditUserNo());
            if(sysUser!= null){
                sendMessageReq.setOperatorNo(sysUser.getOaid());
            }else{
                sendMessageReq.setOperatorNo(auditApplyInfo.getOperatorNo());
            }

            sendMessageReq.setTemplateCode("toAudit");
            sendMessageReq.setStudentNo(studentNo);
            sendMessageReq.setTaskType(2);
            Map<String, String> map = new HashMap<String, String>();

            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setStudentNo(studentNo);
            studentInfo = studentService.get(studentInfo);
            switch (caseId) {
                case 1: {
                    map.put("caseName", "定校方案");
                };break;
                case 2: {
                    map.put("caseName", "首次寄出绩效");
                };break;
                case 3: {
                    map.put("caseName", "申请结果");
                };break;
                case 4: {
                    Integer nation = studentInfo.getNationId();
                    if(nation.equals(4) || nation.equals(40) || nation.equals(41)){
                        map.put("caseName", "院校申请-I-20");
                    }
                    else if(nation.equals(5)){
                        map.put("caseName", "院校申请-Full Offer");
                    }
                    else if(nation.equals(3)){
                        map.put("caseName", "院校申请-CAS");
                    }
                    else if(nation.equals(2)){
                        map.put("caseName", "院校申请-Receipt");
                    }
                    else {
                        map.put("caseName", "院校申请-COE");
                    }
                };break;
                case 5: {
                    map.put("caseName", "签证申请");
                };break;
                case 6: {
                    map.put("caseName", "签证结果");
                };break;
                case 7: {
                    map.put("caseName", "获签信息");
                };break;
                case 8: {
                    map.put("caseName", "后续申请结果");
                };break;
                case 9: {
                    map.put("caseName", "最终寄出绩效");
                };break;
                case 10: {
                    map.put("caseName", "申请停办");
                };break;
                case 11:{
                    map.put("caseName","学生结案申请");
                }
                default:
                    break;
            }
            map.put("studentNo", studentInfo.getStudentNo());
            map.put("studentName", studentInfo.getStudentName());
            sendMessageReq.setTemplateParam(map);
            userTaskRelationService.sendMessage(sendMessageReq);
            return true;
        }
        return false;
    }

    @Override
    public Integer insert(AuditApplyInfo auditApplyInfo) {
        return auditApplyInfoMapper.insertSelective(auditApplyInfo);
    }

    @Override
    public Integer delete(Integer businessId, Integer caseId) {
        AuditApplyInfo auditApplyInfo = get(businessId, caseId);
        auditApplyInfo.setDeleteStatus(true);
        auditApplyInfo.setUpdateTime(new Date());
        return update(auditApplyInfo);
    }

    @Override
    public Integer update(AuditApplyInfo auditApplyInfo) {
        return auditApplyInfoMapper.updateByPrimaryKey(auditApplyInfo);
    }

    @Override
    public AuditApplyInfo get(Integer businessId, Integer caseId) {
        if(businessId == null){
            return null;
        }
        Example example = new Example(AuditApplyInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("businessId", businessId).andEqualTo("caseId", caseId).andEqualTo("deleteStatus", false);
        List<AuditApplyInfo> list = auditApplyInfoMapper.selectByExample(example);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }

    }

    @Override
    public AuditApplyInfo get(AuditApplyInfo auditApplyInfo) {
        auditApplyInfo.setDeleteStatus(false);
        List<AuditApplyInfo> list = auditApplyInfoMapper.select(auditApplyInfo);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<AuditApplyInfo> getList(AuditApplyInfo auditApplyInfo) {
        auditApplyInfo.setDeleteStatus(false);
        List<AuditApplyInfo> list = auditApplyInfoMapper.select(auditApplyInfo);
        return list;
    }

    @Override
    public boolean currUserWithPermission(String roleName, SysUser sysUser){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName(roleName);
        sysRole = roleService.get(sysRole);
        if(sysRole != null){
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(sysRole.getId());
            sysUserRole.setOaId(sysUser.getOaid());
            sysUserRole = roleService.getSysUserRole(sysUserRole);
            if(sysUserRole != null){
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Map<String,Object>> queryAuditUser() {
        return auditApplyInfoMapper.queryAuditUser();
    }
}
