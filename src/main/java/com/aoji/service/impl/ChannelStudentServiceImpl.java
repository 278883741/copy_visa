package com.aoji.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aoji.async.AsyncTask;
import com.aoji.contants.Contants;
import com.aoji.contants.CountryConfeeidEnum;
import com.aoji.contants.RoleContants;
import com.aoji.mapper.*;
import com.aoji.model.*;
import com.aoji.model.req.SendMessageReq;
import com.aoji.model.req.TransferReq;
import com.aoji.model.res.QueryAgentInfoListRes;
import com.aoji.service.*;
import com.aoji.utils.CacheUtils;
import com.aoji.utils.HttpUtils;
import com.aoji.utils.MailUtils;
import com.itextpdf.xmp.impl.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class ChannelStudentServiceImpl implements ChannelStudentService {
    private Logger logger = LoggerFactory.getLogger(ChannelStudentServiceImpl.class);
    @Autowired
    ChannelStudentInfoMapper channelStudentInfoMapper;
    @Autowired
    private ConfirmRecordMapper confirmRecordMapper;
    @Autowired
    private ChannelStudentNoMapper channelStudentNoMapper;
    @Autowired
    private ChannelStudentConsulterMapper channelStudentConsulterMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private TransferSendService transferSendService;
    @Autowired
    private UserTaskRelationService userTaskRelationService;
    @Autowired
    RoleService roleService;
    @Autowired
    SysRoleMapper sysRoleMapper;
    @Autowired
    StudentInfoMapper studentInfoMapper;
    @Autowired
    SendDingDingMessageService sendDingDingMessageService;
    @Autowired
    CountryService countryService;
    @Autowired
    BranchService branchService;
    @Autowired
    AsyncTask asyncTask;
    @Autowired
    StudentService studentService;

    @Value("${channel.visa.responsible}")
    private String channelVisaResponsible;
    @Value("${dingding.message.link}")
    private String dingdingMessageLink;
    @Value("${send.oaid}")
    private String sendOaid;
    @Value("${dingding.message.status}")
    private Boolean dingdingMessageStatus;
    @Value("${dingding.message.api}")
    private String dingdingMessageApi;
    @Value("${queryAgentInfoList.url}")
    private String queryAgentInfoListUrl;
    @Value("${channel.visa.responsibleMail}")
    private String responsibleMail;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean auditChannelStudentById(String channelStudentId, String consulterNo) throws Exception {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SysUser user = userService.getLoginUser();
            ChannelStudentInfo channelStudentInfo = new ChannelStudentInfo();
            channelStudentInfo.setDeleteStatus(false);
            channelStudentInfo.setId(Integer.valueOf(channelStudentId));
            channelStudentInfo.setAuditStatus(3);
            channelStudentInfo.setSignDate(new Date());
            //修改同业学生的审核状态
            Integer updateAuditStatus = channelStudentInfoMapper.updateByPrimaryKeySelective(channelStudentInfo);
            if (updateAuditStatus > 0) {
                //在confirm_record表中插入记录
                ChannelStudentInfo channelStudentInfo1 = channelStudentInfoMapper.selectByPrimaryKey(channelStudentInfo);
                ConfirmRecord confirmRecord = new ConfirmRecord();
                confirmRecord.setCaseId(7);
                confirmRecord.setStudentNo(channelStudentInfo1.getStudentNo());
                confirmRecord.setCreateTime(new Date());
                confirmRecord.setDeleteStatus(false);
                confirmRecord.setOperatorNo(user.getOaid());
                confirmRecord.setOperatorName(user.getUsername());
                confirmRecord.setBusinessId(Integer.valueOf(channelStudentId));
                confirmRecordMapper.insert(confirmRecord);

                TransferReq transferReq = new TransferReq();
                transferReq.setStudentNo(channelStudentInfo1.getStudentNo());
                transferReq.setStudentName(channelStudentInfo1.getStudentName());
                transferReq.setSpelling(channelStudentInfo1.getPinyin());
                if (channelStudentInfo1.getBirthday() != null && !channelStudentInfo1.getBirthday().equals("")) {
                    transferReq.setBirthday(simpleDateFormat.format(channelStudentInfo1.getBirthday()));
                }

                if (channelStudentInfo1.getNationName() != null) {
                    transferReq.setConfeeid(CountryConfeeidEnum.getCodeByName(channelStudentInfo1.getNationName()));
                }
                transferReq.setContractNo("");
                transferReq.setContractType(channelStudentInfo1.getContractType());
                if (channelStudentInfo1.getUsaStatus() != null) {
                    transferReq.setUsaAStatus(Boolean.valueOf(channelStudentInfo1.getUsaStatus().toString()));
                }
                transferReq.setSignDate(simpleDateFormat.format(channelStudentInfo1.getSignDate()));
                CountryInfo countryInfo = new CountryInfo();
                countryInfo.setId(channelStudentInfo1.getNationId());
                countryInfo = countryService.get(countryInfo);
                transferReq.setCountry(countryInfo.getCountryBussid());
                transferReq.setCountryName(channelStudentInfo1.getNationName());
                transferReq.setOperatorNo("");
                transferReq.setOperatorName("");
                BranchInfo branchInfo = new BranchInfo();
                branchInfo.setSeriano(channelStudentInfo1.getBranchId());
                List<BranchInfo> branchInfos = branchService.getList(branchInfo);
                if (!branchInfos.isEmpty()) {
                    transferReq.setBrandId(branchInfos.get(0).getBranchId());
                    transferReq.setBrandName(branchInfos.get(0).getBranchName());
                }
                transferReq.setConsultantNo(consulterNo);
                transferReq.setConsultant(channelStudentConsulterMapper.getConsulterByNo(consulterNo));
                transferReq.setChannelStatus(channelStudentInfo1.getChannelStatus());
                transferReq.setComment(channelStudentInfo1.getTransferRemark());
                transferReq.setAgentId(channelStudentInfo1.getAgentId());
                transferReq.setAgentName(channelStudentInfo1.getAgentName());
                transferReq.setResourceId("");
                //转案(转到学生列表)
                BaseResponse response = transferSendService.doTransfer(transferReq);
                if (!response.isResult()) {
                    throw new Exception(response.getErrorMsg());
                }
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error channelStudentId" + channelStudentId + "审核转案失败!");
        }
        return false;
    }

    /**
     * 同业已审核学生列表查询（此处只查询student_info表里的有代理Id的学生）
     */

    @Override
    public List<StudentInfo> getAuditStudentByAgentId(StudentInfo studentInfo, Boolean channelStatus, String agentId) {
        return studentInfoMapper.getAuditStudentByAgentId(studentInfo, channelStatus, agentId);
    }

    @Override
    public List<ChannelStudentConsulter> getChannelStudentConsulterList() {
        ChannelStudentConsulter channelStudentConsulter = new ChannelStudentConsulter();
        return channelStudentConsulterMapper.select(channelStudentConsulter);
    }

    @Override
    public boolean add(ChannelStudentInfo channelStudentInfo) {
        return channelStudentInfoMapper.insertSelective(channelStudentInfo) > 0;
    }

    @Override
    public boolean update(ChannelStudentInfo channelStudentInfo) {
        return channelStudentInfoMapper.updateByPrimaryKeySelective(channelStudentInfo) > 0;
    }

    @Override
    public ChannelStudentInfo get(Integer id) {
        ChannelStudentInfo channelStudentInfo = new ChannelStudentInfo();
        channelStudentInfo.setId(id);
        channelStudentInfo.setDeleteStatus(false);
        List<ChannelStudentInfo> list = channelStudentInfoMapper.select(channelStudentInfo);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getStudentNo(Integer type) {
        Lock lock = new ReentrantLock();
        try {
            lock.lock();
            String uniqueNo = "";
            ChannelStudentNo channelStudentNo = new ChannelStudentNo();
            channelStudentNo = channelStudentNoMapper.selectOne(channelStudentNo);
            if (type == 2) {
                uniqueNo = String.format(Contants.studentNoPattern, Integer.parseInt(channelStudentNo.getNewOa()) + 1);
                channelStudentNo.setNewOa(uniqueNo);
            } else {
                uniqueNo = String.format(Contants.studentNoPattern, Integer.parseInt(channelStudentNo.getStudentNo()) + 1);
                channelStudentNo.setStudentNo(uniqueNo);
            }
            channelStudentNoMapper.updateByPrimaryKeySelective(channelStudentNo);
            return uniqueNo;
        } catch (Exception ex) {
            throw ex;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public ChannelStudentInfo selectByStudentNo(String studentNo) {
        Example example = new Example(ChannelStudentInfo.class);
        example.createCriteria().andEqualTo("studentNo", studentNo).andEqualTo("deleteStatus", false);
        List<ChannelStudentInfo> channelStudentInfos = channelStudentInfoMapper.selectByExample(example);
        if (!channelStudentInfos.isEmpty()) {
            return channelStudentInfos.get(0);
        }
        return null;
    }

    /**
     * 触发节点：同业负责人“审核”并分配咨询顾问后，提醒同业文签负责人（工号在配置文件中）
     */

    @Override
    public void sendExamineMassage(String channelStudentId, String consulterNo) throws Exception {

        String [] noArry = {consulterNo, channelVisaResponsible};

        //通过Id查询学生信息
        ChannelStudentInfo channelStudentInfo1 = channelStudentInfoMapper.selectOne(new ChannelStudentInfo(false, Integer.valueOf(channelStudentId)));

        for (String no : noArry) {
            sendMessage(no, channelStudentInfo1);
        }
        //根据顾问工号获取顾问邮箱账号
        String consulterEmail = channelStudentConsulterMapper.selectEmailByConsulterNo(consulterNo);
        logger.info("根据顾问工号获取顾问邮箱账号 顾问工号{}"+consulterNo+"顾问邮箱账号{}"+consulterEmail);
        // 异步发邮件,钉钉消息失败了也发邮件
        MailEntity mailEntity = new MailEntity();
        String subject = String.format("学号"+channelStudentInfo1.getStudentNo()+"姓名"+channelStudentInfo1.getStudentName()+"已经分配到您的名下，请您知晓～");
        logger.info("邮件主题{}"+subject);
        String content = String.format("材料清单地址：<a href=http://visa.aojiedu.com/material?studentNo="+channelStudentInfo1.getStudentNo()+">http://visa.aojiedu.com/material?studentNo="+channelStudentInfo1.getStudentNo()+"</a>");
        logger.info("邮件正文"+content);
        //邮件主题
        mailEntity.setSubject(subject);
        //邮件正文
        mailEntity.setContent(content);
        List<String> receives = new ArrayList<>(5);
        //邮件收件人账号
        if(consulterEmail!=null){
            receives.add(consulterEmail);
        }
        logger.info("邮件收件人账号{}"+receives);
        mailEntity.setReceiveAccounts(receives);
        mailEntity.setSendName("澳际文签系统");
        asyncTask.sendEmail(mailEntity);
    }

    private void sendMessage(String consulterNo, ChannelStudentInfo channelStudentInfo1) throws Exception {
        if (channelStudentInfo1 != null) {
            SysUser sysUser = userService.getLoginUser();
            //发送文签工作台消息
            //将数据set到SendMessageReq
            SendMessageReq sendMessageReq = new SendMessageReq();
            sendMessageReq.setOaid(consulterNo);
            logger.info("oaid为:" + consulterNo + "将接收审核消息");
            sendMessageReq.setStudentNo(channelStudentInfo1.getStudentNo());
            sendMessageReq.setTemplateCode("toChannelAudit");
            sendMessageReq.setOperatorNo(sysUser.getOaid());
            sendMessageReq.setTaskType(Contants.WORK_MESSAGE);
            Map<String, String> map = new HashMap<String, String>();
            map.put("studentNo", channelStudentInfo1.getStudentNo());
            map.put("studentName", channelStudentInfo1.getStudentName());
            sendMessageReq.setTemplateParam(map);
            userTaskRelationService.sendMessage(sendMessageReq);

            //发送钉钉消息
            String send_oaid = consulterNo;
            if (StringUtils.hasText(sendOaid)) {
                send_oaid = sendOaid;
            }
            //typeid=10 （小马哥钉钉消息写死的,和文签相关的都是10）
            //javascript:void(0) 发送的消息，不可点击跳转
            String sendUrlEncrypt = Base64.encode("javascript:void(0)");
            String dingdingMessageApiMessage = dingdingMessageApi + "/smspush/DingSing?worknum=" + send_oaid +  "&content=学号" + channelStudentInfo1.getStudentNo() + "姓名" + channelStudentInfo1.getStudentName() + "的同业学生信息，已审批完毕！请您知晓！" + "&url=" + sendUrlEncrypt + "&typeid=10";
            logger.info("编写钉钉消息模板 接收人工号：" + send_oaid + "学号：" + channelStudentInfo1.getStudentNo());
            System.out.println(dingdingMessageApiMessage);
            String  content = "工号："+sysUser.getOaid()+"姓名："+sysUser.getUsername()+"的老师给工号为："+send_oaid+"的老师发送了钉钉消息，消息内容为{学号"+ channelStudentInfo1.getStudentNo() + "姓名" + channelStudentInfo1.getStudentName() + "的同业学生信息，已审批完毕！请您知晓！},发送时间："+new Date();
            SendDingdingMessage sendDingdingMessage = new SendDingdingMessage();
            sendDingdingMessage.setContent(content);
            sendDingdingMessage.setCreateTime(new Date());
            sendDingdingMessage.setStudentNo(channelStudentInfo1.getStudentNo());
            sendDingdingMessage.setOperatorNo(sysUser.getOaid());
            sendDingdingMessage.setOperatorName(sysUser.getUsername());
            sendDingdingMessage.setType(1);
            sendDingdingMessage.setSendTime(new Date());
            sendDingdingMessage.setDingdingMessageApiMessage(dingdingMessageApiMessage);
            sendDingdingMessage.setSendOaid(send_oaid);
            sendDingDingMessageService.pureSendDingDingMessage(sendDingdingMessage);


        }
    }

    /**
     * 触发节点：机构“提交”后，提醒同业负责人(同业经理)
     */
    @Override
    public void sendSubmissionMessage(ChannelStudentInfo channelStudentInfo) throws Exception {

        //通过Id查询学生信息
        ChannelStudentInfo channelStudentInfo1 = channelStudentInfoMapper.selectOne(new ChannelStudentInfo(false, Integer.valueOf(channelStudentInfo.getId())));
        if (channelStudentInfo1 != null) {
            SysUser sysUser = userService.getLoginUser();
            List<SysUserRole> roles = roleService.getOaidByRoleName(RoleContants.CHANNELMANAGER);
            for (SysUserRole roleName : roles) {
                //发送文签工作台消息
                //将数据set到SendMessageReq
                SendMessageReq sendMessageReq = new SendMessageReq();
                sendMessageReq.setOaid(roleName.getOaId());
                logger.info("oaid为:" + roleName.getOaId() + "将接收提交消息");
                sendMessageReq.setStudentNo(channelStudentInfo1.getStudentNo());
                sendMessageReq.setTemplateCode("toChannelSubmission");
                sendMessageReq.setOperatorNo(sysUser.getOaid());
                sendMessageReq.setTaskType(Contants.APPROVAL_MESSAGE);
                Map<String, String> map = new HashMap<String, String>();
                map.put("studentNo", channelStudentInfo1.getStudentNo());
                map.put("studentName", channelStudentInfo1.getStudentName());
                sendMessageReq.setTemplateParam(map);
                userTaskRelationService.sendMessage(sendMessageReq);
                //发送钉钉消息
                String send_oaid = roleName.getOaId();
                if (StringUtils.hasText(sendOaid)) {
                    send_oaid = sendOaid;
                }
                //typeid=10 （小马哥钉钉消息写死的,和文签相关的都是10）
                String sendUrlEncrypt = Base64.encode("javascript:void(0)");
                String dingdingMessageApiMessage = dingdingMessageApi + "/smspush/DingSing?worknum=" + send_oaid + "&content=学号" + channelStudentInfo1.getStudentNo() + "姓名" + channelStudentInfo1.getStudentName() + "的同业学生信息已提交，请及时审批！" + "&url=" + sendUrlEncrypt + "&typeid=10";
                logger.info("编写钉钉消息模板 接收人工号：" + send_oaid + "学号：" + channelStudentInfo1.getStudentNo());
                String  content = "工号："+sysUser.getOaid()+"姓名："+sysUser.getUsername()+"的老师给工号为："+send_oaid+"的老师发送了钉钉消息，消息内容为{学号"+ channelStudentInfo1.getStudentNo() + "姓名" + channelStudentInfo1.getStudentName() + "的同业学生已提交，请及时审批！},发送时间："+new Date();
                SendDingdingMessage sendDingdingMessage = new SendDingdingMessage();
                sendDingdingMessage.setContent(content);
                sendDingdingMessage.setCreateTime(new Date());
                sendDingdingMessage.setStudentNo(channelStudentInfo1.getStudentNo());
                sendDingdingMessage.setOperatorNo(sysUser.getOaid());
                sendDingdingMessage.setOperatorName(sysUser.getUsername());
                sendDingdingMessage.setType(2);
                sendDingdingMessage.setSendTime(new Date());
                sendDingdingMessage.setDingdingMessageApiMessage(dingdingMessageApiMessage);
                sendDingdingMessage.setSendOaid(send_oaid);
                sendDingDingMessageService.pureSendDingDingMessage(sendDingdingMessage);
            }
        }
    }

    @Override
    public Boolean roleName() {
        SysUser sysUser = userService.getLoginUser();
        List<String> roles = sysRoleMapper.getRoleByOaId(sysUser.getOaid());
        if (roles.contains("机构")) {
            return true;
        }
        return false;
    }

    @Override
    public List<QueryAgentInfoListRes> queryAgentInfoList() {
        List<QueryAgentInfoListRes> list = new ArrayList<>();
        CacheItem cacheItem = CacheUtils.getCache("agentInfoList");
        if (cacheItem != null) {
            list = (List<QueryAgentInfoListRes>) cacheItem.getData();
        } else {
            logger.info("调取获取所有代理接口url: " + queryAgentInfoListUrl);
            String result = HttpUtils.doGet(queryAgentInfoListUrl);
            logger.info("调取获取所有代理接口返回数据: " + result);
            if (!StringUtils.isEmpty(result)) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (jsonObject.getInteger("code").equals(200)) {
                    list = JSONObject.parseArray(jsonObject.getString("data"), QueryAgentInfoListRes.class);
                    CacheUtils.setCache("agentInfoList", list, 60);
                    return list;
                }
            }
        }
        return list;
    }

    /**
     * 判断当前学号是否属于同业学生
     *
     * @param studentNo
     * @return
     */
    @Override
    public boolean isChannelStudent(String studentNo) {
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo(studentNo);
        studentInfo.setDeleteStatus(false);
        studentInfo = studentService.get(studentInfo);
        if (studentInfo != null) {
            return studentInfo.getChannelStatus() == 1 ? true : false;
        }
        return false;
    }
}