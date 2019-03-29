package com.aoji.model;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yangsaixing
 * @description
 * @date Created in 下午3:38 2017/11/16
 */
public class SystemFunction implements Serializable, Comparable<SystemFunction> {

    private String id;

    private Integer functionlevel;

    private String functionname;

    private Integer functionorder;

    private String functionurl;

    private String parentfunctionid;

    private String iconid;

    private String deskIconid;

    private Short functiontype;

    private String createBy;

    private String createName;

    private String updateBy;

    private Date updateDate;

    private Date createDate;

    private String updateName;

    private Integer delFlag;

    //以下两个属性便于展示树形结构,无实际含义
    @Transient
    private Integer childCount;
    @Transient
    private List<SystemFunction> childFunctList = new ArrayList<>();//子菜单

    public SystemFunction() {
    }

    public SystemFunction(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public SystemFunction(Integer functionlevel, Integer delFlag) {
        this.functionlevel = functionlevel;
        this.delFlag = delFlag;
    }

    public SystemFunction(String parentfunctionid, Integer delFlag) {
        this.parentfunctionid = parentfunctionid;
        this.delFlag = delFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getFunctionlevel() {
        return functionlevel;
    }

    public void setFunctionlevel(Integer functionlevel) {
        this.functionlevel = functionlevel;
    }

    public String getFunctionname() {
        return functionname;
    }

    public void setFunctionname(String functionname) {
        this.functionname = functionname;
    }

    public Integer getFunctionorder() {
        return functionorder;
    }

    public void setFunctionorder(Integer functionorder) {
        this.functionorder = functionorder;
    }

    public String getFunctionurl() {
        return functionurl;
    }

    public void setFunctionurl(String functionurl) {
        this.functionurl = functionurl;
    }

    public String getParentfunctionid() {
        return parentfunctionid;
    }

    public void setParentfunctionid(String parentfunctionid) {
        this.parentfunctionid = parentfunctionid;
    }

    public String getIconid() {
        return iconid;
    }

    public void setIconid(String iconid) {
        this.iconid = iconid;
    }

    public String getDeskIconid() {
        return deskIconid;
    }

    public void setDeskIconid(String deskIconid) {
        this.deskIconid = deskIconid;
    }

    public Short getFunctiontype() {
        return functiontype;
    }

    public void setFunctiontype(Short functiontype) {
        this.functiontype = functiontype;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getChildCount() {
        return childCount;
    }

    public void setChildCount(Integer childCount) {
        this.childCount = childCount;
    }

    public List<SystemFunction> getChildFunctList() {
        return childFunctList;
    }

    public void setChildFunctList(List<SystemFunction> childFunctList) {
        this.childFunctList = childFunctList;
    }

    @Override
    public int compareTo(SystemFunction o) {
        return o.getFunctionorder() - functionorder;
    }
}
