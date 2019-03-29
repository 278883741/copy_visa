package com.aoji.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "commission_visa_message")
public class CommissionVisaMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 获签院校id
     */
    @Column(name = "visa_record_result_id")
    private Integer visaRecordResultId;

    /**
     * 学生学号
     */
    @Column(name = "student_no")
    private String studentNo;

    /**
     * 学生姓名
     */
    @Column(name = "student_name")
    private String studentName;

    /**
     * 课程类型
     */
    @Column(name = "course_type")
    private String courseType;

    /**
     * 获签审批日期(关联visa_record_result_info  create_time)
     */
    @Column(name = "visa_date")
    private Date visaDate;

    /**
     * 院校名称
     */
    @Column(name = "college_name")
    private String collegeName;

    /**
     * 课程名称
     */
    @Column(name = "course_name")
    private String courseName;

    /**
     * 专业名称
     */
    @Column(name = "major_name")
    private String majorName;

    /**
     * 是否导出到佣金系统(0-未导入;1-已导入)
     */
    @Column(name = "send_commission_status")
    private Boolean sendCommissionStatus;


    /**
     * 合作机构id
     */
    @Column(name = "cooperation_id")
    private Integer cooperationId;


    /**
     * 合作机构名称
     */
    @Column(name = "cooperation_name")
    private String cooperationName;

    /**
     *数据来源:1-定时任务同步到佣金,2-审核通过直接同步到佣金,3-佣金修改院校同步到文签获签院校表
     */
    @Column(name = "inflow_type")
    private Integer inflowType;

    /**
     *修改前文签数据
     */
    @Column(name = "before_data")
    private String beforeData;
    /**
     *修改后佣金数据
     */
    @Column(name = "after_data")
    private String afterData;



    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取获签院校id
     *
     * @return visa_record_result_id - 获签院校id
     */
    public Integer getVisaRecordResultId() {
        return visaRecordResultId;
    }

    /**
     * 设置获签院校id
     *
     * @param visaRecordResultId 获签院校id
     */
    public void setVisaRecordResultId(Integer visaRecordResultId) {
        this.visaRecordResultId = visaRecordResultId;
    }

    /**
     * 获取学生学号
     *
     * @return student_no - 学生学号
     */
    public String getStudentNo() {
        return studentNo;
    }

    /**
     * 设置学生学号
     *
     * @param studentNo 学生学号
     */
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    /**
     * 获取学生姓名
     *
     * @return student_name - 学生姓名
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * 设置学生姓名
     *
     * @param studentName 学生姓名
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * 获取课程类型
     *
     * @return course_type - 课程类型
     */
    public String getCourseType() {
        return courseType;
    }

    /**
     * 设置课程类型
     *
     * @param courseType 课程类型
     */
    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    /**
     * 获取获签审批日期(关联visa_record_result_info  create_time)
     *
     * @return visa_date - 获签审批日期(关联visa_record_result_info  create_time)
     */
    public Date getVisaDate() {
        return visaDate;
    }

    /**
     * 设置获签审批日期(关联visa_record_result_info  create_time)
     *
     * @param visaDate 获签审批日期(关联visa_record_result_info  create_time)
     */
    public void setVisaDate(Date visaDate) {
        this.visaDate = visaDate;
    }

    /**
     * 获取院校名称
     *
     * @return college_name - 院校名称
     */
    public String getCollegeName() {
        return collegeName;
    }

    /**
     * 设置院校名称
     *
     * @param collegeName 院校名称
     */
    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    /**
     * 获取课程名称
     *
     * @return course_name - 课程名称
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * 设置课程名称
     *
     * @param courseName 课程名称
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * 获取专业名称
     *
     * @return major_name - 专业名称
     */
    public String getMajorName() {
        return majorName;
    }

    /**
     * 设置专业名称
     *
     * @param majorName 专业名称
     */
    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    /**
     * 获取是否导出到佣金系统(0-未导入;1-已导入)
     *
     * @return send_commission_status - 是否导出到佣金系统(0-未导入;1-已导入)
     */
    public Boolean getSendCommissionStatus() {
        return sendCommissionStatus;
    }

    /**
     * 设置是否导出到佣金系统(0-未导入;1-已导入)
     *
     * @param sendCommissionStatus 是否导出到佣金系统(0-未导入;1-已导入)
     */
    public void setSendCommissionStatus(Boolean sendCommissionStatus) {
        this.sendCommissionStatus = sendCommissionStatus;
    }


    public Integer getCooperationId() {
        return cooperationId;
    }

    public void setCooperationId(Integer cooperationId) {
        this.cooperationId = cooperationId;
    }

    public String getCooperationName() {
        return cooperationName;
    }

    public void setCooperationName(String cooperationName) {
        this.cooperationName = cooperationName;
    }

    public Integer getInflowType() {
        return inflowType;
    }

    public void setInflowType(Integer inflowType) {
        this.inflowType = inflowType;
    }

    public String getBeforeData() {
        return beforeData;
    }

    public void setBeforeData(String beforeData) {
        this.beforeData = beforeData;
    }

    public String getAfterData() {
        return afterData;
    }

    public void setAfterData(String afterData) {
        this.afterData = afterData;
    }
}