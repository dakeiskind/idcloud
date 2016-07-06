package com.h3c.idcloud.core.adapter.pojo.software.result;

import com.h3c.idcloud.core.adapter.pojo.common.BaseResult;
import com.h3c.idcloud.core.adapter.pojo.software.Software;

import java.util.ArrayList;
import java.util.List;

public class SoftwareDeployResult extends BaseResult {

    private String vmId;
    private List<Software> softwares = new ArrayList<Software>();

    public String getVmId() {
        return vmId;
    }

    public void setVmId(String vmId) {
        this.vmId = vmId;
    }

    public List<Software> getSoftwares() {
        return softwares;
    }

    public void setSoftwares(List<Software> softwares) {
        this.softwares = softwares;
    }

}
