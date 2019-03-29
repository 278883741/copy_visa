package com.aoji.vo;

import org.springframework.util.StringUtils;

/**
 * Im 学生搜索视图对象
 */
public class IMStudentSearchVO {

    /**
     * 学号
     */
    private String student_no;

    /**
     * 用户编号
     */
    private String user_no;

    /**
     * 姓名
     */
    private String name;

    /**
     * 群组id
     */
    private String group_id;

    /**
     * 名字简写 取名字的后两个字
     */
    private String shortName;

    public String getShortName(){
        if(StringUtils.hasText(name) && name.length() >= 2){
            return name.substring(name.length() - 2, name.length());
        }
        return null;
    }

    public String getUser_no() {
        return user_no;
    }

    public void setUser_no(String user_no) {
        this.user_no = user_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getStudent_no() {
        return student_no;
    }

    public void setStudent_no(String student_no) {
        this.student_no = student_no;
    }
}
