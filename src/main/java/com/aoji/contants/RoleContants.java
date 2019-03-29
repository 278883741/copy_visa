package com.aoji.contants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangsaixing
 * @description
 * @date Created in 下午3:47 2018/1/27
 */
public class RoleContants {

    /**
     * 外联角色
     */
    public final static String CONNECTOR="connector";

    /**
     * 同业经理
     */
    public final static String CHANNELMANAGER="同业经理";

    /**
     * 代理机构
     */
    public final static String AGENT_ORGANIZATION="机构";

    /**
     * 财务
     */
    public final static String ROLE_FINANCE="财务";


    public  final static String COPY_OPERATOR="文案顾问";

    /**
     * 同业顾问
     */
    public final static String CHANNEL_OPERATOR="同业顾问";


    /**
     * 根据角色跳转页面
     */
    public final static Map<String,String> roleMap=new HashMap<>();


    static {
        roleMap.put(CHANNELMANAGER,"/channelStudent/studentList");
        roleMap.put(CHANNEL_OPERATOR,"/channelStudent/studentList");
        roleMap.put(AGENT_ORGANIZATION,"/channelStudent/dLstudentList");
        roleMap.put(ROLE_FINANCE,"/commission/financeReturnCommission/list");
    }
}
