package com.h3c.idcloud.core.adapter.facade.provision.model.vm;

import org.codehaus.jackson.annotate.JsonProperty;

public class ServerNetwork {

    @JsonProperty("fixed_ip")
    private String ip;

    @JsonProperty("mac_address")
    private String mac;

    @JsonProperty("network_id")
    private String networkId;
    @JsonProperty("subnet_id")
    private String subnetId;

    public String getSubnetId() {
        return subnetId;
    }

    public void setSubnetId(String subnetId) {
        this.subnetId = subnetId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

}
