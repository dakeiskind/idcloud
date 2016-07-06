package com.h3c.idcloud.core.adapter.pojo.scan.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.ViosResult;

import java.util.List;

public class DiskScanResult extends BaseResult {
    private List<ViosResult> viosVos;

    public List<ViosResult> getViosVos() {
        return viosVos;
    }

    public void setViosVos(List<ViosResult> viosVos) {
        this.viosVos = viosVos;
    }

}
