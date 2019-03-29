package com.aoji.service.impl;

import com.aoji.contants.TransferRelatedEnum;
import com.aoji.mapper.OldTransferRecordMapper;
import com.aoji.mapper.TransferSendInfoMapper;
import com.aoji.model.OldTransferRecord;
import com.aoji.service.TransferRecordService;
import com.aoji.vo.TransferVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Component
public class TransferRecordServiceImpl implements TransferRecordService{

    @Autowired
    OldTransferRecordMapper oldTransferRecordMapper;
    @Autowired
    TransferSendInfoMapper transferSendInfoMapper;

    @Override
    public List<OldTransferRecord> oldTransferRecord(String studentNo, String operatorType) {
        if(!StringUtils.hasText(studentNo) || !StringUtils.hasText(operatorType)){
            return null;
        }
        Example example = new Example(OldTransferRecord.class);
        example.createCriteria().andEqualTo("studentNo", studentNo).andEqualTo("operatorType", operatorType);
        example.orderBy("receiveTime").asc();
        return oldTransferRecordMapper.selectByExample(example);
    }

    @Override
    public List<TransferVO> newTransferRecord(String studentNo, Integer operatorType) {
        if(!StringUtils.hasText(studentNo)){
            return null;
        }
        return transferSendInfoMapper.queryTransferRecord(studentNo, operatorType);
    }

    @Override
    public List<TransferVO> newTransferRecord2(String studentNo, Integer operatorType) {
        if(!StringUtils.hasText(studentNo)){
            return null;
        }
        if(operatorType.equals(1)){
            operatorType = TransferRelatedEnum.TRAN_OPERATOR_TYPE_1.getCode();
        }else if(operatorType.equals(0)){
            operatorType = TransferRelatedEnum.TRAN_OPERATOR_TYPE_2.getCode();
        }else{
            return null;
        }
        return transferSendInfoMapper.queryTransferRecord2(studentNo, operatorType);
    }
}
