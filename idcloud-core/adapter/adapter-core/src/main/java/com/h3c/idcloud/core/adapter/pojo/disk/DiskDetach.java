package com.h3c.idcloud.core.adapter.pojo.disk;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class DiskDetach extends Base {

    private String serverId;
    private String volumeId;
    private String size;
    private String dataStore;


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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDataStore() {
        return dataStore;
    }

    public void setDataStore(String dataStore) {
        this.dataStore = dataStore;
    }

}
