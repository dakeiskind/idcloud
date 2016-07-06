package com.h3c.idcloud.core.pojo.instance;

import com.h3c.idcloud.core.pojo.common.ServiceBaseInput;

import java.io.Serializable;

/**
 * com.h3c.idcloud.core.pojo.instance
 *
 * @author Chaohong.Mao
 */
public class ResNetInst extends ServiceBaseInput implements Serializable {
    private String vpcName;
    private String vpcDescription;
    private String networkName;
    private String networkType;
    private String networkDescription;
    private String ipType;
    private String subnet;
    private String subnetMask;
    private String gateWay;

    public String getGateWay() {
        return gateWay;
    }

    public void setGateWay(String gateWay) {
        this.gateWay = gateWay;
    }

    public String getVpcName() {
        return vpcName;
    }

    public void setVpcName(String vpcName) {
        this.vpcName = vpcName;
    }

    public String getVpcDescription() {
        return vpcDescription;
    }

    public void setVpcDescription(String vpcDescription) {
        this.vpcDescription = vpcDescription;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public String getNetworkDescription() {
        return networkDescription;
    }

    public void setNetworkDescription(String networkDescription) {
        this.networkDescription = networkDescription;
    }

    public String getIpType() {
        return ipType;
    }

    public void setIpType(String ipType) {
        this.ipType = ipType;
    }

    public String getSubnet() {
        return subnet;
    }

    public void setSubnet(String subnet) {
        this.subnet = subnet;
    }

    public String getSubnetMask() {
        return subnetMask;
    }

    public void setSubnetMask(String subnetMask) {
        this.subnetMask = subnetMask;
    }
}
