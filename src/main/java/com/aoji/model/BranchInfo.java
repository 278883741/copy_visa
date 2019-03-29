package com.aoji.model;

import javax.persistence.*;

@Table(name = "branch_info")
public class BranchInfo {
    /**
     * 自增编号
     */
    @Id
    @Column(name = "seriaNo")
    private Integer seriano;

    /**
     * 分支编号
     */
    @Column(name = "branch_id")
    private Integer branchId;

    /**
     * 分支名称
     */
    @Column(name = "branch_name")
    private String branchName;

    /**
     * 所属组
     */
    @Column(name = "branch_group")
    private String branchGroup;

    /**
     * 是否作废
     */
    @Column(name = "isDel")
    private Integer isdel;

    /**
     * 获取自增编号
     *
     * @return seriaNo - 自增编号
     */
    public Integer getSeriano() {
        return seriano;
    }

    /**
     * 设置自增编号
     *
     * @param seriano 自增编号
     */
    public void setSeriano(Integer seriano) {
        this.seriano = seriano;
    }

    /**
     * 获取分支编号
     *
     * @return branch_id - 分支编号
     */
    public Integer getBranchId() {
        return branchId;
    }

    /**
     * 设置分支编号
     *
     * @param branchId 分支编号
     */
    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    /**
     * 获取分支名称
     *
     * @return branch_name - 分支名称
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * 设置分支名称
     *
     * @param branchName 分支名称
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    /**
     * 获取所属组
     *
     * @return branch_group - 所属组
     */
    public String getBranchGroup() {
        return branchGroup;
    }

    /**
     * 设置所属组
     *
     * @param branchGroup 所属组
     */
    public void setBranchGroup(String branchGroup) {
        this.branchGroup = branchGroup;
    }

    /**
     * 获取是否作废
     *
     * @return isDel - 是否作废
     */
    public Integer getIsdel() {
        return isdel;
    }

    /**
     * 设置是否作废
     *
     * @param isdel 是否作废
     */
    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }
}