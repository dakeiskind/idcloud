package com.h3c.idcloud.core.adapter.facade.provision.model.scan;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.CpuPoolVo;

import java.util.List;

public class CpuPools extends CommonAdapterResult {
    private List<CpuPoolVo> cpuPools;

    public List<CpuPoolVo> getCpuPools() {
        return cpuPools;
    }

    public void setCpuPools(List<CpuPoolVo> cpuPools) {
        this.cpuPools = cpuPools;
    }
}
