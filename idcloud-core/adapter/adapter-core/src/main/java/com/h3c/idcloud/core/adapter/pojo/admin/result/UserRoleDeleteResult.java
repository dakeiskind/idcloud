package com.h3c.idcloud.core.adapter.pojo.admin.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

public class UserRoleDeleteResult extends BaseResult {

    private String userUuid;
    private String tenantUuid;
    private String roleUuid;

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

    public String getRoleUuid() {
        return roleUuid;
    }

    public void setRoleUuid(String roleUuid) {
        this.roleUuid = roleUuid;
    }

}
