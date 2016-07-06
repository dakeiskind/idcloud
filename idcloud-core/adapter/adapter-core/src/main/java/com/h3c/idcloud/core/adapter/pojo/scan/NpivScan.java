package com.h3c.idcloud.core.adapter.pojo.scan;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

import java.util.List;

public class NpivScan extends Base {
    private List<ViosVo> viosList;
    private List<String> hosts;

    public List<String> getHosts() {
        return hosts;
    }

    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
    }

    public List<ViosVo> getViosList() {
        return viosList;
    }

    public void setViosList(List<ViosVo> viosList) {
        this.viosList = viosList;
    }
}
