package com.aoji.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aoji.contants.Contants;
import com.aoji.contants.ContentException;
import com.aoji.contants.NoteMessageStatus;
import com.aoji.mapper.StudentNoteRelationMapper;
import com.aoji.mapper.TaskTemplateInfoMapper;
import com.aoji.model.StudentInfo;
import com.aoji.model.StudentNoteRelation;
import com.aoji.model.SysUser;
import com.aoji.model.TaskTemplateInfo;
import com.aoji.service.SendStudentMessageService;
import com.aoji.service.StudentService;
import com.aoji.utils.HttpUtils;
import com.aoji.utils.PlaceholderUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author wangjunqiang
 * @description
 * @date Created in 2018/4/4 11:09
 */

@Service
public class SendStudentMessageServiceImpl implements SendStudentMessageService {

    @Autowired
    TaskTemplateInfoMapper taskTemplateInfoMapper;

    @Autowired
    StudentNoteRelationMapper studentNoteRelationMapper;

    @Autowired
    StudentService studentService;

    @Value("${visa.submit.message}")
    private String visa_submit_message;

    @Value("${visa.submit.message1}")
    private String visa_submit_message1;

    @Value("${visa.sign.message}")
    private String visa_sign_message;

    @Value("${visa.sign.message1}")
    private String visa_sign_message1;

    @Value("${send.note.phoneNumber}")
    private String sendNotePhoneNumber;

    @Value("${student.message.status}")
    private Boolean studentMessageStatus;

    @Value("${student.mobile.number.url}")
    private String studentMessageUrl;

    public static final Logger logger = LoggerFactory.getLogger(SendStudentMessageServiceImpl.class);
    @Override
    public void sendStudentMessage(SysUser sysUser, Integer serviceCode, String studentNo,Boolean studentVisaType,String collegeName) {
        if(studentMessageStatus){
            String phoneNumber = "";
            Map<String, String> phone = studentService.GetPhoneNumByStudentNo(studentNo);
            if (phone != null)
            {
                StudentInfo studentInfo1 = new StudentInfo();
                studentInfo1.setStudentNo(studentNo);
                studentInfo1 = studentService.get(studentInfo1);
                if (StringUtils.hasText(phone.get("phone"))) {
                    if(studentInfo1 != null && !studentInfo1.getBranchId().equals(7)){
                        phoneNumber = phone.get("phone");
                        if(StringUtils.hasText(sendNotePhoneNumber)){
                            phoneNumber = sendNotePhoneNumber;
                            // phoneNumber = "18511832816";
                        }

                        //根据服务进程查询发送消息模板
                        if(StringUtils.hasText(String.valueOf(serviceCode)) && StringUtils.hasText(studentNo) && StringUtils.hasText(phoneNumber)){
                            Map<String ,String> map = new HashMap<>();
                            StudentInfo studentInfo = studentService.getStudentInfoByStudentNo(studentNo);
                            TaskTemplateInfo taskTemplateInfo = new TaskTemplateInfo();
                            taskTemplateInfo.setServiceCode(String.valueOf(serviceCode));
                            taskTemplateInfo.setDeleteStatus(false);
                            //老师电话号码
                            String teacherPhoneNumber = "";
                            String operatorUrl = "http://api.aojiedu.net/Visa/Visa/GetStudentCopyOperator/"+studentInfo.getCopyOperatorNo();
                            String teacherJson = HttpUtils.doGet(operatorUrl);
                            JSONObject operatorObject = JSON.parseObject(teacherJson);
                            if(operatorObject != null){
                                if (operatorObject.getInteger("StatusCode").equals(1)) {
                                    JSONObject jsonObjectDataCopyOperator = JSON.parseObject(operatorObject.getString("Data"));
                                    if (jsonObjectDataCopyOperator != null){
                                        teacherPhoneNumber = jsonObjectDataCopyOperator.getString("mobile");
                                        if (!StringUtils.hasText(teacherPhoneNumber)) {
                                            teacherPhoneNumber = jsonObjectDataCopyOperator.getString("tel");
                                            if (!StringUtils.hasText(teacherPhoneNumber)) {
                                                teacherPhoneNumber = jsonObjectDataCopyOperator.getString("homeTel");
                                            }
                                        }
                                    }
                                }
                            }
                            List<TaskTemplateInfo> taskTemplateInfoList = taskTemplateInfoMapper.select(taskTemplateInfo);
                            if(serviceCode.equals(NoteMessageStatus.NOTE_NO_COPY.getCode()) || serviceCode.equals(NoteMessageStatus.NOTE_NO_COLLEGE_APPLY.getCode()) || serviceCode.equals(NoteMessageStatus.NOTE_NO_COLLEGE_RESULT.getCode()) || serviceCode.equals(NoteMessageStatus.NOTE_NO_COLLEGE_INFO.getCode()) || serviceCode.equals(NoteMessageStatus.NOTE_NO_VISA_RESULT.getCode())){
                                taskTemplateInfo = taskTemplateInfoList.get(0);
                                if(serviceCode.equals(NoteMessageStatus.NOTE_NO_COPY.getCode())){
                                    map.put("copyOperator",studentInfo.getCopyOperator());
                                    map.put("teacherPhoneNumber",teacherPhoneNumber);
                                }else if(serviceCode.equals(NoteMessageStatus.NOTE_NO_COLLEGE_APPLY.getCode())){
                                    map.put("teacherPhoneNumber",teacherPhoneNumber);
                                }else if(serviceCode.equals(NoteMessageStatus.NOTE_NO_COLLEGE_RESULT.getCode())){
                                    if(studentInfo != null && studentInfo.getNationId() == 3){
                                        if (collegeName.contains("-")){
                                            collegeName = collegeName.substring(collegeName.indexOf("-")+1);
                                        }
                                    }
                                    map.put("studentName",studentInfo.getStudentName());
                                    map.put("collegeName",collegeName);
                                }else if(serviceCode.equals(NoteMessageStatus.NOTE_NO_COLLEGE_INFO.getCode())){
                                    if(studentInfo != null && studentInfo.getNationId() == 3){
                                        if (collegeName.contains("-")){
                                            collegeName = collegeName.substring(collegeName.indexOf("-")+1);
                                        }
                                    }
                                    map.put("studentName",studentInfo.getStudentName());
                                    map.put("collegeName",collegeName);
                                }else if(serviceCode.equals(NoteMessageStatus.NOTE_NO_VISA_RESULT.getCode())){
                                    map.put("teacherPhoneNumber",teacherPhoneNumber);
                                }
                            }else if(serviceCode.equals(NoteMessageStatus.NOTE_NO_VISA_COACH.getCode())){
                                for (TaskTemplateInfo taskTemplateInfoNote:taskTemplateInfoList) {
                                    if(StringUtils.hasText(taskTemplateInfoNote.getStudentNation())){
                                        if(getList(taskTemplateInfoNote.getStudentNation()).contains(String.valueOf(studentInfo.getNationId()))){
                                            taskTemplateInfo = taskTemplateInfoNote;
                                            if(getList(visa_submit_message).contains(String.valueOf(studentInfo.getNationId()))){
                                                map.put("teacherPhoneNumber",teacherPhoneNumber);
                                                break;
                                            }else if(getList(visa_submit_message1).contains(String.valueOf(studentInfo.getNationId()))){
                                                break;
                                            }
                                        }
                                    }

                                }
                            }else if(serviceCode.equals(NoteMessageStatus.NOTE_NO_VISA_APPLY.getCode())){
                                for (TaskTemplateInfo taskTemplateInfoNote : taskTemplateInfoList) {
                                    if(studentVisaType) {
                                        if (taskTemplateInfoNote.getStudentVisaType()) {
                                            taskTemplateInfo = taskTemplateInfoNote;
                                            break;
                                        }
                                    }else if(getList(taskTemplateInfoNote.getStudentNation()).contains(String.valueOf(studentInfo.getNationId()))){
                                        taskTemplateInfo = taskTemplateInfoNote;
                                        if(getList(visa_sign_message).contains(String.valueOf(studentInfo.getNationId()))){
                                            map.put("collegeName",collegeName);
                                            break;
                                        }else if(getList(visa_sign_message1).contains(String.valueOf(studentInfo.getNationId()))){
                                            break;
                                        }
                                    }
                                }
                            }


                            if(taskTemplateInfo != null && taskTemplateInfo.getId() != null) {
                                String conent = PlaceholderUtils.resolvePlaceholders(taskTemplateInfo.getMessage(), map);
                                try{
                                    if(conent.contains("&")){
                                        conent = conent.replaceAll("&"," ");
                                    }
                                    //String resConent= (new BASE64Encoder()).encodeBuffer(conent.getBytes("UTF-8"));
                                    String url = "http://api.aojiedu.net/smspush/SMSPush?mobile="+phoneNumber+"&content="+conent+"&typeid=13";
                                    logger.info("发送短信内容:"+conent);
                                    Document document = null;
                                    try{
                                        document = DocumentHelper.parseText(HttpUtils.doGet(url));
                                        String resultCode = document.getStringValue();
                                        if(Contants.STATUSCODE_SUCCESS.equals(resultCode)){
                                            StudentNoteRelation studentNoteRelation = new StudentNoteRelation();
                                            studentNoteRelation.setCreateTime(new Date());
                                            studentNoteRelation.setDeleteStatus(false);
                                            studentNoteRelation.setReadStatus(false);
                                            studentNoteRelation.setReceiveMessage(conent);
                                            studentNoteRelation.setReceiveStudentName(studentInfo.getStudentName());
                                            studentNoteRelation.setSendOaid(sysUser.getOaid());
                                            studentNoteRelation.setSendName(sysUser.getUsername());
                                            studentNoteRelation.setReceiveStudentPhoneNumber(phoneNumber);
                                            studentNoteRelation.setTempleteId(taskTemplateInfo.getId());
                                            studentNoteRelation.setReceiveStudentNo(studentNo);
                                            int saveResult = studentNoteRelationMapper.insert(studentNoteRelation);

                                            logger.info("学生服务进程短信发送成功!");
                                        }else if(Contants.STATUSCODE_ERROR.equals(resultCode)){
                                            new ContentException(1,"学生服务进程短信发送失败!");
                                        }else if(Contants.STATUSCODE_CODERROR.equals(resultCode)){
                                            new ContentException(1,"学生服务进程短信发送失败(程序报错!)");
                                        }else{
                                            logger.info("调用发送短信接口失败:发送手机号码:"+phoneNumber);
                                            new ContentException(1,"调用发送短信接口失败:发送手机号码:"+phoneNumber);
                                        }
                                    }catch(Exception e){
                                        e.printStackTrace();
                                        throw new ContentException(2001,"给学生发送服务进程短信失败");
                                    }
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                            }

                        }
                    }else{
                        logger.info("渠道分支58的学生不发送短信!");
                    }
                }
            }
            else{
                logger.info("发送短信参数缺失,无法发送短信消息");
            }
        }
    }

    public List getList(String str){
        String[] values = str.split(",");
        List<String> list = Arrays.asList(values);
        return list;
    }

//    public static void main(String args[]){
//        String a = "aaa-bbb-ccc-dddd";
//        String b =a.substring(a.indexOf("-")+1);
//        logger.info("b:"+b);
//    }
}
