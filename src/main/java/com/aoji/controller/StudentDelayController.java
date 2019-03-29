package com.aoji.controller;

import com.aoji.contants.Contants;
import com.aoji.model.*;
import com.aoji.service.*;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class StudentDelayController extends BaseController {
    @Autowired
    StudentDelayService studentDelayService;
    @Autowired
    UserService userService;
    @Autowired
    StudentService studentService;
    @Autowired
    StudentDelayCancelService studentDelayCancelService;

    @Value("${upload.ks3.resDomain}")
    private String resDomain;

    private Logger logger= LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/studentDelay")
    public String list(){
        return "studentDelayInfo/list";
    }

    @RequestMapping(value="/studentDelay/query",method = RequestMethod.GET)
    @ResponseBody
    public BasePageModel get(StudentDelayInfo studentDelayInfo, PageParam pageParam, BasePageModel basePageModel){
        Page<?> page = pageWapper(pageParam,propList());
        List<StudentDelayInfo> studentDelayInfos=studentDelayService.get(studentDelayInfo);
        for(StudentDelayInfo item:studentDelayInfos){
            if(!StringUtils.isEmpty(item.getCreateTime())) {
                item.setCreateTimeString(new SimpleDateFormat(Contants.datePattern).format(item.getCreateTime()));
            }
            List<StudentDelayCancel> studentDelayCancels = studentDelayCancelService.getByDelayId(item.getId());
            if(studentDelayCancels.size() > 0){
                item.setCancelTimeString((new SimpleDateFormat(Contants.datePattern).format(studentDelayCancels.get(studentDelayCancels.size()-1).getCreateTime())));
            }
            if(item != null && StringUtils.hasText(item.getAttachment()) && !item.getAttachment().contains(resDomain)){
                item.setAttachment(resDomain +item.getAttachment());
            }
        }
        return dataTableWapper(page,basePageModel);
    }
    public String[] propList(){
        return new String[]{"id","reason","createTimeString","operatorName","attachment","cancelTimeString"};
    }


    /**
     * 解除缓办
     * @param studentNo
     * @return
     */
    @RequestMapping(value="/student/Action_cancelDelay",method = RequestMethod.POST)
    @ResponseBody
    public Integer Action_cancelDelay(String studentNo){
        SysUser sysUser = userService.getLoginUser();
        List<StudentDelayInfo> studentDelayInfo = studentDelayService.getList(studentNo);
        StudentInfo studentInfo = studentService.getStudentInfoByStudentNo(studentNo);
        if(studentInfo == null || studentDelayInfo == null){
            logger.error(MessageFormat.format("studentNo error! studentNo:{0}", studentNo));
            return 0;
        }
        if(studentInfo.getStudentStatus() == Contants.STUDENT_STATUS_NORMAL){
            logger.error(MessageFormat.format("studentNo error! studentNo:{0}", studentNo));
            return 0;
        }
        //更新学生信息
        studentInfo.setStudentStatus(Contants.STUDENT_STATUS_NORMAL);
        studentService.update(studentInfo);
        //更新解除缓办表信息
        StudentDelayCancel studentDelayCancel = new StudentDelayCancel();
        studentDelayCancel.setDelayId(studentDelayInfo.get(studentDelayInfo.size()-1).getId());
        studentDelayCancel.setCreateTime(new Date());
        studentDelayCancel.setUpdateTime(new Date());
        studentDelayCancel.setDeleteStatus(false);
        studentDelayCancel.setOperatorNo(sysUser.getOaid());
        studentDelayCancel.setStudentNo(studentNo);
        studentDelayCancelService.insert(studentDelayCancel);
        //更新缓办表信息
        studentDelayInfo.get(studentDelayInfo.size()-1).setOperatorNo(sysUser.getOaid());
        studentDelayInfo.get(studentDelayInfo.size()-1).setUpdateTime(new Date());
        studentDelayService.update(studentDelayInfo.get(studentDelayInfo.size()-1));

        return 1;
    }
}
