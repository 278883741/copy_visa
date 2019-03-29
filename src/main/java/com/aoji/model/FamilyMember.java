package com.aoji.model;
/**
 * author: chenhaibo
 * description: 家庭成员信息
 * date: 2018/1/27
 */
public class FamilyMember {
    /**
     * 成员姓名
     */
    private String memberName;
    /**
     * 关系
     */
    private int relation;
    /**
     * 所在地
     */
    private String position;
    /**
     * 联系方式
     */
    private String memberTel;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMemberTel() {
        return memberTel;
    }

    public void setMemberTel(String memberTel) {
        this.memberTel = memberTel;
    }
}
