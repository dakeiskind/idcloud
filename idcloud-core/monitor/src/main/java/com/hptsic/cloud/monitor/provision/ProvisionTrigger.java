package com.hptsic.cloud.monitor.provision;

import com.hptsic.cloud.monitor.common.Base;
import com.hptsic.cloud.monitor.pojo.AlarmRoleInfoGet;
import com.hptsic.cloud.monitor.pojo.AlarmRoleUpate;
import com.hptsic.cloud.monitor.pojo.trigger.TriggerVo;
import com.hptsic.cloud.monitor.provision.exception.AdapterUnavailableException;
import com.hptsic.cloud.monitor.provision.exception.CommonAdapterException;

import java.util.List;

public interface ProvisionTrigger {
	/***
	 * 获取触发器列表 //更新告警规则
	 * @param base
	 * @return List<TriggerVo> 触发器列表
	 * @throws CommonAdapterException
	 * @throws AdapterUnavailableException
	 */
	public List<TriggerVo> getAlarmRoleList(Base base) throws CommonAdapterException, AdapterUnavailableException;
	/***
	 * 获取触发器详情  //获取告警规则信息
	 * @param triggerInfoGet
	 * @return
	 * @throws CommonAdapterException
	 * @throws AdapterUnavailableException
	 */
	public TriggerVo getAlarmRoleInfo(AlarmRoleInfoGet triggerInfoGet) throws CommonAdapterException, AdapterUnavailableException;
	/***
	 * 获取触发器列表 // 获取告警规则列表
	 * @param triggerInfoUpdate
	 * @return
	 * @throws CommonAdapterException
	 * @throws AdapterUnavailableException
	 */
	public boolean updateAlarmRoleInfo(AlarmRoleUpate triggerInfoUpdate) throws CommonAdapterException, AdapterUnavailableException;
}
