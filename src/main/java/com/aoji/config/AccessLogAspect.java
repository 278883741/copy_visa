package com.aoji.config;

import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.AccessTypeEnum;
import com.aoji.model.SysAccessLog;
import com.aoji.model.SysUser;
import com.aoji.service.SysAccessLogService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * author: chenhaibo
 * description: 系统访问日志
 * date: 2018/11/26
 */
@Aspect
@Component
public class AccessLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(AccessLogAspect.class);

    @Autowired
    SysAccessLogService sysAccessLogService;

    /**
     * 后台登录
     */
    @Pointcut("execution(* com.aoji.controller.IndexController.checkLogin(..))")
    public void loginLog() {}

    /**
     * 统一登录
     */
    @Pointcut("execution(* com.aoji.controller.IndexController.login(..))")
    public void loginLogForSSO() {}

    /**
     * 登出
     */
    @Pointcut("execution(* com.aoji.controller.IndexController.logout(..))")
    public void logoutLog() {}

    /**
     * 修改学生信息
     */
    @Pointcut("execution(* com.aoji.controller.StudentController.editStudentInfo(..))")
    public void updateStudent() {}

    @Before("logoutLog()")
    public void beforeMethodForLogOut(JoinPoint joinPoint){
        save(AccessTypeEnum.LOGOUT.toString(), null);
    }

    @After("loginLog() || loginLogForSSO()")
    public void afterMethodForLogin(JoinPoint joinPoint){
        save(AccessTypeEnum.LOGIN.toString(), null);
    }

    @Before("updateStudent()")
    public void beforeMethodForUpdateStudent(JoinPoint joinPoint){
        save(AccessTypeEnum.UPDATE_STUDENT_INFO_RQ.toString(), null);
    }

//    @AfterReturning(returning = "ret", pointcut = "updateStudent()")
//    public void doAfterReturningForUpdateStudent(Object ret) throws Throwable {
//        save(AccessTypeEnum.UPDATE_STUDENT_INFO_RS.toString(), JSONObject.toJSONString(ret));
//    }

    /**
     * 获取登录用户远程主机ip地址
     * @param request
     * @return
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public void save(String type, String response){
        try {
            // 接收到请求，记录请求内容
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            SysUser sysUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
            Map<String, String[]> params = request.getParameterMap();

            if(sysUser != null && StringUtils.isNotBlank(sysUser.getOaid())){
                SysAccessLog sysAccessLog = new SysAccessLog();
                sysAccessLog.setMemberId(sysUser.getOaid());
                sysAccessLog.setUserName(sysUser.getUsername());
                sysAccessLog.setIpAddress(getIpAddr(request));
                sysAccessLog.setRequestUrl(request.getRequestURL().toString());
                sysAccessLog.setRequestParam(JSONObject.toJSONString(params));
                sysAccessLog.setResponse(response);
                sysAccessLog.setAccessType(type);
                sysAccessLogService.insert(sysAccessLog);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
