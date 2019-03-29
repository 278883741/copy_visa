package com.aoji.controller;

import com.aoji.model.AuditResultInfo;
import com.aoji.service.AuditResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AuditResultController extends BaseController{

    @Autowired
    AuditResultService auditResultService;

    /**
     * 审批结果列表
     * @param caseId
     * @param bussinessId
     * @return
     */
    @RequestMapping(value = "/auditResult/list")
    public String auditResultList(Integer caseId, Integer bussinessId, Model model){
        if(bussinessId == null){
            model.addAttribute("result", false);
        }else{
            List<AuditResultInfo> auditResultInfoList = auditResultService.getList(caseId, bussinessId);
            if(!auditResultInfoList.isEmpty()){
                model.addAttribute("result", true);
                model.addAttribute("auditResultInfoList", auditResultInfoList);
            }else{
                model.addAttribute("result", false);
            }
        }
        return "auditResult/list";
    }
}
