package com.hptsic.cloud.monitor.provision;

import com.hptsic.cloud.monitor.pojo.AlarmListGet;
import com.hptsic.cloud.monitor.pojo.alarm.AlarmInfo;
import com.hptsic.cloud.monitor.provision.exception.AdapterUnavailableException;
import com.hptsic.cloud.monitor.provision.exception.CommonAdapterException;

import java.util.List;

public interface ProvisionAlarm {
	public List<AlarmInfo> getCurrentAlarmInfos(AlarmListGet alarmsInfoGet) throws CommonAdapterException, AdapterUnavailableException;
	
}
