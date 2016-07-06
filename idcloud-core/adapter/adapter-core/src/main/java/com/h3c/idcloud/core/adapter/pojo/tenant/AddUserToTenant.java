package com.h3c.idcloud.core.adapter.pojo.tenant;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class AddUserToTenant extends Base {
    private String userId;

    private String roleId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
