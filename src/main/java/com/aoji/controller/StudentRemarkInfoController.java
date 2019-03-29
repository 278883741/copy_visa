package com.aoji.controller;

import com.aoji.model.BasePageModel;
import com.aoji.model.PageParam;
import com.aoji.model.StudentRemarkInfo;
import com.aoji.model.SysUser;
import com.aoji.service.StudentRemarkInfoService;
import com.aoji.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StudentRemarkInfoController extends BaseController{

    @Autowired
    private StudentRemarkInfoService studentRemarkInfoService;
    @Autowired
    private UserService userService;

    @RequestMapping("/studentRemark/list/data")
    @ResponseBody
    public BasePageModel listData(String studentNo, PageParam pageParam, BasePageModel basePageModel){
        StudentRemarkInfo studentRemarkInfo = new StudentRemarkInfo();
        studentRemarkInfo.setStudentNo(studentNo);
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart() / pageParam.getiDisplayLength() + 1, pageParam.getiDisplayLength());
        studentRemarkInfoService.select(studentRemarkInfo);
        return dataTableWapper(page, basePageModel);
    }

    @RequestMapping("/studentRemark/add")
    @ResponseBody
    public Boolean add(String content, String studentNo){
        SysUser sysUser = userService.getLoginUser();
        StudentRemarkInfo studentRemarkInfo = new StudentRemarkInfo();
        studentRemarkInfo.setOperatorNo(sysUser.getOaid());
        studentRemarkInfo.setOperatorName(sysUser.getUsername());
        studentRemarkInfo.setStudentNo(studentNo);
        studentRemarkInfo.setContent(content);
        int result = studentRemarkInfoService.insert(studentRemarkInfo);
        return result > 0;
    }
}
