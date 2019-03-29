package com.aoji.service.impl;

import com.aoji.contants.Contants;
import com.aoji.contants.ContentException;
import com.aoji.contants.NoteMessageStatus;
import com.aoji.mapper.ApplyInfoMapper;
import com.aoji.mapper.TransferReceiveInfoMapper;
import com.aoji.mapper.TransferSendInfoMapper;
import com.aoji.model.*;
import com.aoji.service.*;
import com.aoji.vo.TransferVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TransferReceiveServiceImpl implements TransferReceiveService {

    public static final Logger logger = LoggerFactory.getLogger(TransferReceiveServiceImpl.class);

    @Autowired
    private TransferReceiveInfoMapper transferReceiveInfoMapper;
    @Autowired
    private TransferSendInfoMapper transferSendInfoMapper;
    @Autowired
    private TransferSendService transferSendService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ApplyInfoMapper applyInfoMapper;
    @Autowired
    private TransferAssignRecordService transferAssignRecordService;
    @Autowired
    private SendStudentMessageService sendStudentMessageService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(Integer id, Integer confirmStatus, String comment) {

        SysUser sysUser = userService.getLoginUser();
        if(sysUser == null || sysUser.getOaid() == null){
            return false;
        }
        // 查询转案信息
        TransferSendInfo transferSendInfo = transferSendService.getTransferSendById(id);
        if(transferSendInfo == null){
            return false;
        }
        // 分配给自己
        boolean toMyself = false;
        if(transferSendInfo.getOperatorNo().equals(transferSendInfo.getReceiverNo())){
            toMyself = true;
        }

        // 查询接收信息
        TransferReceiveInfo transferReceive = new TransferReceiveInfo();
        transferReceive.setDeleteStatus(false);
        transferReceive.setSendId(transferSendInfo.getId());
        List<TransferReceiveInfo> transferReceiveInfos = transferReceiveInfoMapper.select(transferReceive);
        if(!transferReceiveInfos.isEmpty()){
            for(TransferReceiveInfo tri : transferReceiveInfos){
                if(tri.getConfirmStatus() != null){
                    logger.error("转案确认状态已改变！！");
                    return false;
                }
            }
        }

        // 文签经理接受时要判断
        List<String> roles = roleService.getRolesByOaId(sysUser.getOaid());
        if(roles.contains(Contants.ROLE_VISA_MANAGER) && Integer.valueOf(1).equals(confirmStatus) && !toMyself){
            // 查询接受信息数量
            int transferConfirmCount = transferSendInfoMapper.transferConfirmCount(transferSendInfo.getStudentNo());
            // 已接受
            if(transferConfirmCount > 0){
                return false;
            }
            // 如果是经理接受的话，禁用其他人的可用状态
            transferSendInfoMapper.updateByStudentNoAndId(transferSendInfo.getStudentNo(), transferSendInfo.getId());
        }

        //添加转案记录
        TransferReceiveInfo transferReceiveInfo = new TransferReceiveInfo();
        transferReceiveInfo.setCreateTime(new Date());
        transferReceiveInfo.setDeleteStatus(false);
        transferReceiveInfo.setSendId(id);
        transferReceiveInfo.setConfirmStatus(confirmStatus);
        transferReceiveInfo.setComment(comment);
        transferReceiveInfo.setOperatorName(sysUser.getUsername());
        transferReceiveInfo.setOperatorNo(sysUser.getOaid());
        int insertResult = transferReceiveInfoMapper.insert(transferReceiveInfo);
        if (insertResult < 1){
            return false;
        }
        if(Integer.valueOf(1).equals(confirmStatus)) {
            if (roles.contains(Contants.ROLE_VISA_MANAGER) || roles.contains(Contants.ROLE_VISA_CONSULTANT)) {

                //查询转案分配记录
                int serviceCode = 0;
                List<TransferAssignRecord> transferAssignRecords = transferAssignRecordService.getbyStudentNo(transferSendInfo.getStudentNo(), Contants.TRANSFER_TYPE_VISA);
                if(transferAssignRecords.size() > 0){
                    serviceCode = NoteMessageStatus.NOTE_NO_VISA_RESULT.getCode();
                }else{
                    serviceCode = NoteMessageStatus.NOTE_NO_COPY.getCode();
                }

                //  先判断转案是否完成
                List<TransferVO> transferVOS = transferSendInfoMapper.getTransferResult(transferSendInfo.getStudentNo());
                boolean result = true; //转案结果
                for(TransferVO transferVO : transferVOS){
                    if(transferVO.getConfirmStatus() == null || !transferVO.getConfirmStatus().equals(1)){
                        result = false;
                    }
                }

                //将文签顾问更新到学生表
                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setStudentNo(transferSendInfo.getStudentNo());
                if(roles.contains(Contants.ROLE_VISA_MANAGER)){

                    if(toMyself) {
                        if(result) {
                            transferVOS.forEach(transferVO -> {
                                if (transferVO.isCopyOperator()) {
                                    studentInfo.setCopyOperatorNo(transferVO.getReceiverNo());
                                    studentInfo.setCopyOperator(transferVO.getReceiverName());
                                } else {
                                    studentInfo.setCopyMaker(transferVO.getReceiverName());
                                    studentInfo.setCopyMakerNo(transferVO.getReceiverNo());
                                }
                            });
                            studentService.updateByStudentNo(studentInfo);

                            //发送短信
                            try {
                                if(transferSendInfo.isCopyOperator() || (!transferSendInfo.isCopyOperator() && transferSendInfo.getCopyIsChange())) {
                                    sendStudentMessageService.sendStudentMessage(sysUser, serviceCode, transferSendInfo.getStudentNo(), false, null);
                                }
                            } catch (Exception e) {
                                logger.error("sendStudentMessage failed! " + e.getMessage());
                            }

                            //添加转案分配记录
                            transferAssignRecordService.insert(transferSendInfo.getStudentNo(), Contants.TRANSFER_TYPE_VISA, sysUser.getOaid());
                        }
                    }else{
                        // 签约及转案
                        studentInfo.setCopyOperator(sysUser.getUsername());
                        studentInfo.setCopyOperatorNo(sysUser.getOaid());
                        studentService.updateByStudentNo(studentInfo);
                    }
                }else{
                    if(result){
                        transferVOS.forEach(transferVO -> {
                            if(transferVO.isCopyOperator()){
                                studentInfo.setCopyOperatorNo(transferVO.getReceiverNo());
                                studentInfo.setCopyOperator(transferVO.getReceiverName());
                            }else{
                                studentInfo.setCopyMaker(transferVO.getReceiverName());
                                studentInfo.setCopyMakerNo(transferVO.getReceiverNo());
                            }
                        });
                        studentService.updateByStudentNo(studentInfo);

                        //发送短信
                        try {
                            if(transferSendInfo.isCopyOperator() || (!transferSendInfo.isCopyOperator() && transferSendInfo.getCopyIsChange())) {
                                sendStudentMessageService.sendStudentMessage(sysUser, serviceCode, transferSendInfo.getStudentNo(), false, null);
                            }
                        }catch (Exception e){
                            logger.error("sendStudentMessage failed! "+e.getMessage());
                        }

                        //添加转案分配记录
                        transferAssignRecordService.insert(transferSendInfo.getStudentNo(), Contants.TRANSFER_TYPE_VISA, sysUser.getOaid());
                    }
//                    toMyself = true;
                }
                //发送短信
//                if(transferSendInfo.isCopyOperator() && toMyself){
//                    try {
//                        sendStudentMessageService.sendStudentMessage(sysUser, serviceCode, transferSendInfo.getStudentNo(), false, null);
//                    }catch (Exception e){
//                        logger.error("sendStudentMessage failed! "+e.getMessage());
//                    }
//                }
            } else if (roles.contains(Contants.ROLE_OUTREACH_MANAGER) || roles.contains(Contants.ROLE_OUTREACH_CONSULTANT)) {
                //将外联顾问更新到申请表
                ApplyInfo applyInfo = new ApplyInfo();
                applyInfo.setId(transferSendInfo.getApplyId());
                applyInfo.setConnector(sysUser.getOaid());
                applyInfo.setConnectorName(sysUser.getUsername());
                applyInfoMapper.updateByPrimaryKeySelective(applyInfo);
            }

        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean autoReceive(Integer id, String receiveNo, String receiver, String studentNo) {
        //添加转案记录
        TransferReceiveInfo transferReceiveInfo = new TransferReceiveInfo();
        transferReceiveInfo.setCreateTime(new Date());
        transferReceiveInfo.setDeleteStatus(false);
        transferReceiveInfo.setSendId(id);
        transferReceiveInfo.setConfirmStatus(1);
        transferReceiveInfo.setOperatorName("System");
        transferReceiveInfo.setOperatorNo("System");
        int insertResult = transferReceiveInfoMapper.insert(transferReceiveInfo);
        if(insertResult > 0){
            //将文签顾问更新到学生表
            StudentInfo studentInfo = new StudentInfo();
            studentInfo.setStudentNo(studentNo);
                studentInfo.setCopyOperator(receiver);
                studentInfo.setCopyOperatorNo(receiveNo);
                Boolean updateResult = studentService.updateByStudentNo(studentInfo);
                if(!updateResult){
                    throw new ContentException(1001, "更新学生文签顾问信息失败！");
                }
        }
        return true;
    }

}
