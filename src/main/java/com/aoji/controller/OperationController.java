package com.aoji.controller;

import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.CaseIdEnum;
import com.aoji.contants.Contants;
import com.aoji.contants.StudentStatus;
import com.aoji.mapper.*;
import com.aoji.model.*;
import com.aoji.model.res.Consultor;
import com.aoji.model.res.StudentInfoRes;
import com.aoji.service.*;
import com.aoji.utils.HttpUtils;
import com.aoji.vo.ApplyCollegeVo;
import com.aoji.vo.StudentDetailVO;
import com.aoji.vo.StudentInfoVo;
import com.aoji.vo.StudentProcessStatusVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class OperationController extends BaseController {
    @Autowired
    StudentService studentService;
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";

    @Autowired
    ConfirmRecordService confirmRecordService;
    @Autowired
    UserService userService;
    @RequestMapping("/operation")
    public String list() {
        return "operation/list";
    }

    @RequestMapping(value = "/operation/query", method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel get(StudentInfo studentInfo,PageParam pageParam, BasePageModel basePageModel) {

        String[] str = new String[]{"id","student_no","student_name","pinyin","birthday","status","nation_name","contract_no","sales_consultant","visa_operator","remark"};

        int listCount = studentService.getDisabledListCount(studentInfo.getStudentNo(),studentInfo.getStudentName());
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart()/pageParam.getiDisplayLength()+1, pageParam.getiDisplayLength());

        page.setTotal(listCount);
        studentService.getDisabledList(studentInfo.getStudentNo(),studentInfo.getStudentName());
        basePageModel.setAaData(page);
        basePageModel.setiTotalDisplayRecords((int) page.getTotal());
        basePageModel.setiTotalRecords((int) page.getTotal());
        return basePageModel;
    }

    @RequestMapping(value = "/operation/enableStudent", method = RequestMethod.POST)
    @ResponseBody
    public Boolean enableStudent(String studentNo) {
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(studentNo);
        studentInfo.setDeleteStatus(true);
        studentInfo = studentService.get1(studentInfo);
        studentInfo.setDeleteStatus(false);
        studentInfo.setRemark("运营取消作废学生");
        if(studentService.update(studentInfo) > 0){
            SysUser sysUser = userService.getLoginUser();
            ConfirmRecord confirmRecord = new ConfirmRecord();
            confirmRecord.setCaseId(6);
            confirmRecord.setStudentNo(studentNo);
            confirmRecord.setCreateTime(new Date());
            confirmRecord.setDeleteStatus(false);
            confirmRecord.setOperatorNo(sysUser.getOaid());
            confirmRecord.setOperatorName(sysUser.getUsername());
            confirmRecordService.addOne(confirmRecord);
        }
        return true;
    }
}

