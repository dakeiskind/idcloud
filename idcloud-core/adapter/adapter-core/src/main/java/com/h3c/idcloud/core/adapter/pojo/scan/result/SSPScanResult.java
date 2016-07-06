package com.h3c.idcloud.core.adapter.pojo.scan.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.SSPVo;

import java.util.List;

public class SSPScanResult extends BaseResult {
    private List<SSPVo> sspVos;

    public List<SSPVo> getSspVos() {
        return sspVos;
    }

    public void setSspVos(List<SSPVo> sspVos) {
        this.sspVos = sspVos;
    }
}
