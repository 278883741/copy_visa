package com.aoji.contants;

/**
 * author: chenhaibo
 * description: 审批状态
 * date: 2018/2/9
 */
public enum ApproveStatusEnum {

    APPLIED(1, "审批已提交"),
    PASSED(2, "审批已通过"),
    REFUSED(3, "审批已拒绝");

    private int code;

    private String name;

    private ApproveStatusEnum(int code, String name){
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
