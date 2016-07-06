package com.h3c.idcloud.core.adapter.facade.provision.model.monitor;

import com.h3c.idcloud.core.adapter.facade.provision.model.CommonAdapterResult;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class NetInfo extends CommonAdapterResult {

    private String nodeId;
    private String unit;
    private String avgIn;
    private String avgOut;
    private String maxIn;
    private String maxOut;
    private String minIn;
    private String minOut;
    private List<NetInfoValue> data = new ArrayList<NetInfoValue>();

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @JsonProperty("network_avg_in")
    public String getAvgIn() {
        return avgIn;
    }

    public void setAvgIn(String avgIn) {
        this.avgIn = avgIn;
    }

    @JsonProperty("network_avg_out")
    public String getAvgOut() {
        return avgOut;
    }

    public void setAvgOut(String avgOut) {
        this.avgOut = avgOut;
    }

    @JsonProperty("network_max_in")
    public String getMaxIn() {
        return maxIn;
    }

    public void setMaxIn(String maxIn) {
        this.maxIn = maxIn;
    }

    @JsonProperty("network_max_out")
    public String getMaxOut() {
        return maxOut;
    }

    public void setMaxOut(String maxOut) {
        this.maxOut = maxOut;
    }

    @JsonProperty("network_min_in")
    public String getMinIn() {
        return minIn;
    }

    public void setMinIn(String minIn) {
        this.minIn = minIn;
    }

    @JsonProperty("network_min_out")
    public String getMinOut() {
        return minOut;
    }

    public void setMinOut(String minOut) {
        this.minOut = minOut;
    }

    public List<NetInfoValue> getData() {
        return data;
    }

    public void setData(List<NetInfoValue> data) {
        this.data = data;
    }

}
