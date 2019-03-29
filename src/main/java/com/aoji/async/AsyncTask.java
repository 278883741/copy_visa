package com.aoji.async;

import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.Contants;
import com.aoji.mapper.TaskTemplateInfoMapper;
import com.aoji.model.ChannelStudentInfo;
import com.aoji.model.FailureJob;
import com.aoji.model.MailEntity;
import com.aoji.model.TaskTemplateInfo;
import com.aoji.service.FailureJobService;
import com.aoji.utils.HttpUtils;
import com.aoji.utils.MailUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 赵剑飞
 * @description 异步任务
 * @date Created in 下午2:25 2018/12/7
 */
@Component
public class AsyncTask {
    private static final Logger logger = LoggerFactory.getLogger(AsyncTask.class);
    @Autowired
    FailureJobService failureJobService;
    @Autowired
    TaskTemplateInfoMapper taskTemplateInfoMapper;

    @Value("${channel.visa.MailSendAccount}")
    private String mailSendAccount;
    @Value("${channel.visa.MailSendPassword}")
    private String mailSendPassword;

    @Value("${send.oaid}")
    private String sendOaid;
    @Value("${channel.visa.responsible}")
    private String channelVisaResponsible;

    @Value("${insertMessage.url}")
    private String xxSystemMessageUrl;

    @Async
    public void updateVisaStatusUrl(String studentNo, String url) {
        try {
            String message = String.format("studentNo=%s", studentNo);
            logger.info("调用根据学号更改客户推荐表的获签状态数据：" + message);
            String result = HttpUtils.sendPost2(url, message);
            logger.info("调用根据学号更改客户推荐表的获签状态返回数据：" + result);
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (jsonObject == null || jsonObject.isEmpty()) {
                this.addFailureJob(url, message, "post");
            } else {
                if (jsonObject.getInteger("code") != 1) {
                    this.addFailureJob(url, message, "post");
                }
            }
        } catch (Exception ex) {
            logger.error("调用根据学号更改客户推荐表的获签抛异常: " + ex.getMessage());
        }
    }

    /**
     * 发送邮件
     * @param mailEntity
     */
    @Async
    public void sendEmail(MailEntity mailEntity) {
        mailEntity.setSendAccount(this.mailSendAccount);
        logger.info("发件人账号{}"+this.mailSendAccount);
        mailEntity.setSendPassword(this.mailSendPassword);
        logger.info("发件人密码{}"+this.mailSendPassword);
        new MailUtils().send(mailEntity);
    }

    public Integer addFailureJob(String url, String query_param, String method) {
        FailureJob failureJob = new FailureJob();
        failureJob.setUrl(url);
        failureJob.setModule("visaRecord");
        failureJob.setQueryParam(query_param);
        failureJob.setMethod(method);
        failureJob.setDeleteStatus(false);
        failureJob.setCreateTime(new Date());
        return failureJobService.add(failureJob);
    }

    /**
     * 定校方案学生待确认提示
     * @param studentNo 学号
     * @param auditTime 审核时间
     */
    @Async
    public void sendXxSystemMessage(String studentNo, Date auditTime){
        try {
            TaskTemplateInfo taskTemplateInfo = new TaskTemplateInfo();
            taskTemplateInfo.setCode(Contants.PLAN_COLLEGE_STUDENT_CONFIRM_TIP);
            taskTemplateInfo.setDeleteStatus(false);
            List<TaskTemplateInfo> taskTemplateInfos = taskTemplateInfoMapper.select(taskTemplateInfo);
            if (taskTemplateInfos != null && taskTemplateInfos.size() > 0) {
                String messageContent = taskTemplateInfos.get(0).getMessage();
                MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
                // 1-合同确认消息 2-取消合同支付 3-支付成功 4-定校方案消息 5-院校申请消息 6-签证申请消息 7-海外服务消息 8-留学暂缓消息 9-退费消息 10
                param.add("taskType", "10");
                param.add("title", "定校方案");
                param.add("content", messageContent);
                param.add("messageTime", DateFormatUtils.format(auditTime, Contants.timePattern));
                // 标识类型（1-学号，2-资源号）
                param.add("type", "1");
                param.add("studentNoOrResourceNo", studentNo);
                HttpUtils.doPost(xxSystemMessageUrl, param, String.class);
            }
        } catch (Exception e){
            logger.error("定校方案确认消息发送失败！studentNo = {}", studentNo);
        }
    }
}
