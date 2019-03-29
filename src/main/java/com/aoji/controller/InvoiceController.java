package com.aoji.controller;

import com.alibaba.fastjson.JSONObject;
import com.aoji.mapper.CommissionBalanceMapper;
import com.aoji.model.*;
import com.aoji.service.*;
import com.aoji.service.impl.InvoiceServiceImpl;
import com.aoji.vo.InvoiceListVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author: chenhaibo
 * description:
 * date: 2018/6/20
 */
@Controller
@RequestMapping("/invoice")
public class InvoiceController extends BaseController {

    @Autowired
    private CountryService countryService;
    @Autowired
    private InvoiceService invoiceService;
    @Autowired
    private CurrencyInfoService currencyInfoService;
    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private CommissionInvoiceSeqService commissionInvoiceSeqService;
    @Autowired
    private CommissionBalanceMapper commissionBalanceMapper;

    public static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);
    public static final String INVOICE_LIST_CONDITION = "invoiceListCondition";

    @RequestMapping("/list")
    public String listPage(Model model) {
        List<CountryInfo> countryInfoList = countryService.getList(new CountryInfo());
        List<CountryInfo> countryInfoNew = new ArrayList<>();
        for (CountryInfo item : countryInfoList) {
            if (item.getCountryBussid() != 100010 && item.getCountryBussid() != 100011) {
                countryInfoNew.add(item);
            }
        }
        model.addAttribute("countryInfoList", countryInfoNew);
        return "/invoice/list";
    }

    @RequestMapping("/add")
    public String addPage(InvoiceListCondition invoiceListCondition, Model model) {
        model.addAttribute("schoolIdStr", invoiceListCondition.getSchoolIdStr());
        String[] schoolIdArray = invoiceListCondition.getSchoolIdStr().split(",");
        Date date = new Date();
        String tempKey = date.getTime() + "";
        int len = schoolIdArray.length;
        for (int i = 0; i < len; i++) {
            CommissionInvoiceSeq commissionInvoiceSeq = new CommissionInvoiceSeq();
            commissionInvoiceSeq.setCreateTime(date);
            commissionInvoiceSeq.setSchoolId(Integer.valueOf(schoolIdArray[i]));
            commissionInvoiceSeq.setNumber(i + 1);
            commissionInvoiceSeq.setTempKey(tempKey);
            commissionInvoiceSeqService.insert(commissionInvoiceSeq);
        }
        model.addAttribute("tempKey", tempKey);
        return "/invoice/add";
    }

    @RequestMapping("/edit")
    public String editPage(String invoiceNo, Model model) {
        List<CommissionBankAccount> commissionBankAccountList = bankAccountService.getList(new CommissionBankAccount());
        model.addAttribute("bankAccountList", commissionBankAccountList);
        model.addAttribute("invoiceNo", invoiceNo);
        List<CountryInfo> countryInfoList = countryService.getList(new CountryInfo());
        List<CountryInfo> countryInfoNew = new ArrayList<>();
        for (CountryInfo item : countryInfoList) {
            if (item.getCountryBussid() != 100010 && item.getCountryBussid() != 100011) {
                countryInfoNew.add(item);
            }
        }
        model.addAttribute("countryInfoList", countryInfoNew);
        model.addAttribute("countryInfoListStr", JSONObject.toJSONString(countryInfoNew));
        List<CurrencyInfo> currencyInfos = currencyInfoService.getList(new CurrencyInfo());
        model.addAttribute("currencyInfoStr", JSONObject.toJSONString(currencyInfos));
        model.addAttribute("currencyInfos", currencyInfos);
        InvoiceListCondition invoiceListCondition = new InvoiceListCondition();
        invoiceListCondition.setInvoiceNo(invoiceNo);
        List<InvoiceListVO> invoiceListVOS = invoiceService.getList(invoiceListCondition);
        if (!invoiceListVOS.isEmpty()) {
            model.addAttribute("invoice", invoiceListVOS.get(0));
        }
        return "/invoice/edit";
    }

    @RequestMapping("/modify")
    public String modifyPage(InvoiceListCondition invoiceListCondition, Model model) {
        List<InvoiceListVO> invoiceListVOS = invoiceService.getList(invoiceListCondition);
        if (invoiceListVOS != null && invoiceListVOS.size() > 0) {
            model.addAttribute("invoice", invoiceListVOS.get(0));
        }
        List<CurrencyInfo> currencyInfos = currencyInfoService.getList(new CurrencyInfo());
        model.addAttribute("currencyInfos", currencyInfos);
        List<CountryInfo> countryInfoList = countryService.getList(new CountryInfo());
        List<CountryInfo> countryInfoNew = new ArrayList<>();
        for (CountryInfo item : countryInfoList) {
            if (item.getCountryBussid() != 100010 && item.getCountryBussid() != 100011) {
                countryInfoNew.add(item);
            }
        }
        model.addAttribute("countryInfoList", countryInfoNew);
        return "/invoice/modify";
    }

    @RequestMapping("/acknowledge")
    public String acknowledge(InvoiceListVO invoiceListVO, Model model) {
        model.addAttribute("invoiceListVO", invoiceListVO);
        InvoiceListCondition invoiceListCondition = new InvoiceListCondition();
        invoiceListCondition.setInvoiceIds(InvoiceServiceImpl.strToList(invoiceListVO.getInvoiceIdStr()));
        List<InvoiceListVO> invoiceListVOS = invoiceService.getList(invoiceListCondition);
        if (!invoiceListVOS.isEmpty()) {
            model.addAttribute("invoice", invoiceListVOS.get(0));
        }
        List<CurrencyInfo> currencyInfos = currencyInfoService.getList(new CurrencyInfo());
        model.addAttribute("currencyInfos", currencyInfos);
        model.addAttribute("currencyInfoStr", JSONObject.toJSONString(currencyInfos));
        List<CommissionBankAccount> commissionBankAccountList = bankAccountService.getList(new CommissionBankAccount());
        CommissionBalance commissionBalance = new CommissionBalance();
        List<CommissionBalance> commissionBalanceList = commissionBalanceMapper.select(commissionBalance);
        model.addAttribute("commissionBalanceList", commissionBalanceList);
        model.addAttribute("commissionBalanceListStr", JSONObject.toJSONString(commissionBalanceList));
        model.addAttribute("bankAccountList", commissionBankAccountList);
        model.addAttribute("bankAccountListStr", JSONObject.toJSONString(commissionBankAccountList));
        return "/invoice/acknowledge";
    }

    @RequestMapping("/acknowledgeEdit")
    public String acknowledgeEditPage(InvoiceListCondition invoiceListCondition, Model model) {
        List<InvoiceListVO> invoiceListVOS = invoiceService.getList(invoiceListCondition);
        if (invoiceListVOS != null && invoiceListVOS.size() > 0) {
            model.addAttribute("invoice", invoiceListVOS.get(0));
        }
        List<CurrencyInfo> currencyInfos = currencyInfoService.getList(new CurrencyInfo());
        model.addAttribute("currencyInfos", currencyInfos);
        List<CountryInfo> countryInfoList = countryService.getList(new CountryInfo());
        List<CountryInfo> countryInfoNew = new ArrayList<>();
        for (CountryInfo item : countryInfoList) {
            if (item.getCountryBussid() != 100010 && item.getCountryBussid() != 100011) {
                countryInfoNew.add(item);
            }
        }
        model.addAttribute("countryInfoList", countryInfoNew);
        model.addAttribute("schoolIdStr", invoiceListCondition.getSchoolIdStr());
        List<CommissionBankAccount> commissionBankAccountList = bankAccountService.getList(new CommissionBankAccount());
        model.addAttribute("bankAccountList", commissionBankAccountList);
        return "/invoice/acknowledgeEdit";
    }

    @RequestMapping("/list/data")
    @ResponseBody
    public BasePageModel listData(InvoiceListCondition invoiceListCondition, PageParam pageParam, BasePageModel basePageModel) {

        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength());
        invoiceListCondition.setOperatorType(1);
        invoiceService.getList(invoiceListCondition);
        return dataTableWapper(page, basePageModel);
    }

    @RequestMapping("/add/data")
    @ResponseBody
    public BasePageModel addData(InvoiceListCondition invoiceListCondition, PageParam pageParam, BasePageModel basePageModel) {

        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength());
        if (StringUtils.hasText(invoiceListCondition.getSchoolIdStr())) {
            invoiceListCondition.setSchoolIds(InvoiceServiceImpl.strToList(invoiceListCondition.getSchoolIdStr()));
        }
        List<InvoiceListVO> invoiceListVOS = invoiceService.getListByAddInvoice(invoiceListCondition);

        return dataTableWapper(page, basePageModel);
    }

    @RequestMapping("/edit/data")
    @ResponseBody
    public BasePageModel editData(InvoiceListCondition invoiceListCondition, PageParam pageParam, BasePageModel basePageModel, HttpSession session) {

        InvoiceListVO invoiceListVO = invoiceService.getListSum(invoiceListCondition);

        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength());

        if (StringUtils.hasText(invoiceListCondition.getSchoolIdStr())) {
            invoiceListCondition.setSchoolIds(InvoiceServiceImpl.strToList(invoiceListCondition.getSchoolIdStr()));
        }

        List<InvoiceListVO> invoiceListVOS = invoiceService.getList(invoiceListCondition);
//        invoiceListVOS.add(0, invoiceListVO);

        session.setAttribute(INVOICE_LIST_CONDITION, invoiceListCondition);
        return dataTableWapper(page, basePageModel);
    }

    /**
     * 查询Invoice中某些项的总额
     * 需在列表加载完成时调用，以保证Session中获取的查询条件是最新的条件
     *
     * @param session
     * @return
     */
    @RequestMapping("/list/sum")
    @ResponseBody
    public InvoiceListVO getSum(HttpSession session) {

        InvoiceListCondition invoiceListCondition = (InvoiceListCondition) session.getAttribute(INVOICE_LIST_CONDITION);

        InvoiceListVO invoiceListVO = invoiceService.getListSum(invoiceListCondition);

        return invoiceListVO;
    }

    @RequestMapping("/save")
    @ResponseBody
    public BaseResponse save(InvoiceListVO invoiceListVO, Integer type){
        try {
            return invoiceService.save(invoiceListVO, type);
        }catch(Exception e){
            logger.error(e.getMessage());
            BaseResponse response = new BaseResponse();
            response.setResult(false);
            response.setErrorMsg("系统异常！");
            return response;
        }
    }

    @RequestMapping("/remove")
    @ResponseBody
    public BaseResponse remove(Integer id) {
        return invoiceService.remove(id);
    }

    /**
     * 检查院校是否可以添加到Invoice
     *
     * @param invoiceListCondition
     * @return
     */
    @RequestMapping("/checkSchool")
    @ResponseBody
    public BaseResponse checkSchool(InvoiceListCondition invoiceListCondition) {
        BaseResponse response = invoiceService.checkSchool(invoiceListCondition);
        return response;
    }

    /**
     * 编辑Invoice备注
     *
     * @return
     */
    @RequestMapping("/updateInvoiceRemarkByInvoiceId" )
    @ResponseBody
    public boolean updateInvoiceRemarkByInvoiceId(String invoiceId, String schoolRemark) {
        return this.invoiceService.updateInvoiceRemarkByInvoiceId(invoiceId, schoolRemark);
    }


}
