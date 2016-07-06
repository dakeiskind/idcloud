package com.h3c.idcloud.core.adapter.pojo.vm;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class VmSnapshotCreate extends Base {

    private String vmName;
    private String snapshotName;
    private String snapshotDesp;
    private String serverId;

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
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

    public String getSnapshotDesp() {
        return snapshotDesp;
    }

    public void setSnapshotDesp(String snapshotDesp) {
        this.snapshotDesp = snapshotDesp;
    }

}
