package com.h3c.idcloud.core.adapter.pojo.network;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class FloatingIpAttach extends Base {

    private String fixedIpId;
    private String floatingIpId;
    private String serverId;
    private String floatingIp;
    private String fixedIp;

    private String routerId;
    private String networkId;
    private String subnetId;

    public String getNetworkId() {
        return networkId;
    }

    public FloatingIpAttach setNetworkId(String networkId) {
        this.networkId = networkId;
        return this;
    }

    public String getSubnetId() {
        return subnetId;
    }

    public FloatingIpAttach setSubnetId(String subnetId) {
        this.subnetId = subnetId;
        return this;
    }

    public String getRouterId() {
        return routerId;
    }

    public FloatingIpAttach setRouterId(String routerId) {
        this.routerId = routerId;
        return this;
    }

    public String getFixedIpId() {
        return fixedIpId;
    }

    public void setFixedIpId(String fixedIpId) {
        this.fixedIpId = fixedIpId;
    }

    public String getFloatingIpId() {
        return floatingIpId;
    }

    public void setFloatingIpId(String floatingIpId) {
        this.floatingIpId = floatingIpId;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getFloatingIp() {
        return floatingIp;
    }

    public void setFloatingIp(String floatingIp) {
        this.floatingIp = floatingIp;
    }

    public String getFixedIp() {
        return fixedIp;
    }

    public void setFixedIp(String fixedIp) {
        this.fixedIp = fixedIp;
    }


}
