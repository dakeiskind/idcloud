package com.h3c.idcloud.core.adapter.pojo.vm.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

public class VmSnapshotRevertResult extends BaseResult {

    private String vmName;
    private String snapshotName;
    private String hostName;
    private boolean suppressPowerOn;

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
