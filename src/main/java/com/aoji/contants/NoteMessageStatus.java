package com.aoji.contants;

/**
 * @author wangjunqiang
 * @description
 * @date Created in 2018/4/8 13:53
 */
public enum NoteMessageStatus {

    NOTE_NO_COPY(10, "经理转文案"),
    NOTE_NO_COLLEGE_APPLY(20, "文签接收定校信息"),
    NOTE_NO_COLLEGE_RESULT(30, "学校申请递交"),
    NOTE_NO_COLLEGE_INFO(40, "申请结果上传"),
    NOTE_NO_VISA_COACH(50, "签证递交后发送"),
    NOTE_NO_VISA_APPLY(60, "签证获签后发送"),
    NOTE_NO_VISA_RESULT(70, "经理分配转接文签顾问");

    /**
     * 状态code
     */
    private int code;
    /**
     * 状态名称
     */
    private String name;


    NoteMessageStatus(int index, String name) {
        this.name = name;
        this.code = index;
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
