package com.aoji.service;

import com.aoji.model.CurrencyInfo;
import com.aoji.vo.ToAccountListVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CurrencyInfoService {

    /**
     * 查询货币列表
     * @param currencyInfo
     * @return
     */
    List<CurrencyInfo> getList(CurrencyInfo currencyInfo);

    /**
     * 币种列表
     * @param currencyInfo
     * @return
     */
    Map<String, String> getCurrencyMap(CurrencyInfo currencyInfo);

    Boolean updateCurrencyInfoByExchangeRate(CurrencyInfo currencyInfo);

    /**
     * 查询所有币种的汇率
     * @return
     */
    Map<String, BigDecimal> getCurrencyExchangeRate();

    List<ToAccountListVO> getFinanceReturnCommissionList(ToAccountListVO toAccountListVO);

    List<ToAccountListVO> getReturnCommissionFrequencyListByStudentNo(String studentNo);

    void exportFinanceReturnCommissionList(HttpServletRequest request, HttpServletResponse response);

    boolean financeReturnUpdateChannelReturnStatus(String channCommIds, String resultComment, String name);
}
