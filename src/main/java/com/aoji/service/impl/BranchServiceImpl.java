package com.aoji.service.impl;

import com.aoji.mapper.BranchInfoMapper;
import com.aoji.model.BranchInfo;
import com.aoji.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchServiceImpl implements BranchService {
    @Autowired
    BranchInfoMapper branchInfoMapper;

    @Override
    public List<BranchInfo> getList(BranchInfo branchInfo){
        return branchInfoMapper.select(branchInfo);
    }

    @Override
    public BranchInfo selectByBranchId(Integer branchId) {
        BranchInfo branchInfo = new BranchInfo();
        branchInfo.setBranchId(branchId);
        List<BranchInfo> branchInfos = branchInfoMapper.select(branchInfo);
        if(branchInfos != null && branchInfos.size() > 0){
            return branchInfos.get(0);
        }
        return null;
    }
}
