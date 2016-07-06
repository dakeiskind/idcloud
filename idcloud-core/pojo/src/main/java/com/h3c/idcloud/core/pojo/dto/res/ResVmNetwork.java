package com.h3c.idcloud.core.pojo.dto.res;


import com.h3c.idcloud.core.adapter.pojo.scan.result.vo.IpVO;

import java.io.Serializable;

public class ResVmNetwork implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 资源ID
     */
    private String resVmSid;

    /**
     * 所属网络ID
     */
    private String resNetworkSid;

    /**
     * 所属网络名称
     */
    private String resNetworkName;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * MAC地址
     */
    private String mac;

    /**
     * 标识是否为主网卡
     */
    private String netPrimary;

    /**
     * 网卡状态
     */
    private String status;
    /**
     * IP uuid
     */
    private String uuid;
    /**
     * 释放方式
     */
    private String releaseMode;

    private String networkType;

    public ResVmNetwork() {

    }

    public ResVmNetwork(IpVO ipVo) {
        this.ipAddress = ipVo.getIp();
        this.mac = ipVo.getMac();
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     * @return 资源ID
     */
    public String getResVmSid() {
        return resVmSid;
    }

    /**
     * @param resVmSid 资源ID
     */
    public void setResVmSid(String resVmSid) {
        this.resVmSid = resVmSid;
    }

    /**
     * @return 所属网络ID
     */
    public String getResNetworkSid() {
        return resNetworkSid;
    }

    /**
     * @param resNetworkSid 所属网络ID
     */
    public void setResNetworkSid(String resNetworkSid) {
        this.resNetworkSid = resNetworkSid;
    }

    /**
     * @return IP地址
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress IP地址
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getResNetworkName() {
        return resNetworkName;
    }

    public void setResNetworkName(String resNetworkName) {
        this.resNetworkName = resNetworkName;
    }

    /**
     * @return the netPrimary
     */
    public String getNetPrimary() {
        return netPrimary;
    }

    /**
     * @param netPrimary the netPrimary to set
     */
    public void setNetPrimary(String netPrimary) {
        this.netPrimary = netPrimary;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getReleaseMode() {
        return releaseMode;
    }

    public void setReleaseMode(String releaseMode) {
        this.releaseMode = releaseMode;
    }
}