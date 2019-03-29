package com.aoji.mapper;

import com.aoji.model.MessageQueueInfo;
import tk.mybatis.mapper.common.Mapper;

public interface MessageQueueInfoMapper extends Mapper<MessageQueueInfo> {

    int updateByBusinessKeySelective(MessageQueueInfo messageQueueInfo);
}