package com.h3c.idcloud.core.adapter.pojo.scan.result.vo;

public class SysDiskVo {
    private String diskName;
    private String size;
    private String provisionType;
    private String laprId;
    private String sspName;
    private String clusterName;
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDiskName() {
        return diskName;
    }

    public void setDiskName(String diskName) {
        this.diskName = diskName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getProvisionType() {
        return provisionType;
    }

    public void setProvisionType(String provisionType) {
        this.provisionType = provisionType;
    }

    public String getLaprId() {
        return laprId;
    }

    public void setLaprId(String laprId) {
        this.laprId = laprId;
    }

    public String getSspName() {
        return sspName;
    }

    public void setSspName(String sspName) {
        this.sspName = sspName;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }
}
