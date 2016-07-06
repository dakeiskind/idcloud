package com.h3c.idcloud.core.adapter.pojo.vm.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

import java.util.ArrayList;
import java.util.List;

public class VmQueryResult extends BaseResult {

    private List<VmInfo> servers = new ArrayList<VmInfo>();

    public List<VmInfo> getServers() {
        return servers;
    }

    public void setServers(List<VmInfo> servers) {
        this.servers = servers;
    }

}
