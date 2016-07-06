package com.h3c.idcloud.core.adapter.facade.provision.model.scan;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

import java.util.ArrayList;
import java.util.List;

public class SimpleHosts extends CommonAdapterResult {

    private List<SimpleHost> listdata = new ArrayList<SimpleHost>();

    public List<SimpleHost> getListdata() {
        return listdata;
    }

    public void setListdata(List<SimpleHost> listdata) {
        this.listdata = listdata;
    }

}
