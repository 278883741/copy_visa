package com.aoji.service.impl;

import com.aoji.contants.StudentStatus;
import com.aoji.mapper.StudentInfoMapper;
import com.aoji.mapper.StudentServiceInfoMapper;
import com.aoji.mapper.StudentStatusRecordMapper;
import com.aoji.mapper.PlanInfoMapper;
import com.aoji.model.*;
import com.aoji.service.PlanInfoService;
import com.aoji.service.StudentService;
import com.aoji.service.UserGroupRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PlanInfoServiceImpl implements PlanInfoService{
    @Autowired
    PlanInfoMapper planInfoMapper;

    private Logger logger= LoggerFactory.getLogger(StudentServiceImpl.class);

    @Override
    public PlanInfo get(PlanInfo planInfo){
        planInfo.setDeleteStatus(false);
        List<PlanInfo> list = planInfoMapper.select(planInfo);
        if(list.size() > 0){
            return list.get(0);
        }
        return null;
    }
}
