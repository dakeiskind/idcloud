package com.hptsic.cloud.monitor.provision;

import com.hptsic.cloud.monitor.pojo.AlarmAction;
import com.hptsic.cloud.monitor.pojo.EventListGet;
import com.hptsic.cloud.monitor.pojo.event.Alarm;
import com.hptsic.cloud.monitor.provision.exception.AdapterUnavailableException;
import com.hptsic.cloud.monitor.provision.exception.CommonAdapterException;

import java.util.List;

public interface ProvisionEvent {
	/***
	 * 获取事件列表 //对平台是获取告警列表
	 * @param eventListGet
	 * @return
	 * @throws CommonAdapterException
	 * @throws AdapterUnavailableException
	 */
	public List<Alarm> getAlarmList(EventListGet eventListGet) throws CommonAdapterException, AdapterUnavailableException;
	/***
	 * 确认事件 //确认告警
	 * @param action
	 * @return
	 * @throws CommonAdapterException
	 * @throws AdapterUnavailableException
	 */
	public boolean  ackAlarm(AlarmAction action) throws CommonAdapterException, AdapterUnavailableException;
}
