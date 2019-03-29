package com.aoji.contants;

/**
 * author: chenhaibo
 * description: 定校方案状态
 * date: 2018/2/9
 */
public enum CollegeAuditStatusEnum {

    APPLIED(0, "已申请"),
    AUDIT_PASSED(1, "审批通过"),
    AUDIT_REJECTED(2, "审批拒绝"),
    VISA_PASSED(3, "文签接受"),
    VISA_REJECTED(4, "文签驳回");

    private int code;

    private String name;

    private CollegeAuditStatusEnum(int code, String name){
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
