package com.aoji.service.impl;

import com.aoji.contants.CaseIdEnum;
import com.aoji.contants.Contants;
import com.aoji.mapper.FollowServiceInfoMapper;
import com.aoji.mapper.FollowServiceResultMapper;
import com.aoji.model.FollowServiceInfo;
import com.aoji.service.AuditApplyService;
import com.aoji.service.FollowServiceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class FollowServiceInfoServiceImpl implements FollowServiceInfoService {

    @Autowired
    FollowServiceInfoMapper followServiceInfoMapper;

    @Autowired
    FollowServiceResultMapper followServiceResultMapper;
    @Autowired
    AuditApplyService auditApplyService;

    @Value("${upload.ks3.resDomain}")
    private String resDomain;
    /**
     * 获取后续申请信息列表数据
     * @param followServiceInfo
     * @return
     */
    @Override
    public List<FollowServiceInfo> getList(FollowServiceInfo followServiceInfo) {
        List<FollowServiceInfo> followServiceInfos = followServiceInfoMapper.select(followServiceInfo);
        for (FollowServiceInfo followServiceInfo1:followServiceInfos) {
            if(followServiceInfo1 != null && StringUtils.hasText(followServiceInfo1.getAttachment()) && !followServiceInfo1.getAttachment().contains(resDomain)){
                followServiceInfo1.setAttachment(resDomain +followServiceInfo1.getAttachment());
            }
        }
        return followServiceInfos;
    }

    /**
     * 查询列表（按创建时间倒序排列）
     * @param followServiceInfo
     * @return
     */
    @Override
    public List<FollowServiceInfo> getListByExample(FollowServiceInfo followServiceInfo){
        Example example = new Example(FollowServiceInfo.class);
        example.createCriteria().andEqualTo("studentNo", followServiceInfo.getStudentNo());
        if(followServiceInfo.getApplyStatus() != null) {
            example.createCriteria().andEqualTo("applyStatus", followServiceInfo.getApplyStatus());
        }
        if(followServiceInfo.getVisitType() != null) {
            example.createCriteria().andEqualTo("visitType", followServiceInfo.getVisitType());
        }
        example.setOrderByClause(Contants.CREATE_TIME + " desc");
        return followServiceInfoMapper.selectByExample(example);
    }

    /**
     * 添加后续管理
     * @param followServiceInfo
     * @return
     */
    @Override
    public int insert(FollowServiceInfo followServiceInfo) {
//        if(followServiceInfoMapper.insert(followServiceInfo)> 0 ){
//            auditApplyService.add(followServiceInfo.getId(), CaseIdEnum.FollowService.getCode(), 1, followServiceInfo.getStudentNo(),true);
//            return 1;
//        }
        return followServiceInfoMapper.insert(followServiceInfo);
    }

    /**
     * 修改
     * @param followServiceInfo
     * @return
     */
    @Override
    public int updateById(FollowServiceInfo followServiceInfo) {
        return followServiceInfoMapper.updateByPrimaryKeySelective(followServiceInfo);
    }

    @Override
    public int deleteFollowServiceInfo(FollowServiceInfo followServiceInfo) {
        followServiceInfo.setDeleteStatus(true);
        return followServiceInfoMapper.updateByPrimaryKeySelective(followServiceInfo);
    }
}
