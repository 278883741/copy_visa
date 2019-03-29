package com.aoji.contants;

import javax.persistence.Transient;

/**
 * @author 赵剑飞
 * @description 包装前端页面搜索条件类
 * @date Created in 下午2:25 2018/12/13
 */
public class SearchOption {
    /**
     * 学号
     */
    private String searchStudentNo;
    public void setSearchStudentNo(String searchStudentNo) {
        this.searchStudentNo = searchStudentNo;
    }
    public String getSearchStudentNo() {
        return searchStudentNo;
    }

    /**
     * 学生姓名
     */
    private String searchStudentName;
    public void setSearchStudentName(String searchStudentName) {
        this.searchStudentName = searchStudentName;
    }
    public String getSearchStudentName() {
        return searchStudentName;
    }

    /**
     * 国家
     */
    private Integer searchNationId;
    public void setSearchNationId(Integer searchNationId) {
        this.searchNationId = searchNationId;
    }
    public Integer getSearchNationId() {
        return searchNationId;
    }

    /**
     * 送件时间
     */
    private String searchSendDateStart;
    public void setSearchSendDateStart(String searchSendDateStart) {
        this.searchSendDateStart = searchSendDateStart;
    }
    public String getSearchSendDateStart() {
        return searchSendDateStart;
    }
    private String searchSendDateEnd;
    public void setSearchSendDateEnd(String searchSendDateEnd) {
        this.searchSendDateEnd = searchSendDateEnd;
    }
    public String getSearchSendDateEnd() {
        return searchSendDateEnd;
    }

    /**
     * 创建时间（提交时间）
     */
    private String searchCreateDateStart;
    public void setSearchCreateDateStart(String searchCreateDateStart) {
        this.searchCreateDateStart = searchCreateDateStart;
    }
    public String getSearchCreateDateStart() {
        return searchCreateDateStart;
    }
    private String searchCreateDateEnd;
    public void setSearchCreateDateEnd(String searchCreateDateEnd) {
        this.searchCreateDateEnd = searchCreateDateEnd;
    }
    public String getSearchCreateDateEnd() {
        return searchCreateDateEnd;
    }

    /**
     * 签证结果
     */
    private Integer searchVisaResult;
    public void setSearchVisaResult(Integer searchVisaResult) {
        this.searchVisaResult = searchVisaResult;
    }
    public Integer getSearchVisaResult() {
        return searchVisaResult;
    }

    private Integer searchIsAudited;
    public void setSearchIsAudited(Integer searchIsAudited) {
        this.searchIsAudited = searchIsAudited;
    }
    public Integer isSearchIsAudited() {
        return searchIsAudited;
    }
}
