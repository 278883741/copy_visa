package com.aoji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


/**
 * @author yangsaixing
 * @description  项目启动类
 * @date Created in 上午10:47 2017/11/10
 */
@SpringBootApplication
@EnableAsync
public class VisaApplication {
    public static void main(String[] args) {
        SpringApplication.run(VisaApplication.class);
    }
}


