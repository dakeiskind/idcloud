package com.hptsic.cloud.monitor.provision.model;

import java.util.List;

public class CommonAdapterFault {

	private String type;

	private String message;

	private String vmId;

	private List<String> disks;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	public List<String> getDisks() {
		return disks;
	}

	public void setDisks(List<String> disks) {
		this.disks = disks;
	}

}
