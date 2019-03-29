package com.aoji.service.impl;
import com.aoji.contants.CaseIdEnum;
import com.aoji.mapper.VisaResultRecordInfoMapper;
import com.aoji.model.SysUser;
import com.aoji.model.VisaResultRecordInfo;
import com.aoji.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * @author zhaojianfei
 * @description 获签信息院校表 -- visa_record_id与visa_record_info的id关联,数据来自apply_info
 * @date Created in 下午2:31 2017/12/7
 */
@Service
public class VisaResultRecordServiceImpl implements VisaResultRecordService {
    @Autowired
    VisaResultRecordInfoMapper visaResultRecordInfoMapper;
    @Autowired
    UserService userService;

    @Override
    public boolean add(VisaResultRecordInfo visaResultRecordInfo){
        return visaResultRecordInfoMapper.insertSelective(visaResultRecordInfo) > 0;
    }
    @Override
    public List<VisaResultRecordInfo> getListByRecordId(Integer recordId){
        VisaResultRecordInfo visaResultRecordInfo = new VisaResultRecordInfo();
        visaResultRecordInfo.setVisaRecordId(recordId);
        visaResultRecordInfo.setDeleteStatus(false);
        return visaResultRecordInfoMapper.select(visaResultRecordInfo);
    }
    @Override
    public void deleteByRecordId(Integer recordId){
        List<VisaResultRecordInfo> list = getListByRecordId(recordId);
        for (VisaResultRecordInfo item :list){
            item.setDeleteStatus(true);
            updateOne(item);
        }
    }
    @Override
    public Integer delete(Integer id){
        VisaResultRecordInfo visaResultRecordInfo = new VisaResultRecordInfo();
        visaResultRecordInfo.setId(id);
        visaResultRecordInfo.setDeleteStatus(false);
        visaResultRecordInfo = get(visaResultRecordInfo);
        if(visaResultRecordInfo != null){
            SysUser sysUser = userService.getLoginUser();
            visaResultRecordInfo.setDeleteStatus(true);
            visaResultRecordInfo.setOperatorNo(sysUser.getOaid());
            visaResultRecordInfo.setOperatorName(sysUser.getUsername());
            return updateOne(visaResultRecordInfo);
        }
        return -1;
    }
    @Override
    public Integer updateOne(VisaResultRecordInfo visaResultRecordInfo){
        return visaResultRecordInfoMapper.updateByPrimaryKeySelective(visaResultRecordInfo);
    }
    @Override
    public VisaResultRecordInfo get(VisaResultRecordInfo visaResultRecordInfo){
        List<VisaResultRecordInfo>list = visaResultRecordInfoMapper.select(visaResultRecordInfo);
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
