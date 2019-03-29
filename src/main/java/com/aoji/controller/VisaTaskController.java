package com.aoji.controller;

import com.aoji.contants.TransferRelatedEnum;
import com.aoji.model.BaseResponse;
import com.aoji.model.SysUser;
import com.aoji.service.TransferService;
import com.aoji.service.VisaTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;

/**
 * author: chenhaibo
 * description: 文签定时任务调用
 * date: 2018/6/1
 */
@Controller
@RequestMapping(value = "/task")
public class VisaTaskController {

    private static final Logger logger = LoggerFactory.getLogger(VisaTaskController.class);

    @Autowired
    TransferService transferService;
    @Autowired
    VisaTaskService visaTaskService;

    @RequestMapping("/agreeTransfer")
    @ResponseBody
    public BaseResponse autoConfirmTransfer(Integer id, String oaid, String userName) {
        if (id == null || !StringUtils.hasText(oaid) || !StringUtils.hasText(userName)){
            logger.error(MessageFormat.format("autoConfirmTransfer failed!! id={0}, oaid={1}, userName={2}", id, oaid, userName));
            return null;
        }else{
            String comment = "自动接受";
            SysUser sysUser = new SysUser();
            sysUser.setOaid(oaid);
            sysUser.setUsername(userName);
            BaseResponse response = transferService.updateConfirmStatus(id, TransferRelatedEnum.TRAN_CONFIRM_STATUS_1.getCode(), comment, sysUser);
            logger.info("updateConfirmStatus id="+id+"; RQ:"+ response.toString());
            return response;
        }
    }

    /**
     * 重试 - 同步学生注册状态
     */
    @RequestMapping("/syncStudentRegisterStatus/retry")
    @ResponseBody
    public void syncStudentRegisterStatus() {
        logger.info("同步学生注册状态 重试开始！");
        visaTaskService.syncStudentRegisterStatusRetry();
    }

}
