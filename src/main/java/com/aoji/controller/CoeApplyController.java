package com.aoji.controller;

import com.aoji.contants.ApproveStatusEnum;
import com.aoji.contants.CaseIdEnum;
import com.aoji.mapper.CoeApplyInfoMapper;
import com.aoji.model.*;
import com.aoji.model.req.CoeAuditReq;
import com.aoji.service.*;
import com.aoji.vo.CoeApplyVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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

import java.util.Date;
import java.util.List;

@Controller
public class CoeApplyController {
    @Autowired
    CoeApplyService coeApplyService;
    @Autowired
    UserService userService;
    @Autowired
    AuditApplyService auditApplyService;
    @Autowired
    AuditResultService auditResultService;
    @Autowired
    ApplyCollegeService applyCollegeService;
    @Autowired
    StudentService studentService;
    @Autowired
    CurrencyInfoService currencyInfoService;
    @Autowired
    CoeApplyInfoMapper coeApplyInfoMapper;
    @Autowired
    CoeAttachmentService coeAttachmentService;

    @Value("${upload.ks3.resDomain}")
    private String resDomain;

    public static final Logger logger = LoggerFactory.getLogger(CoeApplyController.class);

    /**
     * 跳转coe申请页
     *
     * @param applyId 申请id
     * @param model
     * @return
     */
    @RequestMapping("/CoeApply/detail")
    public String editPage(Integer applyId, Integer id, String studentNo, Model model) {

        CoeApplyVo coeApplyVo = coeApplyService.getApplyDetail(applyId,id);
        if (!StringUtils.isEmpty(studentNo)) {
            Integer nationId = studentService.getStudentInfoByStudentNo(studentNo).getNationId();
            if(!StringUtils.isEmpty(nationId)) {
                coeApplyVo.setNationId(nationId);
            }
            else{
                coeApplyVo.setNationId(null);
            }
        }
        //获取所有币种
        List<CurrencyInfo> clist = this.currencyInfoService.getList(null);
        //保存和审核按钮状态
        this.getSaveAndApplyStatus(coeApplyVo, model);
        model.addAttribute("cList", clist);
        model.addAttribute("CoeApply", coeApplyVo);
        model.addAttribute("studentNo", studentNo);
        model.addAttribute("resDomain",resDomain);

        //获取多文件列表
        Integer businessId = coeApplyVo.getCoeApplyInfo().getId();
        if(businessId != null) {
            List<CoeAttachmentInfo> coeAttachmentInfos = coeAttachmentService.getList(businessId);
            model.addAttribute("list_files", coeAttachmentInfos);
        }

        return "CoeApply/detail";
    }


    @RequestMapping(value = "/CoeApply/save", method = RequestMethod.POST)
    @ResponseBody
    public Boolean save(CoeApplyVo model, String studentNo) {
        model.getCoeApplyInfo().setStudentNo(studentNo);
        return coeApplyService.saveCoeApplyAndSupplement(model);
    }

    /**
     * 审批
     * @return
     */
    @RequestMapping(value = "/CoeApply/approve")
    @ResponseBody
    public BaseResponse approve(CoeAuditReq coeAuditReq,Integer nationId) {
        logger.info("COE Audit Request : " +coeAuditReq.toString());
        try {
            return coeApplyService.approve(coeAuditReq,nationId);
        } catch (Exception e) {
            logger.info("COE approve failed!! " + e.getMessage());
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setResult(false);
            baseResponse.setErrorMsg("操作失败！");
            return baseResponse;
        }
    }


    /**
     * 控制coe页面 保存和审核按钮
     */
    private void getSaveAndApplyStatus(CoeApplyVo coeApplyVo, Model model) {
        boolean canSave = false; // 能否保存
        boolean canApprove = false; // 能否修改

        // 查询审批信息
        AuditApplyInfo auditApplyInfo = auditApplyService.get(coeApplyVo.getCoeApplyInfo().getId(), CaseIdEnum.CoeApply.getCode());

        ApplyInfo applyInfo = this.applyCollegeService.getById(coeApplyVo.getApplyInfo().getId());

        SysUser sysUser = userService.getLoginUser();

        //判断当前信息是否已经审核 1 审核已提交；2审核已通过；3审核不通过
        Integer applyStatus = coeApplyVo.getCoeApplyInfo().getApplyStatus();

        CoeApplyInfo coeApplyInfo = coeApplyVo.getCoeApplyInfo();

        //判断是否可以保存（1.审核状态不为已审核；2.如果是修改的话，操作人是添加人；3.申请状态为未确认录取院校(2018-4-24由于多条coe已放开此条件限制)； 4.角色是文签顾问）
        if(coeApplyInfo != null && coeApplyInfo.getId() != null) {
            if (ApproveStatusEnum.PASSED.getCode() != applyStatus
                    && sysUser.getOaid().equals(coeApplyInfo.getOperatorNo())) {
                canSave = true;
            }
            //判断是否可以审批（1.审批状态为已申请；2.当前用户是待审批人）
            if(ApproveStatusEnum.APPLIED.getCode() == applyStatus && auditApplyInfo != null){
                canApprove = true;
            }
        }else {
            canSave = true;
        }

        //待审批人
        if(auditApplyInfo != null && auditApplyInfo.getOaid() != null){
            model.addAttribute("waitAuditNo", auditApplyInfo.getOaid());
            model.addAttribute("waitAuditName", auditApplyInfo.getOaName());
            model.addAttribute("hasAuditInfo", true);
        }

        model.addAttribute("canSave", canSave);
        model.addAttribute("canApprove", canApprove);

    }

    @RequestMapping("/coeApply")
    public String list(Integer applyId,String studentNo, Model model){
        ApplyInfo applyInfo = applyCollegeService.getById(applyId);
        model.addAttribute("applyStatus",applyInfo.getApplyStatusCode());
        model.addAttribute("resDomain",resDomain);
        if (!StringUtils.isEmpty(studentNo)) {
            Integer nationId = studentService.getStudentInfoByStudentNo(studentNo).getNationId();
            model.addAttribute("nationId",nationId);
        }
        return "CoeApply/list";
    }

    @RequestMapping(value="coeApply_query",method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel get(CoeApplyInfo coeApplyInfo, PageParam pageParam, BasePageModel basePageModel){
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart()/pageParam.getiDisplayLength()+1, pageParam.getiDisplayLength());
        String[] str = new String[]{"id","studentNo","studentName","attachment","update_time","operator_name"};
        coeApplyInfo.setDeleteStatus(false);
        List<CoeApplyInfo> list = coeApplyService.getListNPA(coeApplyInfo);
        for(CoeApplyInfo item:list){
            if(!StringUtils.isEmpty(item.getStudentNo())){
                item.setStudentName(studentService.getStudentInfoByStudentNo(item.getStudentNo()).getStudentName());
            }
        }
        pageParam.setSortPro(str[pageParam.getiSortCol_0()]);
        basePageModel.setAaData(page);
        basePageModel.setiTotalDisplayRecords((int)page.getTotal());
        basePageModel.setiTotalRecords((int)page.getTotal());
        return basePageModel;
    }

    @RequestMapping(value="/toAuditCOE/todo/query",method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel getToAuditCOE(CoeApplyInfo coeApplyInfo, PageParam pageParam, BasePageModel basePageModel){
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart()/pageParam.getiDisplayLength()+1, pageParam.getiDisplayLength());
        String[] str = new String[]{"id","studentNo","studentName","attachment","update_time","operator_name"};
        SysUser sysUser = userService.getLoginUser();
        String oaid = sysUser.getOaid();
        String studentNo = coeApplyInfo.getStudentNo();
        String oaName = coeApplyInfo.getOaName();
        List<CoeApplyInfo> list = coeApplyInfoMapper.getToAuditCOEList(oaid,studentNo,oaName);
        for(CoeApplyInfo item:list){
            if(!StringUtils.isEmpty(item.getStudentNo())){
                item.setStudentName(studentService.getStudentInfoByStudentNo(item.getStudentNo()).getStudentName());
            }
        }
        pageParam.setSortPro(str[pageParam.getiSortCol_0()]);
        basePageModel.setAaData(page);
        basePageModel.setiTotalDisplayRecords((int)page.getTotal());
        basePageModel.setiTotalRecords((int)page.getTotal());
        return basePageModel;
    }

    @RequestMapping(value = "/coeApplyInfo/delete", method = RequestMethod.POST)
    @ResponseBody
    public String Action_del(Integer id) {
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
        Integer result = coeApplyService.delete(id);
        if(result > 0){//保存成功
            jsonObject.put("code", 0);
            return jsonObject.toString();
        }else{//保存失败
            jsonObject.put("code", 1);
            return jsonObject.toString();
        }
    }

    @RequestMapping(value = "/coeAttachmentInfo/delete", method = RequestMethod.POST)
    @ResponseBody
    public String delete_file(Integer id) {
        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
        SysUser sysUser = userService.getLoginUser();
        CoeAttachmentInfo coeAttachmentInfo = coeAttachmentService.select(id);
        if(!sysUser.getOaid().equals(coeAttachmentInfo.getOperatorNo())){
            jsonObject.put("code", 2);
            return jsonObject.toString();
        }else{
            Integer result = coeAttachmentService.delete(id);
            if(result > 0){//保存成功
                jsonObject.put("code", 0);
                return jsonObject.toString();
            }else{//保存失败
                jsonObject.put("code", 1);
                return jsonObject.toString();
            }
        }
    }

    /**
     * coe confirm
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/coe/confirm", method = RequestMethod.POST)
    @ResponseBody
    public Integer coe_confirm(Integer id) {
        SysUser sysUser = userService.getLoginUser();
        CoeApplyInfo coeApplyInfo = coeApplyService.getById(id);
        coeApplyInfo.setConfirmOperatorNo(sysUser.getOaid());
        coeApplyInfo.setConfirmOperatorName(sysUser.getUsername());
        coeApplyInfo.setConfirmTime(new Date());
        return coeApplyService.update(coeApplyInfo);
    }


}
