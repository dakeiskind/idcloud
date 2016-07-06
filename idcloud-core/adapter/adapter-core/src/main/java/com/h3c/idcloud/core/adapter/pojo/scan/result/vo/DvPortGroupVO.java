package com.h3c.idcloud.core.adapter.pojo.scan.result.vo;

public class DvPortGroupVO {

    private String name;
    private String uuid;
    private String vlanId;
    private String numPorts;
    private String type;
    private String vswitchName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getVlanId() {
        return vlanId;
    }

    public void setVlanId(String vlanId) {
        this.vlanId = vlanId;
    }

    public String getNumPorts() {
        return numPorts;
    }

    public void setNumPorts(String numPorts) {
        this.numPorts = numPorts;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVswitchName() {
        return vswitchName;
    }

    public void setVswitchName(String vswitchName) {
        this.vswitchName = vswitchName;
    }

}
