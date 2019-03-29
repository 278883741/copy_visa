package com.aoji.service;

import com.alibaba.fastjson.JSONObject;
import com.aoji.model.req.UpdateStudentInfoReq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * author: chenhaibo
 * description: RabbitMQService测试用例
 * date: 2018/10/9
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class RabbitMQServiceTest {

    @Autowired
    RabbitMQService rabbitMQService;

    @Test
    public void sendMessageTest(){

        UpdateStudentInfoReq updateStudentInfoReq = new UpdateStudentInfoReq();
        updateStudentInfoReq.setStudentNo("TY00125");
        updateStudentInfoReq.setStudentName("MQ测试");
        byte[] bytes = JSONObject.toJSONString(updateStudentInfoReq).getBytes();
        rabbitMQService.sendMessage("1", "transferCase", bytes);

    }
}
