package com.hptsic.cloud.monitor.common;

import com.hptsic.cloud.monitor.provision.ProvisionAlarm;
import com.hptsic.cloud.monitor.provision.ProvisionEvent;
import com.hptsic.cloud.monitor.provision.ProvisionMonitor;
import com.hptsic.cloud.monitor.provision.ProvisionTrigger;
import com.hptsic.cloud.monitor.provision.impl.ProvisionAlarmImpl;
import com.hptsic.cloud.monitor.provision.impl.ProvisionEventImpl;
import com.hptsic.cloud.monitor.provision.impl.ProvisionMonitroImpl;
import com.hptsic.cloud.monitor.provision.impl.ProvisionTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderFactory {
	@Autowired
	private ProvisionMonitroImpl provisionMonitro;
	@Autowired
	private ProvisionAlarmImpl provisionAlarm;
	@Autowired 
	private ProvisionTriggerImpl provisionTrigger;
	@Autowired
	private ProvisionEventImpl provisionEvent;
	
	public ProvisionMonitor getProvisionMonitor(String providerType) {

		if (providerType.equalsIgnoreCase(Constants.Provider.OPENNMS)) {
			return provisionMonitro;

		} else if (providerType.equalsIgnoreCase(Constants.Provider.VMWARE)) {
			return provisionMonitro;

		}else if (providerType.equalsIgnoreCase(Constants.Provider.HMC)) {
			return provisionMonitro;

		}else {
			return null;
		}

	}
	public ProvisionTrigger getProvisionTrigger(String providerType)
	{
		if (providerType.equalsIgnoreCase(Constants.Provider.OPENNMS)) {
			return provisionTrigger;

		} else if (providerType.equalsIgnoreCase(Constants.Provider.VMWARE)) {
			return provisionTrigger;

		}else if (providerType.equalsIgnoreCase(Constants.Provider.HMC)) {
			return provisionTrigger;

		}else {
			return null;
		}
	}
	public ProvisionAlarm getProvisionAlarm(String providerType) {

		if (providerType.equalsIgnoreCase(Constants.Provider.OPENNMS)) {
			return provisionAlarm;

		} else if (providerType.equalsIgnoreCase(Constants.Provider.VMWARE)) {
			return provisionAlarm;

		}else if (providerType.equalsIgnoreCase(Constants.Provider.HMC)) {
			return provisionAlarm;

		}else {
			return null;
		}

	}
	public ProvisionEvent getProvisionEvent(String providerType) {
		
		if (providerType.equalsIgnoreCase(Constants.Provider.OPENNMS)) {
			return provisionEvent;
			
		} else if (providerType.equalsIgnoreCase(Constants.Provider.VMWARE)) {
			return provisionEvent;
			
		}else if (providerType.equalsIgnoreCase(Constants.Provider.HMC)) {
			return provisionEvent;
			
		}else {
			return null;
		}
		
	}
}
