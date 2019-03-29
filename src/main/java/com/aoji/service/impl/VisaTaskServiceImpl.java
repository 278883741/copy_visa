package com.aoji.service.impl;

import com.aoji.contants.Contants;
import com.aoji.contants.MQBusinessTypeEunm;
import com.aoji.contants.MQStatusEnum;
import com.aoji.model.MessageQueueInfo;
import com.aoji.model.StudentInfo;
import com.aoji.service.MessageQueueInfoService;
import com.aoji.service.StudentService;
import com.aoji.service.VisaTaskService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class VisaTaskServiceImpl implements VisaTaskService{

    private static final Logger logger = LoggerFactory.getLogger(VisaTaskServiceImpl.class);

    @Autowired
    MessageQueueInfoService messageQueueInfoService;
    @Autowired
    StudentService studentService;

    /**
     * 同步注册学生状态重试
     */
    @Override
    public void syncStudentRegisterStatusRetry() {
        Example example = new Example(MessageQueueInfo.class);
        Date currentTime = new Date();
        example.createCriteria().andEqualTo("businessTypeCode", MQBusinessTypeEunm.SYNC_REGISTER_STATUS.getCode())
                .andLessThan("nextRetryTime", currentTime)
                .andEqualTo("status", MQStatusEnum.RECEIVE_SUCCESS.getCode());
        // 学号排序
        example.orderBy("businessKey").desc();
        List<MessageQueueInfo> messageQueueInfoList = messageQueueInfoService.selectByExample(example);
        // 临时学号
        String tempStudentNo = null;
        for(MessageQueueInfo messageQueueInfo : messageQueueInfoList) {
            String studentNo = messageQueueInfo.getMessageBody();
            // 跳过重复学号
            if (StringUtils.isNotBlank(studentNo) && !studentNo.equals(tempStudentNo)) {
                // 查询学号是否存在
                StudentInfo studentInfo = studentService.getStudentInfoByStudentNo(studentNo);
                if (studentInfo != null) {
                    // 修改学生信息
                    StudentInfo studentInfo1 = new StudentInfo();
                    studentInfo1.setStudentNo(studentNo);
                    studentInfo1.setRegisterStatus(true);
                    studentService.updateByStudentNo(studentInfo1);

                    // 修改消费状态
                    messageQueueInfo.setStatus(MQStatusEnum.CONSUME_SUCCESS.getCode());
                } else {
                    logger.info("同步学生注册状态，重试失败！ 学号：" + studentNo);
                    // 重试失败， 计算下次重试时间
                    messageQueueInfo.setNextRetryTime(DateUtils.addMinutes(currentTime, Contants.CONSUME_MESSAGE_MAX_TIMEOUT));
                }
                messageQueueInfo.setRetryCount(messageQueueInfo.getRetryCount() + 1);
                messageQueueInfoService.updateByBusinessKeySelective(messageQueueInfo);
            }
            tempStudentNo = studentNo;
        }
    }
}
