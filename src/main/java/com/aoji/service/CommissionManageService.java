package com.aoji.service;

import com.aoji.model.CommissionSchool;
import com.aoji.model.CommissionStudent;
import com.aoji.vo.CommissionManageVO;
import com.aoji.vo.CommissionVO;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author wangjunqiang
 * @description
 * @date Created in 2018/6/14 14:06
 */
public interface CommissionManageService {

    List<CommissionManageVO> getCommissionManageList(CommissionManageVO commissionManageVO,Integer roleStatus);

    void addCommissionManage(Integer studentId,Model model);

    void getCommissionList(Model model,Integer studentNo);

    Boolean commissionManageSave(CommissionStudent commissionStudent, CommissionSchool commissionSchool);

    Boolean insertCommissionManage(String studentNo) throws Exception;

    List<String> getOffer(String studentNo);

    List<String> getCoe(String StudentNo);

    void testExcel(HttpServletRequest request, HttpServletResponse response) throws  Exception;

    Integer loginRoleName();

    List<CommissionStudent> getCommissionStudentList(CommissionStudent commissionStudent);


}
