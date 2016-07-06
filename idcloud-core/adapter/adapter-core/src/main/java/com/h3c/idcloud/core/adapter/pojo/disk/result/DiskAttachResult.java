package com.h3c.idcloud.core.adapter.pojo.disk.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

public class DiskAttachResult extends BaseResult {

    private String serverId;
    private String volumeId;
    private String device;

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(String volumeId) {
        this.volumeId = volumeId;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

}
