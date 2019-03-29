package com.aoji.config.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @author daitian
 * @date 2018/9/18
 */
@Data
@ApiModel("xxx表格")
public class FinanceModel {
    @ApiModelProperty("澳际学号")
    private String studentNo;
    @ApiModelProperty("学生姓名")
    private String studentName;
    @ApiModelProperty("代理名称")
    private String agentName;
    @ApiModelProperty("币种")
    private String accountCurrency;
    @ApiModelProperty("到账金额")
    private BigDecimal accountMoney;
    @ApiModelProperty("返佣百分比")
    private Integer channelReturnRate;
    @ApiModelProperty("本次应返金额")
    private BigDecimal thisReturnMoney;
    @ApiModelProperty("汇率")
    private BigDecimal exchangeRate;
    @ApiModelProperty("应返（RMB）金额")
    private BigDecimal returnMoneyCny;
    @ApiModelProperty("付款日期")
    private String paymentDate;
}
