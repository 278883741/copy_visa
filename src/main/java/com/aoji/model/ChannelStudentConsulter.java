package com.aoji.model;

import javax.persistence.*;

@Table(name = "channel_student_consulter")
public class ChannelStudentConsulter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 咨询顾问名称
     */
    private String consulter;

    /**
     * 咨询顾问工号
     */
    @Column(name = "consulter_no")
    private String consulterNo;

    /**
     * 咨询顾问邮箱账号
     */
    @Column(name = "email")
    private String email;

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
     * 获取咨询顾问名称
     *
     * @return consulter - 咨询顾问名称
     */
    public String getConsulter() {
        return consulter;
    }

    /**
     * 设置咨询顾问名称
     *
     * @param consulter 咨询顾问名称
     */
    public void setConsulter(String consulter) {
        this.consulter = consulter;
    }

    public String getConsulterNo() {
        return consulterNo;
    }

    public void setConsulterNo(String consulterNo) {
        this.consulterNo = consulterNo;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}