package com.aoji.service;

import com.aoji.model.CommissionSchool;
import com.aoji.model.CommissionStudent;
import com.aoji.model.VisaResultRecordInfo;

import java.util.List;

/**
 * @author wangjunqiang
 * @description
 * @date Created in 2018/6/22 16:05
 */
public interface CommissionSchoolService {

    List<CommissionSchool> getCommissionSchoolList(CommissionSchool commissionSchool);

    /**
     * 编辑佣金院校信息以及同步文签获签表数据
     * @param commissionSchool
     * @return
     */
    Integer editCommissionSchool(CommissionSchool commissionSchool) throws  Exception;

    Boolean removeCommissionSchool(String schoolId);

    Integer editCommissionStudent(CommissionStudent commissionStudent);

    Integer addCommissionSchool(CommissionSchool commissionSchool);
}
