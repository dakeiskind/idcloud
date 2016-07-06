package com.hptsic.cloud.monitor.pojo.node;

import java.util.List;

public class MemUsed {
	private String max;
	private String min;
	private String avg;
	private List<MemData> data;
	public String getMax() {
		return max;
	}
	public String getMin() {
		return min;
	}
	public String getAvg() {
		return avg;
	}
	public List<MemData> getData() {
		return data;
	}
	public void setMax(String max) {
		this.max = max;
	}
	public void setMin(String min) {
		this.min = min;
	}
	public void setAvg(String avg) {
		this.avg = avg;
	}
	public void setData(List<MemData> data) {
		this.data = data;
	}
}
