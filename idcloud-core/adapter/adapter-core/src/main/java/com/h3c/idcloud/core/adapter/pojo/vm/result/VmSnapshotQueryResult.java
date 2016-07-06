package com.h3c.idcloud.core.adapter.pojo.vm.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

import java.util.ArrayList;
import java.util.List;

public class VmSnapshotQueryResult extends BaseResult {

    private List<SnapshotInfo> snapshotInfos = new ArrayList<SnapshotInfo>();

    public List<SnapshotInfo> getSnapshotInfos() {
        return snapshotInfos;
    }

    public void setSnapshotInfos(List<SnapshotInfo> snapshotInfos) {
        this.snapshotInfos = snapshotInfos;
    }

}
