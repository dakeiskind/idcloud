package com.hptsic.cloud.monitor.pojo.node;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class NodeMonitorInfo {
	private String id;
	private String cpuUsage;
	private String memUsage;
	private NodeNetwork network;
	private List<NodeDisk> disk;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCpuUsage() {
		return cpuUsage;
	}
	@JsonProperty("cpu_usage")
	public void setCpuUsage(String cpuUsage) {
		this.cpuUsage = cpuUsage;
	}
	public String getMemUsage() {
		return memUsage;
	}
	@JsonProperty("mem_usage")
	public void setMemUsage(String memUsage) {
		this.memUsage = memUsage;
	}
	public NodeNetwork getNetwork() {
		return network;
	}
	public void setNetwork(NodeNetwork network) {
		this.network = network;
	}
	public List<NodeDisk> getDisk() {
		return disk;
	}
	public void setDisk(List<NodeDisk> disk) {
		this.disk = disk;
	}
}
