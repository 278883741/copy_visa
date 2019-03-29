package com.aoji.mapper;

import com.aoji.config.util.FinanceModel;
import com.aoji.model.CurrencyInfo;
import com.aoji.model.StudentInfo;
import com.aoji.vo.ToAccountListVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;
import java.util.Map;

public interface CurrencyInfoMapper extends Mapper<CurrencyInfo> {
    /**
     * 财务返佣金列表
     * @param toAccountListVO
     * @return
     */
    List<ToAccountListVO> getFinanceReturnCommissionList(
            @Param("toAccountListVO") ToAccountListVO toAccountListVO

    );
    boolean updateChannelReturnStatusBychannCommId(ToAccountListVO toAccountListVO);

    /**
     * 根据学号查询当前学生的返佣次数
     * @param studentNo
     * @param name
     * @return
     */
    List<ToAccountListVO> getReturnCommissionFrequencyListByStudentNo(@Param("studentNo") String studentNo,@Param("name")  String name);
    /**
     * 财务返佣导出查询数据总条数
     * @param toAccountListVO
     * @return
     */
    int getCountFinanceReturnCommissionListForExcel(@Param("toAccountListVO") ToAccountListVO toAccountListVO);

    /**
     * 财务返佣分页导出数据
     * @param toAccountListVO
     * @param pageStart
     * @param pageEnd
     * @return
     */
    List<FinanceModel> getFinanceReturnCommissionListForExcel(@Param("toAccountListVO") ToAccountListVO toAccountListVO,@Param("pageStart") int pageStart,@Param("pageEnd") int pageEnd);

}