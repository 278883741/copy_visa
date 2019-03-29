package com.aoji.contants;

/**
 * @author yangsaixing
 * @description 院校申请状态枚举类
 * @date Created in 下午3:30 2018/1/15
 */
public enum ApplyCollegeStatus {

    NO_COLLEGE_MATERIAL(10, "未提交院校申请"),
    NEED_MATERIAL(11, "未补件"),
    NO_COLLEGE_RESULT(20, "未收到申请结果"),
    NO_ACCEPT(30, "未确认录取院校"),
    COLLEGE_YES_COMPLETE(40, "确认录取已完成"),
    COLLEGE_NO_COMPLETE(50, "确认拒绝已完成");

    /**
     * 状态code
     */
    private int code;
    /**
     * 状态名称
     */
    private String name;

    ApplyCollegeStatus(int index, String name) {
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
