package com.aoji.service;

import com.aoji.model.ChannelStudentConsulter;
import com.aoji.model.ChannelStudentInfo;

import com.aoji.model.StudentInfo;
import com.aoji.model.res.QueryAgentInfoListRes;

import java.util.List;

public interface ChannelStudentService {

    /**
     * 同业学生根据学生id进行审核
     * @param channelStudentId
     */
    Boolean auditChannelStudentById(String channelStudentId,String consulterNo) throws Exception;
    boolean add(ChannelStudentInfo channelStudentInfo);
    ChannelStudentInfo get(Integer id);
    boolean update(ChannelStudentInfo channelStudentInfo);
    /**
     * 同业已审核学生列表查询（此处只查询student_info表里的有代理Id的学生）
     * @param studentInfo
     * @param channelStatus
     * @param agentId
     * @return
     */
    List<StudentInfo> getAuditStudentByAgentId(StudentInfo studentInfo, Boolean channelStatus, String agentId);

    List<ChannelStudentConsulter> getChannelStudentConsulterList();

    ChannelStudentInfo selectByStudentNo(String studentNo);

    /**
     * 触发节点：同业负责人“审核”并分配咨询顾问后，提醒同业文签负责人（工号在配置文件中）
     * @param channelStudentId
     * @throws Exception
     */
    void sendExamineMassage(String channelStudentId,String consulterNo) throws Exception;
    /**
     * 触发节点：机构“提交”后，提醒同业负责人(同业经理)
     * @param channelStudentInfo
     * @throws Exception
     */
    void sendSubmissionMessage(ChannelStudentInfo channelStudentInfo) throws Exception;

    Boolean roleName();

    /**
     * 获取唯一值
     * @param type 1-同业学号 2-工号
     * @return
     */
    String getStudentNo(Integer type);

    /**
     * 根据所有代理列表
     * @return
     */
    List<QueryAgentInfoListRes> queryAgentInfoList();

    /**
     * 判断当前学号是否属于同业学生
     * @param studentNo
     * @return
     */
    boolean isChannelStudent(String studentNo);
}
