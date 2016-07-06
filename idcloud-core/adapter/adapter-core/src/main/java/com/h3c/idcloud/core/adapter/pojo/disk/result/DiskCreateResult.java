package com.h3c.idcloud.core.adapter.pojo.disk.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

public class DiskCreateResult extends BaseResult {

    private String sid;
    private String name;
    private String label;
    private String description;
    private String location;
    private String size;
    private String uuid;
    private String vmName;
    private String path;
    //hmc return
    private String fc;
    private String wwpn;
    private String hostItemIndex;
    private String hostItemAddr;

    public String getHostItemIndex() {
        return hostItemIndex;
    }

    public void setHostItemIndex(String hostItemIndex) {
        this.hostItemIndex = hostItemIndex;
    }

    public String getHostItemAddr() {
        return hostItemAddr;
    }

    public void setHostItemAddr(String hostItemAddr) {
        this.hostItemAddr = hostItemAddr;
    }

    public String getFc() {
        return fc;
    }

    public void setFc(String fc) {
        this.fc = fc;
    }

    public String getWwpn() {
        return wwpn;
    }

    public void setWwpn(String wwpn) {
        this.wwpn = wwpn;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
