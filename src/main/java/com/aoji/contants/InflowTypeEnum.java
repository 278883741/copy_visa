package com.aoji.contants;

public enum InflowTypeEnum {
    TIMEDTASK(1, "定时任务同步到佣金"),
    TOEXAMINE(2, "审核通过直接同步到佣金"),
    COMMISSIONTOVISA(3, "佣金修改院校同步到文签获签院校表"),
    TASKCOMMISSIONTOVISA(4, "佣金定时任务修改院校同步到文签获签院校表");

    private Integer code;

    private String name;

    InflowTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
