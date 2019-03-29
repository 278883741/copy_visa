package com.aoji.service.impl;

import com.aoji.contants.StudentStatus;
import com.aoji.mapper.ApplyInfoMapper;
import com.aoji.mapper.StudentCopyInfoMapper;
import com.aoji.mapper.SupplementInfoMapper;
import com.aoji.model.*;
import com.aoji.service.ApplyCollegeService;
import com.aoji.service.StudentCopyInfoService;
import com.aoji.service.StudentService;
import com.aoji.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Service
public class StudentCopyInfoServiceImpl implements StudentCopyInfoService{

    @Autowired
    StudentCopyInfoMapper studentCopyInfoMapper;

    @Autowired
    ApplyInfoMapper applyInfoMapper;

    @Autowired
    SupplementInfoMapper supplementInfoMapper;

    @Autowired
    UserService userService;

    @Autowired
    ApplyCollegeService applyCollegeService;

    @Autowired
    StudentService studentService;

    @Value("${upload.ks3.resDomain}")
    private String resDomain;

    @Override
    public int saveStudentCopyInfo(StudentCopyInfo studentCopyInfo,String id) {
        studentCopyInfo.setDeleteStatus(false);
        if (StringUtils.hasText(id)){
            return studentCopyInfoMapper.updateByPrimaryKeySelective(studentCopyInfo);
        }
        return studentCopyInfoMapper.insert(studentCopyInfo);
    }

    @Override
    public List<StudentCopyInfo> queryByStudentNo(String studentNo) {
        StudentCopyInfo studentCopyInfo=new StudentCopyInfo();
        studentCopyInfo.setStudentNo(studentNo);
        studentCopyInfo.setDeleteStatus(false);
        List<StudentCopyInfo> studentCopyInfos = studentCopyInfoMapper.select(studentCopyInfo);
        if(studentCopyInfos != null){
            for (StudentCopyInfo studentCopyInfo1:studentCopyInfos) {
                if(StringUtils.hasText(studentCopyInfo1.getCopyUrl()) && !studentCopyInfo1.getCopyUrl().contains(resDomain)){
                    studentCopyInfo1.setCopyUrl(resDomain +studentCopyInfo1.getCopyUrl());
                }
                if(StringUtils.hasText(studentCopyInfo1.getStudentConfirmUrl()) && !studentCopyInfo1.getStudentConfirmUrl().contains(resDomain)){
                    studentCopyInfo1.setStudentConfirmUrl(resDomain +studentCopyInfo1.getStudentConfirmUrl());
                }
            }
        }
        return studentCopyInfos;
    }

    @Override
    public StudentCopyInfo query(StudentCopyInfo studentCopyInfo) {
        return studentCopyInfoMapper.selectByPrimaryKey(studentCopyInfo);
    }

    @Override
    public int delete(StudentCopyInfo studentCopyInfo) {
        return studentCopyInfoMapper.updateByPrimaryKeySelective(studentCopyInfo);
    }

    @Override
    public Integer updateApplyInfo(String copyUrl,String studentNo,String applyId) {
        return applyInfoMapper.updateCopyUrl(copyUrl,studentNo,applyId);
    }

    @Override
    public Integer updateSupplementInfo(String copyUrl, String applyId) {
        return supplementInfoMapper.updateAttachment(copyUrl,applyId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse insertStudentCopyInfo(StudentCopyInfo studentCopyInfo) throws Exception {
        BaseResponse baseResponse = new BaseResponse();
        try{
            SysUser sysUser = userService.getLoginUser();
            studentCopyInfo.setOperatorNo(sysUser.getOaid());
            studentCopyInfo.setOperatorName(sysUser.getUsername());

//            //获取院校对应的文书,如果有就让其进行修改,允许添加;
//            if(StringUtils.hasText(studentCopyInfo.getApplyId()) && studentCopyInfo.getId() ==null){
//                StudentCopyInfo studentCopyInfo1 = studentCopyInfoMapper.getStudentCopyInfoByApplyId(studentCopyInfo.getStudentNo(),Integer.valueOf(studentCopyInfo.getApplyId()));
//                if(studentCopyInfo1 != null){
//                    baseResponse.setErrorMsg("您在该院校已添加过文书,若有补充,请进行修改!");
//                    baseResponse.setResult(false);
//                    baseResponse.setErrorCode("2");
//                    return baseResponse;
//                }
//            }

            StudentCopyInfo studentCopyInfoOld = new StudentCopyInfo();


            //区分是新增还是修改
            if(studentCopyInfo.getId() !=null){
                studentCopyInfoOld.setId(studentCopyInfo.getId());
                studentCopyInfoOld.setDeleteStatus(false);
                studentCopyInfoOld = this.query(studentCopyInfoOld);
                if(studentCopyInfoOld != null && StringUtils.hasText(studentCopyInfoOld.getApplyId())){
                    this.updateApplyInfo("",studentCopyInfo.getStudentNo(),studentCopyInfoOld.getApplyId());
                    this.updateSupplementInfo("",studentCopyInfoOld.getApplyId());
                }
                studentCopyInfo.setUpdateTime(new Date());
            }else{
                studentCopyInfo.setCreateTime(new Date());
            }

    //            if(studentCopyInfoOld != null && studentCopyInfoOld.getCopyUrl() != null){
    //                studentCopyInfo.setCopyUrl(studentCopyInfoOld.getCopyUrl());
    //            }else{
    //                studentCopyInfo.setCopyUrl("");
    //            }


    //            if(studentCopyInfoOld != null && studentCopyInfoOld.getStudentConfirmUrl() != null){
    //                studentCopyInfo.setStudentConfirmUrl(studentCopyInfoOld.getStudentConfirmUrl());
    //            }else{
    //                studentCopyInfo.setStudentConfirmUrl("");
    //            }

            String id = "";
            if(studentCopyInfo.getId() !=null){
                id = String.valueOf(studentCopyInfo.getId());
            }
            int saveResult = this.saveStudentCopyInfo(studentCopyInfo,id);
            if(StringUtils.hasText(studentCopyInfo.getApplyId())){}
            this.updateApplyInfo(studentCopyInfo.getCopyUrl(),studentCopyInfo.getStudentNo(),studentCopyInfo.getApplyId());
            //根据申请id查询时候有首次寄出补件,若没有就添加
            if(StringUtils.hasText(studentCopyInfo.getApplyId())){
                ApplyInfo applyInfo = applyCollegeService.getById(Integer.valueOf(studentCopyInfo.getApplyId()));
                if(applyInfo != null && applyInfo.getApplyWay() != null && applyInfo.getApplyWay() == 1){
                    SupplementInfo supplementInfo = supplementInfoMapper.getSupplementInfoByApplyId(String.valueOf(studentCopyInfo.getApplyId()));
                    SupplementInfo supplementInfoInsert = new SupplementInfo();
                    if(supplementInfo == null){
                        supplementInfoInsert.setApplyId(Integer.valueOf(studentCopyInfo.getApplyId()));
                        supplementInfoInsert.setSupplementAttachment(studentCopyInfo.getCopyUrl());
                        supplementInfoInsert.setSupplementType(1);
                        supplementInfoInsert.setCreateTime(new Date());
                        supplementInfoInsert.setDeleteStatus(false);
                        supplementInfoInsert.setOperatorNo(sysUser.getOaid());
                        supplementInfoInsert.setOperatorName(sysUser.getUsername());
                        supplementInfoMapper.insert(supplementInfoInsert);

                    }else{
                        this.updateSupplementInfo(studentCopyInfo.getCopyUrl(),studentCopyInfo.getApplyId());
                    }
                }
            }

            if(saveResult >= 1){
                //根据学号查询学生的服务进程状态
                StudentInfo studentInfo = studentService.getStudentInfoByStudentNo(studentCopyInfo.getStudentNo());
                if(studentInfo != null){
                    if(studentInfo.getStatus().equals(StudentStatus.NO_COPY.getCode())){
                        //修改学生进程状态
                        studentService.updateProcessStatus(studentCopyInfo.getStudentNo(), StudentStatus.NO_COPY.getCode(), sysUser.getOaid());
                    }
                }
                baseResponse.setResult(true);
                return baseResponse;
            }
            baseResponse.setResult(false);
            baseResponse.setErrorMsg("操作失败");
            return baseResponse;
        }catch(Exception e){
            e.printStackTrace();
            throw new Exception("上传文书失败");
        }
    }
}
