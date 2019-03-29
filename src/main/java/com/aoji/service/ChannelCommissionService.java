package com.aoji.service;

import com.aoji.contants.ChannelReturnStatusEnum;
import com.aoji.model.ChannelAgentInfo;
import com.aoji.vo.ToAccountListVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface ChannelCommissionService {
    /**
     * 渠道到账列表
     * @param toAccountListVO
     * @return
     */
    List<ToAccountListVO> getToAccountList(ToAccountListVO toAccountListVO);

    /**
     * 代理学生列表
     * @param toAccountListVO
     * @return
     */
    List<ToAccountListVO> getAgentStudentList(ToAccountListVO toAccountListVO);

    /**
     * 修改渠道返佣状态 （确认到账，标识）
     * @param invoiceIds
     * @param toAccountListVOList
     * @param type
     * @return
     */
    Boolean updateChannelReturnStatus(String invoiceIds, List<ToAccountListVO> toAccountListVOList, ChannelReturnStatusEnum type);

    /**
     * 根据代理Id获取代理信息
     * @param agentId
     * @return
     */
    ChannelAgentInfo getAgentInfoByAgentId(Integer agentId,String signDate) throws Exception;

    /**
     * 返佣到账列表导出
     * @param request
     * @param response
     */
    void toAccountListToExcel(HttpServletRequest request, HttpServletResponse response);

    /**
     * 代理学生列表导出
     * @param request
     * @param response
     */
    void agentStudentListToExcel(HttpServletRequest request, HttpServletResponse response);
}
