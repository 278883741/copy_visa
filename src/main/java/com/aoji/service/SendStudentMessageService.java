package com.aoji.service;

import com.aoji.model.SysUser;

/**
 * @author wangjunqiang
 * @description
 * @date Created in 2018/4/4 11:05
 */
public interface SendStudentMessageService {

    /**
     *
     * @param sysUser 登录用户
     * @param serviceCode 发送短信服务进程
     * @param studentNo 学生学号
     * @param studentVisaType 是否是探亲类签证 没有或不是:false/是 :true
     * @param collegeName 院校名称
     */
    void sendStudentMessage(SysUser sysUser,Integer serviceCode,String studentNo,Boolean studentVisaType,String collegeName);
}
