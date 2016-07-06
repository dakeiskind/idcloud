package com.h3c.idcloud.core.adapter.facade.provision.model.vm;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.disk.result.FormatDiskResult;

import java.util.List;

public class Fdisks extends CommonAdapterResult {
    private List<FormatDiskResult> disks;

    public List<FormatDiskResult> getDisks() {
        return disks;
    }

    public void setDisks(List<FormatDiskResult> disks) {
        this.disks = disks;
    }

}
