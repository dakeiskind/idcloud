package com.h3c.idcloud.core.adapter.facade.provision.model.scan;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

import org.codehaus.jackson.annotate.JsonProperty;

public class DataStore extends CommonAdapterResult {

    private String uuid;
    private String uncommitted;
    private String storageName;
    private String storageType;
    private String storageCategory;
    private String availableCapacity;
    private String totalCapacity;
    private String provisionCapacity;
    private String status;
    private String[] hostNames;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @JsonProperty("id")
    public void setKvmUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUncommitted() {
        return uncommitted;
    }

    public void setUncommitted(String uncommitted) {
        this.uncommitted = uncommitted;
    }

    public String getStorageName() {
        return storageName;
    }

    public void setStorageName(String storageName) {
        this.storageName = storageName;
    }

    @JsonProperty("name")
    public void setKvmStorageName(String storageName) {
        this.storageName = storageName;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public String getStorageCategory() {
        return storageCategory;
    }

    public void setStorageCategory(String storageCategory) {
        this.storageCategory = storageCategory;
    }

    public String getAvailableCapacity() {
        return availableCapacity;
    }

    public void setAvailableCapacity(String availableCapacity) {
        this.availableCapacity = availableCapacity;
    }

    @JsonProperty("disk_available_least")
    public void setKvmDiskAvailableCapacity(String availableCapacity) {
        this.availableCapacity = availableCapacity;
    }

    public String getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(String totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    @JsonProperty("local_gb")
    public void setKvmTotalDisk(String totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public String getProvisionCapacity() {
        return provisionCapacity;
    }

    public void setProvisionCapacity(String provisionCapacity) {
        this.provisionCapacity = provisionCapacity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String[] getHostNames() {
        return hostNames;
    }

    public void setHostNames(String[] hostNames) {
        this.hostNames = hostNames;
    }

}
