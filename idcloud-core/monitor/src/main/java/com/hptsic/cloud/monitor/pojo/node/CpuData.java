package com.hptsic.cloud.monitor.pojo.node;

import org.codehaus.jackson.annotate.JsonProperty;

public class CpuData {
	private String time;
	private String usage;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getUsage() {
		return usage;
	}
	@JsonProperty("value")
	public void setUsage(String usage) {
		this.usage = usage;
	}
}
