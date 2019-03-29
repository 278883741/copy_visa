package com.aoji.contants;

import java.util.Arrays;
import java.util.List;

public enum CaseIdEnum {
    PlanCollege(1, "文案管理-定效方案"),
    FirstBonus(2, "院校申请-首次寄出绩效"),
    ApplyResult(3, "院校申请-申请结果"),
    CoeApply(4, "院校申请-COE/CAS"),
    VisaApply(5, "签证申请"),
    VisaResult(6, "签证结果"),
    VisaRecord(7, "获签信息"),
    FollowService(8, "后续申请结果"),
    FinallyBonus(9, "院校申请-最终寄出绩效"),
    ApproveDelay(10, "申请缓办"),
    StudentSettle(11, "学生结案申请");

    private int code;
    private String name;

    private CaseIdEnum(int code, String name) {
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

    public static <T extends Enum> List<T> getList(Class<T> clazz) {
        return Arrays.asList(clazz.getEnumConstants());
    }
}
