package com.h3c.idcloud.core.adapter.facade.provision.model.scan;

import org.codehaus.jackson.annotate.JsonProperty;


public class Ip {

    private String mac;
    private String ip;
    private String networkId;
    private String subnetId;

    public String getSubnetId() {
        return subnetId;
    }

    public void setSubnetId(String subnetId) {
        this.subnetId = subnetId;
    }

    @JsonProperty("subnet_id")
    public void setKvmSubnetId(String subnetId) {
        this.subnetId = subnetId;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    @JsonProperty("network_id")
    public void setKvmNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @JsonProperty("mac_address")
    public void setKvmMac(String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @JsonProperty("fixed_ip")
    public void setKvmIp(String ip) {
        this.ip = ip;
    }

}
