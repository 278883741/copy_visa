package com.aoji.model;

import javax.persistence.*;

@Table(name = "channel_student_no")
public class ChannelStudentNo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 新增到的最大学号
     */
    @Column(name = "student_no")
    private String studentNo;

    /**
     * 新工号（用于页面展示和匹配）
     */
    @Column(name = "new_oa_no")
    private String newOa;

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
     * 获取新增到的最大学号
     *
     * @return student_no - 新增到的最大学号
     */
    public String getStudentNo() {
        return studentNo;
    }

    /**
     * 设置新增到的最大学号
     *
     * @param studentNo 新增到的最大学号
     */
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getNewOa() {
        return newOa;
    }

    public void setNewOa(String newOa) {
        this.newOa = newOa;
    }
}