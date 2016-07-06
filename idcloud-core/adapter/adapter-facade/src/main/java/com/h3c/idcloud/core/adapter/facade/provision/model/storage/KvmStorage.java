package com.h3c.idcloud.core.adapter.facade.provision.model.storage;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class KvmStorage extends CommonAdapterResult {
    private String capacity;
    private String storageName;
    private String freeCapacity;
    private String usage;
    private String type;
    private String id;
    private List<KvmVolume> volumes;

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getStorageName() {
        return storageName;
    }

    @JsonProperty("storage_name")
    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public String getFreeCapacity() {
        return freeCapacity;
    }

    @JsonProperty("free_capacity")
    public void setFreeCapacity(String freeCapacity) {
        this.freeCapacity = freeCapacity;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<KvmVolume> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<KvmVolume> volumes) {
        this.volumes = volumes;
    }

}
