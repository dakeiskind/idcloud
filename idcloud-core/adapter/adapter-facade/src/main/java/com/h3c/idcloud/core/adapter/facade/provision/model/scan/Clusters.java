package com.h3c.idcloud.core.adapter.facade.provision.model.scan;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

import java.util.ArrayList;
import java.util.List;

public class Clusters extends CommonAdapterResult {

    private List<Cluster> listdata = new ArrayList<Cluster>();

    public List<Cluster> getListdata() {
        return listdata;
    }

    public void setListdata(List<Cluster> listdata) {
        this.listdata = listdata;
    }

}
