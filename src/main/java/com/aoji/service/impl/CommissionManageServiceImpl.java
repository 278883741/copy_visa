package com.aoji.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.AgentTypeEnum;
import com.aoji.contants.Contants;
import com.aoji.contants.ContentException;
import com.aoji.contants.InflowTypeEnum;
import com.aoji.mapper.CommissionSchoolMapper;
import com.aoji.mapper.CommissionStudentMapper;
import com.aoji.mapper.CommissionVisaMessageMapper;
import com.aoji.mapper.SysRoleMapper;
import com.aoji.model.*;
import com.aoji.model.res.School;
import com.aoji.model.res.StudentInfoRes;
import com.aoji.service.*;
import com.aoji.service.impl.ExcelServiceImpl;
import com.aoji.utils.ExcelUtils;
import com.aoji.utils.HttpUtils;
import com.aoji.vo.*;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;


@Service
public class CommissionManageServiceImpl implements CommissionManageService {

    public static final Logger logger = LoggerFactory.getLogger(CommissionManageServiceImpl.class);

    @Autowired
    private CommissionSchoolMapper commissionSchoolMapper;

    @Autowired
    private CommissionStudentMapper commissionStudentMapper;

    @Autowired
    private CommissionVisaMessageMapper commissionVisaMessageMapper;

    @Autowired
    private VisaRecordService visaRecordService;

    @Autowired
    private CurrencyInfoService currencyInfoService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private UserService userService;

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    CommissionSchoolServiceImpl commissionSchoolServiceimpl;

    @Autowired
    private CommissionAusAgentService commissionAusAgentService;

    @Value("${student.detail.url}")
    private String studentDetailUrl;
    //如果查询条件中国家为澳州（99） 则查询1-澳大利亚，48-昆省IFY(AU)，42-艺术预科（unilearn），45-NCC PMP
    //如果查询条件中国家为非澳（100），则查询这四个国家之外的全部国家
    private Integer  ausNationId [] = {1,48,42,45};
    //如果查询条件中国家为美国（4），则查询4-美国，43-美国USPP项目，53-美国AUP预科，42-艺术预科（unilearn）
    private Integer  usaNationId [] = {4,43,53,42};


    @Override
    public List<CommissionManageVO> getCommissionManageList(CommissionManageVO commissionManageVO, Integer roleStatus) {
        String  agentType =null;
        if(roleStatus.equals(2)){
          agentType = AgentTypeEnum.AGENT.getCode();
        }

        return commissionStudentMapper.getCommissionManageList(commissionManageVO, ausNationId, usaNationId, roleStatus, agentType);

    }
    @Override
    public void addCommissionManage(Integer studentId,Model model) {
        //获取院校库
        Integer studentNationId = null;
        if(studentId  != null){
            CommissionStudent commissionStudent = new CommissionStudent();
            commissionStudent.setId(studentId);
            commissionStudent.setDeleteStatus(false);
            List<CommissionStudent> commissionStudentList = commissionStudentMapper.select(commissionStudent);
            if(commissionStudentList != null && commissionStudentList.size() > 0){
                if(commissionStudentList.get(0).getNationId() != null){
                    studentNationId = commissionStudentList.get(0).getNationId();
                }
            }
        }
        CountryInfo countryInfo = new CountryInfo();
        countryInfo.setId(studentNationId);
        countryInfo = countryService.get(countryInfo);
        model.addAttribute("school",visaRecordService.getSchool1(countryInfo.getCountryXiaoxi()));

        model.addAttribute("studentNationName",studentNationId);



        //获取币种
        model.addAttribute("currencyInfos",  currencyInfoService.getList(new CurrencyInfo()));

        //获取顾问分支
        BranchInfo branchInfo = new BranchInfo();
        branchInfo.setIsdel(0);
        model.addAttribute("advisers",branchService.getList(branchInfo));

        //获取澳洲代理
        CommissionAusAgent commissionAusAgent = new CommissionAusAgent();
        model.addAttribute("commissionAusAgent",commissionAusAgentService.getCommissionAusAgentList(commissionAusAgent));
        CountryInfo countryInfo1 = new CountryInfo();
        model.addAttribute("countryList",countryService.getList(countryInfo1));

        List<String> studentNos = commissionStudentMapper.getStudentNo();
        StringBuffer studentNoString = new StringBuffer();
        if(studentNos !=null && studentNos.size() > 0 ){
            for(int i= 1 ; i <= studentNos.size();i++){
                if(i == 1 ){
                    studentNoString.append(studentNos.get(0));
                }else {
                    studentNoString.append(","+studentNos.get(i-1));
                }
            }
        }
        model.addAttribute("studentNos",studentNoString);

    }

    @Override
    public void getCommissionList(Model model,Integer studentId) {
        CommissionStudent commissionStudent = new CommissionStudent();
        commissionStudent.setDeleteStatus(false);
        commissionStudent.setId(studentId);
        List<CommissionStudent> commissionStudentList = commissionStudentMapper.select(commissionStudent);
        if(commissionStudentList != null && commissionStudentList.size() > 0){
            model.addAttribute("student",commissionStudentList.get(0));
        }
        List<SchoolInvoiceVO> schoolInvoiceVOList = commissionSchoolMapper.getSchoolInviceVOList(studentId);
        if(schoolInvoiceVOList != null && schoolInvoiceVOList.size()>0){
            for (SchoolInvoiceVO schoolInvoiceVO:schoolInvoiceVOList) {
                if(schoolInvoiceVO.getInvoiceSum() != null){
                    schoolInvoiceVO.setInvoiceSum(schoolInvoiceVO.getInvoiceSum().setScale(2, BigDecimal.ROUND_HALF_UP));
                }
                if(schoolInvoiceVO.getAccountSum() != null){
                    schoolInvoiceVO.setAccountSum(schoolInvoiceVO.getAccountSum().setScale(2, BigDecimal.ROUND_HALF_UP));
                }
            }
        }

        model.addAttribute("school",schoolInvoiceVOList);
        model.addAttribute("studentId",studentId);
        //获取澳洲代理
        CommissionAusAgent commissionAusAgent = new CommissionAusAgent();
        model.addAttribute("commissionAusAgent",commissionAusAgentService.getCommissionAusAgentList(commissionAusAgent));
        //获取国家
        CountryInfo countryInfo = new CountryInfo();
        model.addAttribute("countryList",countryService.getList(countryInfo));
    }

    @Override
    public Boolean commissionManageSave(CommissionStudent commissionStudent, CommissionSchool commissionSchool) {
        commissionStudent.setDeleteStatus(false);
        commissionSchool.setDeleteStatus(false);
        if(commissionStudent.getVisaDate() !=null){
            commissionStudent.setCreateTime(commissionStudent.getVisaDate());
        }else{
            commissionStudent.setCreateTime(new Date());
        }
        if(StringUtils.hasText(String.valueOf(commissionStudent.getNationId()))){
            CountryInfo countryInfo = new CountryInfo();
            countryInfo.setId(commissionStudent.getId());
            countryInfo = countryService.get(countryInfo);
            if(StringUtils.hasText(countryInfo.getCountryName())){
                commissionStudent.setNationName(countryInfo.getCountryName());
            }

        }
        int schoolSaveResult = commissionStudentMapper.insert(commissionStudent);
        commissionSchool.setStudentId(commissionStudent.getId());
        commissionSchool.setCreateTime(new Date());
        if(StringUtils.hasText(commissionSchool.getOfferFile())){
            String s = commissionSchoolServiceimpl.setFilesUrl(commissionSchool.getOfferFile());
            commissionSchool.setOfferFile(s);
        }

        if(StringUtils.hasText(commissionSchool.getCoeFile())){
            String s = commissionSchoolServiceimpl.setFilesUrl(commissionSchool.getCoeFile());
            commissionSchool.setCoeFile(s);

        }
        if(StringUtils.hasText(commissionSchool.getEmailFile()) ){
            String s = commissionSchoolServiceimpl.setFilesUrl(commissionSchool.getEmailFile());
            commissionSchool.setEmailFile(s);
        }
        int studentSaveResult = commissionSchoolMapper.insert(commissionSchool);

        if(studentSaveResult == 1 || schoolSaveResult == 1){
            return true;
        }else{
            return false;
        }

    }

    @Override
    public Boolean insertCommissionManage(String studentNo) throws Exception {
        logger.info("save commissionManage start! studentNo:"+studentNo);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Contants.timePattern);
        try{
            List<CommissionManageSaveVO> commissionManageSaveVOList = commissionStudentMapper.getCommissionManageVOList(studentNo);
            VisaRecordInfo visaRecordInfo = new VisaRecordInfo();
            Integer saveResult = 0;
            Integer studentId = null;
            if(commissionManageSaveVOList != null && commissionManageSaveVOList.size() >0){
                for (int j = 0; j < commissionManageSaveVOList.size();j++) {
                    CommissionManageSaveVO commissionManageSaveVO = commissionManageSaveVOList.get(j);
                    CommissionSchool commissionSchool = new CommissionSchool();
                    CommissionStudent commissionStudent = new CommissionStudent();

                    if(j < 1){
                        commissionStudent.setContractType(commissionManageSaveVO.getContractType());
                        String json = HttpUtils.doGet(MessageFormat.format(studentDetailUrl, studentNo));
                        logger.info("StudentDetail RS : " + json);
                        StudentInfoRes studentInfoRes = (StudentInfoRes) JSONObject.parseObject(json, StudentInfoRes.class);
                        if (studentInfoRes != null && studentInfoRes.getState() == 0) {
                            StudentDetailVO studentDetailVO = studentInfoRes.getData();
                            if (studentDetailVO != null && StringUtils.hasText(studentDetailVO.getContractType()) && studentDetailVO.getContractType().equals("2")){
                                commissionStudent.setContractType("5");
                            }
                        }
                        commissionStudent.setStudentNo(commissionManageSaveVO.getStudentNo());
                        commissionStudent.setSignDate(commissionManageSaveVO.getSignDate());
                        commissionStudent.setStudentName(commissionManageSaveVO.getStudentName());
                        commissionStudent.setSpelling(commissionManageSaveVO.getPinyin());
                        commissionStudent.setBirthday(commissionManageSaveVO.getBirthday());
                        commissionStudent.setNationId(commissionManageSaveVO.getNationId());
                        commissionStudent.setNationName(commissionManageSaveVO.getNationName());
                        commissionStudent.setAgentType(commissionManageSaveVO.getAgentType());
                        commissionStudent.setAgentName(commissionManageSaveVO.getAgentName());
                        commissionStudent.setStudentSource("3");
                        commissionStudent.setVisaDate(commissionManageSaveVO.getVisaCreateTime());
                        commissionStudent.setCreateTime(commissionManageSaveVO.getVisaCreateTime());
                        commissionStudent.setStatus(commissionManageSaveVO.getStatus());
                        commissionStudent.setDeleteStatus(false);
                        String visaId ="";
                        if(commissionManageSaveVO.getVisaId()!=null&&!"".equals(commissionManageSaveVO.getVisaId())){
                             visaId = String.valueOf(commissionManageSaveVO.getVisaId());
                        }
                        commissionStudent.setRecordId(visaId);
                        commissionStudent.setStudentRemark(commissionManageSaveVO.getVisaRemark());
                        int result = commissionStudentMapper.insert(commissionStudent);
                        if (result > 0){
                            visaRecordInfo.setId(commissionManageSaveVO.getVisaId());
                            visaRecordInfo.setSendCommissionStatus(true);
                            visaRecordService.update(visaRecordInfo);
                        }
                        studentId = commissionStudent.getId();
                    }


                    commissionSchool.setVisaCommissionId(commissionManageSaveVO.getVisaRecordResultId());
                    commissionSchool.setDeleteStatus(false);
                    commissionSchool.setStudentId(studentId);
                    commissionSchool.setStudyWeek(commissionManageSaveVO.getCourseLength());
                    commissionSchool.setStudentNo(commissionManageSaveVO.getStudentNo());
                    commissionSchool.setSchoolName(commissionManageSaveVO.getCollegeName());
                    commissionSchool.setCollegeId(commissionManageSaveVO.getCollegeId());
                    commissionSchool.setSchoolArea(commissionManageSaveVO.getSchoolArea());
                    commissionSchool.setStartDate(commissionManageSaveVO.getCourseStartTime());
                    commissionSchool.setSchoolRemark2(commissionManageSaveVO.getVisaRemark());
                    commissionSchool.setPaidFee(commissionManageSaveVO.getPrepaidTuition());
                    commissionSchool.setSchoolNo(commissionManageSaveVO.getSchoolNo());
                    commissionSchool.setAgencyNo(commissionManageSaveVO.getCooperationId());
                    commissionSchool.setAgencyName(commissionManageSaveVO.getCooperationName());
                    if(StringUtils.hasText(commissionManageSaveVO.getCourseEndTime())){
                        commissionSchool.setEndDate(simpleDateFormat.parse(commissionManageSaveVO.getCourseEndTime()));
                    }
                    commissionSchool.setCourseId(commissionManageSaveVO.getCourseId());
                    if(StringUtils.hasText(commissionManageSaveVO.getCollegeType())){
                        if(commissionManageSaveVO.getCollegeType().equals("1")){
                            commissionSchool.setCollegeType("2");
                        }else if(commissionManageSaveVO.getCollegeType().equals("2")){
                            commissionSchool.setCollegeType("3");
                        }else if(commissionManageSaveVO.getCollegeType().equals("3")){
                            commissionSchool.setCollegeType("1");
                        }else{
                            commissionSchool.setCollegeType(commissionManageSaveVO.getCollegeType());
                        }
                    }
                    //commissionSchool.setCollegeType(commissionManageSaveVO.getCollegeType());
                    commissionSchool.setCourseName(commissionManageSaveVO.getCourseName());
                    commissionSchool.setMajorId(commissionManageSaveVO.getMajorId());
                    commissionSchool.setMajorName(commissionManageSaveVO.getMajorName());
                    commissionSchool.setTuition(commissionManageSaveVO.getTuition());
                    commissionSchool.setTuitionCurrency(commissionManageSaveVO.getCurrency());
                    commissionSchool.setPaidTuitionCurrency(commissionManageSaveVO.getCurrency());
                    commissionSchool.setCreateTime(commissionManageSaveVO.getSchoolCreateTime());
                    commissionSchool.setStatus("1");
                    commissionSchool.setBranch(commissionManageSaveVO.getBranchName());
                    commissionSchool.setConsulterBranch(commissionManageSaveVO.getBrachId());
                    commissionSchool.setCommissionBelong("2");
                    commissionSchool.setCopyOperator(commissionManageSaveVO.getCopyOperator());
                    commissionSchool.setConsulter(commissionManageSaveVO.getSalesConsultant());
                    commissionSchool.setEducationSection(commissionManageSaveVO.getEducationSection());
                    //查询学生对应的转案顾问
//                    List<String> sendOperators = commissionStudentMapper.getSendOperator(studentNo);
//
//                    StringBuffer sendOperatorString = new StringBuffer();
                    List<String> offerFiles = this.getOffer(studentNo);
                    StringBuffer offerString = new StringBuffer();
                    if(offerFiles !=null && offerFiles.size() > 0 ){
                        for(int i= 1 ; i <= offerFiles.size();i++){
                            if(i == 1 ){
                                offerString.append(offerFiles.get(0));
                            }else {
                                offerString.append(","+offerFiles.get(i-1));
                            }
                        }
                    }
                    List<String> coeFiles = this.getCoe(studentNo);
                    StringBuffer coeString = new StringBuffer();
                    if(coeFiles != null && coeFiles.size() > 0 ){
                        for(int i= 1 ; i <= coeFiles.size();i++){
                            if(i == 1 ){
                                coeString.append(coeFiles.get(0));
                            }else {
                                coeString.append(","+coeFiles.get(i-1));
                            }
                        }
                    }
                    commissionSchool.setCoeFile(coeString.toString());
                    commissionSchool.setOfferFile(offerString.toString());

//                    if(sendOperators != null && sendOperators.size() > 0 ){
//                        for(int i= 1 ; i <= sendOperators.size();i++){
//                            if(i == 1){
//                                sendOperatorString.append(sendOperators.get(0));
//                            }else{
//                                sendOperatorString.append(","+sendOperators.get(i-1));
//                            }
//                        }
//                    }
//                    commissionSchool.setTransferConsulter(sendOperatorString.toString());

                    Integer savaCommissionSchool = commissionSchoolMapper.insert(commissionSchool);
                    if(savaCommissionSchool > 0){
                        saveResult++;
                    }
                }
                if (saveResult > 0){
                    logger.info("save commissionManage end! studentNo:"+studentNo+"添加成功");
                    return true;
                }else{
                    logger.info("save commissionManage end! studentNo:"+studentNo+"添加失败");
                    return false;
                }
            }
        }catch(Exception e){
            throw new Exception("导入佣金系统信息失败");
        }
        return false;
    }

    @Override
    public List<String> getOffer(String studentNo) {
        //获取该学生对应的offer附件
        return commissionStudentMapper.getOfferFiles(studentNo);
    }

    @Override
    public List<String> getCoe(String studentNo) {
        //获取学生对应的coe附件
        return  commissionStudentMapper.getCoeFiles(studentNo);
    }

    @Override
    public void testExcel(HttpServletRequest request, HttpServletResponse response) throws  Exception{


            // 文件名默认设置为当前时间：年月日时分秒
            String fileName = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
            // 设置文件名称
            String sheetName ="佣金管理列表信息";
            String title = sheetName;
            // 设置response头信息
            response.reset();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + title + fileName + ".xls");

            // 创建工作簿并发送到浏览器
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            OutputStream os = response.getOutputStream();
            //获取登录用户的权限
            Integer roleStatus = this.loginRoleName();
            CommissionManageVO commissionManageVO = (CommissionManageVO) request.getSession().getAttribute("commissionManageExcelList");
            if(roleStatus.equals(1) || roleStatus.equals(3)){
                exportCommission(response, title, roleStatus, commissionManageVO);
            }else{
                exportCommissionOverseas(response, title, roleStatus, commissionManageVO);
            }

    }
    /**
     * roleStatus =2 时，佣金系统_海外操作导出  不关联Invoice
     *
     * @param response
     * @param title
     * @param
     * @throws Exception
     */
    private void exportCommissionOverseas(HttpServletResponse response, String title, Integer roleStatus, CommissionManageVO commissionManageVO) throws Exception{
        int countPage =  commissionStudentMapper.getCountCommissionOverseasExcelList(commissionManageVO,ausNationId,usaNationId,roleStatus, AgentTypeEnum.AGENT.getCode());
        if(countPage>Contants.EXPORT_MAX){
            throw new ContentException(9998,"当前条件下，导出数据量过大，请重新筛选条件，进行导出！");
        }
        int pageSize = Contants.STUDENT_EXPORT_PAGESIZE;
        List<CommissionManageVO> commissionManageVOListAll = new ArrayList<>();
        for(int i = 0; i < countPage/pageSize+1; i++){
            List<CommissionManageVO> commissionManageVOList = commissionStudentMapper.getCommissionOverseasExcelList(commissionManageVO,ausNationId,usaNationId,roleStatus,i*pageSize,pageSize,AgentTypeEnum.AGENT.getCode());
            commissionManageVOListAll.addAll(commissionManageVOList);
        }
        String[] rowsName = new String[]{"序号", "澳际学号", "姓名", "拼音", "出生日期", "获签国家", "获签日期", "登记日期","签约日期", "分支机构", "学生来源", "代理推荐", "合同类型", "学生备注", "咨询顾问", "转接顾问", "文签顾问", "课程属性", "课程备注",
                "结佣归属", "结佣备注", "合作机构", "院校名称", "上课校区", "院校学号", "专业类型", "课程名称", "专业名称", "上课周数", "上课日期", "结课日期", "已付学费", "学费", "结佣状态"};
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        for (int i = 0; i < commissionManageVOListAll.size(); i++) {
            CommissionManageVO commissionManageVO1 = commissionManageVOListAll.get(i);
            objs = new Object[rowsName.length];
            objs[0] = i + 1;
            objs[1] = commissionManageVO1.getStudentNo();
            objs[2] = commissionManageVO1.getStudentName();
            objs[3] = commissionManageVO1.getPinyin();
            if (commissionManageVO1.getBirthday() != null) {
                objs[4] = commissionManageVO1.getBirthday().getTime();
            } else {
                objs[4] = null;
            }
            objs[5] = commissionManageVO1.getNationName();
            if (commissionManageVO1.getVisaDate() != null) {
                objs[6] = commissionManageVO1.getVisaDate().getTime();
            } else {
                objs[6] = null;
            }
            if (commissionManageVO1.getCoeDate() != null) {
                objs[7] = commissionManageVO1.getCoeDate().getTime();
            } else {
                objs[7] = null;
            }
            if (commissionManageVO1.getSignDate() != null) {
                objs[8] = commissionManageVO1.getSignDate().getTime();
            } else {
                objs[8] = null;
            }

            objs[9] = commissionManageVO1.getBranch();
            if (StringUtils.hasText(commissionManageVO1.getStudentSource())) {
                if (commissionManageVO1.getStudentSource().equals("1")) {
                    commissionManageVO1.setStudentSource("Walk-in");
                } else if (commissionManageVO1.getStudentSource().equals("2")) {
                    commissionManageVO1.setStudentSource("Sub-agent");
                } else if (commissionManageVO1.getStudentSource().equals("3")) {
                    commissionManageVO1.setStudentSource("国内澳际");
                } else if (commissionManageVO1.getStudentSource().equals("0")) {
                    commissionManageVO1.setStudentSource("");
                }
            }
            objs[10] = commissionManageVO1.getStudentSource();
            if (StringUtils.hasText(commissionManageVO1.getAgentType())) {
                if (commissionManageVO1.getAgentType().equals("1")) {
                    commissionManageVO1.setAgentType("与代理直接签约");
                } else if (commissionManageVO1.getAgentType().equals("2")) {
                    commissionManageVO1.setAgentType("通过代理与澳际签约");
                } else if (commissionManageVO1.getAgentType().equals("0")) {
                    commissionManageVO1.setAgentType("");
                }
            }
            objs[11] = commissionManageVO1.getAgentType();
            if (StringUtils.hasText(commissionManageVO1.getContractType())) {
                if (commissionManageVO1.getContractType().equals("1")) {
                    commissionManageVO1.setContractType("全程合同");
                } else if (commissionManageVO1.getContractType().equals("2")) {
                    commissionManageVO1.setContractType("单申请合同");
                } else if (commissionManageVO1.getContractType().equals("3")) {
                    commissionManageVO1.setContractType("单文书合同");
                } else if (commissionManageVO1.getContractType().equals("4")) {
                    commissionManageVO1.setContractType("单签证合同");
                } else if (commissionManageVO1.getContractType().equals("5")) {
                    commissionManageVO1.setContractType("预科合同");
                } else if (commissionManageVO1.getContractType().equals("0")) {
                    commissionManageVO1.setContractType("");
                }
            }
            objs[12] = commissionManageVO1.getContractType();
            objs[13] = null;
            objs[14] = commissionManageVO1.getConsulter();
            objs[15] = commissionManageVO1.getTransferConsulter();
            objs[16] = commissionManageVO1.getCopyOperator();

            objs[17] = obj16(commissionManageVO1);


            objs[18] = commissionManageVO1.getRemarkTwo();
            if (StringUtils.hasText(commissionManageVO1.getComissionBelong())) {
                if (commissionManageVO1.getComissionBelong().equals("1")) {
                    commissionManageVO1.setComissionBelong("AEA");
                } else if (commissionManageVO1.getComissionBelong().equals("2")) {
                    commissionManageVO1.setComissionBelong("ECIE");
                } else if (commissionManageVO1.getComissionBelong().equals("3")) {
                    commissionManageVO1.setComissionBelong("BAEC");
                }
            }
            objs[19] = commissionManageVO1.getComissionBelong();
            objs[20] = commissionManageVO1.getRemarkThree();
            objs[21] = commissionManageVO1.getAgencyName();
            objs[22] = commissionManageVO1.getSchoolName();
            objs[23] = commissionManageVO1.getSchoolArea();
            objs[24] = commissionManageVO1.getSchoolNo();

            if (StringUtils.hasText(commissionManageVO1.getCollegeType())) {
                if (commissionManageVO1.getCollegeType().equals("1")) {
                    commissionManageVO1.setCollegeType("主课");
                } else if (commissionManageVO1.getCollegeType().equals("2")) {
                    commissionManageVO1.setCollegeType("语言");
                } else if (commissionManageVO1.getCollegeType().equals("3")) {
                    commissionManageVO1.setCollegeType("预备");
                }else if (commissionManageVO1.getCollegeType().equals("4")) {
                    commissionManageVO1.setCollegeType("快捷");
                }else if (commissionManageVO1.getCollegeType().equals("5")) {
                    commissionManageVO1.setCollegeType("夏校");
                }else if (commissionManageVO1.getCollegeType().equals("6")) {
                    commissionManageVO1.setCollegeType("桥梁");
                }else if (commissionManageVO1.getCollegeType().equals("7")) {
                    commissionManageVO1.setCollegeType("top up");
                }

            }
            objs[25] = commissionManageVO1.getCollegeType();
            objs[26] = commissionManageVO1.getCourseName();
            objs[27] = commissionManageVO1.getMajorName();
            objs[28] = commissionManageVO1.getStudyWeek();
            if(commissionManageVO1.getStartDate() != null){
                objs[29] = commissionManageVO1.getStartDate().getTime();
            }else{
                objs[29] = null;
            }
            if (commissionManageVO1.getEndDate() != null) {
                objs[30] = commissionManageVO1.getEndDate().getTime();
            } else {
                objs[30] = null;
            }
            objs[31] = commissionManageVO1.getPaidFee();
            objs[32] = commissionManageVO1.getTuition();
            if (StringUtils.hasText(commissionManageVO1.getStatus())) {
                if (commissionManageVO1.getStatus().equals("1")) {
                    commissionManageVO1.setStatus("未开始");
                } else if (commissionManageVO1.getStatus().equals("2")) {
                    commissionManageVO1.setStatus("进行中");
                } else if (commissionManageVO1.getStatus().equals("3")) {
                    commissionManageVO1.setStatus("已结束");
                } else if (commissionManageVO1.getStatus().equals("4")) {
                    commissionManageVO1.setStatus("已取消");
                }
            }
            objs[33] = commissionManageVO1.getStatus();
            dataList.add(objs);
        }
        ExcelServiceImpl ex = new ExcelServiceImpl(title, rowsName, dataList, response);
        ex.export();
    }
    /**
     * roleStatus =1 时，海外导出（只导出有海外顾问的数据）  roleStatus =3 时，国内佣金导出（佣金（国内）导出全部数据，不区分海外，国内顾问）并关联Invioce
     *
     * @param response
     * @param title
     * @param
     * @throws Exception
     */
    private void exportCommission(HttpServletResponse response, String title, Integer roleStatus, CommissionManageVO commissionManageVO) throws Exception {
        int countPage =  commissionStudentMapper.getCountCommissionManageExcelList(commissionManageVO,ausNationId,usaNationId,roleStatus);
        if(countPage>Contants.EXPORT_MAX){
            throw new ContentException(9998,"当前条件下，导出数据量过大，请重新筛选条件，进行导出！");
        }
        int pageSize = Contants.STUDENT_EXPORT_PAGESIZE;
        List<CommissionManageVO> commissionManageVOListAll = new ArrayList<>();
        for(int i = 0; i < countPage/pageSize+1; i++){
            List<CommissionManageVO> commissionManageVOList = commissionStudentMapper.getCommissionManageExcelList(commissionManageVO,ausNationId,usaNationId,roleStatus,i*pageSize,pageSize);
            commissionManageVOListAll.addAll(commissionManageVOList);
        }

        String[] rowsName = new String[]{"序号", "澳际学号", "姓名", "拼音", "出生日期", "获签国家", "获签日期", "登记日期","签约日期", "分支机构", "学生来源", "代理推荐", "合同类型", "学生备注", "咨询顾问", "转接顾问", "文签顾问", "课程属性", "课程备注",
                "结佣归属", "结佣备注", "合作机构", "院校名称", "上课校区", "院校学号", "专业类型", "课程名称", "专业名称", "上课周数", "上课日期", "结课日期", "已付学费", "学费", "结佣状态", "佣金比例", "invoiceNo", "invoice发出日期", "Invoice金额", "invoiceGST", "Invoice税后金额", "到账日期", "银行账户", "到账币种", "账户币种", "invoice币种", "到账金额",
                "到账GST", "到账税后金额", "银行手续费", "账户金额", "差额", "差额原因", "学生属性"};
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        for (int i = 0; i < commissionManageVOListAll.size(); i++) {
            CommissionManageVO commissionManageVO1 = commissionManageVOListAll.get(i);
            objs = new Object[rowsName.length];
            objs[0] = i + 1;
            objs[1] = commissionManageVO1.getStudentNo();
            objs[2] = commissionManageVO1.getStudentName();
            objs[3] = commissionManageVO1.getPinyin();
            if (commissionManageVO1.getBirthday() != null) {
                objs[4] = commissionManageVO1.getBirthday().getTime();
            } else {
                objs[4] = null;
            }
            objs[5] = commissionManageVO1.getNationName();
            if (commissionManageVO1.getVisaDate() != null) {
                objs[6] = commissionManageVO1.getVisaDate().getTime();
            } else {
                objs[6] = null;
            }
            if (commissionManageVO1.getCoeDate() != null) {
                objs[7] = commissionManageVO1.getCoeDate().getTime();
            } else {
                objs[7] = null;
            }
            if (commissionManageVO1.getSignDate() != null) {
                objs[8] = commissionManageVO1.getSignDate().getTime();
            } else {
                objs[8] = null;
            }

            objs[9] = commissionManageVO1.getBranch();
            if (StringUtils.hasText(commissionManageVO1.getStudentSource())) {
                if (commissionManageVO1.getStudentSource().equals("1")) {
                    commissionManageVO1.setStudentSource("Walk-in");
                } else if (commissionManageVO1.getStudentSource().equals("2")) {
                    commissionManageVO1.setStudentSource("Sub-agent");
                } else if (commissionManageVO1.getStudentSource().equals("3")) {
                    commissionManageVO1.setStudentSource("国内澳际");
                } else if (commissionManageVO1.getStudentSource().equals("0")) {
                    commissionManageVO1.setStudentSource("");
                }
            }
            objs[10] = commissionManageVO1.getStudentSource();
            if (StringUtils.hasText(commissionManageVO1.getAgentType())) {
                if (commissionManageVO1.getAgentType().equals("1")) {
                    commissionManageVO1.setAgentType("与代理直接签约");
                } else if (commissionManageVO1.getAgentType().equals("2")) {
                    commissionManageVO1.setAgentType("通过代理与澳际签约");
                } else if (commissionManageVO1.getAgentType().equals("0")) {
                    commissionManageVO1.setAgentType("");
                }
            }
            objs[11] = commissionManageVO1.getAgentType();
            if (StringUtils.hasText(commissionManageVO1.getContractType())) {
                if (commissionManageVO1.getContractType().equals("1")) {
                    commissionManageVO1.setContractType("全程合同");
                } else if (commissionManageVO1.getContractType().equals("2")) {
                    commissionManageVO1.setContractType("单申请合同");
                } else if (commissionManageVO1.getContractType().equals("3")) {
                    commissionManageVO1.setContractType("单文书合同");
                } else if (commissionManageVO1.getContractType().equals("4")) {
                    commissionManageVO1.setContractType("单签证合同");
                } else if (commissionManageVO1.getContractType().equals("5")) {
                    commissionManageVO1.setContractType("预科合同");
                } else if (commissionManageVO1.getContractType().equals("0")) {
                    commissionManageVO1.setContractType("");
                }
            }
            objs[12] = commissionManageVO1.getContractType();
            objs[13] = commissionManageVO1.getRemarkOne();
            objs[14] = commissionManageVO1.getConsulter();
            objs[15] = commissionManageVO1.getTransferConsulter();
            objs[16] = commissionManageVO1.getCopyOperator();

            objs[17] = obj16(commissionManageVO1);


            objs[18] = commissionManageVO1.getRemarkTwo();
            if (StringUtils.hasText(commissionManageVO1.getComissionBelong())) {
                if (commissionManageVO1.getComissionBelong().equals("1")) {
                    commissionManageVO1.setComissionBelong("AEA");
                } else if (commissionManageVO1.getComissionBelong().equals("2")) {
                    commissionManageVO1.setComissionBelong("ECIE");
                } else if (commissionManageVO1.getComissionBelong().equals("3")) {
                    commissionManageVO1.setComissionBelong("BAEC");
                }
            }
            objs[19] = commissionManageVO1.getComissionBelong();
            objs[20] = commissionManageVO1.getRemarkThree();
            objs[21] = commissionManageVO1.getAgencyName();
            objs[22] = commissionManageVO1.getSchoolName();
            objs[23] = commissionManageVO1.getSchoolArea();
            objs[24] = commissionManageVO1.getSchoolNo();

            if (StringUtils.hasText(commissionManageVO1.getCollegeType())) {
                if (commissionManageVO1.getCollegeType().equals("1")) {
                    commissionManageVO1.setCollegeType("主课");
                } else if (commissionManageVO1.getCollegeType().equals("2")) {
                    commissionManageVO1.setCollegeType("语言");
                } else if (commissionManageVO1.getCollegeType().equals("3")) {
                    commissionManageVO1.setCollegeType("预备");
                }else if (commissionManageVO1.getCollegeType().equals("4")) {
                    commissionManageVO1.setCollegeType("快捷");
                }else if (commissionManageVO1.getCollegeType().equals("5")) {
                    commissionManageVO1.setCollegeType("夏校");
                }else if (commissionManageVO1.getCollegeType().equals("6")) {
                    commissionManageVO1.setCollegeType("桥梁");
                }else if (commissionManageVO1.getCollegeType().equals("7")) {
                    commissionManageVO1.setCollegeType("top up");
                }

            }
            objs[25] = commissionManageVO1.getCollegeType();
            objs[26] = commissionManageVO1.getCourseName();
            objs[27] = commissionManageVO1.getMajorName();
            objs[28] = commissionManageVO1.getStudyWeek();
            if(commissionManageVO1.getStartDate() != null){
                objs[29] = commissionManageVO1.getStartDate().getTime();
            }else{
                objs[29] = null;
            }
            if (commissionManageVO1.getEndDate() != null) {
                objs[30] = commissionManageVO1.getEndDate().getTime();
            } else {
                objs[30] = null;
            }
            objs[31] = commissionManageVO1.getPaidFee();
            objs[32] = commissionManageVO1.getTuition();
            if (StringUtils.hasText(commissionManageVO1.getStatus())) {
                if (commissionManageVO1.getStatus().equals("1")) {
                    commissionManageVO1.setStatus("未开始");
                } else if (commissionManageVO1.getStatus().equals("2")) {
                    commissionManageVO1.setStatus("进行中");
                } else if (commissionManageVO1.getStatus().equals("3")) {
                    commissionManageVO1.setStatus("已结束");
                } else if (commissionManageVO1.getStatus().equals("4")) {
                    commissionManageVO1.setStatus("已取消");
                }
            }
            objs[33] = commissionManageVO1.getStatus();
            objs[34] = commissionManageVO1.getRate();
            objs[35] = commissionManageVO1.getInvoiceNo();
            if (commissionManageVO1.getSendDate() != null) {
                objs[36] = commissionManageVO1.getSendDate().getTime();
            } else {
                objs[36] = null;
            }

            objs[37] = commissionManageVO1.getInvoiceMoney();
            objs[38] = commissionManageVO1.getInvoiceGst();
            objs[39] = commissionManageVO1.getInvoiceSum();
            if (commissionManageVO1.getReturnDate() != null) {
                objs[40] = commissionManageVO1.getReturnDate().getTime();
            } else {
                objs[40] = null;
            }
            objs[41] = commissionManageVO1.getBankAccount();
            objs[42] = commissionManageVO1.getReturnCurrency();
            objs[43] = commissionManageVO1.getAccountCurrency();
            objs[44] = commissionManageVO1.getCurrency();
            objs[45] = commissionManageVO1.getActualAmount();
            objs[46] = commissionManageVO1.getAccountGst();
            objs[47] = commissionManageVO1.getAccountSum();
            objs[48] = commissionManageVO1.getBankFee();
            objs[49] = commissionManageVO1.getAccountMoney();
            objs[50] = commissionManageVO1.getBalance();
            objs[51] = commissionManageVO1.getBalanceType();
            objs[52] = commissionManageVO1.getStudentProperty();

            dataList.add(objs);
        }
        ExcelServiceImpl ex = new ExcelServiceImpl(title, rowsName, dataList, response);
        ex.export();
    }


    private StringBuffer obj16(CommissionManageVO commissionManageVO1) {
        StringBuffer str  = new StringBuffer();
        String s = commissionManageVO1.getCourseProperty();
        if (StringUtils.hasText(commissionManageVO1.getCourseProperty())) {
            if (s.contains("1")) {
                str.append("新学位");
                str.append(",");
            }
            if (s.contains("2")) {
                str.append("转学");
                str.append(",");
            }
            if (s.contains("3")) {
                str.append("转专业");
                str.append(",");
            }
            if (s.contains("4")) {
                str.append("转课程");
                str.append(",");
            }
            if (s.contains("5")) {
                str.append("加读语言");
                str.append(",");
            }
            if (s.contains("6")) {
                str.append("填缝");
                str.append(",");
            }
            if (s.contains("7")) {
                str.append("延期");
                str.append(",");
            }
            if (s.contains("8")) {
                str.append("原打包");
                str.append(",");
            }
            if (s.contains("9")) {
                str.append("补课");
                str.append(",");
            }
            //删除字符串中最后一个","号
            /*if(str!=null&&!str.equals("")){
                StringBuffer delete = str.deleteCharAt(str.length()-1);
            }*/
        }


        return str ;
    }

    @Override
    public Integer  loginRoleName() {
        SysUser sysUser = userService.getLoginUser();
        String roleName = "";
        if (sysUser.getOaid() != null) {
            //查询oaid对应的角色
            List<String> roles = sysRoleMapper.getRoleByOaId(sysUser.getOaid());
            if (roles != null && roles.size() == 1) {
                roleName = roles.get(0);
            } else if (roles.size() > 1) {
                if(roles.contains("佣金系统_海外")){
                    roleName="佣金系统_海外";
                }
                if(roles.contains("佣金系统_海外操作")){
                    roleName="佣金系统_海外操作";
                }
            }
        }
        if(roleName.equals("佣金系统_海外")){
            return 1;
        }else if(roleName.equals("佣金系统_海外操作")){
            return 2;
        }else{
            return 3;
        }
    }

    @Override
    public List<CommissionStudent> getCommissionStudentList(CommissionStudent commissionStudent) {
        return commissionStudentMapper.select(commissionStudent);
    }

    public static void main(String args[]){


    }
}
