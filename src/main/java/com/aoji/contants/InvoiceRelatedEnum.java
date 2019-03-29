package com.aoji.contants;

public enum InvoiceRelatedEnum {

    QUERY_TYPE_ADD(1, "添加"),
    QUERY_TYPE_EDIT(2, "编辑"),
    QUERY_TYPE_LIST(3, "列表"),
    SAVE_TYPE_ADD(1, "新增保存"),
    SAVE_TYPE_EDIT(2, "批量编辑"),
    SAVE_TYPE_MODIFY(3, "单条修改");

    private Integer code;

    private String name;

    InvoiceRelatedEnum(Integer code, String name) {
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
