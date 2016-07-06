package com.h3c.idcloud.core.adapter.facade.provision.model.vm;

public class ServerNic {

    private String netId;
    private String publicIp = "";
    private String privateIp = "";
    private String dns = "";
    private String netmask = "";
    private String gateway = "";
    private String port = "";
    private String operate; // add&delete
    private boolean primary = false;
    private String mac = "";

    private String subnetId;
    private String hostItemIndex;
    private String hostItemAddr;

    public String getHostItemIndex() {
        return hostItemIndex;
    }

    public void setHostItemIndex(String hostItemIndex) {
        this.hostItemIndex = hostItemIndex;
    }

    public String getHostItemAddr() {
        return hostItemAddr;
    }

    public void setHostItemAddr(String hostItemAddr) {
        this.hostItemAddr = hostItemAddr;
    }

    public String getSubnetId() {
        return subnetId;
    }

    public void setSubnetId(String subnetId) {
        this.subnetId = subnetId;
    }

    public String getNetId() {
        return netId;
    }

    public void setNetId(String netId) {
        this.netId = netId;
    }

    public String getPublicIp() {
        return publicIp;
    }

    public void setPublicIp(String publicIp) {
        this.publicIp = publicIp;
    }

    public String getPrivateIp() {
        return privateIp;
    }

    public void setPrivateIp(String privateIp) {
        this.privateIp = privateIp;
    }

    public String getDns() {
        return dns;
    }

    public void setDns(String dns) {
        this.dns = dns;
    }

    public String getNetmask() {
        return netmask;
    }

    public void setNetmask(String netmask) {
        this.netmask = netmask;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

}
