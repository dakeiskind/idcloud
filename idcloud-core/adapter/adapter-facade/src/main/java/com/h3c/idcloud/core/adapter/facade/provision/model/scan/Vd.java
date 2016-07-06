package com.h3c.idcloud.core.adapter.facade.provision.model.scan;

import org.codehaus.jackson.annotate.JsonProperty;

public class Vd {

    private String vdName;
    private String allocateDiskSize;
    private String storagePurpose;
    private String deviceName;
    private String diskMode;
    private String uuid;
    private String path;
    private String dataStoreName;
    private String scsiUnit;

    public String getScsiUnit() {
        return scsiUnit;
    }

    public void setScsiUnit(String scsiUnit) {
        this.scsiUnit = scsiUnit;
    }

    public String getVdName() {
        return vdName;
    }

    public void setVdName(String vdName) {
        this.vdName = vdName;
    }

    @JsonProperty("name")
    public void setKvmVdName(String vdName) {
        this.vdName = vdName;
    }

    public String getAllocateDiskSize() {
        return allocateDiskSize;
    }

    public void setAllocateDiskSize(String allocateDiskSize) {
        this.allocateDiskSize = allocateDiskSize;
    }

    @JsonProperty("size")
    public void setKvmAllocateDiskSize(String allocateDiskSize) {
        this.allocateDiskSize = allocateDiskSize;
    }

    public String getStoragePurpose() {
        return storagePurpose;
    }

    public void setStoragePurpose(String storagePurpose) {
        this.storagePurpose = storagePurpose;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDiskMode() {
        return diskMode;
    }

    public void setDiskMode(String diskMode) {
        this.diskMode = diskMode;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @JsonProperty("volume_id")
    public void setKvmUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDataStoreName() {
        return dataStoreName;
    }

    public void setDataStoreName(String dataStoreName) {
        this.dataStoreName = dataStoreName;
    }

}
