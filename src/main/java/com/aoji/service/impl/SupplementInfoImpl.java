package com.aoji.service.impl;

import com.aoji.contants.Contants;
import com.aoji.mapper.SupplementInfoMapper;
import com.aoji.model.SupplementInfo;
import com.aoji.model.SysUser;
import com.aoji.model.req.SendMessageReq;
import com.aoji.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SupplementInfoImpl implements SupplementInfoService {
    @Autowired
    SupplementInfoMapper supplementInfoMapper;
    @Autowired
    StudentService studentService;
    @Autowired
    ApplyCollegeService applyCollegeService;
    @Autowired
    UserTaskRelationService userTaskRelationService;
    @Autowired
    UserService userService;
    @Autowired
    FileService fileService;

    @Value("${upload.ks3.resDomain}")
    private String resDomain;

    @Override
    public List<SupplementInfo> getList(SupplementInfo supplementInfo) {
        Example example = new Example(SupplementInfo.class);
        if(!StringUtils.hasText(supplementInfo.getExpressNo())){
            example.createCriteria().andEqualTo("deleteStatus",false).andLessThanOrEqualTo("supplementType",Contants.SUPPLEMENTTYPE_ADD).andEqualTo("applyId",supplementInfo.getApplyId());
        }
        else {
            example.createCriteria().andEqualTo("deleteStatus",false).andLessThanOrEqualTo("supplementType",Contants.SUPPLEMENTTYPE_ADD).andEqualTo("applyId",supplementInfo.getApplyId()).andEqualTo("expressNo", supplementInfo.getExpressNo());
        }
        List<SupplementInfo> list= supplementInfoMapper.selectByExample(example);
        for(SupplementInfo item:list) {
            if (!StringUtils.isEmpty(item.getSendDate())) {
                item.setSendDate_string(new SimpleDateFormat(Contants.datePattern).format(item.getSendDate()));
            }
            if (!StringUtils.isEmpty(item.getCreateTime())) {
                item.setCreateTime_string(new SimpleDateFormat(Contants.datePattern).format(item.getCreateTime()));
            }
//            if(!StringUtils.isEmpty(item.getOperatorNo())){
//                SysUser user = userService.getUserByName(item.getOperatorNo());
//                if(!StringUtils.isEmpty(user.getUsername())){
//                    item.setOperatorName(user.getUsername());
//                }
//            }
            if(item != null && StringUtils.hasText(item.getSupplementAttachment()) && !item.getSupplementAttachment().contains(resDomain) ){
//                item.setSupplementAttachment(resDomain +item.getSupplementAttachment());
                item.setSupplementAttachment(fileService.getPrivateUrl(item.getSupplementAttachment()));
            }

        }
        return list;
    }

    //NPA非私密附件，私密附件地址前台获取，不绑定
    @Override
    public List<SupplementInfo> getListNPA(SupplementInfo supplementInfo) {
        Example example = new Example(SupplementInfo.class);
        if(!StringUtils.hasText(supplementInfo.getExpressNo())){
            example.createCriteria().andEqualTo("deleteStatus",false).andLessThanOrEqualTo("supplementType",Contants.SUPPLEMENTTYPE_ADD).andEqualTo("applyId",supplementInfo.getApplyId());
        }
        else {
            example.createCriteria().andEqualTo("deleteStatus",false).andLessThanOrEqualTo("supplementType",Contants.SUPPLEMENTTYPE_ADD).andEqualTo("applyId",supplementInfo.getApplyId()).andEqualTo("expressNo", supplementInfo.getExpressNo());
        }
        List<SupplementInfo> list= supplementInfoMapper.selectByExample(example);
        for(SupplementInfo item:list) {
            if (!StringUtils.isEmpty(item.getSendDate())) {
                item.setSendDate_string(new SimpleDateFormat(Contants.datePattern).format(item.getSendDate()));
            }
            if (!StringUtils.isEmpty(item.getCreateTime())) {
                item.setCreateTime_string(new SimpleDateFormat(Contants.datePattern).format(item.getCreateTime()));
            }
//            if(!StringUtils.isEmpty(item.getOperatorNo())){
//                SysUser user = userService.getUserByName(item.getOperatorNo());
//                if(!StringUtils.isEmpty(user.getUsername())){
//                    item.setOperatorName(user.getUsername());
//                }
//            }
            if(item != null && StringUtils.hasText(item.getSupplementAttachment()) && !item.getSupplementAttachment().contains(resDomain) ){
                item.setSupplementAttachment(resDomain +item.getSupplementAttachment());
//                item.setSupplementAttachment(fileService.getPrivateUrl(item.getSupplementAttachment()));
            }

        }
        return list;
    }

    @Override
    public SupplementInfo getById(Integer id){
        SupplementInfo model = supplementInfoMapper.selectByPrimaryKey(id);
        if(model != null && StringUtils.hasText(model.getSupplementAttachment()) && !model.getSupplementAttachment().contains(resDomain) ){
            model.setSupplementAttachment(resDomain +model.getSupplementAttachment());
        }
        return model;
    }

    @Override
    public Integer update(SupplementInfo supplementInfo) {
        return supplementInfoMapper.updateByPrimaryKeySelective(supplementInfo);
    }

    @Override
    public Integer deleteOne(Integer id){
        return supplementInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Integer addOne(SupplementInfo supplementInfo){
            return supplementInfoMapper.insert(supplementInfo);
    }

    @Override
    public List<SupplementInfo> getListBySupplement(SupplementInfo supplementInfo) {
        return supplementInfoMapper.select(supplementInfo);
    }

}
