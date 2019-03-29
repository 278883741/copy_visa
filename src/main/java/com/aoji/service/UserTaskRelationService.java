package com.aoji.service;

import com.aoji.model.UserTaskRelation;
import com.aoji.model.req.SendMessageReq;
import com.aoji.vo.MessageVO;
import com.aoji.vo.UserTaskRelationVO;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

/**
 * author: chenhaibo
 * description: 消息管理接口
 * date: 2018/1/15
 */
public interface UserTaskRelationService {

    /**
     * 发送消息
     * @param sendMessageReq
     * @return
     */
    boolean sendMessage(SendMessageReq sendMessageReq);

    /**
     * 转案拒绝发送消息
     * @return
     */
    boolean sendTransferRefuse(String studentNo, String receiveNo, Map<String, String> tmeplateParam);

    /**
     * 消息已读
     * @param id
     * @return
     */
    boolean read(Integer id);

    /**
     * 查询列表
     * @param userTaskRelation
     * @return
     */
    List<MessageVO> getList(UserTaskRelation userTaskRelation);


    Boolean getMessageList(String oaid,Model model,Integer pageIndex);

    List<UserTaskRelationVO> getTypeList(String oaid, String taskType, Integer pageIndex);

}
