package com.hptsic.cloud.monitor.pojo.node;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class NodeDiskInfo {

	private String avgUsage;

	private String avgUsed;

	private List<DiskData> disk;
	
	private String maxUsage;
	
	private String maxUsed;
	
	private String minUsage;
	
	private String minUsed;

	public String getAvgUsage() {
		return avgUsage;
	}

	public String getAvgUsed() {
		return avgUsed;
	}

	public List<DiskData> getDisk() {
		return disk;
	}

	public String getMaxUsage() {
		return maxUsage;
	}

	public String getMaxUsed() {
		return maxUsed;
	}
	public String getMinUsage() {
		return minUsage;
	}
	public String getMinUsed() {
		return minUsed;
	}
	@JsonProperty("avg_usage")
	public void setAvgUsage(String avgUsage) {
		this.avgUsage = avgUsage;
	}

	@JsonProperty("avg_used")
	public void setAvgUsed(String avgUsed) {
		this.avgUsed = avgUsed;
	}
	public void setDisk(List<DiskData> disk) {
		this.disk = disk;
	}
	@JsonProperty("max_usage")
	public void setMaxUsage(String maxUsage) {
		this.maxUsage = maxUsage;
	}
	@JsonProperty("max_used")
	public void setMaxUsed(String maxUsed) {
		this.maxUsed = maxUsed;
	}

	@JsonProperty("min_usage")
	public void setMinUsage(String minUsage) {
		this.minUsage = minUsage;
	}

	@JsonProperty("min_used")
	public void setMinUsed(String minUsed) {
		this.minUsed = minUsed;
	}
	
}
