package com.aoji.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.ContentException;
import com.aoji.contants.StudentStatus;
import com.aoji.mapper.ApplyInfoMapper;
import com.aoji.mapper.StudentCopyInfoMapper;
import com.aoji.mapper.SupplementInfoMapper;
import com.aoji.model.*;
import com.aoji.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author wangjunqiang
 * @description 文书上传
 * @date Created in 下午2:25 2017/12/7
 */
@Controller
public class StudentCopyInfoController extends BaseController {

    @Autowired
    StudentCopyInfoService studentCopyInfoService;

    @Autowired
    StudentService studentService;

    @Autowired
    UserService userService;

    @Autowired
    ApplyCollegeService applyCollegeService;

    @Autowired
    ApplyInfoMapper applyInfoMapper;

    @Autowired
    SupplementInfoMapper supplementInfoMapper;

    @Autowired
    SendStudentMessageService sendStudentMessageService;

    @Autowired
    StudentCopyInfoMapper studentCopyInfoMapper;

    @Value("${upload.ks3.resDomain}")
    private String resDomain;


    @RequestMapping("/student/search")
    public String toSearchPage(){
        return "studentInfo/search";
    }
    @RequestMapping("/studentCopyInfo")
    public String list(String studentNo,Model model){

        List<ApplyInfo> applyInfos = applyCollegeService.queryApplyInfoByStudentNo(studentNo);
        model.addAttribute("studentNo",studentNo);
        model.addAttribute("applyInfoList",applyInfos);
        return "/studentCopyInfo/message";
    }

    @RequestMapping("/studentCopyInfo/save")
    @ResponseBody
    public BaseResponse save(StudentCopyInfo studentCopyInfo){
        try{
            return studentCopyInfoService.insertStudentCopyInfo(studentCopyInfo);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/studentCopyInfo/edit")
    public String editCopyInfo(HttpServletRequest request, Model model){
        String id = request.getParameter("id");
        String studentNo = request.getParameter("studentNo") == null ? "" :request.getParameter("studentNo");
        List<ApplyInfo> applyInfos = applyCollegeService.queryApplyInfoByStudentNo(studentNo);
        model.addAttribute("applyInfoList",applyInfos);
        StudentCopyInfo studentCopyInfo = new StudentCopyInfo();
        studentCopyInfo.setId(Integer.valueOf(id));
        studentCopyInfo.setDeleteStatus(false);
        studentCopyInfo = studentCopyInfoService.query(studentCopyInfo);
        ApplyInfo applyInfo = new ApplyInfo();
        if(studentCopyInfo != null && StringUtils.hasText(studentCopyInfo.getApplyId())){
            applyInfo = applyCollegeService.getById(Integer.valueOf(studentCopyInfo.getApplyId()));
        }
        if(StringUtils.hasText(studentCopyInfo.getCopyUrl())  && !studentCopyInfo.getCopyUrl().contains(resDomain)){
            studentCopyInfo.setCopyUrl(resDomain +studentCopyInfo.getCopyUrl());
        }
        if(StringUtils.hasText(studentCopyInfo.getStudentConfirmUrl()) && !studentCopyInfo.getStudentConfirmUrl().contains(resDomain)){
            studentCopyInfo.setStudentConfirmUrl(resDomain +studentCopyInfo.getStudentConfirmUrl());
        }
        List<String> serviceStrings = studentService.getServiceByStudentNo(studentNo);
        Boolean studentCopy = false;
        if(serviceStrings != null && serviceStrings.size() == 1){
            if(serviceStrings.get(0).equals("OFFICE_COPY")){
                studentCopy = true;
            }
        }
        model.addAttribute("studentCopy",studentCopy);
        model.addAttribute("apply",applyInfo);
        model.addAttribute("studentCopyInfo",studentCopyInfo);
        model.addAttribute("resDomain",resDomain);
        return "/studentCopyInfo/editMessage";

    }
    @RequestMapping("/studentCopyInfo/delete")
    public String delete(HttpServletRequest request){
        String id = request.getParameter("id");
        String studentNo = request.getParameter("studentNo");
        StudentCopyInfo studentCopyInfo = new StudentCopyInfo();
        studentCopyInfo.setId(Integer.valueOf(id));
        studentCopyInfo.setDeleteStatus(true);
        studentCopyInfoService.delete(studentCopyInfo);
        //若删掉的话同时删掉院校申请中的文书记录
        StudentCopyInfo studentCopyInfoNew = new StudentCopyInfo();
        studentCopyInfoNew.setId(Integer.valueOf(id));
        studentCopyInfoNew = studentCopyInfoService.query(studentCopyInfoNew);
        ApplyInfo applyInfo = new ApplyInfo();
        String applyId = "";
        SupplementInfo supplementInfo = new SupplementInfo();
        if(studentCopyInfoNew != null && studentCopyInfoNew.getApplyId() != null){
            applyInfo.setId(Integer.valueOf(studentCopyInfoNew.getApplyId()));
            applyInfo.setCollegeCopy("");
            int updateResult = applyCollegeService.update(applyInfo);
            supplementInfo.setApplyId(Integer.valueOf(studentCopyInfoNew.getApplyId()));
            supplementInfo.setSupplementAttachment("");
            Example example=new Example(SupplementInfo.class);
            example.createCriteria().andEqualTo("applyId",supplementInfo.getApplyId()).andEqualTo("deleteStatus",false).andEqualTo("supplementType",1);
            int updateSupplement =  supplementInfoMapper.updateByExampleSelective(supplementInfo,example);

        }
        return "redirect:/copyInfo?studentNo="+studentNo;
    }

}
