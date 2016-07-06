package com.h3c.idcloud.core.adapter.facade.provision.model.scan;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

import java.util.ArrayList;
import java.util.List;

public class Uuids extends CommonAdapterResult {

    private List<Uuid> listdata = new ArrayList<Uuid>();

    public List<Uuid> getListdata() {
        return listdata;
    }

    public void setListdata(List<Uuid> listdata) {
        this.listdata = listdata;
    }

}
