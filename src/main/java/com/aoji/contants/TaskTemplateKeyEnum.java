package com.aoji.contants;

/**
 * author: chenhaibo
 * description: 消息模板参数 key 模板
 * date: 2018/1/15
 */
public enum TaskTemplateKeyEnum {

    STUDENT_NO("studentNo", "学号"),
    STUDENT_NAME("studentName", "学生姓名"),
    COLLEGE_NAME("collegeName", "学校名称");

    private String code;

    private String name;

    private TaskTemplateKeyEnum(String code, String name){
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
