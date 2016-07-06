package com.h3c.idcloud.core.adapter.pojo.vm;

import com.h3c.idcloud.core.adapter.pojo.common.Base;

public class VmOperate extends Base {

    private String vmName;
    private String action;
    private String id;
    private String uuid;
    private String rebootType;

    //for par
    private String hostName;
    private String proFile;

    private VmResize vmResize;

    public VmResize getVmResize() {
        return vmResize;
    }

    public void setVmResize(VmResize vmResize) {
        this.vmResize = vmResize;
    }

    public String getProFile() {
        return proFile;
    }

    public void setProFile(String proFile) {
        this.proFile = proFile;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getRebootType() {
        return rebootType;
    }

    public void setRebootType(String rebootType) {
        this.rebootType = rebootType;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
