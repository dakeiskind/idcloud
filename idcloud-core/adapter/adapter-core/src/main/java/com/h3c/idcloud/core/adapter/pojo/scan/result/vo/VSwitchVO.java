package com.h3c.idcloud.core.adapter.pojo.scan.result.vo;

public class VSwitchVO {
    private String name;
    private String hostName;
    private String serverSn;
    private String vlanId;
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getServerSn() {
        return serverSn;
    }

    public void setServerSn(String serverSn) {
        this.serverSn = serverSn;
    }

    public String getVlanId() {
        return vlanId;
    }

    public void setVlanId(String vlanId) {
        this.vlanId = vlanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
