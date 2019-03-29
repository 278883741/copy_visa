package com.aoji.controller;

import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.ChannelReturnStatusEnum;
import com.aoji.contants.Contants;
import com.aoji.model.*;
import com.aoji.service.ChannelCommissionService;
import com.aoji.service.ChannelStudentService;
import com.aoji.service.CurrencyInfoService;
import com.aoji.vo.ToAccountListVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * author: chenhaibo
 * description: 渠道返佣管理
 * date: 2018/9/14
 */
@Controller
@RequestMapping("/commission")
public class ChannelCommissionController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ChannelCommissionController.class);

    @Autowired
    CurrencyInfoService currencyInfoService;
    @Autowired
    ChannelCommissionService channelCommissionService;

    @Value("${getAgentByUser.url}")
    private String getAgentByUserUrl;

    @Autowired
    ChannelStudentController channelStudentController;

    @Autowired
    ChannelStudentService channelStudentService;

    @Autowired
    SwaggerController swaggerController;

    /**
     * 佣金到账列表页
     * @param model
     * @return
     */
    @RequestMapping("/toAccount/list")
    public String toAccoutListPage(Model model) {
        List<CurrencyInfo> currencyInfos = currencyInfoService.getList(new CurrencyInfo());
        model.addAttribute("currencyInfos", currencyInfos);
        model.addAttribute("channelReturnStatusList", ChannelReturnStatusEnum.values());
        return "/channelCommission/toAccountList";
    }

    /**
     * 佣金到账列表数据
     * @param toAccountListVO
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping("/toAccount/listData")
    @ResponseBody
    public BasePageModel toAccoutListData(ToAccountListVO toAccountListVO, PageParam pageParam, BasePageModel basePageModel, HttpSession session) {
        // 参数处理
        this.check(toAccountListVO);
        Subject currentUser= SecurityUtils.getSubject();
        if(currentUser.isPermitted(Contants.TO_ACCOUNT_LIST_CONFIRMED_PERMISSION)){ // 已确认
            List<String> status = new ArrayList<>();
            status.add(ChannelReturnStatusEnum.CONFIRMED.getName());
            status.add(ChannelReturnStatusEnum.IDENTIFIED.getName());
            status.add(ChannelReturnStatusEnum.PAID.getName());
            toAccountListVO.setChannelReturnStatusIdentified(status);
//            toAccountListVO.setChannelReturnStatus(ChannelReturnStatusEnum.CONFIRMED.getName());
        }else{ // 默认查询佣金组未确认
//            toAccountListVO.setChannelReturnStatus(null);
        }

        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength());
        session.setAttribute(Contants.TO_ACCOUNT_LIST_CONDITION_SESSION_KEY, toAccountListVO);
        List<ToAccountListVO> toAccountListVOList = channelCommissionService.getToAccountList(toAccountListVO);
        basePageModel.setAaData(toAccountListVOList);
        return dataTableWapper(page, basePageModel);
    }

    /**
     * 参数校验
     * @param toAccountListVO
     */
    private static void check(ToAccountListVO toAccountListVO){
        toAccountListVO.setStudentNo(StringUtils.isBlank(toAccountListVO.getStudentNo())? null: toAccountListVO.getStudentNo());
        toAccountListVO.setStudentName(StringUtils.isBlank(toAccountListVO.getStudentName())? null: toAccountListVO.getStudentName());
        toAccountListVO.setSpelling(StringUtils.isBlank(toAccountListVO.getSpelling())? null: toAccountListVO.getSpelling());
        toAccountListVO.setAgentName(StringUtils.isBlank(toAccountListVO.getAgentName())? null: toAccountListVO.getAgentName());
        toAccountListVO.setInvoiceStatus(StringUtils.isBlank(toAccountListVO.getInvoiceStatus())? null: toAccountListVO.getInvoiceStatus());
        toAccountListVO.setReturnDate(toAccountListVO.getReturnDateCondition());
    }

    /**
     * 修改渠道返佣状态
     * @param invoiceIds
     * @param type
     * @param session
     * @return
     */
    @RequestMapping("/toAccount/updateChannelReturnStatus")
    @ResponseBody
    public BaseResponse updateChannelReturnStatus(String invoiceIds, String type, HttpSession session) {
        BaseResponse response = new BaseResponse();
        ChannelReturnStatusEnum channelReturnStatusEnum = null;
        if(ChannelReturnStatusEnum.CONFIRMED.getName().equals(type)){
            channelReturnStatusEnum = ChannelReturnStatusEnum.CONFIRMED;
        } else if (ChannelReturnStatusEnum.IDENTIFIED.getName().equals(type)){
            channelReturnStatusEnum = ChannelReturnStatusEnum.IDENTIFIED;
        }
        if(StringUtils.isBlank(invoiceIds) || channelReturnStatusEnum == null){
            response.setResult(false);
            response.setErrorMsg("参数错误！");
            return response;
        }
        List<ToAccountListVO> toAccountListVOList = (List<ToAccountListVO>)session.getAttribute(Contants.TO_ACCOUNT_LIST_SESSION_KEY);
        try {
            boolean result = channelCommissionService.updateChannelReturnStatus(invoiceIds, toAccountListVOList, channelReturnStatusEnum);
            response.setResult(result);
        } catch (Exception e){
            logger.error("渠道返佣状态修改失败！"+e.getMessage(), e);
            response.setResult(false);
            response.setErrorMsg(e.getMessage());
        }
        return response;
    }

    /**
     * 代理学生列表页
     * @param model
     * @return
     */
    @RequestMapping("/agentStudent/list")
    public String agentStudentList(Model model, String agentName, Integer agentId) {
        List<CurrencyInfo> currencyInfos = currencyInfoService.getList(new CurrencyInfo());
        model.addAttribute("currencyInfos", currencyInfos);
        model.addAttribute("agentName", agentName);
        model.addAttribute("agentId", agentId);
        return "/channelCommission/agentStudentList";
    }

    /**
     * 代理学生列表数据
     * @param toAccountListVO
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping("/agentStudent/listData")
    @ResponseBody
    public BasePageModel agentStudentListData(ToAccountListVO toAccountListVO, PageParam pageParam, BasePageModel basePageModel, HttpSession session) {
        this.check(toAccountListVO);
        List<String>  list = new ArrayList<>();
        list.add(ChannelReturnStatusEnum.CONFIRMED.getName());
        list.add(ChannelReturnStatusEnum.IDENTIFIED.getName());
        list.add(ChannelReturnStatusEnum.PAID.getName());
        toAccountListVO.setChannelReturnStatusIdentified(list);
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength());
        session.setAttribute(Contants.AGENT_STUDENT_LIST_CONDITION_SESSION_KEY, toAccountListVO);
        List<ToAccountListVO> toAccountListVOList = channelCommissionService.getAgentStudentList(toAccountListVO);
        basePageModel.setAaData(toAccountListVOList);
        session.setAttribute(Contants.TO_ACCOUNT_LIST_SESSION_KEY, toAccountListVOList);
        return dataTableWapper(page, basePageModel);
    }

    /**
     * 跳转到财务返佣金列表
     *
     * @param model
     * @return
     */
    @RequestMapping("/financeReturnCommission/list")
    public String financeReturnCommissionList(Model model) {
        List<CurrencyInfo> currencyInfos = currencyInfoService.getList(new CurrencyInfo());
        model.addAttribute("currencyInfos", currencyInfos);
        return "/channelCommission/financeReturnCommissionList";
    }

    /**
     * 查询财务返佣金列表数据
     *
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping("/financeReturnCommission/listData")
    @ResponseBody
    public BasePageModel financeReturnCommissionListData(HttpServletRequest request,ToAccountListVO toAccountListVO,PageParam pageParam, BasePageModel basePageModel) {
        Page<?> page = pageWapper(pageParam, propList());
        request.getSession().removeAttribute(Contants.FINANCE_RETURN_COMMISSION_LIST_KEY);
        Gson gson = new Gson();
        String toJson = gson.toJson(toAccountListVO);
        ToAccountListVO toAccountListVOs = gson.fromJson(toJson, ToAccountListVO.class);
        request.getSession().setAttribute(Contants.FINANCE_RETURN_COMMISSION_LIST_KEY,toAccountListVOs);
        List<ToAccountListVO> financeReturnCommissionList = currencyInfoService.getFinanceReturnCommissionList(toAccountListVO);
        basePageModel.setAaData(financeReturnCommissionList);
        return dataTableWapper(page, basePageModel);
    }

    /**
     * 根据代理Id及签约时间查询学生的合同信息
     * @param agentId
     * @param signDate
     * @return
     */
    @RequestMapping(value = "/selectContractNameByAgentIdAndSignDate",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject selectContractNameByAgentIdAndSignDate(Integer agentId, String signDate){
        JSONObject jsonObject = new JSONObject();
        try {
            if(!org.springframework.util.StringUtils.hasText(signDate)){
                jsonObject.put("result",false);
                jsonObject.put("errorMsg","参数签约日期错误！signDate:"+signDate);
                return jsonObject;
            }
            if(agentId==null || "".equals(agentId) ){
                jsonObject.put("result",false);
                jsonObject.put("errorMsg","参数代理Id错误！agentId:"+agentId);
                return jsonObject;
            }
            ChannelAgentInfo agentInfoByAgentId = channelCommissionService.getAgentInfoByAgentId(agentId, signDate);
            jsonObject.put("result",true);
            jsonObject.put("successMsg","查询获签人数成功");
            jsonObject.put("data",agentInfoByAgentId);
        } catch (Exception e) {
            e.printStackTrace();
            jsonObject.put("result",false);
            jsonObject.put("errorMsg","操作失败");
        }
        return jsonObject;
    }



    /**
     * 跳转到汇率页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/exchangeRate/list")
    public String exchangeRateList(Model model) {
        return "/channelCommission/exchangeRate";
    }

    /**
     * 查询财务返佣金列表数据
     *
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping("/exchangeRate/listData")
    @ResponseBody
    public BasePageModel exchangeRateListData(CurrencyInfo currencyInfo, PageParam pageParam, BasePageModel basePageModel) {
        Page<?> page = pageWapper(pageParam, propList());
        List<CurrencyInfo> currencyInfoList = currencyInfoService.getList(new CurrencyInfo());
        return dataTableWapper(page, basePageModel);
    }


    /**
     * 跳转到返佣次数页面
     * @param studentNo
     * @return
     */
    @RequestMapping(value = "/returnCommissionFrequency/list",method = RequestMethod.GET)
    public String edit(String studentNo,Model model){
        if(!org.springframework.util.StringUtils.hasText(studentNo)){
            return null;
        }
        model.addAttribute("studentNo",studentNo);
        return "/channelCommission/returnCommissionFrequency";
    }

    /**
     * 查询返佣次数数据
     * @param studentNo
     * @return
     */
    @RequestMapping(value = "/returnCommissionFrequency/listData",method = RequestMethod.POST)
    @ResponseBody
    public BasePageModel returnCommissionFrequency(String studentNo,PageParam pageParam, BasePageModel basePageModel){
        if(!org.springframework.util.StringUtils.hasText(studentNo)){
            return null;
        }
        Page<?> page = pageWapper(pageParam, propList());
        List<ToAccountListVO> returnCommissionFrequencyList = currencyInfoService.getReturnCommissionFrequencyListByStudentNo(studentNo);
        basePageModel.setAaData(returnCommissionFrequencyList);
        return dataTableWapper(page, basePageModel);
    }

    /**
     * 导出财务返佣金数据
     * @param request
     * @param response
     */
    @RequestMapping("/toExcel")
    public void exportFinanceReturnCommissionList(HttpServletRequest request, HttpServletResponse response){
        currencyInfoService.exportFinanceReturnCommissionList(request, response);
    }


    /**
     * 修改汇率
     *
     * @param currencyInfo
     * @return
     */
    @RequestMapping("/updateCurrencyInfoByExchangeRate")
    @ResponseBody
    public BaseResponse updateCurrencyInfoByExchangeRate(CurrencyInfo currencyInfo) {
        BaseResponse response = new BaseResponse();
        if(currencyInfo.getId()==null || "".equals(currencyInfo.getId())){
            response.setResult(false);
            response.setErrorMsg("参数Id错误！id:"+currencyInfo.getId());
            return response;
        }
        if(currencyInfo.getExchangeRate()==null || "".equals(currencyInfo.getExchangeRate())){
            response.setResult(false);
            response.setErrorMsg("参数汇率错误！exchangeRate:"+currencyInfo.getExchangeRate());
            return response;
        }
        try {
            boolean result =  currencyInfoService.updateCurrencyInfoByExchangeRate(currencyInfo);
            response.setResult(result);
        } catch (Exception e){
            logger.error("汇率修改失败！"+e.getMessage(), e);
            response.setResult(false);
            response.setErrorMsg("操作失败");
        }
        return response;

    }


    /**
     * 修改渠道返佣状态
     * @param channCommIds
     * @return
     */
    @RequestMapping("/financeReturnCommission/updateChannelReturnStatus")
    @ResponseBody
    public BaseResponse updateChannelReturnStatus(String channCommIds,String resultComment) {
        BaseResponse response = new BaseResponse();
        if(StringUtils.isBlank(channCommIds)){
            response.setResult(false);
            response.setErrorMsg("参数错误！");
            return response;
        }
        ChannelReturnStatusEnum channelReturnStatusEnum = null;
        try {
            boolean result = currencyInfoService.financeReturnUpdateChannelReturnStatus(channCommIds,resultComment, ChannelReturnStatusEnum.PAID.getName());
            response.setResult(result);
        } catch (Exception e){
            logger.error("财务返佣状态修改失败！"+e.getMessage(), e);
            response.setResult(false);
            response.setErrorMsg("操作失败");
        }
        return response;
    }



    /**
     * 排序列表
     *
     * @return
     */
    public String[] propList() {
        return new String[]{""};
    }

}
