package com.h3c.idcloud.core.adapter.pojo.network;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

/**
 * Created by qct on 2016/4/19.
 *
 * @author qct
 */
public class RouterAddExternalGateway extends Base {
    private String routerId;
    private ExternalGateway externalGateway;

    public String getRouterId() {
        return routerId;
    }

    public void setRouterId(String routerId) {
        this.routerId = routerId;
    }

    public ExternalGateway getExternalGateway() {
        return externalGateway;
    }

    public void setExternalGateway(ExternalGateway externalGateway) {
        this.externalGateway = externalGateway;
    }
}
