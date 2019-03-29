package com.aoji.service;

import com.aoji.model.BaseResponse;
import com.aoji.model.SysUser;
import com.aoji.model.TransferInfo;
import com.aoji.vo.TransferListVO;

import java.util.List;

/**
 * author: chenhaibo
 * description: 转案管理接口
 * date: 2018/5/7
 */
public interface TransferService {

    /**
     * 转案列表 - 文签
     * @param transferListVO
     * @return
     */
    List<TransferListVO> transferListForVisa(TransferListVO transferListVO);

    /**
     * 转案列表 - 外联
     * @param transferListVO
     * @return
     */
    List<TransferListVO> transferListForOutreach(TransferListVO transferListVO);

    /**
     * 根据学号查询转案信息
     * @param studentNo
     * @return
     */
    TransferInfo getTransferInfoByStudentNo(String studentNo, Integer transferType);

    /**
     * 保存转案分配记录
     * @param transferListVO
     * @return
     */
    BaseResponse saveTransferForVisa(TransferListVO transferListVO);

    /**
     * 保存转案分配记录
     * @param transferListVO
     * @return
     */
    BaseResponse saveTransferForOutreach(TransferListVO transferListVO);

    /**
     * 转案接收或拒绝
     * @param sendId transfer_send 表 id
     * @param confirmStatus 确认状态
     * @param comment 拒绝原因
     * @return
     */
    BaseResponse updateConfirmStatus(Integer sendId, Integer confirmStatus, String comment, SysUser sysUser);

    /**
     * 查询转案分配人
     * @param countryBussId
     * @return
     */
    List<SysUser> getTransferUser(Integer countryBussId);

    /**
     * 根据国家组查询转案分配人
     * @param countryGroup
     * @return
     */
    List<SysUser> getTransferUserByCountryGroup(Integer countryGroup);

    /**
     * 离职转接记录 - 签约系统
     * @param studentNo
     * @param transferType
     * @param operatorType
     * @return
     */
    void resigendTransfer(String studentNo, Integer transferType, Integer operatorType);

    /**
     * 批量转案
     * @param transferListVO
     * @return
     */
    boolean batchSaveTransferForVisa(TransferListVO transferListVO);
}
