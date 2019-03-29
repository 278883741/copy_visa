package com.aoji.mapper;

import com.aoji.model.TransferInfo;
import com.aoji.vo.CalibrationSchemeVo;
import com.aoji.vo.TransferListVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface TransferInfoMapper extends Mapper<TransferInfo> {

    /**
     * 转案列表
     * @param transferListVO
     * @return
     */
    List<TransferListVO> transferList(TransferListVO transferListVO);

    /**
     * 根据学号查询转案信息
     * @param studentNo
     * @return
     */
    List<TransferInfo> getByStudentNoAndType(@Param("studentNo") String studentNo,
                                      @Param("transferType") Integer transferType
                                        );



    List<Map<String,Object>> transferCaseListByVisaManagerOrDirector(
            @Param("calibrationSchemeVo") CalibrationSchemeVo calibrationSchemeVo,
            @Param("arrayId") Integer[] arrayId
            );
}