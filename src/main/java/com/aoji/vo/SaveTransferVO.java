package com.aoji.vo;

import java.util.List;

public class SaveTransferVO {

    private Integer id;

    private String studentNo;

    /**
     * 申请Id
     */
    private Integer applyId;

    /**
     * 转案类型： 文签/外联
     */
    private String type;

    /**
     * 分配原因
     */
    private Integer reason;

    private List<TransferReceiverVO> transferReceiverVOList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<TransferReceiverVO> getTransferReceiverVOList() {
        return transferReceiverVOList;
    }

    public void setTransferReceiverVOList(List<TransferReceiverVO> transferReceiverVOList) {
        this.transferReceiverVOList = transferReceiverVOList;
    }

    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    public Integer getReason() {
        return reason;
    }

    public void setReason(Integer reason) {
        this.reason = reason;
    }
}
