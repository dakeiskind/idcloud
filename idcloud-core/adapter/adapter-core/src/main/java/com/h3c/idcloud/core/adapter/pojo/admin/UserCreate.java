package com.h3c.idcloud.core.adapter.pojo.admin;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class UserCreate extends Base {

    private String name;
    private String password;
    private String tenantUuid;

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


}
