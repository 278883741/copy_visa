package com.aoji.service.impl;

import com.aoji.mapper.ExpressCompanyMapper;
import com.aoji.model.ExpressCompany;
import com.aoji.service.ExpressCompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpressCompanyServiceImpl implements ExpressCompanyService{
    @Autowired
    ExpressCompanyMapper expressCompanyMapper;
    private Logger logger= LoggerFactory.getLogger(StudentServiceImpl.class);

    @Override
    public List<ExpressCompany> getList(ExpressCompany expressCompany) {
        return expressCompanyMapper.select(expressCompany);
    }

}
