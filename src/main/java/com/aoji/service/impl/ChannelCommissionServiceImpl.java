package com.aoji.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.ChannelReturnStatusEnum;
import com.aoji.contants.Contants;
import com.aoji.controller.ChannelStudentController;
import com.aoji.controller.SwaggerController;
import com.aoji.mapper.ChannelCommissionMapper;
import com.aoji.mapper.CommissionInvoiceMapper;
import com.aoji.mapper.CommissionStudentMapper;
import com.aoji.model.*;
import com.aoji.model.req.AgentItemReq;
import com.aoji.model.req.AgentReq;
import com.aoji.service.*;
import com.aoji.utils.DateUtil;
import com.aoji.utils.HttpUtils;
import com.aoji.vo.ToAccountListVO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ChannelCommissionServiceImpl implements ChannelCommissionService{

    private static final Logger logger = LoggerFactory.getLogger(ChannelCommissionServiceImpl.class);

    @Autowired
    CommissionInvoiceMapper commissionInvoiceMapper;
    @Autowired
    UserService userService;
    @Autowired
    ChannelCommissionMapper channelCommissionMapper;
    @Autowired
    CurrencyInfoService currencyInfoService;

    @Autowired
    VisaRecordService visaRecordService;

    @Value("${getAgentByUser.url}")
    private String getAgentByUserUrl;

    @Autowired
    ChannelStudentController channelStudentController;

    @Autowired
    ChannelStudentService channelStudentService;

    @Autowired
    SwaggerController swaggerController;

    @Autowired
    CommissionStudentMapper commissionStudentMapper;

    @Override
    public List<ToAccountListVO> getToAccountList(ToAccountListVO toAccountListVO) {
        List<ToAccountListVO> toAccountListVOList = commissionInvoiceMapper.getToAccountList(toAccountListVO);
        Subject currentUser= SecurityUtils.getSubject();
        for(ToAccountListVO toAccountListVO1 : toAccountListVOList){
            if(currentUser.isPermitted(Contants.AGENT_STUDENT_LIST_PERMISSION)){
                toAccountListVO1.setToAgentStudentListFlag(true);
            }
        }
        return toAccountListVOList;
    }

    @Override
    public List<ToAccountListVO> getAgentStudentList(ToAccountListVO toAccountListVO) {
        Integer agentId = toAccountListVO.getAgentId();
        if(agentId == null){
            logger.error("查询代理学生列表错误！ 代理Id为空");
            return null;
        }
        // 获取代理信息
        JSONObject agentInfo = this.getAgentInfoByagentId(agentId);
        if(agentInfo == null){
            logger.error("代理信息获取失败！ 代理Id："+agentId);
            return null;
        }
        List<ToAccountListVO> toAccountListVOList = commissionInvoiceMapper.getToAccountList(toAccountListVO);
        // 计算
        for(ToAccountListVO toAccountListVO1 : toAccountListVOList){
            // 汇率
            toAccountListVO1.setExchangeRate(currencyInfoService.getCurrencyExchangeRate().get(toAccountListVO1.getAccountCurrency()));
            // 整合代理信息
            ChannelAgentInfo channelAgentInfo = this.convert(agentInfo, toAccountListVO1.getAgentId(), toAccountListVO1.getSignDate());
            if(channelAgentInfo != null) {
                //获签人数
                toAccountListVO1.setGetVisaSum(channelAgentInfo.getGetVisaSum());
                //本次返佣比例
                toAccountListVO1.setPercentNum(channelAgentInfo.getPercentNum());
                toAccountListVO1.setContractName(channelAgentInfo.getContractName());
                toAccountListVO1.setContractUrl(channelAgentInfo.getContractPic());
                if(toAccountListVO1.getReturnMoney()==null){
                   toAccountListVO1.setReturnMoney(BigDecimal.valueOf(0));
                }
                if (toAccountListVO1.getPercentNum() != null) {
                    // 本次应返金额  (账户金额 * 本次返佣比例-累计已返金额)
                    toAccountListVO1.setThisReturnMoney(toAccountListVO1.getAccountMoney()
                            .multiply(BigDecimal.valueOf(toAccountListVO1.getPercentNum()))
                            .divide(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP)
                            .subtract(toAccountListVO1.getReturnMoney())
                    );
                    // 应返人民币 (本次应返金额 * 费率)
                    if(toAccountListVO1.getExchangeRate() != null) {
                        toAccountListVO1.setReturnMoneyCny(toAccountListVO1.getThisReturnMoney().multiply(toAccountListVO1.getExchangeRate()));
                    }
                }
            }
            if(toAccountListVO1.getReturnMoney()==null){
                toAccountListVO1.setReturnMoney(BigDecimal.valueOf(0));
            }
            if(toAccountListVO1.getThisReturnMoney()==null){
                toAccountListVO1.setThisReturnMoney(BigDecimal.valueOf(0));
            }
            if(toAccountListVO1.getReturnMoneyCny()==null){
                toAccountListVO1.setReturnMoneyCny(BigDecimal.valueOf(0));
            }
        }
        return toAccountListVOList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateChannelReturnStatus(String invoiceIds, List<ToAccountListVO> toAccountListVOList, ChannelReturnStatusEnum type) {
        String[] invoiceIdArray = invoiceIds.split(",");
        // 修改返佣状态
        for(String invoiceId : invoiceIdArray){
            CommissionInvoice commissionInvoice = new CommissionInvoice();
            commissionInvoice.setId(Integer.valueOf(invoiceId));
            commissionInvoice.setChannelReturnStatus(type.getName());
            commissionInvoiceMapper.updateByPrimaryKeySelective(commissionInvoice);
        }
        // 标识
        if(ChannelReturnStatusEnum.IDENTIFIED.equals(type)){
            SysUser sysUser = userService.getLoginUser();
            for(ToAccountListVO toAccountListVO : toAccountListVOList){
                for(String invoiceId : invoiceIdArray){
                    if(toAccountListVO.getId().toString().equals(invoiceId)){
                        ChannelCommission channelCommission = new ChannelCommission();
                        channelCommission.setCreateTime(new Date());
                        channelCommission.setChannelReturnRate(toAccountListVO.getPercentNum());
                        channelCommission.setDeleteStatus(false);
                        channelCommission.setExchangeRate(toAccountListVO.getExchangeRate());
                        channelCommission.setGetVisaSum(toAccountListVO.getGetVisaSum());
                        channelCommission.setInvoiceId(toAccountListVO.getId());
                        channelCommission.setOperatorNo(sysUser.getOaid());
                        channelCommission.setOperatorName(sysUser.getUsername());
                        //累计已返金额
                        channelCommission.setReturnMoney(toAccountListVO.getThisReturnMoney().add(toAccountListVO.getReturnMoney()));
                        //本次应返金额
                        channelCommission.setThisReturnMoney(toAccountListVO.getThisReturnMoney());
                        channelCommission.setReturnMoneyCny(toAccountListVO.getReturnMoneyCny());
                        channelCommission.setStudentId(toAccountListVO.getStudentId());
                        channelCommissionMapper.insert(channelCommission);
                    }
                }
            }
        }
        return true;
    }

    /**
     * 1.根据代理Id及学生签约时间，从小希平台获取代理信息以及合同信息
     *2. 根据合同开始和结束时间从文签系统查询该代理当前的获签学生数量
     * @param agentId 代理Id
     * @param signDate 签约日期
     * @return
     */
    @Override
    public ChannelAgentInfo getAgentInfoByAgentId(Integer agentId,String signDate) throws  Exception{
        ChannelAgentInfo channelAgentInfo = new ChannelAgentInfo();
        if(org.springframework.util.StringUtils.isEmpty(agentId)) {
            logger.info("入参agentId为空"+agentId);
            return  null;
        }
        String url = String.format(getAgentByUserUrl, agentId);
        logger.info("调用获取代理信息接口url: " + url);
        String json = HttpUtils.doGet(url);
        logger.info("调用获取代理信息接口返回数据: " + json);
        if (org.springframework.util.StringUtils.isEmpty(json)) {
            logger.info("调用获取代理信息接口返回数据为空："+json);
            return null;
        }
        JSONObject jsonObject = JSONObject.parseObject(json);
        if (!jsonObject.getInteger("code").equals(200)) {
            logger.info("调用获取代理信息接口返回数据code码不为200");
            return  null;
        }
        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        if (jsonObject1 == null || !jsonObject1.containsKey("agentSubs") || jsonObject1.get("agentSubs") == null) {
            logger.info("调用获取代理信息接口返回数据（主体合同或者子合同为空）");
            return  null;
        }
        channelAgentInfo.setAgentId(agentId);
        SsoAgentInfoModel ssoAgentInfoModel = jsonObject.getObject("data", SsoAgentInfoModel.class);
        Integer countByAgentId =null;
        for (SsoAgentSubsModel ssoAgentSubsModel : ssoAgentInfoModel.getAgentSubs()) {
            if (signDate != null && DateUtil.compareTo(ssoAgentSubsModel.getEndTime(), signDate ) && DateUtil.compareTo(signDate, ssoAgentSubsModel.getStartTime())) {
                channelAgentInfo.setContractName(ssoAgentSubsModel.getContractName());
                channelAgentInfo.setContractPic(ssoAgentSubsModel.getContractPic());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    Date startTime = simpleDateFormat.parse(ssoAgentSubsModel.getStartTime().replace("/","-"));
                    Date endTime = simpleDateFormat.parse(ssoAgentSubsModel.getEndTime().replace("/","-"));
                    if(countByAgentId==null){
                        countByAgentId = visaRecordService.getCountByAgentId(agentId, startTime, endTime);
                        logger.info("查询获签人数 countByAgentId：" + countByAgentId);
                        channelAgentInfo.setGetVisaSum(countByAgentId);
                    }
                    if(ssoAgentSubsModel.getMinNum()<countByAgentId&&countByAgentId<=ssoAgentSubsModel.getMaxNum()){
                       channelAgentInfo.setPercentNum(ssoAgentSubsModel.getPercentNum());
                       break;
                    }
            }
        }
        return channelAgentInfo;
    }
    public JSONObject getAgentInfoByagentId(Integer agentId){
        if(agentId == null) {
            logger.info("入参agentId为空"+agentId);
            return  null;
        }
        String url = String.format(getAgentByUserUrl, agentId);
        logger.info("调用获取代理信息接口url: " + url);
        String json = HttpUtils.doGet(url);
        logger.info("调用获取代理信息接口返回数据: " + json);
        if (org.springframework.util.StringUtils.isEmpty(json)) {
            logger.info("调用获取代理信息接口返回数据为空："+json);
            return null;
        }
        return JSONObject.parseObject(json);
    }

    /**
     * 从代理信息中根据学号获取费率
     * @param jsonObject 代理信息
     * @param signDate 学生签约时间
     * @return
     */
    public ChannelAgentInfo convert(JSONObject jsonObject, Integer agentId, Date signDate){

        AgentReq agentReq = new AgentReq();
        AgentItemReq agentItemReq;
        ChannelAgentInfo channelAgentInfo = new ChannelAgentInfo();

        if (!jsonObject.getInteger("code").equals(200)) {
            logger.info("调用获取代理信息接口返回数据code码不为200");
            return  null;
        }
        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        if (jsonObject1 == null || !jsonObject1.containsKey("agentSubs") || jsonObject1.get("agentSubs") == null) {
            logger.info("调用获取代理信息接口返回数据（主体合同或者子合同为空）");
            return  null;
        }
        channelAgentInfo.setAgentId(agentId);
        SsoAgentInfoModel ssoAgentInfoModel = jsonObject.getObject("data", SsoAgentInfoModel.class);
        Integer countByAgentId =null;
        for (SsoAgentSubsModel ssoAgentSubsModel : ssoAgentInfoModel.getAgentSubs()) {
            if (signDate != null && DateUtil.compareTo(ssoAgentSubsModel.getEndTime(), signDate ) && DateUtil.compareTo(signDate, ssoAgentSubsModel.getStartTime())) {
                channelAgentInfo.setContractName(ssoAgentSubsModel.getContractName());
                channelAgentInfo.setContractPic(ssoAgentSubsModel.getContractPic());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date startTime = simpleDateFormat.parse(ssoAgentSubsModel.getStartTime().replace("/","-"));
                    Date endTime = simpleDateFormat.parse(ssoAgentSubsModel.getEndTime().replace("/","-"));
                    if(countByAgentId==null){
                        countByAgentId = visaRecordService.getCountByAgentId(agentId, startTime, endTime);
                        logger.info("查询获签人数 countByAgentId：" + countByAgentId);
                        channelAgentInfo.setGetVisaSum(countByAgentId);
                    }
                    if(ssoAgentSubsModel.getMinNum()<countByAgentId&&countByAgentId<=ssoAgentSubsModel.getMaxNum()){
                        channelAgentInfo.setPercentNum(ssoAgentSubsModel.getPercentNum());
                        break;
                    }
                }catch(Exception e){
                    logger.error("查询获取人数失败! error:"+ e.getMessage(), e);
                }
            }
        }
        return channelAgentInfo;
    }

    /**
     * 返佣到账列表导出
     * @param request
     * @param response
     */
    @Override
    public void toAccountListToExcel(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 文件名默认设置为当前时间：年月日时分秒
            String fileName = org.apache.commons.lang.time.DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
            // 设置文件名称
            String sheetName ="渠道佣金到账列表";
            String title = sheetName;
            // 设置response头信息
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + title + fileName + ".xls");

            // 创建工作簿并发送到浏览器
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            OutputStream os = response.getOutputStream();
            List<ToAccountListVO> toAccountListVOList = this.convert(request, Contants.TO_ACCOUNT_LIST_CONDITION_SESSION_KEY);
            String[] rowsName = new String[]{
                    "澳际学号","学生名称","获签国家","出生日期","代理名称","账户币种","账户金额","到款时间","获签时间",
                    "付款时间","返佣状态","学校名称","专业名称","课程名称","开课时间"};

            List<Object[]>  dataList = new ArrayList<Object[]>();
            Object[] objs = null;
            for (int i = 0; i < toAccountListVOList.size(); i++) {
                ToAccountListVO toAccountListVO = toAccountListVOList.get(i);
                objs = new Object[rowsName.length];
                objs[0] = toAccountListVO.getStudentNo();
                objs[1] = toAccountListVO.getStudentName();
                objs[2] = toAccountListVO.getNationName();
                objs[3] = toAccountListVO.getBirthday() == null ? null : toAccountListVO.getBirthday().getTime();
                objs[4] = toAccountListVO.getAgentName();
                objs[5] = toAccountListVO.getAccountCurrency();
                objs[6] = toAccountListVO.getAccountMoney();
                objs[7] = toAccountListVO.getReturnDate() == null ? null : toAccountListVO.getReturnDate().getTime();
                objs[8] = toAccountListVO.getVisaDate() == null ? null : toAccountListVO.getVisaDate().getTime();
                objs[9] = toAccountListVO.getPaymentDate() == null ? null : toAccountListVO.getPaymentDate().getTime();
                objs[10] = toAccountListVO.getChannelReturnStatus();
                objs[11] = toAccountListVO.getSchoolName();
                objs[12] = toAccountListVO.getMajorName();
                objs[13] = toAccountListVO.getCourseName();
                objs[14] = toAccountListVO.getStartDate() == null ? null : toAccountListVO.getStartDate().getTime();
                dataList.add(objs);
            }
            ExcelServiceImpl ex = new ExcelServiceImpl(title, rowsName, dataList,response);
            ex.export();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 代理返佣列表
     * @param request
     * @param response
     */
    @Override
    public void agentStudentListToExcel(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 文件名默认设置为当前时间：年月日时分秒
            String fileName = org.apache.commons.lang.time.DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
            // 设置文件名称
            String sheetName ="学生到账列表";
            String title = sheetName;
            // 设置response头信息
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + title + fileName + ".xls");

            // 创建工作簿并发送到浏览器
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            OutputStream os = response.getOutputStream();
            List<ToAccountListVO> toAccountListVOList = this.convert(request, Contants.AGENT_STUDENT_LIST_CONDITION_SESSION_KEY);
            String[] rowsName = new String[]{
                    "澳际学号", "姓名", "合同名称", "账户金额", "账户币种", "到账时间", "获签总人数","累计已返金额","上次标识时间","上次返佣比例","本次返佣比例","本次应返金额",
                     "汇率", "应返（RMB）金额", "学校名称", "专业名称", "课程名称", "开课时间"};

            List<Object[]>  dataList = new ArrayList<Object[]>();
            Object[] objs = null;
            for (int i = 0; i < toAccountListVOList.size(); i++) {
                ToAccountListVO toAccountListVO = toAccountListVOList.get(i);
                objs = new Object[rowsName.length];
                objs[0] = toAccountListVO.getStudentNo();
                objs[1] = toAccountListVO.getStudentName();
                objs[2] = toAccountListVO.getContractName();
                objs[3] = toAccountListVO.getAccountMoney();
                objs[4] = toAccountListVO.getAccountCurrency();
                objs[5] = toAccountListVO.getReturnDate() == null ? null : toAccountListVO.getReturnDate().getTime();
                objs[6] = toAccountListVO.getGetVisaSum();
                //累计已返金额
                objs[7] = toAccountListVO.getReturnMoney()== null ? null : toAccountListVO.getReturnMoney();
                //上次标识时间
                objs[8] = toAccountListVO.getLastCreatTime()== null ? null : toAccountListVO.getLastCreatTime().getTime();
                // 上次返佣比例
                objs[9] = toAccountListVO.getChannelReturnRate()== null ? null : toAccountListVO.getChannelReturnRate();
                //本次返佣比例
                objs[10] = toAccountListVO.getPercentNum()== null ? null : toAccountListVO.getPercentNum();
                //本次应返金额
                objs[11] = toAccountListVO.getThisReturnMoney()== null ? null : toAccountListVO.getThisReturnMoney();
                objs[12] = toAccountListVO.getExchangeRate();
                objs[13] = toAccountListVO.getReturnMoneyCny();
                objs[14] = toAccountListVO.getSchoolName();
                objs[15] = toAccountListVO.getMajorName();
                objs[16] = toAccountListVO.getCourseName();
                objs[17] = toAccountListVO.getStartDate() == null ? null : toAccountListVO.getStartDate().getTime();
                dataList.add(objs);
            }
            ExcelServiceImpl ex = new ExcelServiceImpl(title, rowsName, dataList,response);
            ex.export();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询需要导出的列表
     * @param request
     * @param sessionKey 查询条件缓存key
     * @return
     */
    public List<ToAccountListVO> convert(HttpServletRequest request, String sessionKey){
        ToAccountListVO toAccountListVO = (ToAccountListVO)request.getSession().getAttribute(sessionKey);
        List<ToAccountListVO> toAccountListVOList = new ArrayList<>();
        if(Contants.AGENT_STUDENT_LIST_CONDITION_SESSION_KEY.equals(sessionKey)){
            toAccountListVOList = this.getAgentStudentList(toAccountListVO);
        }
        if(Contants.TO_ACCOUNT_LIST_CONDITION_SESSION_KEY.equals(sessionKey)){
            toAccountListVOList = this.getToAccountList(toAccountListVO);
        }
        return toAccountListVOList;
    }
}
