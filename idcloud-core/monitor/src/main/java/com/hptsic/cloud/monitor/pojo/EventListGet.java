package com.hptsic.cloud.monitor.pojo;

import com.hptsic.cloud.monitor.common.Base;

public class EventListGet extends Base {
	private boolean acknowledged;
	private String eventidFrom;
	private String eventidTill;
	private String timeFrom;
	private String timeTill;

	public boolean isAcknowledged() {
		return acknowledged;
	}
	public String getEventidFrom() {
		return eventidFrom;
	}
	public String getEventidTill() {
		return eventidTill;
	}
	public String getTimeFrom() {
		return timeFrom;
	}
	public String getTimeTill() {
		return timeTill;
	}
	public void setAcknowledged(boolean acknowledged) {
		this.acknowledged = acknowledged;
	}
	public void setEventidFrom(String eventidFrom) {
		this.eventidFrom = eventidFrom;
	}
	public void setEventidTill(String eventidTill) {
		this.eventidTill = eventidTill;
	}
	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}
	public void setTimeTill(String timeTill) {
		this.timeTill = timeTill;
	}
	
}
