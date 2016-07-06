package com.h3c.idcloud.core.adapter.facade.provision.model.monitor;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CpuInfoHis extends CommonAdapterResult {

    private String nodeId;
    private String peroid;
    private String avgUsage;
    private String maxUsage;
    private String minUsage;
    private List<CpuInfoValue> values = new ArrayList<CpuInfoValue>();

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getPeroid() {
        return peroid;
    }

    public void setPeroid(String peroid) {
        this.peroid = peroid;
    }

    @JsonProperty("cpu_avg_usage")
    public String getAvgUsage() {
        return avgUsage;
    }

    public void setAvgUsage(String avgUsage) {
        this.avgUsage = avgUsage;
    }

    @JsonProperty("cpu_max_usage")
    public String getMaxUsage() {
        return maxUsage;
    }

    public void setMaxUsage(String maxUsage) {
        this.maxUsage = maxUsage;
    }

    @JsonProperty("cpu_min_usage")
    public String getMinUsage() {
        return minUsage;
    }

    public void setMinUsage(String minUsage) {
        this.minUsage = minUsage;
    }

    @JsonProperty("data")
    public List<CpuInfoValue> getValues() {
        return values;
    }

    public void setValues(List<CpuInfoValue> values) {
        this.values = values;
    }
}
