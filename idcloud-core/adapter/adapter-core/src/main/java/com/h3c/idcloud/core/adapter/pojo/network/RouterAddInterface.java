package com.h3c.idcloud.core.adapter.pojo.network;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

/**
 * Created by qct on 2016/4/19.
 *
 * @author qct
 */
public class RouterAddInterface extends Base{
    private String routerId;
    private String subnetId;

    public String getRouterId() {
        return routerId;
    }

    public void setRouterId(String routerId) {
        this.routerId = routerId;
    }

    public String getSubnetId() {
        return subnetId;
    }

    public void setSubnetId(String subnetId) {
        this.subnetId = subnetId;
    }
}
