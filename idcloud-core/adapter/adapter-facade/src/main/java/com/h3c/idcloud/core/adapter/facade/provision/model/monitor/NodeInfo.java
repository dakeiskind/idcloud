package com.h3c.idcloud.core.adapter.facade.provision.model.monitor;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class NodeInfo extends CommonAdapterResult {

    private String nodeId;
    private String cpuUsage;
    private String memUsage;
    private String memFree;
    private String memTotal;
    private List<?> disk = new ArrayList();

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(String cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public String getMemUsage() {
        return memUsage;
    }

    public void setMemUsage(String memUsage) {
        this.memUsage = memUsage;
    }

    public String getMemFree() {
        return memFree;
    }

    public void setMemFree(String memFree) {
        this.memFree = memFree;
    }

    public String getMemTotal() {
        return memTotal;
    }

    public void setMemTotal(String memTotal) {
        this.memTotal = memTotal;
    }

    @JsonIgnore
    public List<?> getDisk() {
        return disk;
    }

    public void setDisk(List<?> disk) {
        this.disk = disk;
    }

}
