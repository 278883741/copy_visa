package com.aoji.model;

/**
 * author: chenhaibo
 * description: 学历信息
 * date: 2018/1/27
 */
public class Education {
    /**
     * 学历
     */
    private String educationName;
    /**
     * 学校
     */
    private String schoolName;
    /**
     * 课程
     */
    private String courseName;
    /**
     * 起止时间
     */
    private String schoolDate;

    public String getEducationName() {
        return educationName;
    }

    public void setEducationName(String educationName) {
        this.educationName = educationName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSchoolDate() {
        return schoolDate;
    }

    public void setSchoolDate(String schoolDate) {
        this.schoolDate = schoolDate;
    }
}
