package com.aoji.service.impl;

import com.aoji.mapper.*;
import com.aoji.model.*;
import com.aoji.mapper.CostInfoMapper;
import com.aoji.mapper.StudentCostInfoMapper;
import com.aoji.model.CostInfo;
import com.aoji.model.StudentCostInfo;
import com.aoji.service.ApplyCollegeService;
import com.aoji.service.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CostServiceImpl  implements CostService{
    @Autowired
    private CostInfoMapper costInfoMapper;

    @Autowired
    private StudentCostInfoMapper studentCostInfoMapper;

    @Autowired
    private FollowServiceInfoMapper followServiceInfoMapper;

    @Autowired
    private ApplyInfoMapper applyInfoMapper;

    @Autowired
    private ApplyCollegeService applyCollegeService;

    @Override
    public List<CostInfo> getCostById(CostInfo costInfo) {
        return costInfoMapper.select(costInfo);
    }

    @Override
    public Boolean deleteById(StudentCostInfo studentCostInfo) {
        studentCostInfo.setDeleteStatus(true);
        Example example=new Example(StudentCostInfo.class);
        studentCostInfo.setUpdateTime(new Date());
        example.createCriteria().andEqualTo("id",studentCostInfo.getId()).andEqualTo("deleteStatus",false);
        return studentCostInfoMapper.updateByExampleSelective(studentCostInfo,example)==1;
    }


    @Override
    public List<StudentCostInfo> getCostById(StudentCostInfo studentCostInfo) {
        return studentCostInfoMapper.select(studentCostInfo);

    }

    @Override
    public List<StudentCostInfo> getCostByIds(StudentCostInfo studentCostInfo) {
        this.insertStuentCostInfo(studentCostInfo);
        return this.getCostById(studentCostInfo);

    }
    //pdf查询费用列表的相关信息
    @Override
    public Map<String, Object> getPdfCostById(StudentCostInfo studentCostInfo) {
        Map<String, Object> data3 = new HashMap<String, Object>();
        this.insertStuentCostInfo(studentCostInfo);
        List<StudentCostInfo> costById = this.getCostById(studentCostInfo);
        Double finallyCost1=0.00;
        Double finallyCost=0.00;
            if(costById!=null&&costById.size()>0) {
                if(costById.get(0)!=null){
                    for (int i = 0; i < costById.size(); i++) {
                        if (!costById.get(i).getCostchinese().equals("") || !costById.get(i).getCostEnglish().equals("") || costById.get(i).getMoney() != null) {
                            data3.put("cost" + (i + 101), costById.get(i).getCostchinese() + "(" + costById.get(i).getCostEnglish() + "):" + costById.get(i).getMoney());

                            if (costById.get(i).getMoney() == null){
                                finallyCost1 = 0.00;
                            }
                            finallyCost1 = costById.get(i).getMoney();
                        } else {
                            data3.put("cost" + (i + 101), "");
                            finallyCost1 = 0.00;
                        }

                        finallyCost += finallyCost1;
                    }
                    data3.put("finallyCost", finallyCost);//总费用
                    data3.put("cost42","其他(other Fees):");//其他费用
                    return data3;
                }

            }

        return null;
    }


    @Override
    public Boolean add(StudentCostInfo studentCostInfo) {

        StudentCostInfo studentCostInfos = new StudentCostInfo();
        studentCostInfos.setDeleteStatus(false);
        studentCostInfos.setStudentNo(studentCostInfo.getStudentNo());
        List<StudentCostInfo> selectCount =  studentCostInfoMapper.select(studentCostInfos);
        if(selectCount.size()<25){
            if(studentCostInfo!=null&&!studentCostInfo.equals("")){
                if(studentCostInfo.getCostchinese()!=null&&!studentCostInfo.getCostchinese().equals("")){
                    if(studentCostInfo.getCostEnglish()!=null&&!studentCostInfo.getCostEnglish().equals("")){
                        if(studentCostInfo.getMoney()!=null&&!studentCostInfo.getMoney().equals("")){
                            studentCostInfo.setDeleteStatus(false);
                            studentCostInfo.setCreateTime(new Date());
                            boolean b = studentCostInfoMapper.insert(studentCostInfo) == 1;
                            return  true;
                        }
                    }
                }
            }

        }
        return  false;
    }

    @Override
    public List<StudentCostInfo> insertStuentCostInfo(StudentCostInfo studentCostInfo) {
        List<StudentCostInfo> studentCostInfoList = studentCostInfoMapper.select(studentCostInfo);
        if (studentCostInfoList == null || studentCostInfoList.size() < 1) {
            CostInfo costInfo = new CostInfo();
            costInfo.setDeleteStatus(false);
            List<CostInfo> costInfoList = costInfoMapper.select(costInfo);
            if (costInfoList != null && costInfoList.size() > 0) {
                for (CostInfo costInfo1 : costInfoList) {
                    StudentCostInfo studentCostInfo1 = new StudentCostInfo();
                    studentCostInfo1.setStudentNo(studentCostInfo.getStudentNo());
                    studentCostInfo1.setCostchinese(costInfo1.getCostchinese());
                    studentCostInfo1.setCostEnglish(costInfo1.getCostenglish());
                    /*studentCostInfo1.setMoney(costInfo1.getMoney());*/
                    studentCostInfo1.setMoney(Double.valueOf(costInfo1.getMoney()));
                    studentCostInfo1.setDeleteStatus(false);
                    /*ApplyInfo applyInfo = new ApplyInfo();
                    applyInfo.setPlanCourseStatus(1);
                    applyInfo.setDeleteStatus(false);
                    applyInfo.setStudentNo(studentCostInfo.getStudentNo());*/
                    if (costInfo1.getId() == 6) {
                        FollowServiceInfo followServiceInfo = new FollowServiceInfo();
                        followServiceInfo.setDeleteStatus(false);
                        followServiceInfo.setStudentNo(studentCostInfo.getStudentNo());
                        List<FollowServiceInfo> followServiceInfoList = followServiceInfoMapper.select(followServiceInfo);
                            if (followServiceInfoList != null && followServiceInfoList.size() > 0) {
                                if(followServiceInfoList.get(0).getFee()!=null){
                                    studentCostInfo1.setMoney(followServiceInfoList.get(0).getFee().doubleValue());
                                }
                            }
                    }
                    /*if (costInfo1.getId() == 4 || costInfo1.getId() == 5) {
                        if (applyInfoMapper.getSumTuition(costInfo1, applyInfo) != null) {
                            studentCostInfo1.setMoney(Double.valueOf(applyInfoMapper.getSumTuition(costInfo1, applyInfo)));
                        }
                    }*/
                    studentCostInfoMapper.insertStudentCost(studentCostInfo1);
                }
            }
        }
        return null;
    }

    @Override
    public Boolean editById(StudentCostInfo studentCostInfo) {
        Example example=new Example(StudentCostInfo.class);
        studentCostInfo.setUpdateTime(new Date());
        if(studentCostInfo!=null&&!studentCostInfo.equals("")){
            if(studentCostInfo.getCostchinese()!=null&&!studentCostInfo.getCostchinese().equals("")){
                if(studentCostInfo.getCostEnglish()!=null&&!studentCostInfo.getCostEnglish().equals("")){
                    if(studentCostInfo.getMoney()!=null&&!studentCostInfo.getMoney().equals("")){
                        example.createCriteria().andEqualTo("id",studentCostInfo.getId()).andEqualTo("deleteStatus",false);
                        return studentCostInfoMapper.updateByExampleSelective(studentCostInfo,example)==1;
                    }
                }
            }
        }

        return false;

    }
}
