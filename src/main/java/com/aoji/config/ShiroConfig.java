package com.aoji.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author yangsaixing
 * @description
 * @date Created in 下午4:59 2017/11/16
 */
@Configuration
public class ShiroConfig {

    @Bean
    public MyShiroRealm myShiroRealm(){
        MyShiroRealm myShiroRealm=new MyShiroRealm();
        //不配置权限缓存
        myShiroRealm.setAuthorizationCachingEnabled(false);
        return myShiroRealm;
    }

    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String,String> filterChainDefinitionMap=new LinkedHashMap<>();
        //anon--匿名访问 (访问静态文件)
        filterChainDefinitionMap.put("/static/**","anon");
        filterChainDefinitionMap.put("/assets/**","anon");
        filterChainDefinitionMap.put("/layer/**","anon");
        filterChainDefinitionMap.put("/business/**","anon");
        filterChainDefinitionMap.put("/bootstrap/**","anon");
        filterChainDefinitionMap.put("/css/**","anon");
        filterChainDefinitionMap.put("/js/**","anon");
        filterChainDefinitionMap.put("/imgs/**","anon");
        filterChainDefinitionMap.put("/im/**","anon");

        //anon--匿名访问(接口)
        // IM主页
        filterChainDefinitionMap.put("/webim/**","anon");
        //TODO: 导出测试接口，测试完删除
        filterChainDefinitionMap.put("/export/util","anon");
        //退出登录
        filterChainDefinitionMap.put("/checkLogin","anon");
        //登录
        filterChainDefinitionMap.put("/login","anon");
        //钉钉消息查看
        filterChainDefinitionMap.put("/applyResult/detailPage","anon");
        //钉钉消息审批
        filterChainDefinitionMap.put("/applyResult/approve","anon");
        //签约及转案
        filterChainDefinitionMap.put("/doTransfer", "anon");
        //修改咨询顾问(将学生的咨询顾问同步到文签系统)
        filterChainDefinitionMap.put("/modifyConsultant", "anon");
        //业务系统添加结案(咨询顾问提交的异常结案)
        filterChainDefinitionMap.put("/addStudentSettle","anon");
        //业务系统修改结案(咨询顾问提交的异常结案)
        filterChainDefinitionMap.put("/updateStudentSettle","anon");
        //定时任务调用文签项目
        filterChainDefinitionMap.put("/task/**", "anon");
        //跳转到咨询顾问查看的学生信息页面(学生详细信息展示)
        filterChainDefinitionMap.put("/consultant/student","anon");
        //分页查询院校申请结果列表（销售顾问）
        filterChainDefinitionMap.put("/consultant/student/query","anon");
        //跳转到申请详情页（销售顾问）
        filterChainDefinitionMap.put("/apply/detailPage/sale","anon");
        //分页查询回访信息列表（销售顾问）
        filterChainDefinitionMap.put("/sale/list/query","anon");
        //签约系统查看学生列表
        filterChainDefinitionMap.put("/sign/student/list","anon");
        //签约系统操作定校方案
        filterChainDefinitionMap.put("/sign/planCollege/merge","anon");
        //签约系统查询定校方案
        filterChainDefinitionMap.put("/sign/planCollege/query","anon");
        //签约系统管理层查询定校方案
        filterChainDefinitionMap.put("/sign/planCollege/queryForManager","anon");
        //签约系统修改学生信息
        filterChainDefinitionMap.put("/sign/update/student","anon");
        //签约系统修改学生信息
        filterChainDefinitionMap.put("/sign/planCollege/auditRecord","anon");
        //退费自动结案
        filterChainDefinitionMap.put("/sign/refund/endCase","anon");
        //根据学号查询学生的学费总额和院校
        filterChainDefinitionMap.put("/cost/Info","anon");
        //根据工号查询是否有未结案学生
        filterChainDefinitionMap.put("/student/isSettleStudent","anon");
        //通过学生Id查询coe申请附件
        filterChainDefinitionMap.put("/outer/getCoeListByStudentNo","anon");
        //根据顾问oaid查询学生列表
        filterChainDefinitionMap.put("/getStudentInfoByOaid","anon");
        //根据学生的学号查询学生的信息
        filterChainDefinitionMap.put("/getStudentInfoByStudentNo","anon");
        //M1审核定校方案的时候修改学生确认状态
        filterChainDefinitionMap.put("/updatePlanCollegeStudentConfirmStatus","anon");
        //查看学生是否已经结案
        filterChainDefinitionMap.put("/student/isSettle","anon");
        //分页查询申请结果列表
        filterChainDefinitionMap.put("/applyResult/list/query","anon");
        //查询回访数据
        filterChainDefinitionMap.put("/getVisitInfo","anon");
        //查询该学生的文签联系人
        filterChainDefinitionMap.put("/getCopyOperator1","anon");
        //获取学生信息
        filterChainDefinitionMap.put("/getStudentInfo","anon");
        //获取学生结案信息
        filterChainDefinitionMap.put("/getStudentSettleInfo","anon");
        //获取待审批信息
        filterChainDefinitionMap.put("/getAuditApplyInfo","anon");
        //获取审批结果信息
        filterChainDefinitionMap.put("/getAuditResultInfo","anon");

        // 获取用户上传的文件及上传状态
        // filterChainDefinitionMap.put("/channelStudent/getPapersType","anon");
        // 根据代理编号查询获签学生数
        filterChainDefinitionMap.put("/getCountByAgentId","anon");

        // 根据代理编号查询获签学生数
        filterChainDefinitionMap.put("/getCountByAgentId","anon");
        //Swagger排除url
        filterChainDefinitionMap.put("/swagger-ui.html","anon");
        filterChainDefinitionMap.put("/swagger-resources/**","anon");
        filterChainDefinitionMap.put("/v2/api-docs","anon");
        filterChainDefinitionMap.put("/webjars/**","anon");
        filterChainDefinitionMap.put("/upload/url","anon");
        //authc--认证后可访问
        filterChainDefinitionMap.put("/**","authc");



        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/main");


        //未授权页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * thymeleaf里使用shiro的标签的bean
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}
