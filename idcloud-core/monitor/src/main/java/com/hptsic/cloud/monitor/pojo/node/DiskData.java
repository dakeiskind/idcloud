package com.hptsic.cloud.monitor.pojo.node;

import org.codehaus.jackson.annotate.JsonProperty;

public class DiskData {
	private String path;
	private DiskUsed used;
	private String capacity;

	private String maxUsage;

	private String minUsage;

	private String avgUsage;
	
	public DiskUsed getUsed() {
		return used;
	}
	public void setUsed(DiskUsed used) {
		this.used = used;
	}
	public String getPath() {
		return path;
	}
	public String getCapacity() {
		return capacity;
	}
	public String getMaxUsage() {
		return maxUsage;
	}
	public String getMinUsage() {
		return minUsage;
	}
	public String getAvgUsage() {
		return avgUsage;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	@JsonProperty("max_usage")
	public void setMaxUsage(String maxUsage) {
		this.maxUsage = maxUsage;
	}
	@JsonProperty("min_usage")
	public void setMinUsage(String minUsage) {
		this.minUsage = minUsage;
	}
	@JsonProperty("avg_usage")
	public void setAvgUsage(String avgUsage) {
		this.avgUsage = avgUsage;
	}
}
