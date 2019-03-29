package com.aoji.controller;

import com.aoji.model.BasePageModel;
import com.aoji.model.PageParam;
import com.aoji.model.PreStudentInfo;
import com.aoji.model.StudentInfo;
import com.aoji.model.res.TransferRes;
import com.aoji.service.PreStudentInfoService;
import com.aoji.vo.StudentInfoVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wangjunqiang
 * @description
 * @date Created in 2018/5/17 16:53
 */
@Controller
public class PreStudentInfoController extends BaseController{

    @Autowired
    private PreStudentInfoService preStudentInfoService;


    @RequestMapping("preStudentInfo")
    public String preStudentInfo(){
        return "/preStudentInfo/list";
    }

    /**
     * datatables 列表展示页
     *
     * @param preStudentInfo
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping(value = "preStudentInfo/list")
    @ResponseBody
    public BasePageModel getStudentINfoList(PreStudentInfo preStudentInfo, PageParam pageParam, BasePageModel basePageModel) {
        String roleName = preStudentInfoService.getRoleName();
        Page<?> page = pageWapper2(pageParam, propList());
        preStudentInfoService.preStudentInfoList(preStudentInfo,roleName);
        return dataTableWapper(page, basePageModel);
    }

    @RequestMapping("preStudent/allotCourse")
    public String allotCourse(String studentNo,Model model){
        model.addAttribute("allotTeacherList",preStudentInfoService.getAllotTeacher());
        model.addAttribute("studentNo",studentNo);
        return "/preStudentInfo/allotCourse";
    }

    @RequestMapping("preStudent/addCourse")
    @ResponseBody
    public boolean addAllotTeacher(String studentNo,String oaid){
        return preStudentInfoService.updatePreStudentByStudentNo(studentNo,oaid) == 1;
    }

    @RequestMapping("preStudent/toTransferPage")
    public String toTransferPage(String studentNo,Model model){
        model.addAttribute("studentNo",studentNo);
        return "/preStudentInfo/toTransfer";
    }

    @RequestMapping("preStudent/toTransfer")
    @ResponseBody
    public TransferRes toTransfer(String studentNo, String remark){
        return preStudentInfoService.toTranfer(studentNo,remark);
    }

    @RequestMapping("preStudentInfoTest")
    public void insertPreStudentInfo(){
        preStudentInfoService.test();
    }

    private String[] propList() {
        String[] str = new String[]{"sign_date", "last_visit_time"};
        return str;
    }

}
