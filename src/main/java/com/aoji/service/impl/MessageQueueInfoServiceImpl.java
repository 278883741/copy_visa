package com.aoji.service.impl;

import com.aoji.contants.Contants;
import com.aoji.contants.MQStatusEnum;
import com.aoji.contants.MQTypeEnum;
import com.aoji.mapper.MessageQueueInfoMapper;
import com.aoji.model.MessageQueueInfo;
import com.aoji.service.MessageQueueInfoService;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class MessageQueueInfoServiceImpl implements MessageQueueInfoService {

    @Autowired
    private MessageQueueInfoMapper messageQueueInfoMapper;

    @Override
    public int insertSendMessage(MessageQueueInfo messageQueueInfo) {
        Date now = new Date();
        Date nextRetryTime = DateUtils.addMinutes(now, Contants.SEND_MESSAGE_MAX_TIMEOUT);
        messageQueueInfo.setRetryCount(0);
        messageQueueInfo.setCreateTime(now);
        messageQueueInfo.setNextRetryTime(nextRetryTime);
        messageQueueInfo.setType(MQTypeEnum.SEND.getCode());
        messageQueueInfo.setStatus(MQStatusEnum.SEND_SENDING.getCode());
        return messageQueueInfoMapper.insert(messageQueueInfo);
    }

    @Override
    public int insertReceiveMessage(MessageQueueInfo messageQueueInfo) {
        Date now = new Date();
        Date nextRetryTime = DateUtils.addMinutes(now, Contants.CONSUME_MESSAGE_MAX_TIMEOUT);
        messageQueueInfo.setRetryCount(0);
        messageQueueInfo.setCreateTime(now);
        messageQueueInfo.setNextRetryTime(nextRetryTime);
        messageQueueInfo.setType(MQTypeEnum.RECEIVE.getCode());
        messageQueueInfo.setStatus(MQStatusEnum.RECEIVE_SUCCESS.getCode());
        return messageQueueInfoMapper.insert(messageQueueInfo);
    }

    @Override
    public int updateByPrimaryKeySelective(MessageQueueInfo messageQueueInfo) {
        Date now = new Date();
        messageQueueInfo.setUpdateTime(now);
        return messageQueueInfoMapper.updateByPrimaryKeySelective(messageQueueInfo);
    }

    @Override
    public int updateByBusinessKeySelective(MessageQueueInfo messageQueueInfo) {
        Date now = new Date();
        messageQueueInfo.setUpdateTime(now);
        return messageQueueInfoMapper.updateByBusinessKeySelective(messageQueueInfo);
    }

    @Override
    public List<MessageQueueInfo> select(MessageQueueInfo messageQueueInfo) {
        return messageQueueInfoMapper.select(messageQueueInfo);
    }

    @Override
    public List<MessageQueueInfo> selectByExample(Example example) {
        return messageQueueInfoMapper.selectByExample(example);
    }
}
