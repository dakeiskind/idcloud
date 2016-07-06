package com.h3c.idcloud.core.adapter.facade.provision.model.admin;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

public class User extends CommonAdapterResult {

    private String name;
    private String tenantid;
    private String uuid;
    private String enabled;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getTenantid() {
        return tenantid;
    }

    public void setTenantid(String tenantid) {
        this.tenantid = tenantid;
    }

}
