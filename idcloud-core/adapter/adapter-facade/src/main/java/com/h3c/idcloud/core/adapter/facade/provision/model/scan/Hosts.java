package com.h3c.idcloud.core.adapter.facade.provision.model.scan;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

import java.util.ArrayList;
import java.util.List;

public class Hosts extends CommonAdapterResult {

    private List<Host> listdata = new ArrayList<Host>();
    private List<Host> hypervisors = new ArrayList<Host>();
    private Host host;

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public List<Host> getHypervisors() {
        return hypervisors;
    }

    public void setHypervisors(List<Host> hypervisors) {
        this.hypervisors = hypervisors;
    }

    public List<Host> getListdata() {
        return listdata;
    }

    public void setListdata(List<Host> listdata) {
        this.listdata = listdata;
    }

}
