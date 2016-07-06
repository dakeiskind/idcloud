package com.hptsic.cloud.monitor.pojo.node;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

public class NetworkData {
	private String time;
	private String inVal;
	private String outVal;
	private List<NetworkDataMeta> data;
	public List<NetworkDataMeta> getData() {
		return data;
	}
	public void setData(List<NetworkDataMeta> data) {
		this.data = data;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getInVal() {
		return inVal;
	}
	@JsonProperty("in_val")
	public void setInVal(String inVal) {
		this.inVal = inVal;
	}
	public String getOutVal() {
		return outVal;
	}
	@JsonProperty("out_val")
	public void setOutVal(String outVal) {
		this.outVal = outVal;
	}
}
