package com.hptsic.cloud.monitor.pojo.node;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class NetworkDataMeta {
	private List<NetworkIn> networkIns;
	private List<NetworkOut> networkOuts;
	public List<NetworkIn> getNetworkIns() {
		return networkIns;
	}
	@JsonProperty("network_in")
	public void setNetworkIns(List<NetworkIn> networkIns) {
		this.networkIns = networkIns;
	}
	public List<NetworkOut> getNetworkOuts() {
		return networkOuts;
	}
	@JsonProperty("network_out")
	public void setNetworkOuts(List<NetworkOut> networkOuts) {
		this.networkOuts = networkOuts;
	}
}
