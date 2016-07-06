package com.h3c.idcloud.core.adapter.pojo.vm;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class VmSnapshotRevert extends Base {

    private String vmName;
    private String snapshotName;
    private String hostName;
    private boolean suppressPowerOn;
    private String id;

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

    public String getSnapshotName() {
        return snapshotName;
    }

    public void setSnapshotName(String snapshotName) {
        this.snapshotName = snapshotName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public boolean isSuppressPowerOn() {
        return suppressPowerOn;
    }

    public void setSuppressPowerOn(boolean suppressPowerOn) {
        this.suppressPowerOn = suppressPowerOn;
    }

}
