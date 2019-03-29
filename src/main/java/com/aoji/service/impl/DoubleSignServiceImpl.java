package com.aoji.service.impl;

import com.aoji.mapper.DoubleSignInfoMapper;
import com.aoji.model.DoubleSignInfo;
import com.aoji.service.DoubleSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoubleSignServiceImpl implements DoubleSignService{
    @Autowired
    DoubleSignInfoMapper doubleSignInfoMapper;

    @Override
    public List<DoubleSignInfo> getList(DoubleSignInfo doubleSignInfo){
        return doubleSignInfoMapper.select(doubleSignInfo);
    }

    @Override
    public DoubleSignInfo get(DoubleSignInfo doubleSignInfo) {
        List<DoubleSignInfo> list = this.getList(doubleSignInfo);
        if(list != null && list.size() >0){
            return list.get(0);
        }
        return null;
    }
}
