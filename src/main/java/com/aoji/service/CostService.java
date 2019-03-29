package com.aoji.service;

import com.aoji.model.CostInfo;
import com.aoji.model.StudentCostInfo;

import java.util.List;
import java.util.Map;

public interface CostService {


    List<CostInfo> getCostById(CostInfo costInfo);

    Boolean deleteById(StudentCostInfo costInfo);

    List<StudentCostInfo> getCostById(StudentCostInfo studentCostInfo);
    //pdf查询费用列表的相关信息
    Map<String, Object>  getPdfCostById(StudentCostInfo studentCostInfo);

    List<StudentCostInfo> getCostByIds(StudentCostInfo studentCostInfo);

    Boolean editById(StudentCostInfo studentCostInfo);

    Boolean add(StudentCostInfo studentCostInfo);

    List<StudentCostInfo> insertStuentCostInfo(StudentCostInfo studentCostInfo);


}
