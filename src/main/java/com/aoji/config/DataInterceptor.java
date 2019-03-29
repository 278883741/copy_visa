package com.aoji.config;


import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author yangsaixing
 * @description
 * @date Created in 下午4:30 2018/1/29
 */
@Intercepts({@Signature(type= Executor.class,method = "prepare",args = {MappedStatement.class,Object.class})})
public class DataInterceptor  implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args=invocation.getArgs();
        if(args.length>1){

        }
        return null;
    }

    @Override
    public Object plugin(Object o) {
        return null;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
