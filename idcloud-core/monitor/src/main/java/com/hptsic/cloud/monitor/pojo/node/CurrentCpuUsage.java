package com.hptsic.cloud.monitor.pojo.node;

import java.util.List;

public class CurrentCpuUsage {
	private String usage;
	private List<CpuProcessorUsage> processor;
	public String getUsage() {
		return usage;
	}
	public List<CpuProcessorUsage> getProcessor() {
		return processor;
	}
	public void setUsage(String usage) {
		this.usage = usage;
	}
	public void setProcessor(List<CpuProcessorUsage> processor) {
		this.processor = processor;
	}
}
