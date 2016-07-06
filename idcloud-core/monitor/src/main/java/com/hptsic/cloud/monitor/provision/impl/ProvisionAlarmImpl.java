package com.hptsic.cloud.monitor.provision.impl;

import com.hptsic.cloud.monitor.pojo.AlarmListGet;
import com.hptsic.cloud.monitor.pojo.alarm.AlarmInfo;
import com.hptsic.cloud.monitor.provision.ProvisionAlarm;
import com.hptsic.cloud.monitor.provision.action.alarm.QueryAlarmsInfo;
import com.hptsic.cloud.monitor.provision.action.alarm.UpdateAlarmRole;
import com.hptsic.cloud.monitor.provision.exception.AdapterUnavailableException;
import com.hptsic.cloud.monitor.provision.exception.CommonAdapterException;
import com.hptsic.cloud.monitor.provision.model.NodeAlarmResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvisionAlarmImpl implements ProvisionAlarm {
	
	@Autowired
	private QueryAlarmsInfo queryAlarmsInfo;
	@Autowired
	private UpdateAlarmRole updateAlarmRole;
	@Override
	public List<AlarmInfo> getCurrentAlarmInfos(AlarmListGet alarmsInfoGet)
			throws CommonAdapterException, AdapterUnavailableException {
		NodeAlarmResult result = (NodeAlarmResult) queryAlarmsInfo.invoke(alarmsInfoGet);
		return result.getAlarmInfos();
	}
}
