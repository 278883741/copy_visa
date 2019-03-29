package com.aoji.controller;

import com.aoji.model.BasePageModel;
import com.aoji.model.CommissionAusAgent;
import com.aoji.model.PageParam;
import com.aoji.service.CommissionAusAgentService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

/**
 * @author wangjunqiang
 * @description 澳洲代理维护
 * @date Created in 2018/7/5 10:02
 */
@Controller
public class CommissionAusAgentController extends BaseController{


    @Autowired
    private CommissionAusAgentService commissionAusAgentService;

    @RequestMapping("commissionAusAgent/list")
    public String commissionAusAgentList(){

        return "commissionAusAgent/list";
    }

    @RequestMapping(value="commissionAusAgent/query",method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel get(CommissionAusAgent commissionAusAgent,PageParam pageParam, BasePageModel basePageModel){
        Page<?> page = pageWapper(pageParam,propList());
        commissionAusAgentService.getCommissionAusAgentList(commissionAusAgent);
        return dataTableWapper(page,basePageModel);
    }


    @RequestMapping("commissionAusAgent/add")
    public String addAgent(String id,Model model){
        commissionAusAgentService.getCommissionAusAgentById(id,model);
        return "commissionAusAgent/add";
    }

    @RequestMapping("commissionAusAgent/save")
    @ResponseBody
    public boolean saveAgent(CommissionAusAgent commissionAusAgent){
        return commissionAusAgentService.saveCommissionAusAgent(commissionAusAgent);
    }

    @RequestMapping("commissionAusAgent/remove")
    @ResponseBody
    public boolean removeAgent(String id){
        return commissionAusAgentService.removeCommissionAusAgent(id);
    }


    public String[] propList(){
        return new String[]{};
    }

}
