package com.h3c.idcloud.core.adapter.pojo.user.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

public class UserInfoGetResult extends BaseResult {

    private String tenantId;

    private String id;

    private String name;

    private boolean enable;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }


}
