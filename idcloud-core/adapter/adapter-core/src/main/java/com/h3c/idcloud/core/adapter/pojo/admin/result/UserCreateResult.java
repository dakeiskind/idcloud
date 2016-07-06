package com.h3c.idcloud.core.adapter.pojo.admin.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

public class UserCreateResult extends BaseResult {

    private String name;
    private String password;
    private String tenantUuid;
    private String userUuid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTenantUuid() {
        return tenantUuid;
    }

    public void setTenantUuid(String tenantUuid) {
        this.tenantUuid = tenantUuid;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

}
