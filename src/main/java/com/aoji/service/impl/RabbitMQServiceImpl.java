package com.aoji.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.Contants;
import com.aoji.contants.MQBusinessTypeEunm;
import com.aoji.contants.MQStatusEnum;
import com.aoji.mapper.TransferInfoMapper;
import com.aoji.model.MessageQueueInfo;
import com.aoji.model.StudentInfo;
import com.aoji.model.TransferInfo;
import com.aoji.model.req.UpdateStudentInfoReq;
import com.aoji.service.MessageQueueInfoService;
import com.aoji.service.RabbitMQService;
import com.aoji.service.StudentService;
import com.rabbitmq.client.Channel;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Map;

@Service
public class RabbitMQServiceImpl implements RabbitMQService{

    public static final Logger logger = LoggerFactory.getLogger(RabbitMQServiceImpl.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MessageQueueInfoService messageQueueInfoService;

    public static final String VISA_TEST_EXCHANGE = "visa_test_exchange";
    public static final String VISA_TEST_ROUTING_KEY = "visa.test";

    /**
     * 确认回调函数
     */
    RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean result, String errorMsg) {
            logger.info(MessageFormat.format("回调确认! correlationData = {0}, result = {1}, errorMsg = {2}", correlationData, result, errorMsg));
            if(result){ // 发送成功
                // 更新消息状态
                MessageQueueInfo messageQueueInfo1 = new MessageQueueInfo();
                messageQueueInfo1.setId(Integer.valueOf(correlationData.getId()));
                messageQueueInfo1.setStatus(MQStatusEnum.SEND_SUCCESS.getCode());
                messageQueueInfoService.updateByPrimaryKeySelective(messageQueueInfo1);
            }else{ // 发送失败
                logger.error("MQ发送失败！"+errorMsg);
            }
        }
    };

    @Override
    public void sendMessage(String businessKey, String businessTypeCode, Object messageBody) {
        // 消息持久化
        MessageQueueInfo messageQueueInfo = new MessageQueueInfo();
        messageQueueInfo.setBusinessKey(businessKey);
        messageQueueInfo.setBusinessTypeCode(businessTypeCode);
        messageQueueInfo.setMessageBody(JSONObject.toJSONString(messageBody));
        messageQueueInfoService.insertSendMessage(messageQueueInfo);
        // 发送消息
        rabbitTemplate.setConfirmCallback(confirmCallback);
        logger.info("RabbitMQ 发送消息 开始 ===============");
        CorrelationData correlationData = new CorrelationData(messageQueueInfo.getId().toString());
        rabbitTemplate.convertAndSend(VISA_TEST_EXCHANGE, VISA_TEST_ROUTING_KEY, messageBody, correlationData);

    }

// ======================================================================================================================

    @Autowired
    StudentService studentService;
    @Autowired
    TransferInfoMapper transferInfoMapper;

    /**
     * 消费端监听，同步学生信息，手动确认
     * @param headers
     * @param channel
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${update.student.info.queue}", durable = "true"),
            exchange = @Exchange(value = "${update.student.info.exchange}", durable = "true", type = "topic"),
            key = "${update.student.info.routingKey}"
    ))
    @RabbitHandler
    public void receiveMessage(byte[] bytes, @Headers Map<String , Object> headers, Channel channel){
        String param = new String(bytes);
        logger.info("接受到消息：message = " + param);
        UpdateStudentInfoReq updateStudentInfoReq = JSONObject.parseObject(param, UpdateStudentInfoReq.class);
        // 消息持久化
        MessageQueueInfo messageQueueInfo = new MessageQueueInfo();
        messageQueueInfo.setBusinessKey(updateStudentInfoReq.getStudentNo());
        messageQueueInfo.setBusinessTypeCode(MQBusinessTypeEunm.SYNC_STUDENT_INFO.getCode());
        messageQueueInfo.setMessageBody(JSONObject.toJSONString(updateStudentInfoReq));
        messageQueueInfoService.insertReceiveMessage(messageQueueInfo);

        try {
            // 业务处理
            String studentNo = updateStudentInfoReq.getStudentNo();
            String studentName = updateStudentInfoReq.getStudentName();
            // 修改学生表
            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setStudentNo(studentNo);
            studentInfo.setStudentName(studentName);
            studentInfo.setPinyin(updateStudentInfoReq.getSpelling());
            if(StringUtils.isNotBlank(updateStudentInfoReq.getBirthday())) {
                studentInfo.setBirthday(DateUtils.parseDate(updateStudentInfoReq.getBirthday(), Contants.datePattern));
            }
            studentService.updateByStudentNo(studentInfo);

            // 修改转案表
            Example example = new Example(TransferInfo.class);
            example.createCriteria().andEqualTo("studentNo", studentNo);
            TransferInfo transferInfo = new TransferInfo();
            transferInfo.setStudentNo(studentNo);
            transferInfo.setStudentName(studentName);
            transferInfoMapper.updateByExampleSelective(transferInfo, example);

            // 修改消费状态
            MessageQueueInfo messageQueueInfo1 = new MessageQueueInfo();
            messageQueueInfo1.setId(messageQueueInfo.getId());
            messageQueueInfo1.setStatus(MQStatusEnum.CONSUME_SUCCESS.getCode());
            messageQueueInfoService.updateByPrimaryKeySelective(messageQueueInfo1);
        } catch (Exception e){
            logger.error("消息接收失败！error="+e.getMessage(), e);
        } finally {
            //手动确认
            Long deliverTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
            try {
                channel.basicAck(deliverTag, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 消费端监听，同步学生注册状态，手动确认
     * @param headers
     * @param channel
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${sync.student.register.status.queue}", durable = "true"),
            exchange = @Exchange(value = "${sync.student.register.status.exchange}", durable = "true", type = "topic"),
            key = "${sync.student.register.status.routingKey}"
    ))
    @RabbitHandler
    public void syncRegisterStatus(String studentNo, @Headers Map<String , Object> headers, Channel channel){
        logger.info("接受到消息：message = " + studentNo);

        // 消息持久化
        MessageQueueInfo messageQueueInfo = new MessageQueueInfo();
        messageQueueInfo.setBusinessKey(studentNo);
        messageQueueInfo.setBusinessTypeCode(MQBusinessTypeEunm.SYNC_REGISTER_STATUS.getCode());
        messageQueueInfo.setMessageBody(studentNo);
        messageQueueInfoService.insertReceiveMessage(messageQueueInfo);

        try {
            // 查询学生信息
            StudentInfo student = studentService.getStudentInfoByStudentNo(studentNo);
            if(student != null) {
                if(!student.getRegisterStatus()) {
                    // 业务处理
                    StudentInfo studentInfo = new StudentInfo();
                    studentInfo.setStudentNo(studentNo);
                    studentInfo.setRegisterStatus(true);
                    studentService.updateByStudentNo(studentInfo);
                }

                // 修改消费状态
                MessageQueueInfo messageQueueInfo1 = new MessageQueueInfo();
                messageQueueInfo1.setId(messageQueueInfo.getId());
                messageQueueInfo1.setStatus(MQStatusEnum.CONSUME_SUCCESS.getCode());
                messageQueueInfoService.updateByPrimaryKeySelective(messageQueueInfo1);
            }
        } catch (Exception e){
            logger.error("消息接收失败！error="+e.getMessage(), e);
        } finally {
            //手动确认
            Long deliverTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
            try {
                channel.basicAck(deliverTag, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
