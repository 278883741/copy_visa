package com.aoji.controller;

import com.aoji.contants.Contants;
import com.aoji.model.ApplyInfo;
import com.aoji.model.SysUser;
import com.aoji.model.req.SendMessageReq;
import com.aoji.service.SaleService;
import com.aoji.service.UserService;
import com.aoji.service.UserTaskRelationService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class SaleController  extends  BaseController{

    public static final Logger logger = LoggerFactory.getLogger(SaleController.class);

    @Autowired
    private SaleService saleService;

    @Autowired
    private UserService userService;



    /**
     * 是否设置为入读院校(根据applyId去修改Apply_Info表中的PlanCourseStatus字段)
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/sale/PlanCourseStatus", method = RequestMethod.POST)
    @ResponseBody
    public Boolean PlanCourseStatus(Integer id) {
        SysUser sysUser = userService.getLoginUser();
        ApplyInfo applyInfo = saleService.getById(id);
        applyInfo.setPlanCourseStatus(1);
        applyInfo.setUpdateTime(new Date());
        Integer update = saleService.update(applyInfo);
        if(update==1){
            saleService.sendMessageByOperatorNo(sysUser,applyInfo);
            return true;
        }
        return false;
    }
}
