package com.h3c.idcloud.core.adapter.pojo.network;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

/**
 * Created by qct on 2016/4/15.
 *
 * @author qct
 */
public class RouterCreate extends Base {
    private String name;

    /**
     * ExternalGatewayInfo
     * <br>If you want to create an external gateway to connect to the public network while creating a router,
     * please fill up this field.
     */
    private ExternalGateway externalGateway;

    public ExternalGateway getExternalGateway() {
        return externalGateway;
    }

    public void setExternalGateway(ExternalGateway externalGateway) {
        this.externalGateway = externalGateway;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
