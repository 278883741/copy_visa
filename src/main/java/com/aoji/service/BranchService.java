package com.aoji.service;

import com.aoji.model.BranchInfo;
import java.util.List;

public interface BranchService {
    List<BranchInfo> getList(BranchInfo branchInfo);

    BranchInfo selectByBranchId(Integer branchId);
}
