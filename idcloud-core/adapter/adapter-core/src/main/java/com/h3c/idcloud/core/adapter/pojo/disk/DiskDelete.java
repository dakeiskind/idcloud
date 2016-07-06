package com.h3c.idcloud.core.adapter.pojo.disk;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class DiskDelete extends Base {

    private String id;
    private String vmName = "";
    private String dataStore = "";
    private String diskType;

    public String getDiskType() {
        return diskType;
    }

    public void setDiskType(String diskType) {
        this.diskType = diskType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getDataStore() {
        return dataStore;
    }

    public void setDataStore(String dataStore) {
        this.dataStore = dataStore;
    }


}
