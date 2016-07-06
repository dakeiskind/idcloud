package com.hptsic.cloud.monitor.pojo.node;

import org.codehaus.jackson.annotate.JsonProperty;

public class NodeMemInfo {
	private String id;
	private String avgUsage;
	private String maxUsage;
	private String minUsage;
	private String capacity;
	private MemUsed used;
	
	public MemUsed getUsed() {
		return used;
	}
	public void setUsed(MemUsed used) {
		this.used = used;
	}
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getAvgUsage() {
		return avgUsage;
	}
	@JsonProperty("avg_usage")
	public void setAvgUsage(String avgUsage) {
		this.avgUsage = avgUsage;
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
}
