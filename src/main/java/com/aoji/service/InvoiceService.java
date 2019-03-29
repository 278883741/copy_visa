package com.aoji.service;

import com.aoji.model.BaseResponse;
import com.aoji.model.InvoiceListCondition;
import com.aoji.vo.CommissionReportVO;
import com.aoji.vo.InvoiceListVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface InvoiceService {

    /**
     * invoice列表查询
     * @param invoiceListCondition
     * @return
     */
    List<InvoiceListVO> getList(InvoiceListCondition invoiceListCondition);

    /**
     * invoice列表某些项的总金额查询
     * @param invoiceListCondition
     * @return
     */
    InvoiceListVO getListSum(InvoiceListCondition invoiceListCondition);

    /**
     * invoice添加页查询
     * @param invoiceListCondition
     * @return
     */
    List<InvoiceListVO> getListByAddInvoice(InvoiceListCondition invoiceListCondition);

    /**
     * 保存添加或编辑的invoice
     * @param invoiceListVO
     * @param type
     * @return
     */
    BaseResponse save(InvoiceListVO invoiceListVO, Integer type);

    /**
     * invoice中删除一条
     * @param id
     * @return
     */
    BaseResponse remove(Integer id);

    /**
     * 佣金统计-报表
     * @param invoiceListCondition
     * @return
     */
    List<CommissionReportVO> commissionReport(InvoiceListCondition invoiceListCondition);

    /**
     * 佣金统计列表导出 — 新
     * @param request
     * @param response
     */
    void commissionListToExcel(HttpServletRequest request, HttpServletResponse response);

    /**
     * 佣金统计列表导出到Excel
     * @param request
     * @param response
     */
    void commissionListToExcel2(HttpServletRequest request, HttpServletResponse response);

    /**
     * 检查院校是否可以添加到Invoice
     * @param invoiceListCondition
     * @return
     */
    BaseResponse checkSchool(InvoiceListCondition invoiceListCondition);



    boolean updateInvoiceRemarkByInvoiceId(String invoiceId, String schoolRemark);
}
