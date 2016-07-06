package com.h3c.idcloud.core.adapter.facade.provision.model.vm;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

import java.util.ArrayList;
import java.util.List;

public class SnapshotInfos extends CommonAdapterResult {

    private List<SnapshotInfo> snapshotInfos = new ArrayList<SnapshotInfo>();

    public List<SnapshotInfo> getSnapshotInfos() {
        return snapshotInfos;
    }

    public void setSnapshotInfos(List<SnapshotInfo> snapshotInfos) {
        this.snapshotInfos = snapshotInfos;
    }

}
