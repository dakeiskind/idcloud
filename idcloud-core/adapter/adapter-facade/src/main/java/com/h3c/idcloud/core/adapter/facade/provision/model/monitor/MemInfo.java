package com.h3c.idcloud.core.adapter.facade.provision.model.monitor;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class MemInfo extends CommonAdapterResult {

    private String nodeId;
    private String avgUsage;
    private String maxUsage;
    private String minUsage;
    private String avgFree;
    private String maxFree;
    private String minFree;
    private List<MemInfoValue> dataUsage = new ArrayList<MemInfoValue>();
    private List<MemInfoValue> dataFree = new ArrayList<MemInfoValue>();

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    @JsonProperty("mem_avg_usage")
    public String getAvgUsage() {
        return avgUsage;
    }

    public void setAvgUsage(String avgUsage) {
        this.avgUsage = avgUsage;
    }

    @JsonProperty("mem_max_usage")
    public String getMaxUsage() {
        return maxUsage;
    }

    public void setMaxUsage(String maxUsage) {
        this.maxUsage = maxUsage;
    }

    @JsonProperty("mem_min_usage")
    public String getMinUsage() {
        return minUsage;
    }

    public void setMinUsage(String minUsage) {
        this.minUsage = minUsage;
    }

    @JsonProperty("mem_avg_free")
    public String getAvgFree() {
        return avgFree;
    }

    public void setAvgFree(String avgFree) {
        this.avgFree = avgFree;
    }

    @JsonProperty("mem_max_free")
    public String getMaxFree() {
        return maxFree;
    }

    public void setMaxFree(String maxFree) {
        this.maxFree = maxFree;
    }

    @JsonProperty("mem_min_free")
    public String getMinFree() {
        return minFree;
    }

    public void setMinFree(String minFree) {
        this.minFree = minFree;
    }

    @JsonProperty("memUsagedata")
    public List<MemInfoValue> getDataUsage() {
        return dataUsage;
    }

    public void setDataUsage(List<MemInfoValue> dataUsage) {
        this.dataUsage = dataUsage;
    }

    @JsonProperty("memFreedata")
    public List<MemInfoValue> getDataFree() {
        return dataFree;
    }

    public void setDataFree(List<MemInfoValue> dataFree) {
        this.dataFree = dataFree;
    }

}
