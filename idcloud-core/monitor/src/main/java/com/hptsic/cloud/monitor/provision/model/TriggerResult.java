package com.hptsic.cloud.monitor.provision.model;

import com.hptsic.cloud.monitor.pojo.trigger.TriggerVo;

import java.util.List;

public class TriggerResult extends CommonAdapterResult {
	private List<TriggerVo> triggers;
	private TriggerVo trigger;
	public TriggerVo getTrigger() {
		return trigger;
	}

	public void setTrigger(TriggerVo trigger) {
		this.trigger = trigger;
	}

	public List<TriggerVo> getTriggers() {
		return triggers;
	}

	public void setTriggers(List<TriggerVo> triggers) {
		this.triggers = triggers;
	}
}
