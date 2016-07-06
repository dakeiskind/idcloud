package com.h3c.idcloud.core.adapter.facade.provision.model.admin;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class Roles extends CommonAdapterResult {

    @JsonProperty("roles")
    private List<Role> listdata;

    public List<Role> getListdata() {
        return listdata;
    }

    public void setListdata(List<Role> listdata) {
        this.listdata = listdata;
    }


}
