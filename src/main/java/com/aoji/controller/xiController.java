package com.aoji.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.aoji.contants.CaseIdEnum;
import com.aoji.contants.Contants;
import com.aoji.contants.ContentException;
import com.aoji.contants.StudentStatus;
import com.aoji.mapper.StudentInfoMapper;
import com.aoji.mapper.StudentStatusRecordMapper;
import com.aoji.model.*;
import com.aoji.model.req.SendMessageReq;
import com.aoji.service.*;
import com.aoji.vo.XiStudentInfoVo;
import io.swagger.annotations.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Galleon
 * @description Swagger测试控制器
 * @date Created in 上午11:00 2018/6/14
 */
@Api(value = "Swagger测试控制器")
@Controller
public class xiController {
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
    VisaApplyService visaApplyService;
    @Autowired
    VisaResultService visaResultService;
    @Autowired
    VisitInfoService visitInfoService;
    @Autowired
    StudentInfoMapper studentInfoMapper;
    @Autowired
    StudentStatusRecordService studentStatusRecordService;

    /**
     * 根据顾问oaid查询学生详情
     * @param
     * @return
     */
    @RequestMapping(value="/getStudentInfoByOaid",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ApiOperation(value = "根据顾问oaid查询学生列表",notes = "根据顾问oaid查询学生列表",response = StudentInfo.class)
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "form", dataType = "String", name = "oaid", value = "顾问工号",required = true),
//            @ApiImplicitParam(paramType = "form", dataType = "Integer", name = "type", value = "签约阶段 1-定校方案 2-院校申请 3-签证办理 4-结案",required = true),
//            @ApiImplicitParam(paramType = "form", dataType = "Integer", name = "startIndex", value = "开始索引",required = true),
//            @ApiImplicitParam(paramType = "form", dataType = "Integer", name = "pageSize ", value = "页面容量",required = true)
//
//    })
    @ResponseBody
    public String getStudentInfoByOaid(String oaid,Integer type,Integer startIndex,Integer pageSize){
        JSONObject jsonObject = new JSONObject();
        if(oaid!=null && type != null && startIndex !=null && pageSize !=null) {
            List<StudentInfo> list = studentInfoMapper.getStudentInfoByOaid(oaid,type,startIndex,pageSize);
            if (list != null && list.size() > 0) {
                jsonObject.put("code", 0);
                jsonObject.put("message","成功");
                jsonObject.put("sList", list);
            } else {
                jsonObject.put("code", 1);
                jsonObject.put("message","失败");
                jsonObject.put("sList", "没有找到数据");
            }
        }else{
            jsonObject.put("code", 1);
            jsonObject.put("message","失败");
            jsonObject.put("sList", "传入参数为空");
        }
        return jsonObject.toString();
    }

    /**
     * 根据学号查询学生详情
     * @param studentNo
     * @return
     */
    @RequestMapping(value="/getStudentInfoByStudentNo",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    @ApiOperation(value = "根据学号查询学生详情",notes = "根据学号查询学生详情",response = com.aoji.vo.XiStudentInfoVo.class)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "studentNo", value = "学生学号",required = true)

    })
    @ResponseBody
    public String getStudentInfoByStudentNo(String studentNo){
        JSONObject jsonObject = new JSONObject();
        if(studentNo!=null) {
            StudentInfo studentInfo = studentService.getStudentInfoByStudentNo(studentNo);
            if (studentInfo != null) {
                XiStudentInfoVo xiStudentInfoVo = new XiStudentInfoVo();
                xiStudentInfoVo.setId(studentInfo.getId());
                xiStudentInfoVo.setStudentNo(studentInfo.getStudentNo());
                xiStudentInfoVo.setStudentName(studentInfo.getStudentName());
                xiStudentInfoVo.setNationId(studentInfo.getNationId());
                xiStudentInfoVo.setNationName(studentInfo.getNationName());
                xiStudentInfoVo.setStatus(studentInfo.getStatus());
                if(studentInfo.getUpdateTime() != null){
                    xiStudentInfoVo.setUpdateTime((studentInfo.getUpdateTime()).toString());
                }
                if(studentInfo.getSignDate() != null){
                    xiStudentInfoVo.setSignDate((studentInfo.getSignDate()).toString());
                }
                if(studentInfo.getBranchName() != null){
                    xiStudentInfoVo.setBranchName(studentInfo.getBranchName());
                }
                if(studentInfo.getEmailAccount() != null){
                    xiStudentInfoVo.setEmailAccount(studentInfo.getEmailAccount());
                }
                if(studentInfo.getContractNo() != null){
                    xiStudentInfoVo.setContractNo(studentInfo.getContractNo());
                }
                if(studentStatusRecordService.get(studentNo,10) != null && studentStatusRecordService.get(studentNo,10).getCreateTime() != null){
                    xiStudentInfoVo.setPlanTime((studentStatusRecordService.get(studentNo,10).getCreateTime()).toString());
                }
                if(studentStatusRecordService.get(studentNo,20) != null && studentStatusRecordService.get(studentNo,20).getCreateTime() != null){
                    xiStudentInfoVo.setApplyTime((studentStatusRecordService.get(studentNo,20).getCreateTime()).toString());
                }
                if(studentStatusRecordService.get(studentNo,50) != null && studentStatusRecordService.get(studentNo,50).getCreateTime() != null){
                    xiStudentInfoVo.setVisaTime((studentStatusRecordService.get(studentNo,50).getCreateTime()).toString());
                }
                if(studentStatusRecordService.get(studentNo,90) != null && studentStatusRecordService.get(studentNo,90).getCreateTime() != null){
                    xiStudentInfoVo.setSettleTime((studentStatusRecordService.get(studentNo,90).getCreateTime()).toString());
                }
                jsonObject.put("code", 0);
                jsonObject.put("message","成功");
                jsonObject.put("sList",xiStudentInfoVo);
            } else {
                jsonObject.put("code", 1);
                jsonObject.put("message","失败");
                jsonObject.put("sList", "没有找到数据");
            }
        }else{
            jsonObject.put("code", 1);
            jsonObject.put("message","失败");
            jsonObject.put("sList", "学号为空");
        }
        return jsonObject.toString();
    }
}
