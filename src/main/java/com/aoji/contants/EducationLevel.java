package com.aoji.contants;

/**
 * author: chenhaibo
 * description: 专业教育学段
 * date: 2018/11/30
 */
public enum EducationLevel {

    本科新生(2, "中小学"),
    本科转学分(3, "本科"),
    硕士授课型(4, "硕士"),
    硕士研究型(5, "博士"),
    高中(9, "专科"),
    本科(10, "非学历"),
    研究生(11, "证书"),
    博士(12, "副学士");

    private int code;

    private String name;

    EducationLevel(int code, String name) {
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
        if(code != null) {
            EducationLevel[] educationLevels = EducationLevel.values();
            for (int i = 0; i < educationLevels.length; i++) {
                if (educationLevels[i].getCode() == code) {
                    return educationLevels[i].getName();
                }
            }
        }
        return null;
    }

}
