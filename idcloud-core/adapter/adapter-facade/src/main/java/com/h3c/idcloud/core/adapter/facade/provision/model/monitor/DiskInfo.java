package com.h3c.idcloud.core.adapter.facade.provision.model.monitor;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class DiskInfo extends CommonAdapterResult {

    private String nodeId;
    private String total;
    private String avgUsed;
    private String avgUsage;
    private List<?> data = new ArrayList();

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    @JsonProperty("disk_total")
    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @JsonProperty("disk_avg_used")
    public String getAvgUsed() {
        return avgUsed;
    }

    public void setAvgUsed(String avgUsed) {
        this.avgUsed = avgUsed;
    }

    @JsonProperty("disk_avg_usage")
    public String getAvgUsage() {
        return avgUsage;
    }

    public void setAvgUsage(String avgUsage) {
        this.avgUsage = avgUsage;
    }

    @JsonIgnore
    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

}
