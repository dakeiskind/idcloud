package com.hptsic.cloud.monitor.pojo.node;

import java.util.List;

public class DiskUsed {
	private String max;
	private String min;
	private String avg;
	private List<DiskDataMeta> data;
	public String getMax() {
		return max;
	}
	public String getMin() {
		return min;
	}
	public String getAvg() {
		return avg;
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
	public List<DiskDataMeta> getData() {
		return data;
	}
	public void setData(List<DiskDataMeta> data) {
		this.data = data;
	}
}
