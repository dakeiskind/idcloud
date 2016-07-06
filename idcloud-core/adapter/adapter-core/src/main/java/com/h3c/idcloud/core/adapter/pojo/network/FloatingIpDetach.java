package com.h3c.idcloud.core.adapter.pojo.network;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class FloatingIpDetach extends Base {

    private String serverId;
    private String floatingIp;
    private String floatingIpId;

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


}
