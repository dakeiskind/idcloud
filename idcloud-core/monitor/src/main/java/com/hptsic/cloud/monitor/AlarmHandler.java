package com.hptsic.cloud.monitor;

import com.hptsic.cloud.monitor.common.Base;
import com.hptsic.cloud.monitor.common.ProviderFactory;
import com.hptsic.cloud.monitor.pojo.AlarmAction;
import com.hptsic.cloud.monitor.pojo.AlarmRoleInfoGet;
import com.hptsic.cloud.monitor.pojo.AlarmRoleUpate;
import com.hptsic.cloud.monitor.pojo.EventListGet;
import com.hptsic.cloud.monitor.pojo.event.Alarm;
import com.hptsic.cloud.monitor.pojo.trigger.TriggerVo;
import com.hptsic.cloud.monitor.provision.ProvisionEvent;
import com.hptsic.cloud.monitor.provision.ProvisionTrigger;
import com.hptsic.cloud.monitor.provision.exception.AdapterUnavailableException;
import com.hptsic.cloud.monitor.provision.exception.CommonAdapterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlarmHandler implements ProvisionTrigger, ProvisionEvent {
	@Autowired
	private ProviderFactory providerFactory;

	@Override
	public List<TriggerVo> getAlarmRoleList(Base base)
			throws CommonAdapterException, AdapterUnavailableException {
		return providerFactory.getProvisionTrigger(base.getProviderType()).getAlarmRoleList(base);
	}

	@Override
	public TriggerVo getAlarmRoleInfo(AlarmRoleInfoGet triggerInfoGet)
			throws CommonAdapterException, AdapterUnavailableException {
		return providerFactory.getProvisionTrigger(triggerInfoGet.getProviderType()).getAlarmRoleInfo(triggerInfoGet);
	}

	@Override
	public boolean updateAlarmRoleInfo(AlarmRoleUpate triggerInfoUpdate)
			throws CommonAdapterException, AdapterUnavailableException {
		return providerFactory.getProvisionTrigger(triggerInfoUpdate.getProviderType()).updateAlarmRoleInfo(triggerInfoUpdate);
	}

	@Override
	public List<Alarm> getAlarmList(EventListGet eventListGet)
			throws CommonAdapterException, AdapterUnavailableException {
		return providerFactory.getProvisionEvent(eventListGet.getProviderType()).getAlarmList(eventListGet);
	}

	@Override
	public boolean ackAlarm(AlarmAction action) throws CommonAdapterException,
			AdapterUnavailableException {
		return providerFactory.getProvisionEvent(action.getProviderType()).ackAlarm(action);
	}


}
