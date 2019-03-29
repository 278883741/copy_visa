package com.aoji.service.impl;

import com.aoji.contants.CaseIdEnum;
import com.aoji.contants.Contants;
import com.aoji.contants.ContentException;
import com.aoji.mapper.*;
import com.aoji.model.ApplyInfo;
import com.aoji.model.ApplyResultInfo;
import com.aoji.model.AuditDingdingInfo;
import com.aoji.model.SendDingdingMessage;
import com.aoji.service.*;
import com.aoji.utils.HttpUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class SendDingDingMessageServiceImpl implements SendDingDingMessageService {
    @Autowired
    AuditDingdingInfoMapper auditDingdingInfoMapper;

    @Value("${dingding.message.link}")
    private String dingdingMessageLink;

    @Value("${send.oaid}")
    private String sendOaid;

    @Value("${dingding.message.status}")
    private Boolean dingdingMessageStatus;

    @Value("${dingding.message.api}")
    private String dingdingMessageApi;

    @Autowired
    SendDingdingMessageMapper sendDingdingMessageMapper;

    private Logger logger= LoggerFactory.getLogger(ApplyResultServiceImpl.class);

    /**
     * 此方法涉及院校申请结果自动审批
     * @param bussinessId
     * @param applyId
     * @param applyResultId
     * @param studentNo
     * @param sendUrl
     * @param send_oaid
     * @param dingdingMessageApiMessage
     * @throws Exception
     */
    @Transactional
    @Override
    public void sendDingDingMessage (Integer bussinessId , String applyId,
                                     String  applyResultId , String studentNo,
                                     String sendUrl , String send_oaid,
                                     String dingdingMessageApiMessage) throws  Exception{
           logger.info("调取钉钉发送消息接口 bussinessId："+bussinessId+"applyId："+applyId+"applyResultId："+applyResultId+"studentNo:"+studentNo+"sendUrl:"+sendUrl+"send_oaid:"+send_oaid);

            AuditDingdingInfo auditDingdingInfo = new AuditDingdingInfo();
            //String url = dingdingMessageApi+"/smspush/DingSing?worknum="+send_oaid+"&content=您有新的审批消息!学生学号:"+resultInfo.getStudentNo()+"&url="+sendUrlEncrypt+"&typeid=10";
            String url = dingdingMessageApiMessage;
            logger.info("url:"+url);
            //发送钉钉消息前给钉钉审批表插入数据
            auditDingdingInfo.setCaseId(CaseIdEnum.ApplyResult.getCode());
            if(bussinessId!=null){
                auditDingdingInfo.setBussinessId(bussinessId);
            }
            auditDingdingInfo.setSendStatus(false);
            auditDingdingInfo.setSendLink(sendUrl);
            auditDingdingInfo.setSendTime(new Date());
            auditDingdingInfo.setAuditOpteratorNo(send_oaid);
            if(StringUtils.hasText(applyId)){
                auditDingdingInfo.setApplyId(applyId);
            }
            if(StringUtils.hasText(applyResultId)){
                auditDingdingInfo.setApplyResultId(applyResultId);
            }
            auditDingdingInfo.setAuditStatus(false);
            if(StringUtils.hasText(studentNo)){
                auditDingdingInfo.setStudentNo(studentNo);
            }
            int saveResult = auditDingdingInfoMapper.insert(auditDingdingInfo);

            int updateResut = 0;
            String resultCode = "";
            Document document = null;
            if(dingdingMessageStatus){
                if(saveResult > 0){
                    logger.info("钉钉消息插入audit_dingding_info记录表成功");
                        document = DocumentHelper.parseText(HttpUtils.doGet(url));
                }
                resultCode = document.getStringValue();
            }
            if(Contants.STATUSCODE_SUCCESS.equals(resultCode)){
                auditDingdingInfo.setCaseId(CaseIdEnum.ApplyResult.getCode());
                if(bussinessId!=null){
                    auditDingdingInfo.setBussinessId(bussinessId);
                }
                auditDingdingInfo.setSuccessTime(new Date());
                auditDingdingInfo.setSendStatus(true);
                updateResut = auditDingdingInfoMapper.updateAuditDingSendStatusByBussinessIdAndCaseId(auditDingdingInfo);
                logger.info("钉钉消息发送成功");
            }else if(Contants.STATUSCODE_ERROR.equals(resultCode)){
                new ContentException(1,"钉钉消息发送失败");
            }else if(Contants.STATUSCODE_CODERROR.equals(resultCode)){
                new ContentException(1,"钉钉消息发送失败(程序报错!)");
            }else{
                logger.info("钉钉消息发送成功");
            }
        }

    /**
     * 此方法只是单纯的发送钉钉消息
     * @param sendDingdingMessage
     * @throws Exception
     */
    @Transactional
    @Override
    public void pureSendDingDingMessage (
            SendDingdingMessage sendDingdingMessage) throws  Exception{
        logger.info("调取钉钉发送消息接口 studentNo:"+sendDingdingMessage.getStudentNo()+"send_oaid:"+sendDingdingMessage.getSendOaid());

        String url = sendDingdingMessage.getDingdingMessageApiMessage();
        logger.info("url:"+url);
        String resultCode = "";
        Document document = null;
        if(dingdingMessageStatus){
            document = DocumentHelper.parseText(HttpUtils.doGet(url));
            resultCode = document.getStringValue();
        }
        if(Contants.STATUSCODE_SUCCESS.equals(resultCode)){
            logger.info("钉钉消息发送成功");
            sendDingdingMessage.setSendStatus(false);
        }else if(Contants.STATUSCODE_ERROR.equals(resultCode)){
            sendDingdingMessage.setSendStatus(true);
            new ContentException(1,"钉钉消息发送失败");
        }else if(Contants.STATUSCODE_CODERROR.equals(resultCode)){
            sendDingdingMessage.setSendStatus(true);
            new ContentException(1,"钉钉消息发送失败(程序报错!)");
        }else{
            sendDingdingMessage.setSendStatus(false);
            logger.info("钉钉消息发送成功");
        }

        int save = sendDingdingMessageMapper.insertSelective(sendDingdingMessage);
        if(save>0){
            logger.info("钉钉消息记录成功");
        }else{
            logger.info("钉钉消息记录失败");
        }
    }

}
