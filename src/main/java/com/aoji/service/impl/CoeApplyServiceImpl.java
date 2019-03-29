package com.aoji.service.impl;

import com.aoji.contants.*;
import com.aoji.mapper.ApplyInfoMapper;
import com.aoji.mapper.ApplyResultInfoMapper;
import com.aoji.mapper.CoeApplyInfoMapper;
import com.aoji.mapper.SupplementInfoMapper;
import com.aoji.model.*;
import com.aoji.model.req.CoeAuditReq;
import com.aoji.service.*;
import com.aoji.vo.CoeApplyVo;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CoeApplyServiceImpl implements CoeApplyService {
    @Autowired
    ApplyInfoMapper applyInfoMapper;

    @Autowired
    ApplyResultInfoMapper applyResultInfoMapper;

    @Autowired
    CoeApplyInfoMapper coeApplyInfoMapper;

    @Autowired
    CoeApplyService coeApplyService;

    @Autowired
    AuditResultService auditResultService;

    @Autowired
    SupplementInfoMapper supplementInfoMapper;

    @Autowired
    ApplyCollegeService applyCollegeService;

    @Autowired
    ApplyResultService applyResultService;

    @Autowired
    AuditApplyService auditApplyService;

    @Autowired
    SupplementInfoService supplementInfoService;

    @Autowired
    UserService userService;

    @Autowired
    CoeAttachmentService coeAttachmentService;

    @Autowired
    FileService fileService;

    @Value("${upload.ks3.resDomain}")
    private String resDomain;

    private Logger logger = LoggerFactory.getLogger(ApplyCollegeServiceImpl.class);

    @Override
    public CoeApplyInfo getById(Integer id) {
        return coeApplyInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CoeApplyInfo> getList(CoeApplyInfo coeApplyInfo) {

        List<CoeApplyInfo> list = coeApplyInfoMapper.select(coeApplyInfo);

        if (list != null && list.size() > 0) {

            for (CoeApplyInfo coe : list) {
                //  文件路径
                if (coe.getAttachment() != null) {

                    if (!coe.getAttachment().contains(resDomain)) {

//                        coe.setAttachment(StringUtils.hasText(coe.getAttachment()) ? resDomain + coe.getAttachment() : null);

                        coe.setAttachment(fileService.getPrivateUrl(coe.getAttachment()));
                    }
                }

                if (coe.getApplyAttachment() != null) {

                    if (!coe.getApplyAttachment().contains(resDomain)) {

//                        coe.setApplyAttachment(StringUtils.hasText(coe.getApplyAttachment()) ? resDomain + coe.getApplyAttachment() : null);

                        coe.setApplyAttachment(fileService.getPrivateUrl(coe.getApplyAttachment()));
                    }
                }
            }
        }
        return list;
    }

    //NPA非私密附件，私密附件地址前台获取，不绑定
    @Override
    public List<CoeApplyInfo> getListNPA(CoeApplyInfo coeApplyInfo) {

        List<CoeApplyInfo> list = coeApplyInfoMapper.select(coeApplyInfo);

        if (list != null && list.size() > 0) {

            for (CoeApplyInfo coe : list) {
                //  文件路径
                if (coe.getAttachment() != null) {

                    if (!coe.getAttachment().contains(resDomain)) {

                        coe.setAttachment(StringUtils.hasText(coe.getAttachment()) ? resDomain + coe.getAttachment() : null);

//                        coe.setAttachment(fileService.getPrivateUrl(coe.getAttachment()));
                    }
                }

                if (coe.getApplyAttachment() != null) {

                    if (!coe.getApplyAttachment().contains(resDomain)) {

                        coe.setApplyAttachment(StringUtils.hasText(coe.getApplyAttachment()) ? resDomain + coe.getApplyAttachment() : null);

//                        coe.setApplyAttachment(fileService.getPrivateUrl(coe.getApplyAttachment()));
                    }
                }
            }
        }
        return list;
    }

    @Override
    public CoeApplyVo getApplyDetail(Integer applyId, Integer id) {
        CoeApplyVo coeApplyVo = new CoeApplyVo();
        //设置院校申请信息
        ApplyInfo applyInfo = applyCollegeService.getById(applyId);
        coeApplyVo.setApplyInfo(applyInfo);
        //设置coe申请信息
        if (!StringUtils.isEmpty(id)) {
            Example example2 = new Example(CoeApplyInfo.class);
            example2.createCriteria().andEqualTo("id", id).andEqualTo("deleteStatus", "false");
            List<CoeApplyInfo> CoeApply = coeApplyInfoMapper.selectByExample(example2);
            if (CoeApply.size() > 0) {
                for (CoeApplyInfo coeApplyInfo : CoeApply) {
                    if (coeApplyInfo != null && StringUtils.hasText(coeApplyInfo.getApplyAttachment()) && !coeApplyInfo.getApplyAttachment().contains(resDomain)) {
                        coeApplyInfo.setApplyAttachment(resDomain + coeApplyInfo.getApplyAttachment());
                    }
                    if (coeApplyInfo != null && StringUtils.hasText(coeApplyInfo.getAttachment()) && !coeApplyInfo.getAttachment().contains(resDomain)) {
                        coeApplyInfo.setAttachment(resDomain + coeApplyInfo.getAttachment());
                    }
                    if (coeApplyInfo != null && StringUtils.hasText(coeApplyInfo.getPreAttachment()) && !coeApplyInfo.getPreAttachment().contains(resDomain)) {
                        coeApplyInfo.setPreAttachment(resDomain + coeApplyInfo.getPreAttachment());
                    }
                    if (coeApplyInfo != null && StringUtils.hasText(coeApplyInfo.getLanguageAttachment()) && !coeApplyInfo.getLanguageAttachment().contains(resDomain)) {
                        coeApplyInfo.setLanguageAttachment(resDomain + coeApplyInfo.getLanguageAttachment());
                    }
                }
                coeApplyVo.setCoeApplyInfo(CoeApply.get(0));
            }
        } else {
            CoeApplyInfo coeApplyInfo = new CoeApplyInfo();
            coeApplyVo.setCoeApplyInfo(coeApplyInfo);

        }
        //成绩单/疫苗表
        Example example3 = new Example(SupplementInfo.class);
        example3.createCriteria().andEqualTo("applyId", applyId)
                .andEqualTo("supplementType", "3").andEqualTo("deleteStatus", false);
        List<SupplementInfo> supplementInfo3 = supplementInfoMapper.selectByExample(example3);
        if (supplementInfo3.size() > 0) {
            coeApplyVo.setSupplementInfo3(supplementInfo3.get(0));
        } else {
            SupplementInfo supplementInfo = new SupplementInfo();
            coeApplyVo.setSupplementInfo3(supplementInfo);
        }
        //录取结果信息
        Example example4 = new Example(SupplementInfo.class);
        example4.createCriteria().andEqualTo("applyId", applyId).
                andEqualTo("supplementType", "4").andEqualTo("deleteStatus", false);
        List<SupplementInfo> supplementInfo4 = supplementInfoMapper.selectByExample(example4);
        if (supplementInfo4.size() > 0) {
            coeApplyVo.setSupplementInfo4(supplementInfo4.get(0));
        } else {
            SupplementInfo supplementInfo = new SupplementInfo();
            coeApplyVo.setSupplementInfo4(supplementInfo);
        }
        return coeApplyVo;
    }

    @Override
    public Integer update(CoeApplyInfo coeApplyInfo1) {
        return coeApplyInfoMapper.updateByPrimaryKeySelective(coeApplyInfo1);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveCoeApplyAndSupplement(CoeApplyVo coeApplyVo) {
        Integer updateCoeApplyRows = 0;
        Integer updateSupplement = 0;
        SysUser sysUser = userService.getLoginUser();
        if (coeApplyVo == null) {
            throw new ContentException(1001, "申请信息缺失");
        }
        //查看当前coe是否有申请结果附件
        Boolean isAttachment = false;
        if(coeApplyVo.getCoeApplyInfo().getId() != null) {
            CoeApplyInfo coeApplyInfo0 = coeApplyInfoMapper.selectByPrimaryKey(coeApplyVo.getCoeApplyInfo().getId());
            if(StringUtils.hasText(coeApplyInfo0.getAttachment()) || StringUtils.hasText(coeApplyVo.getCoeApplyInfo().getAttachment())){
                isAttachment = true;
            }
        }else{
            if(StringUtils.hasText(coeApplyVo.getCoeApplyInfo().getAttachment())){
                isAttachment = true;
            }
        }

        // 判断能否添加审批
        Boolean canApproveFlag = true;
        // 校验coe信息的审批状态
        if (coeApplyVo.getCoeApplyInfo().getId() != null) {
            CoeApplyInfo coe = new CoeApplyInfo();
            coe.setId(coeApplyVo.getCoeApplyInfo().getId());
            List<CoeApplyInfo> coeApplyInfoList = coeApplyInfoMapper.select(coe);
            if (coeApplyInfoList.isEmpty() || ApproveStatusEnum.PASSED.getCode() == coeApplyInfoList.get(0).getApplyStatus()) {
                //不可以修改
                logger.error(MessageFormat.format("Save COE failed！coeApplyInfos:{0}", coeApplyInfoList));
                if (!coeApplyInfoList.isEmpty()) {
                    logger.error("Save COE failed! " + coeApplyInfoList.get(0).toString());
                }
                return false;
            }
            // 申请状态拒绝
            if (coeApplyInfoList.get(0).getApplyStatus() == ApproveStatusEnum.PASSED.getCode()) {
                canApproveFlag = false;
            }
        }
        //更新申请信息
        ApplyInfo applyInfo = coeApplyVo.getApplyInfo();
        applyInfoMapper.updateByPrimaryKeySelective(applyInfo);
        //更新Coe申请信息
        CoeApplyInfo coeApplyInfo = coeApplyVo.getCoeApplyInfo();
        if (coeApplyInfo.getId() != null) { //修改
            coeApplyInfo.setApplyStatus(Contants.APPLYSTATUS_SUBMIT);
            coeApplyInfo.setUpdateTime(new Date());
            update(coeApplyInfo);
        } else { //新增
            coeApplyInfo.setApplyId(coeApplyVo.getApplyInfo().getId());
            coeApplyInfo.setApplyStatus(Contants.APPLYSTATUS_SUBMIT);
            coeApplyInfo.setCreateTime(new Date());
            coeApplyInfo.setDeleteStatus(false);
            coeApplyInfo.setOperatorNo(sysUser.getOaid());
            coeApplyInfo.setOperatorName(sysUser.getUsername());
            coeApplyInfo.setUpdateTime(new Date());
            coeApplyInfoMapper.insert(coeApplyInfo);
        }
        if(StringUtils.hasText(coeApplyInfo.getPreAttachment())){
            CoeAttachmentInfo coeAttachmentInfo = new CoeAttachmentInfo();
            String files[] = coeApplyInfo.getPreAttachment().split(",");
            for(int i=0;i<files.length;i++){
                if(coeApplyInfo.getId() == null){
                    CoeApplyInfo coeApplyInfo1 = new CoeApplyInfo();
                    coeApplyInfo1.setStudentNo(applyInfo.getStudentNo());
                    coeApplyInfo1.setApplyId(applyInfo.getId());
                    List<CoeApplyInfo> coeApplyInfos = coeApplyService.getListNPA(coeApplyInfo1);
                    coeAttachmentInfo.setBusinessId(coeApplyInfos.get(coeApplyInfos.size()-1).getId());
                }else{
                    coeAttachmentInfo.setBusinessId(coeApplyInfo.getId());
                }
                coeAttachmentInfo.setId(null);
                coeAttachmentInfo.setApplyId(applyInfo.getId());
                coeAttachmentInfo.setAttachmentType(2);
                coeAttachmentInfo.setCreatedTime(new Date());
                coeAttachmentInfo.setDeleteStatus(false);
                coeAttachmentInfo.setOperatorNo(sysUser.getOaid());
                coeAttachmentInfo.setOperatorName(sysUser.getUsername());
                coeAttachmentInfo.setStudentNo(coeApplyInfo.getStudentNo());
                coeAttachmentInfo.setAttachment(files[i]);
                coeAttachmentService.add(coeAttachmentInfo);
            }
        }

        SupplementInfo supplementParam3 = coeApplyVo.getSupplementInfo3();
        SupplementInfo supplementParam4 = coeApplyVo.getSupplementInfo4();
        // 判断没有邮寄信息的要跳过
        // 邮寄成绩单或疫苗表信息
        if (supplementParam3.getSendDate() != null) {
            if (supplementParam3.getId() != null) { //修改
                supplementInfoService.update(supplementParam3);
            } else { //添加
                supplementParam3.setApplyId(coeApplyVo.getApplyInfo().getId());
                supplementParam3.setSupplementType(Contants.SUPPLEMENTTYPE_SCORE);
                supplementParam3.setDeleteStatus(false);
                supplementParam3.setCreateTime(new Date());
                supplementParam3.setOperatorNo(sysUser.getOaid());
                supplementInfoMapper.insert(supplementParam3);
            }
        }

        //录取包裹到达信息
        if (supplementParam4.getReceiveDate() != null) {
            if (supplementParam4.getId() != null) { //修改
                supplementInfoService.update(supplementParam4);
            } else { //添加
                supplementParam4.setApplyId(coeApplyVo.getApplyInfo().getId());
                supplementParam4.setSupplementType(Contants.SUPPLEMENTTYPE_ENROLL);
                supplementParam4.setDeleteStatus(false);
                supplementParam4.setCreateTime(new Date());
                supplementParam4.setOperatorNo(sysUser.getOaid());
                supplementInfoMapper.insert(supplementParam4);
            }
        }
        // 没有审批记录 或 审批记录状态为已拒绝时可以添加
        // 插入待审批记录
        Integer coeApplyId = coeApplyVo.getCoeApplyInfo().getId();
        Boolean approveResult = false;
        Integer sequence = 1;
        Integer caseId = CaseIdEnum.CoeApply.getCode();
        String studentNo = coeApplyVo.getCoeApplyInfo().getStudentNo();
        AuditApplyInfo auditApplyInfo = auditApplyService.get(coeApplyId, caseId);
        if (canApproveFlag && isAttachment && auditApplyInfo == null) {
            approveResult = auditApplyService.add(coeApplyId, caseId, sequence, studentNo, "", "");
            if (!approveResult) {
                logger.error(MessageFormat.format("Add COE approve failed! businessId:{0}; caseId:{1}; seq:{2}; studentNo:{3}",
                        coeApplyId, caseId, sequence, studentNo));
            }
            //更新coe申请状态为已申请
            CoeApplyInfo coeApplyInfo1 = new CoeApplyInfo();
            coeApplyInfo1.setId(coeApplyId);
            coeApplyInfo1.setApplyStatus(ApproveStatusEnum.APPLIED.getCode());
            coeApplyService.update(coeApplyInfo1);
        } else {
            approveResult = true;
        }

        return true;
    }


    @Override
    public CoeApplyInfo get(CoeApplyInfo coeApplyInfo) {
        coeApplyInfo.setDeleteStatus(false);
        List<CoeApplyInfo> list = coeApplyInfoMapper.select(coeApplyInfo);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public BaseResponse approve(CoeAuditReq coeAuditReq, Integer nationId) {
        BaseResponse response = new BaseResponse();
        SysUser sysUser = userService.getLoginUser();
        if (sysUser.getOaid() == null) {
            logger.error(ErrorMessage.SESSION_TIMEOUT);
            response.setResult(false);
            response.setErrorMsg("会话超时，请重新登录！");
            return response;
        }

        Integer applyId = coeAuditReq.getApplyId();
        Integer coeApplyId = coeAuditReq.getCoeApplyId();
        Integer type = coeAuditReq.getType();
        String remark = coeAuditReq.getRemark();
        String updateTime = coeAuditReq.getUpdateTime();
        CoeApplyInfo coeApplyInfo = new CoeApplyInfo();
        coeApplyInfo.setId(coeApplyId);
        coeApplyInfo.setDeleteStatus(false);
        coeApplyInfo = coeApplyService.get(coeApplyInfo);
        if (coeApplyInfo.getUpdateTime() != null && !(updateTime.equals(DateFormatUtils.format(coeApplyInfo.getUpdateTime(), Contants.timePattern)))) {
            logger.error("COE信息被更新！！");
            response.setResult(false);
            response.setErrorMsg("需审批内容已修改,请重新审批！");
            return response;
        }

        // 修改状态
        CoeApplyInfo coeApplyInfo1 = new CoeApplyInfo();
        coeApplyInfo1.setId(coeApplyId);
        coeApplyInfo1.setApplyStatus(type);
        int updateResult = coeApplyService.update(coeApplyInfo1);
        if (updateResult < 1) {
            response.setResult(false);
            response.setErrorMsg("COE信息更新失败！");
            return response;
        }
        // 获取待审批审批信息
        AuditApplyInfo auditApplyInfo = auditApplyService.get(coeApplyId, CaseIdEnum.CoeApply.getCode());
        if (auditApplyInfo == null) {
            throw new ContentException(1, "无审批申请！");
        }

        // 添加审批记录
        AuditResultInfo auditResultInfo = new AuditResultInfo();
        auditResultInfo.setApplyStatus(type);
        auditResultInfo.setReason(remark);
        auditApplyInfo.setLastAudit(auditApplyInfo.getLastAudit());
        auditResultInfo.setApplyId(auditApplyInfo.getId());
        auditResultInfo.setBusinessId(coeApplyId);
        auditResultInfo.setCaseId(CaseIdEnum.CoeApply.getCode());
        auditResultInfo.setOperatorNo(sysUser.getOaid());
        auditResultInfo.setOperatorName(sysUser.getUsername());
        auditResultInfo.setCreateTime(new Date());
        auditResultInfo.setUpdateTime(new Date());
        auditResultInfo.setStudentNo(coeApplyInfo.getStudentNo());
        Boolean isOk = auditResultService.add(auditResultInfo);
        if (!isOk) {
            throw new ContentException(1, "COE审批失败！");
        }

        // 修改申请状态
        if (ApproveTypeEnum.PASSED.getCode() == type && !nationId.equals(4) && !nationId.equals(40) && !nationId.equals(41)) {
            boolean result = applyCollegeService.acceptOffer(applyId);
            if (!result) {
                response.setErrorMsg("状态修改失败！");
                response.setResult(false);
                return response;
            }
        }
        response.setResult(true);
        return response;
    }

    @Override
    public Integer delete(Integer id){
        CoeApplyInfo coeApplyInfo = coeApplyInfoMapper.selectByPrimaryKey(id);
        coeApplyInfo.setDeleteStatus(true);
        if(coeApplyInfo.getUpdateTime() != null){
            coeApplyInfo.setUpdateTime(new Date());
        }
        if(coeApplyInfo.getOperatorNo() != null){
            SysUser sysUser = userService.getLoginUser();
            coeApplyInfo.setOperatorNo(sysUser.getOaid());
            coeApplyInfo.setOperatorName(sysUser.getUsername());
        }
        int result = update(coeApplyInfo);

        if(result > 0){
            return 1;
        }else{
            return 0;
        }

    }
}
