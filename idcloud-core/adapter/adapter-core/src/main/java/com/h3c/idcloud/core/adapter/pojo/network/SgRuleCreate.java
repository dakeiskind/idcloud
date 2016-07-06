package com.h3c.idcloud.core.adapter.pojo.network;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class SgRuleCreate extends Base {

    private String direction;

    private String portRangMin;

    private String ethertype;

    private String portRangeMax;

    private String protocol;

    private String remoteIpPrefix;

    private String securityGroupId;

    public String getSecurityGroupId() {
        return securityGroupId;
    }

    public void setSecurityGroupId(String securityGroupId) {
        this.securityGroupId = securityGroupId;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }


    public String getPortRangMin() {
        return portRangMin;
    }

    public void setPortRangMin(String portRangMin) {
        this.portRangMin = portRangMin;
    }

    public String getEthertype() {
        return ethertype;
    }

    public void setEthertype(String ethertype) {
        this.ethertype = ethertype;
    }


    public String getPortRangeMax() {
        return portRangeMax;
    }

    public void setPortRangeMax(String portRangeMax) {
        this.portRangeMax = portRangeMax;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getRemoteIpPrefix() {
        return remoteIpPrefix;
    }

    public void setRemoteIpPrefix(String remoteIpPrefix) {
        this.remoteIpPrefix = remoteIpPrefix;
    }


}
