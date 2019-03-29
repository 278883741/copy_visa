package com.aoji.controller;

import com.aoji.contants.Contants;
import com.aoji.model.BasePageModel;
import com.aoji.model.CountryInfo;
import com.aoji.model.InvoiceListCondition;
import com.aoji.model.PageParam;
import com.aoji.service.ChannelCommissionService;
import com.aoji.service.CountryService;
import com.aoji.service.InvoiceService;
import com.aoji.utils.DateUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 佣金统计
 */
@Controller
@RequestMapping("/report/commission")
public class CommissionReportController extends BaseController{

    @Autowired
    InvoiceService invoiceService;
    @Autowired
    CountryService countryService;
    @Autowired
    ChannelCommissionService channelCommissionService;

    @RequestMapping("/list")
    public String listPage(Model model){
        List<CountryInfo> countryInfoList = countryService.getList(new CountryInfo());
        List<CountryInfo> countryInfoNew = new ArrayList<>();
        for (CountryInfo item : countryInfoList) {
            if (item.getCountryBussid() != 100010 && item.getCountryBussid() != 100011) {
                countryInfoNew.add(item);
            }
        }
        model.addAttribute("countryInfoList", countryInfoNew);
        return "/report/commissionList";
    }

    @RequestMapping("/list/data")
    @ResponseBody
    public BasePageModel listData(InvoiceListCondition invoiceListCondition, PageParam pageParam, BasePageModel basePageModel, HttpSession session){

        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength());

        // 日期处理
        String visaDateEnd = invoiceListCondition.getVisaDateEnd();
        String returnDateEnd = invoiceListCondition.getReturnDateEnd();
        invoiceListCondition.setVisaDateEnd(StringUtils.hasText(visaDateEnd) ? visaDateEnd + Contants.DATE_SUFFIX : null);
        invoiceListCondition.setReturnDateEnd(StringUtils.hasText(returnDateEnd) ? returnDateEnd + Contants.DATE_SUFFIX : null);
        Date sendDateEnd = invoiceListCondition.getSendDateEnd();
        invoiceListCondition.setSendDateEnd(DateUtil.changeDayForDate(sendDateEnd, 1));

        invoiceService.commissionReport(invoiceListCondition);

        session.setAttribute(Contants.COMMISSION_REPORT_CONDITION_SESSION_KEY, invoiceListCondition);

        return dataTableWapper(page, basePageModel);
    }

    @RequestMapping("/toExcel")
    public void report(HttpServletRequest request, HttpServletResponse response){
        invoiceService.commissionListToExcel2(request, response);
    }

    @RequestMapping("/toAccountList/toExcel")
    public void toAccountListReport(HttpServletRequest request, HttpServletResponse response){
        channelCommissionService.toAccountListToExcel(request, response);
    }

    @RequestMapping("/agentStudentList/toExcel")
    public void agentStudentListReport(HttpServletRequest request, HttpServletResponse response){
        channelCommissionService.agentStudentListToExcel(request, response);
    }
}
