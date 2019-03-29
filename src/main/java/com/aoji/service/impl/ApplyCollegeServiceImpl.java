package com.aoji.service.impl;

import com.aoji.contants.*;
import com.aoji.mapper.*;
import com.aoji.model.*;
import com.aoji.service.*;
import com.aoji.utils.HttpUtils;
import com.aoji.vo.ApplyCollegeVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author yangsaixing
 * @description
 * @date Created in 下午2:31 2017/12/7
 */
@Service
public class ApplyCollegeServiceImpl implements ApplyCollegeService {
    @Autowired
    ApplyInfoMapper applyInfoMapper;

    @Autowired
    ApplyResultInfoMapper applyResultInfoMapper;

    @Autowired
    SupplementInfoService supplementInfoService;

    @Autowired
    ExpressCompanyService expressCompanyService;

    @Autowired
    private FollowServiceInfoMapper followServiceInfoMapper;

    @Autowired
    StudentInfoMapper studentInfoMapper;

    @Autowired
    StudentService studentService;

    @Autowired
    UserService userService;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    SendStudentMessageService sendStudentMessageService;

    @Autowired
    FileService fileService;

    @Value("${upload.ks3.resDomain}")
    private String resDomain;

    @Value("${upload.ks3.newResDomain}")
    private String newResDomain;

    @Value("${sendMessageAPP.enable}")
    private Boolean sendMessageAPPEnable;
    @Value("${sendMessageAPP.url}")
    private String sendMessageAPPUrl;

    private Logger logger= LoggerFactory.getLogger(ApplyCollegeServiceImpl.class);

    @Override
    public List<ApplyInfo> getByPage(ApplyInfo applyInfo, List<String> roles, String oaid) {
        Example example=new Example(ApplyInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("applyStatusCode",applyInfo.getApplyStatusCode()).andEqualTo("studentNo",applyInfo.getStudentNo())
        .andEqualTo("deleteStatus",false);
//        if(roles != null && roles.contains(Contants.ROLE_OUTREACH_MANAGER)){
////            看到外联经理不为空的
//            criteria.andIsNotNull("connector");
//            criteria.andNotEqualTo("connector", "");
//        }else if(roles != null && roles.contains(Contants.ROLE_OUTREACH_CONSULTANT)){
////            外联顾问看到自己的
//            criteria.andEqualTo("connector", oaid);
//        }
        example.orderBy("plan_id").asc().orderBy("apply_status_code").desc();
        return applyInfoMapper.selectByExample(example);
    }

    /**
     *
     *  分页查询入读院校的列表
     *
     */
    @Override
    public List<ApplyInfo> selectByPlanCourseStatus(ApplyInfo applyInfo) {

        return applyInfoMapper.selectByPlanCourseStatus(applyInfo);
    }

    /**
     *
     *  分页查询入读院校的列表(pdf)
     *
     */
    @Override
    public Map<String, Object> selectPdfByPlanCourseStatus(ApplyInfo applyInfo) {
        Map<String, Object> data2 = new HashMap<String, Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") ;
        List<ApplyInfo> applyInfos = applyInfoMapper.selectByPlanCourseStatus(applyInfo);
        Double finallyApplyCost=0.00;
        if(applyInfos!=null&&applyInfos.size()>0){
            if(applyInfos.get(0) != null){
                for(int i = 0 ;i<applyInfos.size();i++){
                    if(applyInfos.get(i).getCourseName()!=null){
                        data2.put("ProgramRow"+(i+1), applyInfos.get(i).getCourseName());
                    }else{
                        data2.put("ProgramRow"+(i+1),"");
                    }

                    if ( applyInfos.get(i).getSchoolLength()!=null){
                        data2.put("Program DurationRow"+(i+1), applyInfos.get(i).getSchoolLength());
                    }else{
                        data2.put("Program DurationRow"+(i+1), "");
                    }

                    if(applyInfos.get(i).getCourseStartTime()!=null){
                        data2.put("Commencement DateRow"+(i+1),sdf.format(applyInfos.get(i).getCourseStartTime()));
                    }else {
                        data2.put("Commencement DateRow"+(i+1),"");
                    }
                    if(applyInfos.get(i).getCollegeName()!=null){
                        data2.put("UniversitySchoolRow"+(i+1), applyInfos.get(i).getCollegeName());
                        data2.put("cost29", "["+applyInfos.get(0).getCollegeName()+"].");
                    }else{
                        data2.put("UniversitySchoolRow"+(i+1),"");
                        data2.put("cost29","[   ].");
                    }

                    if(applyInfos.get(i).getMajorName()!=null){
                        data2.put("CourseRow"+(i+1), applyInfos.get(i).getMajorName());
                    }else{
                        data2.put("CourseRow"+(i+1),"");
                    }
                    if(applyInfos.get(i).getRegistrationFee()!=null&&applyInfos.get(i).getTuition()!=null&&applyInfos.get(i).getDepositAmount()!=null){
                        /*data2.put("cost"+(i+33),CostLanguageEnum.tuitionChinese+" "+"("+CostLanguageEnum.tuitionEnglish+")"+":"+applyInfos.get(i).getTuition());
                        data2.put("cost"+(i+36), CostLanguageEnum.registrationFeeChinese+" "+"("+CostLanguageEnum.registrationFeeEnglish+")"+":"+applyInfos.get(i).getRegistrationFee());
                        data2.put("cost"+(i+39), CostLanguageEnum.depositAmountChinese+" "+"("+CostLanguageEnum.depositAmountEnglish+")"+":"+applyInfos.get(i).getDepositAmount());*/
                        finallyApplyCost+=applyInfos.get(i).getTuition().doubleValue()+applyInfos.get(i).getRegistrationFee().doubleValue()+applyInfos.get(i).getDepositAmount().doubleValue();
                        if(applyInfos.get(i).getTuition().doubleValue()==0){
                            data2.put("cost"+(i+33),"");
                        }else{
                            data2.put("cost"+(i+33),CostLanguageEnum.tuitionChinese+" "+"("+CostLanguageEnum.tuitionEnglish+")"+":"+applyInfos.get(i).getTuition());
                        }
                        if(applyInfos.get(i).getRegistrationFee().doubleValue()==0){
                            data2.put("cost"+(i+36),"");
                        }else{
                            data2.put("cost"+(i+36), CostLanguageEnum.registrationFeeChinese+" "+"("+CostLanguageEnum.registrationFeeEnglish+")"+":"+applyInfos.get(i).getRegistrationFee());
                        }
                        if(applyInfos.get(i).getDepositAmount().doubleValue()==0){
                            data2.put("cost"+(i+39),"");
                        }else{
                            data2.put("cost"+(i+39), CostLanguageEnum.depositAmountChinese+" "+"("+CostLanguageEnum.depositAmountEnglish+")"+":"+applyInfos.get(i).getDepositAmount());
                        }
                    }else{
                        data2.put("cost"+(i+33),"");
                        data2.put("cost"+(i+36),"");
                        data2.put("cost"+(i+39),"");
                        finallyApplyCost+=0.00;
                    }
                    if(i >=2){
                        break;
                    }
                }
            }
        }
        data2.put("finallyApplyCost",finallyApplyCost);

        if(applyInfos != null && applyInfos.size() >0){
            if(applyInfos.get(0) != null){
                Date  date =applyInfos.get(0).getCreateTime();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_MONTH, +15);//+1今天的时间加一天
                date = calendar.getTime();
                data2.put("cost26",sdf.format(date));//缴费截止日期
            }
        }else{
            data2.put("cost26","");//缴费截止日期
        }

        return  data2;
    }





    //销售顾问查看院校详情
    @Override
    public List<ApplyInfo> getSaleByPage(ApplyInfo applyInfo) {
        Example example=new Example(ApplyInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("applyStatusCode",applyInfo.getApplyStatusCode()).andEqualTo("studentNo",applyInfo.getStudentNo())
                .andEqualTo("deleteStatus",false);
//        if(roles != null && roles.contains(Contants.ROLE_OUTREACH_MANAGER)){
////            看到外联经理不为空的
//            criteria.andIsNotNull("connector");
//            criteria.andNotEqualTo("connector", "");
//        }else if(roles != null && roles.contains(Contants.ROLE_OUTREACH_CONSULTANT)){
////            外联顾问看到自己的
//            criteria.andEqualTo("connector", oaid);
//        }
        example.orderBy("plan_id").asc().orderBy("apply_status_code").desc();
        return applyInfoMapper.selectByExample(example);
    }

    @Override
    public ApplyInfo getById(Integer id) {
//        ApplyInfo applyInfo = applyInfoMapper.selectByPrimaryKey(id);
//        if(applyInfo != null && StringUtils.hasText(applyInfo.getCollegeMaterial()) && !applyInfo.getCollegeMaterial().contains(resDomain)){
//            applyInfo.setCollegeMaterial( resDomain +applyInfo.getCollegeMaterial());
//        }
        return applyInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public ApplyCollegeVo getApplyDetail(Integer applyId) {
        ApplyCollegeVo applyCollegeVo=new ApplyCollegeVo();
        ApplyInfo apply=getById(applyId);
        if(apply!=null){
            if(StringUtils.hasText(apply.getCollegeMaterial()) && !apply.getCollegeMaterial().contains(newResDomain)){
//                apply.setCollegeMaterial(resDomain +apply.getCollegeMaterial());
                apply.setCollegeMaterial(fileService.getPrivateUrl(apply.getCollegeMaterial()));
            }
            if(StringUtils.hasText(apply.getCollegeCopy()) && !apply.getCollegeCopy().contains(newResDomain)){
//                apply.setCollegeCopy( resDomain +apply.getCollegeCopy());
                apply.setCollegeCopy(fileService.getPrivateUrl(apply.getCollegeCopy()));
            }
            applyCollegeVo.setApply(apply);
        }
        List<SupplementInfo> supplementInfos=supplementInfoService.getList(new SupplementInfo(applyId,Contants.SUPPLEMENTTYPE_FIRT));
        if(supplementInfos.size()>0){
            applyCollegeVo.setSupplementInfo(supplementInfos.get(0));

        }
        applyCollegeVo.setExpressCompanyList(expressCompanyService.getList(new ExpressCompany(false)));
        return applyCollegeVo;
    }

    //NPA非私密附件，私密附件地址前台获取，不绑定
    @Override
    public ApplyCollegeVo getApplyDetailNPA(Integer applyId) {
        ApplyCollegeVo applyCollegeVo=new ApplyCollegeVo();
        ApplyInfo apply=getById(applyId);
        if(apply!=null){
            if(StringUtils.hasText(apply.getCollegeMaterial()) && !apply.getCollegeMaterial().contains(resDomain) && !apply.getCollegeMaterial().contains(newResDomain)){
                apply.setCollegeMaterial(resDomain +apply.getCollegeMaterial());
//                apply.setCollegeMaterial(fileService.getPrivateUrl(apply.getCollegeMaterial()));
            }
            if(StringUtils.hasText(apply.getCollegeCopy()) && !apply.getCollegeCopy().contains(resDomain) && !apply.getCollegeCopy().contains(newResDomain)){
                apply.setCollegeCopy( resDomain +apply.getCollegeCopy());
//                apply.setCollegeCopy(fileService.getPrivateUrl(apply.getCollegeCopy()));
            }
            applyCollegeVo.setApply(apply);
        }
        List<SupplementInfo> supplementInfos=supplementInfoService.getListNPA(new SupplementInfo(applyId,Contants.SUPPLEMENTTYPE_FIRT));
        if(supplementInfos.size()>0){
            applyCollegeVo.setSupplementInfo(supplementInfos.get(0));

        }
        applyCollegeVo.setExpressCompanyList(expressCompanyService.getList(new ExpressCompany(false)));
        return applyCollegeVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveApplyAndSupplement(ApplyCollegeVo applyCollegeVo)   {
        SysUser sysUser = userService.getLoginUser();
        Integer updateApplyRows=0;
        Integer updateSupplement=1;
        if(applyCollegeVo==null || applyCollegeVo.getApply()==null || applyCollegeVo.getSupplementInfo()==null ||StringUtils.isEmpty(applyCollegeVo.getApply().getId())){
           throw new ContentException(1001,"申请信息缺失");
        }
        //1、更新申请信息
        ApplyInfo applyInfo=applyCollegeVo.getApply();
        applyInfo.setUpdateTime(new Date());
        applyInfo.setOperatorNo(sysUser.getOaid());
        applyInfo.setOperatorName(sysUser.getUsername());
        //查询申请信息
        ApplyInfo applyDB=applyInfoMapper.selectByPrimaryKey(applyInfo.getId());
        StudentInfo studentInfo = studentInfoMapper.getStudentMaterial(applyInfo.getId()+"");
        if(applyDB==null || StringUtils.isEmpty(applyDB.getApplyStatusCode())){
            throw new ContentException(2001,"申请信息有误，请检查！");
        }
        //添加录入人
        if(applyDB.getApplyStatusCode().equals(ApplyCollegeStatus.NO_COLLEGE_MATERIAL.getCode())){

                sendStudentMessageService.sendStudentMessage(sysUser,NoteMessageStatus.NOTE_NO_COLLEGE_RESULT.getCode(),studentInfo.getStudentNo(),false,applyDB.getCollegeName());

            applyInfo.setInputNo(userService.getLoginUser().getOaid());
            applyInfo.setApplyStatusCode(ApplyCollegeStatus.NO_COLLEGE_RESULT.getCode());
        }
        updateApplyRows= update(applyInfo);
        SupplementInfo supplementParam=applyCollegeVo.getSupplementInfo();

        //2、更新寄件信息
        if(applyInfo!=null && !StringUtils.isEmpty(applyInfo.getApplyWay()) ){
            List<SupplementInfo> supplementInfos=supplementInfoService.getListBySupplement(new SupplementInfo(applyCollegeVo.getApply().getId(),Contants.SUPPLEMENTTYPE_FIRT));
            //邮寄
            if(applyInfo.getApplyWay().equals(Contants.APPLYTYPE_PACKAGE)){
                if (supplementInfos.size() != 0) {
                    //更新寄件信息
                    if(supplementInfos.size()==1){
                        SupplementInfo supplementInfo=supplementInfos.get(0);
                        supplementInfo.setExpressCompanyCode(supplementParam.getExpressCompanyCode()==null?"":supplementParam.getExpressCompanyCode());
                        supplementInfo.setExpressCompanyName(supplementParam.getExpressCompanyName()==null?"":supplementParam.getExpressCompanyName());
                        supplementInfo.setExpressNo(supplementParam.getExpressNo()==null?"":supplementParam.getExpressNo());
//                        supplementInfo.setSendDate(supplementParam.getSendDate()==null?new Date():supplementParam.getSendDate());
                        supplementInfo.setSupplementAttachment(applyInfo.getCollegeCopy());
                        supplementInfo.setOperatorNo(sysUser.getOaid());
                        supplementInfo.setOperatorName(sysUser.getUsername());
                        updateSupplement= supplementInfoService.update(supplementInfo);
                    }else{
                        logger.warn("申请id为"+applyCollegeVo.getApply().getId()+"的寄件信息异常");
                    }
                } else {
                    //新增寄件信息
                    SupplementInfo supplementInfo=applyCollegeVo.getSupplementInfo();
                    supplementInfo.setApplyId(applyCollegeVo.getApply().getId());
//                    supplementInfo.setSendDate(new Date());
                    supplementInfo.setSupplementType(Contants.SUPPLEMENTTYPE_FIRT);
                    supplementInfo.setCreateTime(new Date());
                    supplementInfo.setDeleteStatus(false);
                    supplementInfo.setOperatorNo(sysUser.getOaid());
                    supplementInfo.setOperatorName(sysUser.getUsername());
                    supplementInfo.setSupplementAttachment(applyInfo.getCollegeCopy());
                    updateSupplement=supplementInfoService.addOne(supplementInfo);
                }
                //网申、邮件
            }else{
                //删除寄件信息
                if(supplementInfos.size()==1) {
                    SupplementInfo supplementInfo = supplementInfos.get(0);
                    supplementInfo.setDeleteStatus(true);
                    updateSupplement = supplementInfoService.update(supplementInfo);
                }

            }

        }
        //将学生上传的学术材料上传到学生表中
        studentInfo.setStudentMaterial(applyInfo.getCollegeMaterial());
        Example example=new Example(StudentInfo.class);
        example.createCriteria().andEqualTo("studentNo",studentInfo.getStudentNo()).andEqualTo("deleteStatus",0);
        studentInfoMapper.updateByExampleSelective(studentInfo,example);
        //查询学生服务进程
        StudentInfo student=new StudentInfo();
        student.setStudentNo(studentInfo.getStudentNo());
        student.setDeleteStatus(false);
        student=studentService.get(student);
        if(StringUtils.isEmpty(student.getId()) ){
            logger.error("学生信息有误");
            throw new ContentException(1001,"学生信息有误");
        }
        Boolean updateStudentStatus=true;

        if(updateStudentStatus && updateApplyRows.equals(1) && updateSupplement.equals(1)){
            return true;
        }else{
            throw new ContentException(2002,"");
        }
    }

    @Override
    public Integer update(ApplyInfo applyInfo) {
        Example example=new Example(ApplyInfo.class);
        example.createCriteria().andEqualTo("id",applyInfo.getId()).andEqualTo("deleteStatus",false);
        return applyInfoMapper.updateByExampleSelective(applyInfo,example);
    }

    @Override
    public List<ApplyInfo> queryAcceptSchoolByStudentNo(String studentNo) {
        ApplyInfo applyInfo=new ApplyInfo();
        applyInfo.setStudentNo(studentNo);
        applyInfo.setDeleteStatus(false);
        applyInfo.setApplyStatusCode(ApplyCollegeStatus.COLLEGE_YES_COMPLETE.getCode());
        return applyInfoMapper.select(applyInfo);
    }

    @Override
    public List<ApplyInfo> queryApplyInfoByStudentNo(String studentNo) {
        ApplyInfo applyInfo = new ApplyInfo();
        applyInfo.setStudentNo(studentNo);
        applyInfo.setDeleteStatus(false);
        return applyInfoMapper.select(applyInfo);
    }

    @Override
    public Integer insert(ApplyInfo applyInfo) {
        applyInfo.setCreateTime(new Date());
        applyInfo.setUpdateTime(new Date());
        applyInfo.setDeleteStatus(false);
        applyInfo.setOperatorNo(userService.getLoginUser().getOaid());
        applyInfo.setOperatorName(userService.getLoginUser().getUsername());
        applyInfo.setAddOperatorNo(userService.getLoginUser().getOaid());
        applyInfo.setApplyStatusCode(ApplyCollegeStatus.NO_COLLEGE_MATERIAL.getCode());
        applyInfo.setCourseType(applyInfo.getCourseType());
        Integer i = applyInfoMapper.insertSelective(applyInfo);
        if(i >0 ){
            // 调用学生留学管家发送接口
            if(sendMessageAPPEnable) {
                String message = String.format("studentNo=%s&content=%s。", applyInfo.getStudentNo(), "您好，您的文签顾问已经为您提交院校申请了，进入申请进程了解详情");
                logger.info("添加院校申请后调用留学管家消息: " + message);
                String result = HttpUtils.sendPost2(sendMessageAPPUrl, message);
                logger.info("添加院校申请后调用留学管家消息返回结果:" + result);
            }
            else logger.info("添加院校申请后调用留学管家消息返回结果:发送消息开关处于关闭状态");
        }
        else logger.info("添加院校申请后调用留学管家消息返回结果:添加院校申请失败");
        return i;
    }

    @Override
    public String getApplyConnector(String studentNo,Integer applyId){
        String applyConnector;
        Example applyExample=new Example(StudentInfo.class);
        applyExample.createCriteria().andEqualTo("deleteStatus",false).andEqualTo("studentNo",studentNo).andEqualTo("id",applyId);
        List<ApplyInfo> list = applyInfoMapper.selectByExample(applyExample);
        if(list.size() == 1){
            if(!StringUtils.isEmpty(list.get(0).getConnector())){
                applyConnector = list.get(0).getConnector();
            }
            else{
                applyConnector = "";
            }
        }
        else{
            logger.warn("学号为"+studentNo+",申请ID为"+applyId+"的外联信息异常");
            applyConnector = "";
        }
        return applyConnector;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean acceptOffer(Integer applyId) {
        SysUser sysUser = userService.getLoginUser();
        Boolean updateAcceptStatus=true;
        Boolean updateRejectStatus=true;
        Boolean updateStudentStatus=true;
        //1、判断当前申请的状态
        ApplyInfo applyInfo=getById(applyId);
        if(applyInfo==null || StringUtils.isEmpty(applyInfo.getApplyStatusCode()) || !StringUtils.hasText(applyInfo.getStudentNo())){
            throw new ContentException(2001,"申请信息有误");
        }

        //2、判断该申请所在的定校方案的所有申请已录取
//        ApplyInfo applyInfo1 = new ApplyInfo();
//        applyInfo1.setDeleteStatus(false);
//        applyInfo1.setPlanId(applyInfo.getPlanId());
//        List<ApplyInfo> applyInfos = applyInfoMapper.select(applyInfo1);
        //定校方案的完成状态 (这个判断目前只支持两个申请，没有初始化为true是因为可能会有语言类的申请中断，这种情况暂时还没有确定)
//        boolean planStatus = true;
//        if(applyInfos.size()>1){
//            for (ApplyInfo ai : applyInfos) {
//                if (!ai.getId().equals(applyInfo.getId()) && !(Integer.valueOf(ApplyCollegeStatus.COLLEGE_YES_COMPLETE.getCode()).equals(ai.getApplyStatusCode()))) {
//                    planStatus = false;
//                }
//            }
//        }

//        //3、修改该学生其余定校方案的申请为确认拒绝已完成
//        ApplyInfo applyParam=new ApplyInfo();
//        applyParam.setStudentNo(applyInfo.getStudentNo());
//        applyParam.setPlanId(applyInfo.getPlanId());
//        applyParam.setApplyStatusCode(ApplyCollegeStatus.COLLEGE_NO_COMPLETE.getCode());
//        updateRejectStatus=updateByStudentNo(applyParam);

        //4、修改当前申请的状态为确认录取已完成
        if(applyInfo.getApplyStatusCode().equals(ApplyCollegeStatus.NO_ACCEPT.getCode())) {
            ApplyInfo apply = new ApplyInfo();
            apply.setAcceptOfferTime(new Date());
            apply.setId(applyId);
            apply.setApplyStatusCode(ApplyCollegeStatus.COLLEGE_YES_COMPLETE.getCode());
            updateAcceptStatus = update(apply) == 1;
        }

        //5、修改学生的服务进程
//        if(planStatus) {
            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setStudentNo(applyInfo.getStudentNo());
            studentInfo = studentService.get(studentInfo);
            if (studentInfo == null || StringUtils.isEmpty(studentInfo.getId()) || StringUtils.isEmpty(studentInfo.getStatus())) {
                throw new ContentException(2001, "学生信息有误");
            }
            if (!studentInfo.getStatus().equals(StudentStatus.NO_COLLEGE_INFO.getCode())) {
//                throw new ContentException(2002, "该学生状态与操作不匹配，无法执行");
            }else{
                updateStudentStatus = studentService.updateProcessStatus(studentInfo.getStudentNo(), studentInfo.getStatus(), sysUser.getOaid());
            }
//        }
        return updateAcceptStatus && updateRejectStatus && updateStudentStatus;
    }


    private Boolean updateByStudentNo(ApplyInfo applyInfo){
        Example example=new Example(ApplyInfo.class);
        example.createCriteria().andEqualTo("studentNo",applyInfo.getStudentNo())
                .andEqualTo("deleteStatus",false).andNotEqualTo("planId", applyInfo.getPlanId());
        return applyInfoMapper.updateByExampleSelective(applyInfo,example)==1;
    }

    @Override
    public ApplyInfo get(ApplyInfo applyInfo) {
        applyInfo.setDeleteStatus(false);
        List<ApplyInfo> list= applyInfoMapper.select(applyInfo);
        if(list.size() > 0){
            if(StringUtils.hasText(list.get(0).getCollegeMaterial()) && !list.get(0).getCollegeMaterial().contains(resDomain)){
                list.get(0).setCollegeMaterial( resDomain +list.get(0).getCollegeMaterial());
            }
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<String> getApplyInfoByStudentNo(String studentNo) {
        return applyInfoMapper.getApplyInfoByStudentNo(studentNo);
    }

    @Override
    public Boolean removeApply(Integer applyId) {
        SysUser sysUser = userService.getLoginUser();
        ApplyInfo applyInfo = new ApplyInfo();
        applyInfo.setId(applyId);
        applyInfo.setDeleteStatus(true);
        applyInfo.setRejectOperatorNo(sysUser.getOaid());
        return applyInfoMapper.updateByPrimaryKeySelective(applyInfo) > 0;
    }

    @Override
    public void judgeUser(Model model) {
        SysUser sysUser = userService.getLoginUser();
        List<String> roles = sysRoleMapper.getRoleByOaId(sysUser.getOaid());
        if(roles != null && roles.size() > 0){
            if(roles.contains("外联顾问") || roles.contains("外联经理") || roles.contains("运营人员")|| roles.contains("运营人员-权限")){
                model.addAttribute("canBack","true");
            }else{
                model.addAttribute("canBack","false");
            }
        }
    }

    @Override
    public Map<String, Object> getApplyCostByStudentNo(String studentNo) {

        Map<String, Object> data5 = new HashMap<String, Object>();
        ApplyInfo applyInfo = new ApplyInfo();
        applyInfo.setPlanCourseStatus(1);
        applyInfo.setDeleteStatus(false);
        applyInfo.setStudentNo(studentNo);
        List<ApplyInfo> costById = applyInfoMapper.select(applyInfo);
        for (int i=0;i<costById.size();i++){
            data5.put("",costById.get(i).getTuition());
            data5.put("",costById.get(i).getDepositAmount());
            data5.put("",costById.get(i).getRegistrationFee());
        }
        return data5;
    }

    @Override
    public ApplyInfo basicCostApplylist(ApplyInfo applyInfo) {


        return applyInfoMapper.basicCostApplylist(applyInfo);
    }
    @Override
    public Boolean basicEditFee(ApplyInfo applyInfo) {
        Example example=new Example(StudentCostInfo.class);
        applyInfo.setCreateTime(new Date());
        if(applyInfo!=null&&!applyInfo.equals("")){
            if(applyInfo.getDepositAmount()!=null&&applyInfo.getRegistrationFee()!=null&&applyInfo.getTuition()!=null){
                example.createCriteria().andEqualTo("id",applyInfo.getId()).andEqualTo("deleteStatus",false);
                return applyInfoMapper.updateByExampleSelective(applyInfo,example)==1;
            }
        }

        return false;

    }
    @Override
    public List<ApplyInfo> checkAllApplyList(ApplyInfo applyInfo,String dateStart, String dateEnd){
        return applyInfoMapper.checkAllApplyList(applyInfo,dateStart,dateEnd);
    }
}
