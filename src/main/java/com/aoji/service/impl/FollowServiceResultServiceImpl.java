package com.aoji.service.impl;

import com.aoji.contants.CaseIdEnum;
import com.aoji.contants.ContentException;
import com.aoji.contants.ErrorMessage;
import com.aoji.controller.FollowServiceInfoController;
import com.aoji.mapper.FollowServiceResultMapper;
import com.aoji.mapper.SysUserMapper;
import com.aoji.model.*;
import com.aoji.service.AuditApplyService;
import com.aoji.service.AuditResultService;
import com.aoji.service.FollowServiceResultService;
import com.aoji.service.UserService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class FollowServiceResultServiceImpl implements FollowServiceResultService {

    public static final Logger logger = LoggerFactory.getLogger(FollowServiceResultService.class);

    @Autowired
    FollowServiceResultMapper followServiceResultMapper;

    @Autowired
    AuditApplyService auditApplyService;

    @Autowired
    AuditResultService auditResultService;

    @Autowired
    UserService userService;

    @Autowired
    SysUserMapper sysUserMapper;

    @Value("${upload.ks3.resDomain}")
    private String resDomain;

    @Override
    public List<FollowServiceResult> getResultList(FollowServiceResult followServiceResult) {
        List<FollowServiceResult> followServiceResults = followServiceResultMapper.select(followServiceResult);
        for (FollowServiceResult followServiceResult1:followServiceResults) {
            if(followServiceResult1 != null && StringUtils.hasText(followServiceResult1.getOfferAttachment()) && !followServiceResult1.getOfferAttachment().contains(resDomain)){
                followServiceResult1.setOfferAttachment(resDomain +followServiceResult1.getOfferAttachment());
            }
        }
        return followServiceResults;
    }

    /**
     * 根据id获取申请结果信息
     * @param id
     * @return
     */
    @Override
    public FollowServiceResult getFollowServiceResultById(String id) {
        FollowServiceResult followServiceResult = followServiceResultMapper.getFollowServiceResultById(id);
        return followServiceResult;
    }

    /**
     * 修改学生申请表
     * @param followServiceResult
     * @return
     */
    @Override
    public int update(FollowServiceResult followServiceResult) {
        Example example=new Example(FollowServiceResult.class);
        example.createCriteria().andEqualTo("id",followServiceResult.getId()).andEqualTo("deleteStatus",0);
        return followServiceResultMapper.updateByExampleSelective(followServiceResult,example);
    }

    @Override
    public int updateFollowResult(FollowServiceResult followServiceResult) {
        return followServiceResultMapper.updateFollowServicResult(followServiceResult);
    }

    @Override
    public int save(FollowServiceResult followServiceResult) {
        return followServiceResultMapper.insert(followServiceResult);
    }

    @Override
    public FollowServiceResult get(FollowServiceResult followServiceResult){
        followServiceResult.setDeleteStatus(false);
        List<FollowServiceResult> list = followServiceResultMapper.select(followServiceResult);
        if(list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public BaseResponse approve(Integer applyId, Integer type, String remark, String studentNo, String updateTime) {
        BaseResponse baseResponse = new BaseResponse();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SysUser sysUser = userService.getLoginUser();
        if(sysUser.getOaid() == null){
            logger.error(ErrorMessage.SESSION_TIMEOUT);
            baseResponse.setResult(false);
            baseResponse.setErrorCode("1");
            baseResponse.setErrorMsg("操作失败");
            return baseResponse;
        }
        //查询数据库中待审批的数据
        FollowServiceResult followServiceResultOld = followServiceResultMapper.getFollowServiceResultById(String.valueOf(applyId));
        logger.info("原始时间"+updateTime);
        logger.info("修改时间"+followServiceResultOld.getUpdateTime());
        updateTime = updateTime == null ? "" : updateTime;
        if(followServiceResultOld != null && followServiceResultOld.getUpdateTime() != null && !updateTime.equals(simpleDateFormat.format(followServiceResultOld.getUpdateTime()))){
            baseResponse.setResult(false);
            baseResponse.setErrorCode("2");
            baseResponse.setErrorMsg("需审批内容已修改,请重新审批!");
            return baseResponse;
        }
        // 修改状态
        FollowServiceResult followServiceResult = new FollowServiceResult();
        followServiceResult.setId(applyId);
        if(type ==2){
            followServiceResult.setAuditStatus(3);
        }else if(type ==1){
            followServiceResult.setAuditStatus(4);
        }
        int result = followServiceResultMapper.updateByPrimaryKeySelective(followServiceResult);
        if(result < 1){
            baseResponse.setResult(false);
            baseResponse.setErrorCode("1");
            baseResponse.setErrorMsg("操作失败");
            return baseResponse;
        }

        AuditApplyInfo auditApplyInfo = auditApplyService.get(applyId, CaseIdEnum.FollowService.getCode());
        if(auditApplyInfo == null){
            throw new ContentException(1, "");
        }

        // 修改审批信息
        AuditResultInfo auditResultInfo = new AuditResultInfo();
        auditResultInfo.setStudentNo(studentNo);
        auditResultInfo.setApplyStatus(type);
        auditResultInfo.setReason(remark);
        auditApplyInfo.setLastAudit(auditApplyInfo.getLastAudit());
        auditResultInfo.setApplyId(auditApplyInfo.getId());
        auditResultInfo.setBusinessId(applyId);
        auditResultInfo.setCaseId(CaseIdEnum.FollowService.getCode());
        auditResultInfo.setOperatorNo(sysUser.getOaid());
        auditResultInfo.setOperatorName(sysUser.getUsername());
        auditResultInfo.setCreateTime(new Date());
        auditResultInfo.setUpdateTime(new Date());
        Boolean isOk = auditResultService.add(auditResultInfo);
        if(!isOk){
            throw new ContentException(1, "");
        }
        baseResponse.setResult(true);
        baseResponse.setErrorCode("0");
        baseResponse.setErrorMsg("操作成功");
        return baseResponse;
    }

    @Override
    public List<SysUser> getSysUserByRoleName(String roleName) {
        return sysUserMapper.getSysUsersByRoleName(roleName);
    }

    @Override
    public List<String> getOaidsByRoleName(String roleName) {
        return sysUserMapper.getOaidsByRoleName(roleName);
    }
}
