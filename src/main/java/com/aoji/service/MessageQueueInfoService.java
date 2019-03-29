package com.aoji.service;

import com.aoji.model.MessageQueueInfo;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

public interface MessageQueueInfoService {

    /**
     * 保存发送的消息
     * @param messageQueueInfo
     * @return
     */
    int insertSendMessage(MessageQueueInfo messageQueueInfo);

    /**
     * 保存接收到的消息
     * @param messageQueueInfo
     * @return
     */
    int insertReceiveMessage(MessageQueueInfo messageQueueInfo);

    /**
     * 更新消息
     * @param messageQueueInfo
     * @return
     */
    int updateByPrimaryKeySelective(MessageQueueInfo messageQueueInfo);

    /**
     * 根据businessKey更新消息
     * @param messageQueueInfo
     * @return
     */
    int updateByBusinessKeySelective(MessageQueueInfo messageQueueInfo);

    /**
     * 查询消息信息
     * @param messageQueueInfo
     * @return
     */
    List<MessageQueueInfo> select(MessageQueueInfo messageQueueInfo);

    /**
     * 查询消息信息
     * @param example
     * @return
     */
    List<MessageQueueInfo> selectByExample(Example example);
}
