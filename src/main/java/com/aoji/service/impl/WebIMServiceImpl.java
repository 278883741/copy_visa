package com.aoji.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aoji.mapper.StudentInfoMapper;
import com.aoji.model.StudentInfo;
import com.aoji.model.SysUser;
import com.aoji.model.res.GetUserSigRes;
import com.aoji.model.res.StudentGroupRes;
import com.aoji.service.UserService;
import com.aoji.service.WebIMService;
import com.aoji.utils.HttpUtils;
import com.aoji.vo.IMGroupHistoryVO;
import com.aoji.vo.IMStudentSearchVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

@Service
public class WebIMServiceImpl implements WebIMService {

    private static final Logger logger = LoggerFactory.getLogger(WebIMServiceImpl.class);

    @Autowired
    UserService userService;
    @Autowired
    StudentInfoMapper studentInfoMapper;

    @Value("${im.student.search.data.url}")
    private String studentSearchUrl;
    @Value("${im.user.sign.url}")
    private String getUserSignUrl;
    @Value("${im.group.history.url}")
    private String getGroupHistoryUrl;

    @Override
    public Map<String, String> getLoginInfo(String memberId) {
        SysUser sysUser = userService.getLoginUser();

        try {
            MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
            param.add("name", StringUtils.hasText(memberId) ? memberId : sysUser.getOaid());
            GetUserSigRes getUserSigRes = HttpUtils.doPost(getUserSignUrl, param, GetUserSigRes.class);

            if (getUserSigRes.getCode() == 0) {
                return getUserSigRes.getData();
            } else {
                logger.error("获取IM登录签名失败！Error : " + getUserSigRes.getMessage());
            }
        } catch (Exception e) {
            logger.error("获取IM登录签名失败！Error : " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<IMStudentSearchVO> studentSearch(String memberId) {
        SysUser sysUser = userService.getLoginUser();
        try {
            StudentGroupRes studentGroupRes = HttpUtils.doGet(
                    MessageFormat.format(studentSearchUrl,
                    StringUtils.hasText(memberId) ? memberId : sysUser.getOaid()),
                    StudentGroupRes.class);

            if (studentGroupRes.getCode() == 0) {
                List<IMStudentSearchVO> imStudentSearchVOS = studentGroupRes.getData();
                // 学生姓名处理
                if(imStudentSearchVOS != null && imStudentSearchVOS.size() > 0) {
                    List<StudentInfo> studentInfos = studentInfoMapper.getIMStudentSearchData(imStudentSearchVOS);
                    for (IMStudentSearchVO imStudentSearchVO : imStudentSearchVOS) {
                        i:
                        for (StudentInfo studentInfo : studentInfos) {
                            if (imStudentSearchVO.getStudent_no() != null && imStudentSearchVO.getStudent_no().equals(studentInfo.getStudentNo())) {
                                imStudentSearchVO.setName(studentInfo.getStudentName());
                                break i;
                            }
                        }
                    }
                }
                return imStudentSearchVOS;
            } else {
                logger.error("学生搜索数据获取失败！Error : " + studentGroupRes.getMessage());
            }
        } catch (Exception e) {
            logger.error("学生搜索数据获取失败！Error : " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<IMGroupHistoryVO> getGroupHistory(String groupId, String msgTimeStamp) {
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("groupId", groupId);
        param.add("msgTimeStamp", msgTimeStamp);
        param.add("page", "1");
        param.add("pageSize", "50");
        String str = HttpUtils.doPost(getGroupHistoryUrl, param, String.class);
        List<IMGroupHistoryVO> imGroupHistoryVOS = (List<IMGroupHistoryVO>)JSONObject.parse(str);
        return imGroupHistoryVOS;
    }
}
