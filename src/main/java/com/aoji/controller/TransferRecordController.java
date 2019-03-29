package com.aoji.controller;

import com.aoji.model.OldTransferRecord;
import com.aoji.service.TransferRecordService;
import com.aoji.vo.TransferVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * author: chenhaibo
 * description: 查看转案记录控制器
 * date: 2018/4/25
 */
@Controller
public class TransferRecordController extends BaseController{

    @Autowired
    TransferRecordService transferRecordService;

    /**
     * 查看转案记录 - 老系统数据
     * @param studentNo
     * @param operatorType 1-文案； 2-文书；3-签证
     * @param model
     * @return
     */
    @RequestMapping("/transfer/record_old")
    public String oldTranferRecord(String studentNo, String operatorType, Model model){
        List<OldTransferRecord> oldTransferRecordList = transferRecordService.oldTransferRecord(studentNo, operatorType);
        model.addAttribute("transferRecord", oldTransferRecordList);
        return "/transfer/oldTransferRecord";
    }

    /**
     * 查看转案记录 - 新系统数据
     * @param studentNo
     * @param operatorType 1-高签； 0-制作文案
     * @param model
     * @return
     */
    @RequestMapping("/transfer/record_new")
    public String newTranferRecord(String studentNo, Integer operatorType, Model model){
        List<TransferVO> transferVOS = transferRecordService.newTransferRecord(studentNo, operatorType);
        model.addAttribute("transferRecord", transferVOS);
        return "/transfer/newTransferRecord";
    }

    /**
     * 查看转案记录 - 新系统数据 - 2
     * @param studentNo
     * @param operatorType 1-高签； 0-制作文案
     * @param model
     * @return
     */
    @RequestMapping("/transferNew/record_new")
    public String newTranferRecord2(String studentNo, Integer operatorType, Model model){
        List<TransferVO> transferVOS = transferRecordService.newTransferRecord2(studentNo, operatorType);
        model.addAttribute("transferRecord", transferVOS);
        return "/transfer/newTransferRecord";
    }
}
