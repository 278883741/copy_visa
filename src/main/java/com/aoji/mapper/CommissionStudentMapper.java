package com.aoji.mapper;

import com.aoji.model.CommissionStudent;
import com.aoji.vo.CommissionManageSaveVO;
import com.aoji.vo.CommissionManageVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CommissionStudentMapper extends Mapper<CommissionStudent> {

    List<CommissionManageVO> getCommissionManageList(
                                                        @Param("commissionManageVO") CommissionManageVO commissionManageVO,
                                                        @Param("ausNationId") Integer [] ausNationId,
                                                        @Param("usaNationId") Integer [] usaNationId,
                                                        @Param("roleStatus") Integer roleStatus,
                                                        @Param("agentType")String agentType
                                                );

    List<CommissionManageVO> getCommissionManageExcelList(@Param("commissionManageVO") CommissionManageVO commissionManageVO,
                                                          @Param("ausNationId") Integer [] ausNationId,
                                                          @Param("usaNationId") Integer [] usaNationId,
                                                          @Param("roleStatus") Integer roleStatus,
                                                          @Param("pageStart") Integer pageStart,
                                                          @Param("pageEnd") Integer pageEnd
    );

    List<CommissionManageSaveVO> getCommissionManageVOList(String studentNo);

    /**
     * 从文签系统中获取该学生对应的学校申请的附件
     */
    List<String> getOfferFiles(String studentNo);

    /**
     * 从文签系统中获取该学生对应的coe的附件申请
     */
    List<String> getCoeFiles(String studentNo);

    List<String> getSendOperator(String studentNo);


    /**
     * 已作废，关联关系使用id关联
     * @return
     */
//    int updateByStudentNoSelective(CommissionStudent commissionStudent);

    List<String> getStudentNo();

    int getCountCommissionManageExcelList(
            @Param("commissionManageVO") CommissionManageVO commissionManageVO,
            @Param("ausNationId") Integer [] ausNationId,
            @Param("usaNationId") Integer [] usaNationId,
            @Param("roleStatus") Integer roleStatus
    );


    int getCountCommissionOverseasExcelList(
            @Param("commissionManageVO")CommissionManageVO commissionManageVO,
            @Param("ausNationId") Integer [] ausNationId,
            @Param("usaNationId") Integer [] usaNationId,
            @Param("roleStatus") Integer roleStatus,
            @Param("agentType")String agentType
    );

    List<CommissionManageVO> getCommissionOverseasExcelList(@Param("commissionManageVO")CommissionManageVO commissionManageVO,
                                                            @Param("ausNationId") Integer [] ausNationId,
                                                            @Param("usaNationId") Integer [] usaNationId,
                                                            @Param("roleStatus")Integer roleStatus,
                                                            @Param("pageStart") Integer pageStart,
                                                            @Param("pageEnd") Integer pageEnd,
                                                            @Param("agentType")String agentType);

}