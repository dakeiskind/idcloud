package com.hptsic.cloud.monitor.pojo.node;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class NodeCpuInfo {
	private String id;
	private String maxUsage;
	private String minUsage;
	private String avgUsage;
	private List<CpuProcessor> processor;//cpu 单元
	private List<CpuUsageData> usageDatas;
	public List<CpuUsageData> getUsageDatas() {
		return usageDatas;
	}
	@JsonProperty("data")
	public void setUsageDatas(List<CpuUsageData> usageDatas) {
		this.usageDatas = usageDatas;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMaxUsage() {
		return maxUsage;
	}
	@JsonProperty("max_usage")
	public void setMaxUsage(String maxUsage) {
		this.maxUsage = maxUsage;
	}
	public String getMinUsage() {
		return minUsage;
	}
	@JsonProperty("min_usage")
	public void setMinUsage(String minUsage) {
		this.minUsage = minUsage;
	}
	public String getAvgUsage() {
		return avgUsage;
	}
	@JsonProperty("avg_usage")
	public void setAvgUsage(String avgUsage) {
		this.avgUsage = avgUsage;
	}
	public List<CpuProcessor> getProcessor() {
		return processor;
	}
	public void setProcessor(List<CpuProcessor> processor) {
		this.processor = processor;
	}
	
}
