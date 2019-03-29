package com.aoji.service;

import com.aoji.model.PreStudentInfo;
import com.aoji.model.SysUser;
import com.aoji.model.res.TransferRes;

import java.util.List;

/**
 * @author wangjunqiang
 * @description
 * @date Created in 2018/5/17 17:26
 */
public interface PreStudentInfoService {

    List<PreStudentInfo> preStudentInfoList(PreStudentInfo preStudentInfo,String roleName);

    List<SysUser> getAllotTeacher();

    Integer updatePreStudentByStudentNo(String studentNo,String oaid);

    int insert(PreStudentInfo preStudentInfo);

    TransferRes toTranfer(String studentNo, String remark);

    String getRoleName();

    void test();
}
