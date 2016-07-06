package com.h3c.idcloud.core.adapter.pojo.network.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

public class FloatingIpCreateResult extends BaseResult {

    private String floatingIpAddr;

    private String floatingIpId;

    private String networkId;

    private String id;

    public String getFloatingIpAddr() {
        return floatingIpAddr;
    }

    public void setFloatingIpAddr(String floatingIpAddr) {
        this.floatingIpAddr = floatingIpAddr;
    }

    public String getFloatingIpId() {
        return floatingIpId;
    }

    public void setFloatingIpId(String floatingIpId) {
        this.floatingIpId = floatingIpId;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
