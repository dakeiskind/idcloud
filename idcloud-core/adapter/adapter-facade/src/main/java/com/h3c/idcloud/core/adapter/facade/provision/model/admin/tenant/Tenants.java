package com.h3c.idcloud.core.adapter.facade.provision.model.admin.tenant;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.tenant.result.TenantCreateResult;
import com.h3c.idcloud.core.adapter.pojo.tenant.result.TenantDeleteResult;
import com.h3c.idcloud.core.adapter.pojo.tenant.result.TenantInfoGetResult;
import com.h3c.idcloud.core.adapter.pojo.tenant.result.TenantListGetResult;

public class Tenants extends CommonAdapterResult {

    private TenantCreateResult tenantCreateResult = new TenantCreateResult();

    private TenantDeleteResult tenantDeleteResult = new TenantDeleteResult();

    private TenantInfoGetResult tenantInfoGetResult = new TenantInfoGetResult();

    private TenantListGetResult tenantListGetResult = new TenantListGetResult();

    public TenantCreateResult getTenantCreateResult() {
        return tenantCreateResult;
    }

    public void setTenantCreateResult(TenantCreateResult tenantCreateResult) {
        this.tenantCreateResult = tenantCreateResult;
    }

    public TenantDeleteResult getTenantDeleteResult() {
        return tenantDeleteResult;
    }

    public void setTenantDeleteResult(TenantDeleteResult tenantDeleteResult) {
        this.tenantDeleteResult = tenantDeleteResult;
    }

    public TenantInfoGetResult getTenantInfoGetResult() {
        return tenantInfoGetResult;
    }

    public void setTenantInfoGetResult(TenantInfoGetResult tenantInfoGetResult) {
        this.tenantInfoGetResult = tenantInfoGetResult;
    }

    public TenantListGetResult getTenantListGetResult() {
        return tenantListGetResult;
    }

    public void setTenantListGetResult(TenantListGetResult tenantListGetResult) {
        this.tenantListGetResult = tenantListGetResult;
    }


}
