package com.aoji.model;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import javax.persistence.*;

@Table(name = "visit_info")
public class VisitInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 内网学号
     */
    @Column(name = "student_no")
    private String studentNo;

    /**
     * 回访日期
     */
    @Column(name = "visit_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date visitDate;
    @Transient
    private String visitDateString;
    public void setVisitDateString(String visitDateString) {
        this.visitDateString = visitDateString;
    }
    public String getVisitDateString() {
        return visitDateString;
    }

    /**
     * 回访人
     */
    @Column(name = "visit_no")
    private String visitNo;
    @Column(name = "visit_name")
    private String visitName;
    public void setVisitName(String visitName) {
        this.visitName = visitName;
    }
    public String getVisitName() {
        return visitName;
    }

    /**
     * 回访内容
     */
    private String content;

    /**
     * 创建日期，即学校要求补件日期
     */
    @Column(name = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @Transient
    private String createTimeString;
    public void setCreateTimeString(String createTimeString) {
        this.createTimeString = createTimeString;
    }
    public String getCreateTimeString() {
        return createTimeString;
    }

    /**
     * 删除时间/失效时间
     */
    @Column(name = "delete_time")
    private Date deleteTime;

    /**
     * 删除状态0为未删除/可用，1为已删除/不可用
     */
    @Column(name = "delete_status")
    private Boolean deleteStatus;

    /**
     * 操作人
     */
    @Column(name = "operator_no")
    private String operatorNo;
    @Column(name = "operator_name")
    private String operatorName;
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * 发送方：1-外联 2-学校
     */
    @Column(name = "sender_type")
    private Integer senderType;
    @Transient
    private String senderType_string;
    public void setSenderType_string(String senderType_string) {
        this.senderType_string = senderType_string;
    }
    public String getSenderType_string() {
        return senderType_string;
    }

    /**
     * 回访途径：1-传真 2-邮件 3-电话
     */
    @Column(name = "visit_way")
    private Integer visitWay;
    @Transient
    private String visitWay_string;
    public void setVisitWay_string(String visitWay_string) {
        this.visitWay_string = visitWay_string;
    }
    public String getVisitWay_string() {
        return visitWay_string;
    }

    /**
     * 回访场景：1-文案回访 2-外联回访院校申请 3-后续回访
     */
    @Column(name = "visit_case")
    private Integer visitCase;
    @Transient
    private String visitCase_string;
    public void setVisitCase_string(String visitCase_string) {
        this.visitCase_string = visitCase_string;
    }
    public String getVisitCase_string() {
        return visitCase_string;
    }

    /**
     * 回访关联的业务id。若回访记录是对学生文案进行的回访，那么该字段为空，可直接取学生学号，若是针对院校申请进行的回访，该字段为院校申请表的id。
     */
    @Column(name = "business_id")
    private Integer businessId;

    /**
     * 补件截止日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deadline;

    /**
     * 回访类型：1-收材料 2-补件 3-确认补件 4-跟催 5-其他

     */
    @Column(name = "visit_type")
    private Integer visitType;
    @Transient
    private String visitType_string;
    public void setVisitType_string(String visitType_string) {
        this.visitType_string = visitType_string;
    }
    public String getVisitType_string() {
        return visitType_string;
    }

    /**
     * 附件
     */
    private String attachment;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "next_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date nextDate;

    /**
     * 是否公开
     */
    @Column(name = "is_public")
    private Integer isPublic;
    public void setIsPublic(Integer isPublic) {
        this.isPublic = isPublic;
    }
    public Integer getIsPublic() {
        return isPublic;
    }

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
     * 获取内网学号
     *
     * @return student_no - 内网学号
     */
    public String getStudentNo() {
        return studentNo;
    }

    /**
     * 设置内网学号
     *
     * @param studentNo 内网学号
     */
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    /**
     * 获取回访日期
     *
     * @return visit_date - 回访日期
     */
    public Date getVisitDate() {
        return visitDate;
    }

    /**
     * 设置回访日期
     *
     * @param visitDate 回访日期
     */
    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    /**
     * 获取回访人
     *
     * @return visit_no - 回访人
     */
    public String getVisitNo() {
        return visitNo;
    }

    /**
     * 设置回访人
     *
     * @param visitNo 回访人
     */
    public void setVisitNo(String visitNo) {
        this.visitNo = visitNo;
    }

    /**
     * 获取回访内容
     *
     * @return content - 回访内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置回访内容
     *
     * @param content 回访内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取创建日期，即学校要求补件日期
     *
     * @return create_time - 创建日期，即学校要求补件日期
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建日期，即学校要求补件日期
     *
     * @param createTime 创建日期，即学校要求补件日期
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取删除时间/失效时间
     *
     * @return delete_time - 删除时间/失效时间
     */
    public Date getDeleteTime() {
        return deleteTime;
    }

    /**
     * 设置删除时间/失效时间
     *
     * @param deleteTime 删除时间/失效时间
     */
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    /**
     * 获取删除状态0为未删除/可用，1为已删除/不可用
     *
     * @return delete_status - 删除状态0为未删除/可用，1为已删除/不可用
     */
    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    /**
     * 设置删除状态0为未删除/可用，1为已删除/不可用
     *
     * @param deleteStatus 删除状态0为未删除/可用，1为已删除/不可用
     */
    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    /**
     * 获取操作人
     *
     * @return operator_no - 操作人
     */
    public String getOperatorNo() {
        return operatorNo;
    }

    /**
     * 设置操作人
     *
     * @param operatorNo 操作人
     */
    public void setOperatorNo(String operatorNo) {
        this.operatorNo = operatorNo;
    }

    /**
     * 获取发送方：1-外联 2-学校
     *
     * @return sender_type - 发送方：1-外联 2-学校
     */
    public Integer getSenderType() {
        return senderType;
    }

    /**
     * 设置发送方：1-外联 2-学校
     *
     * @param senderType 发送方：1-外联 2-学校
     */
    public void setSenderType(Integer senderType) {
        this.senderType = senderType;
    }

    /**
     * 获取回访途径：1-传真 2-邮件 3-电话
     *
     * @return visit_way - 回访途径：1-传真 2-邮件 3-电话
     */
    public Integer getVisitWay() {
        return visitWay;
    }

    /**
     * 设置回访途径：1-传真 2-邮件 3-电话
     *
     * @param visitWay 回访途径：1-传真 2-邮件 3-电话
     */
    public void setVisitWay(Integer visitWay) {
        this.visitWay = visitWay;
    }

    /**
     * 获取回访场景：1-文案回访 2-外联回访院校申请 3-后续回访
     *
     * @return visit_case - 回访场景：1-文案回访 2-外联回访院校申请 3-后续回访
     */
    public Integer getVisitCase() {
        return visitCase;
    }

    /**
     * 设置回访场景：1-文案回访 2-外联回访院校申请 3-后续回访
     *
     * @param visitCase 回访场景：1-文案回访 2-外联回访院校申请 3-后续回访
     */
    public void setVisitCase(Integer visitCase) {
        this.visitCase = visitCase;
    }

    /**
     * 获取回访关联的业务id。若回访记录是对学生文案进行的回访，那么该字段为空，可直接取学生学号，若是针对院校申请进行的回访，该字段为院校申请表的id。
     *
     * @return business_id - 回访关联的业务id。若回访记录是对学生文案进行的回访，那么该字段为空，可直接取学生学号，若是针对院校申请进行的回访，该字段为院校申请表的id。
     */
    public Integer getBusinessId() {
        return businessId;
    }

    /**
     * 设置回访关联的业务id。若回访记录是对学生文案进行的回访，那么该字段为空，可直接取学生学号，若是针对院校申请进行的回访，该字段为院校申请表的id。
     *
     * @param businessId 回访关联的业务id。若回访记录是对学生文案进行的回访，那么该字段为空，可直接取学生学号，若是针对院校申请进行的回访，该字段为院校申请表的id。
     */
    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    /**
     * 获取补件截止日
     *
     * @return deadline - 补件截止日
     */
    public Date getDeadline() {
        return deadline;
    }

    /**
     * 设置补件截止日
     *
     * @param deadline 补件截止日
     */
    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    /**
     * 获取回访类型：1-收材料 2-补件 3-确认补件 4-跟催 5-其他

     *
     * @return visit_type - 回访类型：1-收材料 2-补件 3-确认补件 4-跟催 5-其他

     */
    public Integer getVisitType() {
        return visitType;
    }

    /**
     * 设置回访类型：1-收材料 2-补件 3-确认补件 4-跟催 5-其他

     *
     * @param visitType 回访类型：1-收材料 2-补件 3-确认补件 4-跟催 5-其他

     */
    public void setVisitType(Integer visitType) {
        this.visitType = visitType;
    }

    /**
     * 获取附件
     *
     * @return attachment - 附件
     */
    public String getAttachment() {
        return attachment;
    }

    /**
     * 设置附件
     *
     * @param attachment 附件
     */
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return next_date
     */
    public Date getNextDate() {
        return nextDate;
    }

    /**
     * @param nextDate
     */
    public void setNextDate(Date nextDate) {
        this.nextDate = nextDate;
    }
}