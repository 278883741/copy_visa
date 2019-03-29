package com.aoji.service.impl;

import com.aoji.contants.*;
import com.aoji.mapper.CommissionInvoiceMapper;
import com.aoji.mapper.CommissionInvoiceSeqMapper;
import com.aoji.mapper.CommissionSchoolMapper;
import com.aoji.mapper.CommissionStudentMapper;
import com.aoji.model.*;
import com.aoji.service.CurrencyInfoService;
import com.aoji.service.InvoiceService;
import com.aoji.utils.ExcelUtil;
import com.aoji.vo.CommissionReportNewVO;
import com.aoji.vo.CommissionReportVO;
import com.aoji.vo.InvoiceListVO;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class InvoiceServiceImpl implements InvoiceService{

    @Autowired
    private CommissionInvoiceMapper commissionInvoiceMapper;
    @Autowired
    private CommissionSchoolMapper commissionSchoolMapper;
    @Autowired
    private CommissionStudentMapper commissionStudentMapper;
    @Autowired
    private CommissionInvoiceSeqMapper commissionInvoiceSeqMapper;
    @Autowired
    private CurrencyInfoService currencyInfoService;
    //如果查询条件中国家为美国（4），则查询4-美国，43-美国USPP项目，53-美国AUP预科，42-艺术预科（unilearn）
    private Integer  usaNationId [] = {4,43,53,42};


    public static final Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    @Override
    public List<InvoiceListVO> getList(InvoiceListCondition invoiceListCondition) {
        return commissionInvoiceMapper.getList(invoiceListCondition);
    }

    @Override
    public InvoiceListVO getListSum(InvoiceListCondition invoiceListCondition) {
        return commissionInvoiceMapper.getListSum(invoiceListCondition);
    }

    @Override
    public List<InvoiceListVO> getListByAddInvoice(InvoiceListCondition invoiceListCondition) {
        return commissionInvoiceMapper.getListByAddInvoice(invoiceListCondition);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class} )
    public BaseResponse save(InvoiceListVO invoiceListVO, Integer type) {
        if(InvoiceRelatedEnum.SAVE_TYPE_ADD.getCode().equals(type)) {
            return this.addSave(invoiceListVO, type);
        }else if(InvoiceRelatedEnum.SAVE_TYPE_EDIT.getCode().equals(type)){
            return this.editSave(invoiceListVO, type);
        }else if(InvoiceRelatedEnum.SAVE_TYPE_MODIFY.getCode().equals(type)){
            return this.modifySave(invoiceListVO);
        }
        BaseResponse response = new BaseResponse();
        response.setResult(false);
        response.setErrorMsg("参数错误!");
        return response;
    }

    public BaseResponse addSave(InvoiceListVO invoiceListVO, Integer type) {
        BaseResponse response = new BaseResponse();

        String schoolIdStr = invoiceListVO.getSchoolIdStr();
        if(StringUtils.hasText(schoolIdStr)) {
            //更新commission_invoice_seq
            commissionInvoiceSeqMapper.updateInvoiceByTempKey(invoiceListVO.getTempKey(), invoiceListVO.getInvoiceNo());
            // 学校id字符串转成集合
            List<Integer> schoolIds = this.strToList(schoolIdStr);
            // 根据id集合查询学校信息
            InvoiceListCondition invoiceListCondition = new InvoiceListCondition();
            invoiceListCondition.setSchoolIds(schoolIds);

            List<InvoiceListVO> invoiceListVOS = this.getListByAddInvoice(invoiceListCondition);
            boolean statusIsNotNull = StringUtils.hasText(invoiceListVO.getStatus());
            // 循环添加到invoice表
            for(InvoiceListVO invoiceListVO1 : invoiceListVOS) {
                CommissionInvoice commissionInvoice = new CommissionInvoice();
                invoiceListVO1.setInvoiceNo(invoiceListVO.getInvoiceNo());
                if(invoiceListVO.getSchoolRate() != null) {
                    invoiceListVO1.setRate(invoiceListVO.getSchoolRate());
                }
                if(invoiceListVO.getInvoiceGstRate() != null){
                    invoiceListVO1.setInvoiceGstRate(invoiceListVO.getInvoiceGstRate());
                }
                invoiceListVO1.setSendDate(invoiceListVO.getSendDate());
                invoiceListVO1.setBankAccount(invoiceListVO.getBankAccount());
                invoiceListVO1.setReturnDate(invoiceListVO.getReturnDate());
                invoiceListVO1.setAccountCurrency(invoiceListVO.getAccountCurrency());
                invoiceListVO1.setTuition(invoiceListVO1.getSchoolTuition());
                // 学费特殊处理 学费币种默认等于Inv币种
                Map<String, String> currencyMap = currencyInfoService.getCurrencyMap(new CurrencyInfo());
                invoiceListVO1.setCurrency(currencyMap.get(invoiceListVO1.getTuitionCurrency()));

                String result = this.addCheck(invoiceListVO1);
                if(result != null){
                    throw new ContentException(1, result);
                }
                this.convert(commissionInvoice, invoiceListVO1);
//                Integer schoolId = commissionInvoice.getSchoolId();
//                CommissionInvoice commissionInvoice1 = new CommissionInvoice();
//                commissionInvoice1.setSchoolId(schoolId);
//                commissionInvoice1.setDeleteStatus(true);
                //移除原Invoice中的院校
//                commissionInvoiceMapper.updateBySchoolId(commissionInvoice1);
                commissionInvoice.setId(null);
                commissionInvoiceMapper.insert(commissionInvoice);

                // 结佣状态修改
                if(statusIsNotNull){
                    CommissionSchool commissionSchool = new CommissionSchool();
                    commissionSchool.setId(invoiceListVO1.getSchoolId());
                    commissionSchool.setStatus(invoiceListVO.getStatus());
                    commissionSchoolMapper.updateByPrimaryKeySelective(commissionSchool);
                }
                boolean nationIsNotNull = invoiceListVO.getNationId() != null;
                if(nationIsNotNull){
                    CommissionStudent commissionStudent = new CommissionStudent();
                    this.convert(commissionStudent, invoiceListVO);
                    commissionStudentMapper.updateByPrimaryKeySelective(commissionStudent);
                }
            }
            response.setResult(true);
        }else{
            response.setResult(false);
            response.setErrorMsg("请勾选要添加到Invoice的院校！");
        }
        return response;
    }

    /**
     * 批量编辑
     * @param invoiceListVO
     * @param type
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
    public BaseResponse editSave(InvoiceListVO invoiceListVO, Integer type) {
        BaseResponse response = new BaseResponse();
        String invoiceIdStr = invoiceListVO.getInvoiceIdStr();
        if(StringUtils.hasText(invoiceIdStr)) {

            // 学校id字符串转成集合
            List<Integer> invoiceIds = this.strToList(invoiceIdStr);
            // 根据id集合查询学校信息
            InvoiceListCondition invoiceListCondition = new InvoiceListCondition();
            invoiceListCondition.setInvoiceIds(invoiceIds);
            List<InvoiceListVO> invoiceListVOS = this.getList(invoiceListCondition);
            boolean statusIsNotNull = StringUtils.hasText(invoiceListVO.getStatus());
            // 循环添加到invoice表
            for(InvoiceListVO invoiceListVO1 : invoiceListVOS) {
                CommissionInvoice commissionInvoice = new CommissionInvoice();
                invoiceListVO.setId(invoiceListVO1.getId());
                if(invoiceListVO.getInvoiceGstRate() != null){
                    invoiceListVO.setTuition(invoiceListVO1.getTuition());
                    invoiceListVO.setInvoiceMoney(invoiceListVO1.getInvoiceMoney());
                }
                if(invoiceListVO.getSchoolRate() != null){
                    invoiceListVO.setRate(invoiceListVO.getSchoolRate());
                    invoiceListVO.setTuition(invoiceListVO1.getTuition());
                    invoiceListVO.setInvoiceGstRate(invoiceListVO1.getInvoiceGstRate());
                }

                this.convert(commissionInvoice, invoiceListVO);
                commissionInvoice.setCreateTime(null);
                commissionInvoiceMapper.updateByPrimaryKeySelective(commissionInvoice);
                // 结佣状态修改
                if(statusIsNotNull){
                    CommissionSchool commissionSchool = new CommissionSchool();
                    commissionSchool.setId(invoiceListVO1.getSchoolId());
                    commissionSchool.setStatus(invoiceListVO.getStatus());
                    commissionSchoolMapper.updateByPrimaryKeySelective(commissionSchool);
                }
                boolean nationIsNotNull = invoiceListVO.getNationId() != null;
                if(nationIsNotNull){
                    CommissionStudent commissionStudent = new CommissionStudent();
                    this.convert(commissionStudent, invoiceListVO);
                    commissionStudentMapper.updateByPrimaryKeySelective(commissionStudent);
                }
            }
            response.setResult(true);
        }else{
            response.setResult(false);
            response.setErrorMsg("请勾选要添加到Invoice的院校！");
        }
        return response;
    }

    /**
     * 行内编辑
     * @param invoiceListVO
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
    public BaseResponse modifySave(InvoiceListVO invoiceListVO) {
        BaseResponse response = new BaseResponse();
        InvoiceListCondition invoiceListCondition = new InvoiceListCondition();
        List<Integer> schoolIds = new ArrayList<Integer>(0);
        schoolIds.add(invoiceListVO.getSchoolId());
        invoiceListCondition.setSchoolIds(schoolIds);
        invoiceListCondition.setInvoiceId(invoiceListVO.getId());
        List<InvoiceListVO> invoiceListVOS = this.getList(invoiceListCondition);
        if(invoiceListVOS != null && invoiceListVOS.size() > 0){
            CommissionInvoice commissionInvoice = new CommissionInvoice();
            InvoiceListVO invoiceVO = invoiceListVOS.get(0);
            if(invoiceListVO.getInvoiceGstRate() != null){
                invoiceListVO.setGstRate(invoiceListVO.getInvoiceGstRate());
            }else{
                invoiceListVO.setGstRate(invoiceVO.getInvoiceGstRate());
            }
            if(invoiceListVO.getBankFee() == null){
                invoiceListVO.setBankFee(invoiceVO.getBankFee());
            }
            if(invoiceListVO.getTuition() == null){
                invoiceListVO.setTuition(invoiceVO.getTuition());
            }else{
                // 修改学费时到账金额置空，重新计算
                invoiceListVO.setActualAmount(null);
                invoiceListVO.setSchoolRate(invoiceVO.getRate());
            }
            invoiceListVO.setRate(invoiceListVO.getSchoolRate());

            this.editConvert(commissionInvoice, invoiceListVO);
            commissionInvoiceMapper.updateByPrimaryKeySelective(commissionInvoice);
            CommissionSchool commissionSchool = new CommissionSchool();
            this.convert(commissionSchool, invoiceListVO);
            commissionSchoolMapper.updateByPrimaryKeySelective(commissionSchool);
            CommissionStudent commissionStudent = new CommissionStudent();
            this.convert(commissionStudent, invoiceListVO);
            if(commissionStudent.getNationId() != null || StringUtils.hasText(commissionStudent.getNationName())){
                commissionStudentMapper.updateByPrimaryKeySelective(commissionStudent);
            }
            response.setResult(true);
        }else{
            response.setResult(false);
            response.setErrorMsg("Invoice信息不存在!");
        }
        return response;
    }


    /**
     * 处理学校id
     * @param schoolIdStr
     * @return
     */
    public static List<Integer> strToList(String schoolIdStr){
        String[] schoolIdArray = schoolIdStr.split(",");
        List<Integer> schoolIds = new ArrayList<>();
        for(int i=0; i<schoolIdArray.length; i++){
            schoolIds.add(Integer.valueOf(schoolIdArray[i]));
        }
        return schoolIds;
    }

    /**
     * 组装参数
     */
    public void convert(CommissionInvoice commissionInvoice, InvoiceListVO invoiceListVO){
        commissionInvoice.setId(invoiceListVO.getId());
        commissionInvoice.setSchoolId(invoiceListVO.getSchoolId());
        commissionInvoice.setInvoiceNo(invoiceListVO.getInvoiceNo());
        commissionInvoice.setBankFee(invoiceListVO.getBankFee());
        String result = check(invoiceListVO);
        if(result == null){
            //  Invoice金额 = 学费 * 佣金比例
            if(invoiceListVO.getRate() == null || invoiceListVO.getRate().doubleValue() < 0) {
                commissionInvoice.setInvoiceMoney(invoiceListVO.getInvoiceMoney());
            }else{
                commissionInvoice.setInvoiceMoney(invoiceListVO.getTuition().multiply(invoiceListVO.getRate()));
            }
            //  InvoiceGst = Invoice金额 * GST比例
            commissionInvoice.setInvoiceGst(commissionInvoice.getInvoiceMoney().multiply(invoiceListVO.getInvoiceGstRate()));
            //  Invoice总额 = Invoice金额 + InvoiceGst
            commissionInvoice.setInvoiceSum(commissionInvoice.getInvoiceMoney().add(commissionInvoice.getInvoiceGst()));
            //  到账金额默认等于Invoice金额  可编辑
            if(invoiceListVO.getActualAmount() != null){
                commissionInvoice.setActualAmount(invoiceListVO.getActualAmount());
            }else{
                commissionInvoice.setActualAmount(commissionInvoice.getInvoiceMoney());
            }
            //  账户GST = 账户金额 * GST比例
            commissionInvoice.setAccountGst(commissionInvoice.getActualAmount().multiply(invoiceListVO.getInvoiceGstRate()));
            //  到账总额 = 到账金额 + 账户GST
            commissionInvoice.setAccountSum(commissionInvoice.getActualAmount().add(commissionInvoice.getAccountGst()));
            //  账户金额 = 到账总额 - 银行手续费
            if(commissionInvoice.getBankFee() == null){
                commissionInvoice.setAccountMoney(commissionInvoice.getAccountSum());
            }else{
                commissionInvoice.setAccountMoney(commissionInvoice.getAccountSum().subtract(commissionInvoice.getBankFee()));
            }
            //  差额 = Invoice金额 - 账户金额
            commissionInvoice.setBalance(commissionInvoice.getInvoiceSum().subtract(commissionInvoice.getAccountSum()));
        }else{
            logger.error(result);
        }
        commissionInvoice.setSendDate(invoiceListVO.getSendDate());
        commissionInvoice.setReturnDate(invoiceListVO.getReturnDate());
        commissionInvoice.setBankAccount(StringUtils.hasText(invoiceListVO.getBankAccount())? invoiceListVO.getBankAccount(): null);
        commissionInvoice.setAccountCurrency(StringUtils.hasText(invoiceListVO.getAccountCurrency())? invoiceListVO.getAccountCurrency(): null);
        commissionInvoice.setCreateTime(new Date());
        commissionInvoice.setDeleteStatus(false);
        commissionInvoice.setTuition(invoiceListVO.getTuition());
        commissionInvoice.setRate(invoiceListVO.getRate());
        commissionInvoice.setInvoiceGstRate(invoiceListVO.getInvoiceGstRate());
        commissionInvoice.setBalanceType(StringUtils.hasText(invoiceListVO.getBalanceType())? invoiceListVO.getBalanceType(): null);
        commissionInvoice.setCurrency(invoiceListVO.getCurrency());
        commissionInvoice.setReturnCurrency(invoiceListVO.getReturnCurrency());
        commissionInvoice.setInvoiceRemark(invoiceListVO.getInvoiceRemark());
    }

    private void editConvert(CommissionInvoice commissionInvoice, InvoiceListVO invoiceListVO){
        commissionInvoice.setId(invoiceListVO.getId());
        commissionInvoice.setSchoolId(invoiceListVO.getSchoolId());
        commissionInvoice.setInvoiceNo(invoiceListVO.getInvoiceNo());
        commissionInvoice.setSendDate(invoiceListVO.getSendDate());
        commissionInvoice.setReturnDate(invoiceListVO.getReturnDate());
        commissionInvoice.setBalanceType(invoiceListVO.getBalanceType());
        commissionInvoice.setReturnCurrency(StringUtils.hasText(invoiceListVO.getReturnCurrency())? invoiceListVO.getReturnCurrency(): null);
        commissionInvoice.setCurrency(StringUtils.hasText(invoiceListVO.getCurrency())? invoiceListVO.getCurrency(): null);
        commissionInvoice.setBankAccount(StringUtils.hasText(invoiceListVO.getBankAccount())? invoiceListVO.getBankAccount(): null);
        commissionInvoice.setAccountCurrency(StringUtils.hasText(invoiceListVO.getAccountCurrency())? invoiceListVO.getAccountCurrency(): null);
        if(invoiceListVO.getInvoiceMoney() != null || invoiceListVO.getRate() != null) {
            commissionInvoice.setTuition(invoiceListVO.getTuition());
            commissionInvoice.setBankFee(invoiceListVO.getBankFee());
            commissionInvoice.setRate(invoiceListVO.getRate());
            if (commissionInvoice.getRate() == null || commissionInvoice.getRate().equals(BigDecimal.valueOf(-1))) {
                commissionInvoice.setInvoiceMoney(invoiceListVO.getInvoiceMoney());
                commissionInvoice.setRate(BigDecimal.valueOf(-1));
            } else {
                commissionInvoice.setInvoiceMoney(invoiceListVO.getTuition().multiply(invoiceListVO.getRate()));
            }
            //  InvoiceGst = Invoice金额 * GST比例
            commissionInvoice.setInvoiceGst(commissionInvoice.getInvoiceMoney().multiply(invoiceListVO.getGstRate() == null ? BigDecimal.ZERO : invoiceListVO.getGstRate()));
            //  Invoice总额 = Invoice金额 + InvoiceGst
            commissionInvoice.setInvoiceSum(commissionInvoice.getInvoiceMoney().add(commissionInvoice.getInvoiceGst()));
            //  到账金额默认等于Invoice金额  可编辑
            if (invoiceListVO.getActualAmount() != null) {
                commissionInvoice.setActualAmount(invoiceListVO.getActualAmount());
            } else {
                commissionInvoice.setActualAmount(commissionInvoice.getInvoiceMoney());
            }
            //  账户GST = 账户金额 * GST比例
            commissionInvoice.setAccountGst(commissionInvoice.getActualAmount().multiply(invoiceListVO.getGstRate()));
            //  到账总额 = 到账金额 + 账户GST
            commissionInvoice.setAccountSum(commissionInvoice.getActualAmount().add(commissionInvoice.getAccountGst()));
            //  账户金额 = 到账总额 - 银行手续费
            if (commissionInvoice.getBankFee() == null) {
                commissionInvoice.setAccountMoney(commissionInvoice.getAccountSum());
            } else {
                commissionInvoice.setAccountMoney(commissionInvoice.getAccountSum().subtract(commissionInvoice.getBankFee()));
            }
            //  差额 = Invoice金额 - 账户金额
            commissionInvoice.setBalance(commissionInvoice.getInvoiceSum().subtract(commissionInvoice.getAccountSum()));
        }
        commissionInvoice.setInvoiceRemark(invoiceListVO.getSchoolRemark());
    }

    /**
     * 组装参数
     * @param commissionSchool
     * @param invoiceListVO
     */
    private void convert(CommissionSchool commissionSchool, InvoiceListVO invoiceListVO){
        commissionSchool.setId(invoiceListVO.getSchoolId());
        commissionSchool.setDeleteStatus(false);
        commissionSchool.setCommissionBelong(StringUtils.hasText(invoiceListVO.getCommissionBelong())? invoiceListVO.getCommissionBelong(): null);
        commissionSchool.setSchoolNo(StringUtils.hasText(invoiceListVO.getSchoolNo())? invoiceListVO.getSchoolNo(): null);
        if(StringUtils.hasText(invoiceListVO.getStudyWeek())) {
            commissionSchool.setStudyWeek(invoiceListVO.getStudyWeek().toString());
        }
        commissionSchool.setStartDate(invoiceListVO.getStartDate());
    }

    private void convert(CommissionStudent commissionStudent, InvoiceListVO invoiceListVO){
        commissionStudent.setId(Integer.valueOf(invoiceListVO.getStudentId()));
        commissionStudent.setNationName(invoiceListVO.getNationName());
        commissionStudent.setNationId(invoiceListVO.getNationId());
    }

    /**
     * 参数校验
     * @param invoiceListVO
     * @return
     */
    private String check(InvoiceListVO invoiceListVO){
        if(invoiceListVO.getTuition() == null){
            return "学费信息为空！";
        }
        if(invoiceListVO.getInvoiceGstRate() == null){
            return "GST比例为空！";
        }
        return null;
    }

    /**
     * 添加Invoice参数校验
     * @param invoiceListVO
     * @return
     */
    private String addCheck(InvoiceListVO invoiceListVO){
        if(invoiceListVO.getTuition() == null){
            return "学费信息为空！";
        }
        if(invoiceListVO.getRate() == null){
            return "佣金比例为空！";
        }
        if(invoiceListVO.getInvoiceGstRate() == null){
            return "GST比例为空！";
        }
        return null;
    }

    @Override
    public BaseResponse remove(Integer id) {
        BaseResponse response = new BaseResponse();
        try {
            CommissionInvoice commissionInvoice = new CommissionInvoice();
            commissionInvoice.setDeleteStatus(true);
            commissionInvoice.setId(id);
            commissionInvoiceMapper.updateByPrimaryKeySelective(commissionInvoice);
            response.setResult(true);
        }catch(Exception e){
            logger.error(e.getMessage());
            response.setResult(false);
            response.setErrorMsg("系统异常");
        }
        return response;
    }

    @Override
    public List<CommissionReportVO> commissionReport(InvoiceListCondition invoiceListCondition) {
        return commissionInvoiceMapper.commissionReport(invoiceListCondition, null, null,usaNationId);
    }

    @Override
    public void commissionListToExcel(HttpServletRequest request, HttpServletResponse response) {
        InvoiceListCondition invoiceListCondition = (InvoiceListCondition)request.getSession().getAttribute(Contants.COMMISSION_REPORT_CONDITION_SESSION_KEY);
        List<Map> data= commissionInvoiceMapper.commissionReportMap(invoiceListCondition);
        ExcelUtil.export(response, data, CommissionReportNewVO.class);
    }

    @Override
    public void commissionListToExcel2(HttpServletRequest request, HttpServletResponse response) {
        try {
            // 文件名默认设置为当前时间：年月日时分秒
            String fileName = org.apache.commons.lang.time.DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
            // 设置文件名称
            String sheetName ="佣金统计报表";
            String title = sheetName;
            // 设置response头信息
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + title + fileName + ".xls");

            // 创建工作簿并发送到浏览器
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            OutputStream os = response.getOutputStream();
            List<CommissionReportVO> commissionReportVOList = this.convert(request);
            String[] rowsName = new String[]{
                    "学生姓名","拼音","公司号","代理推荐","出生日期","留学国家","获签日期","签约日期","登记日期","分支来源",
                    "学生来源","合同类型","代理名称","咨询员","转接咨询员","文签顾问","学生备注","结佣归属","结佣备注",
                    "学校名称","上课校区","学校学号","学校类型","课程类型","专业名称","课程周数","开学时间","学费","结佣比例",
                    "INV-NO","INV发出日期","INV币种","INV金额","INV-GST","INV税后金额","银行账户","到账日期","到账币种",
                    "到账金额","到账GST","到账税后金额","银行手续费","银行手续费币种", "账户币种","账户金额","差额","差额原因","学生属性"};

            List<Object[]>  dataList = new ArrayList<Object[]>();
            Object[] objs = null;
            for (int i = 0; i < commissionReportVOList.size(); i++) {
                CommissionReportVO commissionReportVO = commissionReportVOList.get(i);
                objs = new Object[rowsName.length];
                objs[0] = commissionReportVO.getStudentName();
                objs[1] = commissionReportVO.getSpelling();
                objs[2] = commissionReportVO.getStudentNo();
                objs[3] = commissionReportVO.getAgentType();
                objs[4] = commissionReportVO.getBirthday() == null ? null : commissionReportVO.getBirthday().getTime();
                objs[5] = commissionReportVO.getNationName();
                objs[6] = commissionReportVO.getVisaDate() == null ? null : commissionReportVO.getVisaDate().getTime();
                objs[7] = commissionReportVO.getSignDate() == null ? null : commissionReportVO.getSignDate().getTime();
                objs[8] = commissionReportVO.getCoeDate() == null ? null : commissionReportVO.getCoeDate().getTime();
                objs[9] = commissionReportVO.getBranch();
                objs[10] = commissionReportVO.getStudentSource();
                objs[11] = commissionReportVO.getContractType();
                objs[12] = commissionReportVO.getAgentName();
                objs[13] = commissionReportVO.getConsulter();
                objs[14] = commissionReportVO.getTransferConsulter();
                objs[15] = commissionReportVO.getCopyOperator();
                objs[16] = commissionReportVO.getSchoolRemark2();
                objs[17] = commissionReportVO.getCommissionBelong();
                objs[18] = commissionReportVO.getSchoolRemark();
                objs[19] = commissionReportVO.getSchoolName();
                objs[20] = commissionReportVO.getSchoolArea();
                objs[21] = commissionReportVO.getSchoolNo();
                objs[22] = commissionReportVO.getCollegeType();
                objs[23] = commissionReportVO.getCourseName();
                objs[24] = commissionReportVO.getMajorName();
                objs[25] = commissionReportVO.getStudyWeek();
                objs[26] = commissionReportVO.getStartDate() == null ? null : commissionReportVO.getStartDate().getTime();
                objs[27] = commissionReportVO.getTuition();
                objs[28] = commissionReportVO.getRate();
                objs[29] = commissionReportVO.getInvoiceNo();
                objs[30] = commissionReportVO.getSendDate() == null ? null : commissionReportVO.getSendDate().getTime();
                objs[31] = commissionReportVO.getCurrency();
                objs[32] = commissionReportVO.getInvoiceMoney();
                objs[33] = commissionReportVO.getInvoiceGst();
                objs[34] = commissionReportVO.getInvoiceSum();
                objs[35] = commissionReportVO.getBankAccount();
                objs[36] = commissionReportVO.getReturnDate() == null ? null : commissionReportVO.getReturnDate().getTime();
                objs[37] = commissionReportVO.getReturnCurrency();
                objs[38] = commissionReportVO.getActualAmount();
                objs[39] = commissionReportVO.getAccountGst();
                objs[40] = commissionReportVO.getAccountSum();
                objs[41] = commissionReportVO.getBankFee();
                objs[42] = commissionReportVO.getBankFeeCurrency();
                objs[43] = commissionReportVO.getAccountCurrency();
                objs[44] = commissionReportVO.getAccountMoney();
                objs[45] = commissionReportVO.getBalance();
                objs[46] = commissionReportVO.getBalanceType();
                objs[47] = commissionReportVO.getStudentProperty();
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
     * @return
     */
    public List<CommissionReportVO> convert(HttpServletRequest request){
        InvoiceListCondition invoiceListCondition = (InvoiceListCondition)request.getSession().getAttribute(Contants.COMMISSION_REPORT_CONDITION_SESSION_KEY);
        int count = commissionInvoiceMapper.commissionReportCount(invoiceListCondition,usaNationId);
        if(count > Contants.EXPORT_MAX){
            throw new RuntimeException("数量太大");
        }
        List<CommissionReportVO> commissionReportVOList = new ArrayList<>();
        int pageSize = Contants.STUDENT_EXPORT_PAGESIZE;
        for(int i = 0; i < count/pageSize+1; i++){
            List<CommissionReportVO> commissionReportVOS= commissionInvoiceMapper.commissionReport(invoiceListCondition, i*pageSize, pageSize,usaNationId);
            commissionReportVOList.addAll(commissionReportVOS);
        }
        commissionReportVOList.forEach(commissionReportVO -> {
            commissionReportVO.setSendDateStr(commissionReportVO.getSendDate() == null? null: DateFormatUtils.format(commissionReportVO.getSendDate(), Contants.datePattern));
            commissionReportVO.setReturnDateStr(commissionReportVO.getReturnDate() == null? null: DateFormatUtils.format(commissionReportVO.getReturnDate(), Contants.datePattern));
            commissionReportVO.setBirthdayStr(commissionReportVO.getBirthday() == null? null: DateFormatUtils.format(commissionReportVO.getBirthday(), Contants.datePattern));
            commissionReportVO.setVisaDateStr(commissionReportVO.getVisaDate() == null? null: DateFormatUtils.format(commissionReportVO.getVisaDate(), Contants.datePattern));
            commissionReportVO.setStartDateStr(commissionReportVO.getStartDate() == null? null: DateFormatUtils.format(commissionReportVO.getStartDate(), Contants.datePattern));
            commissionReportVO.setSignDateStr(commissionReportVO.getSignDate() == null? null: DateFormatUtils.format(commissionReportVO.getSignDate(), Contants.datePattern));
            if(StringUtils.hasText(commissionReportVO.getCollegeType())){
                commissionReportVO.setCollegeType(CollegeTypeEnum.getByCode(commissionReportVO.getCollegeType()));
            }
            commissionReportVO.setContractType(ContractTypeEnum.getByCode(commissionReportVO.getContractType()));
            commissionReportVO.setCommissionBelong(CommissionBelongEnum.getByCode(commissionReportVO.getCommissionBelong()));
            commissionReportVO.setAgentType(AgentTypeEnum.getByCode(commissionReportVO.getAgentType()));
            commissionReportVO.setStudentSource(StudentSourceEnum.getByCode(commissionReportVO.getStudentSource()));
            if(StringUtils.hasText(commissionReportVO.getStatus())){
                if(commissionReportVO.getStatus().equals("1")){
                    commissionReportVO.setStatus("未开始");
                }else if(commissionReportVO.getStatus().equals("2")){
                    commissionReportVO.setStatus("进行中");
                }else if(commissionReportVO.getStatus().equals("3")){
                    commissionReportVO.setStatus("已结束");
                }else if(commissionReportVO.getStatus().equals("4")){
                    commissionReportVO.setStatus("已取消");
                }
            }
        });
        return commissionReportVOList;
    }

    @Override
    public BaseResponse checkSchool(InvoiceListCondition invoiceListCondition) {
        BaseResponse response = new BaseResponse();
        String schoolIdStr = invoiceListCondition.getSchoolIdStr();
        if(StringUtils.hasText(schoolIdStr)) {
            // 学校id字符串转成集合
            List<Integer> schoolIds = this.strToList(schoolIdStr);
            // 同一个invoice填加了相同的院校
            List<CommissionInvoice> commissionInvoices = commissionInvoiceMapper.checkSchool(schoolIds, invoiceListCondition.getInvoiceNo());
            if(!commissionInvoices.isEmpty()){
                response.setResult(false);
                response.setErrorCode("");
                return response;
            }
            // 不同的invoice添加了相同的院校
            List<CommissionInvoice> commissionInvoiceList = commissionInvoiceMapper.checkSchool(schoolIds, null);
            if(!commissionInvoiceList.isEmpty()){
                response.setResult(false);
                response.setErrorCode("");
                return response;
            }
        }

        response.setResult(true);
        return response;
    }

    @Override
    public boolean updateInvoiceRemarkByInvoiceId(String invoiceId, String schoolRemark) {

        CommissionInvoice ci = new CommissionInvoice();

        ci.setId(Integer.valueOf(invoiceId));

        ci.setInvoiceRemark(schoolRemark);

        return this.commissionInvoiceMapper.updateByPrimaryKeySelective(ci) > 0;
    }
}
