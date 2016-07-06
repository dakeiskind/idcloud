package com.h3c.idcloud.core.adapter.pojo.disk;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class DiskCreate extends Base {

    private String sid;
    private String name;
    private String label;
    private String description;
    private String location;
    private String size;
    private String vmName;
    private String virtualDiskType;//thin 瘦置备、thick 厚置备、eagerZeroedThick 厚置备延迟置零

    public String getVirtualDiskType() {
        return virtualDiskType;
    }

    public void setVirtualDiskType(String virtualDiskType) {
        this.virtualDiskType = virtualDiskType;
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

}
