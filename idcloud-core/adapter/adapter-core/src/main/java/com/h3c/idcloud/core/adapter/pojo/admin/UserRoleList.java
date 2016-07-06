package com.h3c.idcloud.core.adapter.pojo.admin;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class UserRoleList extends Base {

    private String userUuid;
    private String tenantUuid;

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getTenantUuid() {
        return tenantUuid;
    }

    public void setTenantUuid(String tenantUuid) {
        this.tenantUuid = tenantUuid;
    }

}
