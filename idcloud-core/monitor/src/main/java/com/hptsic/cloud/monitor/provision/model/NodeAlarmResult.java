package com.hptsic.cloud.monitor.provision.model;

import com.hptsic.cloud.monitor.pojo.alarm.AlarmInfo;
import com.hptsic.cloud.monitor.pojo.alarm.ServiceContext;

import java.util.List;

public class NodeAlarmResult extends CommonAdapterResult {
	private List<AlarmInfo> alarmInfos;
	private List<ServiceContext> serviceContexts;
	public List<ServiceContext> getServiceContexts() {
		return serviceContexts;
	}

	public void setServiceContexts(List<ServiceContext> serviceContexts) {
		this.serviceContexts = serviceContexts;
	}

	public List<AlarmInfo> getAlarmInfos() {
		return alarmInfos;
	}

	public void setAlarmInfos(List<AlarmInfo> alarmInfos) {
		this.alarmInfos = alarmInfos;
	}
}
