package com.aoji.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * author: chenhaibo
 * description:
 * date: 2018/12/29
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class StudentServiceTest {

    @Autowired
    StudentService studentService;

    @Test
    public void getCallCenterUrlTest(){
        studentService.getCallCenterUrl("15201", "测试学生");
    }
}
