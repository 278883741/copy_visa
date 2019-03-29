package com.aoji.service.impl;

import com.aoji.contants.Contants;
import com.aoji.contants.RoleContants;
import com.aoji.mapper.*;
import com.aoji.model.*;
import com.aoji.model.dto.UserInfoDTO;
import com.aoji.service.ChannelStudentService;
import com.aoji.service.RoleService;
import com.aoji.service.UserService;
import com.aoji.vo.StudentInfoVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author yangsaixing
 * @description 系统用户服务实现类
 * @date Created in 上午9:28 2017/11/17
 */
@Service
public class UserServiceImpl implements UserService {

    public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    SysRolePermissionMapper sysRolePermissionMapper;

    @Autowired
    SysPermissionMapper sysPermissionMapper;

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    RoleService roleService;

    @Value("${ssoapi.grantAppLogin}")
    private String grantAppLoginUrl;

    @Autowired
    private UserGroupMapper userGroupMapper;


    @Autowired
    private UserGroupRelationMapper userGroupRelationMapper;

    @Autowired
    private ChannelStudentService channelStudentService;

    /**
     * 获取当前登录用户的权限
     *
     * @return
     */
    @Override
    public String getLoginUserByRoleName(StudentInfoVo studentInfo) {

        // 获取当前用户权限
        SysUser sysUser = this.getLoginUser();
        String roleName = "";
        if (sysUser.getOaid() != null) {
            studentInfo.setCopyOperatorNo(sysUser.getOaid());
            studentInfo.setCopyMakerNo(sysUser.getOaid());
            studentInfo.setCopyNo(sysUser.getOaid());
            studentInfo.setCopierNo(sysUser.getOaid());
            studentInfo.setVisaOperatorNo(sysUser.getOaid());
            studentInfo.setPlanningConsultantNo(sysUser.getOaid());
            //查询oaid对应的角色
            List<String> roles = sysRoleMapper.getRoleByOaId(sysUser.getOaid());
            if (roles != null && roles.size() == 1) {
                roleName = roles.get(0);
            } else if (roles.size() > 1) {
                if(roles.contains("渠道同业_文签顾问")){
                    roleName="渠道同业_文签顾问";
                }else if(roles.contains("渠道文案员_学生列表查看")){
                    roleName="渠道文案员_学生列表查看";
                }else if(roles.contains("渠道文案员")){
                    roleName="渠道文案员";
                }else if (roles.contains("总经理")) {
                    roleName = "总经理";
                } else if (roles.contains("文签总监")) {
                    roleName = "文签总监";
                } else if (roles.contains("文签副总监")) {
                    roleName = "文签副总监";
                } else if (roles.contains("文案经理")) {
                    roleName = "文案经理";
                } else if (roles.contains("外联经理")) {
                    roleName = "外联经理";
                } else if (roles.contains("外联顾问")) {
                    roleName = "外联顾问";
                } else if (roles.contains("文案顾问")) {
                    roleName = "文案顾问";
                }else if (roles.contains("海外办公室")) {
                    roleName = "海外办公室";
                }
            }
        }
        return roleName;
    }

    @Override
    public Boolean isChannelStaus() {
        SysUser sysUser = this.getLoginUser();
        List<String> nations = userGroupMapper.getNation(sysUser.getOaid());
        if(nations.contains("99")){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Boolean isPlannManager() {
        SysUser sysUser = this.getLoginUser();
        List<String> PlannManageList = userGroupRelationMapper.getPlanningConsultantsByOaid(sysUser.getOaid());
        if(PlannManageList != null && PlannManageList.size() >0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public SysUser getUserByName(String oaid) {
        SysUser user = new SysUser();
        user.setOaid(oaid);
        user.setDeleteStatus(false);
        user.setEnableStatus(1);
        List<SysUser> userList = sysUserMapper.select(user);
        if (userList.size() > 0) {
            return userList.get(0);
        }
        return new SysUser();
    }

    @Override
    public SysUser getUserInfo(SysUser sysUser) {
        List<SysUser> userList = sysUserMapper.select(sysUser);
        if (userList.size() > 0) {
            return userList.get(0);
        }
        return new SysUser();
    }

    @Override
    public boolean userEnable(String oaId){
        SysUser user = new SysUser();
        user.setOaid(oaId);
        user.setDeleteStatus(false);
        user.setEnableStatus(1);
        List<SysUser> userList = sysUserMapper.select(user);
        if (userList.size() > 0) {
           return true;
        }
        return false;
    }

    @Override
    public List<SysPermission> getFunctionByName(String username) {
        //1、根据用户名查询用户信息
        SysUser user = getUserByName(username);
        if (user == null || StringUtils.isEmpty(user.getId())) {
            throw new RuntimeException("用户信息异常");
        }
        //2、根据用户信息查询角色
        List<Integer> roleIds = getRoleIdsByUserId(user.getId());
        //3、根据角色查询权限
        return getFunctionByRoleIds(roleIds);
    }

    /**
     * 根据角色ids查询权限信息
     *
     * @param roleIds 角色ids
     * @return
     */
    private List<SysPermission> getFunctionByRoleIds(List<Integer> roleIds) {
        List<SysPermission> permissionParent = new ArrayList<>();
        if (roleIds.size() == 0) {
            return permissionParent;
        }
        List<Integer> permission = sysRolePermissionMapper.getPermissionIds(roleIds);
        if (permission.size() == 0) {
            permission.add(28);
        }
        Example sysPermissionExample = new Example(SysPermission.class);
        sysPermissionExample.createCriteria().andEqualTo("deleteStatus", 0).andIn("id", permission)
                .andEqualTo("level", 0);
        sysPermissionExample.orderBy("sort").desc();
        permissionParent = sysPermissionMapper.selectByExample(sysPermissionExample);
        for (SysPermission sysPermission : permissionParent) {
            Example example = new Example(SysPermission.class);
            example.createCriteria().andEqualTo("deleteStatus", 0).andEqualTo("parentId", sysPermission.getId())
                    .andEqualTo("level", 1)
                    .andIn("id", permission);
            example.orderBy("sort").desc();
            sysPermission.setChildList(sysPermissionMapper.selectByExample(example));
        }
        return permissionParent;
    }

    /**
     * 根据用户id查询角色信息
     *
     * @param userId 用户id
     * @return
     */
    private List<Integer> getRoleIdsByUserId(Long userId) {
        return sysUserRoleMapper.getRoleIdsByUserId(userId);
    }


    @Override
    public List<SysUser> getList(SysUser sysUser) {
        Example example = new Example(SysUser.class);
        if ("".equals(sysUser.getOaid()) || sysUser.getOaid() == null) {
            if ("".equals(sysUser.getUsername()) || sysUser.getUsername() == null) {
                if (sysUser.getEnableStatus() == null) {
                    example.createCriteria().andEqualTo("deleteStatus", false);
                } else {
                    example.createCriteria().andEqualTo("deleteStatus", false).andEqualTo("enableStatus", sysUser.getEnableStatus());
                }
            } else {
                if (sysUser.getEnableStatus() == null) {
                    example.createCriteria().andEqualTo("deleteStatus", false).andLike("username", "%" + sysUser.getUsername() + "%");
                } else {
                    example.createCriteria().andEqualTo("deleteStatus", false).andEqualTo("enableStatus", sysUser.getEnableStatus()).andLike("username", "%" + sysUser.getUsername() + "%");
                }
            }
        } else {
            if ("".equals(sysUser.getUsername()) || sysUser.getUsername() == null) {
                if (sysUser.getEnableStatus() == null) {
                    example.createCriteria().andEqualTo("deleteStatus", false).andEqualTo("oaid", sysUser.getOaid());
                } else {
                    example.createCriteria().andEqualTo("deleteStatus", false).andEqualTo("enableStatus", sysUser.getEnableStatus()).andEqualTo("oaid", sysUser.getOaid());
                }
            } else {
                if (sysUser.getEnableStatus() == null) {
                    example.createCriteria().andEqualTo("deleteStatus", false).andEqualTo("oaid", sysUser.getOaid()).andLike("username", "%" + sysUser.getUsername() + "%");
                } else {
                    example.createCriteria().andEqualTo("deleteStatus", false).andEqualTo("enableStatus", sysUser.getEnableStatus()).andEqualTo("oaid", sysUser.getOaid()).andLike("username", "%" + sysUser.getUsername() + "%");
                }
            }
        }
        List<SysUser> list = sysUserMapper.selectByExample(example);
        return list;
    }

    @Override
    public SysUser getById(Long id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer update(SysUser sysUser) {
        return sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }

    @Override
    public UserInfoDTO getUserInfoDTOByOaid(String oaid) {
        List<UserInfoDTO> userInfoDTOS = sysUserMapper.selectByOaid(oaid);
        if (userInfoDTOS.size() > 0) {
            return userInfoDTOS.get(0);
        }
        return null;
    }

    @Override
    public List<SysUser> getByCountryAndRole(Integer countryBussId, String role, Boolean leaderStatus) {
        return sysUserMapper.selectByCountryAndRole(countryBussId, role, leaderStatus);
    }

    @Override
    public Integer add(SysUser sysUser) {
        return sysUserMapper.insertSelective(sysUser);
    }

    @Override
    public List<SysUser> getByRoleName(String roleName) {
        return sysUserMapper.getByRoleName(roleName);
    }

    @Override
    public Boolean grantAppLogin(String oaid) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("worknum", oaid);
        params.add("appcode", "visaapp");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
        ResponseEntity<String> result = restTemplate.exchange(grantAppLoginUrl, HttpMethod.POST, requestEntity, String.class);
        logger.info("工号为" + oaid + "的添加工号赋权限结果为" + result.getBody());

        return true;
    }
    @Override
    public SysUser getLoginUser() {
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser == null) {
            return new SysUser();
        }
        return (SysUser) currentUser.getPrincipal();
    }

    @Override
    public List<SysUser> getByLeaderOaid(String oaid) {
        return sysUserMapper.getByLeaderOaid(oaid);
    }

    @Override
    public List<SysUser> getOtherLeaders(String oaid) {
        return sysUserMapper.getOtherLeaders(oaid);
    }


    @Override
    public List<SysUser> getByUserGroupAndCountry(Integer userGroup, Integer countryGroup, Boolean leaderStatus) {
//        leaderStatus = null;
        return sysUserMapper.getByUserGroupAndCountry(userGroup, countryGroup, leaderStatus);
    }

    @Override
    public List<String> getRolesByOaid(String oaid) {
        return sysRoleMapper.getRoleByOaId(oaid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean initUserForSSO(SysUser sysUser) {
        logger.info("SSO初始化用户! RQ:"+sysUser.toString());
        String memberId = sysUser.getOaid();
        if(!StringUtils.hasText(memberId)) {
            return false;
        }
        SysUser selectUser = new SysUser();
        selectUser.setOaid(memberId);
        selectUser.setDeleteStatus(false);
        List<SysUser> sysUsers = sysUserMapper.select(selectUser);
        if(sysUsers.isEmpty()){
            // 添加用户
            sysUser.setDeleteStatus(false);
            sysUser.setCreateTime(new Date());
            sysUser.setPassword(Contants.VISA_INIT_PASSWORD);
            sysUser.setEnableStatus(1);
            add(sysUser);
            logger.info("系统没有该用户，添加用户信息完毕，用户:{}",sysUser.toString());
            String roleName=RoleContants.COPY_OPERATOR;
            if(StringUtils.hasText(sysUser.getAgentId()) && !sysUser.getAgentId().equals("-1")) {
                roleName=RoleContants.AGENT_ORGANIZATION;
            }
            // 添加角色
            SysRole sysRole = new SysRole();
            sysRole.setRoleName(roleName);
             sysRole = roleService.get(sysRole);
            if(sysRole!=null && !StringUtils.isEmpty(sysRole.getId())){
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setOaId(sysUser.getOaid());
                sysUserRole.setUserId(sysUser.getId().intValue());
                sysUserRole.setRoleId(sysRole.getId());
                sysUserRole.setCreated(new Date());
                sysUserRole.setDeleteStatus(false);
                sysUserRoleMapper.insert(sysUserRole);
                logger.info("系统没有该用户，添加用户角色信息完毕，用户id:{}，角色名称：{}",memberId,roleName);
            }
        }else{
            logger.info("系统已有该用户，跳过添加，用户id：{}",memberId);
            //更新用户信息
            SysUser updateUser=sysUsers.get(0);
            sysUser.setPassword(updateUser.getPassword());
            sysUser.setCreateTime(updateUser.getCreateTime());
            sysUser.setDeleteStatus(false);
            sysUser.setEnableStatus(1);
            sysUser.setUpdateTime(new Date());
            updateUserInfoByMemberId(sysUser);
        }

        return true;
    }

    /**
     * 根据平台id更新用户信息
     * @param sysUser
     */
    private void updateUserInfoByMemberId(SysUser sysUser) {
        Example example=new Example(SysUser.class);
        example.createCriteria().andEqualTo("oaid",sysUser.getOaid()).andEqualTo("deleteStatus",false);
        sysUserMapper.updateByExample(sysUser,example);
    }
}
