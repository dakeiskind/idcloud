package com.hptsic.cloud.monitor.provision.impl;

import com.hptsic.cloud.monitor.common.Base;
import com.hptsic.cloud.monitor.pojo.AlarmRoleInfoGet;
import com.hptsic.cloud.monitor.pojo.AlarmRoleUpate;
import com.hptsic.cloud.monitor.pojo.trigger.TriggerVo;
import com.hptsic.cloud.monitor.provision.ProvisionTrigger;
import com.hptsic.cloud.monitor.provision.action.trigger.QueryTriggerInfo;
import com.hptsic.cloud.monitor.provision.action.trigger.QueryTriggerListInfo;
import com.hptsic.cloud.monitor.provision.action.trigger.UpdateTriggerInfo;
import com.hptsic.cloud.monitor.provision.exception.AdapterUnavailableException;
import com.hptsic.cloud.monitor.provision.exception.CommonAdapterException;
import com.hptsic.cloud.monitor.provision.model.TriggerResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvisionTriggerImpl implements ProvisionTrigger{
	@Autowired
	private QueryTriggerListInfo queryTriggerList;
	@Autowired
	private QueryTriggerInfo queryTriggerInfo;
	@Autowired
	private UpdateTriggerInfo updateTriggerInfo;
	@Override
	public List<TriggerVo> getAlarmRoleList(Base base)
			throws CommonAdapterException, AdapterUnavailableException {
		TriggerResult result  = (TriggerResult) queryTriggerList.invoke(base);
		return result.getTriggers();
	}
	@Override
	public TriggerVo getAlarmRoleInfo(AlarmRoleInfoGet triggerInfoGet)
			throws CommonAdapterException, AdapterUnavailableException {
		TriggerResult result  = (TriggerResult) queryTriggerInfo.invoke(triggerInfoGet);
		return result.getTrigger();
	}
	@Override
	public boolean updateAlarmRoleInfo(AlarmRoleUpate triggerInfoUpdate)
			throws CommonAdapterException, AdapterUnavailableException {
		return updateTriggerInfo.invoke(triggerInfoUpdate).isSuccess();
	}

}
