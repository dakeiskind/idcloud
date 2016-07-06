package com.hptsic.cloud.monitor.pojo.node;

import java.util.List;

public class NodeNetworkInfo {
	private String id;
	private String unit;
	private String networkAvgIn;
	private String networkAvgOut;
	private String networkMaxIn;
	private String networkMaxOut;
	private String networkMinIn;
	private String networkMinOut;
	private List<NetworkData> data;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getNetworkAvgIn() {
		return networkAvgIn;
	}
	public void setNetworkAvgIn(String networkAvgIn) {
		this.networkAvgIn = networkAvgIn;
	}
	public String getNetworkAvgOut() {
		return networkAvgOut;
	}
	public void setNetworkAvgOut(String networkAvgOut) {
		this.networkAvgOut = networkAvgOut;
	}
	public String getNetworkMaxIn() {
		return networkMaxIn;
	}
	public void setNetworkMaxIn(String networkMaxIn) {
		this.networkMaxIn = networkMaxIn;
	}
	public String getNetworkMaxOut() {
		return networkMaxOut;
	}
	public void setNetworkMaxOut(String networkMaxOut) {
		this.networkMaxOut = networkMaxOut;
	}
	public String getNetworkMinIn() {
		return networkMinIn;
	}
	public void setNetworkMinIn(String networkMinIn) {
		this.networkMinIn = networkMinIn;
	}
	public String getNetworkMinOut() {
		return networkMinOut;
	}
	public void setNetworkMinOut(String networkMinOut) {
		this.networkMinOut = networkMinOut;
	}
	public List<NetworkData> getData() {
		return data;
	}
	public void setData(List<NetworkData> data) {
		this.data = data;
	}
}
