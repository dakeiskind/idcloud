package com.hptsic.cloud.monitor.provision.model;

import com.hptsic.cloud.monitor.pojo.node.*;

import java.util.List;

public class NodeMonitorResult extends CommonAdapterResult {
	private NodeInfo nodeInfo;
	private NodeMonitorInfo nodeMonitorInfo;
	private NodeCpuInfo CpuInfo;
	private NodeMemInfo memInfo;
	private NodeNetworkInfo networkInfo;
	private NodeDiskInfo diskInfo;
	private List<NodeInfo> nodeList;
	public List<NodeInfo> getNodeList() {
		return nodeList;
	}
	public void setNodeList(List<NodeInfo> nodeList) {
		this.nodeList = nodeList;
	}
	public NodeInfo getNodeInfo() {
		return nodeInfo;
	}
	public void setNodeInfo(NodeInfo nodeInfo) {
		this.nodeInfo = nodeInfo;
	}
	public NodeMonitorInfo getNodeMonitorInfo() {
		return nodeMonitorInfo;
	}
	public void setNodeMonitorInfo(NodeMonitorInfo nodeMonitorInfo) {
		this.nodeMonitorInfo = nodeMonitorInfo;
	}
	public NodeCpuInfo getCpuInfo() {
		return CpuInfo;
	}
	public void setCpuInfo(NodeCpuInfo cpuInfo) {
		CpuInfo = cpuInfo;
	}
	public NodeMemInfo getMemInfo() {
		return memInfo;
	}
	public void setMemInfo(NodeMemInfo memInfo) {
		this.memInfo = memInfo;
	}
	public NodeNetworkInfo getNetworkInfo() {
		return networkInfo;
	}
	public void setNetworkInfo(NodeNetworkInfo networkInfo) {
		this.networkInfo = networkInfo;
	}
	public NodeDiskInfo getDiskInfo() {
		return diskInfo;
	}
	public void setDiskInfo(NodeDiskInfo diskInfo) {
		this.diskInfo = diskInfo;
	}
}
