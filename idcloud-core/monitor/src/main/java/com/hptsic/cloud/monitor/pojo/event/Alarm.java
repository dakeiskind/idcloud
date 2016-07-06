package com.hptsic.cloud.monitor.pojo.event;

import org.codehaus.jackson.annotate.JsonProperty;

public class Alarm {
	private String id;
	private String nodeId;
	private Integer acknowledged;
	private String time;
	@JsonProperty("trigger_id")
	private String triggerId;
	@JsonProperty("trigger_value")
	private String triggerValue;
	private String type;
	@JsonProperty("sub_type")
	private String subType;
	private String msg;
	private String title;
	private String content;
	private String priority;
	private String nodeName;
	
	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	@JsonProperty("node_name")
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getId() {
		return id;
	}
	public Integer getAcknowledged() {
		return acknowledged;
	}
	public String getTime() {
		return time;
	}
	public String getTriggerId() {
		return triggerId;
	}
	public String getTriggerValue() {
		return triggerValue;
	}
	public String getType() {
		return type;
	}
	public String getSubType() {
		return subType;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNodeId() {
		return nodeId;
	}
	@JsonProperty("node_id")
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public void setAcknowledged(Integer acknowledged) {
		this.acknowledged = acknowledged;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setTriggerId(String triggerId) {
		this.triggerId = triggerId;
	}
	public void setTriggerValue(String triggerValue) {
		this.triggerValue = triggerValue;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
}
