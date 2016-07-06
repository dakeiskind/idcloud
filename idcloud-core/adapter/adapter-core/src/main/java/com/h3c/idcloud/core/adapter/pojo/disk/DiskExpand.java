package com.h3c.idcloud.core.adapter.pojo.disk;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class DiskExpand extends Base {

    private String vmName;
    private String name;
    private String storage;
    private String volumeId;
    private String size;

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

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }


}
