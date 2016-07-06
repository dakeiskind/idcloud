package com.h3c.idcloud.core.adapter.facade.provision.model.network;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.FloatingIpCreateResult;
import com.h3c.idcloud.core.adapter.pojo.network.result.FloatingIpDeleteResult;

public class FloatingIp extends CommonAdapterResult {

    private FloatingIpCreateResult floatingIpCreateResult = new FloatingIpCreateResult();

    private FloatingIpDeleteResult floatingIpDeleteResult = new FloatingIpDeleteResult();

    public FloatingIpCreateResult getFloatingIpCreateResult() {
        return floatingIpCreateResult;
    }

    public void setFloatingIpCreateResult(
            FloatingIpCreateResult floatingIpCreateResult) {
        this.floatingIpCreateResult = floatingIpCreateResult;
    }

    public FloatingIpDeleteResult getFloatingIpDeleteResult() {
        return floatingIpDeleteResult;
    }

    public void setFloatingIpDeleteResult(
            FloatingIpDeleteResult floatingIpDeleteResult) {
        this.floatingIpDeleteResult = floatingIpDeleteResult;
    }


}
