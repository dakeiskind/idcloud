package com.h3c.idcloud.core.adapter.pojo.tenant.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.tenant.Tenant;

import java.util.List;

public class TenantListGetResult extends BaseResult {

    private List<Tenant> tenants;

    public List<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(List<Tenant> tenants) {
        this.tenants = tenants;
    }

}
