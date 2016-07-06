package com.h3c.idcloud.core.adapter.facade.provision.model.scan;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.SSPVo;

import java.util.List;

public class SSPResult extends CommonAdapterResult {
    private List<SSPVo> sspVos;

    public List<SSPVo> getSspVos() {
        return sspVos;
    }

    public void setSspVos(List<SSPVo> sspVos) {
        this.sspVos = sspVos;
    }
}
