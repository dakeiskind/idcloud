package com.h3c.idcloud.core.adapter.pojo.network.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

public class FloatingIpBulkDetail extends BaseResult {

    private String eth;
    private String project;
    private String instance;
    private String pool;
    private String address;

    //	@JsonProperty("interface")
    public String getEth() {
        return eth;
    }

    public void setEth(String eth) {
        this.eth = eth;
    }

    //	@JsonProperty("projectId")
    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    //	@JsonProperty("instanceUuid")
    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getPool() {
        return pool;
    }

    public void setPool(String pool) {
        this.pool = pool;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


}
