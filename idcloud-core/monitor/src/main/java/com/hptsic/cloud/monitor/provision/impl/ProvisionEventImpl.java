package com.hptsic.cloud.monitor.provision.impl;

import com.hptsic.cloud.monitor.pojo.AlarmAction;
import com.hptsic.cloud.monitor.pojo.EventListGet;
import com.hptsic.cloud.monitor.pojo.event.Alarm;
import com.hptsic.cloud.monitor.provision.ProvisionEvent;
import com.hptsic.cloud.monitor.provision.action.event.EventAction;
import com.hptsic.cloud.monitor.provision.action.event.QueryEventList;
import com.hptsic.cloud.monitor.provision.exception.AdapterUnavailableException;
import com.hptsic.cloud.monitor.provision.exception.CommonAdapterException;
import com.hptsic.cloud.monitor.provision.model.EventResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvisionEventImpl implements ProvisionEvent {
	@Autowired
	private QueryEventList queryEventList;
	@Autowired
	private EventAction eventAction;
	@Override
	public List<Alarm> getAlarmList(EventListGet eventListGet)
			throws CommonAdapterException, AdapterUnavailableException {
		EventResult result = (EventResult) queryEventList.invoke(eventListGet);
		return result.getEvents();
	}

	@Override
	public boolean ackAlarm(AlarmAction action) throws CommonAdapterException,
			AdapterUnavailableException {
		
		return eventAction.invoke(action).isSuccess();
	}

}
