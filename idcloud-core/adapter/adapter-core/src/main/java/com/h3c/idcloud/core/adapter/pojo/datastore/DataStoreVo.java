package com.h3c.idcloud.core.adapter.pojo.datastore;

import java.util.List;

public class DataStoreVo {
    private String dsName;
    private String uuid;
    private List<Volume> volumes;

    public String getDsName() {
        return dsName;
    }

    public void setDsName(String dsName) {
        this.dsName = dsName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<Volume> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<Volume> volumes) {
        this.volumes = volumes;
    }

}
