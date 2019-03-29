package com.aoji.mapper;

import com.aoji.model.TransferSend;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TransferSendMapper extends Mapper<TransferSend> {

    int updateByTransferId(@Param("transferId") Integer transferId,
                           @Param("enableStatus") Integer enableStatus);

    /**
     * 查询上一个顾问
     * @param studentNo 学号
     * @param operatorType 操作类型： 高签/制作
     * @param transferType 转案类型： 文签/外联
     * @return
     */
    List<TransferSend> selectLastReceiver(@Param("studentNo") String studentNo,
                                          @Param("operatorType") Integer operatorType,
                                          @Param("transferType") Integer transferType);

    /**
     * 根据id禁用转案分配信息
     * @param sendIds
     * @return
     */
    int disableByTransferIdAndOperatorType(@Param("sendIds") List<Integer> sendIds,
                                    @Param("operatorType") Integer operatorType);
}