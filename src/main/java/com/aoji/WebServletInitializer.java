package com.aoji;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @author yangsaixing
 * @description
 * @date Created in 下午4:25 2017/11/17
 */
public class WebServletInitializer extends SpringBootServletInitializer{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return  builder.sources(VisaApplication.class);
    }
}
