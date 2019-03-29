package com.aoji.mapper;

import com.aoji.model.TransferSendInfo;
import com.aoji.vo.TransferVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TransferSendInfoMapper extends Mapper<TransferSendInfo> {

    /**
     * 转案列表关联查询
     * @param transferVO
     * @return
     */
    List<TransferVO> transferInfoRelatedQuery(TransferVO transferVO);

    /**
     * 转案列表总数
     * @param transferVO
     * @return
     */
    int transferInfoRelatedQueryCount(TransferVO transferVO);

    /**
     * 根据学号修改信息
     * @param transferSendInfo
     * @return
     */
    int updateByStudentNoSelective(@Param("transferSendInfo") TransferSendInfo transferSendInfo,
                                   @Param("changeCopyOperator") Boolean changeCopyOperator,
                                   @Param("changeCopyMaker") Boolean changeCopyMaker);

    /**
     * 根据applyId修改信息
     * @param transferSendInfo
     * @return
     */
    int updateByApplyIdSelective(TransferSendInfo transferSendInfo);

    /**
     * 修改可用状态根据学号和id
     * @param studentNo
     * @param id
     * @return
     */
    int updateByStudentNoAndId(@Param("studentNo") String studentNo,
                               @Param("id") Integer id);

    /**
     * 查询转案结果(文签转案)
     * @param studentNo
     * @return
     */
    List<TransferVO> getTransferResult(String studentNo);

    /**
     * 查询学生转案确认的数量
     * @param studentNo
     * @return
     */
    int transferConfirmCount(String studentNo);

    /**
     * 查询转案记录
     * @param studentNo
     * @param operatorType
     * @return
     */
    List<TransferVO> queryTransferRecord(@Param("studentNo") String studentNo,
                                         @Param("operatorType") Integer operatorType);

    /**
     * 查询转案记录 - 2
     * @param studentNo
     * @param operatorType
     * @return
     */
    List<TransferVO> queryTransferRecord2(@Param("studentNo") String studentNo,
                                         @Param("operatorType") Integer operatorType);
}