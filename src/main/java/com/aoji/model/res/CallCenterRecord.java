package com.aoji.model.res;

public class CallCenterRecord {

    /**
     * 回访内容
     */
    private String remark;

    /**
     * 回访时间
     */
    private String createtime;

    /**
     * 回访人姓名
     */
    private String userName;

    /**
     * 回访人编号
     */
    private String userid;

    /**
     * 通话时长(如果为0 表示未接通)
     */
    private int answerseconds;

    /**
     * 录音地址（金山云地址）
     */
    private String serverfilepath;

    /**
     *     电话号码
     */
    private String showNumber;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getAnswerseconds() {
        return answerseconds;
    }

    public void setAnswerseconds(int answerseconds) {
        this.answerseconds = answerseconds;
    }

    public String getServerfilepath() {
        return serverfilepath;
    }

    public void setServerfilepath(String serverfilepath) {
        this.serverfilepath = serverfilepath;
    }

    public String getShowNumber() {
        return showNumber;
    }

    public void setShowNumber(String showNumber) {
        this.showNumber = showNumber;
    }
}
