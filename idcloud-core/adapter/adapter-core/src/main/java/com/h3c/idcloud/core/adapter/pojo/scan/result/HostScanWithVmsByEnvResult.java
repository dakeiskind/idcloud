package com.h3c.idcloud.core.adapter.pojo.scan.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.HostVO;

import java.util.List;

public class HostScanWithVmsByEnvResult extends BaseResult {
    private List<HostVO> hostVOs;

    public List<HostVO> getHostVOs() {
        return hostVOs;
    }

    public void setHostVOs(List<HostVO> hostVOs) {
        this.hostVOs = hostVOs;
    }

}
