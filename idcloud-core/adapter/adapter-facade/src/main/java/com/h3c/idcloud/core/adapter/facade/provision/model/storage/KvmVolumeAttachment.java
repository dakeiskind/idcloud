package com.h3c.idcloud.core.adapter.facade.provision.model.storage;

import org.codehaus.jackson.annotate.JsonProperty;

public class KvmVolumeAttachment {
    private String device;
    private String serverId;
    private String volumeId;
    private String id;

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getServerId() {
        return serverId;
    }

    @JsonProperty("server_id")
    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getVolumeId() {
        return volumeId;
    }

    @JsonProperty("volume_id")
    public void setVolumeId(String volumeId) {
        this.volumeId = volumeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
