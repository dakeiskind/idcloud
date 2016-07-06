package com.hptsic.cloud.monitor;

import com.hptsic.cloud.monitor.pojo.AlarmAction;
import com.hptsic.cloud.monitor.pojo.EventListGet;
import com.hptsic.cloud.monitor.pojo.event.Alarm;
import com.hptsic.cloud.monitor.provision.ProvisionEvent;
import com.hptsic.cloud.monitor.provision.action.event.QueryEventList;
import com.hptsic.cloud.monitor.provision.exception.AdapterUnavailableException;
import com.hptsic.cloud.monitor.provision.exception.CommonAdapterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventHandler implements ProvisionEvent {
	@Autowired
	private QueryEventList queryEventList;

	@Override
	public List<Alarm> getAlarmList(EventListGet eventListGet)
			throws CommonAdapterException, AdapterUnavailableException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean ackAlarm(AlarmAction action) throws CommonAdapterException,
			AdapterUnavailableException {
		// TODO Auto-generated method stub
		return false;
	}
}
