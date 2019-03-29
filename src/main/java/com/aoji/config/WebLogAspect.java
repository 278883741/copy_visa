package com.aoji.config;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @Description:
 * @Auther: yj
 * @Date: 2018/7/23 16:11
 */

@Aspect
@Component
public class WebLogAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution( * com.aoji.controller..*.*(..))")
    public void webLog() {

    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.info("请求路径 URL : " + request.getRequestURL().toString());
        logger.info("请求方式 : " + request.getMethod());
        Enumeration<String> enu = request.getParameterNames();
        StringBuffer out=new StringBuffer("请求参数：");
        while (enu.hasMoreElements()) {
            String name = (String) enu.nextElement();
            String value=(String) request.getParameter(name);
            out.append(name);
            out.append(":");
            out.append(value);
            out.append(",");
        }
        logger.info(out.toString());
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        String responseJson="";
        if(ret!=null) {
            responseJson = JSONObject.toJSONString(ret);
        }
        logger.info("响应体 : " + responseJson);
    }

}
