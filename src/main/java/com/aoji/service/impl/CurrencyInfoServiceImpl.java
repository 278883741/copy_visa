package com.aoji.service.impl;
import com.aoji.config.util.FinanceModel;
import com.aoji.contants.ChannelReturnStatusEnum;
import com.aoji.contants.Contants;
import com.aoji.mapper.CurrencyInfoMapper;
import com.aoji.model.CurrencyInfo;
import com.aoji.service.ChannelCommissionService;
import com.aoji.service.CurrencyInfoService;
import com.aoji.service.UserService;
import com.aoji.utils.ExcelUtils;
import com.aoji.vo.ToAccountListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

@Service
public class CurrencyInfoServiceImpl implements CurrencyInfoService {
    @Autowired
    CurrencyInfoMapper currencyInfoMapper;
    @Autowired
    UserService userService;

    @Autowired
    ChannelCommissionService channelCommissionService;
    @Override
    public List<CurrencyInfo> getList(CurrencyInfo currencyInfo) {
        return currencyInfoMapper.select(currencyInfo);
    }

    @Override
    public Map<String, String> getCurrencyMap(CurrencyInfo currencyInfo) {
        List<CurrencyInfo> currencyInfos = currencyInfoMapper.select(currencyInfo);
        Map<String, String> map = new HashMap<>();
        if(currencyInfos != null){
            for(CurrencyInfo currencyInfo1 :currencyInfos){
                map.put(currencyInfo1.getId().toString(), currencyInfo1.getEnName());
            }
        }
        return map;
    }

    @Override
    public Boolean updateCurrencyInfoByExchangeRate(CurrencyInfo currencyInfo) {
        currencyInfo.setOperatorNo(userService.getLoginUser().getOaid());
        currencyInfo.setOperatorName(userService.getLoginUser().getUsername());
        currencyInfo.setUpdateTime(new Date());
        return currencyInfoMapper.updateByPrimaryKeySelective(currencyInfo)==1;
    }

    @Override
    public List<ToAccountListVO> getFinanceReturnCommissionList(ToAccountListVO toAccountListVO) {
        //已付款数据不做默认展示，通过筛选条件查，所以页面首次加载展示数据为已标识数据
        if(!StringUtils.hasText(toAccountListVO.getChannelReturnStatus())){
                toAccountListVO.setChannelReturnStatus(ChannelReturnStatusEnum.IDENTIFIED.getName());
        }
        List<ToAccountListVO> financeReturnCommissionList = currencyInfoMapper.getFinanceReturnCommissionList(toAccountListVO);
        if(financeReturnCommissionList.size()<1 || financeReturnCommissionList==null){
            return null;
        }
        for (ToAccountListVO toAccountListVO1 : financeReturnCommissionList) {
            if(toAccountListVO1.getReturnMoney()==null){
                toAccountListVO1.setReturnMoney(BigDecimal.valueOf(0));
            }
            if(toAccountListVO1.getAccountMoney()==null){
                toAccountListVO1.setAccountMoney(BigDecimal.valueOf(0));
            }
            if(toAccountListVO1.getReturnMoneyCny()==null){
                toAccountListVO1.setReturnMoneyCny(BigDecimal.valueOf(0));
            }
            if(toAccountListVO1.getThisReturnMoney()==null){
                toAccountListVO1.setThisReturnMoney(BigDecimal.valueOf(0));
            }
        }
         return financeReturnCommissionList;

    }

    @Override
    public List<ToAccountListVO> getReturnCommissionFrequencyListByStudentNo(String studentNo) {

        return currencyInfoMapper.getReturnCommissionFrequencyListByStudentNo(studentNo,ChannelReturnStatusEnum.PAID.getName());
    }

    /**
     * 导出佣金管理列表信息
     * @param request
     * @param response
     */
    @Override
    public void exportFinanceReturnCommissionList(HttpServletRequest request, HttpServletResponse response) {
        ToAccountListVO toAccountListVO = (ToAccountListVO) request.getSession().getAttribute(Contants.FINANCE_RETURN_COMMISSION_LIST_KEY);
        if(toAccountListVO!=null){
            if(!StringUtils.hasText(toAccountListVO.getChannelReturnStatus())){
                toAccountListVO.setChannelReturnStatus(ChannelReturnStatusEnum.IDENTIFIED.getName());
            }
        }
        int countPage =  currencyInfoMapper.getCountFinanceReturnCommissionListForExcel(toAccountListVO);
        int pageSize = Contants.STUDENT_EXPORT_PAGESIZE;
        List<FinanceModel> studentInfoList = new ArrayList<>();
        for(int i = 0; i < countPage/pageSize+1; i++){
            List<FinanceModel> studentInfos = currencyInfoMapper.getFinanceReturnCommissionListForExcel( toAccountListVO,i*pageSize, pageSize);
            studentInfoList.addAll(studentInfos);
        }
        ExcelUtils.listToExcel(studentInfoList, getStudentFoExcelFiledMap(),"财务返用数据表格", response);
    }

    /**
     * 定义表头列表模板
     *
     * @return
     */
    public LinkedHashMap<String, String> getStudentFoExcelFiledMap() {

        LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();

        fieldMap.put("studentNo", "澳际学号");

        fieldMap.put("studentName", "学生姓名");

        fieldMap.put("agentName", "代理名称");

        fieldMap.put("accountCurrency", "币种");

        fieldMap.put("accountMoney", "到账金额");

        fieldMap.put("channelReturnRate", "返佣百分比");

        fieldMap.put("thisReturnMoney", "本次应返金额");

        fieldMap.put("exchangeRate", "汇率");

        fieldMap.put("returnMoneyCny", "应返（RMB）金额");

        fieldMap.put("paymentDate", "付款日期");
        return fieldMap;
    }



    /**
     * 根据Id修改当前学生的返佣状态
     * @param channCommIds
     * @param resultComment
     * @param name
     * @return
     */
    @Override
    @Transactional
    public boolean financeReturnUpdateChannelReturnStatus(String channCommIds, String resultComment, String name) {

        String[] channCommIdArray = channCommIds.split(",");
        // 修改返佣状态
        for(String channCommId : channCommIdArray){
            ToAccountListVO toAccountListVO = new ToAccountListVO();
            toAccountListVO.setChannelCommId(Integer.valueOf(channCommId));
            toAccountListVO.setChannelReturnStatus(name);
            toAccountListVO.setResultComment(resultComment);
            currencyInfoMapper.updateChannelReturnStatusBychannCommId(toAccountListVO);
        }
        return true;
    }

    @Override
    public Map<String, BigDecimal> getCurrencyExchangeRate() {
        List<CurrencyInfo> currencyInfos = this.getList(new CurrencyInfo());
        Map<String, BigDecimal> map = new HashMap<>();
        for(CurrencyInfo currencyInfo : currencyInfos){
            map.put(currencyInfo.getEnName(), currencyInfo.getExchangeRate());
        }
        return map;
    }
}
