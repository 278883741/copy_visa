package com.aoji.model;

import javax.persistence.*;

@Table(name = "commission_balance")
public class CommissionBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 差额原因
     */
    private String balance;

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
     * 获取差额原因
     *
     * @return balance - 差额原因
     */
    public String getBalance() {
        return balance;
    }

    /**
     * 设置差额原因
     *
     * @param balance 差额原因
     */
    public void setBalance(String balance) {
        this.balance = balance;
    }
}