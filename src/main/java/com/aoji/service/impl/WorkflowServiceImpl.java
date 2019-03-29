package com.aoji.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.CaseIdEnum;
import com.aoji.contants.ContentException;
import com.aoji.contants.CountryGroup;
import com.aoji.mapper.WorkflowInfoMapper;
import com.aoji.mapper.WorkflowNodeInfoMapper;
import com.aoji.mapper.WorkflowNodeInfoMapper1;
import com.aoji.model.*;
import com.aoji.model.res.Consultor;
import com.aoji.model.res.ConsultorRes;
import com.aoji.service.*;
import com.aoji.service.UserGroupRelationService;
import com.aoji.service.WorkflowService;
import com.aoji.utils.HttpUtils;
import com.aoji.vo.WorkFlowVo;
import com.aoji.vo.WorkflowNodeInfoVo;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.MessageFormat;
import java.util.*;

/**
 * @author yangsaixing
 * @description
 * @date Created in 上午10:45 2018/1/8
 */
@Service
public class WorkflowServiceImpl implements WorkflowService {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowServiceImpl.class);
    @Autowired
    WorkflowNodeInfoMapper workflowNodeInfoMapper;

    @Autowired
    WorkflowNodeInfoMapper1 workflowNodeInfoMapper1;

    @Autowired
    WorkflowInfoMapper workflowInfoMapper;

    @Autowired
    RoleService roleService;

    @Autowired
    UserGroupRelationService userGroupRelationService;

    @Autowired
    UserGroupService userGroupService;

    @Autowired
    StudentService studentService;

    @Autowired
    CountryService countryService;

    @Autowired
    UserService userService;

    @Value("${student.consultor.url}")
    private String studentConsultorUrl;

    @Override
    public WorkflowNodeInfo queryRoleByCode(WorkflowInfo workflowInfo, Integer sequence, Integer nationGroup, String studentNo) {
        workflowInfo = queryFlowInfoByParam(workflowInfo);
        if (workflowInfo == null || StringUtils.isEmpty(workflowInfo.getId())) {
            throw new ContentException(3002, "流程信息不存在");
        }
        WorkflowNodeInfo workflowNodeInfo = new WorkflowNodeInfo();
        Example example = new Example(WorkflowNodeInfo.class);
        example.createCriteria().andEqualTo("flowId", workflowInfo.getId()).andEqualTo("auditSequence", sequence).andEqualTo("deleteStatus", false);
        List<WorkflowNodeInfo> workflowNodeInfoList = workflowNodeInfoMapper.selectByExample(example);
        workflowNodeInfo = workflowNodeInfoList.get(0);

        //获取具体审批用户
        Integer roleId = workflowNodeInfo.getRole();
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(studentNo);
        studentInfo.setDeleteStatus(false);
        studentInfo = studentService.get(studentInfo);
        boolean aoJiTongYe = true;
        if (studentInfo.getChannelStatus() != null && !StringUtils.isEmpty(studentInfo.getChannelStatus())) {
            aoJiTongYe = studentInfo.getChannelStatus().equals(1) ? true : false;
        }
        if (roleId.equals(-999)) {
            if(userService.userEnable(studentInfo.getCopyOperatorNo())) {
                workflowNodeInfo.setAuditUserNo(studentInfo.getCopyOperatorNo());
                SysUser user = userService.getUserByName(studentInfo.getCopyOperatorNo());
                workflowNodeInfo.setAuditUserName(user.getUsername());
            }
        } else if (roleId.equals(-1000)) {
            if(studentNo.startsWith("TY")){
                if(!StringUtils.isEmpty(studentInfo.getSalesConsultantNo())) {
                    workflowNodeInfo.setAuditUserNo(studentInfo.getSalesConsultantNo());
                    workflowNodeInfo.setAuditUserName(studentInfo.getSalesConsultant());
                }
            }else{
                String url = MessageFormat.format(studentConsultorUrl, studentNo);
                logger.info("调用获取学生销售顾问接口url: " + url);
                String json = HttpUtils.doGet(url);
                logger.info("调用获取学生销售顾问接口返回数据: " + json);
                ConsultorRes consultorRes = JSONObject.parseObject(json, ConsultorRes.class);
                if (consultorRes != null && consultorRes.getState() == 0) {
                    Consultor consultor = consultorRes.getData();
                    if(!consultor.getWorknum().equals("0")){
                        workflowNodeInfo.setAuditUserNo(consultor.getWorknum());
                        workflowNodeInfo.setAuditUserName(consultor.getConsultorname());
                    }else{
                        workflowNodeInfo.setAuditUserNo(studentInfo.getSalesConsultantNo());
                        workflowNodeInfo.setAuditUserName(studentInfo.getSalesConsultant());
                    }
                }else{
                    if(!StringUtils.isEmpty(studentInfo.getSalesConsultantNo())){
                        workflowNodeInfo.setAuditUserNo(studentInfo.getSalesConsultantNo());
                        workflowNodeInfo.setAuditUserName(studentInfo.getSalesConsultant());
                    }
                }
            }


        } else if (roleId.equals(20)) {
            groupLeaderAudit(workflowNodeInfo, studentInfo, nationGroup);
        } else if (roleId.equals(27)) {
            List<SysUserRole> users = roleService.getUserByRoleId(roleId, studentNo);
            if (users.size() == 1) {
                if(userService.userEnable(users.get(0).getOaId())) {
                    workflowNodeInfo.setAuditUserNo(users.get(0).getOaId());
                    SysUser user = userService.getUserByName(users.get(0).getOaId());
                    workflowNodeInfo.setAuditUserName(user.getUsername());
                }
            }
        } else if (roleId.equals(23)) {
            if (workflowInfo.getCaseId().equals(CaseIdEnum.CoeApply.getCode()) && nationGroup.equals(CountryGroup.GROUP_CANADA.getCode())) {
                List<SysUserRole> users = roleService.getUserByRoleId(roleId, studentNo);
                if (users.size() == 1) {
                    if(userService.userEnable(users.get(0).getOaId())) {
                        workflowNodeInfo.setAuditUserNo(users.get(0).getOaId());
                        SysUser user = userService.getUserByName(users.get(0).getOaId());
                        workflowNodeInfo.setAuditUserName(user.getUsername());
                    }
                }
            } else {
                if (aoJiTongYe) {
                    SysRole sysRole = new SysRole();
                    sysRole.setRoleName("同业审批coe");
                    sysRole.setDeleteStatus(false);
                    sysRole = roleService.get(sysRole);
                    List<SysUserRole> users = roleService.getUserByRoleId(sysRole.getId(), studentNo);
                    if (users.size() > 0) {
                        if(userService.userEnable(users.get(0).getOaId())) {
                            workflowNodeInfo.setAuditUserNo(users.get(0).getOaId());
                            SysUser user = userService.getUserByName(users.get(0).getOaId());
                            workflowNodeInfo.setAuditUserName(user.getUsername());
                        }
                    }
                } else {
                    if (studentInfo.getNationId().equals(1) || studentInfo.getNationId().equals(2)) {
                        if(userService.userEnable(studentInfo.getCopyOperatorNo())) {
                            workflowNodeInfo.setAuditUserNo(studentInfo.getCopyOperatorNo());
                            SysUser user = userService.getUserByName(studentInfo.getCopyOperatorNo());
                            workflowNodeInfo.setAuditUserName(user.getUsername());
                        }
                    } else {
                        groupLeaderAudit(workflowNodeInfo, studentInfo, nationGroup);
                    }
                }
            }
        }
        return workflowNodeInfo;
    }

    public void groupLeaderAudit(WorkflowNodeInfo workflowNodeInfo, StudentInfo studentInfo, Integer nationGroup) {
        // 1.获取学生文签顾问所在的组
        UserGroupRelation userGroupRelation = new UserGroupRelation();
        userGroupRelation.setOaid(studentInfo.getCopyOperatorNo());
        userGroupRelation.setDeleteStatus(false);
        List<UserGroupRelation> listUserGroupRelation = userGroupRelationService.getList(userGroupRelation);
        // 2.获取上面这些组的组长
        List<UserGroupRelation> list = new ArrayList<UserGroupRelation>();
        for (UserGroupRelation item : listUserGroupRelation) {
            Integer groupId = item.getGroupId();
            userGroupRelation = new UserGroupRelation();
            userGroupRelation.setGroupId(groupId);
            userGroupRelation.setLeaderStatus(true);
            userGroupRelation.setDeleteStatus(false);
            list.add(userGroupRelationService.get(userGroupRelation));
        }
        // 3.获取上边组长结果中国家组和学生留学国家所在国家组相同的数据
        if(list.size() == 1){
            if(userService.userEnable(list.get(0).getOaid())) {
                workflowNodeInfo.setAuditUserNo(list.get(0).getOaid());
                SysUser user = userService.getUserByName(list.get(0).getOaid());
                workflowNodeInfo.setAuditUserName(user.getUsername());
            }
        }else{
            for (UserGroupRelation item : list) {
                // 4 查询各组长所属国家组
                UserGroup userGroup = new UserGroup();
                userGroup.setId(item.getGroupId());
                userGroup.setDeleteStatus(false);
                List<UserGroup> listUserGroup = userGroupService.getUserGroup(userGroup);
                if (listUserGroup.size() > 0) {
                    if (listUserGroup.get(0).getNation().equals(nationGroup)) {
                        if(userService.userEnable(item.getOaid())) {
                            workflowNodeInfo.setAuditUserNo(item.getOaid());
                            SysUser user = userService.getUserByName(item.getOaid());
                            workflowNodeInfo.setAuditUserName(user.getUsername());
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public WorkflowInfo queryWorkflowByCode(String flowCode) {
        Example example = new Example(WorkflowInfo.class);
        example.createCriteria().andEqualTo("flowCode", flowCode).andEqualTo("deleteStatus", false);
        List<WorkflowInfo> workflowInfos = workflowInfoMapper.selectByExample(example);
        if (workflowInfos.size() <= 0) {
            return new WorkflowInfo();
        } else {
            return workflowInfos.get(0);
        }
    }

    private WorkflowInfo queryFlowInfoByParam(WorkflowInfo workflowInfo) {
        List<WorkflowInfo> workflowInfos = workflowInfoMapper.select(workflowInfo);
        if (workflowInfos.size() <= 0) {
            return null;
        } else {
            return workflowInfos.get(0);
        }
    }


    // =================================================================================================================

    /**
     * 添加审批流程
     *
     * @param workflowInfo
     * @return
     */
    public int add(WorkflowInfo workflowInfo) {
        // 根据用户组，和场景id 去查询数据库中是否存在
        workflowInfo.setDeleteStatus(false);
        List<WorkflowInfo> wlist = this.workflowInfoMapper.select(workflowInfo);

        if (wlist.size() != 0) {
            return 0;
        } else {
            //添加操作者
            SysUser systemUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
            workflowInfo.setDeleteStatus(false);
            workflowInfo.setOperatorName(systemUser.getUsername());
            workflowInfo.setOperatorNo(systemUser.getOaid());
            workflowInfo.setUpdateTime(new Date());
            workflowInfo.setCreateTime(new Date());
            return this.workflowInfoMapper.insert(workflowInfo);
        }
    }

    /**
     * datateablsee 列表
     *
     * @param workFlowVo
     * @return
     */
    @Override
    public List<WorkFlowVo> getWorkFlowList(WorkFlowVo workFlowVo) {

        return this.workflowInfoMapper.getWorkFlowList(workFlowVo);
    }

    /**
     * 添加审批流程前的校验
     *
     * @param userGroup
     * @param caseId
     * @return
     */
    public Map<String, Object> addCheck(WorkflowInfo workflowInfo) {

        Map<String, Object> map = new HashMap<>();

        if (workflowInfo.getCaseId() == null || workflowInfo.getUserGroup() == null) {
            map.put("state", false);
            map.put("msg", "未选择用户组或场景");
        } else {
            // 根据用户组，和场景id 去查询数据库中是否存在
            workflowInfo.setDeleteStatus(false);
            List<WorkflowInfo> wlist = this.workflowInfoMapper.select(workflowInfo);

            if (wlist.size() == 0) {
                // 查询当前用户组下的成员
//                List<Map<String, Object>> ulist = this.userGroupService.getUserByUserGroupId(workflowInfo.getUserGroup());
//                map.put("ulist", ulist);
                map.put("state", true);
            } else {
                map.put("state", false);
                map.put("msg", "此用户组的该场景已存在！");
            }
        }

        return map;
    }

    /**
     * 根据流程id获取当前流程
     *
     * @param id
     * @return
     */
    public WorkflowInfo selectWorkFlowById(Integer id) {
        return this.workflowInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取已经为当前分组当前场景下的审批人
     *
     * @param id
     * @return
     */
    public List<WorkflowNodeInfo1> getWorkFlowPeople(Integer id) {

        //根据id 获取当前分组当前场景下已经选择的审批人
        List<WorkflowNodeInfo1> wList = this.workflowNodeInfoMapper1.select(new WorkflowNodeInfo1(id, false));

        return wList;
    }

    /**
     * 获取操作人列表
     *
     * @param workflowNodeInfo
     * @return
     */
    public List<Map<String, Object>> getWorkflowNodeInfoList(WorkflowNodeInfoVo workflowNodeInfo) {

        return this.workflowNodeInfoMapper1.getWorkflowNodeInfoList(workflowNodeInfo);
    }

    /**
     * 删除审批人
     *
     * @param id
     * @return
     */
    public int deleteWorkflowNodeInfo(Integer id) {

        return this.workflowNodeInfoMapper1.deleteByPrimaryKey(id);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    public int deleteWorkflowInfo(Integer id) {

        this.workflowNodeInfoMapper1.delete(new WorkflowNodeInfo1(id));

        return this.workflowInfoMapper.deleteByPrimaryKey(id);
    }

    /**
     * 添加具体审批人
     *
     * @param workflowNodeInfo1
     * @return
     */
    public int addWorkFlowNodeInfo(WorkflowNodeInfo1 workflowNodeInfo1) {
        //添加操作者
        SysUser systemUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        workflowNodeInfo1.setDeleteStatus(false);
        workflowNodeInfo1.setOperatorName(systemUser.getUsername());
        workflowNodeInfo1.setOperatorNo(systemUser.getOaid());
        workflowNodeInfo1.setUpdateTime(new Date());
        workflowNodeInfo1.setCreateTime(new Date());
        return this.workflowNodeInfoMapper1.insert(workflowNodeInfo1);

    }
}
