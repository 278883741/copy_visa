package com.aoji.mapper;

import com.aoji.model.CommissionInvoice;
import com.aoji.model.InvoiceListCondition;
import com.aoji.vo.CommissionReportVO;
import com.aoji.vo.InvoiceListVO;
import com.aoji.vo.ToAccountListVO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface CommissionInvoiceMapper extends Mapper<CommissionInvoice> {

    List<InvoiceListVO> getList(InvoiceListCondition invoiceListCondition);

    List<InvoiceListVO> getListByAddInvoice(InvoiceListCondition invoiceListCondition);

    InvoiceListVO getListSum(InvoiceListCondition invoiceListCondition);

    int updateBySchoolId(CommissionInvoice commissionInvoice);

    List<Map> commissionReportMap(
            @Param("invoiceListCondition") InvoiceListCondition invoiceListCondition);

    List<CommissionReportVO> commissionReport(
            @Param("invoiceListCondition") InvoiceListCondition invoiceListCondition,
            @Param("pageStart") Integer pageStart,
            @Param("pageEnd") Integer pageEnd,
            @Param("usaNationId") Integer [] usaNationId);

    int commissionReportCount(
            @Param("invoiceListCondition")InvoiceListCondition invoiceListCondition,
            @Param("usaNationId")Integer [] usaNationId
                                );

    List<CommissionInvoice> getBySchoolId(Integer schoolId);

    List<CommissionInvoice> checkSchool(@Param("schoolIds") List<Integer> schoolIds, @Param("invoiceNo") String invoiceNo);

    /**
     * 佣金到账列表 - 渠道
     * @return
     */
    List<ToAccountListVO> getToAccountList(ToAccountListVO toAccountListVO);
}