package com.aoji.service;

/**
 * author: chenhaibo
 * description: 发送消息到 MQ
 * date: 2018/10/9
 */
public interface RabbitMQService {

    /**
     * 发送消息
     * @param businessKey 关联业务主键
     * @param businessTypeCode 业务类型code
     * @param messageBody 消息体
     */
    void sendMessage(String businessKey, String businessTypeCode, Object messageBody);

}
