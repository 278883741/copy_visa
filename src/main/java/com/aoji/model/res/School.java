package com.aoji.model.res;

/**
 * author: 赵剑飞
 * description: 查询院校接口返回
 * date: 2018/3/13
 */
public class School {
    private Integer countryID;
    private String countryName;
    private Integer schoolID;
    private String schoolName;
    private Integer courseID;
    private String courseName;
    private Integer majorID;
    private String majorName;
    public void setCountryID(Integer countryID) {
        this.countryID = countryID;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public void setSchoolID(Integer schoolID) {
        this.schoolID = schoolID;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public void setCourseID(Integer courseID) {
        this.courseID = courseID;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setMajorID(Integer majorID) {
        this.majorID = majorID;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public Integer getCountryID() {
        return countryID;
    }

    public String getCountryName() {
        return countryName;
    }

    public Integer getSchoolID() {
        return schoolID;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public Integer getCourseID() {
        return courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public Integer getMajorID() {
        return majorID;
    }

    public String getMajorName() {
        return majorName;
    }
}
