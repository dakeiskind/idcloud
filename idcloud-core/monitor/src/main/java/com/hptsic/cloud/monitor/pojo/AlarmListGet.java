package com.hptsic.cloud.monitor.pojo;

import com.hptsic.cloud.monitor.common.Base;

import java.util.List;

/***
 * 获取告警信息输入参数，可以同时获取多个节点id的信息
 * @author hpadmin
 *
 */
public class AlarmListGet extends Base{
	private String timeFrom;
	private String timeTill;
	private String eventidFrom;
	private String eventidTill;
	private boolean acknowledged;

	private List<String> ids;
	
	public String getTimeFrom() {
		return timeFrom;
	}

	public String getTimeTill() {
		return timeTill;
	}

	public String getEventidFrom() {
		return eventidFrom;
	}

	public String getEventidTill() {
		return eventidTill;
	}

	public boolean isAcknowledged() {
		return acknowledged;
	}

	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}

	public void setTimeTill(String timeTill) {
		this.timeTill = timeTill;
	}

	public void setEventidFrom(String eventidFrom) {
		this.eventidFrom = eventidFrom;
	}

	public void setEventidTill(String eventidTill) {
		this.eventidTill = eventidTill;
	}

	public void setAcknowledged(boolean acknowledged) {
		this.acknowledged = acknowledged;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}
}
