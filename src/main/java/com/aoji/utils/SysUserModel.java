package com.aoji.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
@Data
@ApiModel("xxx表格")
public class SysUserModel {

    /**
     * 内网学号
     */
    @ApiModelProperty("内网学号")
    private String studentNo;

    /**
     * 学生姓名
     */
    @ApiModelProperty("学生姓名")
    private String studentName;

    /**
     * 合同条款编号
     */
    @ApiModelProperty("合同条款编号")
    private String confeeid;

    /**
     * 合同编号
     */
    @ApiModelProperty("合同编号")
    private String contractNo;

    /**
     * 合同类型
     */
    @ApiModelProperty("合同类型")
    private Integer contractType;

    /**
     * 分支机构
     */
    @ApiModelProperty("分支机构")
    private Integer branchId;

    /**
     * 分支机构名称
     */
    @ApiModelProperty("分支机构名称")
    private String branchName;

    /**
     * 销售顾问
     */
    @ApiModelProperty("销售顾问")
    private String salesConsultant;

    /**
     * 销售顾问工号
     */
    @ApiModelProperty("销售顾问工号")
    private String salesConsultantNo;

    /**
     * 创建日期
     */
    @ApiModelProperty("创建日期")
    private Date createTime;

    /**
     * 渠道状态 1-与代理直接签约 0-与公司签约  2-通过代理与澳际签约
     */
    @ApiModelProperty("渠道状态 1-与代理直接签约 0-与公司签约  2-通过代理与澳际签约")
    private Integer channelStatus;

    @ApiModelProperty("备注")
    private String remark;


    /**
     * 渠道转案状态 (1-已转接到文签； 0-未转接到文签)
     */
    @ApiModelProperty("渠道转案状态 (1-已转接到文签； 0-未转接到文签)")
    private Integer channelTransferStatus;

    /**
     * 更新时间
     *//*
    @ApiModelProperty("更新时间")
    private Date updateTime;

    *//**
     * 删除状态0为未删除/可用，1为已删除/不可用
     *//*
    @ApiModelProperty("删除状态0为未删除/可用，1为已删除/不可用")
    private Boolean deleteStatus;

    *//**
     * 学生材料
     *//*
    @ApiModelProperty("学生材料")
    private String studentMaterial;

    *//**
     * 电子邮箱账户
     *//*
    @ApiModelProperty("电子邮箱账户")
    private String emailAccount;

    *//**
     * 电子邮箱密码
     *//*
    @ApiModelProperty("电子邮箱密码")
    private String emailPassword;

    *//**
     * 文签顾问
     *//*
    @ApiModelProperty("文签顾问")
    private String copyOperator;

    *//**
     * 文签顾问工号
     *//*
    @ApiModelProperty("文签顾问工号")
    private String copyOperatorNo;

    *//**
     * 销售顾问
     *//*
    @ApiModelProperty("销售顾问")
    private String connector;

    *//**
     * 制作文案
     *//*
    @ApiModelProperty("制作文案")
    private String copyMaker;

    *//**
     * 制作文案工号
     *//*
    @ApiModelProperty("制作文案工号")
    private String copyMakerNo;

    *//**
     * 是否为美高
     *//*
    @ApiModelProperty("是否为美高")
    private Integer usaStatus;

    *//**
     * 最新回访时间
     *//*
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("最新回访时间")
    private Date lastVisitTime;

    *//**
     * 制作文案
     *//*
    @ApiModelProperty("规划顾问")
    private String planningConsultant;

    *//**
     * 制作文案工号
     *//*
    @ApiModelProperty("规划顾问工号")
    private String planningConsultantNo;*/


  }