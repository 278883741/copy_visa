package com.aoji.service.impl;

import com.aoji.mapper.CommissionRecordInfoMapper;
import com.aoji.model.CommissionRecordInfo;
import com.aoji.model.SysUser;
import com.aoji.service.CommissionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommissionRecordServiceImpl implements CommissionRecordService {
    @Autowired
    CommissionRecordInfoMapper commissionRecordInfoMapper;


    @Override
    public List<CommissionRecordInfo> getList(CommissionRecordInfo commissionRecordInfo,String dateStart, String dateEnd){
        return commissionRecordInfoMapper.getList(commissionRecordInfo,dateStart,dateEnd);
    }

    @Override
    public Integer add(CommissionRecordInfo commissionRecordInfo){
        return commissionRecordInfoMapper.insert(commissionRecordInfo);
    }

    @Override
    public CommissionRecordInfo getById(Integer id){
        return commissionRecordInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer update(CommissionRecordInfo commissionRecordInfo){
        return commissionRecordInfoMapper.updateByPrimaryKeySelective(commissionRecordInfo);
    }

    @Override
    public Integer delete(Integer id){
        CommissionRecordInfo commissionRecordInfo = new CommissionRecordInfo();
        commissionRecordInfo.setId(id);
        commissionRecordInfo.setDeleteStatus(true);
        return commissionRecordInfoMapper.updateByPrimaryKeySelective(commissionRecordInfo);
    }
}
