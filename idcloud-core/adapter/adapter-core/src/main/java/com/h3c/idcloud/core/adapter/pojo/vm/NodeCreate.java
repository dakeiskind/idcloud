package com.h3c.idcloud.core.adapter.pojo.vm;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class NodeCreate extends Base {

    private String nodeId = "";
    private String osType = "";
    private String privateIp = "";
    private String publicIp = "";
    private String parentService = "";
    private String childService = "";
    private String operate = "";

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getPrivateIp() {
        return privateIp;
    }

    public void setPrivateIp(String privateIp) {
        this.privateIp = privateIp;
    }

    public String getPublicIp() {
        return publicIp;
    }

    public void setPublicIp(String publicIp) {
        this.publicIp = publicIp;
    }

    public String getParentService() {
        return parentService;
    }

    public void setParentService(String parentService) {
        this.parentService = parentService;
    }

    public String getChildService() {
        return childService;
    }

    public void setChildService(String childService) {
        this.childService = childService;
    }

    public String getOperate() {
        return operate;
    }

    public void setOperate(String operate) {
        this.operate = operate;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }
}
