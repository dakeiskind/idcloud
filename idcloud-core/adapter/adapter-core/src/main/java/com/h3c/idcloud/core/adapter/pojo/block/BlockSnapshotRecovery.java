package com.h3c.idcloud.core.adapter.pojo.block;


import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class BlockSnapshotRecovery extends Base {

    private String volumeId;

    private String snapshotId;

    public String getVolumeId() {
        return volumeId;
    }

    public void setVolumeId(String volumeId) {
        this.volumeId = volumeId;
    }

    public String getSnapshotId() {
        return snapshotId;
    }

    public void setSnapshotId(String snapshotId) {
        this.snapshotId = snapshotId;
    }


}
