package com.aoji.service;

import com.aoji.model.SendDingdingMessage;
import org.dom4j.DocumentException;

public interface SendDingDingMessageService {


    void  sendDingDingMessage(Integer bussinessId , String applyId,
                              String  applyResultId , String studentNo,
                              String sendUrl , String send_oaid,
                              String dingdingMessageApiMessage) throws Exception;

    void pureSendDingDingMessage(SendDingdingMessage sendDingdingMessage) throws Exception;
}
