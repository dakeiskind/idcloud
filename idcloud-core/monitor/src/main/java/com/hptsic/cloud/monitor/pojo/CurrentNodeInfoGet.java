package com.hptsic.cloud.monitor.pojo;

import com.hptsic.cloud.monitor.common.Base;

import java.util.List;

public class CurrentNodeInfoGet extends Base {
	private String nodeId;
	private List<String> typeList;
	public String getNodeId() {
		return nodeId;
	}
	public List<String> getTypeList() {
		return typeList;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public void setTypeList(List<String> typeList) {
		this.typeList = typeList;
	}
}
