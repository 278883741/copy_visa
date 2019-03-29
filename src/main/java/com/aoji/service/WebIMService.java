package com.aoji.service;

import com.aoji.vo.IMGroupHistoryVO;
import com.aoji.vo.IMStudentSearchVO;

import java.util.List;
import java.util.Map;

/**
 * author: chenhaibo
 * description: IM相关接口
 * date: 2018/12/8
 */
public interface WebIMService {

    /**
     * 获取IM登录信息
     * @return
     */
    Map<String, String> getLoginInfo(String memberId);

    /**
     * 学生搜索数据获取
     * @return
     */
    List<IMStudentSearchVO> studentSearch(String memberId);

    /**
     * 群组聊天记录
     * @param groupId
     * @param msgTimeStamp
     * @return
     */
    List<IMGroupHistoryVO> getGroupHistory(String groupId, String msgTimeStamp);
}
