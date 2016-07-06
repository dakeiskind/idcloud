package com.h3c.idcloud.core.adapter.pojo.vm;

public class VmNic {

    private String netId;
    private String privateIp = "";
    private String dns = "";
    private String netmask = "";
    private String gateway = "";
    private String port = "";
    private String operate; // add&delete
    private boolean primary = false;
    private String mac = "";
    private String subnetId;

    //for power create net oo
    private String networks;
    private Integer mparSlotNumber;
    private Integer portVlanID;
    private String virSwitch;

    private String unitPhysLoc;

    public String getUnitPhysLoc() {
        return unitPhysLoc;
    }

    public void setUnitPhysLoc(String unitPhysLoc) {
        this.unitPhysLoc = unitPhysLoc;
    }

    public String getNetworks() {
        return networks;
    }

    public void setNetworks(String networks) {
        this.networks = networks;
    }

    public Integer getMparSlotNumber() {
        return mparSlotNumber;
    }

    public void setMparSlotNumber(Integer mparSlotNumber) {
        this.mparSlotNumber = mparSlotNumber;
    }

    public Integer getPortVlanID() {
        return portVlanID;
    }

    public void setPortVlanID(Integer portVlanID) {
        this.portVlanID = portVlanID;
    }

    public String getVirSwitch() {
        return virSwitch;
    }

    public void setVirSwitch(String virSwitch) {
        this.virSwitch = virSwitch;
    }

    public String getSubnetId() {
        return subnetId;
    }

    public void setSubnetId(String subnetId) {
        this.subnetId = subnetId;
    }

    /**
     * @return the netId
     */
    public String getNetId() {
        return netId;
    }

    /**
     * @param netId the netId to set
     */
    public void setNetId(String netId) {
        this.netId = netId;
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

        return (port == null) ? "" : port;
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
