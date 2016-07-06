package com.h3c.idcloud.core.adapter.facade.provision.model.scan;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

import java.util.ArrayList;
import java.util.List;

public class DataStores extends CommonAdapterResult {

    private List<DataStore> listdata = new ArrayList<DataStore>();

    public List<DataStore> getListdata() {
        return listdata;
    }

    public void setListdata(List<DataStore> listdata) {
        this.listdata = listdata;
    }

}
