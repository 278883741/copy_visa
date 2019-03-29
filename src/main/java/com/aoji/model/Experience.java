package com.aoji.model;
/**
 * author: chenhaibo
 * description: 工作经历
 * date: 2018/1/27
 */
public class Experience {
    /**
     * 公司
     */
    private String experienceCompany;
    /**
     * 职位
     */
    private String experiencePosition;
    /**
     * 时间
     */
    private String experienceDate;

    public String getExperienceCompany() {
        return experienceCompany;
    }

    public void setExperienceCompany(String experienceCompany) {
        this.experienceCompany = experienceCompany;
    }

    public String getExperiencePosition() {
        return experiencePosition;
    }

    public void setExperiencePosition(String experiencePosition) {
        this.experiencePosition = experiencePosition;
    }

    public String getExperienceDate() {
        return experienceDate;
    }

    public void setExperienceDate(String experienceDate) {
        this.experienceDate = experienceDate;
    }
}
