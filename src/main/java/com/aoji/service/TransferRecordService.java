package com.aoji.service;

import com.aoji.model.OldTransferRecord;
import com.aoji.vo.TransferVO;

import java.util.List;

/**
 * author: chenhaibo
 * description: 转案记录
 * date: 2018/4/25
 */
public interface TransferRecordService {

    /**
     * 老系统的转案记录
     * @param studentNo
     * @param operatorType 1-文案； 2-文书；3-签证
     * @return
     */
    List<OldTransferRecord> oldTransferRecord(String studentNo, String operatorType);

    /**
     * 新系统转案记录
     * @param studentNo
     * @param operatorType 1-高签； 0-制作文案
     * @return
     */
    List<TransferVO> newTransferRecord(String studentNo, Integer operatorType);

    /**
     * 新系统转案记录-2
     * @param studentNo
     * @param operatorType 1-高签； 0-制作文案
     * @return
     */
    List<TransferVO> newTransferRecord2(String studentNo, Integer operatorType);
}
