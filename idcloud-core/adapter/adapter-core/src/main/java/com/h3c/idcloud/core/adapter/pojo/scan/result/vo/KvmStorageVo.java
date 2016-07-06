package com.h3c.idcloud.core.adapter.pojo.scan.result.vo;

import java.util.List;

public class KvmStorageVo {
    private String capacity;
    private String storageName;
    private String freeCapacity;
    private String usage;
    private String type;
    private String id;
    private List<KvmVolumeVo> volumes;

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    public String getFreeCapacity() {
        return freeCapacity;
    }

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

    public List<KvmVolumeVo> getVolumes() {
        return volumes;
    }

    public void setVolumes(List<KvmVolumeVo> volumes) {
        this.volumes = volumes;
    }

}
