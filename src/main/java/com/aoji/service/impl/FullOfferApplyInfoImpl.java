package com.aoji.service.impl;

import com.aoji.model.FullofferApplyInfo;
import com.aoji.mapper.FullofferApplyInfoMapper;
import com.aoji.service.FullOfferApplyServices;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

@Service
public class FullOfferApplyInfoImpl implements FullOfferApplyServices {
    @Autowired
    FullofferApplyInfoMapper fullofferApplyInfoMapper;

    @Override
    public FullofferApplyInfo getById(Integer id){
        FullofferApplyInfo model = fullofferApplyInfoMapper.selectByPrimaryKey(id);
        return model;
    }
}
