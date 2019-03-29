package com.aoji.service.impl;

import com.aoji.mapper.CoeAttachmentInfoMapper;
import com.aoji.model.CoeAttachmentInfo;
import com.aoji.model.SysUser;
import com.aoji.service.CoeAttachmentService;
import com.aoji.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class CoeAttachmentImpl implements CoeAttachmentService {
    @Autowired
    CoeAttachmentInfoMapper coeAttachmentInfoMapper;
    @Autowired
    UserService userService;
    public Integer add(CoeAttachmentInfo coeAttachmentInfo){
        return coeAttachmentInfoMapper.insert(coeAttachmentInfo);
    }

    public CoeAttachmentInfo select(Integer id){
        return coeAttachmentInfoMapper.selectByPrimaryKey(id);
    }

    public List<CoeAttachmentInfo> getList(Integer businessId){
        Example example = new Example(CoeAttachmentInfo.class);

        example.createCriteria().andEqualTo("deleteStatus", false).andEqualTo("businessId",businessId);

        List<CoeAttachmentInfo> list= coeAttachmentInfoMapper.selectByExample(example);
        return list;
    }

    public Integer update(CoeAttachmentInfo coeAttachmentInfo){
        return coeAttachmentInfoMapper.updateByPrimaryKey(coeAttachmentInfo);
    }

    public Integer delete(Integer id){
        SysUser sysUser = userService.getLoginUser();
        CoeAttachmentInfo coeAttachmentInfo = coeAttachmentInfoMapper.selectByPrimaryKey(id);
        coeAttachmentInfo.setId(id);
        coeAttachmentInfo.setDeleteStatus(true);
        coeAttachmentInfo.setOperatorNo(sysUser.getOaid());
        coeAttachmentInfo.setOperatorName(sysUser.getUsername());
        coeAttachmentInfo.setUpdatedTime(new Date());
        return update(coeAttachmentInfo);

    }
}
