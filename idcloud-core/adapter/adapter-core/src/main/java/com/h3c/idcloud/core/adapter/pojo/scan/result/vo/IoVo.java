package com.h3c.idcloud.core.adapter.pojo.scan.result.vo;

public class IoVo {
    private String serverSn;
    private String hostName;
    private String lparName;
    private String lparId;
    private String drcIndex;
    private String drcName;
    private String description;
    private String unitPhysloc;
    private String ioType;
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

    public String getLparName() {
        return lparName;
    }

    public void setLparName(String lparName) {
        this.lparName = lparName;
    }

    public String getLparId() {
        return lparId;
    }

    public void setLparId(String lparId) {
        this.lparId = lparId;
    }

    public String getDrcIndex() {
        return drcIndex;
    }

    public void setDrcIndex(String drcIndex) {
        this.drcIndex = drcIndex;
    }

    public String getDrcName() {
        return drcName;
    }

    public void setDrcName(String drcName) {
        this.drcName = drcName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUnitPhysloc() {
        return unitPhysloc;
    }

    public void setUnitPhysloc(String unitPhysloc) {
        this.unitPhysloc = unitPhysloc;
    }

    public String getIoType() {
        return ioType;
    }

    public void setIoType(String ioType) {
        this.ioType = ioType;
    }
}
