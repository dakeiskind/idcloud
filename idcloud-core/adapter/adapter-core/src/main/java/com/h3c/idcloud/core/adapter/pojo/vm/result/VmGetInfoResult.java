package com.h3c.idcloud.core.adapter.pojo.vm.result;


import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.network.Network;

import java.util.List;

public class VmGetInfoResult extends BaseResult {
    private String stasus;

    private String host_id;

    private String name;

    private String id;

    private List<String> security_name;

    private List<Network> networks;

    public String getStasus() {
        return stasus;
    }

    public void setStasus(String stasus) {
        this.stasus = stasus;
    }

    public String getHost_id() {
        return host_id;
    }

    public void setHost_id(String host_id) {
        this.host_id = host_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getSecurity_name() {
        return security_name;
    }

    public void setSecurity_name(List<String> security_name) {
        this.security_name = security_name;
    }

    public List<Network> getNetworks() {
        return networks;
    }

    public void setNetworks(List<Network> networks) {
        this.networks = networks;
    }

}
