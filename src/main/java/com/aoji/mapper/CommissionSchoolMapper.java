package com.aoji.mapper;

import com.aoji.model.CommissionSchool;
import com.aoji.vo.SchoolInvoiceVO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CommissionSchoolMapper extends Mapper<CommissionSchool> {

    List<SchoolInvoiceVO> getSchoolInviceVOList(Integer studentId);
}