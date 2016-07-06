package com.hptsic.cloud.monitor.pojo.alarm;

import org.codehaus.jackson.annotate.JsonProperty;

public class AlarmInfo {
	private String alarmId;
	private String alarmTarget;
	private String alarmLevel;
	private String alarmStatus;
	private String alarmType;
	private String alarmDetail;
	private String alarmTime;
	public String getAlarmId() {
		return alarmId;
	}
	@JsonProperty("alarm_id")
	public void setAlarmId(String alarmId) {
		this.alarmId = alarmId;
	}
	public String getAlarmTarget() {
		return alarmTarget;
	}
	@JsonProperty("alarm_target")
	public void setAlarmTarget(String alarmTarget) {
		this.alarmTarget = alarmTarget;
	}
	public String getAlarmLevel() {
		return alarmLevel;
	}
	@JsonProperty("alarm_level")
	public void setAlarmLevel(String alarmLevel) {
		this.alarmLevel = alarmLevel;
	}
	public String getAlarmStatus() {
		return alarmStatus;
	}
	@JsonProperty("alarm_status")
	public void setAlarmStatus(String alarmStatus) {
		this.alarmStatus = alarmStatus;
	}
	public String getAlarmType() {
		return alarmType;
	}
	@JsonProperty("alarm_type")
	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}
	public String getAlarmDetail() {
		return alarmDetail;
	}
	@JsonProperty("alarm_detail")
	public void setAlarmDetail(String alarmDetail) {
		this.alarmDetail = alarmDetail;
	}
	public String getAlarmTime() {
		return alarmTime;
	}
	@JsonProperty("alarm_time")
	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}
}
