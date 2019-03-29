package com.aoji.controller;

import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.Contants;
import com.aoji.mapper.VisitRemarkInfoMapper;
import com.aoji.model.*;
import com.aoji.model.res.CallCenterRecordRes;
import com.aoji.service.*;
import com.aoji.utils.HttpUtils;
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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author 赵剑飞
 * @description 回访信息控制器
 * @date Created in 下午2:25 2017/12/7
 */
@Controller
public class VisitController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    VisitInfoService visitInfoService;
    @Autowired
    StudentService studentService;
    @Autowired
    UserService userService;
    @Autowired
    ApplyCollegeService applyCollegeService;
    @Autowired
    SupplementInfoService supplementInfoService;
    @Autowired
    VisitRemarkInfoMapper visitRemarkInfoMapper;

    @Value("${upload.ks3.resDomain}")
    private String resDomain;
    @Value("${student.Revisit.url}")
    private String revisitUrl;
    @Value("${sign.callCenter.record.url}")
    private String signCallCenterRecordUrl;
    /**
     * 跳转到列表页
     * @return
     */
    @RequestMapping("/visit")
    public String list(){
        return "visit/list";
    }

    /**
     * 分页查询回访信息列表
     * @param visitInfo
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping(value="/visit/list/query",method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel get(VisitInfo visitInfo, PageParam pageParam, BasePageModel basePageModel){
        visitInfo.setDeleteStatus(false);
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart()/pageParam.getiDisplayLength()+1, pageParam.getiDisplayLength());
        String[] str = new String[]{"id","student_no","visit_date","visit_no","visit_name","content","create_time","operator_no","operator_name","sender_type","visit_way","visit_case","business_id","deadline","visit_type"};
        visitInfoService.getList(visitInfo);
        pageParam.setSortPro(str[pageParam.getiSortCol_0()]);
        return dataTableWapper(page,basePageModel);
    }

    /**
     * 查询咨询顾问回访列表
     * @return
     */
    @RequestMapping(value="/counselorVisit/list/query",method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel getCounselorVisit(String studentNo, BasePageModel basePageModel){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sid", studentNo);
        jsonObject.put("type", "20");
        String json =  HttpUtils.sendPost(revisitUrl,jsonObject.toString());
        RevisitRes revisitRes = JSONObject.parseObject(json,RevisitRes.class);
        List<Revisit> list = new ArrayList<Revisit>();
        if(revisitRes != null) {
            list = revisitRes.getData();
            if(list == null){
                list = new ArrayList<Revisit>();
            }
        }
        String[] str = new String[]{"sid","content","name","visit_no","itime"};
        basePageModel.setAaData(list);
        basePageModel.setiTotalDisplayRecords(list.size());
        basePageModel.setiTotalRecords(list.size());
        return basePageModel;
    }

    /**
     * 查询客服回访列表
     * @return
     */
    @RequestMapping(value="/serviceVisit/list/query",method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel getServiceVisit(String studentNo, BasePageModel basePageModel){
        /* 1秒只让请求一次接口 */
        try {
           Thread.sleep(2000);
        } catch (Exception e) {
             e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sid", studentNo);
        jsonObject.put("type", "10");
        String json =  HttpUtils.sendPost(revisitUrl,jsonObject.toString());
        RevisitRes revisitRes = JSONObject.parseObject(json,RevisitRes.class);
        List<Revisit> list = new ArrayList<Revisit>();
        if(revisitRes != null) {
            list = revisitRes.getData();
            if(list == null){
                list = new ArrayList<Revisit>();
            }
        }
        String[] str = new String[]{"sid","content","name","visit_no","itime"};
        basePageModel.setAaData(list);
        basePageModel.setiTotalDisplayRecords(list.size());
        basePageModel.setiTotalRecords(list.size());
        return basePageModel;
    }

    /**
     * 跳转到添加页
     * @return
     */
    @RequestMapping("/visit/addPage")
    public String add(){
        return "visit/add";
    }

    /**
     * 保存添加的信息
     * @param visitInfo
     * @return
     */
    @RequestMapping(value="/visit/add",method = RequestMethod.POST)
    @ResponseBody
    public Boolean Action_add(VisitInfo visitInfo){
        Date date = new Date();
        SysUser sysUser = userService.getLoginUser();
        visitInfo.setVisitDate(new Date());
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, 1);
        visitInfo.setNextDate(cal.getTime());
        visitInfo.setVisitNo(sysUser.getOaid());
        visitInfo.setVisitName(sysUser.getUsername());
        visitInfo.setCreateTime(date);
        visitInfo.setUpdateTime(date);
        visitInfo.setDeleteStatus(false);
        visitInfo.setOperatorNo(sysUser.getOaid());
        visitInfo.setOperatorName(sysUser.getUsername());
//        changeApplyStatus(visitInfo);
        if(visitInfo.getVisitCase().equals(1)){
            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setStudentNo(visitInfo.getStudentNo());
            studentInfo = studentService.get(studentInfo);
            if(studentInfo != null){
                studentInfo.setLastVisitTime(date);
                studentService.update(studentInfo);
            }
        }
        //如果回访类型是补件，添加一条补件记录
        if(visitInfo.getVisitCase().equals(2) && visitInfo.getVisitType().equals(2)){
            SupplementInfo supplementInfo = new SupplementInfo();
            supplementInfo.setSupplementType(2);
            supplementInfo.setContent("外联添加回访类型为补件的回访时时自动添加");
            supplementInfo.setSupplementAttachment(visitInfo.getAttachment());
            supplementInfo.setOperatorNo(sysUser.getOaid());
            supplementInfo.setOperatorName(sysUser.getUsername());
            supplementInfo.setDeleteStatus(false);
            supplementInfo.setCreateTime(date);
            supplementInfoService.addOne(supplementInfo);
        }
        return visitInfoService.add(visitInfo) > 0;
    }

    /**
     * 跳转到编辑页
     * @return
     */
    @RequestMapping("/visit/editPage")
    public String edit(Integer id,Model model){
        SysUser sysUser = userService.getLoginUser();
        VisitInfo visitInfo= visitInfoService.get(id);
        model.addAttribute("isCurrUser",visitInfo.getOperatorNo().equals(sysUser.getOaid()));
        model.addAttribute("visitInfo",visitInfo);
        model.addAttribute("resDomain",resDomain);
        return "visit/edit";
    }

    /**
     * 保存编辑的信息
     * @param visitInfo
     * @return
     */
    @RequestMapping(value="/visit/edit",method = RequestMethod.POST)
    @ResponseBody
    public Boolean Action_edit(VisitInfo visitInfo){
        SysUser sysUser = userService.getLoginUser();
        visitInfo.setOperatorNo(sysUser.getOaid());
        visitInfo.setOperatorName(sysUser.getUsername());
        visitInfo.setUpdateTime(new Date());

        String evusAttachment = visitInfo.getAttachment();
        if(!com.mysql.jdbc.StringUtils.isNullOrEmpty(evusAttachment)){
            evusAttachment = evusAttachment.replaceAll(resDomain,"");
            visitInfo.setAttachment(evusAttachment);
        }

//        changeApplyStatus(visitInfo);
        return visitInfoService.update(visitInfo) > 0;
    }

    /**
     * 跳转到详情页
     * @return
     */
    @RequestMapping("/visit/detailPage")
    public String detail(Integer id,Model model){
        VisitInfo visitInfo= visitInfoService.get(id);
        model.addAttribute("visitInfo",visitInfo);
        return "visit/detail";
    }
    private VisitInfo changeApplyStatus(VisitInfo visitInfo){
        //外联回访，若回访类型为补件，需更新申请状态为"未补件"
        if(!StringUtils.isEmpty(visitInfo.getVisitType()) && !StringUtils.isEmpty(visitInfo.getBusinessId()) ){
            ApplyInfo applyInfo=applyCollegeService.getById(visitInfo.getBusinessId());
//            applyInfo.setId(visitInfo.getBusinessId());
            //要求补件
            if(visitInfo.getVisitType().equals(Contants.VISITTYPE_NEED_MATERIAL)){
//                applyInfo.setApplyStatusCode(ApplyCollegeStatus.NEED_MATERIAL.getCode());
                applyInfo.setApplyStatusCode(applyInfo.getApplyStatusCode());
                applyCollegeService.update(applyInfo);
                //确认补件
            }else if(visitInfo.getVisitType().equals(Contants.VISITTYPE_CONFIRM_MATERIAL)){
//                applyInfo.setApplyStatusCode(ApplyCollegeStatus.NO_COLLEGE_RESULT.getCode());
                applyInfo.setApplyStatusCode(applyInfo.getApplyStatusCode());
                applyCollegeService.update(applyInfo);
            }

        }
        return visitInfo;
    }

    /**
     * 添加回访备注
     * @param visitRemarkInfo
     * @return
     */
    @RequestMapping(value="/visit/addRemark",method = RequestMethod.POST)
    @ResponseBody
    public Integer add_remark(VisitRemarkInfo visitRemarkInfo){
        SysUser sysUser = userService.getLoginUser();
        visitRemarkInfo.setOperatorNo(sysUser.getOaid());
        visitRemarkInfo.setOperatorName(sysUser.getUsername());
        visitRemarkInfo.setCreateTime(new Date());
        visitRemarkInfo.setUpdateTime(new Date());
        visitRemarkInfo.setDeleteStatus(false);
        visitRemarkInfo.setRemindStatus(0);

        return visitRemarkInfoMapper.insert(visitRemarkInfo);
    }

    /**
     * 查询回访备注列表
     * @param visitRemarkInfo
     * @param pageParam
     * @param basePageModel
     * @return
     */
    @RequestMapping(value="/visitRemark/list/query",method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel getRemarkList(VisitRemarkInfo visitRemarkInfo, PageParam pageParam, BasePageModel basePageModel){
        visitRemarkInfo.setDeleteStatus(false);
        Page<?> page = PageHelper.startPage(pageParam.getiDisplayStart()/pageParam.getiDisplayLength()+1, pageParam.getiDisplayLength());
        String[] str = new String[]{"id","student_no","remark","remind_time","create_time","operator_no","operator_name"};
        List<VisitRemarkInfo> list = visitRemarkInfoMapper.select(visitRemarkInfo);
        pageParam.setSortPro(str[pageParam.getiSortCol_0()]);
        return dataTableWapper(page,basePageModel);
    }

    /**
     * 获取回访备注
     * @param visitRemarkInfo
     * @return
     */
    @RequestMapping(value="/visit/getRemark",method = RequestMethod.POST)
    @ResponseBody
    public VisitRemarkInfo get_remark(VisitRemarkInfo visitRemarkInfo){
        visitRemarkInfo.setDeleteStatus(false);

        return visitRemarkInfoMapper.selectOne(visitRemarkInfo);
    }

    /**
     * 修改回访备注
     * @param visitRemarkInfo
     * @return
     */
    @RequestMapping(value="/visit/editRemark",method = RequestMethod.POST)
    @ResponseBody
    public Integer edit_remark(VisitRemarkInfo visitRemarkInfo){
        SysUser sysUser = userService.getLoginUser();
        visitRemarkInfo.setOperatorNo(sysUser.getOaid());
        visitRemarkInfo.setOperatorName(sysUser.getUsername());
        visitRemarkInfo.setUpdateTime(new Date());
        visitRemarkInfo.setDeleteStatus(false);
        visitRemarkInfo.setRemindStatus(0);

        return visitRemarkInfoMapper.updateByPrimaryKeySelective(visitRemarkInfo);
    }

    /**
     * callcenter回访记录
     * @param studentNo
     * @param basePageModel
     * @return
     */
    @RequestMapping(value = "/visit/callcenter/record")
    @ResponseBody
    public BasePageModel callCenterRecord(String studentNo, BasePageModel basePageModel){
        if(StringUtils.hasText(studentNo)){
            String response = HttpUtils.doGet(MessageFormat.format(signCallCenterRecordUrl, studentNo), String.class);
            JSONObject jsonObject = JSONObject.parseObject(response);
//            int pageStart = (pageParam.getPageNum() - 1) * pageParam.getPageSize();
//            int pageEnd = pageParam.getPageNum() * pageParam.getPageSize();
            if(jsonObject != null && (Integer)jsonObject.get("code") == 1){
                CallCenterRecordRes callCenterRecordRes = JSONObject.parseObject(response, CallCenterRecordRes.class);
                if(callCenterRecordRes.getData() != null){
                    int length = callCenterRecordRes.getData().size();
//                    pageEnd = pageEnd > length ? length : pageEnd;
//                    basePageModel.setAaData(callCenterRecordRes.getData().subList(pageStart, pageEnd));
                    basePageModel.setAaData(callCenterRecordRes.getData());
                    basePageModel.setiTotalRecords(length);
                    basePageModel.setiTotalDisplayRecords(length);
                }
            }
            else{
                basePageModel.setAaData(new ArrayList<>());
            }
        }
        return basePageModel;
    }
}
