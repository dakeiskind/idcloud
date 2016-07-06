package com.h3c.idcloud.core.adapter.pojo.scan.result.vo;

public class CpuPoolVo {
    private String serverSn;

    private String hostName;

    private String cpuPoolName;

    private Integer shareProcPoolId;

    private String maxPoolProcUnits;

    private String currOeservedPoolProcUnits;
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getServerSn() {
        return serverSn;
    }

    public void setServerSn(String serverSn) {
        this.serverSn = serverSn;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getCpuPoolName() {
        return cpuPoolName;
    }

    public void setCpuPoolName(String cpuPoolName) {
        this.cpuPoolName = cpuPoolName;
    }

    public Integer getShareProcPoolId() {
        return shareProcPoolId;
    }

    public void setShareProcPoolId(Integer shareProcPoolId) {
        this.shareProcPoolId = shareProcPoolId;
    }

    public String getMaxPoolProcUnits() {
        return maxPoolProcUnits;
    }

    public void setMaxPoolProcUnits(String maxPoolProcUnits) {
        this.maxPoolProcUnits = maxPoolProcUnits;
    }

    public String getCurrOeservedPoolProcUnits() {
        return currOeservedPoolProcUnits;
    }

    public void setCurrOeservedPoolProcUnits(String currOeservedPoolProcUnits) {
        this.currOeservedPoolProcUnits = currOeservedPoolProcUnits;
    }

}
