package com.aoji.controller;

import com.aoji.contants.Contants;
import com.aoji.model.BaseResponse;
import com.aoji.model.PlanCollegeRecord;
import com.aoji.model.StudentInfo;
import com.aoji.model.req.PlanCollegeCondition;
import com.aoji.model.req.PlanCollegeInfoReq;
import com.aoji.model.req.StudentInfoReq;
import com.aoji.model.req.UpdateStudentInfoReq;
import com.aoji.model.req.*;
import com.aoji.model.res.PlanCollege;
import com.aoji.model.res.PlanCollegeAuditRecordRes;
import com.aoji.model.res.PlanCollegeRes;
import com.aoji.model.res.StudentListRes;
import com.aoji.service.PlanCollegeInfoService;
import com.aoji.service.PlanCollegeRecordService;
import com.aoji.service.StudentService;
import io.swagger.annotations.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * author: chenhaibo
 * description: 签约系统接口
 * date: 2018/5/2
 */
@Api(value = "文签系统")
@Controller
@RequestMapping("/sign")
public class SignController {

    @Autowired
    StudentService studentService;
    @Autowired
    PlanCollegeInfoService planCollegeInfoService;
    @Autowired
    PlanCollegeRecordService planCollegeRecordService;

    public static final Logger logger = LoggerFactory.getLogger(AutoTransferController.class);

    @RequestMapping(value = "/student/list",method = RequestMethod.POST)
    @ApiOperation(value = "查询学生列表",notes = "查询学生列表信息")
    @ResponseBody
    public StudentListRes queryStudentInfo(@ApiParam(name = "studentInfoReq", value = "请求参数") StudentInfoReq studentInfoReq){
        logger.info("queryStudentInfo RQ : " + studentInfoReq.toString());
        Integer page = studentInfoReq.getPage();
        Integer pageSize = studentInfoReq.getPageSize();
        if(page == null || pageSize== null || page < 1 || pageSize < 1){
            logger.error("queryStudentInfo error: 分页参数错误");
            return null;
        }
        try {
            return studentService.getStudentByBranchAndCountry(studentInfoReq);
        } catch (Exception e) {
            logger.error("queryStudentInfo RS : " + e.getMessage());
            return null;
        }
    }

//    @RequestMapping(value = "/update/student",method = RequestMethod.POST)
//    @ApiOperation(value = "修改学生信息",notes = "修改学生基本信息")
//    @ResponseBody
    public BaseResponse updateStudentInfo(@ApiParam(name = "updateStudentInfoReq", value = "请求参数") UpdateStudentInfoReq updateStudentInfoReq){
        logger.info("updateStudentInfo RQ : " + updateStudentInfoReq.toString());
        BaseResponse response = new BaseResponse();
        try {
            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setStudentNo(updateStudentInfoReq.getStudentNo());
            studentInfo.setStudentName(updateStudentInfoReq.getStudentName());
            studentInfo.setPinyin(updateStudentInfoReq.getSpelling());
            if(StringUtils.isNotBlank(updateStudentInfoReq.getBirthday())) {
                studentInfo.setBirthday(DateUtils.parseDate(updateStudentInfoReq.getBirthday(), Contants.datePattern));
            }
            studentService.updateByStudentNo(studentInfo);
            response.setResult(true);
            return response;
        } catch (Exception e){
            logger.error("updateStudentInfo RS:"+e.getMessage());
            response.setResult(false);
            response.setErrorMsg(e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/planCollege/merge", method = RequestMethod.POST)
    @ApiOperation(value = "添加、修改、作废定校方案",notes = "添加、修改、作废定校方案")
    @ResponseBody
    public BaseResponse mergePlanInfo(@ApiParam(name = "planCollegeInfoReq", value = "请求参数") PlanCollegeInfoReq planCollegeInfoReq){
        logger.info("签约系统操作定校方案 请求 : " + planCollegeInfoReq.toString());
        BaseResponse response = new BaseResponse();
        if (planCollegeInfoReq.getCollegeId() != null || StringUtils.isNotBlank(planCollegeInfoReq.getStudentNo())) {
            try {
                response = planCollegeInfoService.mergePlanCollege(planCollegeInfoReq);
            } catch (Exception e) {
                response.setResult(false);
                response.setErrorMsg(e.getMessage());
            }
        } else {
            response.setResult(false);
            response.setErrorMsg("请求参数错误！");
        }
        logger.info("签约系统操作定校方案 返回 : " + response.toString());
        return response;
    }

    @RequestMapping(value = "/planCollege/query", method = RequestMethod.POST)
    @ApiOperation(value = "查询定校方案",notes = "查询定校方案")
    @ResponseBody
    public PlanCollegeRes queryPlanCollege(@ApiParam(name = "planCollegeCondition", value = "请求参数") PlanCollegeCondition planCollegeCondition){
        logger.info("查询定校方案 请求 : " + planCollegeCondition.toString());
        PlanCollegeRes planCollegeRes = new PlanCollegeRes();
        if (StringUtils.isBlank(planCollegeCondition.getStudentNo()) && planCollegeCondition.getPlanId() == null
                && planCollegeCondition.getCollegeId() == null){
            planCollegeRes.setResult(false);
            planCollegeRes.setErrorMsg("请求参数错误！");
        } else {
            try {
                List<PlanCollege> planColleges = planCollegeInfoService.queryPlanCollege(planCollegeCondition);
                planCollegeRes.setResult(true);
                planCollegeRes.setPlanColleges(planColleges);
            } catch (Exception e) {
                planCollegeRes.setResult(false);
                planCollegeRes.setErrorMsg(e.getMessage());
            }
        }
        return planCollegeRes;
    }

    @RequestMapping(value = "/planCollege/queryForManager", method = RequestMethod.POST)
    @ApiOperation(value = "管理层查询定校方案",notes = "管理层查询定校方案")
    @ResponseBody
    public PlanCollegeRes queryPlanCollegeAll(@ApiParam(name = "planCollegeQueryReq", value = "请求参数") PlanCollegeQueryReq planCollegeQueryReq){
        logger.info("管理层查询定校方案 请求 : " + planCollegeQueryReq.toString());

        PlanCollegeRes planCollegeRes = new PlanCollegeRes();
        Integer page = planCollegeQueryReq.getPage();
        Integer pageSize = planCollegeQueryReq.getPageSize();
        if(page == null || pageSize== null || page < 1 || pageSize < 1){
            logger.error("queryStudentInfo error: 分页参数错误");
            planCollegeRes.setResult(false);
            planCollegeRes.setErrorMsg("分页参数错误");
            return planCollegeRes;
        }
        try {
            return planCollegeInfoService.queryPlanCollege(planCollegeQueryReq);
        } catch (Exception e) {
            planCollegeRes.setResult(false);
            planCollegeRes.setErrorMsg(e.getMessage());
        }
        return planCollegeRes;
    }

    @RequestMapping(value = "/refund/endCase", method = RequestMethod.POST)
    @ApiOperation(value = "学生退费结案",notes = "学生退费结案")
    @ResponseBody
    public BaseResponse refundEndCase(@ApiParam(name = "refundEndCaseReq", value = "请求参数") RefundEndCaseReq refundEndCaseReq){
        logger.info("退费结案功能请求 : " + refundEndCaseReq.toString());
        BaseResponse baseResponse = new BaseResponse();
        if(StringUtils.isBlank(refundEndCaseReq.getStudentNo()) || StringUtils.isBlank(refundEndCaseReq.getOperatorNo())
                || StringUtils.isBlank(refundEndCaseReq.getOperatorName())){
            baseResponse.setResult(false);
            baseResponse.setErrorMsg("参数错误");
            return baseResponse;
        }
        try {
            studentService.refundEndCase(refundEndCaseReq);
            baseResponse.setResult(true);
        }catch(Exception e){
            logger.error("退费结案异常！error:"+e.getMessage(), e);
            baseResponse.setResult(false);
            baseResponse.setErrorMsg(e.getMessage());
        }
        return baseResponse;
    }

    @RequestMapping(value = "/planCollege/auditRecord", method = RequestMethod.POST)
    @ApiOperation(value = "定校审核记录",notes = "定校审核记录")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", dataType = "Integer", name = "collegeId", value = "定校单元ID", required = true)})
    @ResponseBody
    public PlanCollegeAuditRecordRes auditRecord(Integer collegeId){
        logger.info("查询审批记录! collegeId="+collegeId);
        PlanCollegeAuditRecordRes response = new PlanCollegeAuditRecordRes();
        if(collegeId == null){
            response.setResult(false);
            response.setErrorMsg("参数错误！");
            return response;
        }
        try {
            PlanCollegeRecord planCollegeRecord = new PlanCollegeRecord();
            planCollegeRecord.setType(Contants.PLAN_COLLEGE_RECORD_TYPE_SIGN);
            planCollegeRecord.setPlanCollegeId(collegeId);
            List<PlanCollegeRecord> planCollegeRecords = planCollegeRecordService.getList(planCollegeRecord);
            response.setData(planCollegeRecords);
            response.setResult(true);
        }catch (Exception e){
            logger.error("查询定校审核记录失败！"+ e.getMessage(), e);
            response.setResult(false);
            response.setErrorMsg(e.getMessage());
        }
        return response;
    }
}
