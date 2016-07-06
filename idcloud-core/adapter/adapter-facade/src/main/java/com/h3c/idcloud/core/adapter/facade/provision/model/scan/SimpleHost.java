package com.h3c.idcloud.core.adapter.facade.provision.model.scan;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

public class SimpleHost extends CommonAdapterResult {

    private String hostName;
    private String uuid;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}