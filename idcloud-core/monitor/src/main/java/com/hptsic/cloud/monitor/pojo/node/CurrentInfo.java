package com.hptsic.cloud.monitor.pojo.node;

import com.hptsic.cloud.monitor.common.BaseResult;

import java.util.List;

public class CurrentInfo extends BaseResult{
	private CurrentCpuUsage cpu;
	private List<DiskData> disk;
	private MemCurrent memory;
	public List<DiskData> getDisk() {
		return disk;
	}
	public MemCurrent getMemory() {
		return memory;
	}
	public void setDisk(List<DiskData> disk) {
		this.disk = disk;
	}
	public void setMemory(MemCurrent memory) {
		this.memory = memory;
	}
	public CurrentCpuUsage getCpu() {
		return cpu;
	}
	public void setCpu(CurrentCpuUsage cpu) {
		this.cpu = cpu;
	}
	
}
