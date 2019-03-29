package com.aoji.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.aoji.contants.CaseIdEnum;
import com.aoji.contants.Contants;
import com.aoji.contants.StudentStatus;
import com.aoji.mapper.PlanCollegeInfoMapper;
import com.aoji.mapper.PlanCollegeRecordMapper;
import com.aoji.model.*;
import com.aoji.model.req.AgentItemReq;
import com.aoji.model.req.AgentReq;
import com.aoji.model.req.SendMessageReq;
import com.aoji.service.*;
import com.aoji.utils.HttpUtils;
import com.aoji.vo.StudentVO;
import com.google.gson.Gson;
import io.swagger.annotations.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author yangsaixing
 * @description Swagger测试控制器
 * @date Created in 上午11:00 2018/1/11
 */
@Api(value = "Swagger测试控制器")
@Controller
public class SwaggerController {

    @Autowired
    UserTaskRelationService userTaskRelationService;
    @Autowired
    StudentService studentService;
    @Autowired
    ApplyCollegeService applyCollegeService;
    @Autowired
    StudentSettleService studentSettleService;
    @Autowired
    AuditApplyService auditApplyService;
    @Autowired
    AuditResultService auditResultService;
    @Autowired
    VisaApplyService visaApplyService;
    @Autowired
    VisaResultService visaResultService;
    @Autowired
    VisaRecordService visaRecordService;
    @Autowired
    VisitInfoService visitInfoService;
    @Autowired
    PlanCollegeInfoMapper planCollegeInfoMapper;
    @Autowired
    UserService userService;
    @Autowired
    PlanCollegeRecordMapper planCollegeRecordMapper;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${insertMessage.enable}")
    private Boolean insertMessageEnable;
    @Value("${insertMessage.url}")
    private String insertMessageUrl;

    /**
     * 跳转到申请编辑页
     * @return
     */
    @RequestMapping(value = "/swagger",method = RequestMethod.GET)
    @ApiOperation(value = "跳转申请编辑页页",notes = "点击院校申请编辑菜单，进行编辑。")
    public String test(@ApiParam(name = "id",value = "院校申请id")
                                   Integer id, Model model){
        return "apply/edit";
    }

    @RequestMapping(value = "/sendMessage",method = RequestMethod.GET)
    @ApiOperation(value = "发送消息",notes = "调用接口可以添加一条消息")
    @ResponseBody
    public Boolean sendMessageTest(@ApiParam(name = "sendMessageReq", value = "发送消息请求对象")
                                   SendMessageReq sendMessageReq){
        userTaskRelationService.sendMessage(sendMessageReq);
        return true;
    }

    @RequestMapping(value = "/updateStatus",method = RequestMethod.POST)
    @ApiOperation(value = "修改状态",notes = "修改学生进程状态")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "form", dataType = "String", name = "studentNo", value="学号"),
                        @ApiImplicitParam(paramType = "form", dataType = "Integer", name = "status", value = "当前状态"),
                        @ApiImplicitParam(paramType = "form", dataType = "String", name = "operator", value = "操作人")})
    @ResponseBody
    public Boolean updateStatus(String studentNo, Integer status, String operator){
        return studentService.updateProcessStatus(studentNo, status, operator);
    }


    @RequestMapping(value = "/student/service",method = RequestMethod.GET)
    @ApiOperation(value = "查询学生是否有该服务",notes = "")
    @ResponseBody
    public Integer queryExitStudentService(@ApiParam(name = "studentNo",value = "学号")
                               String studentNo,@ApiParam(name="serviceId",value="服务id") Integer serviceId){
         return studentService.getExitStudentService(studentNo,serviceId);
    }

    /**
     * 跳转到highChart页面
     * @return
     */
    @RequestMapping("/table")
    public String table(){

        return "workTable/report";
    }

    /**
     * 饼状图数据填充
     * @return
     */
    @RequestMapping("/data")
    @ResponseBody
    public JSONObject data(){
        Subject currentUser= SecurityUtils.getSubject();
        SysUser user=(SysUser) currentUser.getPrincipal();
        JSONObject chart=new JSONObject();
        chart.put("type","pie");
        chart.put("name","Student Status");
        List<JSONObject> data=new ArrayList<>();
        for(StudentStatus status : StudentStatus.values()){
            JSONObject model=new JSONObject();
            model.put("id", status.getCode());
            model.put("name", status.getName());
            model.put("y",studentService.getStudentsByOAAndStatus(user.getOaid(),status.getCode()));
            model.put("oaid","admin");
            data.add(model);
        }
        chart.put("data",data);
        return chart;
    }


    @RequestMapping(value = "/getCopyOperator",method = RequestMethod.POST)
    @ApiOperation(value = "查询文签",notes = "查询该学生的文签联系人")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "form", dataType = "String", name = "studentNo", value="学号")})
    @ResponseBody
    public String getCopyOperator(String studentNo){
        return studentService.getCopyOperator(studentNo);
    }

    @RequestMapping(value = "/getCopyOperator1",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ApiOperation(value = "查询文签",notes = "查询该学生的文签联系人")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "form", dataType = "String", name = "studentNo", value="学号",required = true)})
    @ResponseBody
    public String getCopyOperator1(String studentNo){
        JSONObject jsonObject = new JSONObject();
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(studentNo);
        studentInfo = studentService.get(studentInfo);
        if(studentInfo == null){
            jsonObject.put("code" , 1);
            jsonObject.put("message" , "没有此学生数据");
            jsonObject.put("data","");
        }
        else{
            jsonObject.put("code" , 0);
            jsonObject.put("message" , "成功");
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("copyOperatorNo",studentInfo.getCopyOperatorNo());
            jsonObject1.put("copyOperator",studentInfo.getCopyOperator());
            jsonObject.put("data",jsonObject1);
        }

        return jsonObject.toString();
    }


    @RequestMapping(value = "/getApplyConnector",method = RequestMethod.POST)
    @ApiOperation(value = "查询外联",notes = "查询该学生此条申请的外联联系人")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "form", dataType = "String", name = "studentNo", value="学号"),
                        @ApiImplicitParam(paramType = "form", dataType = "Integer", name = "applyId", value = "申请id")})
    @ResponseBody
    public String getApplyConnector(String studentNo,Integer applyId){
        return applyCollegeService.getApplyConnector(studentNo,applyId);
    }

    @RequestMapping(value="/addStudentSettle",method = RequestMethod.POST)
    @ApiOperation(value = "添加学生结案申请",notes = "添加学生结案申请")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "studentNo", value="学号",required = true),
            @ApiImplicitParam(paramType = "form", dataType = "Integer", name = "operatorNo", value = "操作人工号",required = true),
            @ApiImplicitParam(paramType = "form", dataType = "Integer", name = "operatorName", value = "操作人姓名",required = true),
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "accachment", value = "学生确认截图"),
            @ApiImplicitParam(paramType = "form", dataType = "Integer", name = "reason", value = "结案原因：1-单文书结案 2-单申请结案 3-其他 4-自动结案 5-退费结案",required = true),
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "closeLetter", value = "套磁信")
            })
    @ResponseBody
    public boolean addStudentSettle(String studentNo,String operatorNo,String operatorName,String accachment,Integer reason,String closeLetter){
        StudentSettleInfo studentSettleInfo = new StudentSettleInfo();
        studentSettleInfo.setStudentNo(studentNo);
        if(!StringUtils.isEmpty(studentNo)){
            String studentName = studentService.getStudentInfoByStudentNo(studentNo).getStudentName();
            studentSettleInfo.setStudentName(studentName);
        }
        studentSettleInfo.setCreateTime(new Date());
        studentSettleInfo.setUpdateTime(new Date());
        studentSettleInfo.setDeleteStatus(false);
        studentSettleInfo.setOperatorNo(operatorNo);
        studentSettleInfo.setOperatorName(operatorName);
        studentSettleInfo.setAttachment(accachment);
        studentSettleInfo.setReason(reason);
        studentSettleInfo.setCloseLetter(closeLetter);
        studentSettleInfo.setAuditStatus(Contants.APPLYSTATUS_SUBMIT);
        return studentSettleService.add(studentSettleInfo,operatorNo,operatorName);
    }

    @RequestMapping(value="/updateStudentSettle",method = RequestMethod.POST)
    @ApiOperation(value = "修改学生结案申请",notes = "修改学生结案申请")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "Integer", name = "businessId", value="业务id",required = true),
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "studentNo", value="学号",required = true),
            @ApiImplicitParam(paramType = "form", dataType = "Integer", name = "operatorNo", value = "操作人工号",required = true),
            @ApiImplicitParam(paramType = "form", dataType = "Integer", name = "operatorName", value = "操作人姓名",required = true),
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "accachment", value = "学生确认截图"),
            @ApiImplicitParam(paramType = "form", dataType = "Integer", name = "reason", value = "结案原因：1-单文书结案 2-单申请结案 3-其他 4-自动结案 5-退费结案",required = true),
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "closeLetter", value = "套磁信")
    })
    @ResponseBody
    public boolean updateStudentSettle(Integer businessId,String studentNo,String operatorNo,String operatorName,String accachment,Integer reason,String closeLetter){
        StudentSettleInfo studentSettleInfo = new StudentSettleInfo();
        studentSettleInfo.setId(businessId);
        studentSettleInfo.setDeleteStatus(false);
        studentSettleInfo = studentSettleService.get(studentSettleInfo);
        if(studentSettleInfo == null){
            return false;
        }
        studentSettleInfo.setStudentNo(studentNo);
        studentSettleInfo.setUpdateTime(new Date());
        studentSettleInfo.setOperatorNo(operatorNo);
        studentSettleInfo.setOperatorName(operatorName);
        studentSettleInfo.setAttachment(accachment);
        studentSettleInfo.setReason(reason);
        studentSettleInfo.setCloseLetter(closeLetter);

        if(studentSettleInfo.getAuditStatus().equals(Contants.APPLYSTATUS_REJECT)){
            studentSettleInfo.setAuditStatus(Contants.APPLYSTATUS_SUBMIT);
            auditApplyService.add(studentSettleInfo.getId(), CaseIdEnum.StudentSettle.getCode(), 1, studentNo, operatorNo, operatorName);
        }
        else if (studentSettleInfo.getAuditStatus().equals(Contants.APPLYSTATUS_SUBMIT)) {
            if(auditApplyService.get(studentSettleInfo.getId(),CaseIdEnum.StudentSettle.getCode()) == null){
                auditApplyService.add(studentSettleInfo.getId(), CaseIdEnum.StudentSettle.getCode(), 1, studentSettleInfo.getStudentNo(),"","");
            }
        }
        return studentSettleService.update(studentSettleInfo) > 0;
    }

    @RequestMapping(value="/getVisaApplyToAudit",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    @ApiOperation(value = "获取签证申请（工号7796）待审批数据",notes = "获取签证申请（工号7796）待审批数据")
    @ResponseBody
    public String getVisaApplyToAudit(){
        return getData(CaseIdEnum.VisaApply.getCode());
    }

    @RequestMapping(value="/getVisaResultToAudit",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    @ApiOperation(value = "获取签证申请结果待审批数据",notes = "获取签证申请结果待审批数据")
    @ResponseBody
    public String getVisaResultToAudit(){
        return getData(CaseIdEnum.VisaResult.getCode());
    }

    @RequestMapping(value="/getStudentSettleToAudit",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    @ApiOperation(value = "获取手动结案待审批数据",notes = "获取手动结案待审批数据")
    @ResponseBody
    public String getStudentSettleToAudit(){
        return getData(CaseIdEnum.StudentSettle.getCode());
    }
    public String getData(Integer caseId){
        try{
            List<JSONObject> dataList=new ArrayList<>();
            JSONObject jsonObject = new JSONObject();
            Integer count = 0;

            AuditApplyInfo auditApplyInfo = new AuditApplyInfo();
            auditApplyInfo.setCaseId(caseId);
            auditApplyInfo.setOaid("7796");
            auditApplyInfo.setDeleteStatus(false);
            List<AuditApplyInfo> list = auditApplyService.getList(auditApplyInfo);
            for(AuditApplyInfo item:list){
                JSONObject obj = new JSONObject();
                switch (caseId){
                    case 5:{
                        VisaApplyInfo visaApplyInfo = new VisaApplyInfo();
                        visaApplyInfo.setId(item.getBusinessId());
                        visaApplyInfo.setDeleteStatus(false);
                        visaApplyInfo = visaApplyService.get(visaApplyInfo);
                        if(visaApplyInfo != null) {

                            obj.put("studentNo", visaApplyInfo.getStudentNo());
                            obj.put("studentName", getStudentName(visaApplyInfo.getStudentNo()));
                            dataList.add(obj);
                        }
                    };break;
                    case 6:{
                        VisaResultInfo visaResultInfo = new VisaResultInfo();
                        visaResultInfo.setId(item.getBusinessId());
                        visaResultInfo.setDeleteStatus(false);
                        visaResultInfo = visaResultService.get(visaResultInfo);
                        if(visaResultInfo != null) {
                            obj.put("studentNo", visaResultInfo.getStudentNo());
                            obj.put("studentName", getStudentName(visaResultInfo.getStudentNo()));
                            dataList.add(obj);
                        }
                    };break;
                    case 11:{
                        StudentSettleInfo studentSettleInfo = new StudentSettleInfo();
                        studentSettleInfo.setId(item.getBusinessId());
                        studentSettleInfo.setDeleteStatus(false);
                        studentSettleInfo = studentSettleService.get(studentSettleInfo);
                        if(studentSettleInfo != null) {
                            obj.put("studentNo", studentSettleInfo.getStudentNo());
                            obj.put("studentName", getStudentName(studentSettleInfo.getStudentNo()));
                            dataList.add(obj);
                        }
                    };break;
                    default:break;
                }
                count++;
            }
            JSONObject jsonResult = new JSONObject();
            jsonResult.put("StatusCode",1);
            jsonResult.put("Info","数据正确返回!");
            jsonResult.put("Data",dataList);
            return jsonResult.toJSONString();
        }catch(Exception e){
            JSONObject jsonResult = new JSONObject();
            jsonResult.put("StatusCode",0);
            jsonResult.put("Info",e.getMessage());
            jsonResult.put("Data",null);
            return jsonResult.toJSONString();
        }
    }
    public String getStudentName(String studentNo){
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(studentNo);
        studentInfo.setDeleteStatus(false);
        studentInfo = studentService.get(studentInfo);
        if(studentInfo != null){
            return studentInfo.getStudentName();
        }
        return "";
    }

    /**
     * 根据工号查询是否有未结案学生
     * @param oaid
     * @return
     */
    @RequestMapping("/student/isSettleStudent")
    @ResponseBody
    public ResponseJson getIsSettleStudent(String oaid){
        List<StudentVO> studentInfoList = studentService.getIsSettleStudents(oaid);
        Gson gson = new Gson();
        ResponseJson responseJson = new ResponseJson();
        if(!StringUtils.hasText(oaid)){
            responseJson.setMessage("请输入员工工号!");
            responseJson.setCode("-1");
            return responseJson;
        }else if(studentInfoList == null || studentInfoList.size() < 1){
            responseJson.setCode("-1");
            responseJson.setMessage("此员工学生已全部结案!");
            return responseJson;
        }else{
            responseJson.setCode("0");
            responseJson.setMessage("此员工有未结案的学生!");
            responseJson.setDate(String.valueOf(studentInfoList.size()));
            responseJson.setDateDetail(studentInfoList);
            return responseJson;
        }
    }



    /**
     * 根据学号查看学生是否已结案
     * @param studentNo
     * @return
     */
    @RequestMapping(value="student/isSettle",produces = {"application/json;charset=UTF-8"},method = RequestMethod.POST)
    @ResponseBody
    public String isSettle1(String studentNo){
        JSONObject jsonObject = new JSONObject();
        List<String> studentNoList = null;
        if(StringUtils.hasText(studentNo)){
             studentNoList= studentService.getIsSettle1(studentNo);
        }
        if(!StringUtils.hasText(studentNo)){
            jsonObject.put("code",-1);
            jsonObject.put("message","请输入学生号");
        }
        if(studentNoList == null || studentNoList.size() == 0){
            jsonObject.put("code",-1);
            jsonObject.put("message","已全部结案!");
        }else if(studentNoList != null && studentNoList.size() > 0){
            jsonObject.put("code",1);
            jsonObject.put("message","有未结案的学生");
            jsonObject.put("studentNo",studentNoList);
            jsonObject.put("data",true);
        }
        return jsonObject.toString();
    }

    /**
     * 查询回访数据
     * @param
     * @return
     */
    @RequestMapping(value="/getVisitInfo",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ApiOperation(value = "查询回访数据",notes = "查询回访数据")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "studentNo", value = "学生学号",required = true)

    })
    @ResponseBody
    public String getVisitInfo(String studentNo){
        JSONObject jsonObject = new JSONObject();
        VisitInfo visitInfo = new VisitInfo();
        visitInfo.setDeleteStatus(false);
        visitInfo.setStudentNo(studentNo);
        List<VisitInfo> list = visitInfoService.getList(visitInfo);
        if(list!=null && list.size() >0){
            jsonObject.put("code" , 1);
            jsonObject.put("data",JSONArray.toJSONString(list, SerializerFeature.WriteMapNullValue));
        }
        else{
            jsonObject.put("code" , 1);
            jsonObject.put("data","没有找到数据");
        }
        return jsonObject.toString();
    }

    @RequestMapping(value="/updatePlanCollegeStudentConfirmStatus",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ApiOperation(value = "修改定校方案学生确认状态",notes = "修改定校方案学生确认状态")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "Integer", name = "planCollegeId", value="定校方案id",required = true),
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "studentNo", value="学号",required = true),
    })
    @ResponseBody
    public boolean updatePlanCollegeStudentConfirmStatus(Integer planCollegeId,String studentNo){
        StudentInfo studentInfo = studentService.getStudentInfoByStudentNo(studentNo);
        SysUser sysUser = userService.getLoginUser();
        if(studentInfo != null){
            if(studentInfo.getUsaStatus() == null || studentInfo.getUsaStatus().equals(0)){
                PlanCollegeInfo planCollegeInfo = new PlanCollegeInfo();
                planCollegeInfo.setId(planCollegeId);
                planCollegeInfo.setDeleteStatus(false);
                planCollegeInfo.setStudentConfirmStatus(0);
                List<PlanCollegeInfo> planCollegeInfos = planCollegeInfoMapper.select(planCollegeInfo);
                if (planCollegeInfos.size() > 0 && planCollegeInfos.get(0).getAuditStatus().equals(1)) {
                    PlanCollegeInfo planCollegeInfo1 = planCollegeInfos.get(0);
                    planCollegeInfo1.setStudentConfirmStatus(1);
                    int result = planCollegeInfoMapper.updateByPrimaryKey(planCollegeInfo1);
                    if(result > 0) {
                        logger.info("学号" + studentNo + "的定校方案学生确认状态已更新");

                        PlanCollegeRecord planCollegeRecord = new PlanCollegeRecord();
                        planCollegeRecord.setType(1);
                        planCollegeRecord.setResult(1);
                        planCollegeRecord.setPlanCollegeId(planCollegeId);
                        planCollegeRecord.setCreateTime(new Date());
                        planCollegeRecord.setDeleteStatus(false);
                        planCollegeRecord.setOperatorNo("admin");
                        planCollegeRecord.setOperatorName("admin");
                        planCollegeRecord.setUpdateTime(new Date());
                        planCollegeRecord.setCanConfirmTime(new Date());

                        planCollegeRecordMapper.insert(planCollegeRecord);

                    }
                    if(insertMessageEnable) {
                        Date now = new Date();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String message = String.format("studentNoOrResourceNo=%s&type=%s&taskType=%s&title=%s&content=%s&messageTime=%s", studentInfo.getStudentNo(), "1", "4", "定校方案", "您好。您的顾问已经把定校方案发给您了，赶快去确认吧", simpleDateFormat.format(now));
                        String s = HttpUtils.sendPost2(insertMessageUrl, message);
                    }
                }
                else{
                    return false;
                }
            }
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * 根据代理编号查询获签学生数
     * @param
     * @return
     */
    @RequestMapping(value="/getCountByAgentId",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ApiOperation(value = "根据代理编号查询获签学生数",notes = "根据代理编号查询获签学生数")

    @ResponseBody
    public Object getCountByAgentId(@RequestBody @ApiParam(name="AgentList",value="传入json格式",required=true)AgentReq agentReq){
        Map<String,Object> map = new HashMap<>();
        try{
            AgentReq agentRes = new AgentReq();
            List<AgentItemReq> listResult = new ArrayList<>();

            Integer agentId = agentReq.getAgentid();
            agentRes.setAgentid(agentId);

            // 获取所有代理名下学生
            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setAgentId(agentId);
            studentInfo.setDeleteStatus(false);
            List<StudentInfo> list_student = studentService.getList(studentInfo);

            // 获取list的所有json数据
            List<AgentItemReq> listData = agentReq.getList();
            for (AgentItemReq item :listData){
                AgentItemReq dataItem = new AgentItemReq();
                dataItem.setContractId(item.getContractId());

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date startTime = simpleDateFormat.parse(item.getStarttime());
                dataItem.setStarttime(item.getStarttime());
                Date endTime = simpleDateFormat.parse(item.getEndtime());
                dataItem.setEndtime(item.getEndtime());

//                for(StudentInfo studentInfoItem :list_student){
//                    if(studentInfoItem.getSignDate().compareTo(startTime) >=0 && studentInfoItem.getSignDate().compareTo(endTime) <= 0){
//                        VisaRecordInfo visaRecordInfo = new VisaRecordInfo();
//                        visaRecordInfo.setStudentNo(studentInfoItem.getStudentNo());
//                        visaRecordInfo.setDeleteStatus(false);
//                        visaRecordInfo.setAuditStatus(Contants.APPLYSTATUS_ACCEPT);
//                        Integer countTemp = visaRecordService.getList(visaRecordInfo).size();
//                        if(countTemp >=1){
//                            countTemp = 1;
//                        }
//                        count = count + countTemp;
//                    }
//                }
                dataItem.setCount(visaRecordService.getCountByAgentId(agentId,startTime,endTime));
                listResult.add(dataItem);
            }
            agentRes.setList(listResult);
            map.put("code" , 1);
            map.put("data" , agentRes);
            map.put("message", "成功");
        }
        catch (Exception ex){
            map.put("code" , 0);
            map.put("data" , null);
            map.put("message", ex.getMessage());
        }
        return map;
    }

    @RequestMapping(value="/getStudentInfo",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ApiOperation(value = "获取学生信息",notes = "获取学生信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "studentNo", value="学号",required = true),
    })
    @ResponseBody
    public String getStudentInfo(String studentNo){
        JSONObject jsonObject = new JSONObject();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            StudentInfo studentInfo = studentService.getStudentInfoByStudentNo(studentNo);
            studentInfo.setBirthdayString(simpleDateFormat.format(studentInfo.getBirthday()));
            List<StudentInfo> list = new ArrayList<>();
            list.add(studentInfo);
            jsonObject.put("code", 0);
            jsonObject.put("data", list);
            jsonObject.put("message", "成功");
        }
        catch (Exception ex){
            jsonObject.put("code" , 1);
            jsonObject.put("data" , null);
            jsonObject.put("message", ex.getMessage());
        }
        return jsonObject.toString();
    }

    @RequestMapping(value="/getStudentSettleInfo",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ApiOperation(value = "获取学生结案信息",notes = "获取学生结案信息",response = StudentSettleInfo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "studentNo", value="学号",required = true),
    })
    @ResponseBody
    public StudentSettleInfo getStudentSettleInfo(String studentNo){
        StudentSettleInfo studentSettleInfo = new StudentSettleInfo();
        try {
            List<StudentSettleInfo> studentSettleInfos = studentSettleService.getListByStudentNo(studentNo);
            if(studentSettleInfos != null && studentSettleInfos.size()>0){
                studentSettleInfo = studentSettleInfos.get(0);
            }
        }
        catch (Exception ex){
            logger.error("获取学生结案信息失败！"+ ex.getMessage(), ex);
        }
        return studentSettleInfo;
    }

    @RequestMapping(value="/getAuditApplyInfo",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ApiOperation(value = "获取待审批信息",notes = "获取待审批信息",response = AuditApplyInfo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "Integer", name = "businessId", value="业务id",required = true),
            @ApiImplicitParam(paramType = "form", dataType = "Integer", name = "caseId", value="审批场景id",required = true),
    })
    @ResponseBody
    public AuditApplyInfo getAuditApplyInfo(Integer businessId,Integer caseId){
        AuditApplyInfo auditApplyInfo = new AuditApplyInfo();
        try {
            auditApplyInfo = auditApplyService.get(businessId,caseId);

        }
        catch (Exception ex){
            logger.error("获取待审批信息失败！"+ ex.getMessage(), ex);
        }
        return auditApplyInfo;
    }

    @RequestMapping(value="/getAuditResultInfo",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ApiOperation(value = "获取审批结果信息",notes = "获取审批结果信息",response = AuditResultInfo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "Integer", name = "businessId", value="业务id",required = true),
            @ApiImplicitParam(paramType = "form", dataType = "Integer", name = "caseId", value="审批场景id",required = true),
    })
    @ResponseBody
    public AuditResultInfo getAuditResultInfo(Integer businessId,Integer caseId){
        AuditResultInfo auditResultInfo = new AuditResultInfo();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            List<AuditResultInfo> auditResultInfos = auditResultService.getList(caseId,businessId);
            if(auditResultInfos != null && auditResultInfos.size()>0) {
                auditResultInfo = auditResultInfos.get(0);
                auditResultInfo.setCreateTimeString(simpleDateFormat.format(auditResultInfo.getCreateTime()));
            }
        }
        catch (Exception ex){
            logger.error("获取审批结果信息失败！"+ ex.getMessage(), ex);
        }
        return auditResultInfo;
    }

}
