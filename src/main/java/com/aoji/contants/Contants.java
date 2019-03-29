package com.aoji.contants;

import java.util.Arrays;
import java.util.List;

/**
 * @author yangsaixing
 * @description 通用常量类
 * @date Created in 下午2:43 2017/12/12
 */
public class Contants {
    public static final int COURSETYPE_LANGUAGE = 1;
    public static final int COURSETYPE_READY = 2;
    public static final int COURSETYPE_MAJOR = 3;

    public static final String DATE_SUFFIX = " 23:59:59";

    /**
     * 系统平台
     */
    public static final String PLAT_FROM_WINDOW = "WIN";
    public static final String PLAT_FROM_MAC = "MAC";

    /**
     * 发送MQ消息最大超时时间, 用来计算下次重试时间 （单位：分钟）
     */
    public static final int SEND_MESSAGE_MAX_TIMEOUT = 3;

    /**
     * 消费MQ消息最大超时时间, 用来计算下次重试时间 （单位：分钟）
     */
    public static final int CONSUME_MESSAGE_MAX_TIMEOUT = 3;

    /**
     * 学生导出分页大小
     */
    public static final int STUDENT_EXPORT_PAGESIZE = 5000;
    /**
     * 导出数据最大数量
     */
    public static final int EXPORT_MAX = 60000;
    /**
     * 文签系统初始密码 - 统一登录注册
     */
    public static final String VISA_INIT_PASSWORD = "visa_123.aoji";

    /**
     * 同业
     */
    public static final String tyPattern="TY";

    /**
     * 代理
     */
    public static final String dlPattern="DL";

    /**
     * 生成学号格式
     */
    public static final String studentNoPattern="%0" + 5 + "d";

    /**
     * 时间格式
     */
    public static final String timePattern="yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式
     */
    public static final String datePattern="yyyy-MM-dd";

    //寄件类型
    /**
     * 首次寄出
     */
    public static final int SUPPLEMENTTYPE_FIRT=1;
    /**
     * 补件
     */
    public static final int SUPPLEMENTTYPE_ADD=2;
    /**
     * 邮寄最终成绩单/疫苗表
     */
    public static final int SUPPLEMENTTYPE_SCORE=3;
    /**
     * 邮寄录取包裹
     */
    public static final int SUPPLEMENTTYPE_ENROLL=4;

    /**
     * 邮寄
     */
    public static final int APPLYTYPE_PACKAGE=1;
    /**
     * 网申
     */
    public static final int APPLYTYPE_ONLINE=2;
    /**
     * 邮件
     */
    public static final int APPLYTYPE_EMAIL=3;

    //offer结果

    /**
     * 录取
     */
    public static final int OFFERRESULT_ACCEPT=1;
    /**
     * 拒绝
     */
    public static final int OFFERRESULT_REJECT=2;
    /**
     * 满位
     */
    public static final int OFFERRESULT_FULL=3;
    /**
     * waiting list
     */
    public static final int SUPPLEMENTTYPE_WAITINGLIST=4;
    /**
     * 预录取
     */
    public static final int SUPPLEMENTTYPE_PREACCEPT=5;
    /**
     * 成功标识
     */
    public static final String SUCCESS_FLAG = "OK";
    /**
     * 失败标识
     */
    public static final String FAIL_FLAG = "ERROR";

    /**
     * 是
     */
    public static final String YES_FLAG = "YES";

    /**
     * 否
     */
    public static final String NO_FLAG = "NO";

    //coe申请

    /**
     * 申请已提交
     */
    public static final int APPLYSTATUS_SUBMIT=1;
    /**
     * 审核中
     */
    public static final int APPLYSTATUS_AUDITING=2;
    /**
     * 审核已通过
     */
    public static final int APPLYSTATUS_ACCEPT=3;
    /**
     * 审核不通过
     */
    public static final int APPLYSTATUS_REJECT=4;

    /**
     * 预警消息
     */
    public static final int WARNING_MESSAGE=1;
    /**
     * 审批消息
     */
    public static final int APPROVAL_MESSAGE=2;
    /**
     * 工作消息
     */
    public static final int WORK_MESSAGE=3;

    /**
     * 修改
     */
    public static final String UPDATE_TYPE = "1";

    /**
     * 删除
     */
    public static final String DELETE_TYPE = "2";

    /**
     * 服务类型： 文书
     */
    public static final String COFFICE_COPY = "OFFICE_COPY";
    /**
     * 服务类型： 申请院校
     */
    public static final String APPLICATION = "APPLICATION";
    /**
     * 服务类型： 签证
     */
    public static final String VISA = "VISA";
    /**
     * 服务类型： 后续
     */
    public static final String FOLLOW = "FOLLOW";

    /**
     * 两个转案接收人的国家
     */
    public static final List<Integer> TWO_RECEIVE = Arrays.asList(5, 3);

    /**
     * 角色： 文签经理
     */
    public static final String ROLE_VISA_MANAGER = "文案经理";

    /**
     * 角色：  文签顾问
     */
    public static final String ROLE_VISA_CONSULTANT = "文案顾问";

    /**
     * 角色：  文签总监
     */
    public static final String ROLE_VISA_DIRECTOR = "文签总监";

    /**
     * 角色：  外联经理
     */
    public static final String ROLE_OUTREACH_MANAGER = "外联经理";

    /**
     * 角色：  外联顾问
     */
    public static final String ROLE_OUTREACH_CONSULTANT = "外联顾问";

    /**
     * 转案类型：文签
     */
    public static final String TRANSFER_TYPE_VISA = "copy_visa";

    /**
     * 转案类型：外联
     */
    public static final String TRANSFER_TYPE_OUT = "outreach";

    /**
     * 院校申请操作记录类型：文签系统
     */
    public static final int PLAN_COLLEGE_RECORD_TYPE_VISA = 1;

    /**
     * 院校申请操作记录类型：签约系统
     */
    public static final int PLAN_COLLEGE_RECORD_TYPE_SIGN = 5;

    /**
     * 学生状态：正常
     */
    public static final int STUDENT_STATUS_NORMAL = 1;

    /**
     * 学生状态：停办
     */
    public static final int STUDENT_STATUS_DELAY = 2;

    /**
     * 美国对应的内网id
     */
    public static final int AMERICA_BUSS_ID = 18;

    /**
     * 美高
     */
    public static final int AMERICA_TOP_BUSS_ID = 100011;

    /**
     * 美普
     */
    public static final int AMERICA_GENERAL_BUSS_ID = 100010;

    /**
     * 预科国家id
     */
    public static final List<Integer> preCounrtyList = Arrays.asList(59,28,29,49,61,54);

    /**
     * 转案权限-分配
     */
    public static final String TRAN_ASSIGN_PERMISSION = "TRAN:ASSIGN";

    /**
     * 转案权限-转组
     */
    public static final String TRAN_CHANGE_GROUP_PERMISSION = "TRAN:CHANGE:GROUP";

    /**
     * 转案权限-转国家
     */
    public static final String TRAN_CHANGE_COUNTRY_PERMISSION = "TRAN:CHANGE:COUNTRY";

    /**
     *  澳新组
     */
    public static final String CHANNEL_GROUP = "CHANNEL_GROUP";
    /**
     *  澳新组
     */
    public static final String AYUSTRALIA_NEW = "AYUSTRALIA_NEW";
    /**
     * 英国组
     */
    public static final String ENGLAND = "ENGLAND";
    /**
     * 加拿大组
     */
    public static final String CANADA = "CANADA";
    /**
     * 美高组
     */
    public static final String AMERICA_H = "AMERICA_H";
    /**
     * 美普组
     */
    public static final String AMERICA_C = "AMERICA_C";
    /**
     * 亚洲组
     */
    public static final String ASIA = "ASIA";
    /**
     * 欧洲组
     */
    public static final String EUROPE = "EUROPE";
    /**
     * 规划组
     */
    public static final String PLAN = "PLAN";

    /**
     * 排序名称： 创建时间
     */
    public static final String CREATE_TIME = "create_time";

    /**
     * 国家组对应信息
     */
    public static final String NATIONGROUP_AUSTRALIA = "101";
    public static final String NATIONGROUP_ENGLAND = "102";
    public static final String NATIONGROUP_CANADA = "103";
    public static final String NATIONGROUP_AMERICA = "104";
    public static final String NATIONGROUP_NEWZEALAND = "105";
    public static final String NATIONGROUP_ASIA = "106";
    public static final String NATIONGROUP_ENGLANDHK = "107";



    public static final String STATUSCODE_SUCCESS = "success";
    public static final String STATUSCODE_ERROR = "error";
    public static final String STATUSCODE_CODERROR = "coderror";


    //回访类型
    /**
     * 收材料
     */
    public static final int VISITTYPE_COLLECT_MATERIAL=1;
    /**
     * 要求补件
     */
    public static final int VISITTYPE_NEED_MATERIAL=2;
    /**
     * 确认补件
     */
    public static final int VISITTYPE_CONFIRM_MATERIAL=3;
    /**
     * 跟催
     */
    public static final int VISITTYPE_REMINDING=4;
    /**
     * 其他
     */
    public static final int VISITTYPE_OTHER=5;

    /**
     * 佣金统计查询条件缓存再Session中的Key
     */
    public static final String COMMISSION_REPORT_CONDITION_SESSION_KEY = "COMMISSION_REPORT_CONDITION_SESSION_KEY";

    /**
     * 渠道佣金到账列表数据缓存的key
     */
    public static final String TO_ACCOUNT_LIST_SESSION_KEY = "TO_ACCOUNT_LIST_SESSION_KEY";

    /**
     * 渠道佣金到账列表查询条件缓存的key
     */
    public static final String TO_ACCOUNT_LIST_CONDITION_SESSION_KEY = "TO_ACCOUNT_LIST_CONDITION_SESSION_KEY";

    /**
     * 代理学生列表查询条件缓存的key
     */
    public static final String AGENT_STUDENT_LIST_CONDITION_SESSION_KEY = "AGENT_STUDENT_LIST_CONDITION_SESSION_KEY";

    /**
     * 渠道返佣-代理学生查看 权限
     */
    public static final String AGENT_STUDENT_LIST_PERMISSION = "渠道返佣-代理学生查看";

    /**
     * 渠道返佣状态-已确认 权限
     */
    public static final String TO_ACCOUNT_LIST_CONFIRMED_PERMISSION = "渠道返佣状态-已确认";




    /**
     * 查询财务返佣金列表查询条件缓存的key
     */
    public static final String FINANCE_RETURN_COMMISSION_LIST_KEY = "FINANCE_RETURN_COMMISSION_LIST_KEY";

    /**
     * 定校方案学生确认提示
     */
    public static final String PLAN_COLLEGE_STUDENT_CONFIRM_TIP = "PLAN_COLLEGE_STUDENT_CONFIRM_TIP";

    /**
     * 私密操作类型：1-查看学生手机号
     */
    public static final int PRIVATE_OPERATION_TYPE_CHECK_PHONE = 1;

    /**
     * 私密操作类型：2-查看咨询顾问联系方式
     */
    public static final int PRIVATE_OPERATION_TYPE_CHECK_SALES = 2;


    /**
     * 同业已审核学生列表重新分配咨询顾问时，记录学生及顾问信息的key
     */
    public static final String CHANNEL_ASSIGN_SALECONSULTANT_LIST = "CHANNEL_ASSIGN_SALECONSULTANT_LIST";

}

