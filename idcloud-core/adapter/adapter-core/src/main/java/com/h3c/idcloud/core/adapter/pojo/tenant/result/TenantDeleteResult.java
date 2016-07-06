package com.h3c.idcloud.core.adapter.pojo.tenant.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

public class TenantDeleteResult extends BaseResult {
    private String tenantId;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

}
