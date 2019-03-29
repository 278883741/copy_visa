package com.aoji.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.*;
import com.aoji.mapper.*;
import com.aoji.model.*;
import com.aoji.model.bo.StudentInfoBO;
import com.aoji.model.req.RefundEndCaseReq;
import com.aoji.model.req.SendMessageReq;
import com.aoji.model.req.StudentInfoReq;
import com.aoji.model.res.*;
import com.aoji.service.*;
import com.aoji.utils.ExcelUtils;
import com.aoji.utils.HttpUtils;
import com.aoji.vo.StudentDetailVO;
import com.aoji.vo.StudentInfoVo;
import com.aoji.vo.StudentVO;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author yangsaixing
 * @description
 * @date Created in 上午11:15 2017/11/10
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Value("${student.detail.url}")
    private String studentDetailUrl;
    @Value("${student.consultor.url}")
    private String studentConsultorUrl;
    @Value("${consultant.student}")
    private String studentInfoURL;
    @Value("${apiTaskComplete.enable}")
    private Boolean apiTaskCompleteEnable;
    @Value("${apiTaskComplete.url}")
    private String apiTaskCompleteUrl;
    @Value("${student.mobile.number.url}")
    private String studentMobileNumberUrl;
    @Value("${res.callCenter.call.register}")
    private String callRegisterUrl;
    @Value("${res.callCenter.call.index}")
    private String callIndexUrl;

    private String settleApplyURL;

    @Autowired
    private AuditApplyService auditApplyService;

    @Autowired
    private StudentDelayService studentDelayService;

    @Autowired
    StudentInfoMapper studentInfoMapper;

    @Autowired
    StudentStatusRecordMapper studentStatusRecordMapper;

    @Autowired
    StudentServiceInfoMapper studentServiceInfoMapper;

    @Autowired
    VisitInfoMapper visitInfoMapper;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserGroupRelationService userGroupRelationService;

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    AuditResultService auditResultService;

    @Autowired
    StudentSettleInfoMapper studentSettleInfoMapper;

    @Autowired
    ChannelStudentInfoMapper channelStudentInfoMapper;

    @Autowired
    BranchService branchService;

    @Autowired
    StudentService studentService;

    @Autowired
    CountryService countryService;

    @Autowired
    AuditResultInfoMapper auditResultInfoMapper;
    @Autowired
    UserTaskRelationService userTaskRelationService;
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    TransferInfoMapper transferInfoMapper;

    @Autowired
    ChannelAssignSaleconsultantMapper channelAssignSaleconsultantMapper;

    private Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    /**
     * 分页
     *
     * @param studentInfo
     * @return
     */
    @Override
    public List<StudentInfo> getStudentINfoList(HttpServletRequest request, StudentInfoVo studentInfo, String roleName,Boolean isChannelStatus,Boolean isPlannManager) {
        /**
         * 前端接受参数对象使用vo
         * PageParam 对象无法使用 mybatis的通用mapper
         */
        studentInfo.setDeleteStatus(false);

        Gson gson = new Gson();

        String toJson = gson.toJson(studentInfo);

        StudentInfo s = gson.fromJson(toJson, StudentInfo.class);

        /**
         * 此参数用于代理机构查询其机构下的学生列表
         */

        //根据用户权限和查询条件查询
        List<StudentInfo> studentInfos = this.getList(s, roleName, studentInfo.getSortPro(), studentInfo.getsSortDir_0(),isChannelStatus,isPlannManager);

        //将本次查询内容存入session,为导出做准备
        request.getSession().removeAttribute("studentQueryList");
        request.getSession().removeAttribute("loginUserByRoleName");
        request.getSession().setAttribute("studentQueryList", s);
        request.getSession().setAttribute("loginUserByRoleName", roleName);
        request.getSession().setAttribute("isChannelStatus",isChannelStatus);

        return studentInfos;
    }

    public List<StudentInfo> getList(StudentInfo studentInfo) {
        return studentInfoMapper.select(studentInfo);
    }

    public List<StudentInfo> getList(StudentInfo studentInfo, String roleName, String s1, String s2,boolean isChannelStatus,boolean isPlannManager) {
        return studentInfoMapper.selectStudentInfoByMoreCondition(studentInfo, roleName, s1, s2,isChannelStatus,isPlannManager, null, null);
    }

    /**
     *
     * @param studentInfo
     * @param roleName
     * @param pageStart
     * @param pageEnd
     * @return
     */
    public List<StudentInfo> getList(
            StudentInfo studentInfo, String roleName, String sortName, String sortOrder,
            boolean isChannelStatus, boolean isPlannManager, Integer pageStart, Integer pageEnd) {
        return studentInfoMapper.selectStudentInfoByMoreCondition(studentInfo, roleName, sortName, sortOrder,
                isChannelStatus,isPlannManager, pageStart, pageEnd);
    }

    @Override
    public List<StudentInfo> getList(StudentInfo studentInfo, String roleName) {
        return studentInfoMapper.selectStudentInfoByMoreCondition(studentInfo, roleName);
    }


    @Override
    public int getListCount(StudentInfo studentInfo, String roleName, Boolean isChannelStatus) {
        return studentInfoMapper.selectStudentInfoByMoreConditionCount(studentInfo, roleName,isChannelStatus);
    }

    @Override
    public StudentInfo getById(Integer id) {
        return studentInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer update(StudentInfo studentInfo) {
        return studentInfoMapper.updateByPrimaryKeySelective(studentInfo);
    }

    /**
     * 修改服务进程状态
     *
     * @param studentNo
     * @param currentStatus
     * @param operator
     * @return
     */
    @Override
    @Transactional
    public boolean updateProcessStatus(String studentNo, Integer currentStatus, String operator) {
        logger.info(MessageFormat.format(
                "updateProcessStatus RQ : studentNo={0}, currentStatus={1}, operator={2}", studentNo, currentStatus, operator));
        if (studentNo == null || currentStatus == null || operator == null) {
            logger.error(MessageFormat.format(
                    "Parameter Error : studentNo={0}, currentStatus={1}, operator={2}", studentNo, currentStatus, operator));
            return false;
        }
        //获取下一个状态
        List<String> serviceCodes = studentInfoMapper.selectServiceCodeByStudentNo(studentNo);
        Integer status = StudentStatus.getNextStatusCode(serviceCodes, currentStatus);
        if (status == null) {
            logger.error(MessageFormat.format("Status error ! status={0}; studentNo={1}", currentStatus, studentNo));
            return false;
        }

        //修改状态
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStatus(status);
        studentInfo.setStudentNo(studentNo);
        int updateResult = studentInfoMapper.updateByStudentNo(studentInfo);
        if (updateResult == 0) {
            return false;
        }
        // 保存修改记录
        StudentStatusRecord studentStatusRecord = new StudentStatusRecord();
        studentStatusRecord.setCreateTime(new Date());
        studentStatusRecord.setDeleteStatus(false);
        studentStatusRecord.setOperatorNo(operator);
        studentStatusRecord.setStatusCode(status);
        studentStatusRecord.setStudentNo(studentNo);
        studentStatusRecord.setUpdateTime(new Date());
        int insertResult = studentStatusRecordMapper.insert(studentStatusRecord);
        if (insertResult == 0) {
            return false;
        }
        return true;
    }

    @Override
    public List<String> getServiceByStudentNo(String studentNo) {
        return studentInfoMapper.selectServiceCodeByStudentNo(studentNo);
    }

    @Override
    public StudentInfo get(StudentInfo studentInfo) {
        studentInfo.setDeleteStatus(false);
        List<StudentInfo> list = studentInfoMapper.select(studentInfo);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
    @Override
    public StudentInfo get1(StudentInfo studentInfo) {
        List<StudentInfo> list = studentInfoMapper.select(studentInfo);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Integer getExitStudentService(String studentNo, Integer serviceId) {
        StudentServiceInfo studentServiceInfo = new StudentServiceInfo();
        studentServiceInfo.setStudentNo(studentNo);
        studentServiceInfo.setServiceId(serviceId);
        studentServiceInfo.setDeleteStatus(false);
        return studentServiceInfoMapper.selectCount(studentServiceInfo);
    }

    @Override
    public Integer getStudentsByOAAndStatus(String oaId, Integer statusCode) {
        //获取员工工号对应的角色
        String roleName = "";
        List<String> roles = this.sysRoleMapper.getRoleByOaId(oaId);
        if (roles != null && roles.size() == 1) {
            roleName = roles.get(0);
        } else if (roles.size() > 1) {
            if(roles.contains("渠道同业_文签顾问")){
                roleName = "渠道同业_文签顾问";
            }else if(roles.contains("渠道文案员_学生列表查看")){
                roleName="渠道文案员_学生列表查看";
            }else if(roles.contains("渠道文案员")){
                roleName = "渠道文案员";
            }else if (roles.contains("总经理")) {
                roleName = "总经理";
            } else if (roles.contains("文签总监")) {
                roleName = "文签总监";
            } else if (roles.contains("文签副总监")) {
                roleName = "文签副总监";
            } else if (roles.contains("文案经理")) {
                roleName = "文案经理";
            } else if (roles.contains("外联经理")) {
                roleName = "外联经理";
            } else if (roles.contains("外联顾问")) {
                roleName = "外联顾问";
            } else if (roles.contains("文案顾问")) {
                roleName = "文案顾问";
            }else if (roles.contains("预科主管")) {
                roleName = "预科主管";
            }
        }
       if(!roleName.equals("预科老师") && !roleName.equals("预科主管")){
           StudentInfo studentInfo = new StudentInfo();
           studentInfo.setStatus(statusCode);
           studentInfo.setDeleteStatus(false);
           studentInfo.setStudentStatus(1);
           studentInfo.setCopyMakerNo(oaId);
           studentInfo.setCopyOperatorNo(oaId);
           //根据登录用户获取用户组
           Boolean isChannelStatus = userService.isChannelStaus();
           return studentInfoMapper.selectStudentInfoByMoreConditionCount(studentInfo, roleName,isChannelStatus);
       }
       return 0;

    }

    @Override
    public Integer getCountByOAIdAndStatus(String oaId, Integer statusCode) {
        List<String> oaIds = getOaIdsByVisaUser(oaId);
        if (oaIds.size() == 0) {
            //文签顾问
            oaIds.add(oaId);
        }
        //2、根据查找到的oaId和状态code查询学生列表
        Example studentExample = new Example(StudentInfo.class);
        studentExample.createCriteria().andEqualTo("status", statusCode).andIn("copyOperator", oaIds).andEqualTo("deleteStatus", false);
        return studentInfoMapper.selectCountByExample(studentExample);
    }

    @Override
    public Boolean updateByStudentNo(StudentInfo studentInfo) {
        int result = studentInfoMapper.updateByStudentNo(studentInfo);
        return result > 0;
    }

    @Override
    public Boolean updateSalesConsultantByStudentNo(StudentInfo studentInfo,HttpServletRequest request) {
        int result = studentInfoMapper.updateByStudentNo(studentInfo);
        if(result>0){
            ChannelAssignSaleconsultant channelAssignSaleconsultant = (ChannelAssignSaleconsultant) request.getSession().getAttribute(Contants.CHANNEL_ASSIGN_SALECONSULTANT_LIST);
            if(studentInfo!=null){

                channelAssignSaleconsultant.setNewSalesConsultantNo(studentInfo.getSalesConsultantNo());
                channelAssignSaleconsultant.setNewSalesConsultant(studentInfo.getSalesConsultant());
            }
            channelAssignSaleconsultant.setCreateTime(new Date());
            channelAssignSaleconsultant.setDeleteStatus(false);
            SysUser sysUser = userService.getLoginUser();
            if(sysUser!=null){
                channelAssignSaleconsultant.setOperatorNo(sysUser.getOaid());
                channelAssignSaleconsultant.setOperatorName(sysUser.getUsername());
            }

            int save = channelAssignSaleconsultantMapper.insertSelective(channelAssignSaleconsultant);
        }

        return result > 0;
    }


    /**
     * 根据学生的学号查询学生的信息
     * @param studentNo
     * @return
     */
    @Override
    public StudentInfo getStudentInfoByStudentNo(String studentNo) {
        return studentInfoMapper.getStudentInfoByStudentNo(studentNo);
    }

    /**
     * 根据学生的学号查询学生的信息(pdf)
     * @param studentNo
     * @return
     */
    @Override
    public Map<String, Object> getPdfStudentInfo(String studentNo) {
        Map<String, Object> data = new HashMap<String, Object>();
        StudentInfo studentInfo = studentInfoMapper.getStudentInfoByStudentNo(studentNo);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") ;
        if(studentInfo.getStudentName()!=null){
            data.put("cost22",studentInfo.getStudentName());
            data.put("cost27",studentInfo.getStudentName()+"同学");
        }else{
            data.put("cost22","");
            data.put("cost27","");
        }
        if(studentInfo.getPinyin()!=null){
            data.put("cost23",studentInfo.getPinyin());
            data.put("cost28","["+studentInfo.getPinyin()+"],");
            //截取" "之前的字符串
            //FamilyName英文
            data.put("cost15",studentInfo.getPinyin().substring(0, studentInfo.getPinyin().indexOf(" ")));
            //截取之后的字符
            //GiveNames英文
            data.put("cost16",studentInfo.getPinyin().substring(studentInfo.getPinyin().indexOf(" ")).trim());

        }else{
            data.put("cost23","");
            data.put("cost28","[  ],");
            data.put("cost15","");
            data.put("cost16","");
        }

        if(studentInfo.getStudentNo()!=null){
            data.put("cost14",studentInfo.getStudentNo());
        }else{
            data.put("cost14","");
        }

        if(studentInfo.getBirthday()!=null){
            data.put("cost17",sdf.format(studentInfo.getBirthday()));
        }else{
            data.put("cost17","");
        }


        return data;
    }

    @Override
    public StudentDetailVO getStudentDetailByStudentNo(String studentNo) {
        //学生信息
        String json = HttpUtils.doGet(MessageFormat.format(studentDetailUrl, studentNo));
        logger.info("StudentDetail RS : " + json);
        StudentInfoRes studentInfoRes = (StudentInfoRes) JSONObject.parseObject(json, StudentInfoRes.class);
        if (studentInfoRes != null && studentInfoRes.getState() == 0) {
            return studentInfoRes.getData();
        }
        return null;
    }

    @Override
    public Map<String,String> GetPhoneNumByStudentNo(String studentNo){
        Map map = new HashMap();
        map.put("sid",studentNo);
        String json = HttpUtils.doPostForXxRes(studentMobileNumberUrl, map, String.class);
        logger.info("查询学生手机号结果: " + json);
        StudentPhoneRes studentPhoneRes = (StudentPhoneRes) JSONObject.parseObject(json, StudentPhoneRes.class);
        if (studentPhoneRes != null && studentPhoneRes.getReCode().equals("10001")) {
            return studentPhoneRes.getPhone();
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String approveDelay(StudentDelayInfo studentDelayInfo, Integer auditResultSize) {
        SysUser sysUser = userService.getLoginUser();
        String studentNo = studentDelayInfo.getStudentNo();
        List<StudentDelayInfo> studentDelayInfos = studentDelayService.getList(studentNo);
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
        // 根据学号获取student_info表id
        StudentInfo studentInfo = studentInfoMapper.getStudentInfoByStudentNo(studentNo);

        AuditApplyInfo auditApplyInfo = null;
        List<AuditResultInfo> auditResultInfos = null;
        if(studentDelayInfos.size() > 0) {
            //查询是否有审批申请
             auditApplyInfo = auditApplyService.get(studentDelayInfos.get(studentDelayInfos.size() - 1).getId(), CaseIdEnum.ApproveDelay.getCode());
            //查询是否有审批结果
             auditResultInfos = auditResultService.getList(CaseIdEnum.ApproveDelay.getCode(), studentDelayInfos.get(studentDelayInfos.size() - 1).getId());

            if (!auditResultSize.equals(auditResultInfos.size())) {
                jsonObject.put("code", 0);
                return jsonObject.toString();
            }
        }
        if (sysUser.getOaid() == null) {
            logger.error(ErrorMessage.SESSION_TIMEOUT);
            jsonObject.put("code", 2);
            return jsonObject.toString();
        }
        // 是否有缓办申请
        studentDelayInfo.setUpdateTime(new Date());

        if (studentDelayInfo.getId() == null) {
            if(auditApplyInfo == null) {
                //add
                StudentDelayInfo studentDelayInfo1 = new StudentDelayInfo();
                studentDelayInfo1.setStudentNo(studentDelayInfo.getStudentNo());
                studentDelayInfo1.setReason(studentDelayInfo.getReason());
                studentDelayInfo1.setContactDate(studentDelayInfo.getContactDate());
                studentDelayInfo1.setAttachment(studentDelayInfo.getAttachment());
                studentDelayInfo1.setOperatorNo(sysUser.getOaid());
                studentDelayInfo1.setAuditStatus(1);
                studentDelayInfo1.setDelayType(studentDelayInfo.getDelayType());
                studentDelayInfo1.setRemark(studentDelayInfo.getRemark());
                studentDelayService.insert(studentDelayInfo1);
            }
            else{
                jsonObject.put("code", 3);
                return jsonObject.toString();
            }
        } else {
            if (auditResultInfos.size() == 0) {
                //edit
                studentDelayInfo.setAuditStatus(1);
                studentDelayService.update(studentDelayInfo);
            } else {
                if (auditApplyInfo == null && auditResultInfos.get(auditResultInfos.size() - 1).getApplyStatus().equals(2)) {
                    //add
                    StudentDelayInfo studentDelayInfo1 = new StudentDelayInfo();
                    studentDelayInfo1.setStudentNo(studentDelayInfo.getStudentNo());
                    studentDelayInfo1.setReason(studentDelayInfo.getReason());
                    studentDelayInfo1.setContactDate(studentDelayInfo.getContactDate());
                    studentDelayInfo1.setAttachment(studentDelayInfo.getAttachment());
                    studentDelayInfo1.setOperatorNo(sysUser.getOaid());
                    studentDelayInfo1.setAuditStatus(1);
                    studentDelayInfo1.setDelayType(studentDelayInfo.getDelayType());
                    studentDelayInfo1.setRemark(studentDelayInfo.getRemark());
                    studentDelayService.insert(studentDelayInfo1);
                } else {
                    //edit
                    studentDelayInfo.setAuditStatus(1);
                    studentDelayService.update(studentDelayInfo);
                }
            }
        }

//        // 是否有缓办申请
//        studentDelayInfo.setUpdateTime(new Date());
//        if(studentDelayInfo.getId() != null && auditApplyInfo != null){
//            studentDelayInfo.setUpdateTime(new Date());
//            studentDelayService.update(studentDelayInfo);
//        }else{
//            // 添加缓办申请
//            StudentDelayInfo studentDelayInfo1 = new StudentDelayInfo();
//            studentDelayInfo1.setStudentNo(studentDelayInfo.getStudentNo());
//            studentDelayInfo1.setReason(studentDelayInfo.getReason());
//            studentDelayInfo1.setContactDate(studentDelayInfo.getContactDate());
//            studentDelayInfo1.setAttachment(studentDelayInfo.getAttachment());
//            studentDelayInfo1.setOperatorNo(sysUser.getOaid());
//            studentDelayService.insert(studentDelayInfo1);
//        }

        if (studentInfo == null) {
            logger.error(MessageFormat.format("studentNo error!! studentNo:{0}", studentNo));
            jsonObject.put("code", 2);
            return jsonObject.toString();
        }

        if (auditApplyInfo == null) {
            List<StudentDelayInfo> studentDelayInfosNew = studentDelayService.getList(studentNo);
            //添加审批信息
            auditApplyService.add(studentDelayInfosNew.get(studentDelayInfosNew.size()-1).getId(), CaseIdEnum.ApproveDelay.getCode(), 1, studentNo, "", "");
        }
        jsonObject.put("code", 1);
        return jsonObject.toString();
    }

    /**
     * 查找该用户作为负责人的组成员
     *
     * @param oaId
     * @return
     */
    private List<String> getOaIdsByVisaUser(String oaId) {
        return userGroupRelationService.getOaIdsByOAIdAndLeader(oaId);
    }

    @Override
    public String getCopyOperator(String studentNo) {
        String copyOperator;
        Example studentExample = new Example(StudentInfo.class);
        studentExample.createCriteria().andEqualTo("deleteStatus", false).andEqualTo("studentNo", studentNo);
        List<StudentInfo> list = studentInfoMapper.selectByExample(studentExample);
        if (list.size() == 1) {
            if (!StringUtils.isEmpty(list.get(0).getCopyOperator())) {
                copyOperator = list.get(0).getCopyOperator();
            } else {
                copyOperator = "";
            }
        } else {
            copyOperator = "";
            logger.warn("学号为" + studentNo + "的文签信息异常");
        }
        return copyOperator;
    }

    @Override
    public Consultor getConsultorByStudentNo(String studentNo) {
        //学生信息
        String json = HttpUtils.doGet(MessageFormat.format(studentConsultorUrl, studentNo));
        logger.info("StudentConsultor RS : " + json);
        ConsultorRes consultorRes = (ConsultorRes) JSONObject.parseObject(json, ConsultorRes.class);
        if (consultorRes != null && consultorRes.getState() == 0) {
            try {
                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setStudentNo(studentNo);
                studentInfo.setSalesConsultantNo(consultorRes.getData().getWorknum());
                studentInfo.setSalesConsultant(consultorRes.getData().getConsultorname());
                //调小马哥接口，更新student_info表的咨询顾问信息及工号
                //应赛星要求，改功能暂时去掉
               // studentInfoMapper.updateByStudentNo(studentInfo);
            }catch(Exception e){
                logger.error("更新咨询顾问失败！");
            }
            return consultorRes.getData();
        }
        return null;
    }

    @Override
    public List<StudentInfo> getGDUnAuditInfo(Integer Type,String studentNo,String nationName) {
       /* SysUser sysUser = userService.getLoginUser();
        if (sysUser.getOaid() == null) {
            logger.warn("当前登录人信息异常");
            return null;
        }
        if (Type == 0) {
            return null;
        }*/
        String oaId="7796";
        if (Type == 1) {
            return studentInfoMapper.getVisaUnAudit(oaId,studentNo,nationName);
        }
        if (Type == 2) {
            return studentInfoMapper.getVisaResultUnAudit(oaId,studentNo,nationName);
        }
        if (Type == 3) {
            return studentInfoMapper.getSettleUnAudit(oaId,studentNo,nationName);
        } else {
            return null;
        }
    }

    /**
     * @param response
     */
    @Override
    public void excel(HttpServletRequest request, HttpServletResponse response) {

        // 获取上次查询条件
        StudentInfo s = (StudentInfo) request.getSession().getAttribute("studentQueryList");

        String roleName = (String) request.getSession().getAttribute("loginUserByRoleName");

        Boolean isChannelStatus  = (Boolean) request.getSession().getAttribute("isChannelStatus");

        //根据登录用户查询改用户是否是规划经理
        Boolean isPlanManager = userService.isPlannManager();

        //根据用户权限和查询条件查询
        int count = this.getListCount(s, roleName, isChannelStatus);
        if(count>Contants.EXPORT_MAX){
            throw new ContentException(9998,"导出数据量过大，请您精确条件或者联系运营协助导出！");
        }

        List<StudentInfo> studentInfoList = new ArrayList<>();
        int pageSize = Contants.STUDENT_EXPORT_PAGESIZE;
        for(int i = 0; i < count/pageSize+1; i++){
            List<StudentInfo> studentInfos = this.getList(s, roleName,"sign_date","desc",
                    isChannelStatus, isPlanManager, i*pageSize, pageSize);
            studentInfoList.addAll(studentInfos);
        }


        ExcelUtils.listToExcel(studentInfoList, getStudentFoExcelFiledMap(), "学生列表信息", response);
    }

    /**
     * 定义表头列表模板
     *
     * @return
     */
    public LinkedHashMap<String, String> getStudentFoExcelFiledMap() {

        LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();

        fieldMap.put("studentNo", "学号");

        fieldMap.put("studentName", "姓名");

        fieldMap.put("pinyin", "拼音");

        fieldMap.put("birthdayString", "出生日期");

        fieldMap.put("statusName", "服务进程");

        fieldMap.put("nationName", "国家");

        fieldMap.put("salesConsultant", "咨询顾问");

        fieldMap.put("contractNo", "合同名称");

        fieldMap.put("branchName", "分支机构");

        fieldMap.put("signDateString", "签约日期");

        fieldMap.put("copyOperator", "文签顾问");

        fieldMap.put("copyMaker", "制作文案");

        fieldMap.put("copier", "文书员");

        fieldMap.put("copy", "文案员");

        fieldMap.put("visaOperator", "业务员");

        fieldMap.put("planningConsultant", "规划顾问");


        return fieldMap;
    }

    @Override
    public void excelVisitInfo(HttpServletRequest request, HttpServletResponse response) {
        SysUser sysUser = userService.getLoginUser();
        // 获取上次查询条件
        StudentInfo studentInfo = (StudentInfo) request.getSession().getAttribute("excelVisitInfoQuery");
        String roleName = "";
        List<String> roles = sysRoleMapper.getRoleByOaId(sysUser.getOaid());
        if (roles != null && roles.size() == 1) {
            roleName = roles.get(0);
        }
        else if (roles.size() > 1) {
            if (roles.contains("总经理")) {
                roleName = "总经理";
            } else if (roles.contains("文签总监")) {
                roleName = "文签总监";
            } else if (roles.contains("文案经理")) {
                roleName = "文案经理";
            } else if (roles.contains("外联经理")) {
                roleName = "外联经理";
            } else if (roles.contains("外联顾问")) {
                roleName = "外联顾问";
            } else if (roles.contains("文案顾问")) {
                roleName = "文案顾问";
            }
        }
        List<StudentInfo> list = getStudentListByOaId(studentInfo, sysUser.getOaid(), roleName);
        for (StudentInfo item:list) {
            if (!StringUtils.isEmpty(item.getBirthday())) {
                item.setBirthdayString(new SimpleDateFormat(Contants.datePattern).format(item.getBirthday()));
            }
            if (!StringUtils.isEmpty(item.getSignDate())) {
                item.setSignDateString(new SimpleDateFormat(Contants.datePattern).format(item.getSignDate()));
            }
            if (!StringUtils.isEmpty(item.getLastVisitTime())) {
                item.setLastVisitTimeString(new SimpleDateFormat(Contants.datePattern).format(item.getLastVisitTime()));
            }
            if(item.getStatus() != null){
                switch (item.getStatus()){
                    case 10 :  item.setStatusString("未上传文书材料");break;
                    case 20 :  item.setStatusString("未递交申请材料");break;
                    case 30 :  item.setStatusString("未收到申请结果");break;
                    case 40 :  item.setStatusString("未确认录取院校");break;
                    case 50 :  item.setStatusString("未进行签证辅导");break;
                    case 60 :  item.setStatusString("未递交签证申请");break;
                    case 70 :  item.setStatusString("未收到签证结果");break;
                    case 80 :  item.setStatusString("未填写获签信息");break;
                    case 90 :  item.setStatusString("已结案");break;
                    case 100 :  item.setStatusString("已退费");break;
                    case 85 :  item.setStatusString("待结案");break;
                    default:break;
                }
            }
        }
        ExcelUtils.listToExcel(list, getVisitInfoExcelFiledMap(), "待回访学生列表信息", response);
    }
    public LinkedHashMap<String, String> getVisitInfoExcelFiledMap() {
        LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
        fieldMap.put("studentNo", "学号");
        fieldMap.put("studentName", "姓名");
        fieldMap.put("pinyin", "拼音");
        fieldMap.put("birthdayString", "出生日期");
        fieldMap.put("salesConsultant", "销售顾问");
        fieldMap.put("copyOperator", "文签顾问");
        fieldMap.put("copyMaker", "制作文案");
        fieldMap.put("copier", "文书员");
        fieldMap.put("copy", "文案员");
        fieldMap.put("visaOperator", "签证员");
        fieldMap.put("branchName", "分支机构");
        fieldMap.put("signDateString", "签约日期");
        fieldMap.put("statusString", "服务进程");
        fieldMap.put("lastVisitTimeString", "最近回访时间");
        return fieldMap;
    }

    /**
     * 根据oaId获取待回访学生列表
     *
     * @param oaId
     * @return
     */
    @Override
    public List<StudentInfo> getStudentListByOaId(StudentInfo studentInfo, String oaId, String roleName) {
        if ("总经理".equals(roleName) || "文签总监".equals(roleName) || "文案经理".equals(roleName) || "文案顾问".equals(roleName)) {
            return studentInfoMapper.getStudentListByOaId(studentInfo, oaId, roleName);
        }
        return null;
    }


    @Override
    public StudentListRes getStudentByBranchAndCountry(StudentInfoReq studentInfoReq) {
        StudentListRes respose = new StudentListRes();
        Integer begin = (studentInfoReq.getPage() - 1) * studentInfoReq.getPageSize();
        Integer end = studentInfoReq.getPage() * studentInfoReq.getPageSize();
        int count = studentInfoMapper.getStudentByBranchAndCountryCount(studentInfoReq);
        List<StudentInfoBO> studentInfoBOS = studentInfoMapper.getStudentByBranchAndCountry(studentInfoReq, studentInfoURL, begin, end);
        respose.setStudentInfoBOList(studentInfoBOS);
        respose.setCount(count);
        return respose;
    }

    @Override
    public Integer addStudentService(String studentNo, String addMessage) {
        StudentServiceInfo studentServiceInfo = new StudentServiceInfo();
        SysUser sysUser = userService.getLoginUser();
        studentServiceInfo.setDeleteStatus(false);
        studentServiceInfo.setServiceId(18);
        studentServiceInfo.setStudentNo(studentNo);
        studentServiceInfo.setCreateTime(new Date());
        studentServiceInfo.setOperatorNo(sysUser.getOaid());
        studentServiceInfo.setRemark(addMessage);
        return studentServiceInfoMapper.insert(studentServiceInfo);
    }

    @Override
    public List<StudentVO> getIsSettleStudents(String oaid) {
        return studentInfoMapper.getIsSettleStudents(oaid);
    }


    @Override
    public Boolean getIsSettle(String studentNo) {
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setDeleteStatus(false);
        studentInfo.setStudentNo(studentNo);
        List<StudentInfo> studentList = studentInfoMapper.select(studentInfo);
        if(studentList == null){
            return null;
        }else if(studentList != null && studentList.size() > 0 && studentList.get(0).getStatus() != null){
            if( studentList.get(0).getStatus() == StudentStatus.COMPLETED.getCode()){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    @Override
    public List<StudentInfo> getSettleList(StudentInfo studentInfo){
        return studentSettleInfoMapper.getSettleList(studentInfo);
    }

    @Override
    public List<StudentInfo> getDisabledList(String studentNo, String studentName){
        List<StudentInfo> list = studentInfoMapper.getDisabledList(studentNo,studentName);
        return list;
    }
    @Override
    public Integer getDisabledListCount(String studentNo, String studentName) {
        return studentInfoMapper.getDisabledListCount(studentNo,studentName);
    }

    @Override
    public ChannelStudentInfo getStudentMessageByStudentNo(String studentNo) {
        ChannelStudentInfo channelStudentInfo = null;
        try {
            channelStudentInfo = channelStudentInfoMapper.selectChannelStudentInfoByStudentNo(studentNo);
        } catch (Exception e) {
            logger.info("方法selectChannelStudentInfoByStudentNo通过学号查询，channel_student_info表里数据不唯一  studentNo："+studentNo);
            e.printStackTrace();
        }
        return  channelStudentInfo;
    }

    @Override
    public StudentDetailVO getChannelStudentInfo(ChannelStudentInfo channelStudentInfo, StudentDetailVO studentDetailVO, String studentNo, int i) {
        //当i唯1时，则查询同业学生信息数据，否则，查询资源系统学生详细信息数据
        if (i == 1) {
            //学生信息
            studentDetailVO.setStudentName(channelStudentInfo.getStudentName());
            studentDetailVO.setPinyin(channelStudentInfo.getPinyin());
            SimpleDateFormat sdf = new SimpleDateFormat(Contants.datePattern);
            String birthday=null;
            if(channelStudentInfo.getBirthday()!=null&&!channelStudentInfo.getBirthday().equals("")){
                birthday = sdf.format(channelStudentInfo.getBirthday());
            }
            studentDetailVO.setBirthday(birthday);
            studentDetailVO.setGender(channelStudentInfo.getGender());
            studentDetailVO.setEmail(channelStudentInfo.getEmail());
            BranchInfo branchInfo = new BranchInfo();
            //分支机构
            branchInfo.setBranchId(channelStudentInfo.getBranchId());
            List<BranchInfo> branchInfos = branchService.getList(branchInfo);
            if (!branchInfos.isEmpty()) {
                studentDetailVO.setBranchName(branchInfos.get(0).getBranchName());
            }
            CountryInfo countryInfo = new CountryInfo();
            countryInfo.setCountryBussid(channelStudentInfo.getNationId());
            List<CountryInfo> countryInfos = countryService.getList(countryInfo);
            if (!countryInfos.isEmpty()) {
                studentDetailVO.setNationName(countryInfos.get(0).getCountryName());
            }
            Consultor consultor = studentService.getConsultorByStudentNo(studentNo);
            if (consultor != null) {
                studentDetailVO.setConsultorName(consultor.getConsultorname());
            }
        } else {
            //调取资源系统，查询学生详细信息
            String json = HttpUtils.doGet(MessageFormat.format(studentDetailUrl, studentNo));
            StudentInfoRes studentInfoRes = (StudentInfoRes) JSONObject.parseObject(json, StudentInfoRes.class);
            //合同信息
            if (studentInfoRes != null && studentInfoRes.getState() == 0) {
                studentDetailVO = studentInfoRes.getData();
                BranchInfo branchInfo = new BranchInfo();
                branchInfo.setBranchId(Integer.valueOf(studentDetailVO.getBranch()));
                List<BranchInfo> branchInfos = branchService.getList(branchInfo);
                if (!branchInfos.isEmpty()) {
                    studentDetailVO.setBranchName(branchInfos.get(0).getBranchName());
                }
                CountryInfo countryInfo = new CountryInfo();
                countryInfo.setCountryBussid(Integer.valueOf(studentDetailVO.getNation()));
                List<CountryInfo> countryInfos = countryService.getList(countryInfo);
                if (!countryInfos.isEmpty()) {
                    studentDetailVO.setNationName(countryInfos.get(0).getCountryName());
                }
                Consultor consultor = studentService.getConsultorByStudentNo(studentNo);
                if (consultor != null) {
                    studentDetailVO.setConsultorName(consultor.getConsultorname());
                }
            }
        }

        return  studentDetailVO;
    }

    @Override
    public List<String> getIsSettle1(String studentNo) {
        List<String> studentNoList = java.util.Arrays.asList(studentNo.split(","));
        List<String> studentList = studentInfoMapper.getStudentNoList(studentNoList);
        List<String> studentNos = new ArrayList<>();
        for(int i=0; i<studentNoList.size(); i++){
            if(!studentList.contains(studentNoList.get(i))){
                studentNos.add(studentNoList.get(i));
            }
        }
        return studentNos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean refundEndCase(RefundEndCaseReq refundEndCaseReq) {
        String studentNo = refundEndCaseReq.getStudentNo();
        String operatorNo = refundEndCaseReq.getOperatorNo();
        String operatorName = refundEndCaseReq.getOperatorName();

        //修改状态
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStatus(StudentStatus.COMPLETED.getCode());
        studentInfo.setStudentNo(studentNo);
        int updateResult = studentInfoMapper.updateByStudentNo(studentInfo);
        StudentStatusRecord studentStatusRecord = new StudentStatusRecord();
        if (updateResult > 0) {
            // 保存修改记录
            Date date = new Date();
            studentStatusRecord.setCreateTime(date);
            studentStatusRecord.setDeleteStatus(false);
            studentStatusRecord.setOperatorNo(operatorNo);
            studentStatusRecord.setStatusCode(StudentStatus.COMPLETED.getCode());
            studentStatusRecord.setStudentNo(studentNo);
            studentStatusRecord.setUpdateTime(date);
            studentStatusRecordMapper.insert(studentStatusRecord);

            // 添加结案和审批信息
            this.autoEndCaseForRefund(studentNo, operatorNo, operatorName);

            // 发送消息
            this.sendRefundMessage(studentNo, operatorNo);

            //退费后自动结案告知任务关闭
            if(apiTaskCompleteEnable){
                String result = String.format("sid=%s&isSuccess=%s&api-version=%s",studentInfo.getStudentNo(),0,"1.0");
                logger.info("退费后自动结案告知任务关闭结果为"+result);
                String s = HttpUtils.sendPost2(apiTaskCompleteUrl+"?"+result, result);
                logger.info(s);
            }
        }
        return false;
    }


    /**
     * 退费自动结案
     * @param studentNo
     * @param operatorNo
     * @param operatorName
     */
    public void autoEndCaseForRefund(String studentNo, String operatorNo, String operatorName){
        Date now = new Date();
        StudentSettleInfo studentSettleInfo = new StudentSettleInfo();
        studentSettleInfo.setDeleteStatus(false);
        studentSettleInfo.setStudentNo(studentNo);
        studentSettleInfo.setCreateTime(now);
        studentSettleInfo.setUpdateTime(now);
        studentSettleInfo.setReason(5); //退费结案
        studentSettleInfo.setAuditStatus(3); //审批通过
        studentSettleInfoMapper.insert(studentSettleInfo);

        AuditResultInfo auditResultInfo = new AuditResultInfo();
        auditResultInfo.setStudentNo(studentNo);
        auditResultInfo.setCaseId(CaseIdEnum.StudentSettle.getCode());
        auditResultInfo.setBusinessId(studentSettleInfo.getId());
        auditResultInfo.setApplyStatus(2); // 同意
        auditResultInfo.setCreateTime(now);
        auditResultInfo.setUpdateTime(now);
        auditResultInfo.setDeleteStatus(false);
        auditResultInfo.setOperatorNo(operatorNo);
        auditResultInfo.setOperatorName(operatorName);
        auditResultInfo.setReason("退费自动结案");
        auditResultInfo.setApplyId(0);
        auditResultInfoMapper.insert(auditResultInfo);
    }

    /**
     * 发送退费消息
     */
    public void sendRefundMessage(String studentNo, String operatorNo){
        StudentInfo studentInfo = studentInfoMapper.getStudentInfoByStudentNo(studentNo);
        if(studentInfo != null && StringUtils.hasText(studentInfo.getCopyOperatorNo())) {

            String copyOperatorNo = studentInfo.getCopyOperatorNo();
            List<String> oaids = sysUserMapper.getCopyManager(studentNo, copyOperatorNo);

            if(!oaids.contains(copyOperatorNo)){
                oaids.add(copyOperatorNo);
            }

            for(String oaid : oaids) {
                SendMessageReq req = new SendMessageReq();
                req.setOaid(oaid);
                req.setOperatorNo(operatorNo);
                req.setStudentNo(studentNo);
                req.setTaskType(Contants.WORK_MESSAGE);
                req.setTemplateCode("studentRefund");
                req.getTemplateParam().put(TaskTemplateKeyEnum.STUDENT_NO.getCode(), studentNo);
                req.getTemplateParam().put(TaskTemplateKeyEnum.STUDENT_NAME.getCode(), studentInfo.getStudentName());
                userTaskRelationService.sendMessage(req);
            }
        }else{
            logger.error("sendRefundMessage failed! error: copyOperatorNo is null; studentNo="+studentNo);
        }
    }

    @Override
    public List<Map> getUserMap(StudentInfo studentInfo) {
        return studentInfoMapper.getUserMap(studentInfo);
    }

    @Override
    public List<StudentInfo> getFirstBonusList(String oaid){
        return studentInfoMapper.getFirstBonusList(oaid);
    }

    @Override
    public List<StudentInfo> getToAuditFirstBonusList(String oaid,StudentInfo studentInfo){
        return studentInfoMapper.getToAuditFirstBonusList(oaid,studentInfo);
    }

    @Override
    public boolean updateStudentInfo(StudentInfo studentInfo) {
        if(StringUtils.hasText(studentInfo.getStudentNo())){
            String studentNo = studentInfo.getStudentNo().trim();
            String studentName = studentInfo.getStudentName();
            Integer nation = studentInfo.getNationId();

            // 国家信息
            CountryInfo countryInfo = new CountryInfo();
            if(nation != null){
                countryInfo.setId(nation);
                countryInfo = countryService.get(countryInfo);
            }

            // 修改学生表
            studentInfo.setStudentNo(studentNo);
            studentInfo.setNationName(countryInfo.getCountryName());
            this.updateByStudentNo(studentInfo);

            // 修改转案表
            if(StringUtils.hasText(studentName) || nation != null) {
                Example example = new Example(TransferInfo.class);
                example.createCriteria().andEqualTo("studentNo", studentNo);
                TransferInfo transferInfo = new TransferInfo();
                transferInfo.setStudentNo(studentNo);
                if(StringUtils.hasText(studentName)){
                    transferInfo.setStudentName(studentName);
                }
                if(nation != null){
                    transferInfo.setCountryGroup(countryInfo.getCountryGroup());
                }
                transferInfoMapper.updateByExampleSelective(transferInfo, example);
            }
            return true;
        }
        return false;
    }

    @Override
    public String getCallCenterUrl(String studentNo, String studentName) {
        SysUser sysUser = userService.getLoginUser();

        // 获取资源号
        Map<String, String> phoneMap = studentService.GetPhoneNumByStudentNo(studentNo);
        if(phoneMap != null){
            // 注册CC
            Map<String, String> callRegisterParam = new HashMap<>();
            callRegisterParam.put("code", "3");
            callRegisterParam.put("number", phoneMap.get("phone"));
            callRegisterParam.put("relationid", studentNo);
            callRegisterParam.put("resourceid", phoneMap.get("resourceid"));
            callRegisterParam.put("type", "1");
            callRegisterParam.put("memberid", sysUser.getOaid());
            callRegisterParam.put("username", studentName);

            JSONObject callRegisterResult = HttpUtils.doPostForXxRes(callRegisterUrl, callRegisterParam, JSONObject.class);

            if(callRegisterResult.getInteger("code").equals(0)){
                // 获取CC地址
                JSONObject jsonObject = callRegisterResult.getJSONObject("data");
                if(jsonObject != null){
                    String requestId = jsonObject.getString("requestid");
                    if(StringUtils.hasText(requestId)){
                        return MessageFormat.format(callIndexUrl, requestId);
                    }
                }
            }
        }
        return null;
    }
}
