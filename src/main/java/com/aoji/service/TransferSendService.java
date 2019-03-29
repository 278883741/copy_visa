package com.aoji.service;

import com.aoji.model.TransferSendInfo;
import com.aoji.model.req.TransferReq;
import com.aoji.model.res.TransferRes;
import com.aoji.vo.AllotVO;
import com.aoji.vo.SaveTransferVO;
import com.aoji.vo.TransferVO;

import java.util.List;

public interface TransferSendService {

    /**
     * 关联查询列表
     *
     * @param transferVO
     * @return
     */
    List<TransferVO> transferInfoRelatedQuery(TransferVO transferVO);

    /**
     * 转案列表总数
     *
     * @param transferVO
     * @return
     */
    int transferInfoRelatedQueryCount(TransferVO transferVO);

    /**
     * 根据ID修改信息
     *
     * @param transferSendInfo
     * @return
     */
    int updateById(TransferSendInfo transferSendInfo);

    /**
     * 根据ID查询转案信息
     *
     * @param id
     * @return
     */
    TransferSendInfo getTransferSendById(Integer id);

    /**
     * 添加数据
     *
     * @param transferSendInfo
     * @return
     */
    int insert(TransferSendInfo transferSendInfo);

    /**
     * 保存转案信息
     *
     * @param saveTransferVO
     * @return
     */
    boolean saveTransfer(SaveTransferVO saveTransferVO);

    /**
     * 转案
     *
     * @param transferReq
     * @return
     */
    TransferRes doTransfer(TransferReq transferReq) throws Exception;

    /**
     * 查询转案是否完成（顾问和文案全都接受）
     *
     * @param studentNo
     * @return
     */
    boolean transferResult(String studentNo);

    /**
     * 新分页查询
     *
     * @param transferVO
     * @return
     */
    List<TransferVO> transferlistByPage(TransferVO transferVO);

    Boolean updateAllot(AllotVO allotVO);
}
