package com.aoji.contants;

/**
 * author: chenhaibo
 * description: 转案相关
 * date: 2018/5/15
 */
public enum TransferRelatedEnum {

    TRAN_TYPE_VISA(1, "转案类型-文签"),
    TRAN_TYPE_OUTREACH(2, "转案类型-外联"),
    TRAN_SEND_TYPE_1(1, "转案分配类型-签约即转案"),
    TRAN_SEND_TYPE_2(2, "转案分配类型-分配"),
    TRAN_SEND_TYPE_3(3, "转案分配类型-转组"),
    TRAN_SEND_TYPE_4(4, "转案分配类型-转国家"),
    TRAN_OPERATOR_TYPE_1(1, "转接类型-文签"),
    TRAN_OPERATOR_TYPE_2(2, "转接类型-制作文案"),
    TRAN_OPERATOR_TYPE_3(3, "转接类型-文书"),
    TRAN_OPERATOR_TYPE_4(4, "转接类型-文书2"),
    TRAN_OPERATOR_TYPE_5(5, "转接类型-规划顾问"),
    TRAN_CONFIRM_STATUS_1(1, "确认状态-接受"),
    TRAN_CONFIRM_STATUS_2(2, "确认状态-拒绝"),
    TRAN_QUERY_TYPE_1(1, "查询类型-分配"),
    TRAN_QUERY_TYPE_2(2, "查询类型-转组"),
    TRAN_QUERY_TYPE_3(3, "查询类型-转国家"),
    TRAN_QUERY_TYPE_4(4, "查询类型-外联");

    private Integer code;

    private String name;

    TransferRelatedEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
