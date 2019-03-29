package com.aoji.model.dto;

import com.aoji.model.SysUser;

public class UserInfoDTO extends SysUser{

    private String groupName;

    private String roleName;

    private Boolean leaderStatus;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Boolean getLeaderStatus() {
        return leaderStatus;
    }

    public void setLeaderStatus(Boolean leaderStatus) {
        this.leaderStatus = leaderStatus;
    }
}
