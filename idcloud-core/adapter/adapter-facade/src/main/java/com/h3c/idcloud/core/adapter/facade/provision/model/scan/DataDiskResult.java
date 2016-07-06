package com.h3c.idcloud.core.adapter.facade.provision.model.scan;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.DataDiskVo;

import java.util.List;

public class DataDiskResult extends CommonAdapterResult {
    private List<DataDiskVo> disks;

    public List<DataDiskVo> getDisks() {
        return disks;
    }

    public void setDisks(List<DataDiskVo> disks) {
        this.disks = disks;
    }
}
