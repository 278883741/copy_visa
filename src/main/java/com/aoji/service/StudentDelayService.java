package com.aoji.service;

import com.aoji.model.StudentDelayInfo;

import java.util.List;

public interface StudentDelayService {

    /**
     * 添加缓办申请
     * @param studentDelayInfo
     * @return
     */
    boolean insert(StudentDelayInfo studentDelayInfo);

    /**
     * 修改缓办
     * @param studentDelayInfo
     * @return
     */
    boolean update(StudentDelayInfo studentDelayInfo);

    /**
     * 根据学号查询缓办申请
     * @param studentNo
     * @return
     */
    List<StudentDelayInfo> getList(String studentNo);

    /**
     * 根据学号查询所有缓办申请记录
     * @param studentDelayInfo
     * @return
     */
    List<StudentDelayInfo> get(StudentDelayInfo studentDelayInfo);

    /**
     * 审批
     * @param id
     * @param type
     * @param reason
     * @return
     */
    String delayApprove(Integer id, Integer type, String updateTime,String reason, String studentNo);


    void testInsertSettle();

}
