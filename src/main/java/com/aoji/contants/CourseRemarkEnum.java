package com.aoji.contants;

/**
 * author: chenhaibo
 * description: 定校方案课程备注
 * date: 2018/11/30
 */
public enum CourseRemarkEnum {

    本科新生(0, "本科新生"),
    本科转学分(1, "本科转学分"),
    硕士授课型(2, "硕士授课型"),
    硕士研究型(3, "硕士研究型"),
    高中(4, "高中"),
    本科(5, "本科"),
    研究生(6, "研究生"),
    博士(7, "博士"),
    本科转学(8, "本科转学"),
    专升硕(9, "专升硕"),
    社区大学(10, "社区大学"),
    预科或快捷课程(11, "预科或快捷课程"),
    初中(12, "初中"),
    短期语言课(13, "短期语言课"),
    小学(14, "小学");

    private int code;

    private String name;

    CourseRemarkEnum(int code, String name) {
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

    public static String getNameByCode(Integer code){
        if(code != null){
            CourseRemarkEnum[] courseRemarkEnums = CourseRemarkEnum.values();
            for(int i=0; i < courseRemarkEnums.length; i++){
                if(courseRemarkEnums[i].getCode() == code){
                    return courseRemarkEnums[i].getName();
                }
            }
        }
        return null;
    }

}
