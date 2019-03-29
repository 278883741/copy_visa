package com.aoji.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import com.aoji.model.*;
import com.aoji.service.CostService;
import com.aoji.service.StudentCostService;
import com.aoji.vo.StudentCostInfoVO;
import com.github.pagehelper.Page;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class CostController extends  BaseController{

    private Logger logger = LoggerFactory.getLogger(CostController.class);
    @Autowired
    private CostService costService;

    @Autowired
    private StudentCostService studentCostService;

    /**
     * 删除不需要的项
     * @param id
     * @param studentCostInfo
     */
    @RequestMapping(value = "/cost/deleteById",method = RequestMethod.POST)
    @ResponseBody
   public  Boolean deleteById(Integer id , StudentCostInfo studentCostInfo){
        studentCostInfo.setId(id);
        return costService.deleteById(studentCostInfo);
   }

    /**
     * 弹出编辑费用金额的窗口
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/cost/editFeePage")
    public String toEditPage(Integer id, String costChiness, String costEnglish, Double money, Model model) {
        model.addAttribute("costId", id);
        model.addAttribute("editCostChiness", costChiness);
        model.addAttribute("editCostEnglish", costEnglish);
        model.addAttribute("editMoney", money);
        return "applyResult/editFee";
    }


    /**
     * 弹出编辑基本费用的窗口
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/cost/basicEditFeePage")
    public String basicEditFeePage(Integer id, String tuition, String registrationFee, String depositAmount, Model model) {
        model.addAttribute("costId", id);
        model.addAttribute("basicTuition", tuition);
        model.addAttribute("basicRegistrationFee", registrationFee);
        model.addAttribute("basicDepositAmount", depositAmount);
        return "applyResult/basicFeePage";
    }

    /**
     * 弹出添加费用金额的窗口
     * @param studentNo
     * @param model
     * @return
     */
    @RequestMapping(value = "/cost/addFeePage")
    public String toAddPage(String studentNo,Model model){
        model.addAttribute("studentNo",studentNo);
        return "applyResult/addFee";
    }


    /**
     * 编辑学生费用
     * @param
     * @param studentCostInfo
     */
    @RequestMapping(value = "/cost/edit",method = RequestMethod.POST)
    @ResponseBody
    public Boolean edit(StudentCostInfo studentCostInfo) {
        return costService.editById(studentCostInfo);
    }

    /**
     * 新增学生费用
     * @param studentCostInfo
     */
    @RequestMapping(value = "/cost/add",method = RequestMethod.POST)
    @ResponseBody
    public  Boolean add(StudentCostInfo studentCostInfo){
        return costService.add(studentCostInfo);


    }

    /**
     * 根据学号查询学生的学费总额和院校
     * @param request
     * @return
     */
    @RequestMapping(value = "/cost/Info")
    @ResponseBody
    public  BaseResponse costInfo(HttpServletRequest request){
        String studentNo = request.getParameter("studentNo");
        String json = null;
        BaseResponse baseResponse = new BaseResponse();
        StudentCostInfoVO studentCostInfoVOs = new StudentCostInfoVO();
        if (!StringUtils.hasText(studentNo)){
            baseResponse.setErrorMsg("请传入学号");
            baseResponse.setResult(false);
            baseResponse.setErrorCode("0");
            return baseResponse;
        }
        try{
             studentCostInfoVOs = studentCostService.getStudentCostInfo(studentNo);
        }catch(Exception e){
            e.printStackTrace();
            baseResponse.setErrorCode("操作失败");
            baseResponse.setResult(false);
            baseResponse.setErrorMsg("2");
            return baseResponse;
        }

        if(studentCostInfoVOs == null){
            baseResponse.setErrorCode("此学号无数据!");
            baseResponse.setResult(false);
            baseResponse.setErrorMsg("1");
            return baseResponse;
        }
        json = JSON.toJSONString(studentCostInfoVOs);
        baseResponse.setErrorCode("操作成功");
        baseResponse.setResult(true);
        baseResponse.setErrorMsg(json);
        return baseResponse;
    }

}
