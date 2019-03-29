package com.aoji.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yangsaixing
 * @description
 * @date Created in 下午3:18 2018/11/28
 */
@Data
@ApiModel(value="导出测试表格")
public class ExportParamModel {

     /**
      * costName：对应sql文件中的列名
      * value:对应导出表格的中文列名
      * dataType:对应导出表格中的数据类型
      */
       @ApiModelProperty(value="费用类别",dataType = "String")
        private String costName;

        @ApiModelProperty(value="付款日期",dataType = "Date")
        private Date paymentDate;

        @ApiModelProperty(value = "应返（RMB）金额",dataType = "Double")
        private BigDecimal returnMoneyCny;

}
