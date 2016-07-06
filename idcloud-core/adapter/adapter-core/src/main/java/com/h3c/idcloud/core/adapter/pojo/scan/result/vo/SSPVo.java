package com.h3c.idcloud.core.adapter.pojo.scan.result.vo;

public class SSPVo {
    private String poolName;
    private String clusterName;
    private String poolSize;
    private String freeSpace;
    private String frameHost;
    private String viosLparName;
    private String viosLparId;
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(String poolSize) {
        this.poolSize = poolSize;
    }

    public String getFreeSpace() {
        return freeSpace;
    }

    public void setFreeSpace(String freeSpace) {
        this.freeSpace = freeSpace;
    }

    public String getFrameHost() {
        return frameHost;
    }

    public void setFrameHost(String frameHost) {
        this.frameHost = frameHost;
    }

    public String getViosLparName() {
        return viosLparName;
    }

    public void setViosLparName(String viosLparName) {
        this.viosLparName = viosLparName;
    }

    public String getViosLparId() {
        return viosLparId;
    }

    public void setViosLparId(String viosLparId) {
        this.viosLparId = viosLparId;
    }
}
