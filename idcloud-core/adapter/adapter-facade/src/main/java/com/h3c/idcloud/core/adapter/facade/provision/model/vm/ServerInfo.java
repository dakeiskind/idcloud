package com.h3c.idcloud.core.adapter.facade.provision.model.vm;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class ServerInfo extends CommonAdapterResult {

    private String status;

    private String hostId;

    private String name;

    private String id;

    private List<String> security_name;

    private List<ServerNetwork> networks;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    @JsonProperty("host_id")
    public void setKvmHostId(String hostId) {
        this.hostId = hostId;
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

    public List<ServerNetwork> getNetworks() {
        return networks;
    }

    public void setNetworks(List<ServerNetwork> networks) {
        this.networks = networks;
    }


}
