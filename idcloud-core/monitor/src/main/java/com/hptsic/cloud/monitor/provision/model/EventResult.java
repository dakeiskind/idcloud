package com.hptsic.cloud.monitor.provision.model;

import com.hptsic.cloud.monitor.pojo.event.Alarm;

import java.util.ArrayList;
import java.util.List;

public class EventResult extends CommonAdapterResult {
	private List<Alarm> events = new ArrayList<Alarm>();
	private Alarm event = new Alarm();
	public List<Alarm> getEvents() {
		return events;
	}
	public Alarm getEvent() {
		return event;
	}
	public void setEvents(List<Alarm> events) {
		this.events = events;
	}
	public void setEvent(Alarm event) {
		this.event = event;
	}
}
