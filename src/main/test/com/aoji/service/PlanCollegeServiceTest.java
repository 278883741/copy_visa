package com.aoji.service;

import com.aoji.model.req.PlanCollegeInfoReq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * author: chenhaibo
 * description: 定校方案接口测试
 * date: 2018/11/8
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PlanCollegeServiceTest {

    @Autowired
    PlanCollegeInfoService planCollegeInfoService;


    @Test
    public void mergePlanCollegeTest(){
        PlanCollegeInfoReq planCollegeInfoReq = new PlanCollegeInfoReq();
        // 定校审批参数 START ===========
        planCollegeInfoReq.setCollegeId(21308);
        planCollegeInfoReq.setAuditStatus(1);
        planCollegeInfoReq.setOperatorNo("test");
        // 定校审批参数 END ===========
        planCollegeInfoService.mergePlanCollege(planCollegeInfoReq);
    }
}
