package com.h3c.idcloud.core.adapter.pojo.network.result;

public class SecurityGroupRules {

    private String remoteGroupId;

    private String direction;

    private String remoteIpPrefix;

    private String protocol;

    private String tenantId;

    private String portRangeMax;

    private String securityGroupId;

    private String PortRangeMin;

    private String ethertype;

    private String id;

    public String getRemoteGroupId() {
        return remoteGroupId;
    }

    public void setRemoteGroupId(String remoteGroupId) {
        this.remoteGroupId = remoteGroupId;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getRemoteIpPrefix() {
        return remoteIpPrefix;
    }

    public void setRemoteIpPrefix(String remoteIpPrefix) {
        this.remoteIpPrefix = remoteIpPrefix;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getPortRangeMax() {
        return portRangeMax;
    }

    public void setPortRangeMax(String portRangeMax) {
        this.portRangeMax = portRangeMax;
    }

    public String getSecurityGroupId() {
        return securityGroupId;
    }

    public void setSecurityGroupId(String securityGroupId) {
        this.securityGroupId = securityGroupId;
    }

    public String getPortRangeMin() {
        return PortRangeMin;
    }

    public void setPortRangeMin(String portRangeMin) {
        PortRangeMin = portRangeMin;
    }

    public String getEthertype() {
        return ethertype;
    }

    public void setEthertype(String ethertype) {
        this.ethertype = ethertype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
