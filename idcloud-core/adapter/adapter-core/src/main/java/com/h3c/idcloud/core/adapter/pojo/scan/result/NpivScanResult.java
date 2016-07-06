package com.h3c.idcloud.core.adapter.pojo.scan.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.DataDiskVo;

import java.util.List;

public class NpivScanResult extends BaseResult {
    private List<DataDiskVo> disks;

    public List<DataDiskVo> getDisks() {
        return disks;
    }

    public void setDisks(List<DataDiskVo> disks) {
        this.disks = disks;
    }
}
