package com.h3c.idcloud.core.adapter.facade.provision.model.scan;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

import java.util.List;

public class Cluster extends CommonAdapterResult {

    private String resTopologyName;
    private String openHa;
    private List<Host> hosts;

    public String getResTopologyName() {
        return resTopologyName;
    }

    public void setResTopologyName(String resTopologyName) {
        this.resTopologyName = resTopologyName;
    }

    public String getOpenHa() {
        return openHa;
    }

    public void setOpenHa(String openHa) {
        this.openHa = openHa;
    }

    public List<Host> getHosts() {
        return hosts;
    }

    public void setHosts(List<Host> hosts) {
        this.hosts = hosts;
    }

}
