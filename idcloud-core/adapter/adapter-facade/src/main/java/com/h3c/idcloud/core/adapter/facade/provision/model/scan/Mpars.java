package com.h3c.idcloud.core.adapter.facade.provision.model.scan;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;
import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.MparVO;

import java.util.List;

public class Mpars extends CommonAdapterResult {
    private List<MparVO> vms;

    public List<MparVO> getVms() {
        return vms;
    }

    public void setVms(List<MparVO> vms) {
        this.vms = vms;
    }

}
