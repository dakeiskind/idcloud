package com.h3c.idcloud.core.adapter.pojo.scan.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.CpuPoolVo;

import java.util.List;

public class CpuPoolScanResult extends BaseResult {
    private List<CpuPoolVo> cpuPools;

    public List<CpuPoolVo> getCpuPools() {
        return cpuPools;
    }

    public void setCpuPools(List<CpuPoolVo> cpuPools) {
        this.cpuPools = cpuPools;
    }

}
