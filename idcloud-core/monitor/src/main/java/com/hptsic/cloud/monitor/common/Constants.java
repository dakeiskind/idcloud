package com.hptsic.cloud.monitor.common;

public interface Constants {

	public interface Provider {

//		String KVM = "Kvm";
		String OPENNMS = "opennms";

		String VMWARE = "Vmware";

		String HYPERV = "HyperV";
		
		String HMC = "HMC";
	}

	public interface AdapterUnvailableException {

		String CODE = "9999";
		String MSG = "adapter unavailable";
	}

}
