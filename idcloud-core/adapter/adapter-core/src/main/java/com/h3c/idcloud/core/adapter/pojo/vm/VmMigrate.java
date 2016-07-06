package com.h3c.idcloud.core.adapter.pojo.vm;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class VmMigrate extends Base {

    private String vmName;
    private String vmId;
    private String targetHostName = "";
    private String targetHostId = "";
    private String targetStore = "";
    private String targetStoreId = "";
    private String migrateType;

    public String getMigrateType() {
        return migrateType;
    }

    public void setMigrateType(String migrateType) {
        this.migrateType = migrateType;
    }

    public String getTargetHostName() {
        return targetHostName;
    }

    public void setTargetHostName(String targetHostName) {
        this.targetHostName = targetHostName;
    }

    public String getTargetStore() {
        return targetStore;
    }

    public void setTargetStore(String targetStore) {
        this.targetStore = targetStore;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public String getTargetHostId() {
        return targetHostId;
    }

    public void setTargetHostId(String targetHostId) {
        this.targetHostId = targetHostId;
    }

    public String getTargetStoreId() {
        return targetStoreId;
    }

    public void setTargetStoreId(String targetStoreId) {
        this.targetStoreId = targetStoreId;
    }

}
