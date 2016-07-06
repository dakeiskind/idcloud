package com.h3c.idcloud.core.adapter.pojo.vm.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;

public class VmRemoveResult extends BaseResult {
    private String sid;
    private String hostName;
    private String vmName;
    private String id;
    private boolean isDueToFailOfCreating;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isDueToFailOfCreating() {
        return isDueToFailOfCreating;
    }

    public void setDueToFailOfCreating(boolean isDueToFailOfCreating) {
        this.isDueToFailOfCreating = isDueToFailOfCreating;
    }

}
