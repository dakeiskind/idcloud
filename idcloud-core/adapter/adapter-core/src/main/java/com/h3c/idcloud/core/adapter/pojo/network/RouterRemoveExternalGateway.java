package com.h3c.idcloud.core.adapter.pojo.network;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

/**
 * Created by qct on 2016/4/19.
 *
 * @author qct
 */
public class RouterRemoveExternalGateway extends Base {
    private String routerId;

    public String getRouterId() {
        return routerId;
    }

    public void setRouterId(String routerId) {
        this.routerId = routerId;
    }
}
