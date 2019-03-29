package com.aoji.service.impl;

import com.aoji.contants.Contants;
import com.aoji.contants.TaskTemplateKeyEnum;
import com.aoji.mapper.NewsInfoMapper;
import com.aoji.mapper.SysRoleMapper;
import com.aoji.mapper.TaskTemplateInfoMapper;
import com.aoji.mapper.UserTaskRelationMapper;
import com.aoji.model.*;
import com.aoji.model.req.SendMessageReq;
import com.aoji.service.UserService;
import com.aoji.service.UserTaskRelationService;
import com.aoji.service.VisaApplyService;
import com.aoji.service.VisaRecordService;
import com.aoji.utils.PlaceholderUtils;
import com.aoji.vo.MessageVO;
import com.aoji.vo.UserTaskRelationVO;
import com.aoji.vo.WorkCountVO;
import com.aoji.vo.WorkTableCountVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.*;

@Service
public class UserTaskRelationServiceImpl implements UserTaskRelationService{

    public static final Logger logger = LoggerFactory.getLogger(UserTaskRelationServiceImpl.class);

    @Autowired
    UserTaskRelationMapper userTaskRelationMapper;

    @Autowired
    TaskTemplateInfoMapper taskTemplateInfoMapper;

    @Autowired
    NewsInfoMapper newsInfoMapper;

    @Autowired
    UserService userService;

    @Autowired
    SysRoleMapper sysRoleMapper;
    @Autowired
    VisaApplyService visaApplyService;
    @Autowired
    VisaRecordService visaRecordService;

    /**
     * 发送消息（通知）
     * @param sendMessageReq
     * @return
     */
    @Override
    public boolean  sendMessage(SendMessageReq sendMessageReq) {
        if(sendMessageReq == null){
            logger.error("sendMessageReq is null!!");
            return false;
        }
        //根据code查询模板
        TaskTemplateInfo taskTemplateInfo = new TaskTemplateInfo();
        taskTemplateInfo.setCode(sendMessageReq.getTemplateCode());
        List<TaskTemplateInfo> taskTemplateInfos = taskTemplateInfoMapper.select(taskTemplateInfo);
        if(taskTemplateInfos.size() != 1){
            logger.error("templateCode error!!");
            return false;
        }
        //保存消息
        UserTaskRelation userTaskRelation = new UserTaskRelation();
        userTaskRelation.setCreateTime(new Date());
        userTaskRelation.setReadStatus(false);
        userTaskRelation.setDeleteStatus(false);
        userTaskRelation.setOaid(sendMessageReq.getOaid());
        userTaskRelation.setTemplateId(taskTemplateInfos.get(0).getId());
        userTaskRelation.setOperatorNo(sendMessageReq.getOperatorNo());
        userTaskRelation.setStudentNo(sendMessageReq.getStudentNo());
        userTaskRelation.setTaskType(sendMessageReq.getTaskType());
//        userTaskRelation.setUrl(taskTemplateInfos.get(0).getUrl()+sendMessageReq.getStudentNo());
        userTaskRelation.setContent(PlaceholderUtils
                .resolvePlaceholders(taskTemplateInfos.get(0).getMessage(), sendMessageReq.getTemplateParam()));
        int insertResult = userTaskRelationMapper.insert(userTaskRelation);
        if(insertResult > 0){
            return true;
        }
        return false;
    }

    /**
     * 发送转案拒绝消息
     * @param studentNo
     * @param receiveNo
     * @param tmeplateParam
     * @return
     */
    @Override
    public boolean sendTransferRefuse(String studentNo, String receiveNo, Map<String, String> tmeplateParam) {
        return false;
    }

    /**
     * 消息已读
     * @param id
     * @return
     */
    @Override
    public boolean read(Integer id) {
        UserTaskRelation userTaskRelation = new UserTaskRelation();
        userTaskRelation.setReadStatus(true);
        userTaskRelation.setId(id);
        userTaskRelation.setUpdateTime(new Date());
        int updateResult = userTaskRelationMapper.updateByPrimaryKeySelective(userTaskRelation);
        if(updateResult > 0){
            return true;
        }
        return false;
    }

    /**
     * 查询列表
     * @param userTaskRelation
     * @return
     */
    @Override
    public List<MessageVO> getList(UserTaskRelation userTaskRelation) {
        List<MessageVO> messageVOS = userTaskRelationMapper.queryList(userTaskRelation);
        //url参数替换
        Map<String, String> templateParam = new HashMap<String, String>();
        for (MessageVO message : messageVOS) {
            templateParam.put(TaskTemplateKeyEnum.STUDENT_NO.getCode(), message.getStudentNo());
            message.setUrl(PlaceholderUtils.resolvePlaceholders(message.getUrl(), templateParam));
        }
        return messageVOS;
    }

    @Override
    public Boolean getMessageList(String oaid,Model model,Integer pageIndex) {
        List<UserTaskRelationVO> warningList = new ArrayList<>();
        List<UserTaskRelationVO> approvalList = new ArrayList<>();
        List<UserTaskRelationVO> workList = new ArrayList<>();
        WorkCountVO workCountVO = new WorkCountVO();
        List<NewsInfo> newsInfoList = newsInfoMapper.queryList(pageIndex);
        SysUser sysUser = userService.getLoginUser();
        try{
            WorkTableCountVo workTableCountVo = new WorkTableCountVo();
            workTableCountVo = userTaskRelationMapper.getworkTableCount(oaid);
            warningList = getTypeList(oaid,1+"",pageIndex);
            approvalList = getTypeList(oaid,2+"",pageIndex);
            workList = getTypeList(oaid,3+"",pageIndex);
            workCountVO = getWorkCount(sysUser.getOaid());

            List<String> roles = sysRoleMapper.getRoleByOaId(oaid);
            if (roles != null && roles.size() >= 1) {
                if(roles.contains("文案顾问")){
                    // 文案顾问未回访总数
                    model.addAttribute("copyConsultantVisitCount",getVisitCount(sysUser.getOaid(),"文案顾问"));
                }
                if(roles.contains("文案经理")) {
                    // 文案经理未回访总数
                    model.addAttribute("copyManagerVisitCount",getVisitCount(sysUser.getOaid(),"文案经理"));
                }
                if(roles.contains("文签总监")) {
                    // 文签总监未回访总数
                    model.addAttribute("directorVisitCount",getVisitCount(sysUser.getOaid(),"文签总监"));
                }
                if(roles.contains("总经理")) {
                    // 总经理未回访总数
                    model.addAttribute("managerVisitCount",getVisitCount(sysUser.getOaid(),"总经理"));
                }
            }

            //各未办事项
            model.addAttribute("workCountVO",workCountVO);
            //预警消息
            model.addAttribute("warning_size",warningList.size());
            model.addAttribute("warningList",warningList);
            //审批消息
            model.addAttribute("approval_size",approvalList.size());
            model.addAttribute("approvalList",approvalList);
            //工作消息
            model.addAttribute("work_size",workList.size());
            model.addAttribute("workList",workList);
            model.addAttribute("workTableCountVo",workTableCountVo);
            if(newsInfoList != null && newsInfoList.size() > 0){
                model.addAttribute("newsInfoList",newsInfoList);
            }
            model.addAttribute("unPassVisaApplyCount",this.getUnPassVisaApply(sysUser.getOaid()));
            model.addAttribute("unPassVisaRecordCount",this.getUnPassVisaRecord(sysUser.getOaid()));
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        // }
        return false;
    }

    @Override
    public List<UserTaskRelationVO> getTypeList(String oaid, String taskType,Integer pageIndex) {
        pageIndex = (pageIndex)*10;
        List<UserTaskRelationVO> userTaskRelations = userTaskRelationMapper.getTypeList(oaid,taskType,pageIndex);
        //url参数替换
        Map<String, String> templateParam = new HashMap<String, String>();
        for (UserTaskRelationVO userTaskRelationVO : userTaskRelations) {
            templateParam.put(TaskTemplateKeyEnum.STUDENT_NO.getCode(), userTaskRelationVO.getStudentNo());
            userTaskRelationVO.setUrl(PlaceholderUtils.resolvePlaceholders(userTaskRelationVO.getUrl(), templateParam));
        }
        return userTaskRelations;
    }

    public WorkCountVO getWorkCount(String oaid){
        WorkCountVO workCountVO = userTaskRelationMapper.getWorkCount(oaid);
        return workCountVO;
    }

    public Integer getVisitCount(String oaid,String roleName){
        Integer visitCount = 0;
        visitCount = userTaskRelationMapper.getVisitCount(oaid,roleName);
        return visitCount;
    }

    /**
     * 获取我提交的审批未通过的签证申请个数
     * @param oaid
     * @return
     */
    public Integer getUnPassVisaApply(String oaid){
        VisaApplyInfo visaApplyInfo = new VisaApplyInfo();
        visaApplyInfo.setOperatorNo(oaid);
        visaApplyInfo.setApplyAuditStatus(Contants.APPLYSTATUS_REJECT);
        visaApplyInfo.setDeleteStatus(false);
        List<VisaApplyInfo> list = visaApplyService.getListByOpeator(visaApplyInfo);
        if(list != null && list.size() >0){
            return list.size();
        }
        return 0;
    }

    /**
     * 获取我提交的审批未通过的获签信息个数
     * @param oaid
     * @return
     */
    public Integer getUnPassVisaRecord(String oaid){
        VisaRecordInfo visaRecordInfo = new VisaRecordInfo();
        visaRecordInfo.setOperatorNo(oaid);
        visaRecordInfo.setAuditStatus(Contants.APPLYSTATUS_REJECT);
        visaRecordInfo.setDeleteStatus(false);
        List<VisaRecordInfo> list = visaRecordService.getList(visaRecordInfo);
        if(list != null && list.size() >0){
            return list.size();
        }
        return 0;
    }
}
