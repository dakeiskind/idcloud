package com.h3c.idcloud.core.adapter.facade.provision.model.scan;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

import java.util.List;

public class SysDiskResult extends CommonAdapterResult {
    private List<ViosVo> viosList;

    public List<ViosVo> getViosList() {
        return viosList;
    }

    public void setViosList(List<ViosVo> viosList) {
        this.viosList = viosList;
    }

}
