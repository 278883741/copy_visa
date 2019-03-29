package com.aoji.service.impl;

import com.aoji.contants.Contants;
import com.aoji.mapper.ApplyInfoMapper;
import com.aoji.mapper.StudentCopyInfoMapper;
import com.aoji.model.ApplyInfo;
import com.aoji.model.StudentCopyInfo;
import com.aoji.service.CopyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
@Service
public class CopyInfoServiceImpl implements CopyInfoService {

    @Autowired
    StudentCopyInfoMapper studentCopyInfoMapper;

    @Autowired
    ApplyInfoMapper applyInfoMapper;

    @Value("${upload.ks3.resDomain}")
    private String resDomain;

    @Override
    public List<StudentCopyInfo> getListByExample(StudentCopyInfo studentCopyInfo) {
        Example example = new Example(StudentCopyInfo.class);
        example.createCriteria().andEqualTo("deleteStatus", false)
                .andEqualTo("studentNo", studentCopyInfo.getStudentNo());
        example.setOrderByClause(Contants.CREATE_TIME + " desc");
        List<StudentCopyInfo> studentCopyInfos = studentCopyInfoMapper.selectByExample(example);
        for (StudentCopyInfo studentCopyInfo1:studentCopyInfos) {
            if(StringUtils.hasText(studentCopyInfo1.getCopyUrl()) && !studentCopyInfo1.getCopyUrl().contains(resDomain)){
                studentCopyInfo1.setCopyUrl(resDomain +studentCopyInfo1.getCopyUrl());
            }
            if(StringUtils.hasText(studentCopyInfo1.getStudentConfirmUrl()) && !studentCopyInfo1.getStudentConfirmUrl().contains(resDomain)){
                studentCopyInfo1.setStudentConfirmUrl(resDomain +studentCopyInfo1.getStudentConfirmUrl());
            }
            if(StringUtils.hasText(studentCopyInfo1.getApplyId())){
                ApplyInfo applyInfo = new ApplyInfo();
                applyInfo.setId(Integer.valueOf(studentCopyInfo1.getApplyId()));
                applyInfo.setDeleteStatus(false);
                applyInfo = applyInfoMapper.selectOne(applyInfo);
                studentCopyInfo1.setCollegeName(applyInfo.getCollegeName());
                studentCopyInfo1.setCourseName(applyInfo.getCourseName());
                studentCopyInfo1.setMajorName(applyInfo.getMajorName());
            }

        }
        return studentCopyInfos;
    }

    @Override
    public List<StudentCopyInfo> getList(StudentCopyInfo studentCopyInfo) {
        studentCopyInfo.setDeleteStatus(false);
        return studentCopyInfoMapper.select(studentCopyInfo);
    }
}
