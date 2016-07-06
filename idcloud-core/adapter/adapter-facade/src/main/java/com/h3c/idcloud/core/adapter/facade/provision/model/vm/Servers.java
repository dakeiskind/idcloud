package com.h3c.idcloud.core.adapter.facade.provision.model.vm;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

import java.util.List;

public class Servers extends CommonAdapterResult {

    private List<Server> listdata;

    private List<Server> servers;

    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    public List<Server> getListdata() {
        return listdata;
    }

    public void setListdata(List<Server> listdata) {
        this.listdata = listdata;
    }

}
